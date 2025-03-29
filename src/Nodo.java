class Nodo {
    private int x1, y1, x2, y2, tipo;
    private Nodo siguiente;

    public Nodo(int x1, int y1, int x2, int y2, int tipo) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.tipo = tipo;
        this.siguiente = null;
    }

    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    public int getTipo() { return tipo; }
    public Nodo getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }

    // Método para verificar si un punto (x, y) está dentro del trazo
    public boolean contienePunto(int x, int y) {
        int margen = 5; // Margen de tolerancia para la selección

        switch (tipo) {
            case 0: // Línea
                return puntoCercaDeLinea(x1, y1, x2, y2, x, y, margen);
            case 1: // Rectángulo
                return (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) &&
                        y >= Math.min(y1, y2) && y <= Math.max(y1, y2));
            case 2: // Óvalo
                int a = Math.abs(x2 - x1) / 2;
                int b = Math.abs(y2 - y1) / 2;
                int centerX = Math.min(x1, x2) + a;
                int centerY = Math.min(y1, y2) + b;
                return (Math.pow(x - centerX, 2) / Math.pow(a, 2) + Math.pow(y - centerY, 2) / Math.pow(b, 2)) <= 1;
        }
        return false;
    }

    
    private boolean puntoCercaDeLinea(int x1, int y1, int x2, int y2, int x, int y, int margen) {
        double distancia = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) /
                           Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
        return distancia <= margen;
    }
}
