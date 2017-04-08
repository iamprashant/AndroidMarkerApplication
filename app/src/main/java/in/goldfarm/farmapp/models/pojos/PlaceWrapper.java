package in.goldfarm.farmapp.models.pojos;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author Prashant Srivastav
 */

public class PlaceWrapper implements Serializable {

    private JsonObject geometry;
    private String icon;
    private String id;
    private String name;
    private List<JsonObject> photos;
    private String place_id;
    private float rating;
    private String reference;
    private String scope;
    private List<String> types;
    private String vicinity;

    public JsonObject getGeometry() {
        return geometry;
    }

    public void setGeometry(JsonObject geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonObject> getPhotos() {
        return photos;
    }

    public void setPhotos(List<JsonObject> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
