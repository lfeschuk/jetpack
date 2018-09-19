package Objects;

import android.support.v4.util.Pair;

public class Delivery {

    private String indexString = "";
   private String adressTo = "";
   private String adressFrom = "";
   private String timeInserted = "";
   private String prepare_time = "";
   private String status = "A";
   private String comment = "";
   private String num_of_packets = "";
   private String costumer_phone = "";
   private String timeTaken = "";
   private String timeDeliver = "";
    private String costumerName = "";
    private String city = "";
    private String floor = "";
    private String entrance = "";
    private String building = "";
    private String street = "";
    private String apartment = "";
    private Boolean is_deleted = false;
    private String business_name = "";
    private String delivery_guy_index_assigned = "";
    public String deliveryGuyName = "";
    private double source_cord_lat;
    private double source_cord_long;
    private double dest_cord_lat;
   private double dest_cord_long;

    public String getDeliveryGuyName() {
        return deliveryGuyName;
    }

    public void setDeliveryGuyName(String deliveryGuyName) {
        this.deliveryGuyName = deliveryGuyName;
    }

    public double getSource_cord_lat() {
        return source_cord_lat;
    }

    public void setSource_cord_lat(double source_cord_lat) {
        this.source_cord_lat = source_cord_lat;
    }

    public double getSource_cord_long() {
        return source_cord_long;
    }

    public void setSource_cord_long(double source_cord_long) {
        this.source_cord_long = source_cord_long;
    }

    public double getDest_cord_lat() {
        return dest_cord_lat;
    }

    public void setDest_cord_lat(double dest_cord_lat) {
        this.dest_cord_lat = dest_cord_lat;
    }

    public double getDest_cord_long() {
        return dest_cord_long;
    }

    public void setDest_cord_long(double dest_cord_long) {
        this.dest_cord_long = dest_cord_long;
    }

    public String getDelivery_guy_index_assigned() {
        return delivery_guy_index_assigned;
    }

    public void setDelivery_guy_index_assigned(String delivery_guy_index_assigned) {
        this.delivery_guy_index_assigned = delivery_guy_index_assigned;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    //  private DeliveryGuys dGuy;

    public Delivery() {
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public String getCostumer_phone() {
        return costumer_phone;
    }

    public void setCostumer_phone(String costumer_phone) {
        this.costumer_phone = costumer_phone;
    }

    public String getIndexString() {
        return indexString;
    }

    public void setIndexString(String indexString) {
        this.indexString = indexString;
    }

    public Delivery(Integer index, String adressTo, String adressFrom, String timeInserted, String status, String comment,
                    String num_of_packets,String costumer_phone,String costumerName,
                    String city,String floor,String building, String entrance,String street,String apartment,
                    String business_name,String delivery_guy_index_assigned,
                     double source_cord_lat,double source_cord_long,double dest_cord_lat,double dest_cord_long,
                    String deliveryGuyName) {

        indexString = index.toString();
        this.adressTo = adressTo;
        this.adressFrom = adressFrom;
        this.timeInserted = timeInserted;
        this.status = status;
        this.comment = comment;
        this.num_of_packets = num_of_packets;
        this.prepare_time = "--";
        this.timeTaken = "--" ;
        this.timeDeliver = "--";
        this.costumer_phone = costumer_phone;
        this.costumerName = costumerName;
        this.city = city;
        this.floor = floor;
        this.entrance = entrance;
        this.building = building;
        this.street = street;
        this.apartment = apartment;
        this.business_name = business_name;
        this.delivery_guy_index_assigned = delivery_guy_index_assigned;
        this.source_cord_lat = source_cord_lat;
        this.source_cord_long = source_cord_long;
        this.dest_cord_lat = dest_cord_lat;
        this.dest_cord_long = dest_cord_long;
        this.deliveryGuyName = deliveryGuyName;
      //  this
    }

public Delivery( Delivery d)
{

    this.indexString = d.getIndexString();
    this.adressTo = d.getAdressTo();
    this.adressFrom = d.getAdressFrom();
    this.timeInserted = d.getTimeInserted();
    this.status = d.getStatus();
    this.comment = d.getComment();
    this.num_of_packets = d.getNum_of_packets();
    this.prepare_time = d.getPrepare_time();
    this.timeTaken = d.getTimeTaken() ;
    this.timeDeliver = d.getTimeDeliver();
    this.costumer_phone = d.getCostumer_phone();
    this.costumerName = d.getCostumerName();
    this.building = d.getBuilding();
    this.floor = d.getFloor();
    this.entrance = d.getEntrance();
    this.city = d.getCity();
    this.street = d.getStreet();
    this.apartment = d.getApartment();
    this.business_name = d.getBusiness_name();
    this.delivery_guy_index_assigned = d.getDelivery_guy_index_assigned();
    this.source_cord_lat = d.getSource_cord_lat();
    this.source_cord_long = d.getSource_cord_long();
    this.dest_cord_lat = d.getDest_cord_lat();
    this.dest_cord_long = d.getDest_cord_long();
    this.deliveryGuyName = d.getDeliveryGuyName();
}




    public String getAdressTo() {
        return adressTo;
    }

    public void setAdressTo(String adressTo) {
        this.adressTo = adressTo;
    }

    public String getAdressFrom() {
        return adressFrom;
    }

    public void setAdressFrom(String adressFrom) {
        this.adressFrom = adressFrom;
    }

    public String getPrepare_time() {
        return prepare_time;
    }

    public void setPrepare_time(String prepare_time) {
        this.prepare_time = prepare_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNum_of_packets() {
        return num_of_packets;
    }

    public void setNum_of_packets(String num_of_packets) {
        this.num_of_packets = num_of_packets;
    }

//    public DeliveryGuys getdGuy() {
//        return dGuy;
//    }
//
//    public void setdGuy(DeliveryGuys dGuy) {
//        this.dGuy = dGuy;
//    }

    public String getTimeInserted() {
        return timeInserted;
    }

    public void setTimeInserted(String timeInserted) {
        this.timeInserted = timeInserted;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getTimeDeliver() {
        return timeDeliver;
    }

    public void setTimeDeliver(String timeDeliver) {
        this.timeDeliver = timeDeliver;
    }
}
