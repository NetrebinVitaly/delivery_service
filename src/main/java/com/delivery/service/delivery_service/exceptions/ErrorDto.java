package com.delivery.service.delivery_service.exceptions;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto extends ResponseEntityExceptionHandler {
    private String errors;

    @JsonProperty("error_description")
    private String errorDescription;
}
