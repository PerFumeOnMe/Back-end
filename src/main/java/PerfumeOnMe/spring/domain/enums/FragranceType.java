package PerfumeOnMe.spring.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum FragranceType {

    PERFUME("6~8시간", "매우 강함"),
    EAU_DE_PERFUME("4~6시간", "강함"),
    EAU_DE_TOILETTE( "2~4시간", "보통"),
    EAU_DE_COLOGNE("1~2시간", "부드러움"),
    SHOWER_COLOGNE( "0.5~1시간", "매우 부드러움");

    private final String lastingPower;     // 지속 시간
    private final String diffusionPower;   // 확산력 설명

}

