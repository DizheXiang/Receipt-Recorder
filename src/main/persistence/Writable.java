package persistence;

import org.json.JSONObject;

// citation URL: https://github.com/stleary/JSON-java.git
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
