package Objects;

public class Business {

    String name = "";
    String index = "";
    double longt = 35.0104;
    double lat = 31.8903;

    public Business(String name,String index,double longt,double lat)
    {
        this.name = name;
        this.longt = longt;
        this.lat = lat;
        this.index = index;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}