package com.example.smarthomeapplication.fragment.Kitchen;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.R;
import com.example.smarthomeapplication.fragment.bedroom.FragmentBedroom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentKitchenWindowsAndDoors extends Fragment implements View.OnClickListener {

    private Switch mSwitchWindow, mSwitchDoor;
    private ImageView mButtonBack;
    private DatabaseReference mRef, mRefWindow, mRefDoor;
    private FirebaseDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_door_and_window, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {
        // DB instance
        db = FirebaseDatabase.getInstance();
        mRef = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen").child("WinDoorIns");
        mRefWindow = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("WinDoorIns").child("Window");
        mRefDoor = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("WinDoorIns").child("Door");

        mButtonBack = view.findViewById(R.id.back_arrow_door_window);
        mSwitchWindow = view.findViewById(R.id.switchWindow);
        mSwitchDoor = view.findViewById(R.id.switchDoor);

        mButtonBack.setOnClickListener(this);


        mSwitchWindow.setOnClickListener(view12 -> {
            mRef.child("Window").setValue((mSwitchWindow.isChecked()) ? 1 : 0).addOnSuccessListener(runnable -> {

            });
            if (mSwitchWindow.isChecked()) {
                Toast.makeText(getActivity(),"Windows are opening...",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),"Windows are closing...",Toast.LENGTH_SHORT).show();
            }

        });


        mSwitchDoor.setOnClickListener(view1 -> {
            mRef.child("Door").setValue((mSwitchDoor.isChecked()) ? 1 : 0).addOnSuccessListener(runnable -> {

            });
            if (mSwitchDoor.isChecked()) {
                Toast.makeText(getActivity(),"Doors are opening...",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),"Doors are closing...",Toast.LENGTH_SHORT).show();
            }
        });

        mRefWindow.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mSwitchWindow.setChecked(true);
                } else {
                    mSwitchWindow.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefDoor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mSwitchDoor.setChecked(true);
                } else {
                    mSwitchDoor.setChecked(false);
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
            case R.id.back_arrow_door_window:
                showTopLevelFragment(new FragmentKitchen());
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
