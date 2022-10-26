package com.example.smarthomeapplication.fragment.Bathroom;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.example.smarthomeapplication.R;
import com.example.smarthomeapplication.fragment.bedroom.FragmentBedRoom;

public class FragmentBathroom extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_bathroom, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {



        }
    }


}
