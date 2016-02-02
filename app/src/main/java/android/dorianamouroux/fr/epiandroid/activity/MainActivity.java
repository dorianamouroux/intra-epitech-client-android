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
        if (isSave(this)) {
            logIn(findViewById(android.R.id.content));
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _ch = (CheckBox)findViewById(R.id.checkBox);

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
        if (_ch.isChecked()) {
            saveUserInfo(this);
        }
        startActivity(intent);
        this.finish();
    }

    private void saveUserInfo(Context context) {
        String login = _login.getText().toString();
        String password = _password.getText().toString();
        SharedPreferences.Editor editor = context.getSharedPreferences(getString(
                R.string.preference_file_key), Context.MODE_PRIVATE).edit();
        editor.putString(getString(R.string.user_login), login);
        editor.putString(getString(R.string.user_password), password);
        editor.putBoolean(getString(R.string.user_has_save), true);
        editor.apply();
    }

    private boolean isSave(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(
                R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(getString(R.string.user_has_save), false);
    }

    public void logIn(View view) {
        final String login;
        final String password;

        if (isSave(this)) {
            SharedPreferences sharedPreferences = this.getSharedPreferences(getString(
                    R.string.preference_file_key), Context.MODE_PRIVATE);
            login = sharedPreferences.getString(getString(R.string.user_login), null);
            password = sharedPreferences.getString(getString(R.string.user_password), null);
        }
        else {
            this._login = (EditText)findViewById(R.id.login);
            this._password = (EditText)findViewById(R.id.password);
            login = this._login.getText().toString();
            password = this._password.getText().toString();
        }
        IntraAPI.login(
                login,
                password,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Object token = response.get("token");
                            IntraAPI.setToken(token.toString());
                            IntraAPI.setLogin(login);
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
