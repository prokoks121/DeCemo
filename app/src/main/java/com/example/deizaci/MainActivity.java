package com.example.deizaci;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import Data.StorageJsonData;
import Model.BottomNavigacija;
import Model.ConnectBack;
import Model.Global;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, FragmentManager.OnBackStackChangedListener {


    private BottomNavigacija nav;
    private Fragment f1;
    private Global global;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.procetna);
        global = (Global) this.getApplication();

        new Global();
        ConnectBack conn = new ConnectBack(this);


        conn.listaSvirki();

        if(StorageJsonData.readFromFile(this,"lista_Kafica") != null){

            conn.proveraVerzije(global);


        }else{
            conn.ListaKafica();
        }


        Fragment f0 = new Logins();
        f1 = new MainFragment();
        Fragment f2 = new Pretraga();

        // Add fragments
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.placeholder1, f1,"main").addToBackStack(null)
                .commit();

        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);

        String username = pref.getString("username", "");
        String password = pref.getString("password", "");
        Log.d("username",username + "1");
        if (!username.equals("") && !password.equals("")){
            global.setListenerKorisnik(() -> {
                if ( global.getKorisnik() != null ) {
                    Log.d("username",username + "1");

                    setFragmentOnPosition(new user(),0,"user");

                }
            });

            conn.login(username,password,this);

        }


        int page_id = 2;
        nav = new BottomNavigacija(page_id, this);
        nav.addFragment(f0,"logins");
        nav.addFragment(f1,"main");
        nav.addFragment(f2,"pretraga");

        nav.show();


        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentResumed(fm, f);
              setBottomMenuBack(f);
            }
        },true);



    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        MainFragment fragment = (MainFragment) f1;
        fragment.onCheckedChanged1( buttonView,  isChecked);
    }

    public void setFragmentOnPosition(Fragment fragment, int postion,String tag){
nav.setFragmentOnPozition(fragment,postion,tag);
    }


    @Override
    public void onBackStackChanged() {

Log.d("tagFr","radi");
    }
    private void setBottomMenuBack(Fragment f) {
        int navId = nav.getPage_id1();
        String tag = f.getTag();
        if (tag != null) {
            if (navId != 2 && tag.equals("main")) {
                nav.show(2);
                nav.setPage_id1(2);
            } else if (navId != 3 && tag.equals("pretraga")) {
                nav.show(3);
                nav.setPage_id1(3);

            } else if (navId != 1 && (tag.equals("logins") || tag.equals("user"))) {
                nav.show(1);
                nav.setPage_id1(1);

            }
        }
    }

}