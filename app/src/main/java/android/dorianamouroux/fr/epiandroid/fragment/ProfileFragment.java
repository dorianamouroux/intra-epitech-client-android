package android.dorianamouroux.fr.epiandroid.fragment;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dorianamouroux.fr.epiandroid.R;

public class ProfileFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        return view;
    }

}
