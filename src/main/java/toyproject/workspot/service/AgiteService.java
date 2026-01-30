package toyproject.workspot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.workspot.Repository.AgiteRepository;
import toyproject.workspot.domain.Agite;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgiteService {

    private final AgiteRepository agiteRepository;

    // 아지트 등록 로직
    @Transactional
    public void register(Agite agite) {
        if (!agiteRepository.existsByUserIdAndSpotId(agite.getUser().getId(), agite.getSpot().getId())) {
            agiteRepository.save(agite);
        }
    }

    @Transactional
    public void cancel(Agite agite) {
        agiteRepository.deleteByUserIdAndSpotId(agite.getUser().getId(), agite.getSpot().getId());
    }

    public boolean existsAgite(Long userId, Long spotId) {
        return agiteRepository.existsByUserIdAndSpotId(userId, spotId);
    }

    public List<Agite> findAllByUserId(Long userId) {
        return agiteRepository.findAllByUserId(userId);
    }
}
