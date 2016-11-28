package onetown.otop.onetownoneproduct.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;

public class PlaceDetailsActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Intent i= getIntent();
        ArrayList<LocationsData> data= (ArrayList<LocationsData>)i.getSerializableExtra("lists");
        Log.i("PlaceDetailsActivity",data.get(0).getLocationName().toString());

        tv= (TextView)findViewById(R.id.textview_placeDetails);
        tv.setText(data.get(0).locationProducts);

    }
}
