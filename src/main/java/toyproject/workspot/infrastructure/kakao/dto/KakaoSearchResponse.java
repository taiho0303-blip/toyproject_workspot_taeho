package toyproject.workspot.infrastructure.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class KakaoSearchResponse {

    @JsonProperty("documents")
    private List<Document> documents;

    @Getter
    @Setter
    public static class Document {
        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("road_address_name")
        private String roadAddressName;

        @JsonProperty("category_name")
        private String categoryName;

        @JsonProperty("category_group_code")
        private String categoryGroupCode;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("x")
        private String x;
        @JsonProperty("y")
        private String y;
        @JsonProperty("id")
        private String id;
    }
}
