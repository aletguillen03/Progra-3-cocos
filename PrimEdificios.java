public class PrimEdificios {

    static final char[] NOMBRES = {'A', 'B', 'C', 'D', 'E', 'F'};
    static final int N = NOMBRES.length;
    static final int SIN_ARISTA = 0;

    public static void main(String[] args) {
        // ---------- Consigna 1: grafo no dirigido y ponderado ----------
        int[][] grafo = new int[N][N];
        agregarArista(grafo, 'A', 'B', 4);
        agregarArista(grafo, 'A', 'C', 3);
        agregarArista(grafo, 'B', 'C', 2);
        agregarArista(grafo, 'B', 'D', 5);
        agregarArista(grafo, 'C', 'D', 3);
        agregarArista(grafo, 'C', 'E', 6);
        agregarArista(grafo, 'D', 'E', 1);
        agregarArista(grafo, 'D', 'F', 7);
        agregarArista(grafo, 'E', 'F', 4);

        // ---------- Consignas 2 y 3: Prim desde A ----------
        prim(grafo, 'A');
    }

    static void agregarArista(int[][] g, char origen, char destino, int peso) {
        int i = indice(origen), j = indice(destino);
        g[i][j] = peso;
        g[j][i] = peso; // no dirigido
    }

    static int indice(char c) {
        return c - 'A';
    }

    static void prim(int[][] g, char inicio) {
        boolean[] tratado = new boolean[N]; // true = ya está en el árbol
        int[] orden = new int[N];           // orden en que se incorporan
        int cantTratados = 0;

        // R: mejor arista de cada pendiente hacia el árbol
        int[] pesoR = new int[N];
        int[] origenR = new int[N];
        for (int i = 0; i < N; i++) {
            pesoR[i] = SIN_ARISTA;
            origenR[i] = -1;
        }

        // Inicial: elegir vértice y sacarlo de pendientes
        int vertice = indice(inicio);
        tratado[vertice] = true;
        orden[cantTratados++] = vertice;

        // Agregar a R las aristas del vértice inicial
        for (int w = 0; w < N; w++) {
            if (g[vertice][w] != SIN_ARISTA) {
                pesoR[w] = g[vertice][w];
                origenR[w] = vertice;
            }
        }

        int costoAcumulado = 0;
        String formato = "%-10s| %-20s| %-38s| %-20s| %-13s| %s%n";
        System.out.printf(formato, "Iteración", "Vértices incorp.", "Aristas candidatas",
                "Arista seleccionada", "Nuevo vértice", "Costo acumulado");
        System.out.println("-".repeat(125));
        System.out.printf(formato, "Inicial", verticesTratados(orden, cantTratados), "-", "-", "-", 0);

        StringBuilder aristasARM = new StringBuilder();
        int iteracion = 1;

        // Mientras haya pendientes
        while (cantTratados < N) {
            String tratadosAntes = verticesTratados(orden, cantTratados);
            String candidatas = aristasCandidatas(g, tratado, orden, cantTratados);

            // Elegir el pendiente con la arista más barata hacia el árbol
            int elegido = -1;
            for (int v = 0; v < N; v++) {
                if (!tratado[v] && pesoR[v] != SIN_ARISTA
                        && (elegido == -1 || pesoR[v] < pesoR[elegido])) {
                    elegido = v;
                }
            }
            if (elegido == -1) { // grafo no conexo
                System.out.println("El grafo no es conexo: no se puede completar el árbol.");
                return;
            }

            // Sacar de pendientes e incorporar al árbol
            tratado[elegido] = true;
            orden[cantTratados++] = elegido;
            costoAcumulado += pesoR[elegido];

            String arista = NOMBRES[origenR[elegido]] + "-" + NOMBRES[elegido] + " (" + pesoR[elegido] + ")";
            aristasARM.append("  ").append(arista).append("\n");

            System.out.printf(formato, iteracion, tratadosAntes, candidatas, arista,
                    NOMBRES[elegido], costoAcumulado);

            // Actualizar R: para cada pendiente, ¿el nuevo vértice ofrece una arista mejor?
            for (int p = 0; p < N; p++) {
                if (!tratado[p] && g[p][elegido] != SIN_ARISTA) {
                    if (pesoR[p] == SIN_ARISTA) {              // no tenía arista en R
                        pesoR[p] = g[p][elegido];
                        origenR[p] = elegido;
                    } else if (pesoR[p] > g[p][elegido]) {     // eliminar la anterior y agregar la nueva
                        pesoR[p] = g[p][elegido];
                        origenR[p] = elegido;
                    }
                }
            }
            iteracion++;
        }

        System.out.println();
        System.out.println("Aristas del árbol de expansión mínima:");
        System.out.print(aristasARM);
        System.out.println("Cantidad de aristas: " + (N - 1));
        System.out.println("Costo total de instalación: " + costoAcumulado + " (miles de u.m.)");
    }

    // Lista los vértices ya incorporados, en orden: "A, C, B"
    static String verticesTratados(int[] orden, int cant) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cant; i++) {
            if (i > 0) sb.append(", ");
            sb.append(NOMBRES[orden[i]]);
        }
        return sb.toString();
    }

    // Aristas que unen un vértice del árbol con uno pendiente
    static String aristasCandidatas(int[][] g, boolean[] tratado, int[] orden, int cant) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cant; i++) {
            int t = orden[i];
            for (int p = 0; p < N; p++) {
                if (!tratado[p] && g[t][p] != SIN_ARISTA) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(NOMBRES[t]).append("-").append(NOMBRES[p])
                      .append("(").append(g[t][p]).append(")");
                }
            }
        }
        return sb.toString();
    }
}