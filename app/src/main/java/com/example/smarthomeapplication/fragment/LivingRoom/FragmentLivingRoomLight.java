package com.example.smarthomeapplication.fragment.LivingRoom;



import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.R;

//Database

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.smarthomeapplication.PopUpClass;
public class FragmentLivingRoomLight extends Fragment implements View.OnClickListener {
    private Switch mChangeLight;
    private DatabaseReference mRef, mRefLight;
    private FirebaseDatabase database;
    public Boolean isOpened = false;
    private ImageView mBack;
    private View view = null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if(isOpened){
//            view = inflater.inflate(R.layout.activity_lighton, container, false);
//        }
//        else{
//            view = inflater.inflate(R.layout.activity_lightoff, container, false);
//        }
        view = inflater.inflate(R.layout.activity_lighton, container, false);
        initView(view);

        return view;

    }



    private void initView(View view) {
        mBack = view.findViewById(R.id.back_arrow);
        mBack.setOnClickListener(this);
        mChangeLight = view.findViewById(R.id.lightstatus);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom").child("LightIns");
        mRefLight = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("LightIns").child("Light");

//        mOpenLight.setOnClickListener();
        //store status
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        mChangeLight.setOnClickListener(view1 -> {
            if (mChangeLight.isChecked()) {
                //Open
                mRef.child("Light").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                isOpened = true;
                popUpClass.showPopupWindow(view1, "Light is ON");


            } else {
                //Close
                mRef.child("Light").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                isOpened = false;
                popUpClass.showPopupWindow(view1, "Light is OFF");

            }
        });

        mRefLight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mChangeLight.setChecked(true);
                    isOpened = true;
                    mChangeLight.setBackgroundResource(R.drawable.on);

                } else {
                    mChangeLight.setChecked(false);
                    isOpened = false;
                    mChangeLight.setBackgroundResource(R.drawable.off);
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }
        });
    }





    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.back_arrow:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_main, new FragmentLivingRoom())
                        .commit();
                break;

            case R.id.lightstatus:
                showTopLevelFragment(new FragmentLivingRoomLight());
                break;


        }
    }

    public void showTopLevelFragment(Fragment fragment) {
        // Use the fragment manager to dynamically change the fragment displayed in the FrameLayout.
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_host_main, fragment)
                .commit();


    }



    public void showPopupWindow(String text, View view) {


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
