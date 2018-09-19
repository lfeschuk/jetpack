package Objects;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public  class DataBaseManager {
    private  DatabaseReference mDatabase ;
     static final String TAG = "DataBaseManager";
   public DataBaseManager()
   {
       mDatabase  = FirebaseDatabase.getInstance().getReference();

   }
    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

  public  void writeDelivery(Delivery delivery)
    {
        Log.d(TAG,"writeDelivery deliveryName: " +delivery.getBusiness_name() + " delivery assign guy: " + delivery.getDeliveryGuyName());
        mDatabase.child("Deliveries").child(delivery.getIndexString()).setValue(delivery);
    }
    public  void writeDeliveryGuy(DeliveryGuys deliveryGuys)
    {
        Log.d(TAG,"writeDeliveryGuy deliveryGuyName: " +deliveryGuys.getName());
        mDatabase.child("Delivery_Guys").child(deliveryGuys.getIndex_string()).setValue(deliveryGuys);
    }
    public void remove_delivery_from_dguy(String index_string_guy, final String index_string_delivery)
    {
        Log.d(TAG,"remove_delivery_from_dguy + guy: " + index_string_guy + " deliv: " + index_string_delivery);
        Query q2 =  FirebaseDatabase.getInstance().getReference("Delivery_Guys").orderByChild("index_string").equalTo(index_string_guy);
        q2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DeliveryGuys temp = new DeliveryGuys(ds.getValue(DeliveryGuys.class));
                    Log.d(TAG,"deliveries array size "+ temp.getDeliveries().size() );
                    temp.removeDelivery(index_string_delivery);
                    writeDeliveryGuy(temp);
                    Log.d(TAG,"remove_delivery_from_dguy DeliveryGuy is :  " + temp.getIndex_string());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public void writeMessage()
    {
        mDatabase.setValue("Hello, World!");
    }
    public void deleteDelivery(Delivery delivery)
    {
        String index = delivery.getIndexString();
        mDatabase.child("Deliveries").removeValue();
        Log.d(TAG,"remove Delivery: " + index);
    }
}
