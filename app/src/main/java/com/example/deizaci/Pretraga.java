package com.example.deizaci;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import Adapter.AutoCorectAdapter;
import Adapter.HorizontalniAdapter;
import Adapter.MyAdapter;
import Model.Global;
import Model.ListaSvirki;

import static android.content.Context.VIBRATOR_SERVICE;

public class Pretraga extends Fragment implements View.OnClickListener, Serializable {

    private RecyclerView rev, revH;
    private RecyclerView.Adapter adapter, adapterH;
    private List<ListaKafica> listaKafica = new ArrayList<>();
    private final List<ListaKafica> listaKaficaFiltrirano = new ArrayList<>();
    private View view;
    private List<ListaSvirki> listaSvirki = new ArrayList<>();
    private RelativeLayout klubIkonica, kaficIkonica, pubIkonica, kafanaIkonica, splavIkonica;
    private Boolean bKafic = true, bPab = false, bKlub = false, bKafana = false, bSplav = false;
    private AutoCompleteTextView search;
    private Global global;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_pretraga, container, false);
        search = view.findViewById(R.id.search);
        global = (Global) Objects.requireNonNull(getActivity()).getApplication();



        setViews();
        GradientDrawable drawableKafic = (GradientDrawable) kaficIkonica.getBackground();
        drawableKafic.setStroke(3, Color.parseColor("#FF5B16"));

        if (global.getListaKafica().size() == 0) {
            global.setListenerListaKaficaPretraga(this::setKafice);
        } else setKafice();

        if (global.getListaSvirki().size() == 0) {


            global.setListenerListaSvirkiPretraga(() -> {
                listaSvirki = global.getListaSvirki();
                adapterH = new HorizontalniAdapter(view.getContext(), listaSvirki);
                revH.setAdapter(adapterH);
            });

        } else {
            listaSvirki = global.getListaSvirki();
            adapterH = new HorizontalniAdapter(view.getContext(), listaSvirki);
            revH.setAdapter(adapterH);
        }


        rev.hasFixedSize();

        Log.d("prvo1", "Prvi2");

        return view;

    }


    @Override
    public void onClick(View v) {
        ImageView kafic = view.findViewById(R.id.kafic);
        ImageView pab = view.findViewById(R.id.pub);
        ImageView klub = view.findViewById(R.id.klub);
        ImageView splav = view.findViewById(R.id.splav);
        ImageView kafana = view.findViewById(R.id.kafana);


        GradientDrawable drawableKafic = (GradientDrawable) kaficIkonica.getBackground();
        GradientDrawable drawableKlub = (GradientDrawable) klubIkonica.getBackground();
        GradientDrawable drawablePab = (GradientDrawable) pubIkonica.getBackground();
        GradientDrawable drawableKafana = (GradientDrawable) kafanaIkonica.getBackground();

        GradientDrawable drawableSplav = (GradientDrawable) splavIkonica.getBackground();


        switch (v.getId()) {
            case R.id.kaficIkonica:
                if (!bKafic) {

                    kafic.setColorFilter(Color.parseColor("#FF5B16"));
                    pab.setColorFilter(Color.WHITE);
                    klub.setColorFilter(Color.WHITE);
                    splav.setColorFilter(Color.WHITE);
                    kafana.setColorFilter(Color.WHITE);


                    drawableKafic.setStroke(3, Color.parseColor("#FF5B16"));
                    drawablePab.setStroke(3, Color.WHITE);
                    drawableKlub.setStroke(3, Color.WHITE);
                    drawableKafana.setStroke(3, Color.WHITE);
                    drawableSplav.setStroke(3, Color.WHITE);


                    bKafic = true;
                    bPab = false;
                    bKlub = false;
                    bKafana = false;
                    bSplav = false;

                }
                break;
            case R.id.pabIkonica:
                if (!bPab) {
                    pab.setColorFilter(Color.parseColor("#FF5B16"));
                    kafic.setColorFilter(Color.WHITE);
                    klub.setColorFilter(Color.WHITE);
                    splav.setColorFilter(Color.WHITE);
                    kafana.setColorFilter(Color.WHITE);

                    drawablePab.setStroke(3, Color.parseColor("#FF5B16"));
                    drawableKafic.setStroke(3, Color.WHITE);
                    drawableKlub.setStroke(3, Color.WHITE);
                    drawableKafana.setStroke(3, Color.WHITE);
                    drawableSplav.setStroke(3, Color.WHITE);
                    bPab = true;
                    bKafic = false;
                    bKlub = false;
                    bKafana = false;
                    bSplav = false;

                }
                break;

            case R.id.klubIkonica:
                if (!bKlub) {
                    klub.setColorFilter(Color.parseColor("#FF5B16"));
                    kafic.setColorFilter(Color.WHITE);
                    pab.setColorFilter(Color.WHITE);
                    splav.setColorFilter(Color.WHITE);
                    kafana.setColorFilter(Color.WHITE);

                    drawableKlub.setStroke(3, Color.parseColor("#FF5B16"));
                    drawableKafic.setStroke(3, Color.WHITE);
                    drawablePab.setStroke(3, Color.WHITE);
                    drawableKafana.setStroke(3, Color.WHITE);
                    drawableSplav.setStroke(3, Color.WHITE);
                    bKlub = true;
                    bPab = false;
                    bKafic = false;
                    bKafana = false;
                    bSplav = false;

                }
                break;

            case R.id.kafanaIkonica:
                if (!bKafana) {
                    kafana.setColorFilter(Color.parseColor("#FF5B16"));
                    kafic.setColorFilter(Color.WHITE);
                    pab.setColorFilter(Color.WHITE);
                    splav.setColorFilter(Color.WHITE);
                    klub.setColorFilter(Color.WHITE);

                    drawableKafana.setStroke(3, Color.parseColor("#FF5B16"));
                    drawableKafic.setStroke(3, Color.WHITE);
                    drawablePab.setStroke(3, Color.WHITE);
                    drawableKlub.setStroke(3, Color.WHITE);
                    drawableSplav.setStroke(3, Color.WHITE);
                    bKafana = true;
                    bPab = false;
                    bKafic = false;
                    bKlub = false;
                    bSplav = false;

                }
                break;
            case R.id.splavIkonica:
                if (!bSplav) {
                    splav.setColorFilter(Color.parseColor("#FF5B16"));
                    kafic.setColorFilter(Color.WHITE);
                    pab.setColorFilter(Color.WHITE);
                    klub.setColorFilter(Color.WHITE);
                    kafana.setColorFilter(Color.WHITE);

                    drawableSplav.setStroke(3, Color.parseColor("#FF5B16"));
                    drawableKafic.setStroke(3, Color.WHITE);
                    drawablePab.setStroke(3, Color.WHITE);
                    drawableKafana.setStroke(3, Color.WHITE);
                    drawableKlub.setStroke(3, Color.WHITE);
                    bSplav = true;
                    bPab = false;
                    bKafic = false;
                    bKafana = false;
                    bKlub = false;

                }
                break;

        }

        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) Objects.requireNonNull(getActivity()).getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) Objects.requireNonNull(getActivity()).getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }






            listaKaficaFiltrirano.clear();
        new backgroundAdapter().execute();


    }


    public static class CustomComparatorVrsta implements Comparator<ListaKafica> {

        @Override
        public int compare(ListaKafica o1, ListaKafica o2) {
            return o1.getVrsta().compareTo(o2.getVrsta());
        }
    }


    class backgroundAdapter extends AsyncTask<Void,ListaKafica,Void>{
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {


            for (ListaKafica x : listaKafica) {
                if (Filter.proveraFilteraCeoNiz(x.getVrsta(), bKafic, bPab, bKlub, bSplav, bKafana))
                    publishProgress(x);


            }



            return null;
        }

        @Override
        protected void onProgressUpdate(ListaKafica... values) {
            listaKaficaFiltrirano.add(values[0]);
            adapter.notifyDataSetChanged();



        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }



    private void setViews() {
        kaficIkonica = view.findViewById(R.id.kaficIkonica);
        klubIkonica = view.findViewById(R.id.klubIkonica);
        pubIkonica = view.findViewById(R.id.pabIkonica);
        kafanaIkonica = view.findViewById(R.id.kafanaIkonica);
        splavIkonica = view.findViewById(R.id.splavIkonica);
        search = view.findViewById(R.id.search);

        kaficIkonica.setOnClickListener(this);
        klubIkonica.setOnClickListener(this);
        pubIkonica.setOnClickListener(this);
        splavIkonica.setOnClickListener(this);
        kafanaIkonica.setOnClickListener(this);
        rev = view.findViewById(R.id.view);
        revH = view.findViewById(R.id.viewHoriz);

        rev.setLayoutManager(new LinearLayoutManager(view.getContext()));
        revH.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        rev.setNestedScrollingEnabled(false);
    }

    private void setKafice() {
        listaKaficaFiltrirano.clear();
        listaKafica = global.getListaKafica();

        adapter = new MyAdapter(getActivity(), listaKaficaFiltrirano);
        rev.setAdapter(adapter);
        new backgroundAdapter().execute();
        AutoCorectAdapter mAdapter = new AutoCorectAdapter(Objects.requireNonNull(getActivity()), listaKafica);
        search.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("prvo1", "Prvi1");
    }
}