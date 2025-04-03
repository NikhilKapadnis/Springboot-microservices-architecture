package edu.tus.winemanager.validation;

public enum ErrorMessages {
	EMPTY_FIELDS("One or more empty fields"),
	ALREADY_EXISTS("Wine with given name and year already exists"),
	INVALID_COUNTRY("Not accepting more wines from that country"),
	BAD_GRAPES("Type of grape not acceptable");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
