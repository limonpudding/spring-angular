package ru.dfsystems.spring.tutorial.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Настройка обработки исключений и выдачи сообщений пользователю при различных ошибках.
 */
@ControllerAdvice
public class AppExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e){
        e.printStackTrace();
        logger.info("ОШИБКА: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, InvalidFormatException.class, HttpMessageNotReadableException.class, org.modelmapper.MappingException.class})
    public ResponseEntity<String> handler(Throwable e){
        e.printStackTrace();
        logger.info("ОШИБКА: " + e.getMessage());
        return new ResponseEntity<>("Попытка вставить некорректные данные в БД.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
