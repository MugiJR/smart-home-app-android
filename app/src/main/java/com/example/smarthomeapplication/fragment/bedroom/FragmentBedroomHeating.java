package com.example.smarthomeapplication.fragment.bedroom;

import android.os.Bundle;
import android.util.Log;
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

import com.example.smarthomeapplication.PopUpClass;
import com.example.smarthomeapplication.R;
import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentBedroomHeating extends Fragment implements View.OnClickListener {

    private TextView mCurrentTemp;
    private ImageView mSyncTemp, mBack;
    private SeekBar mAdjustTemp;
    private Switch mAC, mHeater;
    private DatabaseReference mRef, mRefCurrentTemp, mRefAC, mRefHeater;
    private FirebaseDatabase db;
    private Boolean isOpened;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_heating, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        // DB instance
        db = FirebaseDatabase.getInstance();
        mRef = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom").child("HeatingIns");
        mRefCurrentTemp = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("HeatingIns").child("Temperature");
        mRefAC = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("HeatingIns").child("AC");
        mRefHeater = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("HeatingIns").child("Heater");

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
                mRef.child("Temperature").setValue(seekBar.getProgress()).addOnSuccessListener(runnable -> {

                });
                Toast.makeText(getActivity(), "Current temperature has been set to " + seekBar.getProgress() + " C.", Toast.LENGTH_SHORT).show();
            }
        });

        mAC.setOnClickListener(view1 -> {
            mRef.child("AC").setValue((mAC.isChecked()) ? 1 : 0).addOnSuccessListener(runnable -> {

            });
            if (mAC.isChecked()) {
                //Open
                mRef.child("AC").setValue(1).addOnSuccessListener(runnable -> {

                });
                mRef.child("Heater").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                isOpened = true;
                popUpClass.showPopupWindow(view1, "AC is ON");
            } else {
                //Close
                mRef.child("AC").setValue(0).addOnSuccessListener(runnable -> {

                });
                mRef.child("Heater").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                isOpened = false;
                popUpClass.showPopupWindow(view1, "AC is OFF");
            }
        });

        mHeater.setOnClickListener(view1 -> {
            mRef.child("Heater").setValue((mHeater.isChecked()) ? 1 : 0).addOnSuccessListener(runnable -> {

            });
            if (mHeater.isChecked()) {
                //Open
                mRef.child("Heater").setValue(1).addOnSuccessListener(runnable -> {

                });
                mRef.child("AC").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                isOpened = true;
                popUpClass.showPopupWindow(view1, "Heater is ON");
            } else {
                //Close
                mRef.child("Heater").setValue(0).addOnSuccessListener(runnable -> {

                });
                mRef.child("AC").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                isOpened = false;
                popUpClass.showPopupWindow(view1, "Heater is OFF");
            }
        });

        mRefCurrentTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (!user.toString().isEmpty()) {
                    int currentTemp = user.intValue();
                    mAdjustTemp.setProgress(currentTemp);
                    mCurrentTemp.setText("" + currentTemp + " C");
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }
        });

        mRefAC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mAC.setChecked(true);
                    mHeater.setChecked(false);
                    mRef.child("Heater").setValue(0);
                } else {
                    mAC.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefHeater.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mHeater.setChecked(true);
                    mAC.setChecked(false);
                    mRef.child("AC").setValue(0);

                } else {
                    mHeater.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back_arrow:
                showTopLevelFragment(new FragmentBedroom());
                break;
            case R.id.syncTemperature:
                Toast.makeText(getActivity(),"Synced successfully...",Toast.LENGTH_SHORT).show();
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
