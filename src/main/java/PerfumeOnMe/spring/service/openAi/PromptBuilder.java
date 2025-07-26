package PerfumeOnMe.spring.service.openAi;

import PerfumeOnMe.spring.web.dto.Pbti.PbtiRequestDTO;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;

public class PromptBuilder {

	public static String buildPromptFromRequest(PbtiRequestDTO.PbtiQuestionRequest request,
		PbtiResponseDTO.PbtiResult result) {

		String keywordString = String.join(", ",
			result.getKeyword1(),
			result.getKeyword2(),
			result.getKeyword3(),
			result.getKeyword4()
		);

		return String.format("""	
				아래는 사용자가 향수 성향 테스트에 응답한 결과입니다.:
				Q1: %s
				Q2: %s
				Q3: %s
				Q4: %s
				Q5: %s
				Q6: %s
				Q7: %s
				Q8: %s
				
				이 사용자는 다음과 같은 향수 성향 키워드를 가지고 있습니다:
				%s
				
				이 키워드 각각에 대해 향수 성향 기반 설명(keywordDescription)을 작성하세요. 각 키워드는 다음 JSON 형식의 "keywords" 필드에 배열로 포함되어야 합니다.
				또한 각 keywords 배열의 "keyword"는 위의 향수 성향 키워드에서 그대로 사용하세요. GPT가 임의로 바꾸지 마세요.
				
				추가로 각 질문에 대한 사용자의 답변을 아래 JSON 포맷에 맞게 분석하여 출력하세요. JSON 이외의 설명은 절대 하지 말고, JSON 데이터만 정확하게 출력해주세요.:
				
				- "recommendation"은 '당신은' 으로 시작되도록 하세요.
				- "perfumeStyle" 내 "notes" 배열의 "category"를 "scentPoint" 내의 "category"에서 사용해주세요.
				- "scentPoint" 배열 내의 "category"와 "perfumeStyle" 내 "notes" 배열 내의 "category"는 한국어로 응답해주세요.
				- "scentPoint" 배열 내의 "point"는 숫자가 큰 순서대로 출력해주세요.
				- "summary"는 사용자의 성격이 반영되는 단어가 들어가도록 간단하게 요약해주세요. ex) “사람들과의 에너지 흐름을 잘 이끌어내는 ~한 사람”
				- "keywords" 배열에는 4개 항목을 포함하세요.
				- "perfumeStyle" 내 "notes" 배열에는 5개 항목을 포함하세요.
				- "scentPoint" 배열에는 5개 항목을 포함하세요.
				{
				  "recommendation": "...",
				  "keywords": [
				    {
				      "keyword": "...",
				      "keywordDescription": "..."
				    }
				    // 4개 항목
				  ],
				  "perfumeStyle": {
				    "description": "...",
				    "notes": [
				      {
				        "category": "...",
				        "categoryDescription": "..."
				      }
				      // 5개 항목
				    ]
				  },
				  "scentPoint": [
				    {
				      "category": "...",
				      "point": 1~6 범위의 정수 중 하나
				    }
				    // 5개 항목
				  ],
				  "summary": "..."x`
				}
				""", request.getQOne(), request.getQTwo(), request.getQThree(), request.getQFour(),
			request.getQFive(), request.getQSix(), request.getQSeven(), request.getQEight(), keywordString);
	}
}
