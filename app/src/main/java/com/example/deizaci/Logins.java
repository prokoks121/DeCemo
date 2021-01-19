package com.example.deizaci;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import Model.ConnectBack;
import Model.Global;

import static android.content.Context.MODE_PRIVATE;

public class Logins extends Fragment {

    private EditText username,password;
    private String encryptPassword;
    private Fragment f4;
    private Global global;
    private View view;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_logins, container, false);
        global = (Global) Objects.requireNonNull(getActivity()).getApplication();

        username = view.findViewById(R.id.username);
        password =  view.findViewById(R.id.password);
        Button potvrdi =  view.findViewById(R.id.potvrdi);
        potvrdi.setOnClickListener(v -> {
            encryptPassword = md5(password.getText().toString());
            Log.d("sifra",encryptPassword);
            ConnectBack conn = new ConnectBack(getActivity());

            global.setListenerKorisnik(() -> {
                if ( global.getKorisnik() != null ){
                    f4 = new user();
                    SharedPreferences userDetails = getActivity().getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor edit = userDetails.edit();
                    edit.putString("username", username.getText().toString().trim());
                    edit.putString("password", encryptPassword.trim());
                    edit.apply();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.placeholder1, f4);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    ((MainActivity) getActivity()).setFragmentOnPosition(f4,0,"user");
                }
            });
            conn.login(username.getText().toString(),encryptPassword,getActivity());

        });


        return view;
    }


    private String md5(String password){
        String encryptPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            encryptPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return encryptPassword;
    }



}