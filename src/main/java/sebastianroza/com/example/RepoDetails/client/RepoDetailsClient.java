package sebastianroza.com.example.RepoDetails.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sebastianroza.com.example.RepoDetails.model.RepoDTO;

import java.util.Set;

@FeignClient(value = "GitHubUserInfo", url = "https://api.github.com")
public interface RepoDetailsClient {
    @GetMapping(value = "/users/{username}/repos")
    Set<RepoDTO> fetchUserRepositories(@PathVariable String username);

    @GetMapping(value = "/repos/{username}/{repoName}")
    RepoDTO getRepositoryInfo(@PathVariable String username, @PathVariable String repoName);
}
