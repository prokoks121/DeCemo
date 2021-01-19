package Data;

import android.content.Context;
import android.util.Log;

import com.example.deizaci.ListaKafica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StorageJsonData {




    public static void writeToFile(String userString, Context context, String FILE_NAME) {

        File file = new File(context.getFilesDir(),FILE_NAME);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int readVersion(Context context, String FILE_NAME) {

        File file = new File(context.getFilesDir(),FILE_NAME);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

                stringBuilder.append(line);

            bufferedReader.close();
            String responce = stringBuilder.toString();
            responce.replaceAll(" ","");



                return Integer.parseInt(responce);


        }catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static ArrayList<ListaKafica> readFromFile(Context context, String FILE_NAME) {
        Log.d("lista_json" ,"Poceo");

        File file = new File(context.getFilesDir(),FILE_NAME);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String responce = stringBuilder.toString();


            return  getListaKafica(responce);

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<ListaKafica> getListaKafica(String responce) {
        ArrayList<ListaKafica> listaKafica = new ArrayList<>();

        try {
            JSONArray json = new JSONArray(responce);


            for (int i = 0; i < json.length(); i++) {
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

            }
            return listaKafica;
        } catch(JSONException e){
                e.printStackTrace();
            }
return null;

        }
    }
