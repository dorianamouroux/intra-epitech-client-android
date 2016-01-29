package android.dorianamouroux.fr.epiandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout    _drawerLayout;
    private NavigationView  _navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        _drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        _navigationView = (NavigationView) findViewById(R.id.nav_view);
        _navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // Select item in the NavigationDrawer
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.license:
                        break;
                    case R.id.profile:
                        OnFragProfileSelected();
                    case R.id.schedule:
                        OnFragScheduleSelected();
                    case R.id.module:
                        OnFragModuleSelected();
                }
                _drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnFragProfileSelected() {
        ProfileFragment _newFragment = new ProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, _newFragment);
        transaction.commit();
    }

    public void OnFragModuleSelected() {
        ModuleFragment _newFragment = new ModuleFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, _newFragment);
        transaction.commit();
    }

    public void OnFragScheduleSelected() {
        ScheduleFragment _newFragment = new ScheduleFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, _newFragment);
        transaction.commit();
    }

}