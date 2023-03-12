package model.persistence;

import org.json.JSONObject;

// Adapted from JsonSerializationDemo Repo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
