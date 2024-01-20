package com.delivery.service.delivery_service.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ExceptionController implements ErrorController {
    private static final String PATH = "/errors";

    public String getErrorPath() {
        return PATH;
    }

    private final ErrorAttributes errorAttributes;

    @RequestMapping(ExceptionController.PATH)
    public ResponseEntity<ErrorDto> errorResponseEntity(WebRequest req) {

        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                req,
                ErrorAttributeOptions.of(
                        ErrorAttributeOptions.Include.EXCEPTION,
                        ErrorAttributeOptions.Include.MESSAGE
                )
        );

        return ResponseEntity
                .status((Integer) attributes.get("status"))
                .body(ErrorDto
                        .builder()
                        .errors((String) attributes.get("error"))
                        .errorDescription((String) attributes.get("message"))
                        .build());
    }
}
