package android.dorianamouroux.fr.epiandroid.fragment;

import android.content.Intent;
import android.dorianamouroux.fr.epiandroid.activity.ProjectActivity;
import android.dorianamouroux.fr.epiandroid.utils.ExpandableListAdapter;
import android.os.Bundle;

import android.support.annotation.Nullable;

// Elements
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import java.util.ArrayList;

// Personal import
import android.dorianamouroux.fr.epiandroid.R;
import android.dorianamouroux.fr.epiandroid.IntraAPI;

// Request
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

// Json
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

// Log
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GradeFragment extends Fragment {

    /*
    ** Attributes
    */

    // Log
    private static final String TAG = "DasIstMeinTag";

    private JSONArray _semesters = new JSONArray();
    private ArrayList<String> semesterSpinner = new ArrayList<>();
    private ArrayList<String> moduleListExp = new ArrayList<>();
    private Map<String, List<String>> projectsSelected = new HashMap<>();

    // selected
    private int selectedSemester = 1;

    private ProgressBar progressBar;

    /*
    ** public methode
    */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grade_fragment, container, false);

        IntraAPI.userModules(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray modules = response.getJSONArray("modules");
                    for (int i = 0; i < modules.length(); ++i) {
                        _addModule(modules.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    Log.i(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "userModule request failed with status code: " + statusCode + " and " + String.valueOf(errorResponse));
            }

        });

        IntraAPI.marks(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray notes = response.getJSONArray("notes");
                    for (int i = 0; i < notes.length(); ++i) {
                        _addMark(notes.getJSONObject(i));
                    }
                    progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                    setSpinner();
                } catch (JSONException e) {
                    Log.i(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "Marks request failed with status code: " + statusCode + "and " + String.valueOf(errorResponse));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                Log.i(TAG, response);
            }

        });

        return view;
    }

    /*
    ** Private methode
    */

    private void _addModule(JSONObject module) {
        try {
            JSONObject i_module = new JSONObject();
            i_module.put("name", module.getString("title"));
            i_module.put("id", module.getString("codemodule"));
            i_module.put("projects", new JSONArray());
            int semester = module.getInt("semester");

            for (int i = 0; i < _semesters.length(); ++i) {
                if (_semesters.getJSONObject(i).getInt("semester") == semester) {
                    _semesters.getJSONObject(i).getJSONArray("modules").put(i_module);
                    return;
                }
            }
            JSONObject newSemester = new JSONObject();
            newSemester.put("semester", semester);
            newSemester.put("modules", new JSONArray());
            newSemester.getJSONArray("modules").put(i_module);
            _semesters.put(newSemester);
        } catch (JSONException e) {
            Log.i(TAG, e.toString());
        }
    }

    private void _addMark(JSONObject mark) {
        try {
            for (int i = 0; i < _semesters.length(); ++i) {
                JSONArray modules = _semesters.getJSONObject(i).getJSONArray("modules");
                for (int j = 0; j < modules.length(); ++j) {
                    if (modules.getJSONObject(j).getString("id").equals(mark.getString("codemodule"))) {
                        _semesters.getJSONObject(i).getJSONArray("modules").getJSONObject(j).getJSONArray("projects").put(mark);
                        return;
                    }
                }
            }
            Log.i(TAG, "Can't add folowing mark: " + mark.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setSpinner() {
        try {
            for (int i = 0; i < _semesters.length(); ++i) {
                semesterSpinner.add("semestre " + _semesters.getJSONObject(i).getString("semester"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, semesterSpinner.toString());

        Spinner spinner = (Spinner) getView().findViewById(R.id.semester_selector);
        spinner.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, semesterSpinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.i(TAG, "semestre sélectionné: " + pos + 1);
                selectedSemester = pos + 1;

                setModuleList();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                selectedSemester = 1;
            }
        });

    }

    private void setModuleList() {
        moduleListExp.clear();
        projectsSelected.clear();
        JSONArray selectedModules = new JSONArray();

        try {
            for (int i = 0; i < _semesters.length(); ++i) {
                if (_semesters.getJSONObject(i).getInt("semester") == selectedSemester) {
                    selectedModules = _semesters.getJSONObject(i).getJSONArray("modules");
                    break;
                }
            }
            for (int i = 0; i < selectedModules.length(); ++i) {
                String moduleName = selectedModules.getJSONObject(i).getString("name");
                ArrayList<String> projectList = new ArrayList<>();
                moduleListExp.add(moduleName);
                JSONArray projects = selectedModules.getJSONObject(i).getJSONArray("projects");
                for (int j = 0; j < projects.length(); ++j) {
                    projectList.add(projects.getJSONObject(j).getString("title"));
                }
                projectsSelected.put(moduleName, projectList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ExpandableListView moduleExpListView = (ExpandableListView) getView().findViewById(R.id.module_explist);
        final ExpandableListAdapter moduleAdapter = new ExpandableListAdapter(this.getActivity(), moduleListExp, projectsSelected);
        moduleExpListView.setAdapter(moduleAdapter);
        moduleExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ProjectActivity.class);
                Bundle bundle = new Bundle();
                JSONObject project;
                try {
                    project = _semesters.getJSONObject(selectedSemester - 1).getJSONArray("modules").getJSONObject(groupPosition).getJSONArray("projects").getJSONObject(childPosition);
                    bundle.putString("name", project.getString("title"));
                    bundle.putDouble("note", project.getDouble("final_note"));
                    bundle.putString("comment", project.getString("comment"));
                    bundle.putString("module", project.getString("titlemodule"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
    }

}
