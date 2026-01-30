package toyproject.workspot.infrastructure.naver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import toyproject.workspot.infrastructure.SearchApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class NaverApiClient implements SearchApi {

    @Value("${naver.client.id}") // 보안을 위해 설정파일(yml)에서 가져오기
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Override
    public String searchLocal(String keyword) {
        try {
            String openAPI = "https://openapi.naver.com/v1/search/local.json?query=" +
                    URLEncoder.encode(keyword, "UTF-8") + "&display=10";

            URL url = new URL(openAPI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

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
            throw new RuntimeException("네이버 API 호출 실패", e);
        }
    }
}
