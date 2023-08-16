package sebastianroza.com.example.RepoDetails.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Owner {
    @JsonProperty("login")
    private String login;
    @JsonProperty("id")
    private String id;
    @JsonProperty("avatar_url")
    private String avatarUrl;
}
