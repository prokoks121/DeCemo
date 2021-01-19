package Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.deizaci.ListaKafica;
import com.example.deizaci.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Mapa implements PermissionsListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private final Context context;
    private MapView mapView;
    private final List<MarkerOptions> polygonLatLngList = new ArrayList<>();
    public MapboxMap mapboxMaps;
    private PermissionsManager permissionsManager;
    private final Bundle savedInstanceState;
    private final Date currentTime = Calendar.getInstance().getTime();
    private final View view;
    private double oldZoom = 12;

    public void setListaKafica(List<ListaKafica> listaKafica) {
        this.listaKafica = listaKafica;
        markeri();
    }

    private List<ListaKafica> listaKafica;

    public Mapa(Bundle savedInstanceState, View view, List<ListaKafica> listaKafica) {
        this.savedInstanceState = savedInstanceState;
        this.context = view.getContext();
        this.view = view;
        this.listaKafica = listaKafica;
        markeri();
    }


    public void markeri() {

        IconFactory iconF = IconFactory.getInstance(view.getContext());
        com.mapbox.mapboxsdk.annotations.Icon icon;
        for (ListaKafica x : listaKafica) {
            if (x.getVrsta().equals("Kafic"))
                icon = iconF.fromResource(R.drawable.coffee);
            else if (x.getVrsta().equals("Pab"))
                icon = iconF.fromResource(R.drawable.beer);
            else
                icon = iconF.fromResource(R.drawable.placeholder);

            polygonLatLngList.add(new MarkerOptions().position(x.getLatLng()).setTitle(x.getIme()).icon(icon));

        }
    }

    @SuppressLint("SetTextI18n")
    public void mapa() {

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(mapboxMap -> {
            mapboxMaps = mapboxMap;
            mapboxMaps.getUiSettings().setAttributionEnabled(false);
            mapboxMaps.getUiSettings().setLogoEnabled(false);
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(44.786604, 20.4717838))
                    .zoom(12)
                    .build();

            mapboxMaps.setCameraPosition(position);


            mapboxMaps.setStyle(Style.MAPBOX_STREETS, style -> {
                enableLocationComponent(style);

                // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                mapboxMaps.addMarkers(polygonLatLngList);

mapboxMaps.setAllowConcurrentMultipleOpenInfoWindows(true);
                mapboxMaps.addOnCameraIdleListener(() -> {
                    double curentZoom = mapboxMaps.getCameraPosition().zoom;
                    if (curentZoom > 16 && oldZoom <= 16){
                        List<Marker> markers = mapboxMaps.getMarkers() ;

                        for(Marker x : markers){
                        mapboxMaps.selectMarker(x);

                        }
                        promeniUgao(90);
                    }else if(curentZoom <= 16 && oldZoom > 16){
                      mapboxMaps.deselectMarkers();
                        promeniUgao(0);

                    }
                    oldZoom = curentZoom;
                });



                mapboxMaps.setOnMarkerClickListener(marker -> {
                    BottomSheetDialog sheet = new BottomSheetDialog(view.getContext(), R.style.SheetDialog);
                    sheet.setContentView(R.layout.bootom_shape);
                    sheet.setCanceledOnTouchOutside(true);

                    String imeLokala = marker.getTitle();
                    int id = 0;


                    for (int i = 0; i < listaKafica.size(); i++) {
                        if (listaKafica.get(i).getIme().equals(imeLokala)) {
                            id = i;
                        }
                    }
                    ListaKafica kafic = listaKafica.get(id);


                    TextView ime = sheet.findViewById(R.id.imeKafica);
                    ImageView slika = sheet.findViewById(R.id.slikaLokala);
                    TextView vrsta = sheet.findViewById(R.id.vrsta);
                    TextView telefon1 = sheet.findViewById(R.id.telefon1);
                    TextView adresa = sheet.findViewById(R.id.adresa);
                    TextView vreme = sheet.findViewById(R.id.radnoVreme);
                    TextView radnoPr = sheet.findViewById(R.id.vremePr);
                    LinearLayout otvoriKafic = sheet.findViewById(R.id.otvoriKafic);


                    assert vrsta != null;
                    vrsta.setText(kafic.getVrsta());
                    assert telefon1 != null;
                    telefon1.setText(kafic.getTelefon());
                    assert adresa != null;
                    adresa.setText(kafic.getAdresa());
                    assert vreme != null;
               //     vreme.setText(kafic.getRadno());

                    assert ime != null;
                    ime.setText(kafic.getIme());
                    String url = "https://api.polovnitelefoni.net/slike/" + kafic.getSlika();
                    Log.d("url", url);
                    Picasso.with(sheet.getContext()).load(url).into(slika);
                   if (!daLiRadi(kafic)){
                       assert radnoPr != null;
                       radnoPr.setText("Zatvoreno");
                       radnoPr.setTextColor(Color.RED);
                    }

                    assert otvoriKafic != null;
                    otvoriKafic.setOnClickListener(v -> {

                        Intent intent =  new Intent(context, com.example.deizaci.kafic.class);
                        intent.putExtra("Kafic", (Parcelable) kafic);
                        context.startActivity(intent);
                    });



                    sheet.show();
                    return true;
                });



            });

        });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(view.getContext())) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMaps.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(view.getContext(), loadedMapStyle).build());

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions((Activity) context);
        }
    }

    public void locMap(){
        mapboxMaps.getStyle(loadedMapStyle -> {
            enableLocationComponent(loadedMapStyle);
        });
    }

    public void onResume(){

        mapView.onResume();
    }
    public void onPause() {
        mapView.onPause();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
    }
    public void setPolygonLatLngListClear(){
        polygonLatLngList.clear();

    }
    public void addPolygonLatLngListClear(MarkerOptions op){
        polygonLatLngList.add(op);

    }
    public void getStyleMap(){
        mapboxMaps.getStyle(style -> {

            mapboxMaps.clear();
            mapboxMaps.addMarkers( polygonLatLngList);


        });
    }






    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(context, "Dozvoljeno", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
           locMap();
        } else {
            Toast.makeText(context, "Nije dozvoljeno", Toast.LENGTH_LONG).show();
        }
    }




    private boolean daLiRadi(ListaKafica kafic){
        int h = currentTime.getHours();
        double hm = h;
        String[] listaVremena;
        listaVremena =kafic.getRadnoVreme().get(1)

                .replaceAll("\"", "")
                .replaceAll(" ", "")
                .replaceAll("h", "")

                .split("-");

        double hm1 = Integer.parseInt(listaVremena[0]);
        double hm2 = Integer.parseInt(listaVremena[1]);

        if (hm1 <= hm && hm2 > hm) {
            return true;
        }else if(hm1 == hm2)
        {
            return true;
        }
        else if(hm1 > hm2 && hm > hm1)
            return true;
        else return hm1 > hm2 && hm < hm1 && hm < hm2;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    public void promeniUgao(double angle){
        CameraPosition position = new CameraPosition.Builder().tilt(angle).build();

        mapboxMaps.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);

    }
}
