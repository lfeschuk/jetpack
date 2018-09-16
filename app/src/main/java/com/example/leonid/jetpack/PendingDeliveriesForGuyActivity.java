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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.DataBaseManager;
import Objects.Delivery;
import Objects.DeliveryGuys;
import Objects.Destination;
import layout.TouchListView;

public class PendingDeliveriesForGuyActivity extends AppCompatActivity {
    public PendingDeliveriesForGuyActivity(){}
    private Toolbar toolbar;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private ListAdapter adapter=null;
    TouchListView tlv;
    Delivery to_assign_delivery = null;
    DeliveryGuys chosen_delivery_guy = null;
    final static String TAG = "PendingDeliverForGuy";
    private DataBaseManager dbm = new DataBaseManager();
    private ArrayList<Destination> array = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_deliveries_activity);
        Log.d(TAG, "on intent");
        tlv=(TouchListView)findViewById(R.id.touch_listview_delivery_routes);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        final String delivery_key = b.getString("Delivery_Index");
        final String delivery_guy_key = b.getString("Delivery_Guy_Index");
        Log.d(TAG,"onCreate got from another intent Delivery_Index: " + delivery_key +" Delivery_Guy_index: " +delivery_guy_key);

        final Button assign_button = findViewById(R.id.button_assign_pending);
        Query q =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").orderByChild("index_string").equalTo(delivery_guy_key);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Delivery> deliv_array = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    TextView title = findViewById(R.id.main_title_text);
                    title.setText( temp.getName());
                    deliv_array = temp.getDeliveries();
                    chosen_delivery_guy = temp;
                    Log.d(TAG,"DeliveryGuy is :  " + temp.getName());
                }
                for(Delivery d : deliv_array)
                {
                    Destination to_business = new Destination(d,false);
                    Destination to_costumer = new Destination(d,true);
                    array.add(to_business);
                    array.add(to_costumer);
                }
                Log.d(TAG,"Done retrieving Destinations old " + array.size());
                tlv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        Query q2 =  FirebaseDatabase.getInstance().getReference("Deliveries").orderByChild("indexString").equalTo(delivery_key);
        q2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Delivery temp = new Delivery(ds.getValue(Delivery.class));
                    if (temp.getDelivery_guy_index_assigned().equals(chosen_delivery_guy.getIndex_string()) == false)
                    {
                        array.add(new Destination(temp,false));
                        array.add(new Destination(temp,true));
                    }
                    to_assign_delivery = temp;
                    assign_button.setClickable(true);
                    Log.d(TAG,"Delivery is :  " + temp.getIndexString());
                }
                Log.d(TAG,"Done retrieving Destinations new" + array.size());
                tlv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        adapter=new ListAdapter();
        tlv.setAdapter(adapter);
        tlv.setDropListener(onDrop);
        Log.d(TAG,"onCreate");

        //assign the button
        assign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onclick Status: " +to_assign_delivery.getStatus());
                if (to_assign_delivery.getStatus().equals("A"))
                {
                    to_assign_delivery.setStatus("B");
                }
                //its B
                else
                {
                    Log.d(TAG,"status B detected on assigning, removing from previous delivery guy");
                    Log.d(TAG,"DeliveryGuyPrev: " + to_assign_delivery.getDelivery_guy_index_assigned() +" DeliveryInd: " + to_assign_delivery.getIndexString());
                    dbm.remove_delivery_from_dguy(to_assign_delivery.getDelivery_guy_index_assigned(),to_assign_delivery.getIndexString());
                }
                to_assign_delivery.setDelivery_guy_index_assigned(chosen_delivery_guy.getIndex_string());
                chosen_delivery_guy.addDelivery(to_assign_delivery);
                dbm.writeDelivery(to_assign_delivery);
                dbm.writeDeliveryGuy(chosen_delivery_guy);
                Intent intent = new Intent(PendingDeliveriesForGuyActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
//        tlv.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//                                    long arg3) {
//                Log.d(TAG,"onTouch");
//                Destination d = array.get(position);
//                Toast.makeText(getApplicationContext(), "Touch delivery guy: " + d.getName(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }


    private TouchListView.DropListener onDrop=new TouchListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            // change the item position using from and to position
            Destination item=adapter.getItem(from);
            adapter.remove(item);
            adapter.insert(item, to);

        }
    };


    class ListAdapter extends ArrayAdapter<Destination> {
        ListAdapter() {
            super(PendingDeliveriesForGuyActivity.this, R.layout.adapter_layout_delivery_routes, array);
        }
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row=convertView;
            if (row==null) {
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.adapter_layout_delivery_routes, parent, false);
            }
            TextView index_delivery = row.findViewById(R.id.index_delivery_routes);
            index_delivery.setText(array.get(position).getIndex_string());
            TextView time_of_order = row.findViewById(R.id.time_of_order_routes);
            time_of_order.setText(array.get(position).getTimeInserted());
            TextView time_to_destination = row.findViewById(R.id.time_to_destination_routes);
            time_to_destination.setText(array.get(position).getTimeDeliver());
            TextView addresses = row.findViewById(R.id.addresses_routes);
            TextView from_where_the_delivery = row.findViewById(R.id.from_where_the_delivery_routes);
            if (array.get(position).getTo_costumer())
            {
                String adress_or_cost_name = "(" + array.get(position).getName_costumer() + ")";
                addresses.setText(array.get(position).getAdressTo() + adress_or_cost_name);
                from_where_the_delivery.setText("משלוח מ:" + array.get(position).getBusiness_name());
                from_where_the_delivery.setVisibility(View.VISIBLE);


            }
            else
            {
                String adress_or_cost_name = "(" + array.get(position).getAdressFrom() + ")";
                addresses.setText(array.get(position).getBusiness_name() + adress_or_cost_name);
            }

            return(row);
        }
    }

}
