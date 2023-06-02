
package com.application.S2_dev.modele.data;

public enum TowerType {
    
    Nikola(2), Edison(0), Oppenheimer(1);
    
    private int value;
    
    private TowerType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
