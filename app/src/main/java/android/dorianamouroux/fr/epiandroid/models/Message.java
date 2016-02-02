package android.dorianamouroux.fr.epiandroid.models;

import android.text.Html;
import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by dorianamouroux on 2/2/16.
 */
public class Message {
    private String _title;
    private String _content;

    public Message(Object message) {
        JSONObject jsonMessage = (JSONObject) message;
        try {
            this._title = jsonMessage.getString("title");
            this._content = jsonMessage.getString("content");
        } catch (Exception e) {
            Log.d("ERROR", "UNKNOWN ERROR");
        }
    }
    public String getTitle() {
        return (this._title);
    }

    public String getContent() {
        return (this._content);
    }
}
