package in.goldfarm.farmapp.models.pojos;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * @author Prashant Srivastav
 */

public class DirectionStep implements Serializable {

    private JsonObject distance;
    private JsonObject duration;
    private JsonObject polyline;
    private LocationCoordinate end_location;
    private LocationCoordinate start_location;
    private String travel_mode;


    public JsonObject getDistance() {
        return distance;
    }

    public void setDistance(JsonObject distance) {
        this.distance = distance;
    }

    public JsonObject getDuration() {
        return duration;
    }

    public void setDuration(JsonObject duration) {
        this.duration = duration;
    }

    public LocationCoordinate getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LocationCoordinate end_location) {
        this.end_location = end_location;
    }

    public LocationCoordinate getStart_location() {
        return start_location;
    }

    public void setStart_location(LocationCoordinate start_location) {
        this.start_location = start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public JsonObject getPolyline() {
        return polyline;
    }

    public void setPolyline(JsonObject polyline) {
        this.polyline = polyline;
    }

}
