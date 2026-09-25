import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// clase Arista
class Arista implements Comparable<Arista> {
    char origen, destino;
    int costo;

    Arista(char origen, char destino, int costo) {
        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }

    @Override
    public int compareTo(Arista otra) {
        return Integer.compare(this.costo, otra.costo);
    }

    @Override
    public String toString() {
        return origen + "-" + destino;
    }
}

// clase UnionFind
class UnionFind {
    Map<Character, Character> padre = new HashMap<>(); // utilizamos HashMap que es más útil en este caso en comparación a usar array

    UnionFind(char[] vertices) {
        for (char v : vertices) padre.put(v, v); // cada vértice empieza siendo su propio "jefe"
    }

    char find(char v) {
        if (padre.get(v) != v) {
            padre.put(v, find(padre.get(v))); // compresión de camino
        }
        return padre.get(v);
    }

    boolean union(char a, char b) {
        char raizA = find(a);
        char raizB = find(b);
        if (raizA == raizB) return false; // ya conectados -> ciclo
        padre.put(raizA, raizB);
        return true;
    }
}

// Main para realizar las pruebas
public class Kruskal {
    public static void main(String[] args) {
        char[] vertices = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        List<Arista> aristas = new ArrayList<>(List.of(
            new Arista('A', 'B', 4), new Arista('A', 'C', 3),
            new Arista('B', 'C', 2), new Arista('B', 'D', 5),
            new Arista('C', 'D', 4), new Arista('C', 'E', 6),
            new Arista('D', 'E', 3), new Arista('D', 'F', 7),
            new Arista('E', 'F', 2), new Arista('E', 'G', 5),
            new Arista('F', 'G', 3)
        ));

        Collections.sort(aristas); // ordenar por costo, de menor a mayor

        UnionFind uf = new UnionFind(vertices);
        List<Arista> aem = new ArrayList<>();
        int costoTotal = 0;

        for (Arista a : aristas) {
            if (aem.size() == vertices.length - 1) break; // ya tenemos el árbol completo

            if (uf.union(a.origen, a.destino)) {
                aem.add(a);
                costoTotal += a.costo;
                System.out.println("Aceptada: " + a + " (costo " + a.costo + ")");
            } else {
                System.out.println("Rechazada: " + a + " -> forma ciclo");
            }
        }

        System.out.println("\nCosto total del AEM: " + costoTotal);
    }
}

