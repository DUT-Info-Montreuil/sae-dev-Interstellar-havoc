package com.application.S2_dev.modele.bfs;

import java.util.LinkedList;

public class BFS {

    public BFS(int[][] matrice, int[] debut, int[] fin) {
        plusCourtChemin2(matrice, debut, fin);

    }

    private LinkedList<Cellule> plusCourtChemin;

    private void visiter(Cellule[][] cellules, LinkedList<Cellule> file, int x, int y, Cellule parent) {
        // hors des limites ou cellule différente de 1
        if (x < 0 || x >= cellules.length || y < 0 || y >= cellules[0].length || cellules[x][y] == null || (cellules[x][y].distance == 2 || cellules[x][y].distance == 3)) {
            return;
        }

        // mettre à jour la distance et le nœud précédent
        int distance = parent.distance + 1;
        Cellule p = cellules[x][y];

        if (distance < p.distance) {
            p.distance = distance;
            p.precedente = parent;
            file.add(p);
        }
    }

    public void plusCourtChemin2(int[][] matrice, int[] debut, int[] fin) {

        int dx = debut[0], dy = debut[1];
        int fx = fin[0], fy = fin[1];
        plusCourtChemin = new LinkedList<>();
        // si la valeur de départ ou d'arrivée est 0, retourne un chemin vide
        if (!(matrice[dx][dy] != 1 || matrice[fx][fy] != 1)) {


            // initialisation des cellules
            int m = matrice.length;
            int n = matrice[0].length;
            Cellule[][] cellules = new Cellule[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrice[i][j] == 1 || matrice[i][j] == 2) {  // Changer ici
                        cellules[i][j] = new Cellule(i, j, Integer.MAX_VALUE, null);
                    }
                }
            }


            // parcours en largeur d'abord
            LinkedList<Cellule> file = new LinkedList<>();
            Cellule debutCellule = cellules[dx][dy];
            debutCellule.distance = 0;
            file.add(debutCellule);
            Cellule destination = null;
            Cellule p;

            while ((p = file.poll()) != null) {
                // trouver la destination
                if (p.i == fx && p.j == fy) {
                    destination = p;
                    break;
                }

                // déplacement vers le haut
                visiter(cellules, file, p.i - 1, p.j, p);
                // déplacement vers le bas
                visiter(cellules, file, p.i + 1, p.j, p);
                // déplacement vers la gauche
                visiter(cellules, file, p.i, p.j - 1, p);
                // déplacement vers la droite
                visiter(cellules, file, p.i, p.j + 1, p);
            }

            // composer le chemin si un chemin existe
            if (destination != null) {
                LinkedList<Cellule> chemin = new LinkedList<>();
                p = destination;
                do {
                    chemin.addFirst(p);
                } while ((p = p.precedente) != null);

                plusCourtChemin = chemin;
            }
        }

    }

    public LinkedList<Cellule> getPlusCourtChemin() {
        return plusCourtChemin;
    }
}
