package com.example.leonid.jetpack;

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
import Objects.DeliveryGuys;
import layout.TouchListView;

public class FragmentDeliveryGuys extends Fragment {
    public FragmentDeliveryGuys(){}
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FragmentDeliveryGuys.ListAdapter adapter=null;
    TouchListView tlv;
    final static String TAG = "FragmentDeliveriesGuy";
    private DataBaseManager dbm = new DataBaseManager();
    private ArrayList<DeliveryGuys> array = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment_view =  inflater.inflate(R.layout.fragment_delivery_guys, container, false);
        tlv=(TouchListView)fragment_view.findViewById(R.id.touch_listview_deliv_guys);
        mDatabase =  FirebaseDatabase.getInstance().getReference("Delivery_Guys");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    array.add(temp);
                    Log.d(TAG,"DeliveryGuy is :  " + temp.getName());
                }
                MainActivity.set_title_for_adapter(1,array.size());
                Log.d(TAG,"Done retrieving DeliveryGuy " + array.size());
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
        Log.d(TAG,"onCreateView");
        tlv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Log.d(TAG,"onTouch");
                DeliveryGuys d = array.get(position);
                Toast.makeText(getActivity(), "Touch delivery guy: " + d.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        return fragment_view;
    }
    private TouchListView.DropListener onDrop=new TouchListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            // change the item position using from and to position
            DeliveryGuys item=adapter.getItem(from);
            adapter.remove(item);
            adapter.insert(item, to);

        }
    };


    class ListAdapter extends ArrayAdapter<DeliveryGuys> {
        ListAdapter() {
            super(getActivity(), R.layout.adapter_layout_delivery_guys, array);
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
