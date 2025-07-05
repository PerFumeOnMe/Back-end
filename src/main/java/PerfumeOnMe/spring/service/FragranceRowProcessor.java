package PerfumeOnMe.spring.service;

import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.Location;
import PerfumeOnMe.spring.domain.Note;
import PerfumeOnMe.spring.domain.Season;
import PerfumeOnMe.spring.domain.enums.Brand;
import PerfumeOnMe.spring.domain.enums.FragranceGender;
import PerfumeOnMe.spring.domain.enums.FragranceType;
import PerfumeOnMe.spring.domain.enums.NoteType;
import PerfumeOnMe.spring.domain.mapping.FragranceBaseNote;
import PerfumeOnMe.spring.domain.mapping.FragranceLocation;
import PerfumeOnMe.spring.domain.mapping.FragranceMiddleNote;
import PerfumeOnMe.spring.domain.mapping.FragranceSeason;
import PerfumeOnMe.spring.domain.mapping.FragranceTopNote;
import PerfumeOnMe.spring.repository.fragrance.FragranceRepository;
import PerfumeOnMe.spring.repository.fragranceBaseNote.FragranceBaseNoteRepository;
import PerfumeOnMe.spring.repository.fragranceLocation.FragranceLocationRepository;
import PerfumeOnMe.spring.repository.fragranceMiddleNote.FragranceMiddleNoteRepository;
import PerfumeOnMe.spring.repository.fragranceSeason.FragranceSeasonRepository;
import PerfumeOnMe.spring.repository.fragranceTopNote.FragranceTopNoteRepository;
import PerfumeOnMe.spring.repository.location.LocationRepository;
import PerfumeOnMe.spring.repository.note.NoteRepository;
import PerfumeOnMe.spring.repository.season.SeasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FragranceRowProcessor {

	// 필요한 Repository 들 의존성 주입
	private final FragranceRepository fragranceRepository;
	private final NoteRepository noteRepository;
	private final FragranceTopNoteRepository fragranceTopNoteRepository;
	private final FragranceMiddleNoteRepository fragranceMiddleNoteRepository;
	private final FragranceBaseNoteRepository fragranceBaseNoteRepository;
	private final LocationRepository locationRepository;
	private final FragranceLocationRepository fragranceLocationRepository;
	private final SeasonRepository seasonRepository;
	private final FragranceSeasonRepository fragranceSeasonRepository;

	/**
	 * 엑셀 한 행(Row)의 향수 데이터를 읽어 DB에 저장하는 핵심 메서드
	 * @Transactional : 한 행 단위로 트랜잭션 보장
	 */
	@Transactional
	public void importFragranceFromExcelRow(Row row) {
		String name = getCellValue(row, 1);

		// 이미 저장된 향수라면 중복 저장 방지
		if (fragranceRepository.findByName(name).isPresent()) {
			log.info("⚠️이미 존재하는 향수: " + name + " → 저장하지 않음");
			return;
		}

		// 엑셀 각 셀 데이터 추출
		String brandStr = getCellValue(row, 2);
		String keywordStr = getCellValue(row, 4);
		String topNotesStr = getCellValue(row, 5);
		String topNoteKeyword = getCellValue(row, 6);
		String topNoteDescription = getCellValue(row, 7);
		String middleNotesStr = getCellValue(row, 8);
		String middleNoteKeyword = getCellValue(row, 9);
		String middleNoteDescription = getCellValue(row, 10);
		String baseNotesStr = getCellValue(row, 11);
		String baseNoteKeyword = getCellValue(row, 12);
		String baseNoteDescription = getCellValue(row, 13);
		String description = getCellValue(row, 14);
		String genderStr = getCellValue(row, 15);
		String locationStr = getCellValue(row, 16);
		String seasonStr = getCellValue(row, 17);
		String homepage = getCellValue(row, 18);
		String typeStr = getCellValue(row, 19);
		String imageUrl = getCellValue(row, 20);

		// 문자열을 enum 으로 변환
		Brand brand = convertToBrand(brandStr);
		FragranceGender gender = convertToFragranceGender(genderStr);
		FragranceType type = convertType(typeStr);

		// Fragrance 엔티티 생성 및 저장
		Fragrance fragrance = Fragrance.builder()
			.name(name)
			.brand(brand)
			.description(description)
			.gender(gender)
			.fragranceType(type)
			.homePageURL(homepage)
			.imageURL(imageUrl)
			.keyword(keywordStr)
			.topNoteKeyword(topNoteKeyword)
			.middleNoteKeyword(middleNoteKeyword)
			.baseNoteKeyword(baseNoteKeyword)
			.topNoteDescription(topNoteDescription)
			.middleNoteDescription(middleNoteDescription)
			.baseNoteDescription(baseNoteDescription)
			.build();

		fragranceRepository.save(fragrance);

		// 향수와 각 노트/계절/장소 정보 연결
		saveNotes(fragrance, topNotesStr, NoteType.TOP); // 탑 노트
		saveNotes(fragrance, middleNotesStr, NoteType.MIDDLE); // 미들 노트
		saveNotes(fragrance, baseNotesStr, NoteType.BASE); // 베이스 노트
		saveLocations(fragrance, locationStr);
		saveSeasons(fragrance, seasonStr);
	}

	private String getCellValue(Row row, int index) {
		Cell cell = row.getCell(index);
		return cell == null ? "" : cell.toString().trim();
	}

	/**
	 * 노트 문자열 리스트(noteStr)를 파싱하고
	 * Note 테이블과 각 노트 매핑 테이블(top, middle, base)에 저장
	 */
	private void saveNotes(Fragrance fragrance, String noteStr, NoteType type) {
		Arrays.stream(noteStr.split(","))
			.map(String::trim)
			.filter(n -> !n.isEmpty())
			.forEach(noteName -> {
				Note note = noteRepository.findByName(noteName).orElse(null);
				if (note == null) {
					// Note 가 없으면 새로 저장
					note = Note.builder()
						.name(noteName)
						.top(type == NoteType.TOP) // 해당 노트가 TOP 이면 true
						.middle(type == NoteType.MIDDLE) // 해당 노트가 MIDDLE 이면 true
						.base(type == NoteType.BASE) // 해당 노트가 BASE 이면 true
						.build();
				} else {  // 이미 해당 노트가 존재하다면 해당 노트에 대한 필드 활성화만 0 -> 1

					// builder()는 객체를 새로 만들 때만 사용함.
					// 이미 존재하는 Note 객체를 수정하는 것은 builder()로 수정 불가
					// 따라서 note 테이블에서 activateType() 메서드를 만들어서 필드 수정
					note.activateType(type);
				}
				noteRepository.save(note);

				// 매핑 테이블 저장
				if (type == NoteType.TOP) {
					fragranceTopNoteRepository.save(FragranceTopNote.builder().fragrance(fragrance).note(note).build());
				} else if (type == NoteType.MIDDLE) {
					fragranceMiddleNoteRepository.save(
						FragranceMiddleNote.builder().fragrance(fragrance).note(note).build());
				} else {
					fragranceBaseNoteRepository.save(
						FragranceBaseNote.builder().fragrance(fragrance).note(note).build());
				}
			});
	}

	/**
	 * 장소 테이블과 향수-장소 관계 매핑 테이블 저장
	 */
	private void saveLocations(Fragrance fragrance, String locationStr) {
		Arrays.stream(locationStr.split(","))
			.map(String::trim)
			.filter(loc -> !loc.isEmpty())
			.distinct()
			.forEach(locName -> {
				Location location = locationRepository.findByName(locName)
					.orElseGet(() -> locationRepository.save(Location.builder().name(locName).build()));
				fragranceLocationRepository.save(
					FragranceLocation.builder().fragrance(fragrance).location(location).build()
				);
			});
	}

	/**
	 * 계절 테이블과 향수-계절 관계 매핑 테이블 저장
	 */
	private void saveSeasons(Fragrance fragrance, String seasonStr) {
		Arrays.stream(seasonStr.split(","))
			.map(String::trim)
			.filter(season -> !season.isEmpty())
			.distinct()
			.forEach(seasonName -> {
				Season season = seasonRepository.findByName(seasonName)
					.orElseGet(() -> seasonRepository.save(Season.builder().name(seasonName).build()));
				fragranceSeasonRepository.save(
					FragranceSeason.builder().fragrance(fragrance).season(season).build()
				);
			});
	}

	/**
	 * Enum 변환 유틸 메서드들
	 */
	/*
	MAISON MARGIELA , FREDERIC MALLE 는 엑셀 시트에 "_" 언더바 표시가 아닌
	띄어씌기로 되어있어서 Brand enum 타입에 맞게 변환
	* */
	private Brand convertToBrand(String brandStr) {
		return switch (brandStr.trim().toUpperCase()) {
			case "MAISON MARGIELA" -> Brand.MAISON_MARGIELA;
			case "FREDERIC MALLE" -> Brand.FREDERIC_MALLE;
			case "LOIVIE" -> Brand.LOIVIE;
			case "DIPTYQUE" -> Brand.DIPTYQUE;
			case "JOMALONE" -> Brand.JOMALONE;
			default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_BRAND);
		};
	}

	/*
	엑셀 시트에 남성용, 여성용, 남녀불문 이라고 저장되어 있기 때문에
	FragranceGender enum 타입에 맞게 변환
	* */
	private FragranceGender convertToFragranceGender(String genderStr) {
		return switch (genderStr) {
			case "남성용" -> FragranceGender.MALE;
			case "여성용" -> FragranceGender.FEMALE;
			default -> FragranceGender.NEUTRAL;
		};
	}

	/*
	향수 타입도 마찬가지로 FragranceType 에 맞게 변환
	* */
	private FragranceType convertType(String typeStr) {
		return switch (typeStr) {
			case "퍼퓸" -> FragranceType.PERFUME;
			case "오 드 퍼퓸" -> FragranceType.EAU_DE_PERFUME;
			case "오 드 뚜왈렛" -> FragranceType.EAU_DE_TOILETTE;
			case "오 드 코롱" -> FragranceType.EAU_DE_COLOGNE;
			case "샤워 코롱" -> FragranceType.SHOWER_COLOGNE;
			default -> throw new GeneralException(ErrorStatus.UNSUPPORTED_TYPE);
		};
	}
}
