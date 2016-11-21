package onetown.otop.onetownoneproduct.Classes;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by root on 11/20/16.
 */

public interface DistanceCalculator {

    public void calculateDistanceOfTwoPoint(Location source);

}
