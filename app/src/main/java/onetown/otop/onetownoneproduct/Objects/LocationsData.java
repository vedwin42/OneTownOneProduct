package onetown.otop.onetownoneproduct.Objects;

import java.io.Serializable;

/**
 * Created by root on 11/17/16.
 */

public class LocationsData implements Serializable{

    private int _id;
    private String locationName;
    private String locationProducts;
    private double locationLatitude;
    private double locationLongitude;

    public LocationsData() {

    }
    public LocationsData( String locationName,String locationProducts, double locationLatitude, double locationLongitude) {
        this.locationName=locationName;
        this.locationProducts= locationProducts;
        this.locationLatitude=locationLatitude;
        this.locationLongitude=locationLongitude;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationProducts() {
        return locationProducts;
    }

    public void setLocationProducts(String locationProducts) {
        this.locationProducts = locationProducts;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
