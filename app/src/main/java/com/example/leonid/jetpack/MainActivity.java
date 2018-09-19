package com.example.leonid.jetpack;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.here.android.mpa.common.GeoCoordinate;
//import com.here.android.mpa.routing.CoreRouter;
//import com.here.android.mpa.search.ErrorCode;
//import com.here.android.mpa.search.GeocodeRequest;
//import com.here.android.mpa.search.ResultListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Objects.DataBaseManager;
import Objects.Delivery;
import Objects.DeliveryGuys;
import Objects.DirectionsJSONParser;
import Objects.DistanceDuration;

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
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

public static final String TAG = "MainActivity";
DataBaseManager dbm = new DataBaseManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();


//        Integer index, String adressTo, String adressFrom, String timeInserted, String status, String comment,
//                String num_of_packets,String costumer_phone,String costumerName,
//                String city,String floor,String building, String entrance,String street,String apartment,
//                String business_name,String delivery_guy_index_assigned,
//        double source_cord_lat,double source_cord_long,double dest_cord_lat,double dest_cord_long,
//        String deliveryGuyName


        //getAddressFromLocation(31.919018100000002,34.984552799999996);
//        Delivery delivery = new Delivery(1,"עמק האלה","מרכז שמשוני","23:00","A",
//                "אל תאחרו","3","0525410912","לאוניד כהן","מודיעין","1",
//                "54","1","עמק האלה","6","ביג אפל פיצה","",
//                31.911910,35.001865,31.913116,35.007167,"");
//       dbm.writeDelivery(delivery);
//         delivery = new Delivery(2,"עמק החולה","דומינוס שמשוני","23:10","A",
//                "אל תאחרו בבקשה","1","0525410944","יוסי לוי","מודיעין","1",
//                "54","1","עמק החולה","3","דומינוס פיצה שמשוני","",
//                 31.893120,34.960698,31.895772,35.016540,"");
//        dbm.writeDelivery(delivery);
       // dbm.writeMessage();
//        DeliveryGuys deliveryGuy = new DeliveryGuys("לאוניד","12:00",null,123456,123456,"please be hurry","1",true);
//         dbm.writeDeliveryGuy(deliveryGuy);
//        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + 31.8903 + "," +  35.0104 + "&mode=w");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);
       Log.d(TAG,"send to Db");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void requestPermissions() {

        final List<String> requiredSDKPermissions = new ArrayList<String>();
        requiredSDKPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        requiredSDKPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requiredSDKPermissions.add(Manifest.permission.INTERNET);
        requiredSDKPermissions.add(Manifest.permission.ACCESS_WIFI_STATE);
        requiredSDKPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);

        ActivityCompat.requestPermissions(this,
                requiredSDKPermissions.toArray(new String[requiredSDKPermissions.size()]),
                REQUEST_CODE_ASK_PERMISSIONS);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                for (int index = 0; index < permissions.length; index++) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {

                        /**
                         * If the user turned down the permission request in the past and chose the
                         * Don't ask again option in the permission request system dialog.
                         */
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                                permissions[index])) {
                            Toast.makeText(this,
                                    "Required permission " + permissions[index]
                                            + " not granted. Please go to settings and turn on for sample app",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this,
                                    "Required permission " + permissions[index] + " not granted",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }

                /**
                 * All permission requests are being handled.Create map fragment view.Please note
                 * the HERE SDK requires all permissions defined above to operate properly.
                 */
              //  m_mapFragmentView = new MapFragmentView(this);
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this);

      //  Geocoder gc = new Geocoder(context);
        if(geocoder.isPresent()){
            List<Address> list = null;
            try {
               // list = geocoder.getFromLocationName("עמק האלה 103 מודיעין",1);
                list = geocoder.getFromLocation(latitude,longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = list.get(0);
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            StringBuffer str = new StringBuffer();
            str.append("Name: " + address.getLocality() + "\n");
            str.append("Sub-Admin Ares: " + address.getSubAdminArea() + "\n");
            str.append("Admin Area: " + address.getAdminArea() + "\n");
            str.append("Country: " + address.getCountryName() + "\n");
            str.append("Country Code: " + address.getCountryCode() + "\n");
            String strAddress = str.toString();
            address.getAddressLine(0);
     //      Log.d(TAG,"lat: " + lat + " long: " + lng);
            Log.d(TAG,"addr: " +  address.getAddressLine(0));




        }

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
