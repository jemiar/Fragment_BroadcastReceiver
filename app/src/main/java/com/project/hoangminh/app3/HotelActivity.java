package com.project.hoangminh.app3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Arrays;

/*---------------------------------------------
Project 3 - App 3
HotelActivity class

Extends AppCompatActivity for ActionBar
Implements HotelFragment.ListSelectionListener interface
for communicating between fragments
----------------------------------------------*/

public class HotelActivity extends AppCompatActivity implements HotelFragment.ListSelectionListener{

    //arraylist of hotel name
    public static ArrayList<String> mHotelArray;
    //arraylist of hotel address
    public static ArrayList<String> mHotelAddrArray;
    //arraylist of hotel picture
    public static ArrayList<Integer> imageIDs = new ArrayList<Integer>(Arrays.asList(
            R.drawable.congress,
            R.drawable.hilton,
            R.drawable.marriot,
            R.drawable.palmer,
            R.drawable.seasons,
            R.drawable.sheraton));

    private FragmentManager mFragmentManager;
    private HotelPicFragment mHotelPicFrag = null;
    private HotelFragment mHotelFrag = null;
    private FrameLayout hotelList;
    private FrameLayout hotelPic;

    //store whether and which hotel is selected
    int mShownIndex = -1;
    static String OLD_ITEM = "old_item" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initiate hotel name array
        mHotelArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Hotels)));
        //initiate hotel address array
        mHotelAddrArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.HotelAddrs)));

        setContentView(R.layout.activity_hotel);

        //set the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //check whether having saved instance, and set the hotel selected
        if (savedInstanceState != null) {
            mShownIndex = savedInstanceState.getInt(OLD_ITEM) ;
        }

        //framelayout contains hotel list
        hotelList = (FrameLayout) findViewById(R.id.hotelList_container);
        //framelayout contains hotel picture
        hotelPic = (FrameLayout) findViewById(R.id.hotelPic_container);

        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //initiate 2 fragment
        mHotelFrag = new HotelFragment();
        mHotelPicFrag = new HotelPicFragment();

        //add the hotel list fragment to its container
        fragmentTransaction.add(R.id.hotelList_container, mHotelFrag);

        fragmentTransaction.commit();

        //set layout if there are changes in the backstack
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });
    }

    public void setLayout(){
        //if the hotel pic fragment hasn't been added, only display the hotel list fragment
        //else, if in landscape, add the hotel pic fragment to occupy 2/3 of the display
        if(!mHotelPicFrag.isAdded()){
            hotelList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            hotelPic.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        }else{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                hotelList.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                hotelPic.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f));
            }
        }
    }

    //implement the onLisSelection of the interface
    public void onListSelection(int index){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        //set the shown index to the index clicked
        mShownIndex = index;

        //if hotel pic fragment hasn't been added
        //if in portrait, replace the hotel list container. In landscape, add to its container
        if(!mHotelPicFrag.isAdded()) {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                fragmentTransaction.replace(R.id.hotelList_container, mHotelPicFrag);
            } else {
                fragmentTransaction.add(R.id.hotelPic_container, mHotelPicFrag);
            }

            fragmentTransaction.commit();

            mFragmentManager.executePendingTransactions();
        }

        //display the image
        mHotelPicFrag.showImageAtIndex(index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //used for if there is saved instance
    @Override
    protected void onStart() {
        super.onStart();
        if (mShownIndex >= 0) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            //if in portrait and hotel list fragment is added, set selection and set item checked
            //else in landscape and hotel pic fragment hasn't been added, add it to its container, then set selection and checked
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                if(mHotelFrag.isAdded()) {
                    mHotelFrag.setSelection(mShownIndex);
                    mHotelFrag.getListView().setItemChecked(mShownIndex, true);
                }
            }else{
                if(!mHotelPicFrag.isAdded()){
                    fragmentTransaction.add(R.id.hotelPic_container, mHotelPicFrag);
                }
                mHotelFrag.setSelection(mShownIndex);
                mHotelFrag.getListView().setItemChecked(mShownIndex,true);
            }

            fragmentTransaction.commit();
            mFragmentManager.executePendingTransactions();
            mHotelPicFrag.showImageAtIndex(mShownIndex);
        }
    }

    //create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //in case hotel pic fragment is shown and in portrait mode and user chooses hotels,
            //replace current fragment with hotel list fragment
            //otherwise, do nothing
            case R.id.hotels:
                if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                        mHotelPicFrag.isVisible()){
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.hotelList_container, mHotelFrag);
                    fragmentTransaction.commit();
                    mFragmentManager.executePendingTransactions();
                    mHotelFrag.setSelection(mShownIndex);
                    mHotelFrag.getListView().setItemChecked(mShownIndex,true);
                }
                return true;

            //choose restaurants item, start new activity by explicit intent
            case R.id.restaurants:
                Intent rIntent = new Intent(this, RestaurantActivity.class);
                startActivity(rIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //save the index selected
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(OLD_ITEM, mShownIndex);
    }
}
