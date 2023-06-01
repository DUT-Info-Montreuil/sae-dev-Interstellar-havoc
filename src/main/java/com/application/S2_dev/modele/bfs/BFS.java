// Package et importations

package com.application.S2_dev.modele.bfs;

import java.util.*;

// Classe BFS pour la recherche en largeur d'abord
public class BFS {
    // Constructeur de la classe BFS
    public BFS() {
        // Aucune initialisation particulière dans le constructeur
    }

    // Fonction pour trouver le plus court chemin entre deux points dans une matrice
    public LinkedList<Cell> shortestPath(int[][] matrix, int[] start, int[] end) {
        // Coordonnées du point de départ
        int sx = start[0], sy = start[1];
        // Coordonnées du point d'arrivée
        int dx = end[0], dy = end[1];

        // Si la valeur du point de départ ou du point d'arrivée est différente de 1, il n'y a pas de chemin possible
        if (matrix[sx][sy] != 1 || matrix[dx][dy] != 1) {
            System.out.println("Il n'y a pas de chemin possible.");
            return new LinkedList<>();
        }

        // Initialisation des cellules
        int m = matrix.length;
        int n = matrix[0].length;
        Cell[][] cells = new Cell[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // Création d'une cellule si la valeur de la matrice est 1
                if (matrix[i][j] == 1) {
                    cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null);
                }
            }
        }

        // Recherche en largeur d'abord (BFS)
        LinkedList<Cell> queue = new LinkedList<>();
        Cell src = cells[sx][sy]; // Cellule de départ
        src.dist = 0; // La distance de la cellule de départ est mise à 0
        queue.add(src); // Ajout de la cellule de départ à la file d'attente
        Cell dest = null; // Cellule d'arrivée (destination)
        Cell p; // Cellule en cours de traitement

        while ((p = queue.poll()) != null) {
            // Trouver la destination
            if (p.x == dx && p.y == dy) {
                dest = p;
                break;
            }

            // Déplacement vers le haut
            visit(cells, queue, p.x - 1, p.y, p);
            // Déplacement vers le bas
            visit(cells, queue, p.x + 1, p.y, p);
            // Déplacement vers la gauche
            visit(cells, queue, p.x, p.y - 1, p);
            // Déplacement vers la droite
            visit(cells, queue, p.x, p.y + 1, p);
        }

        // Construire le chemin s'il existe
        if (dest == null) {
            System.out.println("Il n'y a pas de chemin possible.");
            return new LinkedList<>();
        } else {
            // Reconstruction du chemin à partir de la destination jusqu'à la source
            LinkedList<Cell> path = new LinkedList<>();
            p = dest;
            do {
                path.addFirst(p);
            } while ((p = p.prev) != null);

            return path;
        }
    }

    // Fonction pour visiter une cellule adjacente
    private void visit(Cell[][] cells, LinkedList<Cell> queue, int x, int y, Cell parent) {
        // Vérifier si la cellule est en dehors des limites de la matrice ou si sa valeur est différente de 1
        if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null ||cells[x][y] == null ||cells[x][y].equals(2) ||cells[x][y].equals(3) ) {
            return;
        }

        // Mettre à jour la distance et le nœud précédent
        int dist = parent.dist + 1; // Calcul de la nouvelle distance en ajoutant 1 à la distance du nœud parent
        Cell p = cells[x][y]; // Récupération de la cellule adjacente correspondante

        // Vérifier si la nouvelle distance est plus petite que la distance actuelle de la cellule adjacente
        if (dist < p.dist) {
            p.dist = dist; // Mise à jour de la distance de la cellule adjacente avec la nouvelle distance
            p.prev = parent; // Mise à jour du nœud précédent de la cellule adjacente avec le nœud parent actuel
            queue.add(p); // Ajout de la cellule adjacente à la file d'attente pour être visitée ultérieurement
        }

    }
}
