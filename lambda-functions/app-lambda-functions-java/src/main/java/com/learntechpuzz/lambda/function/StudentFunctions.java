package com.mathtutio.lambda.function;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mathtutio.lambda.dynamodb.DynamoDBManager;
import com.mathtutio.lambda.model.Student;

public class StudentFunctions {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	public void createStudent(InputStream request, OutputStream response, Context context) {
		context.getLogger().log("\nCalling saveStudent function");

		JsonParser parser = new JsonParser();
		JsonObject inputObj = null;
		try {
			inputObj = parser.parse(IOUtils.toString(request, "UTF-8")).getAsJsonObject();
			context.getLogger().log("\ninputObj: " + inputObj);
			JsonObject userAttributes = null;
			if (inputObj.get("request") != null) {
				userAttributes = inputObj.get("request").getAsJsonObject().get("userAttributes").getAsJsonObject();
				context.getLogger().log("\nuserAttributes: " + userAttributes);
				mapper.save(new Student(inputObj.get("userName").getAsString(),
						userAttributes.get("name").getAsString(), userAttributes.get("email").getAsString(),
						userAttributes.get("phone_number").getAsString()));
			} else {
				context.getLogger().log("\nInvalid input");
			}
		} catch (Exception e) {
			System.err.println("saveStudent failed.");
			System.err.println(e.getMessage());
		}
		
		String output = getGson().toJson(inputObj);
		context.getLogger().log("\noutput: " + output);

		try {
			IOUtils.write(output, response, "UTF-8");
		} catch (final IOException e) {
			System.err.println("Error while writing response");
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
