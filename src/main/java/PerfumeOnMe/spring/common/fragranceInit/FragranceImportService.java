package PerfumeOnMe.spring.common.fragranceInit;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.fragrance.domain.Price;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragrancePrice;
import PerfumeOnMe.spring.fragrance.repository.FragranceRepository;
import PerfumeOnMe.spring.fragrance.repository.fragrancePrice.FragrancePriceRepository;
import PerfumeOnMe.spring.fragrance.repository.price.PriceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 엑셀로부터 향수 정보를 불러와 DB에 저장하는 서비스 클래스
 * - 향수 정보는 "향수정보" 시트에서 가져오며, FragranceRowProcessor를 통해 처리
 * - 가격 정보는 "가격" 시트에서 직접 처리하여 저장
 * - @PostConstruct 어노테이션을 통해 서버 구동 시 자동 실행
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class FragranceImportService {

	private final FragranceRepository fragranceRepository;
	private final FragranceRowProcessor fragranceRowProcessor;
	private final FragrancePriceRepository fragrancePriceRepository;
	private final PriceRepository priceRepository;

	/**
	 * 애플리케이션 실행 시 엑셀 파일을 읽고 전체 데이터를 DB에 로드함
	 */
	@PostConstruct
	public void init() throws Exception {
		InputStream is = new ClassPathResource(
			"data/perfumeOnMe_data.xlsx").getInputStream(); // resources 폴더에서 엑셀 파일 로드

		Workbook workbook = WorkbookFactory.create(is); // 엑셀 Workbook 객체 생성
		importAllFromWorkbook(workbook);
		log.info("✅향수 엑셀 데이터 로드 완료!");
	}

	/**
	 * 엑셀 파일 시트를 순회하며 향수 정보와 가격 정보를 각각 처리
	 * 엑셀 파일에서 밑에 있는 향수정보 시트와 가격 시트를 각각 가져옴
	 */
	public void importAllFromWorkbook(Workbook workbook) {
		Sheet infoSheet = workbook.getSheet("향수정보");
		for (Row row : infoSheet) {
			if (row.getRowNum() == 0 || isRowEmpty(row)) // 첫 줄(헤더)이거나 비어있는 행은 건너뜀
				continue;
			fragranceRowProcessor.importFragranceFromExcelRow(row); // 향수 정보 저장
		}

		Sheet priceSheet = workbook.getSheet("가격");
		for (Row row : priceSheet) {
			if (row.getRowNum() == 0 || isRowEmpty(row)) // 첫 줄(헤더)이거나 비어있는 행은 건너뜀
				continue;
			importPriceFromRow(row); // 가격 정보 저장
		}
	}

	/**
	 * 개별 가격 Row 를 처리하여 FragrancePrice 및 Price 엔티티를 저장
	 */
	private void importPriceFromRow(Row row) {
		String idStr = getCellValue(row, 0); // perfume_id
		String mlStr = getCellValue(row, 1); // ml 용량
		String priceStr = getCellValue(row, 2); // price(가격)

		try {
			Long perfumeId = (long)Double.parseDouble(idStr);
			int mlCount = (int)Double.parseDouble(mlStr);
			int price = (int)Double.parseDouble(priceStr);

			// 향수가 존재할 경우 가격 및 매핑 정보 저장
			fragranceRepository.findById(perfumeId).ifPresent(fragrance -> {
				// 동일한 ml, 가격이 이미 존재하는지 확인
				Price savedPrice = priceRepository.findByMlCountAndPrice(mlCount, price)
					.orElseGet(() -> priceRepository.save(
						Price.builder()
							.mlCount(mlCount)
							.price(price)
							.build()
					));
				// 이미 연결된 fragrance + price 조합이 있는지 확인하고 없을 때만 매핑 저장
				boolean alreadyMapped = fragrancePriceRepository
					.existsByFragranceAndPrice(fragrance, savedPrice);

				if (!alreadyMapped) {
					fragrancePriceRepository.save(
						FragrancePrice.builder()
							.fragrance(fragrance)
							.price(savedPrice)
							.build()
					);
				}
			});
		} catch (NumberFormatException e) {
			throw new GeneralException(ErrorStatus.PRICE_PARSING_ERROR, "가격 파싱 실패: " + priceStr); // 파싱 실패 로그
		}
	}

	/**
	 * 셀에서 문자열 값을 안전하게 가져오는 유틸 메서드
	 * row = 행, index = 열
	 */
	private String getCellValue(Row row, int index) {
		Cell cell = row.getCell(index);
		return cell == null ? "" : cell.toString().trim();
	}

	/**
	 * 행이 비어있는지 여부를 판단 (모든 셀이 비어있거나 BLANK 인 경우 true)
	 */
	private boolean isRowEmpty(Row row) {
		if (row == null)
			return true;
		for (int c = 0; c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != CellType.BLANK && !cell.toString().trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}
}