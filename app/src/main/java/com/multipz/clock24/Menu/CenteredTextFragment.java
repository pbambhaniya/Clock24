package com.multipz.clock24.Menu;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multipz.clock24.R;


/**
 * Created by yarolegovich on 25.03.2017.
 */

public class CenteredTextFragment extends Fragment {
    Typeface custom_font;
    private static final String EXTRA_TEXT = "text";

    public static CenteredTextFragment createFor(String text) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String text = getArguments().getString(EXTRA_TEXT);
        TextView textView = (TextView) view.findViewById(R.id.text);

        custom_font = Typeface.createFromAsset(getActivity().getAssets(), "Leelawadee_font/LeelawUI.ttf");
        textView.setTypeface(custom_font);
        textView.setText(text);
    }
}
