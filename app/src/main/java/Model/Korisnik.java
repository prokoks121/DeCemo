package Model;

public class Korisnik {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }



    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }



    public String getDatumRodjenja() {
        return datumRodjenja;
    }



    public String getImg() {
        return img;
    }



    private final String mail;
    private String ime;
    private final String prezime;
    private final String datumRodjenja;
    private final String img;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    private String pass;

    public Korisnik(int id, String mail, String ime, String prezime, String datumRodjenja, String img) {
        this.id = id;
        this.mail = mail;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.img = img;
    }

}
