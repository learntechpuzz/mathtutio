package com.mathtutio.lambda.function;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mathtutio.lambda.dynamodb.DynamoDBManager;
import com.mathtutio.lambda.model.Course;
import com.mathtutio.lambda.model.CourseMaterial;

public class CourseFunctions {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	public void getAllCourses(InputStream request, OutputStream response, Context context) {
		context.getLogger().log("\nCalling getAllEventsHandler function");
		List<Course> courses = mapper.scan(Course.class, new DynamoDBScanExpression());
		context.getLogger().log("\ncourses:" + courses);
		output(courses, response, context);
	}

	public void findCourseByID(InputStream request, OutputStream response, Context context) {
		context.getLogger().log("\nCalling findCourseByID function");
		JsonParser parser = new JsonParser();
		JsonObject inputObj = null;
		try {
			inputObj = parser.parse(IOUtils.toString(request, "UTF-8")).getAsJsonObject();
			context.getLogger().log("\ninputObj: " + inputObj);
			int courseId = inputObj.get("courseId").getAsInt();
			context.getLogger().log("\ncourseId:" + courseId);
			Course course = mapper.load(Course.class, courseId);
			context.getLogger().log("\ncourse:" + course);
			output(Optional.ofNullable(course), response, context);

		} catch (Exception e) {
			System.err.println("saveStudent failed.");
			System.err.println(e.getMessage());
		}

	}

	public void findCourseMaterialsByCourseID(InputStream request, OutputStream response, Context context) {
		context.getLogger().log("\nCalling findCourseMaterialsByCourseID function");
		JsonParser parser = new JsonParser();
		JsonObject inputObj = null;
		try {
			inputObj = parser.parse(IOUtils.toString(request, "UTF-8")).getAsJsonObject();
			context.getLogger().log("\ninputObj: " + inputObj);
			String courseId = inputObj.get("courseId").getAsString();
			Map<String, AttributeValue> eav = new HashMap<>();
			eav.put(":courseId", new AttributeValue().withN(courseId));
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
					.withFilterExpression("CourseID = :courseId").withExpressionAttributeValues(eav);
			List<CourseMaterial> courseMaterials = mapper.scan(CourseMaterial.class, scanExpression);
			List<CourseMaterial> sortedCourseMaterials = new LinkedList<>();
			sortedCourseMaterials.addAll(courseMaterials);
			sortedCourseMaterials.sort((e1, e2) -> e1.getId() <= e2.getId() ? -1 : 1);
			context.getLogger().log("\nsortedCourseMaterials: " + sortedCourseMaterials);
			output(sortedCourseMaterials, response, context);

		} catch (Exception e) {
			System.err.println("saveStudent failed.");
			System.err.println(e.getMessage());
		}

	}

	public void findCourseMaterialsByCourseIDAndTag(InputStream request, OutputStream response, Context context) {
		context.getLogger().log("\nCalling findCourseMaterialsByCourseIDAndTag function");

		JsonParser parser = new JsonParser();
		JsonObject inputObj = null;
		try {
			inputObj = parser.parse(IOUtils.toString(request, "UTF-8")).getAsJsonObject();
			context.getLogger().log("\ninputObj: " + inputObj);
			String courseId = inputObj.get("body").getAsJsonObject().get("courseId").getAsString();
			String tag = inputObj.get("body").getAsJsonObject().get("tag").getAsString();
			Map<String, AttributeValue> eav = new HashMap<>();
			eav.put(":courseId", new AttributeValue().withN(courseId));
			eav.put(":tag", new AttributeValue().withS(tag));

			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
					.withFilterExpression("CourseID = :courseId and contains(Tag, :tag)")
					.withExpressionAttributeValues(eav);
			List<CourseMaterial> courseMaterials = mapper.scan(CourseMaterial.class, scanExpression);
			context.getLogger().log("\ncourseMaterials: " + courseMaterials);
			output(courseMaterials, response, context);

		} catch (Exception e) {
			System.err.println("saveStudent failed.");
			System.err.println(e.getMessage());
		}

	}

	protected Gson getGson() {
		return new GsonBuilder().setPrettyPrinting().create();
	}

	protected void output(Object out, OutputStream response, Context context) {
		String output = getGson().toJson(out);
		context.getLogger().log("\noutput: " + output);
		try {
			IOUtils.write(output, response, "UTF-8");
		} catch (final IOException e) {
			context.getLogger().log("\nError while writing response" + e.getMessage());
		}

	}

}
