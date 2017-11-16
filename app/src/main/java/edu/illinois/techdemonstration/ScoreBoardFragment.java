package edu.illinois.techdemonstration;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ScoreBoardFragment extends Fragment {

    View scoreBoardFragment;

    public static ScoreBoardFragment newInstance() {
        ScoreBoardFragment rankingFragment = new ScoreBoardFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        scoreBoardFragment = inflater.inflate(R.layout.fragment_categories, container, false);
        return scoreBoardFragment;
    }

}
