package sebastianroza.com.example.RepoDetails.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sebastianroza.com.example.RepoDetails.mapper.RepoMapper;
import sebastianroza.com.example.RepoDetails.model.dto.RepoDTO;
import sebastianroza.com.example.RepoDetails.client.RepoDetailsClient;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepoDetailsService {
    private final RepoDetailsClient repoDetailsClient;
    private final RepoMapper repoMapper;

    public Set<RepoDTO> fetchUserRepositories(String username) {
        return repoDetailsClient.fetchUserRepositories(username).stream()
                .map(repo -> {
                            RepoDTO repoDTO = repoMapper.toDto(repo);
                            repoDTO.setOwnerDto(repoMapper.toDto(repo.getOwner()));
                            return repoDTO;
                        }
                )
                .collect(Collectors.toSet());
    }

    public RepoDTO getRepositoryInfo(String username, String repoName) {
        return repoMapper.toDto(repoDetailsClient.getRepositoryInfo(username, repoName));
    }
}
