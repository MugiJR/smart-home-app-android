package com.example.smarthomeapplication.fragment.Kitchen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentKitchenShades extends Fragment implements View.OnClickListener {

    private SeekBar mSeekbar;
    private ImageView mBackArrow;
    private TextView mShadesText;
    private DatabaseReference mRef, mRefShades;
    private FirebaseDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_shades, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {
        // DB instance
        db = FirebaseDatabase.getInstance();
        mRef = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen").child("ShadesIns");
        mRefShades = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("ShadesIns").child("Shades");
        mSeekbar = view.findViewById(R.id.seekBar_shades);
        mShadesText = view.findViewById(R.id.textView_shades);
        mBackArrow = view.findViewById(R.id.back_arrow_shades);
        mBackArrow.setOnClickListener(this);

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mShadesText.setText("" + i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mRef.child("Shades").setValue(seekBar.getProgress()).addOnSuccessListener(runnable -> {

                });
                Toast.makeText(getActivity(),"Shades has been set to "+ seekBar.getProgress()+ "%",Toast.LENGTH_SHORT).show();
            }
        });

        mRefShades.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (!user.toString().isEmpty()) {
                    int shadesLevel = user.intValue();
                    mSeekbar.setProgress(shadesLevel);
                    mShadesText.setText("" + shadesLevel + "%");
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

            case R.id.back_arrow_shades:
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
