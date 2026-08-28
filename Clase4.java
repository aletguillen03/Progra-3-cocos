/**
 * Algoritmo del Elemento Mayoritario (recursivo, divide y venceras)
 *
 * Fase 1 - Busqueda del candidato:
 *   Se divide el arreglo en dos mitades, se busca el candidato de cada mitad
 *   de forma recursiva y luego se combinan: si ambos candidatos coinciden,
 *   ese es el candidato; si no, se cuenta cuantas veces aparece cada uno
 *   dentro del rango actual y se elige el que mas se repite.
 *
 * Fase 2 - Comprobacion de mayoria:
 *   El candidato obtenido en la Fase 1 NO garantiza ser mayoritario
 *   (solo es "el mas probable"), por lo que se debe contar sus apariciones
 *   reales en todo el arreglo y verificar que sean mas de n/2.
 */
public class Clase4 {

    public static void main(String[] args) {
        String[] lenguajes = {"Java", "Python", "Java", "C++", "Java",
                               "Python", "Java", "Java", "C++", "Java"};

        System.out.println("=== Caso 1: Array original ===");
        procesarMayoritario(lenguajes);

        // Se modifica el ultimo elemento: de "Java" pasa a "Python"
        lenguajes[lenguajes.length - 1] = "Python";

        System.out.println("\n=== Caso 2: Array modificado (ultimo elemento -> Python) ===");
        procesarMayoritario(lenguajes);
    }

    /**
     * Ejecuta las dos fases del algoritmo sobre el arreglo dado
     * e imprime el resultado para que la universidad pueda decidir
     * si existe un lenguaje favorito claramente mayoritario.
     */
    static void procesarMayoritario(String[] arr) {
        System.out.println("Array: " + java.util.Arrays.toString(arr));

        // Fase 1: busqueda del candidato
        String candidato = buscarCandidato(arr, 0, arr.length - 1);

        // Fase 2: comprobacion de si el candidato es realmente mayoritario
        int apariciones = contarOcurrencias(arr, candidato, 0);
        boolean esMayoritario = esMayoritario(arr.length, apariciones);

        System.out.println("Candidato encontrado: " + candidato);
        System.out.println("Apariciones reales: " + apariciones + " de " + arr.length);

        if (esMayoritario) {
            System.out.println("Resultado: \"" + candidato + "\" ES el lenguaje mayoritario.");
        } else {
            System.out.println("Resultado: NO existe un lenguaje mayoritario.");
        }
    }

    // ---------------------- FASE 1: BUSQUEDA DEL CANDIDATO ----------------------

    /**
     * Devuelve el elemento candidato a mayoritario dentro de arr[i..j]
     * usando divide y venceras.
     */
    static String buscarCandidato(String[] arr, int i, int j) {
        // Caso base: un solo elemento es candidato de si mismo
        if (i == j) {
            return arr[i];
        }

        int mid = (i + j) / 2;

        String candidatoIzq = buscarCandidato(arr, i, mid);
        String candidatoDer = buscarCandidato(arr, mid + 1, j);

        // Si ambas mitades proponen el mismo candidato, se conserva
        if (candidatoIzq.equals(candidatoDer)) {
            return candidatoIzq;
        }

        // Si difieren, se cuenta cual de los dos aparece mas veces
        // dentro del rango actual [i, j]
        int contIzq = contarEnRango(arr, candidatoIzq, i, j);
        int contDer = contarEnRango(arr, candidatoDer, i, j);

        return (contIzq >= contDer) ? candidatoIzq : candidatoDer;
    }

    /**
     * Cuenta recursivamente cuantas veces aparece "elemento" en arr[i..j].
     */
    static int contarEnRango(String[] arr, String elemento, int i, int j) {
        if (i > j) {
            return 0;
        }
        int resto = contarEnRango(arr, elemento, i + 1, j);
        return (arr[i].equals(elemento) ? 1 : 0) + resto;
    }

    // ---------------------- FASE 2: COMPROBACION DE MAYORIA ----------------------

    /**
     * Cuenta recursivamente cuantas veces aparece "elemento" en todo el arreglo.
     */
    static int contarOcurrencias(String[] arr, String elemento, int i) {
        if (i == arr.length) {
            return 0;
        }
        int resto = contarOcurrencias(arr, elemento, i + 1);
        return (arr[i].equals(elemento) ? 1 : 0) + resto;
    }

    /**
     * Un elemento es mayoritario si aparece mas de n/2 veces.
     */
    static boolean esMayoritario(int n, int conteo) {
        return conteo > n / 2;
    }
}
