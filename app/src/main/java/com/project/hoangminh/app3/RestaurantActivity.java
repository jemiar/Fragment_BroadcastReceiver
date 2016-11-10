package com.project.hoangminh.app3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
RestaurantActivity class

Same as HotelActivity class
----------------------------------------------*/

public class RestaurantActivity extends AppCompatActivity implements RestaurantFragment.ListSelectionListener{

    public static ArrayList<String> mRestaurantArray;
    public static ArrayList<String> mRestaurantAddrArray;
    public static ArrayList<Integer> imageIDs = new ArrayList<Integer>(Arrays.asList(
            R.drawable.acadia,
            R.drawable.alinea,
            R.drawable.blackbird,
            R.drawable.grace,
            R.drawable.momotaro,
            R.drawable.oriole));

    private FragmentManager mFragmentManager;
    private RestaurantPicFragment mRestaurantPicFrag = null;
    private RestaurantFragment mRestaurantFrag = null;
    private FrameLayout restaurantList;
    private FrameLayout restaurantPic;

    int mShownIndex = -1;
    static String OLD_ITEM = "old_item" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRestaurantArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Restaurants)));
        mRestaurantAddrArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.RestaurantAddrs)));

        setContentView(R.layout.activity_restaurant);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (savedInstanceState != null) {
            mShownIndex = savedInstanceState.getInt(OLD_ITEM) ;
        }

        restaurantList = (FrameLayout) findViewById(R.id.restaurantList_container);
        restaurantPic = (FrameLayout) findViewById(R.id.restaurantPic_container);

        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        mRestaurantFrag = new RestaurantFragment();
        mRestaurantPicFrag = new RestaurantPicFragment();

        fragmentTransaction.add(R.id.restaurantList_container, mRestaurantFrag);

        fragmentTransaction.commit();

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });
    }

    public void setLayout(){
        if(!mRestaurantPicFrag.isAdded()){
            restaurantList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            restaurantPic.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        }else{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                restaurantList.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                restaurantPic.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f));
            }
        }
    }

    public void onListSelection(int index){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        mShownIndex = index;

        if(!mRestaurantPicFrag.isAdded()) {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                fragmentTransaction.replace(R.id.restaurantList_container, mRestaurantPicFrag);
            } else {
                fragmentTransaction.add(R.id.restaurantPic_container, mRestaurantPicFrag);
            }

            fragmentTransaction.commit();

            mFragmentManager.executePendingTransactions();
        }

        mRestaurantPicFrag.showImageAtIndex(index);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (mShownIndex >= 0) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                if(mRestaurantFrag.isAdded()) {
                    mRestaurantFrag.setSelection(mShownIndex);
                    mRestaurantFrag.getListView().setItemChecked(mShownIndex, true);
                }
            }else{
                if(!mRestaurantPicFrag.isAdded()) {
                    fragmentTransaction.add(R.id.restaurantPic_container, mRestaurantPicFrag);
                }
                mRestaurantFrag.setSelection(mShownIndex);
                mRestaurantFrag.getListView().setItemChecked(mShownIndex,true);
            }
            fragmentTransaction.commit();
            mFragmentManager.executePendingTransactions();
            mRestaurantPicFrag.showImageAtIndex(mShownIndex);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hotels:
                Intent hIntent = new Intent(this, HotelActivity.class);
                startActivity(hIntent);
                return true;

            case R.id.restaurants:
                if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                        mRestaurantPicFrag.isVisible()){
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.restaurantList_container, mRestaurantFrag);
                    fragmentTransaction.commit();
                    mFragmentManager.executePendingTransactions();
                    mRestaurantFrag.setSelection(mShownIndex);
                    mRestaurantFrag.getListView().setItemChecked(mShownIndex,true);
                }
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

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(OLD_ITEM, mShownIndex);
    }
}
