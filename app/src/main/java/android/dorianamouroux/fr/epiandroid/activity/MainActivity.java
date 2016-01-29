package android.dorianamouroux.fr.epiandroid;

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
                        }
                        catch (JSONException e) {
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


