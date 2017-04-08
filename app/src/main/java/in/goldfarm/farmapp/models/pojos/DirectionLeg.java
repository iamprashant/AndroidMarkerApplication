package in.goldfarm.farmapp.models.pojos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author Prashant Srivastav
 */

public class DirectionLeg implements Serializable {

    private JsonObject distance;
    private JsonObject duration;
    private String end_address;
    private LocationCoordinate end_location;
    private LocationCoordinate start_location;
    private String start_address;
    private List<DirectionStep> steps;
    private JsonArray traffic_speed_entry;
    private JsonArray via_waypoint;



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

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
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

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public List<DirectionStep> getSteps() {
        return steps;
    }

    public void setSteps(List<DirectionStep> steps) {
        this.steps = steps;
    }

    public JsonArray getTraffic_speed_entry() {
        return traffic_speed_entry;
    }

    public void setTraffic_speed_entry(JsonArray traffic_speed_entry) {
        this.traffic_speed_entry = traffic_speed_entry;
    }

    public JsonArray getVia_waypoint() {
        return via_waypoint;
    }

    public void setVia_waypoint(JsonArray via_waypoint) {
        this.via_waypoint = via_waypoint;
    }


}
