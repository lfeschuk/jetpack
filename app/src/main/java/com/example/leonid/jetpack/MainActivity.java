package com.example.leonid.jetpack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Objects.DataBaseManager;
import Objects.Delivery;
import Objects.DeliveryGuys;

public class MainActivity extends AppCompatActivity {
DrawerLayout dLayout;
    private Toolbar toolbar;
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    public static  ViewPagerAdapter adapter;
    Fragment deliveries_fragment;
    Fragment delivery_guys_fragment;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

public static final String TAG = "MainActivity";
DataBaseManager dbm = new DataBaseManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();
//        Delivery delivery = new Delivery(3,"עמק האלה 54","פיצה מאנצ","12:10","A","אל תאחרו",3);
//       dbm.writeDelivery(delivery);
       // dbm.writeMessage();
//        DeliveryGuys deliveryGuy = new DeliveryGuys("לאוניד","12:00",null,123456,123456,"please be hurry","1",true);
//         dbm.writeDeliveryGuy(deliveryGuy);
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + 31.8903 + "," +  35.0104 + "&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
       Log.d(TAG,"send to Db");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        deliveries_fragment = new FragmentDeliveries();
        delivery_guys_fragment = new FragmentDeliveryGuys();

        adapter.addFragment(deliveries_fragment, "משלוחים");
        adapter.addFragment(delivery_guys_fragment, "שליחים");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment frag = null; // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
// check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.nav_1) {
                    frag = new FirstFragment();
                } else if (itemId == R.id.nav_2) {
                    frag = null;
                    Log.d(TAG,"maps checked");
                } else if (itemId == R.id.nav_3) {
                    frag = new DelayedDeliveryFragment();
                } else if (itemId == R.id.nav_4) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_15) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_5) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_6) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_7) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_8) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_9) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_10) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_11) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_12) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_13) {
                    frag = new ThirdFragment();
                } else if (itemId == R.id.nav_14) {
                    frag = new ThirdFragment();
                }
// display a toast message with menu item's title
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, frag); // replace a Fragment with Frame Layout
                  //  transaction.addToBackStack(frag.getClass().getSimpleName()); //maybe null instead
                //    transaction.addToBackStack(deliveries_fragment.getClass().getSimpleName());
                 //   transaction.remove(deliveries_fragment);
                    transaction.addToBackStack(BACK_STACK_ROOT_TAG); //maybe null instead
                    transaction.commit(); // commit the changes
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }
                else
                {
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
    public static void set_title_for_adapter(int pos,int num)
    {
        Log.d(TAG,"set_title_for_adapter  pos: " + pos + " num: " + num);
       String set_text =  tabLayout.getTabAt(pos).getText() + "(" + num + ")";
       tabLayout.getTabAt(pos).setText(set_text);
    }
}
