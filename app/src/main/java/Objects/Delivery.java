package Objects;

public class Delivery {
    private Integer index;
    private String indexString;
   private String adressTo;
   private String adressFrom;
   private String timeInserted;
   private String prepare_time;
   private String status;
   private String comment;
   private Integer num_of_packets;
   private String timeTaken;
   private String timeDeliver;
 //  private DeliveryGuys dGuy;

    public Delivery() {
    }

    public String getIndexString() {
        return indexString;
    }

    public void setIndexString(String indexString) {
        this.indexString = indexString;
    }

    public Delivery(Integer index, String adressTo, String adressFrom, String timeInserted, String status, String comment, int num_of_packets) {
        this.index = index;
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
      //  this
    }

public Delivery( Delivery d)
{
    this.index = d.getIndex();
    indexString = d.getIndexString();
    this.adressTo = d.getAdressTo();
    this.adressFrom = d.getAdressFrom();
    this.timeInserted = d.getTimeInserted();
    this.status = d.getStatus();
    this.comment = d.getComment();
    this.num_of_packets = d.getNum_of_packets();
    this.prepare_time = d.getPrepare_time();
    this.timeTaken = d.getTimeTaken() ;
    this.timeDeliver = d.getTimeDeliver();

}
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public Integer getNum_of_packets() {
        return num_of_packets;
    }

    public void setNum_of_packets(int num_of_packets) {
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
