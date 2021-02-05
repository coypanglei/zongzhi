package com.weique.overhaul.v2.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.weique.overhaul.v2.R;


@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private static final String TITLE = "TITLE";

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment fragment = new SimpleCardFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        try {
            TextView card_title_tv = v.findViewById(R.id.card_title_tv);
            card_title_tv.setText(getArguments().getString(TITLE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }
}