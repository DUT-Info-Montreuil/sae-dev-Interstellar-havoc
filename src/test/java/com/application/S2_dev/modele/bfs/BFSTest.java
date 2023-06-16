package com.application.S2_dev.modele.bfs;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.map.Terrain;
import org.junit.jupiter.api.Test;


import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {
    @Test
    public void testPlusCourtChemin2() {
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

        LinkedList<Cellule> cheminPlusCourt = BFS.getPlusCourtChemin();

        // Vérifiez si le chemin le plus court est valide
        assertEquals(cheminPlusCourt.size(),8);

        // Affichez le chemin le plus court
        for (Cellule cellule : cheminPlusCourt) {
            System.out.println("Cellule : (" + cellule.getI() + ", " + cellule.getJ() + ")");
        }
    }
    @Test
    public void testLinkedListPlusCourtChemin() {
        int[][] grid = {
                {1, 0, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0, 0, 1, 1}
        };

        int[] depart = {0, 0};
        int[] fin = {1, 7};
        Terrain terrain = new Terrain();
        BFS BFS = new BFS(grid, depart, fin);

        LinkedList<Cellule> cheminPlusCourt = BFS.getPlusCourtChemin();

        assertEquals(15, cheminPlusCourt.size());

        boolean presentDansLinkedList = false;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                    for (Cellule cellule : cheminPlusCourt) {
                        if (cellule.getI() == i && cellule.getJ() == j) {
                            presentDansLinkedList = true;
                            break;
                        }
                    }
            }
        }
        for (Cellule cellule : cheminPlusCourt) {
            System.out.println("Cellule : (" + cellule.getI() + " " + cellule.getJ() + ")");
        }
        assertTrue(presentDansLinkedList);
    }

    @Test
    public void testLinkedListPlusCourtChemin2() {
        int[][] grid = {
                {1, 0, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0, 0, 1, 1}
        };

        int[] depart = {0, 0};
        int[] fin = {1, 7};
        Terrain terrain = new Terrain();
        BFS BFS = new BFS(grid, depart, fin);

        LinkedList<Cellule> cheminPlusCourt = BFS.getPlusCourtChemin();
        assertEquals(15, cheminPlusCourt.size());


    }


}