package com.application.S2_dev.modele.Tourelle;

import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.IntegerProperty;

public class Tourelle {
    private IntegerProperty x,y;
    protected Terrain terrain;
    private int prix;

    public Tourelle(IntegerProperty x, IntegerProperty y, Terrain terrain,int prix) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.prix=prix;
    }
    public Tourelle(Terrain terrain) {
        this.terrain=terrain;
    }

    public IntegerProperty xProperty(){
        return x;
    }

    public IntegerProperty yProperty(){
        return y;
    }


    public final int getX(){
        return this.xProperty().getValue();
    }

    public final void setX (int n){
        this.xProperty().setValue(n);
    }


    public final int getY(){
        return this.yProperty().getValue();
    }

    public final void setY (int n){
        this.yProperty().setValue(n);
    }





}
