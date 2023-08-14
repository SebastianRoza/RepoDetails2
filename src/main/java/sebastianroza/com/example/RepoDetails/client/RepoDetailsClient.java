package sebastianroza.com.example.RepoDetails.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="GitHubUserInfo", url = "https://api.github.com")
public interface RepoDetailsClient {
    @GetMapping(value = "/users/{email}/repos")
    String fetchUserRepositories(@PathVariable String email);
    @GetMapping(value = "/repos/{email}/{repoName}")
    String getRepositoryInfo(@PathVariable String email, @PathVariable String repoName);
}
