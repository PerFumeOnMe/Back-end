package PerfumeOnMe.spring.domain;

import PerfumeOnMe.spring.domain.base.BaseEntity;
import PerfumeOnMe.spring.domain.mapping.FragranceBaseNote;
import PerfumeOnMe.spring.domain.mapping.FragranceMiddleNote;
import PerfumeOnMe.spring.domain.mapping.FragranceTopNote;
import PerfumeOnMe.spring.domain.mapping.UserNote;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private List<FragranceTopNote> fragranceTopNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<FragranceMiddleNote> fragranceMiddleNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<FragranceBaseNote> fragranceBaseNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<UserNote> userNoteList = new ArrayList<>();
}
