package com.example.deizaci;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Rezervacije extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervacije);

        Spinner dropdown = findViewById(R.id.spiner1);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, items);
        adapter.setDropDownViewResource(R.layout.spiner_dropdown);

        dropdown.setAdapter(adapter);

        Spinner dropdown2 = findViewById(R.id.spiner2);
        items = new String[]{"Unutra", "Napolju"};
        adapter = new ArrayAdapter<>(this, R.layout.spinner, items);
        adapter.setDropDownViewResource(R.layout.spiner_dropdown);

        dropdown2.setAdapter(adapter);

        Spinner dropdown3 = findViewById(R.id.spiner3);
        adapter = new ArrayAdapter<>(this, R.layout.spinner, daniRezervacije());
        adapter.setDropDownViewResource(R.layout.spiner_dropdown);

        dropdown3.setAdapter(adapter);


    }


    private String[] daniRezervacije(){
        String[] items = new String[3];


        GregorianCalendar calendar = new GregorianCalendar();
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        int dan = calendar.get(Calendar.DAY_OF_MONTH);
        int mesec  = calendar.get(Calendar.MONTH);

        items[0] = "Danas " + dan + "."+ mesec;
        if (i == 7) i=1; else i++;
        items[1] = vratiDan(i) + dan + "." + mesec;
        if (i == 7) i=1; else i++;
        items[2] = vratiDan(i) + dan + "."+ mesec;


        return items;

    }

    private String vratiDan( int i){
        String dayOfTheWeek;
        if(i == 2){
            dayOfTheWeek = "Ponedeljak ";
        } else if (i==3){
            dayOfTheWeek = "Utorak ";
        } else if (i==4){
            dayOfTheWeek = "Sreda ";
        } else if (i==5){
            dayOfTheWeek = "Četvrtak ";
        } else if (i==6){
            dayOfTheWeek = "Petak ";
        } else if (i==7){
            dayOfTheWeek = "Subota ";
        } else {
            dayOfTheWeek = "Nedelja ";
        }
        return dayOfTheWeek;

    }
}