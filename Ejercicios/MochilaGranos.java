public class MochilaGranos {

    /** Mochila entera 0/1 con tabla (bottom-up, sin recursividad). */
    static void resolver(int capacidad, String[] nombres, int[] pesos, int[] ganancias) {
        int n = pesos.length;

        // dp[i][w] = ganancia maxima usando los primeros i lotes con capacidad w
        int[][] dp = new int[n + 1][capacidad + 1];   // fila 0 y columna 0 quedan en 0

        for (int i = 1; i <= n; i++) {
            int peso = pesos[i - 1];
            int ganancia = ganancias[i - 1];
            for (int w = 0; w <= capacidad; w++) {
                int noIncluir = dp[i - 1][w];
                int incluir = (peso <= w) ? ganancia + dp[i - 1][w - peso] : -1;
                dp[i][w] = Math.max(noIncluir, incluir);
            }
        }

        // Recuperacion de los lotes elegidos (de la ultima fila hacia arriba)
        boolean[] elegido = new boolean[n];
        int w = capacidad;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {   // el valor cambio => el lote i fue incluido
                elegido[i - 1] = true;
                w -= pesos[i - 1];
            }
        }
        int pesoTotal = capacidad - w;

        System.out.println("=== Capacidad: " + capacidad + " t ===");
        imprimirTabla(dp, nombres, capacidad, 5);
        System.out.println("Ganancia maxima: $" + dp[n][capacidad]);
        System.out.println("Lotes seleccionados:");
        for (int i = 0; i < n; i++) {
            if (elegido[i]) {
                System.out.println("  Lote " + (i + 1) + " - " + nombres[i]
                        + " (" + pesos[i] + " t, $" + ganancias[i] + ")");
            }
        }
        System.out.println("Peso total transportado: " + pesoTotal + " t");
        System.out.println("Capacidad sobrante: " + (capacidad - pesoTotal) + " t");
        System.out.println();
    }

    /** Muestra la tabla cada "paso" toneladas (todos los pesos son multiplos de 5). */
    static void imprimirTabla(int[][] dp, String[] nombres, int capacidad, int paso) {
        System.out.printf("%-10s", "i \\ w");
        for (int w = 0; w <= capacidad; w += paso) System.out.printf("%6d", w);
        System.out.println();
        for (int i = 0; i < dp.length; i++) {
            System.out.printf("%-10s", i == 0 ? "0 (nada)" : i + " " + nombres[i - 1]);
            for (int w = 0; w <= capacidad; w += paso) System.out.printf("%6d", dp[i][w]);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String[] nombres = {"Soja", "Maiz", "Trigo", "Girasol"};
        int[] pesos      = {40, 30, 20, 25};
        int[] ganancias  = {2400, 1500, 1400, 1250};

        if (args.length > 0) {                 // capacidad opcional por linea de comandos
            resolver(Integer.parseInt(args[0]), nombres, pesos, ganancias);
        } else {
            resolver(70, nombres, pesos, ganancias);   // punto 4
            resolver(90, nombres, pesos, ganancias);   // punto 5
        }
    }
}