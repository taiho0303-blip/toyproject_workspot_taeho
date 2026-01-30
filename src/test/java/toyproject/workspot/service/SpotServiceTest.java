package toyproject.workspot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.workspot.controller.searchdto.SpotSearchDto;
import toyproject.workspot.domain.Spot;

import java.util.List;

@SpringBootTest
class SpotServiceTest {

    @Autowired
    SpotService spotService;

    @Test
    void search() {
        String keyWord = "성수동 카페";
        List<SpotSearchDto> search = spotService.searchNaver(keyWord);
        for (SpotSearchDto spotSearchDto : search) {
            System.out.println("spotSearchDto.getTitle() = " + spotSearchDto.getTitle());
        }
    }

    @Test
    void getOrSaveSpot() {
        String keyWord = "성수동 카페";
        List<SpotSearchDto> search = spotService.searchNaver(keyWord);

        Spot orSaveSpot = spotService.getOrSaveSpot(search.get(0));
        System.out.println("orSaveSpot.getNaverPlaceId() = " + orSaveSpot.getUniqueId());

    }
}