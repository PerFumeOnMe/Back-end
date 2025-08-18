package PerfumeOnMe.spring.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.chatbot.domain.ChatMessage;
import PerfumeOnMe.spring.common.base.BaseEntity;
import PerfumeOnMe.spring.common.enums.Age;
import PerfumeOnMe.spring.common.enums.Social;
import PerfumeOnMe.spring.common.enums.UserGender;
import PerfumeOnMe.spring.diary.domain.Diary;
import PerfumeOnMe.spring.imagekeyword.domain.ImageKeyword;
import PerfumeOnMe.spring.pbti.domain.PBTI;
import PerfumeOnMe.spring.user.domain.mapping.UserFragrance;
import PerfumeOnMe.spring.user.domain.mapping.UserNote;
import PerfumeOnMe.spring.user.web.dto.UserRequestDTO;
import PerfumeOnMe.spring.workshop.domain.Workshop;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "users")
public class User extends BaseEntity {

	// ----- 필드 -----
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, length = 10)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(10) DEFAULT 'NONE'")
	private Age age;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(10) DEFAULT 'NONE'")
	private UserGender gender;

	@Column(nullable = false, unique = true)
	private String loginId;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(10) DEFAULT 'LOCAL'")
	private Social social;

	@Column(columnDefinition = "TEXT")
	private String imageURL;

	// ----- 매핑 -----
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<UserFragrance> userFragranceList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Diary> diaryList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<ChatMessage> chatMessageList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<UserNote> userNoteList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<PBTI> pbtiList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<ImageKeyword> imageKeywordList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Workshop> workshopList = new ArrayList<>();

	public void onboarding(UserRequestDTO.Onboarding request) {
		this.nickname = request.getNickname();
		this.imageURL = request.getImageURL();
		this.gender = UserGender.valueOf(request.getGender().toUpperCase());
		this.age = Age.valueOf(request.getAge().toUpperCase());
	}

	// 연관관계 편의 메서드
	public void addUserNote(UserNote userNote) {
		this.userNoteList.add(userNote);
		userNote.setUser(this);
	}

	// 프로필 사진 변경 메서드
	public void updateImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
