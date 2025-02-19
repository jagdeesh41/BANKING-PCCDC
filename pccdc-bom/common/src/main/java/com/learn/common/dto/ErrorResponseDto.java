package com.learn.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(description = "API path invoked by client")
    private String apiPath;
    @Schema(description = "Error code representing the error happened")
    private String errorCode;
    @Schema(description = "Error message representing the error happened")
    private String errorMessage;
    @Schema(description = "Time representation when the error occurred")
    private String errorTimestamp;




}
