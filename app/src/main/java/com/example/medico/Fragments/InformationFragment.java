package com.example.medico.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medico.LandingPageActivity;
import com.example.medico.R;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {

    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);


    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PhotoView imageView = view.findViewById(R.id.photo_view);
        imageView.setImageResource(R.drawable.quickfacts3);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // TODO: make image topcrop https://github.com/chrisbanes/PhotoView/issues/203
        // cant edit source code of photoview library
    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((LandingPageActivity) getActivity())
                .setActionBarTitle("Health Facts");
    }

    private int getImageViewWidth(ImageView imageView) {
        return imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
    }

    private int getImageViewHeight(ImageView imageView) {
        return imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
    }

}
