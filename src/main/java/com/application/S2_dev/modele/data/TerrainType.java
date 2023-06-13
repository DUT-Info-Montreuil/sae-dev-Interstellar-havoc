
package com.application.S2_dev.modele.data;

public enum TerrainType {
    terrain(0),chemin(1),mur(2), base_tourelle(3),cheminBloque(4), base_finale(5);

    private int valeur;

    private TerrainType(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }
}
