package com.example.smarthomeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;
import com.example.smarthomeapplication.fragment.Kitchen.FragmentKitchen;
import com.example.smarthomeapplication.fragment.LivingRoom.FragmentLivingroom;
import com.example.smarthomeapplication.fragment.bedroom.FragmentBedRoom;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener  {

    private ImageView mBedRoom, mLivingRoom, mKitchen, mBathroom;
    //public static BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {


        mBathroom = (ImageView) findViewById(R.id.bathroom);
        mLivingRoom = (ImageView) findViewById(R.id.livingroom);
        mKitchen = (ImageView)findViewById(R.id.kitchenn);
        mBedRoom =(ImageView) findViewById(R.id.bedroom);

        mBedRoom.setOnClickListener(this);
        mLivingRoom.setOnClickListener(this);
        mKitchen.setOnClickListener(this);
        mBathroom.setOnClickListener(this);
}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bathroom:
               showTopLevelFragment(new FragmentBathroom());

                break;

            case (R.id.livingroom):
               showTopLevelFragment(new FragmentLivingroom());

                break;

            case R.id.kitchenn:
                showTopLevelFragment(new FragmentKitchen());


                break;
            case R.id.bedroom:
                MainActivity.this.showTopLevelFragment(new FragmentBedRoom());
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