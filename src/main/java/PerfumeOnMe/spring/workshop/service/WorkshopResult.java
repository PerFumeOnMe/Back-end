package PerfumeOnMe.spring.workshop.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * GPT 응답으로부터 파싱된 향수공방 결과를 담는 클래스
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkshopResult {

	private String keywordSummary;      // 시각적 키워드 (해시태그 형태)
	private String firstImpression;     // 향기의 첫인상 (탑 노트 설명 + 사용자 성향)
	private String centerImpression;    // 중심을 잡는 향 (미들 노트 설명 + 사용자 성향)
	private String lastImpression;      // 마지막에 남는 잔향 (베이스 노트 설명 + 사용자 성향)
	private String tendency;            // 향기로 해석한 당신의 성향 (성향 분석 부분)
	private String remembered;          // 기억되는 모습 (당신은 사람들에게 ~으로 기억됩니다)
}