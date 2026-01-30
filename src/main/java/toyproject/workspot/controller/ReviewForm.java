package toyproject.workspot.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewForm {

    private String title;
    private Long userId;
    private Long spotId;
    private String content;
    private LocalDateTime createdAt;
}
