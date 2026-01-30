package toyproject.workspot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import toyproject.workspot.Repository.SpotRepository;
import toyproject.workspot.controller.searchdto.SpotSearchDto;
import toyproject.workspot.controller.searchdto.SpotSearchDtoKakao;
import toyproject.workspot.domain.Spot;
import toyproject.workspot.infrastructure.SearchApi;
import toyproject.workspot.infrastructure.kakao.dto.KakaoSearchResponse;
import toyproject.workspot.infrastructure.naver.dto.NaverSearchResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {

    private final SearchApi searchApi;
    private final SpotRepository spotRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // json 파싱하여 객체로 변환
    // controller 에서 객체로 변환하여 화면에 보여줄 때 사용
    @Transactional
    public List<SpotSearchDto> searchNaver(String keyword) {
        String jsonResponse = searchApi.searchLocal(keyword);

        // searchLocal 메서드를 통해 받아온 json 을 parseJson을 통해서 객체로 변환 -> objectMapper을 이용해서 객체의 field와 매핑
        NaverSearchResponse response = parseJsonNaver(jsonResponse);

        List<SpotSearchDto> dtoList = new ArrayList<>();

        // 객체로 변환한 json을 dto로 변환 -> dtoList에 넣음
        for (NaverSearchResponse.Item item : response.getItems()) {
            SpotSearchDto dto = SpotSearchDto.from(item);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional
    public List<SpotSearchDtoKakao> searchKakao(String keyword) {
        String jsonResponse = searchApi.searchLocal(keyword);

        KakaoSearchResponse response = parseJsonKakao(jsonResponse);

        List<SpotSearchDtoKakao> dtoList = new ArrayList<>();

        for (KakaoSearchResponse.Document document : response.getDocuments()) {
            SpotSearchDtoKakao dto = SpotSearchDtoKakao.from(document);
            dtoList.add(dto);
        }

        return dtoList;
    }



    //화면에 보여준 것을 상세보기를 하거나... 하면 db에 저장?
    @Transactional
    public Spot getOrSaveSpot(SpotSearchDto dto) {
        //db에서 먼저 조회해봄
        Spot foundSpot = spotRepository.findByUniqueId(dto.getNaverPlaceId());

        //만약 db에 없다면
        if (foundSpot == null) {
            Spot newSpot = dto.toEntity();
            spotRepository.save(newSpot);
            return newSpot;
        }

        return foundSpot;
    }

    @Transactional
    public Spot getOrSaveSpot(SpotSearchDtoKakao dto) {
        //db에서 먼저 조회해봄
        Spot foundSpot = spotRepository.findByUniqueId(dto.getId());

        //만약 db에 없다면
        if (foundSpot == null) {
            Spot newSpot = dto.toEntity();
            spotRepository.save(newSpot);
            return newSpot;
        }

        return foundSpot;
    }

    // staticMap을 이용한 image 생성 로직


    // ---------------------조회----------------------------
    public Spot findOne(Long spotId) {
        return spotRepository.findOne(spotId);
    }

    public List<Spot> findAll() {
        return spotRepository.findAll();
    }


    private NaverSearchResponse parseJsonNaver(String jsonResponse) {
        try {
            // jackson 라이브러리로 NaverSearchResponse 객체와 json key를 매핑하여 객체 생성
            NaverSearchResponse response = objectMapper.readValue(jsonResponse, NaverSearchResponse.class);
            return response;

        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }

    private KakaoSearchResponse parseJsonKakao(String jsonResponse) {

        try {
            KakaoSearchResponse response = objectMapper.readValue(jsonResponse, KakaoSearchResponse.class);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }
}
