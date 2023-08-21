package sebastianroza.com.example.RepoDetails.mapper;

import org.mapstruct.Mapper;
import sebastianroza.com.example.RepoDetails.model.Owner;
import sebastianroza.com.example.RepoDetails.model.Repo;
import sebastianroza.com.example.RepoDetails.model.dto.OwnerDto;
import sebastianroza.com.example.RepoDetails.model.dto.RepoDTO;

@Mapper(componentModel = "spring")
public interface RepoMapper {
    RepoDTO toDto(Repo repo);

    OwnerDto toDto(Owner owner);
}
