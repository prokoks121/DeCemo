package Model;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.deizaci.ListaKafica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Data.StorageJsonData;

public class ConnectBack{

    private final RequestQueue req;
    private final Activity activity;
    private final List<ListaKafica> listaKafica = new ArrayList<>();
    private final List<ListaSvirki> listaSvirki = new ArrayList<>();
    private Korisnik korisnik;





    public  ConnectBack(Activity activity) {

        this.activity = activity;
        req = Volley.newRequestQueue(this.activity);
         }






    public void ListaKafica(){
        String url = "https://api.polovnitelefoni.net/api.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                JSONArray json = response.getJSONArray("lokali");

                for (int i= 0;i< json.length();i++){
                    JSONObject lok = json.getJSONObject(i);
                    int id = lok.getInt("id");
                    String ime = lok.getString("ime");
                    String adresa = lok.getString("adresa");
                    String vrsta = lok.getString("vrsta");
                    String radno = lok.getString("radno");
                    String usluge = lok.getString("usluge");

                    String telefon = lok.getString("telefon");
                    double lat = lok.getDouble("lat");
                    double lon = lok.getDouble("long");

                    String slika = lok.getString("slika");




                    listaKafica.add(new ListaKafica(id,lat,lon,ime,adresa, vrsta,  radno,usluge, telefon, slika));

                    Log.d("radnovremeILista", String.valueOf(listaKafica));






                }

                StorageJsonData.writeToFile( json.toString(), activity,"lista_Kafica");
                ((Global) activity.getApplication()).setListaKafica(listaKafica);



            } catch (JSONException e) {

                e.printStackTrace();

            }

        }, error -> {
            if (listaKafica.size() != 0) {
                listaKafica.clear();
            }
            ListaKafica();
            error.printStackTrace();
        });
        request.setShouldCache(false);

        req.add(request);

    }


    public void listaSvirki(){
        String url = "https://api.polovnitelefoni.net/api-dogadjaji.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                JSONArray json = response.getJSONArray("dog");

                for (int i= 0;i< json.length();i++){
                    JSONObject lok = json.getJSONObject(i);
                    int id = lok.getInt("id");
                    int idLokala = lok.getInt("id_lokala");
                    String ime = lok.getString("imeLokala");
                    String koPeva = lok.getString("koPeva");
                    String datum = lok.getString("datum");
                    String slika = lok.getString("slika");

                    listaSvirki.add(new ListaSvirki(ime,koPeva,slika,datum,id,idLokala));








                }



                ((Global) activity.getApplication()).setListaSvirki(listaSvirki);



            } catch (JSONException e) {

                e.printStackTrace();

            }

        }, error -> {
            if (listaSvirki.size() != 0) {
                listaSvirki.clear();
            }
listaSvirki();
            error.printStackTrace();
        });
        request.setShouldCache(false);

        req.add(request);

    }




    public void login(String username, String password,Activity activitys){

        String url = "https://api.polovnitelefoni.net/api-korisnik.php?usr="+username+"&pass="+password;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, response -> {

            try {
                JSONArray json = response.getJSONArray("korisnik");

                    JSONObject lok = json.getJSONObject(0);
                    int id = lok.getInt("id");
                    String mail = lok.getString("email");
                    String ime= lok.getString("ime");
                    String prezime = lok.getString("prezime");
                    String datumRodjenja = lok.getString("datum_rodjenja");
                    String img = lok.getString("slika");

                korisnik = new Korisnik(id,mail,ime,prezime,datumRodjenja,img);
                Log.d("LoginP","Postavljen korisnik");
                ((Global) activity.getApplication()).setKorisnik(korisnik);

            } catch (JSONException e) {
                korisnik = null;
                Toast.makeText(activitys.getApplicationContext(),"Uneti podaci su netačni",Toast.LENGTH_LONG).show();
                Log.d("LoginP","1");

                ((Global) activity.getApplication()).setKorisnik(korisnik);
                e.printStackTrace();

            }

        }, error -> {
korisnik = null;
            Toast.makeText(activitys.getApplicationContext(),"Nema interntea1",Toast.LENGTH_SHORT).show();
            Log.d("LoginP","2");

            ((Global) activity.getApplication()).setKorisnik(korisnik);
            error.printStackTrace();
        });

        request.setShouldCache(false);

        req.add(request);

    }


    public void proveraVerzije( Global global){
        int trenutraVerzija = StorageJsonData.readVersion(activity, "verzija");

        String url = "https://api.polovnitelefoni.net/api-listaVerzija.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {

                JSONArray json = response.getJSONArray("verzija");


                    JSONObject lok = json.getJSONObject(0);
                   int id = lok.getInt("id");
                    Log.d("idVerzije", String.valueOf(id));
                Log.d("idVerzije", String.valueOf(trenutraVerzija));

                if (trenutraVerzija  == id && id !=0){
                    global.setListaKafica(StorageJsonData.readFromFile(activity,"lista_Kafica"));

                }else{
                    StorageJsonData.writeToFile(String.valueOf(id), activity,"verzija");
                   ListaKafica();
                }





            } catch (JSONException e) {

                e.printStackTrace();
                global.setListaKafica(StorageJsonData.readFromFile(activity,"lista_Kafica"));

            }

        }, Throwable::printStackTrace);
        request.setShouldCache(false);
        global.setListaKafica(StorageJsonData.readFromFile(activity,"lista_Kafica"));

        req.add(request);

    }

}
