package android.dorianamouroux.fr.epiandroid.adapter;

import android.dorianamouroux.fr.epiandroid.R;
import android.dorianamouroux.fr.epiandroid.item.Activity;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patoche on 1/30/16.
 */
public class ActivityAdapter extends ArrayAdapter<Activity> {

    public ActivityAdapter(Context context, int resource, List<Activity> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.activity_adapter, null);
        }

        Activity act = getItem(position);
        if (act != null) {
            TextView room = (TextView) view.findViewById(R.id.room);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView time = (TextView) view.findViewById(R.id.time);
            ImageButton button = (ImageButton) view.findViewById(R.id.imageButton);

            if (room != null) {
                room.setText(act.getRoom());
            }
            if (title != null) {
                title.setText(act.getTitle());
            }
            if (time != null) {
                String start = act.getHourStart();
                String end = act.getHourEnd();
                String sTime = start + "\n" + end;
                time.setText(sTime);
            }
        }

        return view;
    }
}
