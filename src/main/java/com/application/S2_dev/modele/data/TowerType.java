
package com.application.S2_dev.modele.data;

public enum TowerType {
    
    Nikola(0), Edison(1), Oppenheimer(2);
    
    private int value;
    
    private TowerType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
