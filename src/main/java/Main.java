import org.kohsuke.github.*;

import java.io.IOException;
import java.util.List;

/**
 깃헙 이슈 1번부터 18번까지 댓글을 순회하며 댓글을 남긴 사용자를 체크 할 것.
 참여율을 계산하세요. 총 18회에 중에 몇 %를 참여했는지 소숫점 두자리가지 보여줄 것.
 Github 자바 라이브러리를 사용하면 편리합니다.
 깃헙 API를 익명으로 호출하는데 제한이 있기 때문에 본인의 깃헙 프로젝트에 이슈를 만들고 테스트를 하시면 더 자주 테스트할 수 있습니다.
 */

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "src/main/resources/application.properties";
        GitHub github = GitHubBuilder.fromPropertyFile(path).build();

        GHRepository repository = github.getRepository("WonYong-Jang/Github-API-Practice");

        List<GHIssue> issues = repository.getIssues(GHIssueState.ALL);

        for(GHIssue issue : issues) {

            

        }
    }
}
