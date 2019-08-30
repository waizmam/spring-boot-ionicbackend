package com.waizmam.cursomc.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.waizmam.cursomc.services.exceptions.DataIntegrityException;
import com.waizmam.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e){
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e){
		
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", System.currentTimeMillis());		
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	/*
	 * Essa é outra forma mais rápida de verificar se o email já existe na base de dados, 
	 * serve tanto para validar uma requisição do tipo POST(Inserção) ou PUT (Atualização)
	 * Evitando alguns passos a mais do CLienteInsert, ClienteInsertValidator, ClienteUpdate e ClienteUpdateValidator
	 */
	/*@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegretyViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
	    ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),System.currentTimeMillis());
	    if(ex.getMessage().contains("EMAIL")){
	        error.setMsg("Erro de validação.");
	        error.addError("email","Email já existente!");
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}*/
	
}
