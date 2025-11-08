package ita.growin.domain.task.validator;

import ita.growin.domain.event.entity.Event;
import ita.growin.domain.event.repository.EventRepository;
import ita.growin.domain.task.dto.request.CreateTaskReqDto;
import ita.growin.domain.task.dto.request.UpdateTaskRequestDto;
import ita.growin.domain.task.enums.TaskType;
import ita.growin.domain.task.service.UserProvider;
import ita.growin.global.exception.BusinessException;
import ita.growin.global.exception.EventException;
import ita.growin.global.exception.TaskException;
import ita.growin.global.exception.errorcode.EventErrorCode;
import ita.growin.global.exception.errorcode.TaskErrorCode;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {

    private final EventRepository eventRepository;
    private final UserProvider userProvider;

    public TaskValidator(EventRepository eventRepository,
                         UserProvider userProvider) {
        this.eventRepository = eventRepository;
        this.userProvider = userProvider;
    }

    public void createValidate(CreateTaskReqDto req) {
        // 제목 비었을 시 예외 throw
        if (req.title() == null)
            throw new TaskException(TaskErrorCode.INVALID_FIELD);

        switch (req.taskType()) {
            case IN_EVENT -> {
                if(req.eventId() == null)
                    throw new TaskException(TaskErrorCode.INVALID_FIELD);

                Event event = eventRepository
                        .findById(req.eventId())
                        .orElseThrow(() -> new TaskException(EventErrorCode.EVENT_NOT_FOUND));

                // 본인 확인
                if (!userProvider.currentUser().equals(event.getUser()))
                    throw new BusinessException(EventErrorCode.EVENT_NOT_OWNER);
            }
            case SCHEDULED -> {
                if(req.repeatType() == null || req.endDate() == null)
                    throw new TaskException(TaskErrorCode.INVALID_FIELD);
            }
            case UNSCHEDULED -> {}
            default -> throw new TaskException(TaskErrorCode.INVALID_FIELD);
        }
    }

    public void updateValidate(UpdateTaskRequestDto req) {
        if (req.title() == null)
            throw new TaskException(TaskErrorCode.INVALID_FIELD);

        if (req.taskType() == null)
            throw new TaskException(TaskErrorCode.INVALID_FIELD);

        if (req.taskType() == TaskType.SCHEDULED) {
            if (req.repeatType() == null || req.startDate() == null || req.endDate() == null)
                throw new TaskException(TaskErrorCode.INVALID_FIELD);
        }
    }

    public void deleteValidate(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new TaskException(EventErrorCode.EVENT_NOT_FOUND));

        if(!userProvider.currentUser().equals(event.getUser()))
            throw new EventException(EventErrorCode.EVENT_NOT_OWNER);
    }

    public void getTasksValidate(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new TaskException(EventErrorCode.EVENT_NOT_FOUND));
    }

    public void getTasksValidate() {
        
    }
}
