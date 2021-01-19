package com.example.deizaci;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

public class ListaKafica implements Parcelable {


    protected ListaKafica(Parcel in) {
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        id = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
        ime = in.readString();
        adresa = in.readString();
        vrsta = in.readString();
        usluge = in.createStringArrayList();
        radnoVreme = in.createStringArrayList();
        telefon = in.readString();
        slika = in.readString();
    }

    public static final Creator<ListaKafica> CREATOR = new Creator<ListaKafica>() {
        @Override
        public ListaKafica createFromParcel(Parcel in) {
            return new ListaKafica(in);
        }

        @Override
        public ListaKafica[] newArray(int size) {
            return new ListaKafica[size];
        }
    };

    public LatLng getLatLng() {
        return latLng;
    }


    private LatLng latLng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getAdresa() {
        return adresa;
    }


    public String getVrsta() {
        return vrsta;
    }





    public String getTelefon() {
        return telefon;
    }


    public String getSlika() {
        return slika;
    }


    private int id;
            private final double lat;
    private final double lon;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    private String ime;
    private final String adresa;
    private final String vrsta;


    public ArrayList<String> getUsluge() {
        return usluge;
    }

    private ArrayList<String> usluge = new ArrayList<>();
    private final String telefon;
    private final String slika;

    public ArrayList<String> getRadnoVreme() {
        return radnoVreme;
    }

    private ArrayList<String> radnoVreme = new ArrayList<>();

    public ListaKafica(int id, double lat, double lon,String ime, String adresa, String vrsta, String radno, String usluge, String telefon, String slika) {
        this.ime = ime;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.adresa = adresa;
        this.vrsta = vrsta;
        setRadnoVreme(radno);
        setUsluge(usluge);
        this.telefon = telefon;
        this.slika = slika;

        tacka();
    }
    private void tacka(){
        latLng = new LatLng(lat,lon);
    }



    @Override
    public String toString() {
        return "ListaKafica{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                '}';
    }

    private void setRadnoVreme(String radno){

        String[] items = radno.replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .split(",");
        for (int i =0;i<items.length;i++) {

            radnoVreme.add(items[i].split(":")[1]);

        }

        Log.d("radnovreme", "setRadnoVreme: " + radnoVreme);



    }

    private void setUsluge(String usluge){

        String[] items = usluge.replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .replaceAll("\"", "")
                .replaceAll(" h", "")
                .split(",");
        for (int i =0;i<items.length;i++) {

            this.usluge.add(items[i].split(":")[0]);

        }

        Log.d("radnovreme", "setRadnoVreme: " + usluge);



    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(latLng, flags);
        dest.writeInt(id);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(ime);
        dest.writeString(adresa);
        dest.writeString(vrsta);
        dest.writeStringList(usluge);
        dest.writeStringList(radnoVreme);

        dest.writeString(telefon);
        dest.writeString(slika);
    }


    public boolean daLiImaUUslugama(String usluge) {

        for (String x : this.usluge) {

            if (x.equals(usluge))
                return true;

        }
        return false;
    }


}
