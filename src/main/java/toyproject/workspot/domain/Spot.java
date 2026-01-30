package toyproject.workspot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spot {

    @Id
    @GeneratedValue
    @Column(name = "spot_id")
    private Long id;

    private String title;

    @Column(unique = true)
    private String uniqueId;

    private String address;
    private String roadAddress;

    private String category;

    private String categoryGroupCode;
    private String categoryGroupName;

    private String phone;


    private Double mapx; // x좌표
    private Double mapy; // y좌표

    public Spot() {
    }

    public Spot(String title, String uniqueId, String address, Double mapx, Double mapy, String category) {
        this.title = title;
        this.uniqueId = uniqueId;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
        this.category = category;
    }

    public Spot(String title, String uniqueId, String address,String category, String categoryGroupCode, String categoryGroupName, String phone, Double mapx, Double mapy) {
        this.title = title;
        this.uniqueId = uniqueId;
        this.address = address;
        this.category = category;
        this.categoryGroupCode = categoryGroupCode;
        this.categoryGroupName = categoryGroupName;
        this.phone = phone;
        this.mapx = mapx;
        this.mapy = mapy;
    }
}
