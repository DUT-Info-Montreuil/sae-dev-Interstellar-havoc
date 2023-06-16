package com.application.S2_dev.modele.bfs;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.map.Terrain;
import org.junit.jupiter.api.Test;


import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {
    @Test
    public void testCheminPlusCourt() {
        // Créez une instance de la classe Terrain
        int grid[][] = {{1,0,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                        {1,1,0,0,0,0,0,1},
                        {0,1,0,1,1,1,1,1},
                        {0,1,1,1,0,0,1,1}

        };
        int[] depart = {1, 0};
        int[] fin = {1,7};
        Terrain terrain = new Terrain();
        BFS BFS = new BFS(grid, depart, fin);

        // Obtenez le chemin le plus court à partir de la classe Terrain
        LinkedList<Cellule> cheminPlusCourt = BFS.getPlusCourtChemin();

        // Vérifiez si le chemin le plus court est valide
        assertEquals(cheminPlusCourt.size(),8);

        // Affichez le chemin le plus court
        for (Cellule cellule : cheminPlusCourt) {
            System.out.println("Cellule : (" + cellule.getI() + ", " + cellule.getJ() + ")");
        }
    }

}