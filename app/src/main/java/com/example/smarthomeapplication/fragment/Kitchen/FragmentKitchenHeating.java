package com.example.smarthomeapplication.fragment.Kitchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.R;
import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;

public class FragmentKitchenHeating extends Fragment implements View.OnClickListener {

    private TextView mCurrentTemp;
    private ImageView mSyncTemp, mBack;
    private SeekBar mAdjustTemp;
    private Switch mAC, mHeater;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_heating, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        mCurrentTemp = view.findViewById(R.id.textTemperature);
        mSyncTemp = view.findViewById(R.id.syncTemperature);
        mBack = view.findViewById(R.id.back_arrow);
        mAdjustTemp = view.findViewById(R.id.seekBarAdjustTemperature);
        mAC = view.findViewById(R.id.switchAC);
        mHeater = view.findViewById(R.id.switchHeater);

        mBack.setOnClickListener(this);
        mSyncTemp.setOnClickListener(this);

        mAdjustTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCurrentTemp.setText("" + i + " C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getActivity(),"Current temperature has been set to "+ seekBar.getProgress()+ " C.",Toast.LENGTH_SHORT).show();
            }
        });

        mAC.setOnClickListener(view12 -> {
            if (mAC.isChecked()) {
                Toast.makeText(getActivity(),"AC has been turned ON!",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),"AC has been turned OFF!",Toast.LENGTH_SHORT).show();
            }
        });

        mHeater.setOnClickListener(view12 -> {
            if (mHeater.isChecked()) {
                Toast.makeText(getActivity(),"Heater has been turned ON!",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),"Heater has been turned OFF!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back_arrow:
                showTopLevelFragment(new FragmentKitchen());
                break;
            case R.id.syncTemperature:
                Toast.makeText(getActivity(),"Synced successfully!",Toast.LENGTH_SHORT).show();
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
