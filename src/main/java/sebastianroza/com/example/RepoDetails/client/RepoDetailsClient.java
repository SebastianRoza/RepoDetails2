package sebastianroza.com.example.RepoDetails.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sebastianroza.com.example.RepoDetails.model.Repo;


import java.util.Set;

@FeignClient(value = "GitHubUserInfo", url = "${github.api.client.url}")
@Headers("Accept: application/vnd.github+json")
public interface RepoDetailsClient {
    @GetMapping(value = "/users/{username}/repos")
    Set<Repo> fetchUserRepositories(@PathVariable String username);

    @GetMapping(value = "/repos/{username}/{repoName}")
    Repo getRepositoryInfo(@PathVariable String username, @PathVariable String repoName);
}
