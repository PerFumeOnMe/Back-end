package PerfumeOnMe.spring.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import PerfumeOnMe.spring.domain.base.BaseEntity;
import PerfumeOnMe.spring.domain.mapping.FragranceBaseNote;
import PerfumeOnMe.spring.domain.mapping.FragranceMiddleNote;
import PerfumeOnMe.spring.domain.mapping.FragranceTopNote;
import PerfumeOnMe.spring.domain.mapping.UserNote;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "notes")
public class Note extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 40)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private boolean top;

	@Column(nullable = false)
	private boolean middle;

	@Column(nullable = false)
	private boolean base;

	@OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceTopNote> fragranceTopNoteList = new ArrayList<>();

	@OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceMiddleNote> fragranceMiddleNoteList = new ArrayList<>();

	@OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
	@Builder.Default
	private List<FragranceBaseNote> fragranceBaseNoteList = new ArrayList<>();

	@OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
	@Builder.Default
	private List<UserNote> userNoteList = new ArrayList<>();
}
