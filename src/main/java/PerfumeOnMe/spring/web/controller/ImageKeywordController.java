package PerfumeOnMe.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.service.imagekeyword.ImageKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image-keyword")
@Tag(name = "Image-Keyword", description = "이미지키워드 API")
public class ImageKeywordController {
	private final ImageKeywordService imageKeywordService;

	// 이미지키워드 목록 조회 API
	@GetMapping("/result/list")
	@Operation(
		summary = "이미지키워드 목록 조회(마이페이지)",
		description = "사용자가 저장한 이미지키워드 목록을 조회하는 API입니다.",
		// responses = {}
	)

}
