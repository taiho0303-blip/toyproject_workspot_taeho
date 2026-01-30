package toyproject.workspot.infrastructure.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import toyproject.workspot.infrastructure.SearchApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
@Primary
public class KakaoApiClient implements SearchApi {

    @Value("${kakao.api.key}")
    private String REST_API_KEY;

    @Override
    public String searchLocal(String keyWord) {
        try {
            String openAPI = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" +
                    URLEncoder.encode(keyWord, "UTF-8") + "&size=15";

            URL url = new URL(openAPI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + REST_API_KEY);
            con.setRequestProperty("Content-Type", "application/json");

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            throw new RuntimeException("카카오 API 호출 실패", e);
        }
    }
}
