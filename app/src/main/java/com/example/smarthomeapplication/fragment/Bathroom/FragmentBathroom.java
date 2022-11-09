package com.example.smarthomeapplication.fragment.Bathroom;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.example.smarthomeapplication.MainActivity;
import com.example.smarthomeapplication.R;
import com.example.smarthomeapplication.RegisterActivity;

public class FragmentBathroom extends Fragment implements View.OnClickListener {
    private ImageView mBack, mLight,mPlugIn,mHeating,mWindows,mShades;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_bathroom, container, false);
        initView(view);
        return view;

    }

   private void initView(View view) {


        mBack = view.findViewById(R.id.back_arrow);


        mLight =  view.findViewById(R.id.light);
        mPlugIn = view.findViewById(R.id.devices);
        mHeating =  view.findViewById(R.id.heating);
        mWindows = view.findViewById(R.id.windowsAndDoors);
        mShades = view.findViewById(R.id.shades);


        mBack.setOnClickListener(this);
        mLight.setOnClickListener(this);
        mPlugIn.setOnClickListener(this);
        mHeating.setOnClickListener(this);
        mWindows.setOnClickListener(this);
        mShades.setOnClickListener(this);
    }

   @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.back_arrow:
                Intent intent = new Intent(this.getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.light:
                showTopLevelFragment(new FragmentBathroomLight());
                break;

            case R.id.devices:
                showTopLevelFragment(new FragmentBathroomPlugins());

                break;


            case R.id.heating:

                showTopLevelFragment(new FragmentBathroomHeating());

                break;

            case R.id.windowsAndDoors:

                showTopLevelFragment(new FragmentBathroomWindowsAndDoors());

                break;
            case R.id.shades:

                showTopLevelFragment(new FragmentBathroomShades());

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
