package sebastianroza.com.example.RepoDetails.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sebastianroza.com.example.RepoDetails.client.RepoDetailsClient;
import sebastianroza.com.example.RepoDetails.model.Owner;
import sebastianroza.com.example.RepoDetails.model.RepoDTO;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class RepoDetailsServiceTest {
    @Mock
    RepoDetailsClient repoDetailsClient;
    @InjectMocks
    RepoDetailsService repoDetailsService;

    @Test
    void fetchUserRepositories_RepositoriesFetched_RepositoriesReturned() {
        RepoDTO repoDTO = new RepoDTO();
        repoDTO.setName("abc");
        Owner owner = new Owner();
        owner.setLogin("seba");
        repoDTO.setOwner(owner);
        RepoDTO repoDTO2 = new RepoDTO();
        repoDTO.setName("xyz");
        Owner owner2 = new Owner();
        owner2.setLogin("seba");
        repoDTO2.setOwner(owner2);
        Set<RepoDTO> repoDTOList = new HashSet<>();
        repoDTOList.add(repoDTO2);
        repoDTOList.add(repoDTO);
        Mockito.when(repoDetailsClient.fetchUserRepositories("seba")).thenReturn(repoDTOList);

        var result = repoDetailsService.fetchUserRepositories("seba");

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(repoDTO2));
        Assertions.assertTrue(result.contains(repoDTO));
    }

    @Test
    void getRepositoryInfo_RepositoryFound_RepositoryReturned() {
        RepoDTO repoDTO = new RepoDTO();
        repoDTO.setName("abc");
        Owner owner = new Owner();
        owner.setLogin("seba");
        repoDTO.setOwner(owner);
        repoDTO.setId(123L);
        Mockito.when(repoDetailsService.getRepositoryInfo("seba", "abc")).thenReturn(repoDTO);

        var result = repoDetailsService.getRepositoryInfo("seba", "abc");

        Assertions.assertEquals("seba", result.getOwner().getLogin());
        Assertions.assertEquals(123L, result.getId());
        Assertions.assertEquals("abc", result.getName());
    }

}
