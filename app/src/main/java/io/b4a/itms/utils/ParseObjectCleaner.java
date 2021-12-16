package io.b4a.itms.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseQuery;

import io.b4a.itms.data.parse_model.Message;

public class ParseObjectCleaner {

    private static final String TAG = ParseObjectCleaner.class.getSimpleName();

    static ParseQuery<Message> messageQuery = ParseQuery.getQuery(Message.class);

    public static void deleteAllMessage(final Context context) {
        // The query will search for a ParseObject, given its objectId.
        // When the query finishes running, it will invoke the GetCallback
        // with either the object, or the exception thrown
        messageQuery.findInBackground((messages, e) -> {
            if (e == null) {
                for (final Message m : messages) {
                    final String objectId = m.getObjectId();
                    String textSuccess = "Delete Message " + objectId + " Successful";
                    m.deleteInBackground(e1 -> {
                        if (e1 == null) {
                            Log.d(TAG, textSuccess);
                            Toast.makeText(context, textSuccess, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error: " + e1.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                // something went wrong
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void deleteMessage(final Context context, final String objectId) {
        String textSuccess = "Delete Message " + objectId + " Successful";
        // Retrieve the object by id
        messageQuery.getInBackground(objectId, (object, e) -> {
            if (e == null) {
                //Object was fetched
                //Deletes the fetched ParseObject from the database
                object.deleteInBackground(e2 -> {
                    if (e2 == null) {
                        Toast.makeText(context, textSuccess, Toast.LENGTH_SHORT).show();
                    } else {
                        //Something went wrong while deleting the Object
                        Toast.makeText(context, "Error: " + e2.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                //Something went wrong
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

