package com.firestoresample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class FirestoreProfileFragment extends Fragment {
    EditText etEmailsignup, etpasswordsignup, etNamesignup, etDobsignup, etCitysignup;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    String userId;
    RadioGroup radioGroup;
    RadioButton radioMale, radioFemale;


    public FirestoreProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firestore_my_profile, viewGroup, false);
        etEmailsignup = (EditText) view.findViewById(R.id.etEmail);
        etpasswordsignup = (EditText) view.findViewById(R.id.etpassword);
        etNamesignup = (EditText) view.findViewById(R.id.etName);
        etDobsignup = (EditText) view.findViewById(R.id.etDob);
        etCitysignup = (EditText) view.findViewById(R.id.etCity);


        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioMale = (RadioButton)view. findViewById(R.id.radioMale);
        radioFemale = (RadioButton)view. findViewById(R.id.radioFemale);
        getData();
        return view;
    }

    public void getData() {
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Signup_Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    etEmailsignup.setText(documentSnapshot.getString("signup_password"));
                    etNamesignup.setText(documentSnapshot.getString("signup_name"));
                    etpasswordsignup.setText(documentSnapshot.getString("signup_email"));
                    etCitysignup.setText(documentSnapshot.getString("signup_city"));
                    etDobsignup.setText(documentSnapshot.getString("signup_birthdate"));
                    if(documentSnapshot.getString("signup_gender").equals("Male")){
                        radioMale.setChecked(true);
                    }
                    else {
                        radioFemale.setChecked(true);
                    }


                } else {
                    Toast.makeText(getContext(), "Somethig went wrong please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}