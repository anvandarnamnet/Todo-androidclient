package DataHandeler;

import android.content.Context;
import android.content.SharedPreferences;

import com.auth0.android.result.Credentials;
import com.todo.rahle.todo_app.R;

import DataHandeler.Constants;

/**
 * Created by rahle on 2016-12-21.
 * This class handels user specifik data such as the user id-token.
 * This class is used to handle the automatic sign in.
 */

public class CredentialsManager {

    // save the user specifik data
    public static void saveCredentials(Context context, Credentials credentials){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.auth0_preferences), Context.MODE_PRIVATE);

        sharedPref.edit()
                .putString(Constants.ID_TOKEN, credentials.getIdToken())
                .putString(Constants.REFRESH_TOKEN, credentials.getRefreshToken())
                .putString(Constants.ACCESS_TOKEN, credentials.getAccessToken())
                .putString(Constants.CREDENTIAL_TYPE, credentials.getType())
                .commit();
    }

    // get the user specifik data
    public static Credentials getCredentials(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.auth0_preferences), Context.MODE_PRIVATE);

        Credentials credentials = new Credentials(
                sharedPref.getString(Constants.ID_TOKEN, null),
                sharedPref.getString(Constants.ACCESS_TOKEN, null),
                sharedPref.getString(Constants.CREDENTIAL_TYPE, null),
                sharedPref.getString(Constants.REFRESH_TOKEN, null));

        return credentials;
    }

    // delete user specifik data
    public static void deleteCredentials(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.auth0_preferences), Context.MODE_PRIVATE);

        sharedPref.edit()
                .putString(Constants.ID_TOKEN, null)
                .putString(Constants.REFRESH_TOKEN, null)
                .putString(Constants.ACCESS_TOKEN, null)
                .putString(Constants.CREDENTIAL_TYPE, null)
                .commit();
    }
}
