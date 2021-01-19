package com.example.deizaci;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import Adapter.HorizontalniAdapter;
import Model.Global;
import Model.ListaSvirki;

public class kafic extends AppCompatActivity  {
   private ListaKafica kafic;
    private final Date currentTime = Calendar.getInstance().getTime();
    private final List<ListaSvirki> listaSvirkiZaKafic = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_kafic);
        List<ListaSvirki> listaSvirki = ((Global) this.getApplication()).getListaSvirki();
        Intent intent = getIntent();
        RecyclerView rev = findViewById(R.id.view);

        rev.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView.Adapter adapter = new HorizontalniAdapter(this, listaSvirkiZaKafic);
        rev.setAdapter(adapter);
        kafic = intent.getParcelableExtra("Kafic");

        for (ListaSvirki x: listaSvirki){
            if (x.getIdLokala() == kafic.getId())
                listaSvirkiZaKafic.add(x);
        }


        adapter = new HorizontalniAdapter(this, listaSvirkiZaKafic);
        rev.setAdapter(adapter);


        ImageView back = findViewById(R.id.back);
         back.setOnClickListener(v -> finish());

        Button rezervacije = findViewById(R.id.rezervacije);
        rezervacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(kafic.this, Rezervacije.class);
                startActivity(intent);

            }
        });

        //mapBox

        MapView mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        disableScroll(mapView);
        mapView.getMapAsync(mapboxMap -> {
            mapboxMap.getUiSettings().setAttributionEnabled(false);
            mapboxMap.getUiSettings().setLogoEnabled(false);
            CameraPosition position = new CameraPosition.Builder()
                    .target(kafic.getLatLng())
                    .zoom(13.5)
                    .build();

            mapboxMap.setCameraPosition(position);

            mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {

                // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                mapboxMap.addMarker(new MarkerOptions()
                        .position(kafic.getLatLng())
                        .title(kafic.getIme()));

            });

        });


        TextView ime = findViewById(R.id.imeKafica);
        TextView vrsta = findViewById(R.id.vrstaKafica);
        TextView adresa = findViewById(R.id.adresa);
        TextView radno = findViewById(R.id.vreme);
        TextView radnoPr = findViewById(R.id.vremePr);
        TextView telefon = findViewById(R.id.telefon);
        TextView basta = findViewById(R.id.basta);
        TextView kartica = findViewById(R.id.kartica);
        TextView shisha = findViewById(R.id.sisa);
        TextView pusenje = findViewById(R.id.pusenje);
        telefon.setText(kafic.getTelefon());
       // if (kafic.daLiImaUUslugama(Plaćanje karticom))
        // podesiNe(basta);
        if (!kafic.daLiImaUUslugama("Plaćanje karticom"))
            podesiNe(kartica);
        //if (kafic.getSisa().equals("Ne"))
         //   podesiNe(shisha);
       //if (kafic.getPusenje().equals("Ne"))
         //   podesiNe(pusenje);

        ImageView slika = findViewById(R.id.slikaLokala);

        ime.setText(kafic.getIme());
        vrsta.setText(kafic.getVrsta());
        adresa.setText(kafic.getAdresa());
       radno.setText(kafic.getRadnoVreme().get(GregorianCalendar.DAY_OF_MONTH));
        String url = "https://api.polovnitelefoni.net/slike/" + kafic.getSlika();

        Picasso.with(this).load(url).fit().centerInside().into(slika);

       if (!daLiRadi()){
           radnoPr.setText("Zatvoreno");
           radnoPr.setTextColor(Color.RED);
       }

   }

  private boolean daLiRadi(){
       int h = currentTime.getHours();
        int m= currentTime.getMinutes();
      double hm = h + m*0.01;
        String[] listaVremena;
       listaVremena = kafic.getRadnoVreme().get(1)

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
    private void podesiNe(TextView textView){
        textView.setText("Ne");
        textView.setTextColor(Color.RED);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void disableScroll(MapView mapView){
        final ScrollView sv = (ScrollView) findViewById(R.id.scrollKafic);

        mapView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    sv.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    sv.requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return mapView.onTouchEvent(event);
        });
    }


}