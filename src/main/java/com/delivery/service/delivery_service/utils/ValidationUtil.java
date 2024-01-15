package com.delivery.service.delivery_service.utils;

import com.delivery.service.delivery_service.exceptions.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class ValidationUtil {

    private Validator validator;

    public <T> void isValid(T req){
        if(req != null){
            Set<ConstraintViolation<T>> set = validator.validate(req);
            if(!set.isEmpty()){
                String result = set
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .reduce((str1, str2) -> str1 + ", " + str2).orElse(" ");
                log.info("The following parameters were entered incorrectly: {}", result );
                throw new BadRequestException("Validation exception");
            }
        }
    }
}
