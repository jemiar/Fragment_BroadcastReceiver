package com.project.hoangminh.app3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/*---------------------------------------------
Project 3 - App 3
RestaurantPicFragment class

Same as HotelPicFragment class
----------------------------------------------*/

public class RestaurantPicFragment extends Fragment {

    private int mCurrentIndex = -1;
    private int imageArrayLength;
    private ImageView imageView;

    int getShownIndex(){
        return mCurrentIndex;
    }

    void showImageAtIndex(int i){
        if(i < 0 || i >= imageArrayLength)
            return;
        mCurrentIndex = i;
        imageView.setImageResource(RestaurantActivity.imageIDs.get(i));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pic, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView = (ImageView) getActivity().findViewById(R.id.imageView);
        imageArrayLength = RestaurantActivity.imageIDs.size();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
