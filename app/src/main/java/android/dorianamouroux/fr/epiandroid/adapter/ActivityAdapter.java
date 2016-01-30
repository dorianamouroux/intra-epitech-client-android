package android.dorianamouroux.fr.epiandroid.adaptater;

import android.content.Context;
import android.dorianamouroux.fr.epiandroid.R;
import android.dorianamouroux.fr.epiandroid.item.Activity;

import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by patoche on 1/30/16.
 */
public class ActivityAdapter extends ArrayAdapter<Activity> {

    public ActivityAdapter(Context context, List<Activity> activityList) {
        super(context, R.layout.activity_home, activityList);
    }

}
