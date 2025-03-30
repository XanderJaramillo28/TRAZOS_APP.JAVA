class ListaLigada {
    private Nodo cabeza;

    public ListaLigada() {
        this.cabeza = null;
    }

    public void agregar(Nodo nuevo) {
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
    }

    public boolean eliminar(Nodo trazo) {
        if (cabeza == null) return false;
        if (cabeza == trazo) {
            cabeza = cabeza.getSiguiente();
            return true;
        }
        Nodo actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente() == trazo) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void limpiar() {
        this.cabeza = null;
    }
}