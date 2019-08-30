package com.mathtutio.lambda.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Students")
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@DynamoDBHashKey(attributeName = "UserName")
	private String userName;

	@DynamoDBAttribute(attributeName = "Name")
	private String name;

	@DynamoDBAttribute(attributeName = "Email")
	private String email;

	@DynamoDBAttribute(attributeName = "Mobile")
	private String mobile;

	public Student() {
		super();
	}

	public Student(String userName, String name, String email, String mobile) {
		super();
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Student [userName=" + userName + ", name=" + name + ", email=" + email + ", mobile=" + mobile + "]";
	}

}
