package uz.pdp.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.pdp.clickup.domain.*;
import uz.pdp.clickup.payload.CommentDto;
import uz.pdp.clickup.payload.resp.*;

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

    /**
     * Workspace
     */
    @Mapping(source = "owner.email", target = "owner")
    WorkspaceRespDto toWorkspaceDto(Workspace workspace);

    List<WorkspaceRespDto> toWorkspaceDto(List<Workspace> workspaces);

    /**
     * Space
     */
    @Mapping(source = "workspace.id", target = "workspace")
    @Mapping(source = "icon.id", target = "icon")
    @Mapping(source = "avatar.id", target = "avatar")
    @Mapping(source = "owner.id", target = "owner")
    SpaceRespDto toSpaceDto(Space space);

    List<SpaceRespDto> toSpaceDto(List<Space> space);

    /**
     * Category
     */

    @Mapping(source = "project.id", target = "project")
    CategoryRespDto toCategoryDto(Category category);

    List<CategoryRespDto> toCategoryDto(List<Category> categories);

    /**
     * Status
     */

    @Mapping(source = "space.id", target = "spaceId")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "category.id", target = "categoryId")
    StatusRespDto toStatusDto(Status status);

    List<StatusRespDto> toStatusDto(List<Status> statuses);

    /**
     * Task
     */

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "task.id", target = "parentId")
    @Mapping(source = "priority.id", target = "priorityId")
    TaskRespDto toTaskDto(Task task);

    List<TaskRespDto> toTaskDto(List<Task> tasks);

    /**
     * Comment
     */

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "createdBy.id", target = "createdBy")
    CommentRespDto toCommentDto(Comment comment);

    List<CommentRespDto> toCommentDto(List<Comment> comments);

    /**
     * Tag
     */
    @Mapping(source = "workspace.id", target = "workspaceId")
    TagRespDto toTagDto(Tag tag);

    List<TagRespDto> toTagDto(List<Tag> tags);

}