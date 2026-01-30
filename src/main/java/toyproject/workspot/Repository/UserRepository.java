package toyproject.workspot.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.workspot.domain.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long userId) {
        return em.find(User.class, userId);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByNickName(String nickName) {
        return em.createQuery("select u from User u where u.nickName=:nickName", User.class)
                .setParameter("nickName", nickName)
                .getResultList();
    }
}
