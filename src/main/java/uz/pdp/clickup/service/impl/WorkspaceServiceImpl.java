package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.domain.*;
import uz.pdp.clickup.enums.AddType;
import uz.pdp.clickup.enums.WorkspacePermissionName;
import uz.pdp.clickup.enums.WorkspaceRoleName;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDTO;
import uz.pdp.clickup.payload.WorkspaceDTO;
import uz.pdp.clickup.repository.*;
import uz.pdp.clickup.service.WorkspaceService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    private final AttachmentRepository attachmentRepository;

    private final WorkspaceUserRepository workspaceUserRepository;

    private final WorkspaceRoleRepository workspaceRoleRepository;

    private final WorkspacePermissionRepository workspacePermissionRepository;

    private final UserRepository userRepository;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse getUserWorkspaces(UUID userId) {
        return new ApiResponse("OK", true,
                mapper.toWorkspaceDto(workspaceRepository.findAllByOwnerId(userId)));
    }

    @Override
    public ApiResponse getWorkspaceMembers(Long id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()){
            return new ApiResponse("Workspace not found", false);
        }
        List<User> members = new ArrayList<>();
        for (WorkspaceRoleName value : WorkspaceRoleName.values()) {
            if (value!=WorkspaceRoleName.ROLE_GUEST){
                List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByWorkspaceIdAndWorkspaceRole_Name(id, value.name());
                workspaceUsers.forEach(workspaceUser -> members.add(workspaceUser.getUser()));
            }
        }
        return new ApiResponse("OK", true,
                mapper.toUserDto(members));
    }

    @Override
    public ApiResponse getWorkspaceGuests(Long id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()){
            return new ApiResponse("Workspace not found", false);
        }
        List<User> guests = new ArrayList<>();
        List<WorkspaceUser> workspaceUsers =
                workspaceUserRepository.findAllByWorkspaceIdAndWorkspaceRole_Name(id, WorkspaceRoleName.ROLE_GUEST.name());
        workspaceUsers.forEach(workspaceUser -> guests.add(workspaceUser.getUser()));
        return new ApiResponse("OK", true,
                mapper.toUserDto(guests));
    }

    @Override
    public ApiResponse addWorkspace(WorkspaceDTO dto, User user) {
        //WORKSPACE OCHDIK
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), dto.getName()))
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        Workspace workspace = new Workspace(
                dto.getName(),
                dto.getColor(),
                user,
                dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workspaceRepository.save(workspace);

        //WORKSPACE ROLE OCHDIK
        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                workspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));
        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));


        //OWERGA HUQUQLARNI BERYAPAMIZ
        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName);
            workspacePermissions.add(workspacePermission);
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workspacePermissions.add(new WorkspacePermission(
                        adminRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workspacePermissions.add(new WorkspacePermission(
                        memberRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workspacePermissions.add(new WorkspacePermission(
                        guestRole,
                        workspacePermissionName));
            }

        }
        workspacePermissionRepository.saveAll(workspacePermissions);

        //WORKSPACE USER OCHDIK
        workspaceUserRepository.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())

        ));

        return new ApiResponse("Ishxona saqlandi", true);
    }

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDTO dto) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        if (workspaceRepository.existsByOwnerIdAndNameAndIdNot(workspace.getOwner().getId(), dto.getName(), id)){
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        }

        workspace.setAvatar(dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
        workspace.setColor(dto.getColor());
        workspace.setName(dto.getName());
        workspaceRepository.save(workspace);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Optional<User> optionalUser = userRepository.findById(ownerId);
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found", false);
        workspace.setOwner(optionalUser.get());
        workspaceRepository.save(workspace);
        return new ApiResponse("Owner edited", false);
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("O'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik", false);
        }
    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO dto) {
        if (dto.getAddType().equals(AddType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    userRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    workspaceRoleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );
            workspaceUserRepository.save(workspaceUser);

            //TODO EMAILGA INVITE XABAR YUBORISH
        } else if (dto.getAddType().equals(AddType.EDIT)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, dto.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")));
            workspaceUserRepository.save(workspaceUser);
        } else if (dto.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, dto.getId());
        }
        return new ApiResponse("Muvaffaqiyatli", true);
    }

    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Error", false);
    }
}
