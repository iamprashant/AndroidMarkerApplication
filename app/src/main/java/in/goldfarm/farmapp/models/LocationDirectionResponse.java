package in.goldfarm.farmapp.models;

import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.List;

import in.goldfarm.farmapp.models.pojos.DirectionRoute;

/**
 * @author Prashant Srivastav
 */

public class LocationDirectionResponse implements Serializable {
    private List<String> available_travel_modes;
    private JsonArray geocoded_waypoints;
    private List<DirectionRoute> routes;
    private String status;


    public List<String> getAvailable_travel_modes() {
        return available_travel_modes;
    }

    public void setAvailable_travel_modes(List<String> available_travel_modes) {
        this.available_travel_modes = available_travel_modes;
    }

    public JsonArray getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(JsonArray geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<DirectionRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<DirectionRoute> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
