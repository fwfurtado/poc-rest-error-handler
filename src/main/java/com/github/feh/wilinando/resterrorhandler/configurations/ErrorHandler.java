package com.github.feh.wilinando.resterrorhandler.configurations;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        System.out.println(ex.getMessage());


        return handleExceptionInternal(ex, ex.getBindingResult().getAllErrors().stream().map(MessageMapper::new).collect(Collectors.toList()), headers, HttpStatus.PRECONDITION_FAILED, request);

    }


    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleBindException(ex, headers, status, request);
    }



    class MessageMapper {
        private final DefaultMessageSourceResolvable messageSourceResolvable;

        public MessageMapper(DefaultMessageSourceResolvable messageSourceResolvable) {
            this.messageSourceResolvable = messageSourceResolvable;
        }

        @Nullable
        public Object[] getArguments() {

            for (Object o: messageSourceResolvable.getArguments()) {
                System.out.println(o.getClass());
            }

            return messageSourceResolvable.getArguments();
        }

        @Nullable
        public String getDefaultMessage() {
            return messageSourceResolvable.getDefaultMessage();
        }
    }
}
