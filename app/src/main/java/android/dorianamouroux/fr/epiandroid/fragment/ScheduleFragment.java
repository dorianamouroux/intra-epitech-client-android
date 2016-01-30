package android.dorianamouroux.fr.epiandroid.fragment;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.app.Fragment;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dorianamouroux.fr.epiandroid.R;
import android.dorianamouroux.fr.epiandroid.IntraAPI;
import android.widget.ListView;


import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ScheduleFragment extends Fragment {

    private ListView _activityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);

        _activityList = (ListView) view.findViewById(R.id.activityList);

        IntraAPI.planning("2016-01-27", "2016-01-27", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.toString());
                    addSchedule(response.getJSONArray("items"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.v("Fail request trombi", String.valueOf(errorResponse));
            }
        });

        return view;
    }

    private void addSchedule(JSONArray scheduleArray)
    {
        try {
            JSONObject scheduleItem = scheduleArray.getJSONObject(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
