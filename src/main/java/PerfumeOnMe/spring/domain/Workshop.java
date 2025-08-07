package PerfumeOnMe.spring.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.domain.base.BaseEntity;
import jakarta.persistence.Column;
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
@Table(name = "workshops")
public class Workshop extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(length = 50, nullable = false)
	private String savedName;

	@Column(length = 40, nullable = false)
	private String baseNote;

	@Column(nullable = false)
	private Long baseNoteVolume;

	@Column(length = 40, nullable = false)
	private String middleNote;

	@Column(nullable = false)
	private Long middleNoteVolume;

	@Column(length = 40, nullable = false)
	private String topNote;

	@Column(nullable = false)
	private Long topNoteVolume;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String keywordSummary;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String firstImpression;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String centerImpression;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String lastImpression;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String tendency;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String remembered;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String recommendedFragranceJson;
}
