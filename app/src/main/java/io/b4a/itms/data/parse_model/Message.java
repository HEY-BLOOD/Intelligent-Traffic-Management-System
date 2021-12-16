package io.b4a.itms.data.parse_model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Message")
public class Message extends ParseObject {

    private final static String KEY_SENDER = "sender";
    private final static String KEY_BODY = "body";

    public ParseUser getSender() {
        return getParseUser(KEY_SENDER);
    }

    public String getBody() {
        return getString(KEY_BODY);
    }

    public void setSender(ParseUser user) {
        put(KEY_SENDER, user);
    }

    public void setBody(String body) {
        put(KEY_BODY, body);
    }
}