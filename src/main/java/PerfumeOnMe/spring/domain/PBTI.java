package PerfumeOnMe.spring.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.domain.base.BaseEntity;
import PerfumeOnMe.spring.domain.mapping.RecommendedFragrance;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "pbtis")
public class PBTI extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false, length = 50)
	private String savedName;

	@Column(nullable = false, length = 150)
	private String qOne;

	@Column(nullable = false, length = 150)
	private String qTwo;

	@Column(nullable = false, length = 150)
	private String qThree;

	@Column(nullable = false, length = 150)
	private String qFour;

	@Column(nullable = false, length = 150)
	private String qFive;

	@Column(nullable = false, length = 150)
	private String qSix;

	@Column(nullable = false, length = 150)
	private String qSeven;

	@Column(nullable = false, length = 150)
	private String qEight;

	@Column(columnDefinition = "text", nullable = false)
	private String recommendation;

	@Column(columnDefinition = "json", nullable = false)
	private String keywords;

	@Column(columnDefinition = "json", nullable = false)
	private String perfumeStyle;

	@Column(columnDefinition = "json", nullable = false)
	private String scentPoint;

	@Column(columnDefinition = "text", nullable = false)
	private String summary;

	@Column(columnDefinition = "json", nullable = false)
	private String perfumeRecommend;

	@OneToMany(mappedBy = "pbti", cascade = CascadeType.ALL)
	@Builder.Default
	private List<RecommendedFragrance> RecommendedFragranceList = new ArrayList<>();
}
