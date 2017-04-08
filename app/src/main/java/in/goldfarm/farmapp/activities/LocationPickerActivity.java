package in.goldfarm.farmapp.activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.goldfarm.farmapp.R;
import in.goldfarm.farmapp.models.pojos.ComponentWrapper;
import in.goldfarm.farmapp.models.pojos.DirectionLeg;
import in.goldfarm.farmapp.models.pojos.DirectionRoute;
import in.goldfarm.farmapp.models.pojos.DirectionStep;
import in.goldfarm.farmapp.models.LocationDirectionResponse;
import in.goldfarm.farmapp.models.LocationResponse;
import in.goldfarm.farmapp.models.PlaceResponse;
import in.goldfarm.farmapp.models.pojos.PlaceWrapper;
import in.goldfarm.farmapp.services.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.goldfarm.farmapp.utils.MapUtils.decodePolyPoints;
import static in.goldfarm.farmapp.utils.MapUtils.getLatLngAsString;


/**
 * @author Prashant Srivastav
 */
public class LocationPickerActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private static final String TAG = LocationPickerActivity.class.getSimpleName();
    public static final int RADIUS = 1000;
    private static final LatLng DEFAULT_LAT_LNG = new LatLng(12.9684355, 77.6328103);
    private static final String NEAREST_PLACE_TYPE = "gas_station";
    public static final String MAP_API_URL = "https://maps.googleapis.com";
    private static final int ROUTE_STROKE = 7;
    private static final boolean MAP_SENSOR = false;


    /**
     * current polygon route
     */
    Polyline mPolygon = null;

    /**
     * Main Marker
     */
    Marker mMarker = null;

    /**
     * Radius circle
     */
    Circle mCircle = null;


    /**
     * Refrence of old marker
     */
    List<Marker> oldMarkerList = null;


    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.tvLatLng)
    TextView tvLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);


        ButterKnife.bind(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        mMap.addMarker((new MarkerOptions().position(DEFAULT_LAT_LNG)).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LAT_LNG));

        getAddressFull(DEFAULT_LAT_LNG);
//        tvLocationName.setText(mMap.get);

//        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker.showInfoWindow();
        if (!mMarker.getPosition().equals(marker.getPosition())) {
            Snackbar.make(findViewById(android.R.id.content), "Navigate to " + marker.getTitle(), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Navigate", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            drawRouteStep(mMarker.getPosition(), marker.getPosition());
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        }
        return true;
    }

    /**
     *
     * @param cw
     * @param latlng
     */

    private void setLocationDetails(ComponentWrapper cw, LatLng latlng) {
        tvLocationName.setText(cw.getFormatted_address());
        tvLatLng.setText(getLatLngAsString(latlng));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        if(mPolygon != null)
            mPolygon.remove();

        if (mMarker == null) {
            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title(cw.getFormatted_address()).draggable(true));
            mMarker.setIcon(getBitmapDescriptor(R.drawable.map_current_marker));
            mMarker.setTag(0);
        } else {
            mMarker.setPosition(latlng);
            mMarker.setTitle(cw.getFormatted_address());
        }

        if (mCircle == null) {
            mCircle = mMap.addCircle(new CircleOptions()
                    .center(latlng)
                    .radius(RADIUS)
                    .strokeWidth(0f)
                    .fillColor(0x550000FF));
        } else {
            mCircle.setCenter(latlng);
        }


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14f));


        getNearestLocation(latlng);
    }


    /**
     *
     * @param mLatLng
     */
    private void getAddressFull(final LatLng mLatLng) {
        new ServiceFactory(MAP_API_URL)
                .getLocationService()
                .getLocationNameFromLatLng(getLatLngAsString(mLatLng), MAP_SENSOR)
                .enqueue(new Callback<LocationResponse>() {
                    @Override
                    public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                        if (response.isSuccessful()) {
                            LocationResponse lr = response.body();
                            if (lr.getStatus().equalsIgnoreCase("OK")) {
                                setLocationDetails(lr.getResults().get(0), mLatLng);
                            } else {
                                Toast.makeText(LocationPickerActivity.this, "Unable to find location on given address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LocationResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    /**
     *
     * @param mLatLng
     */
    private void getNearestLocation(LatLng mLatLng) {

        new ServiceFactory(MAP_API_URL)
                .getLocationService()
                .searchNearBy(mLatLng.latitude + "," + mLatLng.longitude, RADIUS, NEAREST_PLACE_TYPE, MAP_SENSOR, getResources().getString(R.string.http_api_key))
                .enqueue(new Callback<PlaceResponse>() {
                    @Override
                    public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                        if (response.isSuccessful()) {
                            PlaceResponse pr = response.body();
                            if (pr.getStatus().equalsIgnoreCase("OK")) {
                                setNearestLocationMarker(pr);
                            } else {
                                Toast.makeText(LocationPickerActivity.this, "Unable to find nearest Gas station in radius", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PlaceResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    /**
     *
     * @param pr
     */
    private void setNearestLocationMarker(PlaceResponse pr) {
        if(oldMarkerList != null && oldMarkerList.size() > 0){
            /**
             * clearing old marker
             */
            for (Marker m: oldMarkerList) {
                m.remove();
            }
        }

        oldMarkerList = new ArrayList<>();
        for (PlaceWrapper pw : pr.getResults()) {
            JsonObject geometry = pw.getGeometry();
            JsonObject location = geometry.getAsJsonObject("location");
            Marker plMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.get("lat").getAsDouble(), location.get("lng").getAsDouble()))
                    .title(pw.getName()));

            plMarker.setIcon(getBitmapDescriptor(R.drawable.map_dest_marker));
            oldMarkerList.add(plMarker);

        }

        if(oldMarkerList.size() > 0){
            oldMarkerList.get(0).showInfoWindow();
        }

    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d(TAG, "onMarkerDragStart: ");

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "onMarkerDrag: ");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        getAddressFull(marker.getPosition());
    }


    /**
     *
     * @param id
     * @return
     */
    private BitmapDescriptor getBitmapDescriptor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) getDrawable(id);

            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();

            vectorDrawable.setBounds(0, 0, w, h);

            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bm);

        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }


    /**
     *
     * @param originLatLng
     * @param destinationLatLng
     */
    private void drawRouteStep(final LatLng originLatLng, final LatLng destinationLatLng) {

        if (mPolygon != null) {
            mPolygon.remove();
        }

        new ServiceFactory(MAP_API_URL)
                .getLocationService()
                .getDirection(getLatLngAsString(originLatLng), getLatLngAsString(destinationLatLng), getResources().getString(R.string.http_api_key))
                .enqueue(new Callback<LocationDirectionResponse>() {
                    @Override
                    public void onResponse(Call<LocationDirectionResponse> call, Response<LocationDirectionResponse> response) {
                        if (response.isSuccessful()) {
                            LocationDirectionResponse ldr = response.body();
                            if (ldr.getStatus().equalsIgnoreCase("OK")) {
                                if (ldr.getRoutes().size() > 0) {
                                    PolylineOptions rectOptions = new PolylineOptions();
                                    for (DirectionRoute dr : ldr.getRoutes()) {
                                        for (DirectionLeg dl : dr.getLegs()) {
                                            for (DirectionStep ds : dl.getSteps()) {
                                                for (LatLng latlng : decodePolyPoints(ds.getPolyline().get("points").getAsString())) {
                                                    Log.d(TAG, "onResponse: "+latlng);
                                                    rectOptions.add(latlng);
                                                }
                                            }
                                        }
                                    }
                                    rectOptions.color(Color.GRAY);
                                    rectOptions.width(ROUTE_STROKE);
                                    mPolygon = mMap.addPolyline(rectOptions);
                                } else {
                                    Toast.makeText(LocationPickerActivity.this, "Routes Not available", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(LocationPickerActivity.this, "Unable to find route", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LocationPickerActivity.this, "Unable to find route", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocationDirectionResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
