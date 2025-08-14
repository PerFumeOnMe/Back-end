package PerfumeOnMe.spring.imagekeyword.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.common.base.BaseEntity;
import PerfumeOnMe.spring.common.enums.Ambience;
import PerfumeOnMe.spring.common.enums.Gender;
import PerfumeOnMe.spring.common.enums.Personality;
import PerfumeOnMe.spring.common.enums.Season;
import PerfumeOnMe.spring.common.enums.Style;
import PerfumeOnMe.spring.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "image_keywords")
public class ImageKeyword extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(length = 50, nullable = false)
	private String savedName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Ambience ambience;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Style style;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Season season;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Personality personality;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String imageUrl;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String scenario;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String keywordDescription;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String recommendedFragranceJson;

}
