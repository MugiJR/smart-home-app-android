package com.example.smarthomeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;
import com.example.smarthomeapplication.fragment.Kitchen.FragmentKitchen;
import com.example.smarthomeapplication.fragment.LivingRoom.FragmentLivingRoom;
import com.example.smarthomeapplication.fragment.bedroom.FragmentBedroom;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener  {

    private Switch mOpenRouter, mOpenMusic;
    private ImageView mBedRoom, mLivingRoom, mKitchen, mBathroom;
    private DatabaseReference mRef, mRefRouter, mRefMusic;
    private FirebaseDatabase database;
    private Boolean isOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Main");
        mRefRouter = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Main")
                .child("Router");
        mRefMusic = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Main")
                .child("Music");


        mBathroom = (ImageView) findViewById(R.id.bathroom);
        mLivingRoom = (ImageView) findViewById(R.id.livingroom);
        mKitchen = (ImageView)findViewById(R.id.kitchenn);
        mBedRoom =(ImageView) findViewById(R.id.bedroom);

        mOpenRouter = (Switch) findViewById(R.id.router);
        mOpenMusic = (Switch) findViewById(R.id.music);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MySharedPref",MODE_PRIVATE);


        mBedRoom.setOnClickListener(this);
        mLivingRoom.setOnClickListener(this);
        mKitchen.setOnClickListener(this);
        mBathroom.setOnClickListener(this);

        mOpenRouter.setOnClickListener(view1 -> {
            if (mOpenRouter.isChecked()) {

                mRef.child("Router").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Router is ON");
            } else {

                mRef.child("Router").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Router is OFF");
                });

            }
        });

        mOpenMusic.setOnClickListener(view1 -> {
            if (mOpenMusic.isChecked()) {

                mRef.child("Music").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Music is ON");
            } else {

                mRef.child("Music").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Music is OFF");
                });

            }
        });
        mRefRouter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenRouter.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenRouter.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefMusic.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenMusic.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenRouter.setChecked(false);
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
        switch (view.getId()) {
            case R.id.bathroom:
               showTopLevelFragment(new FragmentBathroom());

                break;

            case (R.id.livingroom):
               showTopLevelFragment(new FragmentLivingRoom());

                break;

            case R.id.kitchenn:
                showTopLevelFragment(new FragmentKitchen());


                break;
            case R.id.bedroom:
                MainActivity.this.showTopLevelFragment(new FragmentBedroom());
                break;
        }
    }


   void showTopLevelFragment(Fragment f1) {

        FragmentManager fragmentManager;
       fragmentManager=MainActivity.this.getSupportFragmentManager();
       FragmentTransaction ft = fragmentManager.beginTransaction();
       ft.replace(R.id.fragment_host_main, f1); // f1_container is your FrameLayoutcontainer
       ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
       ft.addToBackStack(null);
       ft.commit();
    }


}