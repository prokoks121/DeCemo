package com.example.deizaci;

public class Filter {


    public static boolean proveraFilteraCeoNiz(String vrsta, boolean bKafic, boolean bPab, boolean bKlub, boolean bSplav, boolean bKafana){
String[] lista;
lista = vrsta.split(",");
for(String x: lista) {


    if (bKafic && daLiJeKafic(x)) return true;

    if (bPab && daLiJePab(x)) return true;

    if (bKlub && daLiJeKlub(x)) return true;
    if (bKafana && daLiJeKafana(x)) return true;
    if (bSplav && daLiJeSplav(x)) return true;

}

        return false;
    }

    private static boolean daLiJeKafic(String vrsta){
        return vrsta.equals("Kafic");
    }
    private static boolean daLiJePab(String vrsta){
        return vrsta.equals("Pab");
    }
    private static boolean daLiJeKlub(String vrsta){
        return vrsta.equals("Klub");
    }

    private static boolean daLiJeKafana(String vrsta){
        return vrsta.equals("Kafana");
    }
    private static boolean daLiJeSplav(String vrsta){
        return vrsta.equals("Splav");
    }

}
