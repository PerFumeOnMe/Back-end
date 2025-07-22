package PerfumeOnMe.spring.service.pbti;

import PerfumeOnMe.spring.web.dto.Pbti.PbtiRequestDTO;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;

public class PbtiScoringUtil {

	public static PbtiResponseDTO.PbtiResult calculateMbtiType(PbtiRequestDTO.PbtiQuestionRequest request) {
		int eScore = 0, iScore = 0;
		int sScore = 0, nScore = 0;
		int tScore = 0, fScore = 0;
		int jScore = 0, pScore = 0;

		// Q1~Q2 → E/I
		if (request.getQOne().contains("칫솔을") || request.getQOne().contains("바로"))
			eScore++;
		else if (request.getQOne().contains("수건으로") || request.getQOne().contains("닦고"))
			iScore++;

		if (request.getQTwo().contains("버튼") || request.getQTwo().contains("분사해"))
			eScore++;
		else if (request.getQTwo().contains("중간에") || request.getQTwo().contains("내리는"))
			iScore++;

		// Q3~Q4 → S/N
		if (request.getQThree().contains("알림처럼") || request.getQThree().contains("미리"))
			sScore++;
		else if (request.getQThree().contains("버스가") || request.getQThree().contains("가방에서 꺼내"))
			nScore++;

		if (request.getQFour().contains("신호가 바뀌기 직전") || request.getQFour().contains("분사해"))
			sScore++;
		else if (request.getQFour().contains("기다리다") || request.getQFour().contains("향이 옅어지면"))
			nScore++;

		// Q5~Q6 → T/F
		if (request.getQFive().contains("공간") || request.getQFive().contains("퍼뜨린다"))
			tScore++;
		else if (request.getQFive().contains("기분") || request.getQFive().contains("헹굴 때마다"))
			fScore++;

		if (request.getQSix().contains("목줄") || request.getQSix().contains("가볍게"))
			tScore++;
		else if (request.getQSix().contains("손목에") || request.getQSix().contains("레이어링한다"))
			fScore++;

		// Q7~Q8 → J/P
		if (request.getQSeven().contains("리모컨을") || request.getQSeven().contains("채널을 돌리며"))
			jScore++;
		else if (request.getQSeven().contains("광고가") || request.getQSeven().contains("확실히"))
			pScore++;

		if (request.getQEight().contains("이불 위에서") || request.getQEight().contains("잔향을"))
			jScore++;
		else if (request.getQEight().contains("중앙에서") || request.getQEight().contains("톡톡"))
			pScore++;

		// 키워드 결정
		String keyword1 = (eScore > iScore) ? "긍정적 임팩트를 가진 당신"
			: (eScore < iScore) ? "은은한 집중형인 당신"
			: "외향과 내향의 균형을 지닌 당신";
		String keyword2 = (sScore > nScore) ? "촉각에 민감한 당신"
			: (sScore < nScore) ? "직관으로 이끄는 당신"
			: "감각과 직관을 오가는 당신";
		String keyword3 = (tScore > fScore) ? "세부까지 놓치지 않는 당신"
			: (tScore < fScore) ? "감성을 우선하는 당신"
			: "사고와 감정을 조화시키는 당신";
		String keyword4 = (jScore > pScore) ? "미리 움직이는 당신"
			: (jScore < pScore) ? "순간을 즐기는 당신"
			: "계획과 즉흥이 공존하는 당신";

		return new PbtiResponseDTO.PbtiResult(keyword1, keyword2, keyword3, keyword4);
	}
}
