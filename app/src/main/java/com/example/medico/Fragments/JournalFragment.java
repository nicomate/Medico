package com.example.medico.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medico.LandingPageActivity;
import com.example.medico.LoginActivity;
import com.example.medico.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment {

    FirebaseUser firebaseUser;

    public JournalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().onBackPressed();
            Toast.makeText(getActivity(), "You need to Log in to access your Journal!", Toast.LENGTH_SHORT).show();
        }
        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        return view;
    }
}
