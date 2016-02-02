package android.dorianamouroux.fr.epiandroid.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.support.design.widget.NavigationView;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.MenuItem;

import android.dorianamouroux.fr.epiandroid.fragment.ModuleFragment;
import android.dorianamouroux.fr.epiandroid.fragment.ProfileFragment;
import android.dorianamouroux.fr.epiandroid.fragment.ScheduleFragment;

import android.dorianamouroux.fr.epiandroid.R;
import android.view.View;


public class HomeActivity extends AppCompatActivity {
    private DrawerLayout            _drawerLayout;
    private NavigationView          _navigationView;
    private ActionBarDrawerToggle   _drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        _drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        _drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                _drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawerOpen,  /* "open drawer" description */
                R.string.drawerClose  /* "close drawer" description */
        ) {};

        _navigationView = (NavigationView) findViewById(R.id.nav_view);
        _navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // Select item in the NavigationDrawer
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        OnFragProfileSelected();
                        break;
                    case R.id.schedule:
                        OnFragScheduleSelected();
                        break;
                    case R.id.module:
                        OnFragModuleSelected();
                        break;
                }
                _drawerLayout.closeDrawers();
                return true;
            }
        });

        _drawerLayout.setDrawerListener(_drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
