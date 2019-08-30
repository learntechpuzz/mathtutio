package com.mathtutio.lambda.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Courses")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	@DynamoDBHashKey(attributeName = "CourseID")
	private int courseId;

	@DynamoDBAttribute(attributeName = "Title")
	private String title;

	@DynamoDBAttribute(attributeName = "Summary")
	private String summary;

	@DynamoDBAttribute(attributeName = "Logo_FileName")
	private String logoFileName;

	@DynamoDBAttribute(attributeName = "About")
	private String about;

	@DynamoDBAttribute(attributeName = "CourseContents_FileName")
	private String courseContentsFileName;

	public Course() {
		super();
	}

	public Course(int courseId, String title, String summary, String logoFileName, String about,
			String courseContentsFileName) {
		super();
		this.courseId = courseId;
		this.title = title;
		this.summary = summary;
		this.logoFileName = logoFileName;
		this.about = about;
		this.courseContentsFileName = courseContentsFileName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getCourseContentsFileName() {
		return courseContentsFileName;
	}

	public void setCourseContentsFileName(String courseContentsFileName) {
		this.courseContentsFileName = courseContentsFileName;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", title=" + title + ", summary=" + summary + ", logoFileName="
				+ logoFileName + ", about=" + about + ", courseContentsFileName=" + courseContentsFileName + "]";
	}

}
