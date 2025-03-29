import java.io.*;
import java.util.*;

public class GestorArchivos {
    private static final String ARCHIVO = "trazos.dat";

    public static void guardarTrazos(ListaLigada listaTrazos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            Nodo actual = listaTrazos.getCabeza();
            List<Nodo> trazos = new ArrayList<>();
            while (actual != null) {
                trazos.add(new Nodo(actual.getX1(), actual.getY1(), actual.getX2(), actual.getY2(), actual.getTipo()));
                actual = actual.getSiguiente();
            }
            oos.writeObject(trazos);
            System.out.println("Trazos guardados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cargarTrazos(ListaLigada listaTrazos) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            List<Nodo> trazos = (List<Nodo>) ois.readObject();
            // Limpiar la lista antes de cargar nuevos trazos
            listaTrazos.limpiar();
            for (Nodo trazo : trazos) {
                listaTrazos.agregar(trazo);  // Agregar cada trazo a la lista
            }
            System.out.println("Trazos cargados correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }