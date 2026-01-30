package toyproject.workspot.controller.searchdto;

import lombok.Getter;
import lombok.Setter;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.infrastructure.kakao.dto.KakaoSearchResponse;

@Getter
@Setter
public class SpotSearchDtoKakao {

    private String id;
    private String place_name;
    private String category_name;
    private String category_group_code; //중요 카테고리만 그룹핑한 카테고리 그룹 코드
    private String category_group_name; //중요 카테고리만 그룹핑한 카테고리 그룹명
    private String phone;
    private String road_address_name;
    private String x;
    private String y;

    public SpotSearchDtoKakao(String id, String place_name, String category_name, String category_group_code, String category_group_name, String phone, String road_address_name, String x, String y) {
        this.id = id;
        this.place_name = place_name;
        this.category_name = category_name;
        this.category_group_code = category_group_code;
        this.category_group_name = category_group_name;
        this.phone = phone;
        this.road_address_name = road_address_name;
        this.x = x;
        this.y = y;
    }

    public Spot toEntity() {
        Double lat = Double.parseDouble(this.x);
        Double lng = Double.parseDouble(this.y);

        return new Spot(
                this.place_name,
                this.id,
                this.road_address_name,
                this.category_name,
                this.category_group_code,
                this.category_group_name,
                this.phone,
                lat,
                lng
        );
    }

    public static SpotSearchDtoKakao from(KakaoSearchResponse.Document document) {
        String cleanTitle = document.getPlaceName().replaceAll("<[^>]*>", "");

        return new SpotSearchDtoKakao(
                document.getId(),
                cleanTitle,
                document.getCategoryName(),
                document.getCategoryGroupCode(),
                document.getCategoryGroupName(),
                document.getPhone(),
                document.getRoadAddressName(),
                document.getX(),
                document.getY()
        );
    }
}
