package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickup.domain.*;
import uz.pdp.clickup.enums.AddType;
import uz.pdp.clickup.mapper.MapstructMapper;
import uz.pdp.clickup.payload.*;
import uz.pdp.clickup.repository.*;
import uz.pdp.clickup.service.TaskService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;

    private final StatusRepo statusRepo;

    private final CategoryRepo categoryRepo;

    private final PriorityRepo priorityRepo;

    private final TaskHistoryRepo taskHistoryRepo;

    private final AttachmentRepository attachmentRepository;

    private final TaskAttachmentRepo taskAttachmentRepo;

    private final WorkspaceRepository workspaceRepository;

    private final TagRepo tagRepo;

    private final TaskTagRepo taskTagRepo;

    private final UserRepository userRepository;

    private final TaskUserRepo taskUserRepo;

    private final MapstructMapper mapper;

    private final Path root = Paths.get("C:\\opt");

    @Override
    public ApiResponse get(UUID id) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        return optionalTask.map(task -> new ApiResponse("Ok", true, mapper.toTaskDto(task))).orElseGet(() -> new ApiResponse("Task not found", false));
    }

    @Override
    public ApiResponse getByCategory(UUID categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            return new ApiResponse("Category not found", false);
        }
        return new ApiResponse("OK", true, mapper.toTaskDto(taskRepo.findAllByCategoryId(categoryId)));
    }

    @Override
    public ApiResponse create(TaskDto dto) {
        Optional<Status> optionalStatus = statusRepo.findById(dto.getStatusId());
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found", false);
        }
        Optional<Category> optionalCategory = categoryRepo.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found", false);
        }
        Optional<Priority> optionalPriority = priorityRepo.findById(dto.getPriorityId());
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found", false);
        }
        Status status = optionalStatus.get();
        Category category = optionalCategory.get();
        Priority priority = optionalPriority.get();
        Task task = new Task(dto.getName(), status, category, priority, dto.getStartedDate(), dto.isStartTimeHas(), dto.isDueTimeHas(), dto.getDueDate());
        Task savedTask = taskRepo.save(task);

        createTaskHistory(savedTask, null, null, null,
                User.getCurrentUser().getUsername() + " created task");

        TaskUser taskUser = new TaskUser(savedTask, User.getCurrentUser());
        taskUserRepo.save(taskUser);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse changeStatus(UUID id, UUID statusId) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Optional<Status> optionalStatus = statusRepo.findById(statusId);
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found", false);
        }
        Status status = optionalStatus.get();
        Task task = optionalTask.get();
        createTaskHistory(task, "status", task.getStatus().getName(), status.getName(),
                User.getCurrentUser().getUsername() + " changed status from " + task.getStatus().getName() + " to " +
                        status.getName());
        task.setStatus(status);
        taskRepo.save(task);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse changeDescription(UUID id, String description) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        createTaskHistory(task, "description", task.getDescription(), description,
                User.getCurrentUser().getUsername() + " changed description");
        return new ApiResponse("Updated", true);
    }

    @SneakyThrows
    @Override
    public ApiResponse attachFile(UUID id, MultipartFile file) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        String uniqueName = UUID.randomUUID().toString();

        try {
            Files.copy(file.getInputStream(), root.resolve(uniqueName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Attachment attachment = new Attachment();
        attachment.setName(uniqueName);
        attachment.setOriginalName(file.getOriginalFilename());
        attachment.setPath(root.toString());
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());

        Attachment savedAttachment = attachmentRepository.save(attachment);
        TaskAttachment taskAttachment = new TaskAttachment(task, savedAttachment);
        taskAttachmentRepo.save(taskAttachment);
        createTaskHistory(task, "File", null, null,
                User.getCurrentUser().getUsername() + " attached file");

        return new ApiResponse("Attached", true);
    }

    @Override
    public ApiResponse detachFile(UUID id, String fileName) {
        Attachment attachment = attachmentRepository.findByName(fileName);
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        taskAttachmentRepo.deleteByAttachmentIdAndTaskId(attachment.getId(), task.getId());
        createTaskHistory(task, "File", null, null,
                User.getCurrentUser().getUsername() + " detached file");

        return new ApiResponse("Detached", true);
    }

    @Override
    public ApiResponse createSubtask(SubtaskDto dto) {
        Optional<Task> optionalTask = taskRepo.findById(dto.getParentId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Parent task not found", false);
        }
        Optional<Priority> optionalPriority = priorityRepo.findById(dto.getPriorityId());
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found", false);
        }
        Task task = optionalTask.get();
        Priority priority = optionalPriority.get();
        Task subtask = new Task(dto.getName(), priority, task, dto.getStartedDate(), dto.isStartTimeHas(), dto.isDueTimeHas(), dto.getDueDate(), dto.getEstimateTime(), dto.getActivateDate());
        taskRepo.save(subtask);
        createTaskHistory(task, "Subtask", null, null,
                User.getCurrentUser().getUsername() + " created subtask");
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse dueDate(UUID id, DueDateDto dto) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Parent task not found", false);
        }
        Task task = optionalTask.get();

        if (dto.getAddType().equals(AddType.REMOVE)) {
            task.setDueDate(null);
            task.setDueTimeHas(false);
            task.setStartedDate(null);
            task.setStartTimeHas(false);
        } else {
            task.setDueDate(dto.getDueDate());
            task.setDueTimeHas(dto.isDueTimeHas());
            task.setStartedDate(dto.getStartedDate());
            task.setStartTimeHas(dto.isStartTimeHas());
        }
        taskRepo.save(task);
        createTaskHistory(task, "Due date", null, null,
                User.getCurrentUser().getUsername() + " changed due date");
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse changePriority(UUID id, Long priorityId) {
        Optional<Priority> optionalPriority = priorityRepo.findById(priorityId);
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found", false);
        }
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Priority priority = optionalPriority.get();
        Task task = optionalTask.get();
        createTaskHistory(task, "Priority", task.getPriority().getName(), priority.getName(),
                User.getCurrentUser().getUsername() + " changed priority");
        task.setPriority(priority);
        taskRepo.save(task);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse changeEstimate(UUID id, Long estimate) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        createTaskHistory(task, "Estimate", String.valueOf(task.getEstimateTime()), String.valueOf(estimate),
                User.getCurrentUser().getUsername() + " changed estimate time");
        task.setEstimateTime(estimate);
        taskRepo.save(task);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse addTag(TaskTagDto dto) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Optional<Task> optionalTask = taskRepo.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Task task = optionalTask.get();
        Tag tag = new Tag(dto.getName(), dto.getColor(), workspace);
        Tag savedTag = tagRepo.save(tag);
        TaskTag taskTag = new TaskTag(task, savedTag);
        taskTagRepo.save(taskTag);
        createTaskHistory(task, "Tag", null, tag.getName(), "Tag added");
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse removeTag(UUID tagId, UUID taskId) {
        if (taskTagRepo.existsByTagIdAndTaskId(tagId, taskId)) {
            taskTagRepo.deleteByTagIdAndTaskId(tagId, taskId);
            createTaskHistory(taskRepo.getById(taskId), "Tag", tagRepo.getById(tagId).getName(), null, "Tag removed");
            return new ApiResponse("Removed", true);
        }
        return new ApiResponse("Not found", false);
    }

    @Override
    public ApiResponse assignUser(UUID taskId, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        Optional<Task> optionalTask = taskRepo.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        TaskUser taskUser = new TaskUser(optionalTask.get(), optionalUser.get());
        taskUserRepo.save(taskUser);
        createTaskHistory(optionalTask.get(), "User", null, optionalUser.get().getUsername(),
                "User assigned");
        return new ApiResponse("Assigned", true);
    }

    @Override
    public ApiResponse removeUser(UUID taskId, UUID userId) {
        if (taskUserRepo.existsByUserIdAndTaskId(userId, taskId)) {
            taskUserRepo.deleteByUserIdAndTaskId(userId, taskId);
            createTaskHistory(taskRepo.getById(taskId), "User", userRepository.getById(userId).getUsername(),
                    null,"User removed");
            return new ApiResponse("Removed", true);
        }
        return new ApiResponse("Not found", false);
    }

    private void createTaskHistory(Task task, String changeField, String before, String after, String data) {
        TaskHistory taskHistory = new TaskHistory(task, changeField, before, after, data);
        taskHistoryRepo.save(taskHistory);
    }
}
