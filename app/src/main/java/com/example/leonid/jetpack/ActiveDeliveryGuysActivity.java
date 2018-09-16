package com.example.leonid.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Delivery;
import Objects.DeliveryGuys;
import layout.TouchListView;

public class ActiveDeliveryGuysActivity extends AppCompatActivity {

    public static final String TAG = "ActiveDeliveryGuysAct";
    private  Toolbar toolbar;
    public Delivery clicked_delivery = null;
    private ArrayList<DeliveryGuys> array = new ArrayList<>();
    private ListAdapter adapter=null;
    TouchListView tlv;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_delivery_guys_activity);
        Log.d(TAG, "on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlv=findViewById(R.id.touch_listview_deliv_guys);

        //query for active deliveries
        Query q =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").orderByChild("is_active").equalTo(true);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    array.add(temp);
                    Log.d(TAG,"DeliveryGuy is :  " + temp.getName());
                }
                Log.d(TAG,"Done retrieving DeliveryGuy " + array.size());
                tlv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //get the clicked delivery

//        mDatabase =  FirebaseDatabase.getInstance().getReference("Deliveries");
//        mDatabase.child(delivery_key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                clicked_delivery = new Delivery(dataSnapshot.getValue(Delivery.class));
//                Log.d(TAG,"the delivery is " + clicked_delivery.getIndexString() + " " + clicked_delivery.getAdressFrom() + clicked_delivery);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        Bundle b = getIntent().getExtras();
        final String delivery_key = b.getString("Delivery_Index");

        adapter=new ListAdapter();
        tlv.setAdapter(adapter);
        tlv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                DeliveryGuys d = array.get(position);
                Bundle b = new Bundle();
                Intent intent = new Intent(ActiveDeliveryGuysActivity.this, PendingDeliveriesForGuyActivity.class);
                b.putString("Delivery_Index",delivery_key);
                b.putString("Delivery_Guy_Index",d.getIndex_string());
                Log.d(TAG,"onTouch send to another intent Delivery_Index: " + delivery_key +" Delivery_Guy_index: " +d.getIndex_string());
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
//                d.addDelivery(clicked_delivery);
//                clicked_delivery.setStatus("B");
                Toast.makeText(getApplicationContext(), "Touch delivery guy: " + d.getName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    class ListAdapter extends ArrayAdapter<DeliveryGuys> {
        ListAdapter() {
            super(ActiveDeliveryGuysActivity.this, R.layout.adapter_layout_delivery_guys, array);
        }
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row=convertView;
            if (row==null) {
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.adapter_layout_delivery_guys, parent, false);
            }
            TextView delivery_guy_name = (TextView)row.findViewById(R.id.deliv_guys_name);
            delivery_guy_name.setText(array.get(position).getName());
            //    Button delivery_guy_button = (Button)row.findViewById(R.id.deliv_guys_button);



            return(row);
        }
    }

}
