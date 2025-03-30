import java.io.*;
import java.util.*;

public class GestorArchivos {
    public static void guardarTrazos(ListaLigada listaTrazos, File archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            Nodo actual = listaTrazos.getCabeza();
            List<Nodo> trazos = new ArrayList<>();
            while (actual != null) {
                trazos.add(new Nodo(actual.getX1(), actual.getY1(), 
                                   actual.getX2(), actual.getY2(), 
                                   actual.getTipo()));
                actual = actual.getSiguiente();
            }
            oos.writeObject(trazos);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al guardar el archivo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void cargarTrazos(ListaLigada listaTrazos, File archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            @SuppressWarnings("unchecked")
            List<Nodo> trazos = (List<Nodo>) ois.readObject();
            
            // Limpiar la lista antes de cargar nuevos trazos
            listaTrazos.limpiar();
            
            for (Nodo trazo : trazos) {
                listaTrazos.agregar(trazo);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar el archivo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}