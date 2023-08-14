package sebastianroza.com.example.RepoDetails.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RepoDTO {
    private Long id;
    private String name;
    private String description;
    private String ownerLogin;

}
