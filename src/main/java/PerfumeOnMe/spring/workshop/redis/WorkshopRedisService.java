package PerfumeOnMe.spring.workshop.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.workshop.web.dto.WorkshopResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 향수공방 미리보기 결과를 Redis에 임시 저장하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkshopRedisService {

	private static final Duration TTL = Duration.ofMinutes(15); // 15분 TTL
	private final StringRedisTemplate redisTemplate;
	private final ObjectMapper objectMapper;

	/**
	 * 향수공방 미리보기 결과를 Redis에 저장
	 */
	public void savePreview(Long userId, WorkshopResponseDTO.WorkshopPreviewResponseDTO dto) {
		try {
			String key = buildKey(userId);
			String json = objectMapper.writeValueAsString(dto);
			redisTemplate.opsForValue().set(key, json, TTL);
			log.info("향수공방 미리보기 결과 Redis 저장 완료 - 사용자 ID: {}, 키: {}", userId, key);
		} catch (Exception e) {
			log.error("향수공방 미리보기 결과 Redis 저장 실패 - 사용자 ID: {}, 오류: {}", userId, e.getMessage(), e);
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	/**
	 * Redis에서 향수공방 미리보기 결과를 조회
	 */
	public WorkshopResponseDTO.WorkshopPreviewResponseDTO getPreview(Long userId) {
		try {
			String key = buildKey(userId);
			String json = redisTemplate.opsForValue().get(key);
			if (json == null) {
				log.warn("향수공방 미리보기 결과가 만료되었거나 존재하지 않음 - 사용자 ID: {}", userId);
				throw new GeneralException(ErrorStatus.EXPIRED_WORKSHOP_RESULT);
			}
			log.info("향수공방 미리보기 결과 Redis 조회 완료 - 사용자 ID: {}", userId);
			return objectMapper.readValue(json, WorkshopResponseDTO.WorkshopPreviewResponseDTO.class);
		} catch (GeneralException e) {
			throw e;
		} catch (Exception e) {
			log.error("향수공방 미리보기 결과 Redis 조회 실패 - 사용자 ID: {}, 오류: {}", userId, e.getMessage(), e);
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	/**
	 * Redis에서 향수공방 미리보기 결과를 삭제
	 */
	public void deletePreview(Long userId) {
		String key = buildKey(userId);
		redisTemplate.delete(key);
		log.info("향수공방 미리보기 결과 Redis 삭제 완료 - 사용자 ID: {}", userId);
	}

	/**
	 * Redis 키 생성
	 */
	private String buildKey(Long userId) {
		return "workshop:preview:" + userId;
	}
}