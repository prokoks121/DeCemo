package com.example.deizaci;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import Model.Global;
import Model.ListaSvirki;
import Model.Mapa;

public class MainFragment extends Fragment{
    private List<ListaKafica> listaKafica = new ArrayList<>();
    private Model.Filter filter;
    private Mapa mapa;
    private List<ListaSvirki> listaSvirki = new ArrayList<>();
    private Global global;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));

        View view = inflater.inflate(R.layout.activity_main, container, false);
        filter = new Model.Filter(view, getActivity());
        global = (Global) getActivity().getApplication();








        mapa = new Mapa(savedInstanceState, view,listaKafica);
        mapa.mapa();

        if (global.getListaKafica().size() == 0) {

            global.setListenerListaKafica(this::getLokali);
        }else {
            getLokali();
        }



        if (global.getListaSvirki().size() == 0) {

            global.setListenerListaSvirki(() -> listaSvirki = global.getListaSvirki());
        }else{
            listaSvirki = global.getListaSvirki();

        }

        return view;

    }



    @Override
    public void onResume(){
        super.onResume();
        mapa.onResume();

    }
    @Override
    public void onPause() {
        super.onPause();
        mapa.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapa.onLowMemory();
    }

    @SuppressLint("NonConstantResourceId")
    public void onCheckedChanged1(CompoundButton buttonView, boolean isChecked) {
        int id =  buttonView.getId();

        switch (id){
            case R.id.pabF:
                filter.setbPab(isChecked);

                break;
            case R.id.kaficF:
                filter.setbKafic(isChecked);
                break;
            case R.id.klubF:
                filter.setbKlub(isChecked);
                break;
            case R.id.splavF:
                filter.setbSplav(isChecked);
                break;
            case R.id.kafanaF:
                filter.setbKafana(isChecked);
                break;

            case R.id.svirke:
                filter.setbSvirke(isChecked);
                break;
        }
        mapa.setPolygonLatLngListClear();

        for (ListaKafica x: listaKafica) {
            if (Filter.proveraFilteraCeoNiz(x.getVrsta(),filter.getbKafic(),filter.getbPab(),filter.getbKlub(),filter.getbSplav(),filter.getbKafana())) {
                if (filter.getbSvirke()){
                    for(ListaSvirki l: listaSvirki){
                        if (l.getIdLokala() == x.getId()){
                            mapa.addPolygonLatLngListClear(new MarkerOptions().position(x.getLatLng()).setTitle(x.getIme()));
                            break;
                        }
                    }
                }else {
                    mapa.addPolygonLatLngListClear(new MarkerOptions().position(x.getLatLng()).setTitle(x.getIme()));
                }
            }

        }






        mapa.getStyleMap();
    }

    private void getLokali(){
        listaKafica = global.getListaKafica();

        try {
            mapa.setListaKafica(listaKafica);
            mapa.markeri();
            mapa.getStyleMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
