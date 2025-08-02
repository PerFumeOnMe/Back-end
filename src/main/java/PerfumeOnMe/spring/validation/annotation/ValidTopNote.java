package PerfumeOnMe.spring.validation.annotation;

public @interface ValidTopNote {
	String message() default "탑 노트는 베르가뭇, 레몬, 오렌지, 자몽, 사과, 페퍼민트 중 하나여야 합니다.";

	Class<?>[] groups() default {};

	Class<? extends java.lang.annotation.Annotation>[] payload() default {};
}
