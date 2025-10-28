package ita.growin.domain.event.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "일정 생성 응답 DTO")
public record EventResDto(
	@Schema(description = "생성된 일정 ID", example = "1")
	Long eventId,

	@Schema(description = "일정명", example = "팀 회의")
	String title
) {
}
