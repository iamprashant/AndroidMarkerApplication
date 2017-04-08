package in.goldfarm.farmapp.models;

import java.io.Serializable;
import java.util.List;

import in.goldfarm.farmapp.models.pojos.PlaceWrapper;

/**
 * @author Prashant Srivastav
 */

public class PlaceResponse implements Serializable {
    private String status;
    private List<PlaceWrapper> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlaceWrapper> getResults() {
        return results;
    }

    public void setResults(List<PlaceWrapper> results) {
        this.results = results;
    }

}
