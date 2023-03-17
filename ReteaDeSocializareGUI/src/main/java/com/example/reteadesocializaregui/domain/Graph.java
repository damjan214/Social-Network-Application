package com.example.reteadesocializaregui.domain;

public class Graph {
    /**
     * matricea de adiacenta
     */
    private int[][] matrix;
    /**
     * lista de noduri vizitate
     */
    private int[] visited;
    /**
     * numarul de utilizatori din comunitate(capacitatea matricii de adiacenta si a vectorului de noduri vizitate
     */
    private int nr_of_users;

    /**
     * Constructorul pentru graph
     * @param matrix - matricea de adiacenta
     * @param visited - vectorul de noduri vizitate
     * @param nr_of_users - numarul de utilizatori din comunitate
     */
    public Graph(int[][] matrix, int[] visited, int nr_of_users) {
        this.matrix = matrix;
        this.visited = visited;
        this.nr_of_users = nr_of_users;
    }

    /**
     * Functie ce determina numarul de comunitati din retea.
     *
     * @return - numarul de comunitati din retea
     */
    public int number_of_communities() {
        int nr_of_communities = 0;
        for (int index = 0; index < nr_of_users; index++) {
            if (visited[index] == 0) {
                dfs(index, visited, matrix, nr_of_users);
                nr_of_communities++;
            }
        }
        return nr_of_communities;
    }

    /**
     * Functie ce face parcurgerea in adancime a grafului dat de matricea de adiacenta
     *
     * @param k           - nodul de la care se incepe parcurgerea
     * @param visited     - vectorul ce tine minte care noduri au fost vizitate
     * @param matrix      - matricea de adiacenta
     * @param nr_of_users - nr de noduri din graf, sau numarul de utilizatori din retea
     */
    public void dfs(int k, int visited[], int matrix[][], int nr_of_users) {
        visited[k] = 1;
        for (int i = 0; i < nr_of_users; i++)
            if (matrix[k][i] == 1 && visited[i] == 0) {
                dfs(i, visited, matrix, nr_of_users);
            }
    }
}
