package com.example.smarthomeapplication.fragment.bedroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.R;
import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;

public class FragmentBedroomShades extends Fragment implements View.OnClickListener {

    private SeekBar mSeekbar;
    private ImageView mBackArrow;
    private TextView mShadesText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_shades, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {
        mSeekbar = view.findViewById(R.id.seekBar_shades);
        mShadesText = view.findViewById(R.id.textView_shades);
        mBackArrow = view.findViewById(R.id.back_arrow_shades);
        mBackArrow.setOnClickListener(this);

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mShadesText.setText("" + i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getActivity(),"Shades has been set to "+ seekBar.getProgress()+ "%",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.back_arrow_shades:
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
