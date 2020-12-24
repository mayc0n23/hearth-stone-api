package com.sistemas.tema.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sistemas.tema.domain.exception.CartaNaoEncontradaException;
import com.sistemas.tema.domain.exception.ParametrosNaoPassadosException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o "
			+ "problema persistir, entre em contato com o administrador do sistema.";

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(CartaNaoEncontradaException.class)
	public ResponseEntity<?> handlerCartaNaoEncontradaException(CartaNaoEncontradaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ParametrosNaoPassadosException.class)
	public ResponseEntity<?> handlerParametrosInvalidosException(ParametrosNaoPassadosException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), status, headers, request);
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpStatus status, HttpHeaders headers, WebRequest request) {

		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			String name = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			return Problem.Object.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);

	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder().userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).title(status.getReasonPhrase())
					.status(status.value()).build();
		} else if (body instanceof String) {
			body = Problem.builder().userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).title((String) body)
					.status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().timestamp(OffsetDateTime.now()).status(status.value()).type(problemType.getUri())
				.title(problemType.getTitle()).detail(detail);
	}

}