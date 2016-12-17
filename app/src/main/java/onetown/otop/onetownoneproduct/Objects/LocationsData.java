package onetown.otop.onetownoneproduct.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by root on 11/17/16.
 */

public class LocationsData implements Parcelable {

    public int _id;
    public String locationName;
    public String locationProducts;
    public double locationLatitude;
    public double locationLongitude;
    public String image_path;

    public LocationsData() {

    }
    public LocationsData( String locationName,String locationProducts, double locationLatitude, double locationLongitude,String image_path) {
        this.locationName=locationName;
        this.locationProducts= locationProducts;
        this.locationLatitude=locationLatitude;
        this.locationLongitude=locationLongitude;
        this.image_path=image_path;
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

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String toString() {
        return "Location: "+locationName+"\n"
                +"Location Products: "+locationProducts+"\n"
                +"Location Latitude: "+locationLatitude+"\n"
                +"Location Longitude: "+locationLongitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.locationName);
        dest.writeString(this.locationProducts);
        dest.writeDouble(this.locationLatitude);
        dest.writeDouble(this.locationLongitude);
        dest.writeString(this.image_path);
    }

    private void readFromParcel(Parcel in) {
        _id=in.readInt();
        locationName= in.readString();
        locationProducts= in.readString();
        locationLatitude= in.readDouble();
        locationLongitude= in.readDouble();
        image_path= in.readString();
    }

  protected LocationsData(Parcel in) {
        this._id = in.readInt();
        this.locationName = in.readString();
        this.locationProducts = in.readString();
        this.locationLatitude = in.readDouble();
        this.locationLongitude = in.readDouble();
        this.image_path = in.readString();
    }

    public static final Parcelable.Creator<LocationsData> CREATOR = new Parcelable.Creator<LocationsData>() {
        @Override
        public LocationsData createFromParcel(Parcel source) {
            return new LocationsData(source);
        }

        @Override
        public LocationsData[] newArray(int size) {
            return new LocationsData[size];
        }
    };
}
