package PerfumeOnMe.spring.workshop.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "workshop_fragrances")
public class WorkshopFragrance extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name; // 향수이름

	@Column(nullable = false, length = 50)
	private String brand; // 브랜드

	@Column(length = 50)
	private String mainAccord1; // 메인어코드 1순위

	@Column(length = 50)
	private String mainAccord2; // 메인어코드 2순위

	@Column(length = 50)
	private String mainAccord3; // 메인어코드 3순위

	@Column(columnDefinition = "TEXT")
	private String topNote; // 탑노트

	@Column(columnDefinition = "TEXT")
	private String middleNote; // 미들노트

	@Column(columnDefinition = "TEXT")
	private String baseNote; // 베이스노트

	@Column(length = 500)
	private String imageUrl; // 향수이미지

	@Column(length = 500)
	private String removebgImageUrl; // 배경 제거된 향수이미지

	@Column(columnDefinition = "TEXT")
	private String description; // 향수설명

	@Column(nullable = false)
	private Integer price; // 가격
}