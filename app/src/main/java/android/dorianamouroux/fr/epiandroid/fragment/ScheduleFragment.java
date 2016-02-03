package android.dorianamouroux.fr.epiandroid.fragment;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.app.Fragment;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.dorianamouroux.fr.epiandroid.R;
import android.dorianamouroux.fr.epiandroid.IntraAPI;
import android.dorianamouroux.fr.epiandroid.adapter.ActivityAdapter;
import android.dorianamouroux.fr.epiandroid.item.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ScheduleFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ListView            _activityList;
    private ActivityAdapter     _adapter;
    private ArrayList<Activity>      _listActivity  = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);
        _activityList = (ListView) view.findViewById(R.id.activityList);

        Log.v("Lol : ", "Avant");
        IntraAPI.planning("2016-01-27", "2016-01-27", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.v("Lol : ", "je rentre");
                JSONArray json;
                try {
                    json = new JSONArray(response.toString());
                    int i = 1;
                    JSONObject tmp;
//                    for (int i = 0; i < json.length(); ++i) {
                    if ((tmp = json.getJSONObject(i)) != null) {
                            if (tmp.has("module_registered") && tmp.getBoolean("module_registered")) {
                                Log.v("Lol : ", "Pendant " + Integer.toString(i));
                                Activity item = new Activity();
                                item.setTitle(tmp.getString("acti_title"));
                                item.setStart(tmp.getString("start"));
                                item.setEnd(tmp.getString("end"));
                                item.setCodeacti(tmp.getString("codeacti"));
                                item.setCodeinstance(tmp.getString("codeinstance"));
                                item.setCodeevent(tmp.getString("codeevent"));
                                item.setCodemodule(tmp.getString("codemodule"));
                                item.setScolaryear(tmp.getString("scolaryear"));
                                item.setRegistered(tmp.getString("event_registered"));
                                _listActivity.add(item);
                            }
                        }
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.v("Schedule :", String.valueOf(errorResponse));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                Log.i("Schedule ", response);
            }
        });

        Log.v("Lol : ", "Après");

        _adapter = new ActivityAdapter (view.getContext(), R.layout.activity_adapter, _listActivity);
        _activityList.setAdapter(_adapter);
//        ListAdapter planningSwipeAdapter = new PlanningSwipeAdapter(rootView.getContext(), R.layout.planning_item, items);

        return view;
    }

}