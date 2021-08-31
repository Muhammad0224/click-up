package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.domain.CheckList;
import uz.pdp.clickup.domain.CheckListItem;
import uz.pdp.clickup.domain.ChecklistItemUser;
import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistItemDto;
import uz.pdp.clickup.payload.ItemUserDto;
import uz.pdp.clickup.repository.ChecklistItemRepo;
import uz.pdp.clickup.repository.ChecklistRepo;
import uz.pdp.clickup.repository.UserItemRepo;
import uz.pdp.clickup.repository.UserRepository;
import uz.pdp.clickup.service.ChecklistItemService;
import uz.pdp.clickup.service.ChecklistService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChecklistItemServiceImpl implements ChecklistItemService {
    private final ChecklistItemRepo checklistItemRepo;

    private final ChecklistRepo checklistRepo;

    private final UserRepository userRepository;

    private final UserItemRepo userItemRepo;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse getByChecklist(UUID checklistId) {
        if (!checklistRepo.existsById(checklistId)) {
            return new ApiResponse("Checklist not found", false);
        }
        return new ApiResponse("OK", true, mapper.toItemDto(checklistItemRepo.findAllByCheckListId(checklistId)));
    }

    @Override
    public ApiResponse create(ChecklistItemDto dto) {
        Optional<CheckList> optionalCheckList = checklistRepo.findById(dto.getChecklistId());
        if (!optionalCheckList.isPresent()) {
            return new ApiResponse("Checklist not found", false);
        }
        CheckList checkList = optionalCheckList.get();
        CheckListItem item = new CheckListItem(dto.getName(), checkList, false);
        checklistItemRepo.save(item);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse edit(UUID id, String name) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepo.findById(id);
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("Item not found", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        item.setName(name);
        checklistItemRepo.save(item);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse resolve(UUID id) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepo.findById(id);
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("Item not found", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        item.setResolved(true);
        checklistItemRepo.save(item);
        return new ApiResponse("Resolved", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepo.findById(id);
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("Item not found", false);
        }
        checklistItemRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }

    @Override
    public ApiResponse assign(ItemUserDto dto) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepo.findById(dto.getItemId());
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("Item not found", false);
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        User user = optionalUser.get();
        userItemRepo.save(new ChecklistItemUser(item, user));
        return new ApiResponse("Assigned", true);
    }

    @Override
    public ApiResponse removeUser(ItemUserDto dto) {
        if (!userItemRepo.existsByUserIdAndCheckListItemId(dto.getUserId(),dto.getItemId())){
            return new ApiResponse("Not found", false);
        }
        userItemRepo.deleteByUserIdAndCheckListItemId(dto.getUserId(),dto.getItemId());
        return new ApiResponse("Removed", true);
    }
}
