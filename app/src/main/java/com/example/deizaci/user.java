package com.example.deizaci;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import Model.Global;
import Model.Korisnik;

import static android.content.Context.MODE_PRIVATE;

public class user extends Fragment {

    private View view;
    private TextView ime,datumRodjenja,email;
    private ImageView slika;
    private Korisnik korisnik;
    private Button logout;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_user, container, false);
        ime = (TextView) view.findViewById(R.id.ime);
        datumRodjenja =  (TextView) view.findViewById(R.id.datumRodjenja);
        email = (TextView) view.findViewById(R.id.email);
        korisnik = ((Global) Objects.requireNonNull(getActivity()).getApplication()).getKorisnik();
        slika = (ImageView) view.findViewById(R.id.slika);

        ime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
        datumRodjenja.setText(korisnik.getDatumRodjenja());
        email.setText(korisnik.getMail());
        String url = "https://api.polovnitelefoni.net/slike/" + korisnik.getImg();

        Picasso.with(view.getContext()).load(url).fit().centerInside().into(slika);

        logout = (Button) view.findViewById(R.id.plogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user", MODE_PRIVATE);
                preferences.edit().clear().apply();
                Fragment fragment = new Logins();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.placeholder1, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                ((Global) Objects.requireNonNull(getActivity()).getApplication()).setKorisnikNonListener(null);
                ((MainActivity) getActivity()).setFragmentOnPosition(fragment,0,"logins");

            }
        });

        return view;
    }
}