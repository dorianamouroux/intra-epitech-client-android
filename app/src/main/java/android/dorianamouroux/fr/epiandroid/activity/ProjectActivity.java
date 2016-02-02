package android.dorianamouroux.fr.epiandroid.activity;

import android.app.Activity;
import android.dorianamouroux.fr.epiandroid.R;
import android.os.Bundle;
import android.widget.TextView;

public class ProjectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Bundle params = getIntent().getExtras();
        String name = params.getString("name");
        String comment = params.getString("comment");
        String module = params.getString("module");
        Double note = params.getDouble("note");

        TextView titleView = (TextView) findViewById(R.id.project_name);
        TextView noteView = (TextView) findViewById(R.id.note);
        TextView commentView = (TextView) findViewById(R.id.project_comment);
        TextView moduleView = (TextView) findViewById(R.id.module_name);

        titleView.setText(name);
        noteView.setText(note.toString());
        commentView.setText(comment);
        moduleView.setText(module);
    }
}
