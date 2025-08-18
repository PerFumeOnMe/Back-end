package PerfumeOnMe.spring.imagekeyword.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.imagekeyword.web.dto.ImageKeywordResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageKeywordRedisService {

	private static final Duration TTL = Duration.ofMinutes(15);
	private final StringRedisTemplate redisTemplate;
	private final ObjectMapper objectMapper;

	public void savePreview(Long userId, ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO dto) {
		try {
			String key = buildKey(userId);
			String json = objectMapper.writeValueAsString(dto);
			redisTemplate.opsForValue().set(key, json, TTL);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	public ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO getPreview(Long userId) {
		try {
			String key = buildKey(userId);
			String json = redisTemplate.opsForValue().get(key);
			if (json == null) {
				throw new GeneralException(ErrorStatus.EXPIRED_IMAGEKEYWORD_RESULT);
			}
			return objectMapper.readValue(json, ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO.class);
		} catch (GeneralException e) {
			throw e;
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	public void deletePreview(Long userId) {
		redisTemplate.delete(buildKey(userId));
	}

	private String buildKey(Long userId) {
		return "image-keyword:preview:" + userId;
	}
}