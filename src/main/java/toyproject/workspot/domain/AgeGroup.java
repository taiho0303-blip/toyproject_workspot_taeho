package toyproject.workspot.domain;

public enum AgeGroup {
    TEENS("10대", 10, 19),
    TWENTIES("20대", 20, 29),
    THIRTIES("30대", 30, 39),
    FORTIES_PLUS("40대 이상", 40, 100);

    private final String label;  // 화면 출력용
    private final int startAge;  // 계산 로직용
    private final int endAge;

    AgeGroup(String label, int startAge, int endAge) {
        this.label = label;
        this.startAge = startAge;
        this.endAge = endAge;
    }
}
