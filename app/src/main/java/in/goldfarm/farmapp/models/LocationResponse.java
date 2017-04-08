package in.goldfarm.farmapp.models;

import java.io.Serializable;
import java.util.List;

import in.goldfarm.farmapp.models.pojos.ComponentWrapper;

/**
 * @author Prashant Srivastav
 */

public class LocationResponse implements Serializable {

    private String status;

    private List<ComponentWrapper> results;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ComponentWrapper> getResults() {
        return results;
    }

    public void setResults(List<ComponentWrapper> results) {
        this.results = results;
    }


}
