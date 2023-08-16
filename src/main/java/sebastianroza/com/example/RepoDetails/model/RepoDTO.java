package sebastianroza.com.example.RepoDetails.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RepoDTO {
    private Long id;
    private String name;
    private String description;
    private Owner owner;
}
