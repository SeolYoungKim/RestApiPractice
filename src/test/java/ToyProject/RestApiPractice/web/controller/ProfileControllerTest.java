package ToyProject.RestApiPractice.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("profile은 인증 없이 호출된다.")
    @Test
    void callProfile() {
        String expected = "default";

        ResponseEntity<String> response = restTemplate.getForEntity("/profile", String.class);  // /profile 요청에 대한 응답객체(ResponseEntity)를 얻어낸다.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);  // 응답 status code를 예상한다.
        assertThat(response.getBody()).isEqualTo(expected);  // 활성화된 profile이 없으므로 default이다.
    }

}