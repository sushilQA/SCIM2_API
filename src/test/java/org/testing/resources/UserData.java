package org.testing.resources;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserData {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String EXTENSION = "urn:aehsc:scim:api:extension:2.0:UserExtension";

	private String id;
	private String userName;
	private String password;      // used for login/token only, never sent in the payload
	private Boolean active = true;
	private String title;
	private String email;
	private String givenName;
	private String familyName;
	private String middleName;

	// Generic map so ANY customProperties/extension attribute can be set:
	// locationCode, employmentStatus, userType, workLocation, etc.
	private final Map<String, Object> customProperties = new LinkedHashMap<>();

	// default user, so each test only changes what it needs (used for CREATE tests - no existing data to copy from)
	public static UserData sample(String userName) {
		UserData d = new UserData();
		d.id = userName;          // override with setId(...) if your server returns a different id
		d.userName = userName;
		d.title = "AT00063126";
		d.email = userName + "@yopmail.com";
		d.givenName = userName;
		d.familyName = userName;
		d.middleName = userName;
		d.customProperties.put("locationCode", "90015");
		return d;
	}

	// Builds a UserData from a real fetched user (used for UPDATE tests - starts from actual
	// current state, so fields you don't explicitly change stay exactly as they were on the server)
	public static UserData fromExisting(JsonNode userData) {
		UserData d = new UserData();
		d.id = textOrNull(userData, "id");
		d.userName = textOrNull(userData, "userName");
		d.title = textOrNull(userData, "title");
		d.active = userData.has("active") ? userData.get("active").asBoolean() : true;

		JsonNode emails = userData.get("emails");
		if (emails != null && emails.isArray() && emails.size() > 0) {
			d.email = textOrNull(emails.get(0), "value");
		}

		JsonNode name = userData.get("name");
		if (name != null) {
			d.givenName = textOrNull(name, "givenName");
			d.familyName = textOrNull(name, "familyName");
			d.middleName = textOrNull(name, "middleName");
		}

		JsonNode userExtension = userData.get(EXTENSION);
		if (userExtension != null) {
			d.loadCustomPropertiesFrom(userExtension);
		}

		return d;
	}

	private static String textOrNull(JsonNode node, String field) {
		return (node != null && node.has(field)) ? node.get(field).asText() : null;
	}

	// Loads customProperties from an existing user's response (e.g. after a GET),
	// so an update test can start from the real current state and only change what it needs.
	public void loadCustomPropertiesFrom(JsonNode userExtensionNode) {
		JsonNode cp = userExtensionNode.get("customProperties");
		if (cp != null) {
			cp.fields().forEachRemaining(entry -> customProperties.put(entry.getKey(), entry.getValue().asText()));
		}
	}

	// Set (or overwrite) a single customProperties attribute, by name
	public void setCustomProperty(String key, Object value) {
		customProperties.put(key, value);
	}

	public Object getCustomProperty(String key) {
		return customProperties.get(key);
	}

	// Captures every current attribute (including all customProperties) as a flat, ordered map,
	// used for the Before/After snapshot comparison in ApiValidation.logAttributeChanges(...)
	public Map<String, String> snapshot() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("id", id);
		map.put("userName", userName);
		map.put("active", active == null ? null : active.toString());
		map.put("title", title);
		map.put("email", email);
		map.put("name.givenName", givenName);
		map.put("name.familyName", familyName);
		map.put("name.middleName", middleName);
		for (Map.Entry<String, Object> entry : customProperties.entrySet()) {
			map.put("extension." + entry.getKey(),
					entry.getValue() == null ? null : String.valueOf(entry.getValue()));
		}
		return map;
	}

	// builds the SCIM payload; null fields are left out
	public ObjectNode toPayload() {
		ObjectNode root = MAPPER.createObjectNode();
		if (active != null) root.put("active", active);
		put(root, "title", title);
		put(root, "userName", userName);
		put(root, "id", id);

		if (email != null) {
			ObjectNode e = root.putArray("emails").addObject();
			e.put("type", "work");
			e.put("value", email);
			e.put("primary", true);
		}

		ObjectNode name = MAPPER.createObjectNode();
		put(name, "givenName", givenName);
		put(name, "familyName", familyName);
		put(name, "middleName", middleName);
		if (name.size() > 0) root.set("name", name);

		if (!customProperties.isEmpty()) {
			ObjectNode cp = root.putObject(EXTENSION).putObject("customProperties");
			for (Map.Entry<String, Object> entry : customProperties.entrySet()) {
				cp.put(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return root;
	}

	public String toJson() {
		return toPayload().toString();
	}

	private static void put(ObjectNode node, String key, String value) {
		if (value != null) node.put(key, value);
	}

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public Boolean getActive() { return active; }
	public void setActive(Boolean active) { this.active = active; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getGivenName() { return givenName; }
	public void setGivenName(String givenName) { this.givenName = givenName; }

	public String getFamilyName() { return familyName; }
	public void setFamilyName(String familyName) { this.familyName = familyName; }

	public String getMiddleName() { return middleName; }
	public void setMiddleName(String middleName) { this.middleName = middleName; }

	// Kept for backward compatibility with existing calls to setLocationCode/getLocationCode
	public String getLocationCode() {
		Object v = customProperties.get("locationCode");
		return v == null ? null : String.valueOf(v);
	}
	public void setLocationCode(String locationCode) {
		customProperties.put("locationCode", locationCode);
	}
}