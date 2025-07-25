package PerfumeOnMe.spring.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.workshop.WorkshopService;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workshop")
@Tag(name = "Workshop", description = "향수공방 API")
public class WorkshopController {

	private final WorkshopService workshopService;

	/** 향수공방 목록 조회*/
	@GetMapping("/result/list")
	@Operation(
		summary = "향수공방 목록 조회 (마이페이지)",
		description = "해당 유저가 저장한 향수공방 결과 목록을 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = WorkshopResponseDTO.WorkshopListResponseDTO.class)
				)
			)
		}
	)
	public ResponseEntity<ApiResponse<List<WorkshopResponseDTO.WorkshopListResponseDTO>>> getWorkshopList(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService
			.findAllWorkshopsByUser(
				userDetails)));
	}

}
