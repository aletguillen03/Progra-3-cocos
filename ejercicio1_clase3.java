package ejercicios;

import java.util.Arrays;

public class ejercicio1_clase3 {
    public static void main(String[] args) {
        int[] velocidades = {18, 12, 25, 13, 20, 15};

        int[] paraInsercion = Arrays.copyOf(velocidades, velocidades.length);
        int[] paraSeleccion = Arrays.copyOf(velocidades, velocidades.length);

        System.out.println("Ordenamiento por Inserción: ");
        ordenarPorInsercion(paraInsercion);

        System.out.println("\nOrdenamiento por Selección: ");
        ordenarPorSeleccion(paraSeleccion);
    }

    private static void ordenarPorInsercion(int[] datos) {
        int intercambiosAux = 0;   // total acumulado

        for (int i = 1; i < datos.length; i++) {
            int clave = datos[i];
            int j = i - 1;
            int comparaciones = 0;
            int desplazamientos = 0;

            while (j >= 0 && datos[j] > clave) {
                comparaciones++;
                datos[j + 1] = datos[j];
                desplazamientos++;
                j--;
            }
            if (j >= 0) {
                comparaciones++;
            }

            datos[j + 1] = clave;
            if (desplazamientos > 0) {   // la clave cambió de lugar
                intercambiosAux++;
            }        // uso de la variable auxiliar

            System.out.println("Iteración " + i +
                    " | insertando: " + clave +
                    " | comparaciones: " + comparaciones +
                    " | desplazamientos: " + desplazamientos +
                    " | estado: " + Arrays.toString(datos));
        }

        System.out.println("Total intercambios con auxiliar: " + intercambiosAux);
    }

    private static void ordenarPorSeleccion(int[] datos) {
        int intercambiosAux = 0;   // total acumulado

        for (int i = 0; i < datos.length - 1; i++) {
            int posMenor = i;
            int comparaciones = 0;
            int intercambios = 0;

            for (int j = i + 1; j < datos.length; j++) {
                comparaciones++;
                if (datos[j] < datos[posMenor]) {
                    posMenor = j;
                }
            }

            if (posMenor != i) {
                int temp = datos[i];
                datos[i] = datos[posMenor];
                datos[posMenor] = temp;
                intercambios++;
                intercambiosAux++;     // uso de la variable auxiliar
            }

            System.out.println("Iteración " + i +
                    " | menor: " + datos[i] +
                    " | posición original del menor: " + posMenor +
                    " | comparaciones: " + comparaciones +
                    " | intercambios: " + intercambios +
                    " | estado: " + Arrays.toString(datos));
        }

        System.out.println("Total intercambios con auxiliar: " + intercambiosAux);
    }
}