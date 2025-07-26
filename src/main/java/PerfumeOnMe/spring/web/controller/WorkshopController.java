package PerfumeOnMe.spring.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	/** 향수공방 결과 상세조회*/
	@GetMapping("/{workshopId}")
	@Operation(
		summary = "향수공방 결과 상세조회",
		description = "저장된 향수공방 결과의 상세정보를 조회합니다. 키워드 요약, 인상 설명, 성향 분석, 추천 향수 목록을 포함합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = WorkshopResponseDTO.WorkshopDetailResponseDTO.class)
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON401",
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요."
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "WORKSHOP4001",
				description = "해당 ID의 향수공방 결과를 찾을 수 없습니다."
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "WORKSHOP4002",
				description = "해당 향수공방 결과에 접근할 권한이 없습니다."
			)
		}
	)
	public ResponseEntity<ApiResponse<WorkshopResponseDTO.WorkshopDetailResponseDTO>> getWorkshopDetail(
		@Parameter(description = "조회할 향수공방 결과 ID", required = true, example = "1")
		@PathVariable Long workshopId,
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService
			.findWorkshopById(workshopId,
				userDetails)));
	}

}
