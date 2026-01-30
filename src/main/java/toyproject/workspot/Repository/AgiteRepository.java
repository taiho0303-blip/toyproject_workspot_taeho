package toyproject.workspot.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.workspot.domain.Agite;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AgiteRepository {

    private final EntityManager em;

    public void save(Agite agite) {
        em.persist(agite);
    }

    public Agite findOne(Long agiteId) {
        return em.find(Agite.class, agiteId);
    }

    public List<Agite> findAllByUserId(Long userId) {
        return em.createQuery("select a from Agite a where a.user.id= :userId", Agite.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 이미 등록된 아지트인지 확인하는 로직
    public boolean existsByUserIdAndSpotId(Long userId, Long spotId) {
        Integer result = em.createQuery(
                        "select 1 from Agite a where a.user.id = :userId AND a.spot.id = :spotId", Integer.class)
                .setParameter("userId", userId)
                .setParameter("spotId", spotId)
                .setMaxResults(1) // 하나라도 찾으면 중단하도록 설정
                .getResultList()
                .size();

        return result > 0;
    }

    public void deleteByUserIdAndSpotId(Long userId, Long spotId) {
        em.createQuery("delete from Agite a where a.user.id = :userId and a.spot.id = :spotId")
                .setParameter("userId", userId)
                .setParameter("spotId", spotId)
                .executeUpdate(); // DB에 즉시 삭제 쿼리를 날립니다.
    }
}
