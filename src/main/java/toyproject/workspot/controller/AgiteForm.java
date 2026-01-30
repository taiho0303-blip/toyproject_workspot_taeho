package toyproject.workspot.controller;

import lombok.Getter;
import lombok.Setter;
import toyproject.workspot.domain.HasOutlet;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.domain.User;

@Getter
@Setter
public class AgiteForm {

    private String customName;

    private Long userId;

    private Long spotId;

    private HasOutlet hasOutlet;
    private String memo;
}
