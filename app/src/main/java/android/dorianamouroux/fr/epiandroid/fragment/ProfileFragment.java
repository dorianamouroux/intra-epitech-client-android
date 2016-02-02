package android.dorianamouroux.fr.epiandroid.fragment;

import android.dorianamouroux.fr.epiandroid.R;
import android.content.Context;
import android.dorianamouroux.fr.epiandroid.IntraAPI;
import android.dorianamouroux.fr.epiandroid.adapter.MessagesAdapter;
import android.dorianamouroux.fr.epiandroid.models.Message;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileFragment extends Fragment {

    private JSONObject _profile;
    private JSONArray _messages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // load profil info
        IntraAPI.getMyProfile(
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        _profile = response;
                        afterFetchProfile();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.v("bitch", errorResponse.toString());
                    }
                });

        // load messages
        IntraAPI.getMessages(
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        _messages = response;
                        afterFetchMessages();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.v("bitch", errorResponse.toString());
                    }
                });

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.profile_fragment, container, false));
    }

    public void errorException() {
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text = "Unknow error";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void afterFetchProfile() {
        TextView helloText;
        TextView logTime;
        ImageView imageView;

        try {
            // set text with firstname
            helloText = (TextView)getView().findViewById(R.id.helloText);
            helloText.setText("Hello again, " + _profile.getString("firstname"));

            // set image
            imageView = (ImageView)getView().findViewById(R.id.imageProfile);
            Picasso.with(getActivity().getApplicationContext())
                    .load(_profile.getString("picture"))
                    .into(imageView);

            // set log time
            logTime = (TextView)getView().findViewById(R.id.logTime);
            logTime.setText("Log time this week : " + _profile.getJSONObject("nsstat").getInt("active"));

        } catch (Exception e) {
            errorException();
        }
    }

    public ArrayList<Message> buildListMessage() {
        ArrayList<Message> listMessages = new ArrayList<>();

        for (int i = 0; i < _messages.length(); i++) {
            try {
                Message oneMessage = new Message(_messages.get(i));
                listMessages.add(oneMessage);
            } catch (Exception e) {
                errorException();
            }
        }
        return (listMessages);
    }

    public void afterFetchMessages() {
        // Construct the data source
        ArrayList<Message> arrayOfMessages = buildListMessage();
        // Create the adapter to convert the array to views
        MessagesAdapter adapter = new MessagesAdapter(getActivity(), arrayOfMessages);
        // Attach the adapter to a ListView
        ListView listView = (ListView)getView().findViewById(R.id.listMessages);
        listView.setAdapter(adapter);
    }
}
