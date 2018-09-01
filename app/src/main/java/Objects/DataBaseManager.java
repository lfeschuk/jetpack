package Objects;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

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
        mDatabase.child("Deliveries").child(delivery.getIndexString()).setValue(delivery);
    }
    public void writeMessage()
    {
        mDatabase.setValue("Hello, World!");
    }
    public ArrayList<Delivery> get_deliveries()
    {

       final ArrayList<Delivery> out = new ArrayList<>();
        mDatabase =  FirebaseDatabase.getInstance().getReference("Deliveries");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Delivery temp = new Delivery(ds.getValue(Delivery.class));
                    out.add(temp);
                   // Log.d(TAG,temp.getAdressFrom() + temp.getIndexString() +" "+ out.size());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return out;

    }
}
