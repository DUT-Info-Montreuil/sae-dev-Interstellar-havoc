package com.application.S2_dev.modele.bfs;

import java.util.*;

public class BFS {
    public BFS() {

    }

    public LinkedList<Cellule> plusCourtChemin(int[][] matrice, int[] depart, int[] arrivee) {
        // Coordonnées de départ et d'arrivée
        int xDepart = depart[0], yDepart = depart[1];
        int xArrivee = arrivee[0], yArrivee = arrivee[1];

        if (matrice[xDepart][yDepart] != 1 || matrice[xArrivee][yArrivee] != 1) {
            // S'il n'y a pas de chemin possible, affiche un message et retourne une LinkedList vide
            System.out.println("Il n'y a pas de chemin possible.");
            return new LinkedList<>();
        }

        // Initialisation des cellules
        int m = matrice.length;
        int n = matrice[0].length;
        Cellule[][] cellules = new Cellule[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrice[i][j] == 1) {
                    cellules[i][j] = new Cellule(i, j, Integer.MAX_VALUE, null);
                }
            }
        }

        // BFS
        LinkedList<Cellule> file = new LinkedList<>();
        Cellule departCellule = cellules[xDepart][yDepart];
        departCellule.distance = 0;
        file.add(departCellule);
        Cellule arriveeCellule = null;
        Cellule celluleCourante;

        while ((celluleCourante = file.poll()) != null) {
            // Trouve la destination
            if (celluleCourante.x == xArrivee && celluleCourante.y == yArrivee) {
                arriveeCellule = celluleCourante;
                break;
            }

            // Déplacement vers le haut
            visiter(cellules, file, celluleCourante.x - 1, celluleCourante.y, celluleCourante);
            // Déplacement vers le bas
            visiter(cellules, file, celluleCourante.x + 1, celluleCourante.y, celluleCourante);
            // Déplacement vers la gauche
            visiter(cellules, file, celluleCourante.x, celluleCourante.y - 1, celluleCourante);
            // Déplacement vers la droite
            visiter(cellules, file, celluleCourante.x, celluleCourante.y + 1, celluleCourante);

        }

        // Construit le chemin s'il existe
        if (arriveeCellule == null) {
            System.out.println("Il n'y a pas de chemin possible.");
            return new LinkedList<>();
        } else {
            LinkedList<Cellule> chemin = new LinkedList<>();
            celluleCourante = arriveeCellule;
            do {
                chemin.addFirst(celluleCourante);
            } while ((celluleCourante = celluleCourante.precedente) != null);

            return chemin;
        }
    }

    private void visiter(Cellule[][] cellules, LinkedList<Cellule> file, int x, int y, Cellule parent) {
        // Hors des limites ou cellule différente de 1
        if (x < 0 || x >= cellules.length || y < 0 || y >= cellules[0].length || cellules[x][y] == null || (cellules[x][y].distance == 2 || cellules[x][y].distance == 3) ) {
            return;
        }

        // Met à jour la distance et le nœud précédent
        int distance = parent.distance + 1;
        Cellule p = cellules[x][y];

        if (distance < p.distance) {
            p.distance = distance;
            p.precedente = parent;
            file.add(p);
        }
    }
}
