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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.dorianamouroux.fr.epiandroid.R;
import android.dorianamouroux.fr.epiandroid.IntraAPI;
import android.dorianamouroux.fr.epiandroid.adapter.ActivityAdapter;
import android.dorianamouroux.fr.epiandroid.item.Activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ScheduleFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private JSONArray           _schedule;
    private ListView            _activityList;
    private ActivityAdapter     _adapter;
    private ArrayList<Activity> _listActivity  = new ArrayList<>();
    private static int          _day = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);
        ImageButton prevBtn = (ImageButton) view.findViewById(R.id.btnPrevDay);
        ImageButton nextBtn = (ImageButton) view.findViewById(R.id.btnNextDay);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        _activityList = (ListView) view.findViewById(R.id.activityList);

        getSchedule(dateToShow());

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextDay :
                _day++;
                getSchedule(dateToShow());
                break;
            case R.id.btnPrevDay :
                _day--;
                getSchedule(dateToShow());
                break;
//            case R.id.btnToken :
//                validateToken(view);
//                break;
        }
    }

    private String dateToShow() {
        Calendar c = GregorianCalendar.getInstance(Locale.FRANCE);
        c.add(Calendar.DATE, _day);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        try {
            TextView txtDay = (TextView) this.getView().findViewById(R.id.planningDate);
            String stringDay = df.format(c.getTime()).split("-")[2];
            String stringMonth = df.format(c.getTime()).split("-")[1];
            String stringYear = df.format(c.getTime()).split("-")[0];

            String day = stringDay + "/" + stringMonth + "/" + stringYear;

            txtDay.setText(day);
        } catch (java.lang.NullPointerException e) {
            Log.v("Schedule", "Error");
        }
        return df.format(c.getTime());
    }

    private void getSchedule(String date) {
        IntraAPI.planning(date, date, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                _schedule = response;
                afterFetchSchedule();
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
    }

    private void buildListActivity() {
        JSONObject tmp;

        _listActivity.clear();

        for (int i = 0; i < _schedule.length(); ++i) {
            try {
                if ((tmp = _schedule.getJSONObject(i)) != null) {
                    if (tmp.has("module_registered") && tmp.getBoolean("module_registered")) {
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
                        item.setRoom(tmp.getString("room"));
                        _listActivity.add(item);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void afterFetchSchedule() {
        // Construct the data source
        buildListActivity();
        // Create the adapter to convert the array to views
        // Attach the adapter to a ListView
        _adapter = new ActivityAdapter (getActivity(), R.layout.activity_adapter, _listActivity);
        _activityList.setAdapter(_adapter);
    }
}