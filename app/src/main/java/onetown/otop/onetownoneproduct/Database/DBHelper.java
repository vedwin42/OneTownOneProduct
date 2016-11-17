package onetown.otop.onetownoneproduct.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import onetown.otop.onetownoneproduct.Objects.LocationsData;

/**
 * Created by root on 11/17/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION=1;
    private static String DB_NAME="LocationData";
    private static String TBL_ID="id";

    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    String sqlquery= "CREATE TABLE location ("+TBL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, locationName TEXT, locationProducts TEXT," +
            "locationLat DECIMAL, locationLong DECIMAL)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlquery);
        Log.i("onCreate",String.valueOf(db.getPageSize()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public LocationsData addNewLocation(LocationsData data) {

        SQLiteDatabase db= getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put("locationName",data.getLocationName());
        values.put("locationProducts",data.getLocationProducts());
        values.put("locationLat",data.getLocationLatitude());
        values.put("locationLong",data.getLocationLongitude());

        //db.insert("location",null,values);
        db.insertWithOnConflict("location",null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();

        return data;

    }
    // Get All Location rows and their details
    public ArrayList<LocationsData> getAllLocationsAndDatas() {
        SQLiteDatabase db= getReadableDatabase();
        LocationsData locationsData= new LocationsData();
        ArrayList<LocationsData> locations= new ArrayList<LocationsData>();

        String query= "SELECT * FROM location";
        Log.i("Query ",query);
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()) {

            do{

            locationsData.set_id(c.getInt(c.getColumnIndex(TBL_ID)));
            locationsData.setLocationName(c.getString(c.getColumnIndex("locationName")));
            locationsData.setLocationProducts(c.getString(c.getColumnIndex("locationProducts")));
            locationsData.setLocationLatitude(c.getDouble(c.getColumnIndex("locationLat")));
            locationsData.setLocationLongitude(c.getDouble(c.getColumnIndex("locationLong")));

            locations.add(locationsData);

        }
            while (c.moveToNext());


    }
        db.close();
        Log.d("getAllLocations",String.valueOf(locations));
        return locations;
    }

}