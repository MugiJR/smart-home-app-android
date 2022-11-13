package com.example.smarthomeapplication.fragment.Bathroom;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.R;

public class FragmentBathroomWindowsAndDoors extends Fragment implements View.OnClickListener {

    private Switch mSwitchWindow, mSwitchDoor;
    private ImageView mButtonBack;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_door_and_window, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {
        mButtonBack = view.findViewById(R.id.back_arrow_door_window);
        mSwitchWindow = view.findViewById(R.id.switchWindow);
        mSwitchDoor = view.findViewById(R.id.switchDoor);

        mButtonBack.setOnClickListener(this);


        mSwitchWindow.setOnClickListener(view12 -> {
            if (mSwitchWindow.isChecked()) {
                Toast.makeText(getActivity(),"Windows are opening...",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),"Windows are closing...",Toast.LENGTH_SHORT).show();
            }
        });


        mSwitchDoor.setOnClickListener(view1 -> {
            if (mSwitchDoor.isChecked()) {
                Toast.makeText(getActivity(),"Doors are opening...",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),"Doors are closing...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back_arrow_door_window:
                showTopLevelFragment(new FragmentBathroom());
                break;


        }
    }

    public void showTopLevelFragment(Fragment fragment) {
        // Use the fragment manager to dynamically change the fragment displayed in the FrameLayout.
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_host_main, fragment)
                .commit();
    }

}
