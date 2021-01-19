package Model;

public class RadnoVreme {
    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public RadnoVreme(String dan, String vreme) {
        this.dan = dan;
        this.vreme = vreme;
    }



    private String dan;
    private String vreme;

    @Override
    public String toString() {
        return "RadnoVreme{" +
                "dan='" + dan + '\'' +
                ", vreme='" + vreme + '\'' +
                '}';
    }
}
