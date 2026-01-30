package toyproject.workspot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.workspot.Repository.ReviewRepository;
import toyproject.workspot.controller.ReviewForm;
import toyproject.workspot.domain.Review;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.domain.User;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    //리뷰 등록, 삭제, 수정, 조회
    @Transactional
    public Long saveReview(ReviewForm form, User user, Spot spot) {
        Review review = toEntity(form, user, spot);
        reviewRepository.save(review);
        return review.getId();
    }

    private Review toEntity(ReviewForm form,User user, Spot spot) {
        Review review = new Review();
        review.setTitle(form.getTitle());
        review.setUser(user);
        review.setSpot(spot);
        review.setContent(form.getContent());
        review.setCreatedAt(LocalDateTime.now());
        return review;
    }


    public Review findOne(Long reviewId) {
        return reviewRepository.findOne(reviewId);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    // 리뷰 리스트를 등록 순서대로 정렬하는 로직
    public List<Review> findAllSortByCreatedTime(List<Review> reviews) {
        reviews.sort(Comparator.comparing(Review::getCreatedAt).reversed());
        return reviews;
    }

    // 작성자로 리뷰조회
    public List<Review> findAllByUserId(Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    // 장소에 대한 리뷰 조회
    public List<Review> findAllBySpotId(Long spotId) {
        return reviewRepository.findAllBySpotId(spotId);
    }


}
