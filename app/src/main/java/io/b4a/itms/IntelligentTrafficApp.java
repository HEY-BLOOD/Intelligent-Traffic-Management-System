package io.b4a.itms;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import io.b4a.itms.data.parse_model.Message;

public class IntelligentTrafficApp extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // register customized parse class
        ParseObject.registerSubclass(Message.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.parse_app_id))
                // if defined
                .clientKey(getString(R.string.parse_client_key))
                .server(getString(R.string.parse_server_url))
                .build()
        );
    }

}