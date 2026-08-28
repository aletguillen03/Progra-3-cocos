import java.util.ArrayList;
import java.util.List;

public class MayoritarioPorVentanas {

    // ---------- Elemento mayoritario dentro de un rango [inicio, fin] (Boyer-Moore + verificación) ----------

    private static int encontrarCandidato(int[] datos, int inicio, int fin) {
        int candidato = datos[inicio];
        int contador = 0;

        for (int i = inicio; i <= fin; i++) {
            if (contador == 0) {
                candidato = datos[i];
                contador = 1;
            } else if (datos[i] == candidato) {
                contador++;
            } else {
                contador--;
            }
        }
        return candidato;
    }

    private static int contarOcurrencias(int[] datos, int inicio, int fin, int candidato) {
        int cuenta = 0;
        for (int i = inicio; i <= fin; i++) {
            if (datos[i] == candidato) {
                cuenta++;
            }
        }
        return cuenta;
    }

    // Devuelve el código mayoritario de la ventana [inicio, fin], o null si no existe
    private static Integer mayoritarioEnVentana(int[] datos, int inicio, int fin) {
        int tam = fin - inicio + 1;
        int candidato = encontrarCandidato(datos, inicio, fin);
        int cuenta = contarOcurrencias(datos, inicio, fin, candidato);
        return (cuenta > tam / 2.0) ? candidato : null;
    }

    // ---------- Validaciones (consigna 3) ----------

    private static void validar(int n, int k, int r, int totalVentanas) {
        if (k < 1 || k > n) {
            throw new IllegalArgumentException("k debe cumplir 1 <= k <= n (k=" + k + ", n=" + n + ")");
        }
        // Nota: el enunciado pide 1 <= r <= n - k + 1, fórmula pensada para ventanas
        // deslizantes solapadas (que se corren de a 1). Como acá las ventanas son
        // bloques NO solapados de tamaño k, la cantidad real de ventanas es
        // ceil(n / k), y es contra ese valor que validamos r.
        if (r < 1 || r > totalVentanas) {
            throw new IllegalArgumentException(
                "r debe cumplir 1 <= r <= cantidad de ventanas (r=" + r + ", ventanas=" + totalVentanas + ")");
        }
    }

    // ---------- Recorrido de ventanas + detección de alertas (consignas 1 y 2) ----------

    public static void analizar(int[] datos, int k, int r) {
        int n = datos.length;
        int totalVentanas = (int) Math.ceil(n / (double) k);
        validar(n, k, r, totalVentanas);

        Integer codigoRacha = null; // código que se está repitiendo
        int largoRacha = 0;         // cuántas ventanas consecutivas lleva
        boolean alertaYaEmitida = false; // evita repetir la alerta mientras dure la racha

        System.out.println("Ventanas (k=" + k + "), r=" + r + ":");

        for (int w = 0; w < totalVentanas; w++) {
            int inicio = w * k;
            int fin = Math.min(inicio + k - 1, n - 1);

            Integer mayoritario = mayoritarioEnVentana(datos, inicio, fin);

            System.out.println("  Ventana [" + inicio + "," + fin + "] -> mayoritario: "
                + (mayoritario == null ? "ninguno" : mayoritario));

            if (mayoritario != null && mayoritario.equals(codigoRacha)) {
                largoRacha++;
            } else if (mayoritario != null) {
                // arranca una racha nueva con este código
                codigoRacha = mayoritario;
                largoRacha = 1;
                alertaYaEmitida = false;
            } else {
                // ventana sin mayoría: corta cualquier racha en curso
                codigoRacha = null;
                largoRacha = 0;
                alertaYaEmitida = false;
            }

            if (largoRacha >= r && !alertaYaEmitida) {
                System.out.println("    >>> ALERTA: código " + codigoRacha
                    + " es mayoritario en " + largoRacha + " ventanas consecutivas <<<");
                alertaYaEmitida = true;
            }
        }
    }

    public static void main(String[] args) {
        int[] datos = {1, 2, 2, 2, 3, 2, 2, 1, 1, 1, 2, 1, 2};

        System.out.println("=== r = 2 ===");
        analizar(datos, 4, 2);

        System.out.println();
        System.out.println("=== r = 1 (referencia: alerta ante cualquier ventana con mayoría) ===");
        analizar(datos, 4, 1);
    }
}

