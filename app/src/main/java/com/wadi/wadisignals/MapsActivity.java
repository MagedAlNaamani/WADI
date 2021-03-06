package com.wadi.wadisignals;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    int REQUEST_PLACE_PICKER;
    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MajidonCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_map);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Initializing
        markerPoints = new ArrayList<LatLng>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        map = mapFragment.getMap();


        onMapReady(map);

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        Log.d("MajidonMapReady", "onMapReady");
        // Add a marker in Sydney, Australia, and move the camera.
        //LatLng oman = new LatLng(21.524933, 55.90000);
        //map.addMarker(new MarkerOptions().position(oman).title("Marker in Oman"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(oman));

        if(map!=null){

            map.setMyLocationEnabled(true);

            //move camera to Oman Map
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(21.524933, 55.90000)).zoom(5).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            // Setting onclick event listener for the map
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
            {
                @Override
                public void onMapClick(LatLng point)
                {
                    // Already two locations
                    if (markerPoints.size() > 1) {
                        markerPoints.clear();
                        map.clear();
                    }

                    // Adding new item to the ArrayList
                    markerPoints.add(point);

                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();

                    // Setting the position of the marker
                    options.position(point);

                    /**
                     * For the start location, the color of marker is GREEN and
                     * for the end location, the color of marker is RED.
                     */
                    if (markerPoints.size() == 1) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    } else if (markerPoints.size() == 2) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }

                    // Add new marker to the Google Map Android API V2
                    map.addMarker(options);

                    // Checks, whether start and end locations are captured
                    if (markerPoints.size() >= 2) {
                        LatLng origin = markerPoints.get(0);
                        LatLng dest = markerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);

                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }
            });
        }
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest) {
        Log.d("MajidgetDirectionsUrl", "getDirectionsUrl");
        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url)
        {
            Log.d("MajiddoInBackground", "doInBackground task");
            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("MajidBackground Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            Log.d("onPostExecute", "onPostExecute");
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            Log.d("MajiddownloadUrl", "downloadUrl");
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("MDException while url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData)
        {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                Log.d("MajiddoInBackground", "doInBackground PrsTsk");
                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result)
        {
            try
            {
                if(result!=null)
                {
                    Log.d("Majid onPostExecute", "onPostExecute PrsTsk");
                    ArrayList<LatLng> points = null;
                    PolylineOptions lineOptions = null;
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Traversing through all the routes
                    for (int i = 0; i < result.size(); i++) {
                        points = new ArrayList<LatLng>();
                        lineOptions = new PolylineOptions();

                        // Fetching i-th route
                        List<HashMap<String, String>> path = result.get(i);

                        // Fetching all the points in i-th route
                        for (int j = 0; j < path.size(); j++) {
                            HashMap<String, String> point = path.get(j);

                            final float lat = (float) Double.parseDouble(point.get("lat"));
                            final float lng = (float) Double.parseDouble(point.get("lng"));
                            final int R = 6371;
                            final double[] distances = {};
                            final int closest = -1;
                            /*
                            1. query to class has location
                            2.fetch the location and add it to ParseGeoPoint
                            3. check if parseGeoPoint is not null
                            4.Compare the ParseGeoPoaint with lat and lng variable declared before
                             */
                            final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Wadi");
                            query.findInBackground(new FindCallback<ParseObject>() {
                                public void done(List<ParseObject> List, ParseException e) {
                                    if (e == null)
                                    {
                                        Log.d("Majid score", "Retrieved " + List.size() + " objects");
                                        for (ParseObject object : List)
                                        {
                                            ParseGeoPoint parseGeoPoint = object.getParseGeoPoint("Location");
                                            if (parseGeoPoint != null)
                                            {
                                                float x = distFrom(lat, lng, parseGeoPoint.getLatitude(), parseGeoPoint.getLongitude());
                                                if(x <= 100) {
                                                    Log.d("Majid object", "parseGeoPoint not null x");
                                                    map.addMarker(new MarkerOptions()
                                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                                                            .title("This Wadi is Active")
                                                            .position(new LatLng(parseGeoPoint.getLatitude(), parseGeoPoint.getLongitude())));
                                                }
                                                //addPlaceToGoogleDbAndToTrip((String) object.get("wadiName"),"95675967",parseGeoPoint.getLatitude(),parseGeoPoint.getLongitude());
                                            }
                                        }
                                    }
                                    else
                                    {
                                        Log.d("Majid score", "Error: " + e.getMessage());
                                    }
                                }
                            });



                            LatLng position = new LatLng(lat, lng);

                            points.add(position);
                        }

                        // Adding all the points in the route to LineOptions
                        lineOptions.addAll(points);
                        lineOptions.width(7);
                        lineOptions.color(Color.RED);
                    }

                    // Drawing polyline in the Google Map for the i-th route
                    map.addPolyline(lineOptions);

                    //onPickButtonClick();
                    // Instantiates a new CircleOptions object and defines the center and radius
//                    CircleOptions circleOptions = new CircleOptions()
//                            .center(new LatLng(22.9378, 57.30763))
//                            .radius(1000)
//                            .zIndex(12);// In meters

                    // Get back the mutable Circle
//                    Circle circle = map.addCircle(circleOptions);

                }

            }catch (Exception ex)
            {
                // handling what ever the error wAS
                Toast.makeText(MapsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     *
     * @param lat1 Latitude of the First Location
     * @param lng1 Logitude of the First Location
     * @param lat2 Latitude of the Second Location
     * @param lng2 Longitude of the Second Location
     * @return distance between two lat-lon in float format
     */

    public static float distFrom (float lat1, float lng1, double lat2, double lng2 ) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return new Float(dist * meterConversion).floatValue();
    }

    private void addPlaceToGoogleDbAndToTrip(String placeName,String phoneNumber,Double lat,Double lng) {
        //final Double lat = getIntent().getExtras().getDouble("lat");
        //final Double lng = getIntent().getExtras().getDouble("lng");
        //final String placeName = newPlaceNameET.getText().toString();
        //final String address = newPlaceAddressET.getText().toString();
        //final String website = newPlaceWebsiteET.getText().toString();
        //final String phoneNumber = newPlacePhoneET.getText().toString();
        LatLng latlng = new LatLng(lat, lng);
        Log.d("MajidaddPlaceToGoogleDb", "addPlaceToGoogleDbAnd");
        AddPlaceRequest place =
                new AddPlaceRequest
                        (
                                placeName, // Name
                                latlng, // Latitude and longitude
                                //address, // Address
                                phoneNumber, // Phone Number
                                Collections.singletonList(Place.TYPE_POINT_OF_INTEREST), // Place Type
                                Uri.parse("www.SomeWebsite.co.il") // Website
                        );

        Places.GeoDataApi.addPlace(mGoogleApiClient, place).setResultCallback(new ResultCallback<PlaceBuffer>()
        {
            @Override
            public void onResult(PlaceBuffer places) {

                if (!places.getStatus().isSuccess()) {

                    Log.e("eeeee", "Place query did not complete. Error: " + places.getStatus().toString());
                    places.release();
                    return;
                }

                final Place place = places.get(0);
                String newPlaceID = place.getId();
                Log.i("iiiii", "Place add result: " + place.getName());
                places.release();

            }
        });
    }

    public void onPickButtonClick() {

        // Construct an intent for the place picker
        try {
            Log.d("MajidonPickButtonClick", "onPickButtonClick");
            REQUEST_PLACE_PICKER = 1;
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            // ...
        } catch (GooglePlayServicesNotAvailableException e) {
            // ...
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MajidonActivityResult", "onActivityResult");

        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null) {
                attributions = "";
            }


            //mViewName.setText(name);
            //mViewAddress.setText(address);
            //mViewAttributions.setText(Html.fromHtml(attributions));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}