import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class GestorArchivos {
    public static void guardarTrazos(ListaLigada listaTrazos, File archivo, FrmTrazos frame) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            // Serializar la lista completa directamente
            oos.writeObject(serializarLista(listaTrazos));
            
            // Limpiar la lista y el panel después de guardar
            listaTrazos.limpiar();
            if (frame != null) {
                frame.repaint(); // Forzar el redibujado del panel vacío
            }
            
            // Confirmación de guardado exitoso
            JOptionPane.showMessageDialog(frame, 
                "Dibujo guardado exitosamente en: " + archivo.getAbsolutePath(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, 
                "Error al guardar el archivo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static List<Nodo> serializarLista(ListaLigada lista) {
        List<Nodo> nodos = new ArrayList<>();
        Nodo actual = lista.getCabeza();
        while (actual != null) {
            nodos.add(actual);
            actual = actual.getSiguiente();
        }
        return nodos;
    }

    public static void cargarTrazos(ListaLigada listaTrazos, File archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            @SuppressWarnings("unchecked")
            List<Nodo> trazos = (List<Nodo>) ois.readObject();
            
            listaTrazos.limpiar();
            
            // Reconstruir la lista ligada manteniendo las referencias
            Nodo anterior = null;
            for (Nodo trazo : trazos) {
                trazo.setSiguiente(null); // Limpiar referencia temporal
                if (anterior == null) {
                    listaTrazos.agregar(trazo);
                } else {
                    anterior.setSiguiente(trazo);
                }
                anterior = trazo;
            }
            
            JOptionPane.showMessageDialog(null, 
                "Dibujo cargado exitosamente desde: " + archivo.getAbsolutePath(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar el archivo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}