import org.junit.jupiter.api.*;
import org.kohsuke.github.GHIssue;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


// 메소드 마다 개별적으로 테스트 하여 테스트 별로 의존성을 줄이는게 좋은 테스트 방법 이지만
// 메소드 마다 각각 인스턴스를 생성해야 하므로 하나의 인스턴스를 이용하여 테스트해야 하는 경우에는
// 테스트 생명주기를 클래스 단위로 설정 할 수 있다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 생명 주기를 클래스 단위로 설정
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 메소드 마다 순서를 정할 수 있음
@DisplayName("Github API 테스트")
class GithubApiTest {

    private GithubApi api;


    @Test
    @Order(1) // 숫자가 적을 수록 우선순위가 높다
    @DisplayName("Github API 객체 생성 테스트")
    void createInstance() throws IOException {

        api = new GithubApi();
        assertNotNull(api);

    }

    @Test
    @Order(2)
    @DisplayName("ISSUE객체를 받아온다.")
    void setIssue() {

        List<GHIssue> issues = api.getIssues();

        assertThat(issues.size()).isGreaterThan(0);
    }
}