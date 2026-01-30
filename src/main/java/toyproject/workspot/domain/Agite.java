package toyproject.workspot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Agite {

    @Id
    @GeneratedValue
    @Column(name = "agite_id")
    private Long id;

    private String customName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @Enumerated(EnumType.STRING)
    private HasOutlet hasOutlet; //콘센트 유무

    private String memo;
}
