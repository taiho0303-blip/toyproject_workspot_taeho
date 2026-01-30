package toyproject.workspot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.workspot.controller.searchdto.SpotSearchDtoKakao;
import toyproject.workspot.domain.Review;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.domain.User;
import toyproject.workspot.service.ReviewService;
import toyproject.workspot.service.SpotService;
import toyproject.workspot.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final SpotService spotService;


    // 리뷰 작성에 들어가면 리뷰를 작성할 유저 선택
    @GetMapping("/review/chooseUser")
    public String chooseUser(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "review/chooseUser";
    }

    // 장소를 검색하기 위해 검색어를 입력하는 페이지
    @GetMapping("/review/searchSpot/{userId}")
    public String searchWord(@PathVariable long userId, Model model) {
        model.addAttribute("userId", userId);
        return "review/searchSpotForm";
    }

    // 검색한 장소들이 나오고 리뷰 작성할 장소를 선택하는 페이지
    @PostMapping("/review/searchSpot/{userId}")
    public String searchResult(@PathVariable long userId,
                               @RequestParam("searchWord") String searchWord,
                               Model model) {
        List<SpotSearchDtoKakao> searchResult = spotService.searchKakao(searchWord);

        model.addAttribute("spots", searchResult);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("userId", userId);

        return "review/searchSpotList";
    }

    // 선택한 장소를 DB에 저장 하고 userId와 spotId를 가지고 reviewForm을 만듦
    @PostMapping("/review/reviewForm/{userId}")
    public String chooseSpot(@PathVariable long userId,
                             @ModelAttribute SpotSearchDtoKakao dto,
                             Model model) {
        Spot spot = spotService.getOrSaveSpot(dto);
        ReviewForm form = new ReviewForm();
        form.setUserId(userId);
        form.setSpotId(spot.getId());
        model.addAttribute("reviewForm", form);

        return "review/createReviewForm";
    }

    // 리뷰제목과 내용을 입력하고 완성된 리뷰를 db에 save
    //PRG 패턴..
    @PostMapping("/review/new")
    public String create(@ModelAttribute("reviewForm") ReviewForm form, RedirectAttributes redirectAttributes) {

        User user = userService.findOne(form.getUserId());
        Spot spot = spotService.findOne(form.getSpotId());

        reviewService.saveReview(form, user, spot);

        redirectAttributes.addAttribute("status", true);
        redirectAttributes.addAttribute("type", "review");
        return "redirect:/";
    }


    @GetMapping("/review/reviews")
    public String list(@RequestParam(value = "userNickName", required = false) String userNickName, Model model) {

        List<User> usersByNickName = userService.findByNickName(userNickName);

        // userNickName이 파라미터로 들어오지 않았거나 검색한 닉네임이 없는 경우 모든 리뷰를 화면에 보여줌
        if (usersByNickName.isEmpty()) {
            List<Review> reviews = reviewService.findAll();
            List<Review> sortReviews = reviewService.findAllSortByCreatedTime(reviews);
            model.addAttribute("reviews", reviews);

            return "review/reviews";
        }

        List<Review> reviews = new ArrayList<>();
        // user의 id값 알아내고 그걸로 reviewService.findByUserId로 찾아내야될듯
        for (User user : usersByNickName) {
            Long userId = user.getId();
            List<Review> reviewsByUserId = reviewService.findAllByUserId(userId);
            List<Review> sortReviews = reviewService.findAllSortByCreatedTime(reviewsByUserId);
            reviews.addAll(sortReviews);
        }

        model.addAttribute("reviews", reviews);

        return "review/reviews";
    }

    @GetMapping("/review/{reviewId}")
    public String showReview(@PathVariable("reviewId") Long reviewId, Model model) {
        Review review = reviewService.findOne(reviewId);
        model.addAttribute("review", review);

        return "review/showReview";
    }
}
