package com.msbookings.bookings.shared.exception;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.msbookings.bookings.infrastructure.rest.dto.ResponseDto;
import com.msbookings.bookings.shared.config.MenssageResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class BookingExceptionHandler {
	private final MenssageResponse menssageResponse;

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<ResponseDto> badRequest(MissingServletRequestParameterException e) {
		return new ResponseEntity<>(ResponseDto.builder()
				.code(MenssageResponse.BR400)
				.message(menssageResponse.getMessages().get(MenssageResponse.BR400))
				.build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ResponseDto> badRequest(HttpServletRequest request, ConstraintViolationException e) {

		return new ResponseEntity<>(ResponseDto.builder().code(MenssageResponse.BR400).message(
				e.getConstraintViolations().stream().map(c -> c.getMessage())
						.collect(Collectors.joining(MenssageResponse.SP)))
				.build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<ResponseDto> badRequest(HttpServletRequest request,
			MethodArgumentNotValidException e) {
		return new ResponseEntity<>(buidlResponseDto(e.getBindingResult().getFieldErrors()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<ResponseDto> notFound(HttpServletRequest request,
			NotFoundException e) {
		return new ResponseEntity<>(e.getResponseDto(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ ConflictException.class })
	public ResponseEntity<ResponseDto> conflict(HttpServletRequest request,
			ConflictException e) {
		return new ResponseEntity<>(e.getResponseDto(),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ResponseDto> fatalErrorUnexpectedException(HttpServletRequest request, Exception exception) {
		return new ResponseEntity<>(ResponseDto.builder()
				.code(MenssageResponse.E500)
				.message(menssageResponse.getMessages().get(MenssageResponse.E500))
				.build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseDto buidlResponseDto(List<FieldError> fieldErrors) {
		return ResponseDto.builder()
				.code(getCode(fieldErrors))
				.message(getMessage(fieldErrors))
				.build();
	}

	private String getCode(List<FieldError> fieldErrors) {
		if (fieldErrors.isEmpty()) {
			return MenssageResponse.BR400;
		}
		return fieldErrors.stream().map(field -> {
			if (Objects.nonNull(field.getDefaultMessage())) {
				return field.getDefaultMessage();
			} else {
				return null;
			}
		}).filter(mss -> mss != null && !mss.isEmpty()).collect(Collectors.joining(MenssageResponse.SP));
	}

	private String getMessage(List<FieldError> fieldErrors) {

		if (fieldErrors.isEmpty()) {
			return menssageResponse.getMessages().get(MenssageResponse.BR400);
		}
		return fieldErrors.stream().map(field -> {
			if (Objects.nonNull(field.getDefaultMessage())) {
				return menssageResponse.getMessages().get(field.getDefaultMessage());
			} else {
				return null;
			}
		}).filter(mss -> mss != null && !mss.isEmpty()).collect(Collectors.joining(MenssageResponse.SP));
	}
}
