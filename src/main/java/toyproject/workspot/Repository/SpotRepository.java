package toyproject.workspot.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.workspot.domain.Spot;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpotRepository {

    private final EntityManager em;

    public void save(Spot spot) {
        em.persist(spot);
    }

    public Spot findOne(Long spotId) {
        return em.find(Spot.class, spotId);
    }

    public List<Spot> findAll() {
        return em.createQuery("select s from Spot s", Spot.class).getResultList();
    }


    // uniqueId로 장소 찾기
    public Spot findByUniqueId(String uniqueId) {
        List<Spot> spots = em.createQuery("select s from Spot s where s.uniqueId=:uniqueId", Spot.class)
                .setParameter("uniqueId", uniqueId)
                .getResultList();

        if (spots.isEmpty()) {
            return null;
        }
        return spots.get(0);
    }
}
