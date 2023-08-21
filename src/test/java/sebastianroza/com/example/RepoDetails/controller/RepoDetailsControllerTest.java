package sebastianroza.com.example.RepoDetails.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sebastianroza.com.example.RepoDetails.model.dto.OwnerDto;
import sebastianroza.com.example.RepoDetails.model.dto.RepoDTO;

import java.util.HashSet;
import java.util.Set;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8887"})
@AutoConfigureWireMock(port = 7070)
public class RepoDetailsControllerTest {
    @Autowired
    private WireMockServer mockServer;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;


    @Test
    void fetchUserRepositories_UserFoundAndRepositoriesFetched_RepositoriesDetailsReturned() throws Exception {
        String username = "testuser";
        String repositoryName = "testrepo";
        RepoDTO repoDTO = new RepoDTO(1L, repositoryName, "abc", OwnerDto.builder().login(username).build());
        Set<RepoDTO> repoDTOS = new HashSet<>();
        repoDTOS.add(repoDTO);
        mockServer.stubFor(get(urlEqualTo("/github-details/" + username))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(repoDTOS))));
        ResponseEntity<Set<RepoDTO>> responseEntity = restTemplate.exchange("http://localhost:7070/github-details/" + username, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertAll(
                () -> Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200)),
                () -> Assertions.assertTrue(responseEntity.getBody().contains(repoDTO)),
                () -> Assertions.assertEquals(1, responseEntity.getBody().size())
        );
    }

    @Test
    void fetchUserRepositories_UserOrRepositoryNotFound_ExceptionThrown() throws Exception {
        String username = "xxx";
        stubFor(get(urlEqualTo("/github-details/" + username))
                .willReturn(aResponse().withStatus(404)));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.exchange("http://localhost:7070/github-details/" + username, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                })
        );

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(exception.getMessage()).contains("404 Not Found: [no body]");
    }

    @Test
    void getRepositoryInfo_RepositoryFound_RepositoryDetailsReturned() throws Exception {
        String username = "testuser";
        String repositoryName = "testrepo";
        OwnerDto ownerDto = OwnerDto.builder().login(username).build();
        RepoDTO repoDTO = new RepoDTO(1L, repositoryName, "abc", ownerDto);
        mockServer.stubFor(get(urlEqualTo("/github-details/" + username + "/" + repositoryName))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(repoDTO))));

        ResponseEntity<RepoDTO> responseEntity = restTemplate.exchange("http://localhost:7070/github-details/" + username + "/" + repositoryName, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertAll(
                () -> Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200)),
                () -> Assertions.assertEquals(repoDTO.getName(), responseEntity.getBody().getName()),
                () -> Assertions.assertEquals(repoDTO.getId(), responseEntity.getBody().getId())
        );
    }

    @Test
    void getRepositoryInfo_RepositoryNotFound_ExceptionThrown() throws Exception {
        String username = "testuser";
        String repositoryName = "testrepo";
        OwnerDto ownerDto = OwnerDto.builder().login(username).build();
        RepoDTO repoDTO = new RepoDTO(1L, repositoryName, "abc", ownerDto);
        stubFor(get(urlEqualTo("/github-details/" + username + "/" + repositoryName))
                .willReturn(aResponse().withStatus(404)));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.exchange("http://localhost:7070/github-details/" + username + "/" + repositoryName, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                })
        );

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(exception.getMessage()).contains("404 Not Found: [no body]");
    }
}
