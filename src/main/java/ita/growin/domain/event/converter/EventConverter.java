package ita.growin.domain.event.converter;

import ita.growin.domain.event.dto.request.EventReqDto;
import ita.growin.domain.event.dto.response.EventDetailResDto;
import ita.growin.domain.event.dto.response.EventResDto;
import ita.growin.domain.event.entity.Event;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventConverter {

	public Event toEntity(EventReqDto request) {
		return Event.builder()
			.title(request.title())
			.allDay(request.allDay())
			.startDate(request.startDate())
			.endDate(request.endDate())
			.startDay(request.startDay())
			.endDay(request.endDay())
			.startTime(request.startTime())
			.endTime(request.endTime())
			.repeatType(request.repeatType())
			.repeatCount(request.repeatCount())
			.repeatEndDate(request.repeatEndDate())
			.build();
	}

	public EventDetailResDto toEventDetailResponse(Event event) {
		return EventDetailResDto.builder()
			.id(event.getId())
			.title(event.getTitle())
			.startDate(event.getStartDate())
			.endDate(event.getEndDate())
			.startDay(event.getStartDay())
			.endDay(event.getEndDay())
			.startTime(event.getStartTime())
			.endTime(event.getEndTime())
			.repeatType(event.getRepeatType())
			.repeatCount(event.getRepeatCount())
			.repeatEndDate(event.getRepeatEndDate())
			.build();
	}

	public static EventResDto toResponse(Event event) {
		return EventResDto.builder()
			.eventId(event.getId())
			.title(event.getTitle())
			.build();
	}
}
