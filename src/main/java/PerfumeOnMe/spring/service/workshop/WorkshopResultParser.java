package PerfumeOnMe.spring.service.workshop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * GPT 응답을 파싱하여 WorkshopResult 객체로 변환하는 파서 클래스
 */
@Slf4j
public class WorkshopResultParser {

	/**
	 * GPT 응답 텍스트를 파싱하여 WorkshopResult 객체로 변환
	 *
	 * 예상 GPT 응답 형식:
	 * 시각적 키워드: #상큼한첫인상 #감성적중심 #우디잔향
	 * #깊이있는사람 #신뢰감있는향기
	 * 향기의 첫인상: 베르가못의 상쾌한 시트러스 향이 첫 만남을 장식합니다. 이런 향을 선택하는 당신은 활기차고 긍정적인 에너지를 가진 사람으로 보입니다.
	 * 중심을 잡는 향: 장미의 우아한 플로럴 향이 중심을 잡으며 로맨틱함을 연출합니다. 이런 향을 좋아하는 당신은 섬세하고 감성적인 면을 가진 사람입니다.
	 * 마지막에 남는 잔향: 샌달우드의 깊고 따뜻한 잔향이 오래도록 머물며 안정감을 줍니다. 이런 향을 선택하는 당신은 차분하고 신뢰할 수 있는 성격의 소유자입니다.
	 * 향기로 해석한 당신의 성향: 복합적이고 다층적인 매력을 가진 당신은 첫인상은 밝고 활기차지만, 깊이 알수록 더욱 매력적인 면을 발견하게 됩니다.
	 * 기억되는 모습: 당신은 사람들에게 '기분 좋은 여운이 오래 남는 사람'으로 기억됩니다.
	 */
	public WorkshopResult parseGptResponse(String gptResponse) {
		try {
			log.info("GPT 응답 파싱 시작" + gptResponse);

			String keywordSummary = extractValue(gptResponse, "시각적 키워드:");
			String firstImpression = extractValue(gptResponse, "향기의 첫인상:");
			String centerImpression = extractValue(gptResponse, "중심을 잡는 향:");
			String lastImpression = extractValue(gptResponse, "마지막에 남는 잔향:");
			String tendency = extractValue(gptResponse, "향기로 해석한 당신의 성향:");
			String remembered = extractValue(gptResponse, "기억되는 모습:");

			WorkshopResult result = WorkshopResult.builder()
				.keywordSummary(keywordSummary)
				.firstImpression(firstImpression)
				.centerImpression(centerImpression)
				.lastImpression(lastImpression)
				.tendency(tendency)
				.remembered(remembered)
				.build();

			log.info("GPT 응답 파싱 완료 - 시각적 키워드: {}", keywordSummary);
			return result;

		} catch (Exception e) {
			log.error("GPT 응답 파싱 중 오류 발생: {}", e.getMessage(), e);
			log.error("원본 GPT 응답: {}", gptResponse);

			// 파싱 실패 시 기본값 반환
			return WorkshopResult.builder()
				.keywordSummary("#맞춤형향수 #개성있는조합 #특별한향기\n#매력적인사람 #기억에남는향")
				.firstImpression("선택한 탑 노트가 상쾌한 첫인상을 선사합니다. 당신은 활기찬 에너지를 가진 사람으로 보입니다.")
				.centerImpression("미들 노트가 조화로운 중심을 잡아줍니다. 당신은 균형감 있는 성격의 소유자입니다.")
				.lastImpression("베이스 노트가 깊이 있는 잔향을 남깁니다. 당신은 신뢰할 수 있는 매력을 가진 사람입니다.")
				.tendency("개성 있는 향을 추구하는 당신은 자신만의 스타일을 가진 사람입니다.")
				.remembered("당신은 사람들에게 '특별한 매력을 가진 사람'으로 기억됩니다.")
				.build();
		}
	}

	/**
	 * 특정 키워드 뒤의 값을 추출하는 헬퍼 메서드
	 */
	private String extractValue(String text, String keyword) {
		try {
			// 키워드 뒤에 오는 내용을 다음 키워드나 문서 끝까지 추출
			String regex = keyword
				+ "\\s*([^\\n]*(?:\\n(?!시각적 키워드:|향기의 첫인상:|중심을 잡는 향:|마지막에 남는 잔향:|향기로 해석한 당신의 성향:|기억되는 모습:)[^\\n]*)*)";
			Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
			Matcher matcher = pattern.matcher(text);

			if (matcher.find()) {
				String value = matcher.group(1).trim();
				log.debug("추출된 값 - {}: {}", keyword, value);
				return value;
			}

			log.warn("키워드 '{}'에 대한 값을 찾을 수 없습니다.", keyword);
			return "정보를 찾을 수 없습니다.";

		} catch (Exception e) {
			log.error("값 추출 중 오류 발생 - 키워드: {}, 오류: {}", keyword, e.getMessage());
			return "정보를 찾을 수 없습니다.";
		}
	}
}