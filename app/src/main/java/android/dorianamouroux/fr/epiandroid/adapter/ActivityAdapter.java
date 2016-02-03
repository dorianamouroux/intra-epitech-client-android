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

        Log.v("Acitvity Adapter", "Position = " + Integer.toString(position));
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
                time.setText(start + " - " + end);
            }
        }

        return view;
    }

//    private Context                 _context;
//    private List<Activity>          _listActivity;
//    private int                     _resourceId;
//
//    private final Context context;
//    private final String[] values;
//
//    public ActivityAdapter(Context context, int resourceId, List<Activity> values) {
//        super(context, resourceId, values);
//        _context = context;
//        _listActivity = values;
//        _resourceId = resourceId;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        Activity activity = getItem(position);
//
//        TextView titleLabel = (TextView) convertView.findViewById(R.id.adapterPlanningTitle);
//        TextView scheduleLabel = (TextView) convertView.findViewById(R.id.adapterPlanningSchedule);
//        TextView roomLabel = (TextView) convertView.findViewById(R.id.adapterPlanningRoom);
//
//        titleLabel.setText(activity.getTitle());
//        scheduleLabel.setText(activity.getSchedule());
//        String roomName = activity.getRoom();
//        roomName = roomName.substring(roomName.lastIndexOf("/") + 1);
//        roomLabel.setText(roomName);
//
//        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
//        TextView textView = (TextView) rowView.findViewById(R.id.label);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        textView.setText(values[position]);
//        // change the icon for Windows and iPhone
//        String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }
//
//        return rowView;
//    }
//}


//
//    public ActivityAdapter(Context context, int resourceId, ArrayList<Activity> listActivity) {
//        super(context, resourceId, listActivity);
//        try {
//            _context = context;
//            _listActivity = listActivity;
//            _resourceId = resourceId;
//
//            _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        } catch (Exception e) {
//            Log.e("Activity Adapter", "error", e);
//        }
//    }
//

//    public int getCount() {
//        return _listActivity.size();
//    }
//
//    public Activity getItem(Activity position) {
//        return position;
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public static class ViewHolder {
//        public TextView display_name;
//        public TextView display_number;
//
//    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//        View vi = convertView;
//        final ViewHolder holder;
//        try {
//            if (convertView == null) {
//                vi = _inflater.inflate(R.layout.yourlayout, null);
//                holder = new ViewHolder();
//
//                holder.display_name = (TextView) vi.findViewById(R.id.display_name);
//                holder.display_number = (TextView) vi.findViewById(R.id.display_number);

//                if (planningItem != null) {
//                    TextView titleLabel = (TextView) v.findViewById(R.id.adapterPlanningTitle);
//                    TextView scheduleLabel = (TextView) v.findViewById(R.id.adapterPlanningSchedule);
//                    TextView roomLabel = (TextView) v.findViewById(R.id.adapterPlanningRoom);
//                    Button btnToken = (Button) v.findViewById(R.id.btnToken);
//
//                    btnToken.setTag(position);
//
//                    if (titleLabel != null) {
//                        titleLabel.setText(planningItem.getTitle());
//                    }
//                    if (scheduleLabel != null) {
//                        scheduleLabel.setText(planningItem.getSchedule());
//                    }
//
//                    if (roomLabel != null) {
//                        String roomName = planningItem.getRoom().getCode();
//                    }
//
//                    roomName = roomName.substring(roomName.lastIndexOf("/") + 1);
//                }
//
//                vi.setTag(holder);
//            }
//            else {
//                holder = (ViewHolder) vi.getTag();
//            }
//
//
//
//            holder.display_name.setText(_listActivity.get(position).getTitle());
//            holder.display_number.setText(_listActivity.get(position).getRoom());
//
//
//        } catch (Exception e) {
//            Log.e("Activity Adapter", "error", e);
//        }
//        return vi;
//    }


}
