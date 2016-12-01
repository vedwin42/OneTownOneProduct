package onetown.otop.onetownoneproduct.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;

public class PlaceDetailsActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Intent i= getIntent();
        ArrayList<LocationsData> data= (ArrayList<LocationsData>)i.getSerializableExtra("lists");
        Log.i("PlaceDetailsActivity",data.get(0).getLocationName().toString());

        tv= (TextView)findViewById(R.id.textview_placeDetails);
        tv.setText(data.get(0).locationProducts);
        iV= (ImageView)findViewById(R.id.place_image);
        iV.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iV.setImageResource(Integer.parseInt(data.get(0).image_path));
    }
}
