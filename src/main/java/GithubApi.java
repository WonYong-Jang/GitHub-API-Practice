import org.kohsuke.github.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GithubApi {

    private static final int TOTAL_ISSUE = 2; // 여기서는 2회까지만 순회

    private String path;
    private GitHub github;
    private GHRepository repository;

    private List<GHIssue> issues;
    private HashMap<GHUser, Boolean[]> map;

    public GithubApi() throws IOException {

        // properties 파일로 token 관리
        path = "src/main/resources/application.properties";
        github = GitHubBuilder.fromPropertyFile(path).build();

        // 해당 repository 가져오기
        repository = github.getRepository("WonYong-Jang/Github-API-Practice");

        // 모든 issue 객체 가져오기
        issues = repository.getIssues(GHIssueState.ALL);

        // 사용자가 각 Issue 별로 몇번 comment를 입력했는지
        map = new HashMap<>();
    }
    public void checkAttendance() throws IOException {

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
    }
    public void NumOfParticipation() throws IOException {

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

    public GitHub getGithub() {
        return github;
    }

    public GHRepository getRepository() {
        return repository;
    }

    public List<GHIssue> getIssues() {
        return issues;
    }

    public HashMap<GHUser, Boolean[]> getMap() {
        return map;
    }
}
