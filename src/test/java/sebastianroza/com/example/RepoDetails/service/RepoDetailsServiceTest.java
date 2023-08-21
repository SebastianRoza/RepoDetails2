package sebastianroza.com.example.RepoDetails.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sebastianroza.com.example.RepoDetails.client.RepoDetailsClient;
import sebastianroza.com.example.RepoDetails.mapper.RepoMapper;
import sebastianroza.com.example.RepoDetails.model.Owner;
import sebastianroza.com.example.RepoDetails.model.Repo;
import sebastianroza.com.example.RepoDetails.model.dto.OwnerDto;
import sebastianroza.com.example.RepoDetails.model.dto.RepoDTO;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class RepoDetailsServiceTest {
    @Mock
    RepoDetailsClient repoDetailsClient;
    @Mock
    RepoMapper repoMapper;
    @InjectMocks
    RepoDetailsService repoDetailsService;

    @Test
    void fetchUserRepositories_RepositoriesFetched_RepositoriesReturned() {
        Owner owner = Owner.builder().login("seba").build();
        Repo repo = Repo.builder().name("xyz").owner(owner).build();
        RepoDTO repoDTO = RepoDTO.builder().name("xyz").build();
        Set<Repo> repoList = new HashSet<>();
        repoList.add(repo);
        Mockito.when(repoDetailsClient.fetchUserRepositories(eq("seba"))).thenReturn(repoList);
        Mockito.when(repoMapper.toDto(repo)).thenReturn(repoDTO);

        var result = repoDetailsService.fetchUserRepositories("seba");

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains(repoDTO));
    }

    @Test
    void getRepositoryInfo_RepositoryFound_RepositoryReturned() {
        RepoDTO repoDTO = new RepoDTO();
        repoDTO.setName("abc");
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setLogin("seba");
        repoDTO.setOwnerDto(ownerDto);
        repoDTO.setId(123L);
        Mockito.when(repoDetailsService.getRepositoryInfo(eq("seba"), eq("abc"))).thenReturn(repoDTO);

        var result = repoDetailsService.getRepositoryInfo("seba", "abc");

        Assertions.assertEquals("seba", result.getOwnerDto().getLogin());
        Assertions.assertEquals(123L, result.getId());
        Assertions.assertEquals("abc", result.getName());
    }

}
