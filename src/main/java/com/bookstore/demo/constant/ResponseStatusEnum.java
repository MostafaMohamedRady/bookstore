package com.bookstore.demo.constant;

public enum ResponseStatusEnum {
	SUCCESS("Success"), FAILURE("Failure");

	private String value;

	ResponseStatusEnum(String value) {
		this.value = value;
	}

}
