package com.project.hoangminh.app3;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/*---------------------------------------------
Project 3 - App 3
HotelFragment class

Extends ListFragment
----------------------------------------------*/

public class HotelFragment extends ListFragment {

    private ListSelectionListener mListener = null;
    private int mCurrentIndex = -1;
    static final String OLD_POSITION = "oldPos" ;
    Integer mOldPosition = null ;

    //define interface, so attaching activity must implement for communication
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Set the ListSelectionListener for communicating with the QuoteViewerActivity
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState) ;
        if (savedInstanceState != null) {
            int oldPosition = savedInstanceState.getInt(OLD_POSITION) ;
            mOldPosition = oldPosition ;
        }
        return view ;
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set the list adapter for the ListView
        setListAdapter(new TextAdapter(getActivity(), HotelActivity.mHotelArray, HotelActivity.mHotelAddrArray));

        // If an item has been selected, set its checked state
        if (mCurrentIndex != -1)
            getListView().setItemChecked(mCurrentIndex, true);
    }

    //implement itemclick from ListFragment
    @Override
    public void onListItemClick(ListView lv, View v, int pos, long id) {
        mCurrentIndex = pos;
        mListener.onListSelection(pos);
        lv.setItemChecked(mCurrentIndex, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //for saved state existed
    @Override
    public void onStart() {
        super.onStart();
        if (mOldPosition != null) {
            int oldPosition = mOldPosition;
            getListView().setSelection(oldPosition);
            mListener.onListSelection(oldPosition);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
