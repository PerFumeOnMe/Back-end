package PerfumeOnMe.spring.domain.mapping;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.ImageKeyword;
import PerfumeOnMe.spring.domain.PBTI;
import PerfumeOnMe.spring.domain.Workshop;
import PerfumeOnMe.spring.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.jdbc.Work;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class RecommendedFragrance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fragrance_id")
    private Fragrance fragrance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageKeyword_id")
    private ImageKeyword imageKeyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pbti_id")
    private PBTI pbti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

}
