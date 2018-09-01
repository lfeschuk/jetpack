package com.example.leonid.jetpack;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Objects.DataBaseManager;
import Objects.Delivery;

public class MainActivity extends AppCompatActivity {
DrawerLayout dLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
public static final String TAG = "MainActivity";
DataBaseManager dbm = new DataBaseManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();
//        Delivery delivery = new Delivery(2,"עמק האלה","פיצה עבגניה","12:35","A","please be hurry",1);
//       dbm.writeDelivery(delivery);
       // dbm.writeMessage();
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDeliveries(), "ONE");
        adapter.addFragment(new FragmentDeliveryGuys(), "TWO");
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
                    frag = new DelayedDeliveryFragment();
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
                 //   transaction.replace(R.id.frame, frag); // replace a Fragment with Frame Layout
                    transaction.commit(); // commit the changes
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }
                return false;
            }
        });
    }
}
