package in.goldfarm.farmapp.services;

import in.goldfarm.farmapp.models.LocationDirectionResponse;
import in.goldfarm.farmapp.models.LocationResponse;
import in.goldfarm.farmapp.models.PlaceResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Prashant Srivastav
 */

public class ServiceFactory {


    private Retrofit mRetrofit;


    /**
     *
     * @param baseUrl
     */
    public ServiceFactory(String baseUrl){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient().build())
                .build();
    }

    /**
     *
     * @return
     */
    private OkHttpClient.Builder getHttpClient(){
        return new OkHttpClient.Builder();
    }


    /**
     *
     * @return
     */
    public LocationService getLocationService(){
        return mRetrofit.create(LocationService.class);
    }


    public interface LocationService{

        /**
         *
         * @param location
         * @param radius
         * @param type
         * @param sensor
         * @param key
         */
        @GET("maps/api/place/nearbysearch/json")
        Call <PlaceResponse> searchNearBy (@Query("location") String location,
                                           @Query("radius") int radius,
                                           @Query("type") String type,
                                           @Query("sensor") boolean sensor,
                                           @Query("key") String key);



        /**
         *
         * @param latlng
         * @param sensor
         * @return
         */
        @GET("maps/api/geocode/json")
        Call <LocationResponse> getLocationNameFromLatLng(@Query("latlng") String latlng,
                                                          @Query("sensor") boolean sensor);

        /**
         *
         * @param origin
         * @param destination
         * @param key
         * @return
         */
        @GET("maps/api/directions/json")
        Call <LocationDirectionResponse> getDirection(@Query("origin") String origin,
                                                      @Query("destination") String destination,
                                                      @Query("key") String key);
    }
}


