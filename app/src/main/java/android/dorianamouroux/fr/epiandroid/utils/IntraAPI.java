package android.dorianamouroux.fr.epiandroid;

/**
 * Created by dorianamouroux on 1/20/16.
 */
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dorianamouroux on 1/20/16.
 */
public class IntraAPI {
    private static String _token;

    public static void setToken(String token) {
        IntraAPI._token = token;
    }

    public static void login(String login, String password, JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("login", login);
        params.put("password", password);

        client.post("https://epitech-api.herokuapp.com/login", params, callback);
    }

}
