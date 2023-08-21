package sebastianroza.com.example.RepoDetails.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sebastianroza.com.example.RepoDetails.model.dto.RepoDTO;
import sebastianroza.com.example.RepoDetails.service.RepoDetailsService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github-details")
public class RepoDetailsController {
    private final RepoDetailsService repoDetailsService;

    @Operation(summary = "Fetch all User repositories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repositories fetched and displayed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Repositories not fetched and not displayed",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Repo not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Repo forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "301", description = "Repo moved permanently",
                    content = @Content),

    })
    @GetMapping("/{username}")
    Set<RepoDTO> fetchUserRepositories(@PathVariable String username) {
        return repoDetailsService.fetchUserRepositories(username);
    }

    @Operation(summary = "Get info about specific user repository (input=name of repository)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repository found and displayed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Repositories not fetched and not displayed",
                    content = @Content)})
    @GetMapping("/{username}/{repoName}")
    RepoDTO getRepositoryInfo(@PathVariable String username, @PathVariable String repoName) {
        return repoDetailsService.getRepositoryInfo(username, repoName);
    }
}
