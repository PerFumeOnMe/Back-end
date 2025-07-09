package PerfumeOnMe.spring.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.domain.base.BaseEntity;
import PerfumeOnMe.spring.domain.enums.Ambience;
import PerfumeOnMe.spring.domain.enums.Character;
import PerfumeOnMe.spring.domain.enums.Gender;
import PerfumeOnMe.spring.domain.enums.Season;
import PerfumeOnMe.spring.domain.enums.Style;
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
	private Character character;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String imageUrl;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String scenario;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String keywordDescription;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String recommendedFragranceJson;

}
