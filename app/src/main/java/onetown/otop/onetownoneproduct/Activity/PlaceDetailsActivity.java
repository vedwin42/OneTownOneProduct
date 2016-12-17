package onetown.otop.onetownoneproduct.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Objects.Comments;
import onetown.otop.onetownoneproduct.Objects.Credentials;
import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;

public class PlaceDetailsActivity extends AppCompatActivity {

    private TextView placeDetailstextview;
    private ImageView placeImageView;
    private EditText commentEditext;
    private Button postComment;
    DBHelper helper;
    ArrayList<LocationsData> data;
    LocationsData locationsData;
    Comments comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        helper= new DBHelper(PlaceDetailsActivity.this);

     /**   data= null;
        data= this.getIntent().getParcelableExtra("lists");
        Log.d("Parcelable Arraylist",data.toString()); */

        locationsData=getIntent().getExtras().getParcelable("places");
        Log.i("LocationsData",locationsData.toString());


        placeDetailstextview= (TextView)findViewById(R.id.textview_placeDetails);

     //   placeDetailstextview.setText(data.get(0).getLocationProducts());
        placeDetailstextview.setText(locationsData.getLocationProducts());

        placeImageView= (ImageView)findViewById(R.id.place_image);
        placeImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

     //   placeImageView.setImageResource(Integer.parseInt(data.get(0).image_path));
        placeImageView.setImageResource(Integer.parseInt(locationsData.getImage_path()));

        commentEditext= (EditText)findViewById(R.id.comment_editext);
        postComment= (Button)findViewById(R.id.button_postComment);

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences userIdPref= getApplicationContext().getSharedPreferences("useridpref",Context.MODE_PRIVATE);
                int _userId=userIdPref.getInt("user_id",0);
                String _userEmail= userIdPref.getString("user_email","");

                comments= new Comments();
                comments.setCommentContent(commentEditext.getText().toString());
                comments.setCurrentTimeStamp(helper.getCurrentTimeStamp());

                LocationsData data1= new LocationsData();
                data1.set_id(locationsData.get_id());
                comments.setLocationsData(data1);
                comments.getCredentials().set_id(_userId);

                helper.addComment(comments);




            }
        });
    }

}
