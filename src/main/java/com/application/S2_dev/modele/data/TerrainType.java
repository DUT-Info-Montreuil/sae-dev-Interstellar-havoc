
package com.application.S2_dev.modele.data;

public enum TerrainType {
    GRASS(0),PATH(1),BLOCKED(2), BATS(3);
    
    private int value;
    
    private TerrainType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
