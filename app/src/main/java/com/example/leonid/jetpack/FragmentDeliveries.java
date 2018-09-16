package com.example.leonid.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.DataBaseManager;
import Objects.Delivery;
import layout.TouchListView;

public class FragmentDeliveries extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private ListAdapter adapter=null;
    TouchListView tlv;
    final static String TAG = "FragmentDeliveries";
    private DataBaseManager dbm = new DataBaseManager();
    private ArrayList<Delivery> array = new ArrayList<>();
    public FragmentDeliveries(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragment_view =  inflater.inflate(R.layout.fragment_deliveries, container, false);

        tlv=(TouchListView)fragment_view.findViewById(R.id.touch_listview_delivery);
        mDatabase =  FirebaseDatabase.getInstance().getReference("Deliveries");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Delivery temp = new Delivery(ds.getValue(Delivery.class));
                    array.add(temp);
                    Log.d(TAG,"Delivery is :  " + temp);
                }
                MainActivity.set_title_for_adapter(0,array.size());
                Log.d(TAG,"Done retrieving Deliveries " + array.size());
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
        tlv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Log.d(TAG,"onTouch");
                Delivery d = array.get(position);
                Toast.makeText(getActivity(), "Touch delivery guy: " + d.getIndexString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DeliveryDataActivity.class);
                Bundle b = new Bundle();
                Log.d(TAG,"passing index string " + d.getIndexString());
         //       b.putInt("Delivery_Index", Integer.valueOf(d.getIndexString()) ); //Your i

                b.putString("Delivery_Index",d.getIndexString());
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);

            }
        });
        return fragment_view;

    }
    private TouchListView.DropListener onDrop=new TouchListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            // change the item position using from and to position
            Delivery item=adapter.getItem(from);
            adapter.remove(item);
            adapter.insert(item, to);

        }
    };

//    private final class MyTouchListener implements View.OnTouchListener {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ClipData data = ClipData.newPlainText("", "");
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
//                        view);
//                view.startDrag(data, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
class ListAdapter extends ArrayAdapter<Delivery> {
    ListAdapter() {
        super(getActivity(), R.layout.adapter_layout_deliveries, array);
    }
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View row=convertView;
        if (row==null) {
            LayoutInflater inflater=getLayoutInflater();
            row=inflater.inflate(R.layout.adapter_layout_deliveries, parent, false);
        }
       // Log.d(TAG,"Array_ size  is :  " + array.size());
        TextView index_delivery=(TextView)row.findViewById(R.id.index_delivery);
        index_delivery.setText(array.get(position).getIndexString());
        TextView time_of_order=(TextView)row.findViewById(R.id.time_of_order);
        time_of_order.setText(array.get(position).getTimeInserted());
        TextView time_to_prepare=(TextView)row.findViewById(R.id.time_to_prepare);
        time_to_prepare.setText(array.get(position).getPrepare_time());
        TextView status=(TextView)row.findViewById(R.id.status);
        status.setText(array.get(position).getStatus());
        TextView addresses=(TextView)row.findViewById(R.id.addresses);
        addresses.setText(array.get(position).getAdressTo()+ "-" + array.get(position).getAdressFrom());
        TextView time_taken=(TextView)row.findViewById(R.id.time_taken);
        time_taken.setText(array.get(position).getTimeTaken());
        TextView delta_taken=(TextView)row.findViewById(R.id.delta_taken);
        delta_taken.setText("+2");
        TextView time_arrived=(TextView)row.findViewById(R.id.time_arrived);
        time_arrived.setText(array.get(position).getTimeDeliver());
        TextView delta_arrived=(TextView)row.findViewById(R.id.delta_arrived);
        delta_arrived.setText("-2");
        TextView comment=(TextView)row.findViewById(R.id.comment);
        comment.setText(array.get(position).getComment());
        TextView num_of_packages=(TextView)row.findViewById(R.id.num_of_packages);
        num_of_packages.setText(array.get(position).getNum_of_packets().toString() + "חבילות");



        return(row);
    }
}

}
