package com.example.smarthomeapplication.fragment.Kitchen;

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


public class FragmentKitchenPlugins extends Fragment implements View.OnClickListener{

    private Switch mOpenMicrowave, mOpenCoffeeMaker, mOpenDishwasher;
    private ImageView mBack;

    private DatabaseReference mRef,mRefDishwasher, mRefCoffeeMaker, mRefMicrowave;
    private FirebaseDatabase database;
    private Boolean isOpened;
    private ImageView timer;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_kitchen_plugins, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen").child("PlugIns");
        mRefMicrowave = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("PlugIns").child("Microwave");
        mRefCoffeeMaker = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("PlugIns").child("CoffeeMaker");
        mRefDishwasher = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("PlugIns").child("Dishwasher");


        mOpenMicrowave = view.findViewById(R.id.switchMicrowaveOn);
        mOpenCoffeeMaker = view.findViewById(R.id.switchCoffeemakerOn);
        mOpenDishwasher = view.findViewById(R.id.switchDishwasherOn);
        timer = view.findViewById(R.id.timerMicrowave);
        mBack = view.findViewById(R.id.back_arrow);
        mBack.setOnClickListener(this);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();
        Long MicrowaveTimer = sharedPreferences.getLong("KitchenMicrowave",0);



        timer.setOnClickListener(view1 ->
        {
            showPopupWindow("KitchenMicrowave",view1);

        });
        if (currentTime> MicrowaveTimer)
        {
            mOpenMicrowave.setChecked(false);
            sharedPreferences.edit().putLong("KitchenMicrowave",0);
            sharedPreferences.edit().apply();

        }
        mOpenMicrowave.setOnClickListener(view1 -> {
            if (mOpenMicrowave.isChecked()) {

                mRef.child("Microwave").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Microwave is ON");
            } else {

                mRef.child("Microwave").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Microwave is OFF");
                });

            }
        });

        mOpenCoffeeMaker.setOnClickListener(view1 -> {
            if (mOpenCoffeeMaker.isChecked()) {

                mRef.child("CoffeMaker").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Coffe Maker is ON");
            } else {

                mRef.child("CoffeMaker").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Coffee Maker is OFF");
                });

            }
        });

        mOpenDishwasher.setOnClickListener(view1 -> {
            if (mOpenDishwasher.isChecked()) {

                mRef.child("Dishwasher").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Dishwasher is ON");
            } else {

                mRef.child("Dishwasher").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Dishwasher is OFF");
                });

            }
        });


        mRefMicrowave.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenMicrowave.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenMicrowave.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefCoffeeMaker.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenCoffeeMaker.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenCoffeeMaker.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


        mRefDishwasher.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenDishwasher.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenDishwasher.setChecked(false);
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

    public void showPopupWindow(String test, View view) {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_small_timer, null);

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


        Button timer1 = popupView.findViewById(R.id.timer1);
        timer1.setOnClickListener(v -> {

            //As an example, display the message
            if (test.equals("KitchenMicrowave"))
            {
                Toast.makeText(getActivity(),"Timer is set for 1 Minutes",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("KitchenMicrowave", (System.currentTimeMillis()+(60*1000)));
                sharedPreferences.edit().commit();
                if (!mOpenMicrowave.isChecked())
                {
                    mOpenMicrowave.setChecked(true);
                }
                popupWindow.dismiss();

            }


        });

        Button timer2 = popupView.findViewById(R.id.timer2);
        timer2.setOnClickListener(v -> {

            if (test.equals("KitchenMicrowave"))
            {
                Toast.makeText(getActivity(),"Timer is set for 2 Minutes",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("KitchenMicrowave", (System.currentTimeMillis()+(60*2000)));
                sharedPreferences.edit().commit();
                if (!mOpenMicrowave.isChecked())
                {
                    mOpenMicrowave.setChecked(true);
                }
                popupWindow.dismiss();

            }


        });

        Button timer3 = popupView.findViewById(R.id.timer3);
        timer3.setOnClickListener(v -> {

            if (test.equals("KitchenMicrowave"))
            {
                Toast.makeText(getActivity(),"Timer is set for 3 Minutes",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("KitchenMicrowave", (System.currentTimeMillis()+(60*3000)));
                sharedPreferences.edit().commit();
                if (!mOpenMicrowave.isChecked())
                {
                    mOpenMicrowave.setChecked(true);
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