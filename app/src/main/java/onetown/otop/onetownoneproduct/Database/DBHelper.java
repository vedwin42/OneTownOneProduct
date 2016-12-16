package onetown.otop.onetownoneproduct.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import onetown.otop.onetownoneproduct.Activity.LoginActivity;
import onetown.otop.onetownoneproduct.Objects.Comments;
import onetown.otop.onetownoneproduct.Objects.Credentials;
import onetown.otop.onetownoneproduct.Objects.LocationsData;

public class DBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION=1;
    private static String DB_NAME="LocationData";
    private static String TBL_ID="_id";

    private String OTOP_TABLE="tbl_town";
    private String COMMENTS_TABLE="tbl_comments";
    private String USERS_TABLE="tbl_user";
    private String LIKE_TABLE="tbl_like";

    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    String createLocationTableQuery= "CREATE TABLE " +OTOP_TABLE+" ("+TBL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "locationName TEXT, locationProducts TEXT," +
            "locationLat DECIMAL, locationLong DECIMAL, drawable_image TEXT)";

    String createCommentsTableQuery= "CREATE TABLE "+ COMMENTS_TABLE+ "( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "otop_id INTEGER NOT NULL, comment_content TEXT NOT NULL, created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            " user_fullname INTEGER, FOREIGN KEY(user_fullname) REFERENCES tbl_user(id), FOREIGN KEY(otop_id) REFERENCES tbl_town(id) )";

    String createUserTableQuery= "CREATE TABLE "+ USERS_TABLE+"( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            " user_email TEXT NOT NULL, " +
            "user_password TEXT NOT NULL )";

    String createLikesTableQuery= "CREATE TABLE "+LIKE_TABLE+"( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, liked INTEGER DEFAULT 0, " +
            "likedby_username INTEGER, places_liked INTEGER, FOREIGN KEY(likedby_username) REFERENCES "+USERS_TABLE+"(id), "+
            "FOREIGN KEY(places_liked) REFERENCES "+OTOP_TABLE+"(id))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createLocationTableQuery);
        db.execSQL(createCommentsTableQuery);
        db.execSQL(createUserTableQuery);
        db.execSQL(createLikesTableQuery);

        Log.i("onCreate",String.valueOf(db.getPageSize()));
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST location");
    }

    // Location Functions------> Start
    public LocationsData addNewLocation(LocationsData data) {

        SQLiteDatabase db= getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put("locationName",data.getLocationName());
        values.put("locationProducts",data.getLocationProducts());
        values.put("locationLat",data.getLocationLatitude());
        values.put("locationLong",data.getLocationLongitude());
        values.put("drawable_image",data.getImage_path());


        //db.insert("location",null,values);
        db.insertWithOnConflict(OTOP_TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();

        return data;

    }
    // Get All Location rows and their details
    public ArrayList<LocationsData> getAllLocationsAndDatas() {
        SQLiteDatabase db= getWritableDatabase();
        ArrayList<LocationsData> locations= new ArrayList<LocationsData>();

        String query= "SELECT * FROM "+OTOP_TABLE+";";
        Log.i("Query ",query);
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()) {

            do {

                LocationsData locationsData= new LocationsData();
                locationsData.set_id(c.getInt(c.getColumnIndex(TBL_ID)));
                locationsData.setLocationName(c.getString(c.getColumnIndex("locationName")));
                locationsData.setLocationProducts(c.getString(c.getColumnIndex("locationProducts")));
                locationsData.setLocationLatitude(c.getDouble(c.getColumnIndex("locationLat")));
                locationsData.setLocationLongitude(c.getDouble(c.getColumnIndex("locationLong")));
                locationsData.setImage_path(c.getString(c.getColumnIndex("drawable_image")));

                locations.add(locationsData);
            }while (c.moveToNext());



    }
        Log.d("getAllLocations",String.valueOf(locations));
        Log.i("Cursor values",c.toString());
        return locations;

    }
    // Location Functions------ END





    // Users Functions------- Start

    public ArrayList<Credentials> getSingleUserDetails(Credentials credentials) {
        ArrayList<Credentials> singlePersonDetails=new ArrayList<Credentials>();

        SQLiteDatabase db= getReadableDatabase();
        Cursor c=db.query(USERS_TABLE,null,"user_email like ? and user_password like ? ", new String[]{credentials.getEmail(),credentials.getPassword()},null,null,null);
        c.moveToFirst();
        if (c.getCount() < 1) {

        }else {
            credentials= new Credentials(c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("user_email")),c.getString(c.getColumnIndex("user_password")));
            singlePersonDetails.add(credentials);
        }

        Log.i("USER ID: ",String.valueOf(singlePersonDetails.get(0).get_id()));

        return singlePersonDetails;
    }

    // Getting Each USER DETAILS
    public Credentials addCredentialsToDb(Credentials credentials) {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("user_email",credentials.getEmail());
        cv.put("user_password",credentials.getPassword());

        db.insert(USERS_TABLE,null,cv);
        Log.d("Users Table Size: ",String.valueOf(db.getPageSize()));

        if (cv.size() < 0) {
            Log.e("Add Credentials","Empty! No value!");
        }
        db.close();
        return credentials;

    }

    public Boolean checkIfEmailExist(String email) {
        boolean userExists=true;

        SQLiteDatabase db= getReadableDatabase();
        Cursor c=db.query(USERS_TABLE,null,"user_email=?",new String[]{email},null,null,null);
        if (c.getCount() < 1) {
            c.close();
            userExists=false;
            Log.e("User Email Check: ","user existed");
        }

        db.close();
        return userExists;
    }

    public boolean checkIfAccountExist(String email,String password) {
        boolean accountExist= true;

        SQLiteDatabase db= getReadableDatabase();
        Cursor c= db.rawQuery("SELECT user_email, user_password FROM "+USERS_TABLE+" where user_email = ? AND user_password = ?",
                new String[]{email,password});

        if (c.getCount() < 1) {
            accountExist=false;
            Log.e("Account checking","No Accounts found");
        }else {
            c.moveToFirst();
            if (c.getString(c.getColumnIndex("user_email")).toString().equals(email) && c.getString(c.getColumnIndex("user_password")).toString().equals(password))  {
                Log.i("Account checking","Account Exist!");
            }
        }

        return accountExist;
    }


    public boolean checkDatabaseIfEmpty() {
        SQLiteDatabase db= getReadableDatabase();
        String query="SELECT * FROM "+OTOP_TABLE;
        Cursor c= db.rawQuery(query,null);

        if (c.getCount() > 0) {
            return false;
        }

        return true;
    }

    /** Comments Functions */

    //Getting all comments in every specific places
    // int id--> id of the place, to filter only related comments

    public ArrayList<Comments> getAllComments(int id) {
        ArrayList<Comments> commentsArrayList= new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        Cursor c= db.rawQuery("SELECT "+COMMENTS_TABLE+".comment_content, "+COMMENTS_TABLE+".created_at, "+OTOP_TABLE+".locationName, "+USERS_TABLE+".user_email"+
                    "FROM "+COMMENTS_TABLE+
                    "INNER JOIN "+USERS_TABLE+" ON "+USERS_TABLE+"._id = "+COMMENTS_TABLE+".user_fullname "+
                    "INNER JOIN "+OTOP_TABLE+" ON "+OTOP_TABLE+"._id = "+COMMENTS_TABLE+".otop_id WHERE "+COMMENTS_TABLE+".otop_id = ?",new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            do {
               Comments commentsObj= new Comments();
                commentsObj.setCurrentEmail(c.getString(c.getColumnIndex("user_email")));
                commentsObj.setCommentContent(c.getString(c.getColumnIndex("comment_content")));
                commentsObj.setCurrentTimeStamp(c.getString(c.getColumnIndex("created_at")));

                commentsArrayList.add(commentsObj);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return commentsArrayList;
    }

    public String getCurrentTimeStamp() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date= new Date();
        return sdf.format(date);
    }

    public Comments addComment(Comments comments) {
            SQLiteDatabase db= getWritableDatabase();
            ContentValues contentValues= new ContentValues();
        contentValues.put("otop_id",comments.getOtop_id());
        contentValues.put("comment_content",comments.getCommentContent());
        contentValues.put("created_at",getCurrentTimeStamp());
        contentValues.put("user_fullname",comments.getCurrentUser());

        db.insert(COMMENTS_TABLE,null,contentValues);
        db.close();

        return comments;

    }

}
