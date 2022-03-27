package com.antonio.Api.resources.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.antonio.Api.services.exceptions.BankAccountNotFoundException;
import com.antonio.Api.services.exceptions.CardNotFoundException;
import com.antonio.Api.services.exceptions.CardTypeNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(BankAccountNotFoundException.class)
	public ResponseEntity<StandardError> BankAccountNotFound(BankAccountNotFoundException e,
			HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(CardNotFoundException.class)
	public ResponseEntity<StandardError> CardNotFound(CardNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(CardTypeNotFoundException.class)
	public ResponseEntity<StandardError> CardTypeNotFound(CardTypeNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> MethodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError("Constraint Exception");
		err.setPath(request.getRequestURI());
		int index = e.getMessage().indexOf("Description:");
		int indexEnd = e.getMessage().lastIndexOf(".");
		err.setMessage(e.getMessage().substring(index, indexEnd + 1));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	protected ResponseEntity<Object> handlePersistenceException(final DataIntegrityViolationException e,
			HttpServletRequest request) {

		Throwable cause = e.getRootCause();

		if (cause instanceof SQLIntegrityConstraintViolationException) {

			SQLIntegrityConstraintViolationException constraintException = (SQLIntegrityConstraintViolationException) cause;

			StandardError err = new StandardError();
			err.setTimestamp(Instant.now());
			err.setStatus(HttpStatus.CONFLICT.value());
			err.setError("Conflict Exception");
			err.setPath(request.getRequestURI());
			err.setMessage(constraintException.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(err);

		}

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		err.setError("Not Acceptable Exception");
		err.setPath(request.getRequestURI());
		err.setMessage(e.getLocalizedMessage());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);

	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<StandardError> NoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> HttpMessageNotReadableException(HttpMessageNotReadableException e,
			HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);

	}

}
