package Model;

import android.app.Application;

import com.example.deizaci.ListaKafica;

import java.util.ArrayList;
import java.util.List;

public class Global extends Application {
    public List<ListaSvirki> getListaSvirki() {
        return listaSvirki;
    }
    private ChangeListenerListaKafica listenerListaKafica;

    public void setListenerKorisnik(ChangeKorisnik listenerKorisnik) {
        this.listenerKorisnik = listenerKorisnik;
    }

    private ChangeKorisnik listenerKorisnik;

    public void setListenerListaKaficaPretraga(ChangeListenerListaKaficaPretraga listenerListaKaficaPretraga) {
        this.listenerListaKaficaPretraga = listenerListaKaficaPretraga;
    }

    private ChangeListenerListaKaficaPretraga listenerListaKaficaPretraga;

    public void setListenerListaSvirkiPretraga(ChangeListenerListaSvirkiPretraga listenerListaSvirkiPretraga) {
        this.listenerListaSvirkiPretraga = listenerListaSvirkiPretraga;
    }

    private ChangeListenerListaSvirkiPretraga listenerListaSvirkiPretraga;



    private ChangeListenerListaSvirki listenerListaSvirki;


    public void setListaSvirki(List<ListaSvirki> listaSvirki) {
        this.listaSvirki = listaSvirki;
try {
    listenerListaSvirki.onChange();
    listenerListaSvirkiPretraga.onChange();
} catch (Exception e) {
    e.printStackTrace();
}


    }

    private List<ListaSvirki> listaSvirki = new ArrayList<>();

    public List<ListaKafica> getListaKafica() {
        return listaKafica;
    }

    public void setListaKafica(List<ListaKafica> listaKafica) {
        this.listaKafica = listaKafica;
try {
    listenerListaKafica.onChange();
    listenerListaKaficaPretraga.onChange();
} catch (Exception e) {
    e.printStackTrace();
}


    }

    private List<ListaKafica> listaKafica = new ArrayList<>();

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
        listenerKorisnik.onChange();

    }
    public void setKorisnikNonListener(Korisnik korisnik) {
        this.korisnik = korisnik;

    }

    private Korisnik korisnik;


    public void setListenerListaSvirki(ChangeListenerListaSvirki listener) {
        this.listenerListaSvirki = listener;
    }

    public void setListenerListaKafica(ChangeListenerListaKafica listener) {

        this.listenerListaKafica = listener;
    }
    public interface ChangeListenerListaKafica{
        void onChange();


    }

    public interface ChangeKorisnik{
        void onChange();


    }



    public interface ChangeListenerListaKaficaPretraga{
        void onChange();


    }


    public interface ChangeListenerListaSvirki{
        void onChange();

    }

    public interface ChangeListenerListaSvirkiPretraga{
        void onChange();

    }



}
