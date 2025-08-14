package PerfumeOnMe.spring.fragrance.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.common.base.BaseEntity;
import PerfumeOnMe.spring.common.enums.Brand;
import PerfumeOnMe.spring.common.enums.FragranceGender;
import PerfumeOnMe.spring.common.enums.FragranceType;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceBaseNote;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceLocation;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceMiddleNote;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragrancePrice;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceSeason;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceTopNote;
import PerfumeOnMe.spring.fragrance.domain.mapping.RecommendedFragrance;
import PerfumeOnMe.spring.user.domain.mapping.UserFragrance;
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
@Table(name = "fragrances")
public class Fragrance extends BaseEntity {

	// ----- 필드 -----
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(50)")
	private Brand brand;

	@Column(nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(10)")
	private FragranceGender gender;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String homePageURL;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(30)", nullable = false)
	private FragranceType fragranceType;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String imageURL;

	@Column(nullable = false)
	private String topNoteDescription;

	@Column(nullable = false)
	private String middleNoteDescription;

	@Column(nullable = false)
	private String baseNoteDescription;

	@Column(nullable = false)
	private String topNoteKeyword;

	@Column(nullable = false)
	private String middleNoteKeyword;

	@Column(nullable = false)
	private String baseNoteKeyword;

	@Column(nullable = false, unique = true)
	private String keyword;

	//----- 매핑 관계 -----

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<UserFragrance> userFragranceList = new ArrayList<>();

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceSeason> fragranceSeasonList = new ArrayList<>();

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceLocation> fragranceLocationList = new ArrayList<>();

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragrancePrice> fragrancePriceList = new ArrayList<>();

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceTopNote> fragranceTopNoteList = new ArrayList<>();

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceMiddleNote> fragranceMiddleNoteList = new ArrayList<>();

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceBaseNote> fragranceBaseNoteList = new ArrayList<>();

	@OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
	@Builder.Default
	private List<RecommendedFragrance> RecommendedFragranceList = new ArrayList<>();

}
