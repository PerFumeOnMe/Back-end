package PerfumeOnMe.spring.converter;

import PerfumeOnMe.spring.domain.Note;
import PerfumeOnMe.spring.domain.mapping.UserNote;

public class UserNoteConverter {

	// 노트 + 사용자 = 사용자 노트 반환
	public static UserNote toUserNote(Note note) {
		return UserNote.builder()
			.note(note)
			.build();
	}
}
