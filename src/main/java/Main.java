import org.kohsuke.github.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 깃헙 이슈 1번부터 18번까지 댓글을 순회하며 댓글을 남긴 사용자를 체크 할 것.
 참여율을 계산하세요. 총 18회에 중에 몇 %를 참여했는지 소숫점 두자리가지 보여줄 것.
 Github 자바 라이브러리를 사용하면 편리합니다.
 깃헙 API를 익명으로 호출하는데 제한이 있기 때문에 본인의 깃헙 프로젝트에 이슈를 만들고 테스트를 하시면 더 자주 테스트할 수 있습니다.
 */

public class Main {

    private static final int TOTAL_ISSUE = 2; // 여기서는 2회까지만 순회

    public static void main(String[] args) throws IOException {

        // properties 파일로 token 관리
        String path = "src/main/resources/application.properties";
        GitHub github = GitHubBuilder.fromPropertyFile(path).build();

        // 해당 repository 가져오기
        GHRepository repository = github.getRepository("WonYong-Jang/Github-API-Practice");

        // 모든 issue 객체 가져오기
        List<GHIssue> issues = repository.getIssues(GHIssueState.ALL);

        // 사용자가 각 Issue 별로 몇번 comment를 입력했는지
        HashMap<GHUser, Boolean[]> map = new HashMap<>();

        for(int i=0; i< issues.size(); i++) {

            // 각 Issue 에 대한 comments 확인
            List<GHIssueComment> comments = issues.get(i).getComments();

            // 각 comment 확인
            for(GHIssueComment comment : comments) {

                GHUser user = comment.getUser();

                Boolean[] attendance = new Boolean[TOTAL_ISSUE];;
                if(map.containsKey(user)) {
                    attendance = map.get(user);
                }
                attendance[i] = true;
                map.put(user, attendance);

            }
        }

        // 참여 횟수 구하기
        NumOfParticipation(map);

    }
    public static void NumOfParticipation(HashMap<GHUser, Boolean[]> map) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append("### 스터디 현황\n");
        sb.append("| 참여자 | 1주차 | 2주차 | 참석율\n");
        sb.append("| --- | --- | --- | --- | \n");

        for(Map.Entry<GHUser, Boolean[]> cur : map.entrySet()) {

            int sum = 0;
            Boolean[] attendance = cur.getValue();
            GHUser user = cur.getKey();
            sb.append("|"+user.getName()+"|");
            for(int i =0; i < attendance.length; i++) {
                if(attendance[i]) {
                    sum++;
                    sb.append(":white_check_mark:|");
                }
            }

            String percent = String.format("%.2f", (double)(sum*100) / TOTAL_ISSUE);
            sb.append( percent + "|");
            sb.append("\n");

        }

        System.out.println(sb.toString());
    }
}
