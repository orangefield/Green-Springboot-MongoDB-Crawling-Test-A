package site.orangefield.mongocrudsample.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import site.orangefield.mongocrudsample.domain.Naver;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // 통합테스트(모든 레이어가 메모리에 다 뜬다)
public class NaverApiControllerTest {

    @Autowired // DI
    private TestRestTemplate rt; // http 통신

    // 다른 메서드에서도 쓸 헤더 만들기
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void save_테스트() throws JsonProcessingException {
        // (given : 가짜 데이터 만들기)
        Naver naver = new Naver();
        naver.setTitle("스프링1강");
        naver.setCompany("재밌어요");

        ObjectMapper om = new ObjectMapper(); // JSON으로 바꿔준다
        String content = om.writeValueAsString(naver); // Object를 JSON으로

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);

        // (when : 실행)
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.POST, httpEntity, String.class);

        // (then : 검증)
        // System.out.println("=======================================");
        // System.out.println(response.getBody());
        // System.out.println(response.getHeaders());
        // System.out.println(response.getStatusCode());
        // System.out.println(response.getStatusCode().is2xxSuccessful());
        // System.out.println("=======================================");
        // assertTrue(response.getStatusCode().is2xxSuccessful());
        DocumentContext dc = JsonPath.parse(response.getBody());
        // System.out.println(dc.jsonString());
        String title = dc.read("$.title"); // 키값 찾기
        // System.out.println(title);
        assertEquals("스프링1강", title);
    }

    @Test
    public void findAll_테스트() {
        // given

        // when
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.GET, null, String.class);

        // then (findAll은 이렇게 값 보는 것보다 - 내가 준게 아니니까)
        // DocumentContext dc = JsonPath.parse(response.getBody());
        // String title = dc.read("$.[0].title"); // 키값 찾기
        // assertEquals("지방선거 6.1일이 곧 다가온다", title);
        // (상태코드 보는 걸로만 끝내자)
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
