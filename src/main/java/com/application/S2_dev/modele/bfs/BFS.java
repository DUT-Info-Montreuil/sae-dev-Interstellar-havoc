package com.application.S2_dev.modele.bfs;

import java.util.*;

public class BFS {

    public BFS() {
    }


    /* la methode shortPath doit renvoyer un void et on fait un bfs.getShortesPath pour l'utilisation
    * On enleve les if dans la methode agit de l'ennemis c'est repetitif trop de redondense
    * Avoir des images bien centré
    * dans la classe terrain c'est lui qui calcul le chemin le plus court avec le bfs et on calcul le bfs une fois et non pas a chaque tour
    * ensuite en fonction du terrain tant qu'il ne change pas on recalcul pas le bfs
    * le chemin de chaque bfs varie en fonction de l'ennemis, deux se deplace sur les 1 et les 2 (le mur) et un autre prend en compte
    * les 2 et 1 et 4 pour les chemin bloquer, une fois qu'il est au 4 il attaque le mur
    * faire les deplacement pixel par pixel */

    public LinkedList<Cell> shortestPath(int[][] matrix, int[] start, int[] end) {
        int sx = start[0], sy = start[1];
        int dx = end[0], dy = end[1];

        //System.out.println("m "+matrix[sx][sy]+" ,m2 "+matrix[dx][dy]);
        // if start or end value is 0, return empty path
        if (matrix[sx][sy] != 1 || matrix[dx][dy] != 1 ) {
            System.out.println("There is no path.");
            return new LinkedList<>();
        }

        // initialize the cells
        int m = matrix.length;
        int n = matrix[0].length;
        Cell[][] cells = new Cell[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1 || matrix [i][j] == 2 ) {  // Change here
                    cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null);
                }
            }
        }

        // breadth-first search
        LinkedList<Cell> queue = new LinkedList<>();
        Cell src = cells[sx][sy];
        src.dist = 0;
        queue.add(src);
        Cell dest = null;
        Cell p;

        while ((p = queue.poll()) != null) {
            // find destination
            if (p.x == dx && p.y == dy) {
                dest = p;
                break;
            }

            // moving up
            visit(cells, queue, p.x - 1, p.y, p);
            // moving down
            visit(cells, queue, p.x + 1, p.y, p);
            // moving left
            visit(cells, queue, p.x, p.y - 1, p);
            // moving right
            visit(cells, queue, p.x, p.y + 1, p);
        }

        // compose the path if path exists
        if (dest == null) {
            System.out.println("There is no path.");
            return new LinkedList<>();
        } else {
            LinkedList<Cell> path = new LinkedList<>();
            p = dest;
            do {
                path.addFirst(p);
            } while ((p = p.prev) != null);

            return path;
        }
    }

    private void visit(Cell[][] cells, LinkedList<Cell> queue, int x, int y, Cell parent) {
        // out of boundary or cell not equals 1
        if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null || (cells[x][y].dist == 2) ) {
            return;
        }

        // update distance and previous node
        int dist = parent.dist + 1;
        Cell p = cells[x][y];

        if (dist < p.dist) {
            p.dist = dist;
            p.prev = parent;
            queue.add(p);
        }
    }




}
