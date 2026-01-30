package toyproject.workspot.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import toyproject.workspot.domain.AgeGroup;
import toyproject.workspot.domain.Gender;

@Getter
@Setter
public class UserForm {

    @NotEmpty(message = "이름 입력은 필수입니다.")
    private String name;
    private String nickName;

    private String job;
    private Gender gender;
    private AgeGroup ageGroup;
}
