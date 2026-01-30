package toyproject.workspot.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.workspot.domain.Review;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository{

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public Review findOne(Long reviewId) {
        return em.find(Review.class, reviewId);
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class).getResultList();
    }

    public List<Review> findAllByUserId(Long userId) {
        return em.createQuery("select r from Review r where r.user.id= :userId", Review.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Review> findAllBySpotId(Long spotId) {
        return em.createQuery("select r from Review r where r.spot.id=: spotId", Review.class)
                .setParameter("spotId", spotId)
                .getResultList();
    }

    public void deleteReviewById(Long reviewId) {
        em.createQuery("delete from Review r where r.id = :reviewId")
                .setParameter("reviewId", reviewId)
                .executeUpdate();
    }
}
