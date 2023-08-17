package sebastianroza.com.example.RepoDetails.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import sebastianroza.com.example.RepoDetails.model.Owner;
import sebastianroza.com.example.RepoDetails.model.RepoDTO;

import java.util.Collections;
import java.util.Set;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.hasSize;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.cloud.openfeign.FeignClientsRegistrar.getUrl;

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
    void fetchUserRepositories_UserFoundAndRepositoriesFetched_RepositoriesFetched() throws Exception {
        String username = "testuser";
        String repositoryName = "testrepo";
        RepoDTO repoDTO = new RepoDTO(1L, repositoryName, "abc", Owner.builder().login(username).build());
        mockServer.stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse()
                        .withBody(objectMapper.writeValueAsString(Collections.singletonList(repoDTO)))));
        ResponseEntity<Set<RepoDTO>> responseEntity = restTemplate.exchange(getUrl(username), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertAll(
                () -> Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200)),
                () -> Assertions.assertEquals(repositoryName, responseEntity.getBody().contains(repoDTO)),
                () -> Assertions.assertEquals(1, responseEntity.getBody().size())
        );
    }
}
