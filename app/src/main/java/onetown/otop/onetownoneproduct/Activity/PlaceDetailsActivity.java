package onetown.otop.onetownoneproduct.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;

public class PlaceDetailsActivity extends AppCompatActivity {

    private TextView placeDetailstextview;
    private ImageView placeImageView;
    private EditText commentEditext;
    private Button postComment;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        helper= new DBHelper(PlaceDetailsActivity.this);

        Intent i= getIntent();
        ArrayList<LocationsData> data= (ArrayList<LocationsData>)i.getSerializableExtra("lists");
        Log.i("PlaceDetailsActivity",data.get(0).getLocationName().toString());

        placeDetailstextview= (TextView)findViewById(R.id.textview_placeDetails);
        placeDetailstextview.setText(data.get(0).locationProducts);
        placeImageView= (ImageView)findViewById(R.id.place_image);
        placeImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        placeImageView.setImageResource(Integer.parseInt(data.get(0).image_path));


    }

    public void AddNewComment() {

    }
}
