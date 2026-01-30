package toyproject.workspot.controller.searchdto;

import lombok.Getter;
import lombok.Setter;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.infrastructure.naver.dto.NaverSearchResponse;

@Getter
@Setter
public class SpotSearchDto {

    private String title;
    private String address;        // 지번 주소
    private String roadAddress;    // 도로명 주소
    private String naverPlaceId;   // 중복 저장 방지용 고유 키 (link나 mapx/y 조합)
    private String category;     // 카테고리
    private String mapx;           // X 좌표
    private String mapy;           // Y 좌표

    public SpotSearchDto(String title, String address, String roadAddress, String naverPlaceId, String category, String mapx, String mapy) {
        this.title = title;
        this.address = address;
        this.roadAddress = roadAddress;
        this.naverPlaceId = naverPlaceId;
        this.category = category;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public Spot toEntity() {
        // 1. 문자열로 된 좌표를 숫자(Double)로 변환
        Double lat = Double.parseDouble(this.mapy);
        Double lng = Double.parseDouble(this.mapx);

        // 2. 생성자를 호출하여 Spot 객체 생성 후 반환
        return new Spot(
                this.title,
                this.naverPlaceId,
                this.address,
                lat,
                lng,
                category
        );
    }
    public static SpotSearchDto from(NaverSearchResponse.Item item) {
        // 1. 태그 제거 및 ID 생성 처리
        String cleanTitle = item.getTitle().replaceAll("<[^>]*>", "");
        String placeId = String.valueOf(item.getLink().hashCode());

        // 2. 생성자를 통해 객체 생성 및 반환
        return new SpotSearchDto(
                cleanTitle,
                item.getAddress(),
                item.getRoadAddress(),
                placeId,
                item.getCategory(),
                item.getMapx(),
                item.getMapy()
        );
    }
}
