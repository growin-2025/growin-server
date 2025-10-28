package ita.growin.domain.event.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ita.growin.domain.event.dto.request.EventReqDto;
import ita.growin.domain.event.dto.response.EventDetailResDto;
import ita.growin.domain.event.dto.response.EventResDto;
import ita.growin.domain.event.service.EventService;
import ita.growin.global.response.APIResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "일정", description = "일정 관련 API")
public class EventController {

	private final EventService eventService;

	@PostMapping
	@Operation(summary = "일정 생성", description = "새로운 일정을 생성합니다.")
	public APIResponse<EventResDto> createEvent(@Valid  @RequestBody EventReqDto request) {
		EventResDto response = eventService.createEvent(request);
		return APIResponse.success(response);
	}

	@GetMapping("/{eventId}")
	@Operation(summary = "일정 상세 조회", description = "일정 ID로 상세 일정을 조회합니다.")
	public APIResponse<EventDetailResDto> getEvent(@PathVariable long eventId) {
		EventDetailResDto response = eventService.getEventDetail(eventId);
		return APIResponse.success(response);
	}
}