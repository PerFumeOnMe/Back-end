package PerfumeOnMe.spring.service.Diary;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.converter.DiaryConverter;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.mapping.Diary;
import PerfumeOnMe.spring.repository.diary.DiaryRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.web.dto.diary.DiaryRequestDTO;
import PerfumeOnMe.spring.web.dto.diary.DiaryResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

	private final DiaryRepository diaryRepository;
	private final UserRepository userRepository;

	// 다이어리 추가 API
	@Override
	public DiaryResponseDTO.AddDiaryResponse addDiary(Long userId, DiaryRequestDTO.AddDiaryRequest addDiaryRequest) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		Diary diary = Diary.builder()
			.user(user)
			.fragranceName(addDiaryRequest.getFragranceName())
			.content(addDiaryRequest.getContent())
			.date(addDiaryRequest.getDate())
			.build();

		diaryRepository.save(diary);
		return DiaryConverter.addDiaryResponseDTO(diary);
	}

	// 다이어리 수정 API
	@Override
	public void updateDiary(Long userId, Long diaryId, DiaryRequestDTO.UpdateDiaryRequest updateDiaryRequest) {
		// 다이어리 존재 여부 확인
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.DIARY_NOT_FOUND));

		// 다이어리 소유자 확인
		if (!diary.getUser().getId().equals(userId)) {
			throw new GeneralException(ErrorStatus.USER_DIARY_FORBIDDEN);
		}

		diary.updateFragranceNameAndContent(updateDiaryRequest.getFragranceName(), updateDiaryRequest.getContent());

		// 다이어리 저장
		diaryRepository.save(diary);
	}

	// 다이어리 삭제 API
	@Override
	public void deleteDiary(Long userId, Long diaryId) {
		// 다이어리 존재 여부 확인
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.DIARY_NOT_FOUND));

		// 다이어리 소유자 확인
		if (!diary.getUser().getId().equals(userId)) {
			throw new GeneralException(ErrorStatus.USER_DIARY_FORBIDDEN);
		}

		diaryRepository.delete(diary);
	}
}
