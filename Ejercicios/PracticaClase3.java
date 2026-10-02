// Ordena productos por calificacion descendente y, en empate, precio ascendente.
public class PracticaClase3 {

    static class Producto {
        int id;
        String nombre;
        double precio;
        double calificacion;

        Producto(int id, String nombre, double precio, double calificacion) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
            this.calificacion = calificacion;
        }

        public String toString() {
            return nombre + " (calif=" + calificacion + ", precio=" + precio + ")";
        }
    }

    // Negativo si a va antes que b, positivo si va despues.
    static int comparar(Producto a, Producto b) {
        if (a.calificacion != b.calificacion) {
            if (a.calificacion > b.calificacion) {
                return -1;
            } else {
                return 1;
            }
        }
        if (a.precio != b.precio) {
            if (a.precio < b.precio) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

    // ---------- Merge Sort ----------
    static void mergeSort(Producto[] arr, int inicio, int fin) {
        if (inicio >= fin) return;
        int medio = (inicio + fin) / 2;
        mergeSort(arr, inicio, medio);
        mergeSort(arr, medio + 1, fin);
        merge(arr, inicio, medio, fin);
    }

    static void merge(Producto[] arr, int inicio, int medio, int fin) {
        Producto[] izq = new Producto[medio - inicio + 1];
        Producto[] der = new Producto[fin - medio];
        for (int i = 0; i < izq.length; i++) izq[i] = arr[inicio + i];
        for (int j = 0; j < der.length; j++) der[j] = arr[medio + 1 + j];

        int i = 0, j = 0, k = inicio;
        while (i < izq.length && j < der.length) {
            arr[k++] = comparar(izq[i], der[j]) <= 0 ? izq[i++] : der[j++];
        }
        while (i < izq.length) arr[k++] = izq[i++];
        while (j < der.length) arr[k++] = der[j++];
    }

    // ---------- Quick Sort (pivote = ultimo elemento) ----------
    static void quickSort(Producto[] arr, int inicio, int fin) {
        if (inicio >= fin) return;
        int p = particionar(arr, inicio, fin);
        quickSort(arr, inicio, p - 1);
        quickSort(arr, p + 1, fin);
    }

    static int particionar(Producto[] arr, int inicio, int fin) {
        Producto pivote = arr[fin];
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (comparar(arr[j], pivote) <= 0) {
                i++;
                Producto temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            }
        }
        Producto temp = arr[i + 1]; arr[i + 1] = arr[fin]; arr[fin] = temp;
        return i + 1;
    }

    // ---------- Main de prueba ----------
    public static void main(String[] args) {
        Producto[] productos = {
            new Producto(1, "Mouse", 15.0, 4.5),
            new Producto(2, "Teclado", 25.0, 3.0),
            new Producto(3, "Monitor", 150.0, 4.5),
            new Producto(4, "Auriculares", 40.0, 4.8),
            new Producto(5, "Webcam", 30.0, 3.0),
            new Producto(6, "Mousepad", 10.0, 4.5)
        };

        Producto[] conMerge = productos.clone();
        mergeSort(conMerge, 0, conMerge.length - 1);
        System.out.println("Merge Sort:");
        for (Producto p : conMerge) System.out.println(p);

        Producto[] conQuick = productos.clone();
        quickSort(conQuick, 0, conQuick.length - 1);
        System.out.println("\nQuick Sort:");
        for (Producto p : conQuick) System.out.println(p);
    }
}
