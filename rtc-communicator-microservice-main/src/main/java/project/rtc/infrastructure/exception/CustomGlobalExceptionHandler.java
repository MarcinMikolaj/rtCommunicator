package project.rtc.infrastructure.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import project.rtc.infrastructure.exception.exceptions.*;
import project.rtc.infrastructure.dto.ApiExceptionDto;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = prepareResponseMap(ex, status);
        ex.printStackTrace();
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> exception(Exception e, WebRequest requestMetadata){
        e.printStackTrace();
        return new ResponseEntity<>(prepareExceptionDto(e, HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(e.getMessage())
                , requestMetadata.getDescription(false), ((ServletWebRequest) requestMetadata).getHttpMethod().toString()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e, WebRequest requestMetadata) {
        e.printStackTrace();
        return new ResponseEntity<>(prepareExceptionDto(e, HttpStatus.UNAUTHORIZED, Collections.singletonList(e.getMessage())
                , requestMetadata.getDescription(false), ((ServletWebRequest) requestMetadata).getHttpMethod().toString()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e, WebRequest requestMetadata){
        e.printStackTrace();
        return new ResponseEntity<>(prepareExceptionDto(e, HttpStatus.NOT_FOUND, Collections.singletonList(e.getMessage())
                , requestMetadata.getDescription(false), ((ServletWebRequest) requestMetadata).getHttpMethod().toString()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleRoomNotFoundException(RoomNotFoundException e, WebRequest requestMetadata){
        e.printStackTrace();
        return new ResponseEntity<>(prepareExceptionDto(e, HttpStatus.NOT_FOUND, Collections.singletonList(e.getMessage())
                , requestMetadata.getDescription(false), ((ServletWebRequest) requestMetadata).getHttpMethod().toString()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleMessageNotFoundException(RoomNotFoundException e, WebRequest requestMetadata){
        e.printStackTrace();
        return new ResponseEntity<>(prepareExceptionDto(e, HttpStatus.NOT_FOUND, Collections.singletonList(e.getMessage())
                , requestMetadata.getDescription(false), ((ServletWebRequest) requestMetadata).getHttpMethod().toString()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAuthorizationTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleNoAuthorizationTokenException(NoAuthorizationTokenException e, WebRequest requestMetadata){
        e.printStackTrace();
        return new ResponseEntity<>(prepareExceptionDto(e, HttpStatus.UNAUTHORIZED, Collections.singletonList(e.getMessage())
                , requestMetadata.getDescription(false), ((ServletWebRequest) requestMetadata).getHttpMethod().toString()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException e, WebRequest requestMetadata){
        e.printStackTrace();
        return new ResponseEntity<>(prepareExceptionDto(e, HttpStatus.UNAUTHORIZED, Collections.singletonList(e.getMessage())
                , requestMetadata.getDescription(false), ((ServletWebRequest) requestMetadata).getHttpMethod().toString()),
                HttpStatus.BAD_REQUEST);
    }

    private Map prepareResponseMap(Exception e, HttpStatus httpStatus){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", httpStatus.value());

        List<String> errors = new ArrayList<>();
        if(e instanceof MethodArgumentNotValidException){
            errors = ((MethodArgumentNotValidException) e).getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
        }else
            errors.add(e.getMessage());
        body.put("errors", errors);
        body.put("stackTrace", e.getStackTrace());
        return body;
    }

    private ApiExceptionDto prepareExceptionDto(Exception e, HttpStatus httpStatus, List<String> messages,
                                                String path, String method){
        return ApiExceptionDto.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .messages(messages)
                .path(path)
                .method(method)
                .build();
    }
}
