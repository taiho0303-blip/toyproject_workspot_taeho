package toyproject.workspot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.workspot.Repository.UserRepository;
import toyproject.workspot.domain.User;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용 디폴트, 데이터 변경 메서드만 transactional 붙이기
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByNickName(String nickName) {
        return userRepository.findByNickName(nickName);
    }
}
