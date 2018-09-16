package Objects;

import android.util.Log;

import java.util.ArrayList;

public class DeliveryGuys {
    public static final String TAG = "DeliveryGuys";
    String name = "";
    String timeBeFree = "";
   ArrayList<Delivery> deliveries = new ArrayList<>();
    int latetude = 0;
    int longtitude = 0;
    String picture = "";
    String index_string = "";
    Boolean is_active = true;

    public DeliveryGuys(String name, String timeBeFree, ArrayList<Delivery> deliveries, int latetude, int longtitude, String picture,String index_string,Boolean is_active) {
        this.name = name;
        this.timeBeFree = timeBeFree;
        if (deliveries != null)
        {
            this.deliveries = deliveries;
        }
        this.latetude = latetude;
        this.longtitude = longtitude;
        this.picture = picture;
        this.index_string = index_string;
        this.is_active = is_active;
    }
    public DeliveryGuys(){}

    public DeliveryGuys( DeliveryGuys d)
    {
        this.name = d.getName();
        this.timeBeFree = d.getTimeBeFree();
        this.deliveries = d.getDeliveries();
        this.latetude = d.getLatetude();
        this.longtitude = d.getLongtitude();
        this.picture = d.getPicture();
        this.index_string = d.getIndex_string();
        this.is_active = d.getIs_active();
    }

    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(ArrayList<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public void addDelivery(Delivery d)
    {
        this.deliveries.add(d);
    }
    public void removeDelivery(String delivery_index)
    {
        for (Delivery d : deliveries)
        {
            Log.d(TAG,"iter indx: "+ d.getIndexString() + " wanted: " +delivery_index);
            if (d.getIndexString().equals(delivery_index))
            {
                Log.d(TAG,"remove delivery from guy: " + index_string + " deliv: " + delivery_index);
                deliveries.remove(d);
                return;
            }
        }
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getIndex_string() {
        return index_string;
    }

    public void setIndex_string(String index_string) {
        this.index_string = index_string;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeBeFree() {
        return timeBeFree;
    }

    public void setTimeBeFree(String timeBeFree) {
        this.timeBeFree = timeBeFree;
    }



    public int getLatetude() {
        return latetude;
    }

    public void setLatetude(int latetude) {
        this.latetude = latetude;
    }

    public int getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(int longtitude) {
        this.longtitude = longtitude;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
