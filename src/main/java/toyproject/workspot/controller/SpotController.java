package toyproject.workspot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.workspot.controller.searchdto.SpotSearchDto;
import toyproject.workspot.controller.searchdto.SpotSearchDtoKakao;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.service.SpotService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    // 검색어 입력하는 화면 보여줌
    @GetMapping("/spot/searchWord")
    public String searchForm() {
        return "spot/searchSpotForm";
    }

    // 화면에 입력된 검색어(searchWord)를 바탕으로 search 해서 searchResult를 받아옴 -> spotList.html에서 검색된 결과를 보여줌
    @PostMapping("/spot/searchWord")
    public String search(@RequestParam("searchWord") String searchWord, Model model) {
        List<SpotSearchDtoKakao> searchResult = spotService.searchKakao(searchWord);

        model.addAttribute("spots", searchResult);
        model.addAttribute("searchWord", searchWord);

        return "spot/spotList";
    }

    //다음으로 구현해야 할것
    // 상세보기를 눌렀을 때 db에 저장되는 것과 리뷰 보이게 하는 등의 절차가 필요할듯...

    @PostMapping("/spot/detail")
    public String spotDetail(SpotSearchDtoKakao dto, Model model) {

        Spot spot = spotService.getOrSaveSpot(dto);
        model.addAttribute("spot", spot);

        return "spot/spotDetail";
    }

}
