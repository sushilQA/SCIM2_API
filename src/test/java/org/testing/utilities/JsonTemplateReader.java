package org.testing.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonTemplateReader {

	// Single token replacement - short syntax
	public static String getJsonWithReplacedToken(String filePath, String token, String value) throws IOException {
		try {
			String content = Files.readString(Paths.get(filePath));
			return content.replace(token, value);

		} catch (IOException e) {
			System.out.println("getJsonWithReplacedToken failed for file '" + filePath + "': " + e.getMessage());
			throw e;
		}
	}

	// Multiple token replacement - Map based
	public static String getJsonWithReplacedTokens(String filePath, Map<String, String> tokenValues)
			throws IOException {
		try {
			String content = Files.readString(Paths.get(filePath));

			for (Map.Entry<String, String> entry : tokenValues.entrySet()) {
				content = content.replace(entry.getKey(), entry.getValue());
			}

			return content;

		} catch (IOException e) {
			System.out.println("getJsonWithReplacedTokens failed for file '" + filePath + "': " + e.getMessage());
			throw e;
		}
	}
	
	public static String getJsonBody(String filePath) throws IOException {
		try {
			String content = Files.readString(Paths.get(filePath));
			return content;

		} catch (IOException e) {
			System.out.println("getJsonWithReplacedToken failed for file '" + filePath + "': " + e.getMessage());
			throw e;
		}
	}

}