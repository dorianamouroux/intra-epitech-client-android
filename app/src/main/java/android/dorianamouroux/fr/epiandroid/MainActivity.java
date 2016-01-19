package android.dorianamouroux.fr.epiandroid;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.Toast;

import org.json.*;
import com.loopj.android.http.*;


import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    protected EditText _login;
    protected EditText _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void logIn(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        this._login = (EditText)findViewById(R.id.login);
        this._password = (EditText)findViewById(R.id.password);
        params.put("login", this._login.getText().toString());
        params.put("password", this._password.getText().toString());
        Log.v("bitch", params.toString());
        client.post("https://epitech-api.herokuapp.com/login", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Context context = getApplicationContext();
                CharSequence text = "You have successfully logged in";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Context context = getApplicationContext();
                CharSequence text = "Error with login";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

}
