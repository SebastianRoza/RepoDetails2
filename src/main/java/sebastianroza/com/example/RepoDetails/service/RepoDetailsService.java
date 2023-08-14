package sebastianroza.com.example.RepoDetails.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sebastianroza.com.example.RepoDetails.model.RepoDTO;
import sebastianroza.com.example.RepoDetails.client.RepoDetailsClient;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RepoDetailsService {
    ObjectMapper objectMapper = new ObjectMapper();
    private final RepoDetailsClient repoDetailsClient;

    public Set<RepoDTO> fetchUserRepositories(String username) throws IOException {
        Set<RepoDTO> repositories = new HashSet<>();
        String json = repoDetailsClient.fetchUserRepositories(username);
        JsonNode jsonNode = objectMapper.readTree(json);
        for (JsonNode repositoryNode : jsonNode) {
            String name = repositoryNode.get("name").asText();
            Long id = repositoryNode.get("id").asLong();
            String description = repositoryNode.get("description").asText();
            String ownerLogin = repositoryNode.get("owner").get("login").asText();
            RepoDTO repository = new RepoDTO(id, name, description, ownerLogin);
            repositories.add(repository);
        }
        return repositories;
    }

    public RepoDTO getRepositoryInfo(String username, String repoName) throws IOException {
        String json = repoDetailsClient.getRepositoryInfo(username, repoName);
        JsonNode jsonNode = objectMapper.readTree(json);
        String name = jsonNode.get("name").asText();
        Long id = jsonNode.get("id").asLong();
        String description = jsonNode.get("description").asText();
        String ownerLogin = jsonNode.get("owner").get("login").asText();
        return new RepoDTO(id, name, description, ownerLogin);
    }
}
