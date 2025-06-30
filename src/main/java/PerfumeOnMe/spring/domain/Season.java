package PerfumeOnMe.spring.domain;

import PerfumeOnMe.spring.domain.base.BaseEntity;
import PerfumeOnMe.spring.domain.mapping.FragranceSeason;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Season extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 15)
    private String name;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<FragranceSeason> fragranceSeasonList = new ArrayList<>();
}
