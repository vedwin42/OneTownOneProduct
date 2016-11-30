package onetown.otop.onetownoneproduct.Classes;

import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;

/**
 * Created by root on 11/20/16.

 import onetown.otop.onetownoneproduct.Activity.MainActivity;
 */

public class LocationStaticDatas {

    public LocationStaticDatas(DBHelper helper) {

        // Region 11
        helper.addNewLocation(new LocationsData("Davao City","Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",7.253501,125.1708687, String.valueOf(R.drawable.davaocity)));
        helper.addNewLocation(new LocationsData("Davao Del Sur","Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",6.4653752,124.2872263,String.valueOf(R.drawable.davaocity)));
        helper.addNewLocation(new LocationsData("Davao Del Norte","Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",7.4455742,125.036961,String.valueOf(R.drawable.davaocity)));
        helper.addNewLocation(new LocationsData("Davao Oriental","Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",7.1382151,125.1483339,String.valueOf(R.drawable.davaocity)));
        helper.addNewLocation(new LocationsData("Compostela Valley","Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",7.5384869,125.4260916,String.valueOf(R.drawable.davaocity)));
        helper.addNewLocation(new LocationsData("Sarangani","Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",6.0171631,124.3854389,String.valueOf(R.drawable.davaocity)));
    }




}
