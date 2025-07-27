package PerfumeOnMe.spring.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.workshop.WorkshopService;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopRequestDTO;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workshop")
@Tag(name = "Workshop", description = "향수공방 API")
public class WorkshopController {

	private final WorkshopService workshopService;

	/** 향수공방 결과 미리보기(결과 생성)*/
	@PostMapping("/preview")
	@Operation(
		summary = "향수공방 결과 확인(미리보기)",
		description = "사용자가 선택한 향(Top, Middle, Base 노트)과 용량을 바탕으로 향기 해석 결과를 미리 확인합니다. " +
			"결과는 Redis에 15분간 임시 저장되며, 향수공방 저장 API 호출 시 활용됩니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = WorkshopResponseDTO.WorkshopPreviewResponseDTO.class)
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON401",
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요."
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "WORKSHOP4002",
				description = "선택한 노트들의 총 용량은 10을 초과할 수 없습니다."
			)
		}
	)
	public ResponseEntity<ApiResponse<WorkshopResponseDTO.WorkshopPreviewResponseDTO>> getWorkshopPreview(
		@Parameter(description = "향수공방 미리보기 생성 요청", required = true)
		@RequestBody @Valid WorkshopRequestDTO.WorkshopPreviewRequestDTO request,
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService.
			createWorkshopPreview(request, userDetails)));
	}

	/** 향수공방 결과 저장 */
	@PostMapping("/save")
	@Operation(
		summary = "향수공방 결과 저장",
		description = "사용자가 미리보기에서 확인한 향수공방 결과를 지정한 이름으로 데이터베이스에 영구 저장합니다. " +
			"Redis에 임시 저장된 미리보기 데이터를 사용하므로, 미리보기 생성 후 15분 이내에 호출해야 합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = WorkshopResponseDTO.WorkshopSaveResponseDTO.class)
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON401",
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요."
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "WORKSHOP4003",
				description = "향수공방 미리보기 결과가 만료되었습니다. 다시 시도해주세요."
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "WORKSHOP4006",
				description = "이미 같은 이름으로 저장된 향수공방 결과가 있습니다."
			)
		}
	)
	public ResponseEntity<ApiResponse<WorkshopResponseDTO.WorkshopSaveResponseDTO>> saveWorkshopResult(
		@Parameter(description = "향수공방 저장 요청", required = true)
		@RequestBody @Valid WorkshopRequestDTO.WorkshopSaveRequestDTO request,
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService.
			saveWorkshop(request, userDetails)));
	}

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
				responseCode = "WORKSHOP4004",
				description = "해당 향수공방 결과에 접근할 수 없습니다."
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "WORKSHOP4005",
				description = "해당 향수공방 결과 정보가 존재하지 않습니다."
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
