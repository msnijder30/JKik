package nl.marksnijder.jkik.message;

import java.util.HashMap;

import com.google.gson.JsonObject;

public interface Sendable {

	public JsonObject initSending();
	public JsonObject getJsonData();
	
}
