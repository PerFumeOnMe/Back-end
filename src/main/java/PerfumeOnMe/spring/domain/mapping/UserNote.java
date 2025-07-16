package PerfumeOnMe.spring.domain.mapping;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.domain.Note;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "user_notes")
public class UserNote extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "note_id")
	private Note note;

	// 연관관계 편의 메서드
	public void setUser(User user) {
		if (this.user != null) {
			user.getUserNoteList().remove(this);
		}
		this.user = user;
		user.getUserNoteList().add(this);
	}

	public void setNote(Note note) {
		if (this.note != null) {
			note.getUserNoteList().remove(this);
		}
		this.note = note;
		note.getUserNoteList().add(this);
	}
}
