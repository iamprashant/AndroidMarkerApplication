package in.goldfarm.farmapp.models.pojos;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author Prashant Srivastav
 */

public class DirectionRoute implements Serializable {
    private JsonObject bounds;
    private String copyrights;
    private List<DirectionLeg> legs;


    public JsonObject getBounds() {
        return bounds;
    }

    public void setBounds(JsonObject bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<DirectionLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<DirectionLeg> legs) {
        this.legs = legs;
    }

}
