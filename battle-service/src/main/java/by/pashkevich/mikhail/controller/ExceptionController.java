package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.model.dto.ResponseExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = NotFoundException.class)
    private ResponseExceptionDto handleNotFoundException(NotFoundException e) {
        return new ResponseExceptionDto(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = IncorrectDataException.class)
    private ResponseExceptionDto handleIncorrectDataException(IncorrectDataException e) {
        return new ResponseExceptionDto(e.getMessage());
    }
}
