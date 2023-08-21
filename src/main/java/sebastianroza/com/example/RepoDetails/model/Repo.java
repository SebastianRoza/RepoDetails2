package sebastianroza.com.example.RepoDetails.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Repo {
    private Long id;
    private String name;
    private String description;
    private Owner owner;
}
