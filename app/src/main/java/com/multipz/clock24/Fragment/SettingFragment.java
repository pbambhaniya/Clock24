package com.multipz.clock24.Fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.multipz.clock24.R;
import com.multipz.clock24.utils.Constant;
import com.multipz.clock24.utils.Shared;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    TextView btnsave;
    Switch swtopnews, swbusiness, swsport, swentertainment, swgaming, swtechnology, swscience, swgeneral, swmusic, swpolitics;
    Shared shared;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        shared = new Shared(getActivity());

        swbusiness = (Switch) view.findViewById(R.id.swbusiness);
        swsport = (Switch) view.findViewById(R.id.swsport);
        swentertainment = (Switch) view.findViewById(R.id.swentertainment);
        swgaming = (Switch) view.findViewById(R.id.swgaming);
        swtechnology = (Switch) view.findViewById(R.id.swtechnology);
        swscience = (Switch) view.findViewById(R.id.swscience);
        swgeneral = (Switch) view.findViewById(R.id.swgeneral);
        swmusic = (Switch) view.findViewById(R.id.swmusic);
        swpolitics = (Switch) view.findViewById(R.id.swpolitics);

        swbusiness.setChecked(shared.getBoolean(Constant.IS_Business_oN, true));
        swsport.setChecked(shared.getBoolean(Constant.Is_Sport_oN, true));
        swentertainment.setChecked(shared.getBoolean(Constant.Is_Entertainment_oN, true));
        swgaming.setChecked(shared.getBoolean(Constant.Is_Gaming_oN, true));
        swtechnology.setChecked(shared.getBoolean(Constant.Is_Technology_oN, true));
        swscience.setChecked(shared.getBoolean(Constant.Is_Science_oN, true));
        swgeneral.setChecked(shared.getBoolean(Constant.Is_General_oN, true));
        swmusic.setChecked(shared.getBoolean(Constant.Is_Music_oN, true));
        swpolitics.setChecked(shared.getBoolean(Constant.Is_Politics_oN, true));

        swbusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                shared.putBoolean(Constant.IS_Business_oN, b);

            }
        });

        swsport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_Sport_oN, b);
            }
        });

        swentertainment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_Entertainment_oN, b);
            }
        });


        swgaming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_Gaming_oN, b);
            }
        });


        swtechnology.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_Technology_oN, b);
            }
        });


        swscience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_Science_oN, b);
            }
        });

        swgeneral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_General_oN, b);
            }
        });

        swmusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_Music_oN, b);
            }
        });
        swpolitics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shared.putBoolean(Constant.Is_Politics_oN, b);
            }
        });

        return view;


    }


}
