package in.goldfarm.farmapp.models.pojos;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by prashant on 4/8/17.
 */

public class LocationCoordinate implements Serializable {
    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public LatLng getLatLng(){
        return new LatLng(this.lat, this.lng);
    }

}
