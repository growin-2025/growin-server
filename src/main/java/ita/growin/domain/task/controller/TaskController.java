//package ita.growin.domain.task.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import ita.growin.domain.task.dto.CreateTaskRequestDto;
//import ita.growin.domain.task.dto.UpdateTaskRequestDto;
//import ita.growin.domain.task.service.TaskService;
//import ita.growin.global.response.APIResponse;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Slice;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.lang.Nullable;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/tasks")
//@Tag(name = "할 일", description = "할 일 관련 API")
//public class TaskController {
//
//    private final TaskService taskService;
//
//    public TaskController(TaskService taskService) {
//        this.taskService = taskService;
//    }
//
//    @PostMapping
//    @Operation(summary = "할 일 생성 API")
//    public APIResponse<?> createTask(
//            @RequestParam("eventId") @Nullable Long eventId,
//            @RequestBody CreateTaskRequestDto req) {
//        taskService.createTask(eventId, req);
//        return APIResponse.success();
//    }
//
//    @PatchMapping("/{taskId}")
//    @Operation(summary = "할 일 수정 API")
//    public APIResponse<?> updateTask(
//            @PathVariable Long taskId,
//            @RequestBody UpdateTaskRequestDto req) {
//        taskService.updateTask(taskId, req);
//        return APIResponse.success();
//    }
//
//    @DeleteMapping("/{taskId}")
//    @Operation(summary = "할 일 삭제 API")
//    public APIResponse<?> deleteTask(
//            @RequestParam("eventId") @Nullable Long eventId,
//            @PathVariable Long taskId) {
//        taskService.deleteTask(eventId, taskId);
//        return APIResponse.success();
//    }
//
//    @GetMapping("/{taskId}")
//    @Operation(summary = "할 일 단건 조회")
//    public APIResponse<?> getTask(@PathVariable Long taskId) {
//        return APIResponse.success(taskService.getTask(taskId));
//    }
//
//    @GetMapping("/event/{eventId}/tasks")
//    @Operation(summary = "일정 내 할 일 조회")
//    public APIResponse<Slice<?>> getTasks(
//            @PathVariable Long eventId,
//            @PageableDefault(size = 20) Pageable pageable) {
//        return APIResponse.success(taskService.getTasks(eventId, pageable));
//    }
//
//    @GetMapping("/tasks/today")
//    @Operation(summary = "오늘 할 일 조회")
//    public APIResponse<?> getTodayTasks(
//            @PageableDefault(size = 20) Pageable pageable
//    ) {
//        return APIResponse.success(taskService.getTodayTasks(pageable));
//    }
//
//    @GetMapping("/tasks/someday")
//    @Operation(summary = "언젠가 할 일 조회")
//    public APIResponse<?> getSomedayTasks(
//            @PageableDefault(size = 20) Pageable pageable
//    ) {
//        return APIResponse.success(taskService.getSomedayTasks(pageable));
//    }
//}
