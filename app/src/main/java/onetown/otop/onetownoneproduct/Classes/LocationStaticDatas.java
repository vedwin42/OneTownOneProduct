package onetown.otop.onetownoneproduct.Classes;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import onetown.otop.onetownoneproduct.Activity.MainActivity;
import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Objects.LocationsData;

/**
 * Created by root on 11/20/16.
 */

public class LocationStaticDatas {

    public LocationStaticDatas(DBHelper helper) {

        // Region 11
        helper.addNewLocation(new LocationsData("Davao City","Banana Chips",7.253501,125.1708687));
        helper.addNewLocation(new LocationsData("Davao Del Sur","Processed Mango",6.4653752,124.2872263));
        helper.addNewLocation(new LocationsData("Davao Del Norte","Banana Chips",7.4455742,125.036961));
        helper.addNewLocation(new LocationsData("Davao Oriental","Coconut-based Products",7.1382151,125.1483339));
        helper.addNewLocation(new LocationsData("Compostela Valley","Gold and Silver Jewelry-Making",7.5384869,125.4260916));
        helper.addNewLocation(new LocationsData("Sarangani","Mango",6.0171631,124.3854389));
    }




}
