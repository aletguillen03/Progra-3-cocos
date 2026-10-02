import java.util.ArrayList;
import java.util.List;

public class GreedyCambio {

    // Monedas ya ordenadas de mayor a menor
    static int[] monedas = {4, 3, 1};

    static List<Integer> cambioGreedy(int monto) {
        List<Integer> usadas = new ArrayList<>();
        for (int moneda : monedas) {
            while (monto >= moneda) {
                usadas.add(moneda);
                monto -= moneda;
            }
        }
        return usadas;
    }

    public static void main(String[] args) {
        int[] montos = {6, 8, 10};

        for (int monto : montos) {
            List<Integer> resultado = cambioGreedy(monto);
            System.out.println("Cambio para " + monto + ": " + resultado
                    + " -> " + resultado.size() + " monedas");
        }
    }
}
