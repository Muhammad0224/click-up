package uz.pdp.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.domain.Workspace;
import uz.pdp.clickup.payload.resp.UserRespDto;
import uz.pdp.clickup.payload.resp.WorkspaceRespDto;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {

    /**
     * User
     */

    UserRespDto toUserDto(User user);

    List<UserRespDto> toUserDto(List<User> user);

    @Mapping(source = "owner.email", target = "owner")
    WorkspaceRespDto toWorkspaceDto(Workspace workspace);

    List<WorkspaceRespDto> toWorkspaceDto(List<Workspace> workspaces);
}