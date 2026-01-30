package toyproject.workspot.infrastructure.naver.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverSearchResponse {
    // 응답으로 받은 메세지를 파싱하여 결과 하나하나를 item으로 만들고 item들을 List로 저장
    private List<Item> items;

    @Data
    public static class Item {
        private String title;
        private String link;
        private String category;
        private String address;
        private String roadAddress;
        private String mapx;
        private String mapy;
    }
}
