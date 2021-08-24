package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.clickup.domain.*;
import uz.pdp.clickup.enums.AccessType;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.*;
import uz.pdp.clickup.repository.*;
import uz.pdp.clickup.service.SpaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepo spaceRepo;

    private final WorkspaceRepository workspaceRepository;

    private final AttachmentRepository attachmentRepository;

    private final IconRepo iconRepo;

    private final SpaceUserRepo spaceUserRepo;

    private final WorkspaceUserRepository workspaceUserRepository;

    private final SpaceClickAppsRepo spaceClickAppsRepo;

    private final SpaceViewRepo spaceViewRepo;

    private final ClickAppsRepo clickAppsRepo;

    private final ViewRepo viewRepo;

    private final UserRepository userRepository;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse get(UUID id) {
        Optional<Space> optionalSpace = spaceRepo.findById(id);
        return optionalSpace.map(space -> new ApiResponse("OK", true, mapper.toSpaceDto(space))).orElseGet(() -> new ApiResponse("Space not found", false));
    }

    @Override
    public ApiResponse get(Long workspaceId) {
        return new ApiResponse("OK", true, mapper.toSpaceDto(spaceRepo.findAllByWorkspaceId(workspaceId)));
    }

    @Override
    public ApiResponse addSpace(SpaceDto dto, User user) {
        if (spaceRepo.existsByNameAndWorkspaceId(dto.getName(), dto.getWorkspaceId())) {
            return new ApiResponse("Space has already existed", false);
        }
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Space space = new Space(
                dto.getName(),
                dto.getColor(),
                workspace,
                dto.getIconId() == null ? null : iconRepo.findById(dto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("icon")),
                dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")),
                user,
                dto.getAccessType());
        spaceRepo.save(space);

        List<SpaceUser> members = new ArrayList<>();
        if (dto.getAccessType().equals(AccessType.PUBLIC)) {
            for (WorkspaceUser workspaceUser : workspaceUserRepository.findAllByWorkspaceId(workspace.getId())) {
                members.add(new SpaceUser(workspaceUser.getUser(), space));
            }
            spaceUserRepo.saveAll(members);
        } else {
            for (UUID memberId : dto.getMembers()) {
                if (workspaceUserRepository.findByWorkspaceIdAndUserId(workspace.getId(), memberId).isPresent()) {
                    members.add(new SpaceUser(userRepository.getById(memberId), space));
                }
            }
            spaceUserRepo.save(new SpaceUser(user, space));
        }
        spaceClickAppsRepo.save(new SpaceClickApps(clickAppsRepo.getByName("Priority"), space));
        spaceViewRepo.save(new SpaceView(viewRepo.getByName("List"), space));

        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse editSpace(SpaceDto dto, UUID id) {
        if (spaceRepo.existsByNameAndWorkspaceIdAndIdNot(dto.getName(), dto.getWorkspaceId(), id)) {
            return new ApiResponse("Space has already existed", false);
        }

        Optional<Space> optionalSpace = spaceRepo.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        space.setAvatar(dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
        space.setIcon(dto.getIconId() == null ? null : iconRepo.findById(dto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("icon")));
        space.setColor(dto.getColor());
        space.setName(dto.getName());

        if (!space.getAccessType().equals(dto.getAccessType())) {
            space.setAccessType(dto.getAccessType());
            editMembers(space, dto.getMembers(), "SAVE");
        }

        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse attachMembers(AttachMemberDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepo.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editMembers(space, dto.getMembers(), "SAVE");

        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse attachViews(AttachViewsDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepo.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editViews(space, dto.getViews(), "SAVE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse attachClickApp(AttachClickAppDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepo.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editClickApp(space, dto.getClickApps(), "SAVE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse detachMembers(AttachMemberDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepo.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editMembers(space, dto.getMembers(), "DELETE");

        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse detachViews(AttachViewsDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepo.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editViews(space, dto.getViews(), "DELETE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse detachClickApp(AttachClickAppDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepo.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        editClickApp(space, dto.getClickApps(), "DELETE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!spaceRepo.existsById(id)){
            return new ApiResponse("Space not found", false);
        }
        spaceRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }

    private void editMembers(Space space, List<UUID> members, String operation) {
        List<SpaceUser> spaceUsers = spaceUserRepo.findAllBySpaceId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceUser> newMembers = new ArrayList<>(spaceUsers);
            for (UUID memberId : members) {
                if (workspaceUserRepository.findByWorkspaceIdAndUserId(space.getWorkspace().getId(), memberId).isPresent()) {
                    boolean has = false;
                    for (SpaceUser spaceUser : spaceUsers) {
                        if (spaceUser.getMember().getId().equals(memberId)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newMembers.add(new SpaceUser(userRepository.getById(memberId), space));
                    }
                }
            }
            spaceUserRepo.saveAll(newMembers);
        } else if (operation.equals("DELETE")) {
            List<SpaceUser> deleting = new ArrayList<>();
            for (UUID memberId : members) {
                for (SpaceUser spaceUser : spaceUsers) {
                    if (workspaceUserRepository.findByWorkspaceIdAndUserId(space.getWorkspace().getId(), memberId).isPresent()) {
                        if (memberId.equals(spaceUser.getMember().getId())) {
                            deleting.add(spaceUser);
                        }
                    }
                }
            }
            spaceUserRepo.deleteAll(deleting);
        }
    }

    private void editViews(Space space, List<Long> views, String operation) {
        List<SpaceView> spaceViews = spaceViewRepo.findAllBySpaceId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceView> newViews = new ArrayList<>(spaceViews);
            for (Long viewId : views) {
                if (viewRepo.findById(viewId).isPresent()) {
                    boolean has = false;
                    for (SpaceView spaceView : spaceViews) {
                        if (spaceView.getView().getId().equals(viewId)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newViews.add(new SpaceView(viewRepo.getById(viewId), space));
                    }
                }
            }
            spaceViewRepo.saveAll(newViews);
        } else if (operation.equals("DELETE")) {
            List<SpaceView> deleting = new ArrayList<>();
            for (Long viewId : views) {
                for (SpaceView spaceView : spaceViews) {
                    if (viewRepo.findById(viewId).isPresent() && viewId.equals(spaceView.getView().getId())) {
                        deleting.add(spaceView);
                    }
                }
            }
            spaceViewRepo.deleteAll(deleting);
        }
    }

    private void editClickApp(Space space, List<Long> clickApps, String operation) {
        List<SpaceClickApps> spaceClickApps = spaceClickAppsRepo.findAllBySpaceId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceClickApps> newClickApps = new ArrayList<>(spaceClickApps);
            for (Long clickApp : clickApps) {
                if (clickAppsRepo.findById(clickApp).isPresent()) {
                    boolean has = false;
                    for (SpaceClickApps spaceClickApps1 : spaceClickApps) {
                        if (spaceClickApps1.getClickApps().getId().equals(clickApp)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newClickApps.add(new SpaceClickApps(clickAppsRepo.getById(clickApp), space));
                    }
                }
            }
            spaceClickAppsRepo.saveAll(newClickApps);
        } else if (operation.equals("DELETE")) {
            List<SpaceClickApps> deleting = new ArrayList<>();
            for (Long clickApp : clickApps) {
                for (SpaceClickApps clickApps1 : spaceClickApps) {
                    if (clickAppsRepo.findById(clickApp).isPresent() && clickApp.equals(clickApps1.getClickApps().getId())) {
                        deleting.add(clickApps1);
                    }
                }
            }
            spaceClickAppsRepo.deleteAll(deleting);
        }
    }


}


