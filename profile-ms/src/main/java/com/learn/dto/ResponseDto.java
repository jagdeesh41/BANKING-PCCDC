package com.learn.dto;

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
        name = "Response" ,
        description = "Schema  to hold response information"

)
public class ResponseDto {
    @Schema(
            description = "Status code in the response"
    )
    private String statusCode;
    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;


}
