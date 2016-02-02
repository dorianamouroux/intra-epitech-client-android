package android.dorianamouroux.fr.epiandroid.adapter;

import android.content.Context;
import android.dorianamouroux.fr.epiandroid.R;
import android.dorianamouroux.fr.epiandroid.models.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dorianamouroux on 2/2/16.
 */
public class MessagesAdapter extends ArrayAdapter<Message> {

    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_message, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        // Populate the data into the template view using the data object
        tvTitle.setText(Html.fromHtml(message.getTitle()));
        tvContent.setText(Html.fromHtml(message.getContent()));
        // Return the completed view to render on screen
        return convertView;
    }
}