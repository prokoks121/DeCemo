package Model;

public class ListaSvirki {
    private final String imeLokala;
    private final String koPeva;
    private final String slika;
    private final String datum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getIdLokala() {
        return idLokala;
    }

    private final int idLokala;


    public String getImeLokala() {
        return imeLokala;
    }


    public String getKoPeva() {
        return koPeva;
    }


    public String getSlika() {
        return slika;
    }


    public String getDatum() {
        return datum;
    }


    public ListaSvirki(String imeLokala, String koPeva, String slika, String datum,int id,int idLokala) {
        this.imeLokala = imeLokala;
        this.koPeva = koPeva;
        this.slika = slika;
        this.datum = datum;
        this.id = id;
        this.idLokala = idLokala;
    }



}
