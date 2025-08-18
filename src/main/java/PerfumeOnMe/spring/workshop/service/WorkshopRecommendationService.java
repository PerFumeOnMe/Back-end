package PerfumeOnMe.spring.workshop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import PerfumeOnMe.spring.workshop.domain.WorkshopFragrance;
import PerfumeOnMe.spring.workshop.repository.WorkshopFragranceRepository;
import PerfumeOnMe.spring.workshop.web.dto.WorkshopRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkshopRecommendationService {

	// 점수 가중치 상수
	private static final double NOTE_WEIGHT = 0.4; // 노트 매칭 40%
	private static final double ACCORD_WEIGHT = 0.6; // 메인어코드 매칭 60%
	// 노트 매칭 점수
	private static final double PERFECT_NOTE_MATCH = 100.0; // 정확한 위치 매치
	private static final double DIFFERENT_POSITION_MATCH = 50.0; // 다른 위치 매치
	private static final double NO_MATCH_PENALTY = -10.0; // 매치 없음 페널티
	// 메인어코드 매칭 점수
	private static final double FIRST_ACCORD_MATCH = 100.0; // 1순위 매치
	private static final double SECOND_ACCORD_MATCH = 60.0; // 2순위 매치
	private static final double THIRD_ACCORD_MATCH = 30.0; // 3순위 매치
	private final WorkshopFragranceRepository workshopFragranceRepository;

	/**
	 * 사용자의 향수공방 선택을 기반으로 상위 3개 향수 추천
	 */
	public List<WorkshopFragrance> recommendFragrances(WorkshopRequestDTO.WorkshopCreateRequestDTO request) {
		log.info("향수 추천 시작 - 사용자 노트 선택: Top={}, Middle={}, Base={}",
			request.getTopNoteList(), request.getMiddleNoteList(), request.getBaseNoteList());

		// 모든 향수 조회
		List<WorkshopFragrance> allFragrances = workshopFragranceRepository.findAllForRecommendation();
		log.info("전체 향수 개수: {}", allFragrances.size());

		// 각 향수에 대해 점수 계산
		List<FragranceScore> fragranceScores = allFragrances.stream()
			.map(fragrance -> calculateScore(fragrance, request))
			.collect(Collectors.toList());

		// 점수 기준으로 정렬하고 상위 3개 선택
		List<WorkshopFragrance> recommendations = fragranceScores.stream()
			.sorted(Comparator.comparingDouble(FragranceScore::getScore).reversed())
			.limit(3)
			.map(FragranceScore::getFragrance)
			.collect(Collectors.toList());

		log.info("추천 완료 - 상위 3개 향수 선택됨");
		return recommendations;
	}

	/**
	 * 향수에 대한 종합 점수 계산
	 */
	private FragranceScore calculateScore(WorkshopFragrance fragrance,
		WorkshopRequestDTO.WorkshopCreateRequestDTO request) {
		double noteScore = calculateNoteMatchingScore(fragrance, request);
		double accordScore = calculateAccordMatchingScore(fragrance, request);

		double totalScore = (noteScore * NOTE_WEIGHT) + (accordScore * ACCORD_WEIGHT);

		return new FragranceScore(fragrance, totalScore);
	}

	/**
	 * 노트 매칭 점수 계산 (40% 가중치)
	 */
	private double calculateNoteMatchingScore(WorkshopFragrance fragrance,
		WorkshopRequestDTO.WorkshopCreateRequestDTO request) {
		double totalScore = 0.0;
		int totalWeight = 0;

		// 사용자 선택 노트와 용량 정보
		Map<String, Integer> userTopNotes = request.getTopNoteList();
		Map<String, Integer> userMiddleNotes = request.getMiddleNoteList();
		Map<String, Integer> userBaseNotes = request.getBaseNoteList();

		// Top 노트 매칭
		for (Map.Entry<String, Integer> entry : userTopNotes.entrySet()) {
			String noteName = entry.getKey();
			int volume = entry.getValue();

			double score = calculateSingleNoteScore(fragrance, noteName, "top");
			totalScore += score * volume;
			totalWeight += volume;
		}

		// Middle 노트 매칭
		for (Map.Entry<String, Integer> entry : userMiddleNotes.entrySet()) {
			String noteName = entry.getKey();
			int volume = entry.getValue();

			double score = calculateSingleNoteScore(fragrance, noteName, "middle");
			totalScore += score * volume;
			totalWeight += volume;
		}

		// Base 노트 매칭
		for (Map.Entry<String, Integer> entry : userBaseNotes.entrySet()) {
			String noteName = entry.getKey();
			int volume = entry.getValue();

			double score = calculateSingleNoteScore(fragrance, noteName, "base");
			totalScore += score * volume;
			totalWeight += volume;
		}

		return totalWeight > 0 ? totalScore / totalWeight : 0.0;
	}

	/**
	 * 개별 노트 점수 계산
	 */
	private double calculateSingleNoteScore(WorkshopFragrance fragrance, String noteName, String expectedPosition) {
		String topNote = fragrance.getTopNote() != null ? fragrance.getTopNote() : "";
		String middleNote = fragrance.getMiddleNote() != null ? fragrance.getMiddleNote() : "";
		String baseNote = fragrance.getBaseNote() != null ? fragrance.getBaseNote() : "";

		// 정확한 위치에서 매치
		switch (expectedPosition) {
			case "top":
				if (topNote.contains(noteName))
					return PERFECT_NOTE_MATCH;
				break;
			case "middle":
				if (middleNote.contains(noteName))
					return PERFECT_NOTE_MATCH;
				break;
			case "base":
				if (baseNote.contains(noteName))
					return PERFECT_NOTE_MATCH;
				break;
		}

		// 다른 위치에서 매치
		if (topNote.contains(noteName) || middleNote.contains(noteName) || baseNote.contains(noteName)) {
			return DIFFERENT_POSITION_MATCH;
		}

		// 매치 없음
		return NO_MATCH_PENALTY;
	}

	/**
	 * 메인어코드 매칭 점수 계산 (60% 가중치)
	 */
	private double calculateAccordMatchingScore(WorkshopFragrance fragrance,
		WorkshopRequestDTO.WorkshopCreateRequestDTO request) {
		// 사용자가 선택한 모든 노트 수집
		List<String> userSelectedNotes = new ArrayList<>();
		userSelectedNotes.addAll(request.getTopNoteList().keySet());
		userSelectedNotes.addAll(request.getMiddleNoteList().keySet());
		userSelectedNotes.addAll(request.getBaseNoteList().keySet());

		double totalScore = 0.0;
		int matchCount = 0;

		// 향수의 메인어코드들
		List<String> fragranceAccords = Arrays.asList(
			fragrance.getMainAccord1(),
			fragrance.getMainAccord2(),
			fragrance.getMainAccord3()
		);

		// 각 메인어코드에 대해 점수 계산
		for (int i = 0; i < fragranceAccords.size(); i++) {
			String accord = fragranceAccords.get(i);
			if (accord == null || accord.trim().isEmpty())
				continue;

			// 사용자 선택 노트와 매칭 확인
			boolean matched = userSelectedNotes.stream()
				.anyMatch(note -> accord.contains(note) || note.contains(accord));

			if (matched) {
				switch (i) {
					case 0: // 1순위
						totalScore += FIRST_ACCORD_MATCH;
						break;
					case 1: // 2순위
						totalScore += SECOND_ACCORD_MATCH;
						break;
					case 2: // 3순위
						totalScore += THIRD_ACCORD_MATCH;
						break;
				}
				matchCount++;
			}
		}

		return matchCount > 0 ? totalScore / matchCount : 0.0;
	}

	/**
	 * 향수와 점수를 함께 저장하는 내부 클래스
	 */
	private static class FragranceScore {
		private final WorkshopFragrance fragrance;
		private final double score;

		public FragranceScore(WorkshopFragrance fragrance, double score) {
			this.fragrance = fragrance;
			this.score = score;
		}

		public WorkshopFragrance getFragrance() {
			return fragrance;
		}

		public double getScore() {
			return score;
		}
	}
}