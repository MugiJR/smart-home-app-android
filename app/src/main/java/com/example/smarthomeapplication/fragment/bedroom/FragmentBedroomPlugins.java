package com.example.smarthomeapplication.fragment.bedroom;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.MainActivity;
import com.example.smarthomeapplication.PopUpClass;
import com.example.smarthomeapplication.R;
import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragmentBedroomPlugins extends Fragment implements View.OnClickListener{

    private Switch mOpenLamp, mOpenAirPurifier, mOpenAudio;
    private ImageView mBack;
    private DatabaseReference mRef, mRefLamp, mRefAirPurifier, mRefAudio;
    private FirebaseDatabase database;
    private Boolean isOpened;
    private ImageView timer;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_bedroom_plugins, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom").child("PlugIns");
        mRefLamp = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("PlugIns").child("Lamp");
        mRefAirPurifier = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("PlugIns").child("AirPurifier");
        mRefAudio = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("PlugIns").child("BedAudio");

        mOpenLamp = view.findViewById(R.id.switchBedsideTableLampOn);
        mOpenAirPurifier = view.findViewById(R.id.switchAirPurifierOn);
        mOpenAudio = view.findViewById(R.id.switchAudioOn);
        timer = view.findViewById(R.id.timerAudio);
        mBack = view.findViewById(R.id.back_arrow);
        mBack.setOnClickListener(this);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();
        Long AudioTime = sharedPreferences.getLong("BedAudio",0);

        timer.setOnClickListener(view1 ->
        {
            showPopupWindow("BedAudio",view1);

        });
        if (currentTime> AudioTime)
        {
            mOpenAudio.setChecked(false);
            sharedPreferences.edit().putLong("BedAudio",0);
            sharedPreferences.edit().apply();

        }

        mOpenLamp.setOnClickListener(view1 -> {
            if (mOpenLamp.isChecked()) {

                mRef.child("Lamp").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Lamp is ON");
            } else {

                mRef.child("Lamp").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Lamp is OFF");
                });

            }
        });

        mOpenAirPurifier.setOnClickListener(view1 -> {
            if (mOpenAirPurifier.isChecked()) {

                mRef.child("AirPurifier").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Air Purifier is ON");
            } else {

                mRef.child("AirPurifier").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Air Purifier is OFF");
                });

            }
        });

        mOpenAudio.setOnClickListener(view1 -> {
            if (mOpenAudio.isChecked()) {

                mRef.child("Audio").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Bed Audio is ON");
            } else {

                mRef.child("Audio").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Bed Audio is OFF");
                });

            }
        });

        mRefLamp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenLamp.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenLamp.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefAirPurifier.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenAirPurifier.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenAirPurifier.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefAudio.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenAudio.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenAudio.setChecked(false);
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
                showTopLevelFragment(new FragmentBedroom());
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
            if (test.equals("BedAudio"))
            {
                Toast.makeText(getActivity(),"Timer is set for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BedAudio", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                if (!mOpenAudio.isChecked())
                {
                    mOpenAudio.setChecked(true);
                }
                popupWindow.dismiss();

            }


        });

        Button timer4 = popupView.findViewById(R.id.timer4);
        timer4.setOnClickListener(v -> {

            if (test.equals("BedAudio"))
            {
                Toast.makeText(getActivity(),"Timer is set for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BedAudio", (System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                if (!mOpenAudio.isChecked())
                {
                    mOpenAudio.setChecked(true);
                }
                popupWindow.dismiss();

            }



        });

        Button timer6 = popupView.findViewById(R.id.timer6);
        timer6.setOnClickListener(v -> {

            if (test.equals("BedAudio"))
            {
                Toast.makeText(getActivity(),"Timer is set for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BedAudio", (System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                if (!mOpenAudio.isChecked())
                {
                    mOpenAudio.setChecked(true);
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