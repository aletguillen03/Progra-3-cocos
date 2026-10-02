import java.util.ArrayList;
import java.util.List;

public class MochilaProductos {

    // Construye la tabla de PD siguiendo la estrategia de la clase (sin fila de ceros)
    public static int[][] construirTabla(int[] pesos, int[] valores, int P) {
        int n = pesos.length;
        int[][] M = new int[n][P + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= P; j++) {
                if (i == 0 && pesos[i] > j) {
                    M[i][j] = 0;                                   
                } else if (i == 0) {
                    M[i][j] = valores[i];        
                } else if (pesos[i] > j) {
                    M[i][j] = M[i - 1][j];                         
                } else {
                    M[i][j] = Math.max(M[i - 1][j],                
                                       M[i - 1][j - pesos[i]] + valores[i]); 
                }
            }
        }
        return M;
    }

    // Reconstrucción top-down: recorre la tabla desde M[n-1][P] hacia arriba
    public static List<Integer> reconstruir(int[][] M, int[] pesos, int P) {
        List<Integer> elegidos = new ArrayList<>();
        int j = P;
        for (int i = M.length - 1; i > 0; i--) {
            if (M[i][j] != M[i - 1][j]) {  
                elegidos.add(0, i);
                j -= pesos[i];
            }
        }
        if (M[0][j] > 0) {             
            elegidos.add(0, 0);
        }
        return elegidos;
    }

    public static void resolver(String[] nombres, int[] pesos, int[] valores, int P) {
        int[][] M = construirTabla(pesos, valores, P);
        List<Integer> elegidos = reconstruir(M, pesos, P);

        int pesoTotal = 0;
        System.out.println("Capacidad máxima: " + P + " kg");
        System.out.println("Productos seleccionados:");
        for (int i : elegidos) {
            System.out.println("  - " + nombres[i] + " (peso " + pesos[i] + " kg, valor $" + valores[i] + ")");
            pesoTotal += pesos[i];
        }
        System.out.println("Valor máximo: $" + M[pesos.length - 1][P]);
        System.out.println("Peso transportado: " + pesoTotal + " kg");
        System.out.println("Capacidad sobrante: " + (P - pesoTotal) + " kg");
    }

    public static void main(String[] args) {
        String[] nombres = {"A", "B", "C"};
        int[] pesos      = {10, 20, 30};
        int[] valores    = {60, 100, 120};
        int capacidad    = 50;

        resolver(nombres, pesos, valores, capacidad);
    }
}
