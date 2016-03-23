package com.kpi.campus.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpi.campus.R;

/**
 * Created by Administrator on 23.03.2016.
 */
public class BbFragment extends Fragment {
    private static final String KEY_POSITION = "position";

    public static BbFragment newInstance(int position) {
        BbFragment frag = new BbFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_bulletin_actual_new, container, false);
        TextView editor = (TextView) result.findViewById(R.id.editor);
        int position = getArguments().getInt(KEY_POSITION, -1);

        editor.setText("tab number " + position);

        return (result);
    }
}
