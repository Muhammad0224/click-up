package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.domain.CheckList;
import uz.pdp.clickup.domain.Task;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistDto;
import uz.pdp.clickup.repository.ChecklistRepo;
import uz.pdp.clickup.repository.TaskRepo;
import uz.pdp.clickup.service.ChecklistService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {
    private final ChecklistRepo checklistRepo;

    private final TaskRepo taskRepo;

    private final MapstructMapper mapper;

    @Override
    public ApiResponse getChecklistByTask(UUID taskId) {
        if (!taskRepo.existsById(taskId)) {
            return new ApiResponse("Task not found", false);
        }
        return new ApiResponse("OK", true, mapper.toChecklistDto(checklistRepo.findAllByTaskId(taskId)));
    }

    @Override
    public ApiResponse create(ChecklistDto dto) {
        Optional<Task> optionalTask = taskRepo.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        CheckList checkList = new CheckList(dto.getName(), optionalTask.get());
        checklistRepo.save(checkList);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse edit(UUID id, String name) {
        Optional<CheckList> optionalCheckList = checklistRepo.findById(id);
        if (!optionalCheckList.isPresent()) {
            return new ApiResponse("Checklist not found", false);
        }
        CheckList checkList = optionalCheckList.get();
        checkList.setName(name);
        checklistRepo.save(checkList);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!checklistRepo.existsById(id)){
            return new ApiResponse("Checklist not found", false);
        }
        checklistRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
