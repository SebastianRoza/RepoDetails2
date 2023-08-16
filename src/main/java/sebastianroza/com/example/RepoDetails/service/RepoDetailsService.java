package sebastianroza.com.example.RepoDetails.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sebastianroza.com.example.RepoDetails.model.RepoDTO;
import sebastianroza.com.example.RepoDetails.client.RepoDetailsClient;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RepoDetailsService {
    private final RepoDetailsClient repoDetailsClient;

    public Set<RepoDTO> fetchUserRepositories(String username) {
        return repoDetailsClient.fetchUserRepositories(username);
    }

    public RepoDTO getRepositoryInfo(String username, String repoName) {
        return repoDetailsClient.getRepositoryInfo(username, repoName);
    }
}
