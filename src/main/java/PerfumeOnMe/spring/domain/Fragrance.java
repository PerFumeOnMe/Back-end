package PerfumeOnMe.spring.domain;

import PerfumeOnMe.spring.domain.base.BaseEntity;
import PerfumeOnMe.spring.domain.enums.*;
import PerfumeOnMe.spring.domain.mapping.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Fragrance extends BaseEntity {

    // ----- 필드 -----
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15)")
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
    private String ImageURL;

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

    //----- 매핑 관계 -----

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<UserFragrance> userFragranceList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<FragranceSeason> fragranceSeasonList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<FragranceLocation> fragranceLocationList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<FragranceKeyword> fragranceKeywordList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<FragrancePrice> fragrancePriceList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<FragranceTopNote> fragranceTopNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<FragranceMiddleNote> fragranceMiddleNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<FragranceBaseNote> fragranceBaseNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "fragrance", cascade = CascadeType.ALL)
    private List<RecommendedFragrance> RecommendedFragranceList = new ArrayList<>();

}
