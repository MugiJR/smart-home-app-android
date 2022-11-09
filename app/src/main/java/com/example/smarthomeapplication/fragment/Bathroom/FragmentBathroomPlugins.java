package com.example.smarthomeapplication.fragment.Bathroom;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import com.example.smarthomeapplication.MainActivity;
import com.example.smarthomeapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.smarthomeapplication.PopUpClass;


public class FragmentBathroomPlugins extends Fragment implements View.OnClickListener{

    private Switch mOpenDryer, mOpenWasher, mOpenDiffuser;
    private ImageView mBack;
    private DatabaseReference mRef, mRefDryer, mRefWasher, mRefDiffuser;
    private FirebaseDatabase database;
    private Boolean isOpened;
    private ImageView timer;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_bathroom_plugins, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom").child("PlugIns");
        mRefDryer = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIns").child("Dryer");
        mRefWasher = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIns").child("Washer");
        mRefDiffuser = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIns").child("Diffuser");

        mBack = view.findViewById(R.id.back_arrow);

        mOpenDryer = view.findViewById(R.id.switchDryerOn);
        mOpenWasher = view.findViewById(R.id.switchWasherOn);
        mOpenDiffuser = view.findViewById(R.id.switchAromatherapyDiffuserOn);
        timer = view.findViewById(R.id.timerDiffuser);
        mBack.setOnClickListener(this);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();
        Long MicrowaveTimer = sharedPreferences.getLong("BathroomDiffuser",0);



        timer.setOnClickListener(view1 ->
        {
            showPopupWindow("BathroomDiffuser",view1);

        });
        if (currentTime> MicrowaveTimer)
        {
            mOpenDiffuser.setChecked(false);
            sharedPreferences.edit().putLong("BathroomDiffuser",0);
            sharedPreferences.edit().apply();

        }

        mOpenDryer.setOnClickListener(view1 -> {
            if (mOpenDryer.isChecked()) {

                mRef.child("Dryer").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Dryer is ON");
            } else {

                mRef.child("Dryer").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Dryer is OFF");
                });

            }
        });

        mOpenWasher.setOnClickListener(view1 -> {
            if (mOpenWasher.isChecked()) {

                mRef.child("Washer").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Washer is ON");
            } else {

                mRef.child("Washer").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Washer is OFF");
                });

            }
        });

        mOpenDiffuser.setOnClickListener(view1 -> {
            if (mOpenDiffuser.isChecked()) {

                mRef.child("Diffuser").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Diffuser is ON");
            } else {

                mRef.child("Diffuser").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Diffuser is OFF");
                });

            }
        });

        mRefDryer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenDryer.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenDryer.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefWasher.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenWasher.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenWasher.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefDiffuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenDiffuser.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenDiffuser.setChecked(false);
                    isOpened = false;

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

    public void showPopupWindow(String test, View view) {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_timer, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler



        Button timer2 = popupView.findViewById(R.id.timer2);
        timer2.setOnClickListener(v -> {

            //As an example, display the message
            if (test.equals("BathroomDiffuser"))
            {
                Toast.makeText(getActivity(),"Timer is set for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BathroomDiffuser", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                if (!mOpenDiffuser.isChecked())
                {
                    mOpenDiffuser.setChecked(true);
                }
                popupWindow.dismiss();

            }


        });

        Button timer4 = popupView.findViewById(R.id.timer4);
        timer4.setOnClickListener(v -> {

            if (test.equals("BathroomDiffuser"))
            {
                Toast.makeText(getActivity(),"Timer is set for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BathroomDiffuser", (System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                if (!mOpenDiffuser.isChecked())
                {
                    mOpenDiffuser.setChecked(true);
                }
                popupWindow.dismiss();

            }



        });

        Button timer6 = popupView.findViewById(R.id.timer6);
        timer6.setOnClickListener(v -> {

            if (test.equals("BathroomDiffuser"))
            {
                Toast.makeText(getActivity(),"Timer is set for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BathroomDiffuser", (System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                if (!mOpenDiffuser.isChecked())
                {
                    mOpenDiffuser.setChecked(true);
                }
                popupWindow.dismiss();

            }

        });


        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }
}