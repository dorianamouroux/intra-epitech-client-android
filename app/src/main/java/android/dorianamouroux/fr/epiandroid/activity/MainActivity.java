package android.dorianamouroux.fr.epiandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import android.dorianamouroux.fr.epiandroid.HomeActivity;
import android.dorianamouroux.fr.epiandroid.IntraAPI;
import android.dorianamouroux.fr.epiandroid.R;

import org.json.*;
import com.loopj.android.http.*;


import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    protected EditText _login;
    protected EditText _password;
    protected CheckBox _ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _ch=(CheckBox)findViewById(R.id.checkBox);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void afterConnexion() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        if (_ch.isChecked())
        {
            Log.v("OK", "c'est cool");
        }
        startActivity(intent);
        this.finish();
    }

    public void logIn(View view) {
        this._login = (EditText)findViewById(R.id.login);
        this._password = (EditText)findViewById(R.id.password);

        IntraAPI.login(
                this._login.getText().toString(),
                this._password.getText().toString(),
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Object token = response.get("token");
                            IntraAPI.setToken(token.toString());
                            afterConnexion();
                        } catch (JSONException e) {
                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_SHORT;
                            CharSequence text = "Unknow error";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Context context = getApplicationContext();
                        CharSequence text = "Error with login (" + errorResponse.toString() + ")";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
    }

}

//public class LoginPreferences {
//    public static String USER_PREFS = "UserPrefs";
//    public static String USER_LOGEDIN = "user_logedin";
//    public static String USER_LOGIN = "user_login";
//    public static String USER_PASSWD = "user_passwd";
//
//    public static boolean isUserLogedIn(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
//        return sharedPreferences.getBoolean(USER_LOGEDIN, false);
//    }
//
//    public static String getLogin(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
//        return sharedPreferences.getString(USER_LOGIN, null);
//    }
//
//    public static String getPassword(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
//        return sharedPreferences.getString(USER_PASSWD, null);
//    }
//
//    public static void loginInfo(Context context, String login, String pwd) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, 0).edit();
//        editor.putString(USER_LOGIN, login);
//        editor.putString(USER_PASSWD, pwd);
//        editor.putBoolean(USER_LOGEDIN, true);
//        editor.apply();
//    }
//
//    public static void logout(Context context) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, 0).edit();
//        editor.remove(USER_LOGIN);
//        editor.remove(USER_PASSWD);
//        editor.remove(USER_LOGEDIN);
//        editor.apply();
//    }
//}
