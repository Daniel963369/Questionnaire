package com.example.questionnaire.constants;

public enum RtnCode {
	
	SUCCESSFUL(200,"successful"),//
	
	QUESTIONNAIRE_PARAM_ERROR(400," Questionnaire param error!"),//
	QUESTION_PARAM_ERROR(400," Question param error!"),//
	QUESTIONNAIRE_ID_PARAM_ERROR(400," Question param error!"),//
	QUESTION_ID_PARAM_ERROR(400," Question param error!"),//
	QUESTIONNAIRE_ID_NOT_FOUND(404," Questionnaire id not found!"),//
	UPDATE_ERROR(400," update error!"),//
	;
	
	private int code;
	
	private String message;
	
	
	
	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}





	
}
