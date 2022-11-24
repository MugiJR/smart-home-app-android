package com.example.smarthomeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;
import com.example.smarthomeapplication.fragment.Kitchen.FragmentKitchen;
import com.example.smarthomeapplication.fragment.LivingRoom.FragmentLivingRoom;
import com.example.smarthomeapplication.fragment.bedroom.FragmentBedroom;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SummaryActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageView mBack, mBedroom, mLivingRoom, mKitchen, mBathroom, profile;
    private DatabaseReference mRef, mRefRouter, mRefMusic;
    private FirebaseDatabase database;
    private Boolean isOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        initView();
    }


    private void initView() {



        mBathroom = (ImageView) findViewById(R.id.EditBathroom);
        mLivingRoom = (ImageView) findViewById(R.id.EditLivingroom);
        mKitchen = (ImageView) findViewById(R.id.EditKitchen);
        mBedroom = (ImageView) findViewById(R.id.EditBedroom);
        profile = (ImageView) findViewById(R.id.profile);
        mBack = (ImageView) findViewById(R.id.back_arrow);


        mBedroom.setOnClickListener(this);
        mLivingRoom.setOnClickListener(this);
        mKitchen.setOnClickListener(this);
        mBathroom.setOnClickListener(this);
        mBack.setOnClickListener(this);



        profile.setOnClickListener(view1 -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userEmail = user.getEmail();
                showPopupWindow(view1, userEmail);
            } else {
                // No user is signed in
            }


        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.EditBathroom:
                //showTopLevelFragment(new FragmentBathroom());

                Intent intent = new Intent(SummaryActivity.this, FragmentBedroom.class);
                startActivity(intent);
                break;

            case (R.id.EditLivingroom):
                showTopLevelFragment(new FragmentLivingRoom());

                break;

            case R.id.EditKitchen:
                showTopLevelFragment(new FragmentKitchen());


                break;
            case R.id.EditBedroom:
                this.showTopLevelFragment(new FragmentBedroom());
                break;
            case R.id.back_arrow:
                Intent intent1 = new Intent(SummaryActivity.this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
        }
    }


    void showTopLevelFragment(Fragment f1) {

        FragmentManager fragmentManager;
        fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_host_summary, f1); // f1_container is your FrameLayoutcontainer
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showPopupWindow(final View view, String mTExt) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_profile, null);

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

        TextView test2 = popupView.findViewById(R.id.user_name);
        test2.setText(mTExt);

        Button logout = popupView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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