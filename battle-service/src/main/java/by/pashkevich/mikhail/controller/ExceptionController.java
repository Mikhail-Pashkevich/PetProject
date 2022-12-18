package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.exception.*;
import by.pashkevich.mikhail.model.dto.ResponseExceptionDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = NotFoundException.class)
    private ResponseExceptionDto handleNotFoundException(NotFoundException e) {
        return new ResponseExceptionDto(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseExceptionDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";\n"));

        return new ResponseExceptionDto(message);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(value = {AccessException.class, ForbiddenException.class})
    private ResponseExceptionDto handleForbiddenException(Exception e) {
        return new ResponseExceptionDto(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = {IncorrectDataException.class, DuplicateException.class, BattleUnavailableException.class})
    private ResponseExceptionDto handleException(Exception e) {
        return new ResponseExceptionDto(e.getMessage());
    }
}
