package com.example.smarthomeapplication.fragment.Kitchen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.R;

public class FragmentKitchenWindowsAndDoors extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_door_and_window, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {



        }
    }

}
