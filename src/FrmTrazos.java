import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrmTrazos extends JFrame {
    private String[] tipoTrazo = {"Linea", "Rectangulo", "Ovalo"};
    private JComboBox<String> cmbTipoTrazo;
    private JTextField txtInfo;
    private JPanel pnlDibujo;
    private ListaLigada listaTrazos;
    private int xInicio, yInicio, xActual, yActual;
    private boolean trazando = false;
    private Nodo trazoSeleccionado = null;
    private boolean modoSeleccion = false;  // Variable para controlar el modo selección

    public FrmTrazos() {
        setSize(500, 400);
        setTitle("Editor de gráficas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        listaTrazos = new ListaLigada();

        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/app_trazos.png")).getImage());

        JToolBar tbTrazos = new JToolBar();
        cmbTipoTrazo = new JComboBox<>(tipoTrazo);
        tbTrazos.add(cmbTipoTrazo);
        txtInfo = new JTextField(15);
        tbTrazos.add(txtInfo);

        JButton btnGuardar = new JButton("Guardar", new ImageIcon(getClass().getResource("/IMAGENES/guardar.PNG")));
        JButton btnCargar = new JButton("Cargar", new ImageIcon(getClass().getResource("/IMAGENES/cargar.PNG")));
        JButton btnEliminar = new JButton("Eliminar", new ImageIcon(getClass().getResource("/IMAGENES/eliminar.PNG")));
        JButton btnSeleccionar = new JButton("Seleccionar", new ImageIcon(getClass().getResource("/IMAGENES/trazar.PNG"))); 

        btnGuardar.setBackground(new Color(100, 126, 148));
        btnCargar.setBackground(new Color(204, 255, 153));
        btnEliminar.setBackground(new Color(196, 188, 250));
        btnSeleccionar.setBackground(new Color(255, 175, 96));

        tbTrazos.add(btnGuardar);
        tbTrazos.add(btnCargar);
        tbTrazos.add(btnEliminar);
        tbTrazos.add(btnSeleccionar);

        pnlDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                redibujarTrazos(g);
                if (trazando) {
                    g.setColor(Color.RED);
                    dibujarVistaPrevia(g);
                }
            }
        };
        pnlDibujo.setBackground(Color.BLACK);

        getContentPane().add(tbTrazos, BorderLayout.NORTH);
        getContentPane().add(pnlDibujo, BorderLayout.CENTER);

        pnlDibujo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                xInicio = me.getX();
                yInicio = me.getY();
                trazando = true;
            }
            
            public void mouseReleased(MouseEvent me) {
                trazando = false;
                xActual = me.getX();
                yActual = me.getY();
                agregarTrazo();
                repaint();
            }
        });

        pnlDibujo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                xActual = me.getX();
                yActual = me.getY();
                repaint();
            }
        });
        
        pnlDibujo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                seleccionarTrazo(me.getX(), me.getY());
                repaint();
            }
        });
        
        btnEliminar.addActionListener(e -> {
            if (trazoSeleccionado != null) {
                listaTrazos.eliminar(trazoSeleccionado);
                trazoSeleccionado = null;
                repaint();
            }
        });

        
        btnSeleccionar.addActionListener(e -> {
            modoSeleccion = !modoSeleccion;  // Alterna el estado del modo selección
            btnSeleccionar.setBackground(modoSeleccion ? Color.RED : new Color(255, 175, 96));  // Cambia el color del botón
        });
    }

    private void agregarTrazo() {
        int tipo = cmbTipoTrazo.getSelectedIndex();
        listaTrazos.agregar(new Nodo(xInicio, yInicio, xActual, yActual, tipo));
    }

    private void seleccionarTrazo(int x, int y) {
        if (!modoSeleccion) {
            return; // Si no está activado el modo selección, no hace nada
        }
        Nodo actual = listaTrazos.getCabeza();
        while (actual != null) {
            if (actual.contienePunto(x, y)) {
                trazoSeleccionado = actual;
                return;
            }
            actual = actual.getSiguiente();
        }
        trazoSeleccionado = null;
    }

    private void dibujarVistaPrevia(Graphics g) {
        int tipo = cmbTipoTrazo.getSelectedIndex();
        switch (tipo) {
            case 0: g.drawLine(xInicio, yInicio, xActual, yActual); break;
            case 1: g.drawRect(Math.min(xInicio, xActual), Math.min(yInicio, yActual), Math.abs(xInicio - xActual), Math.abs(yInicio - yActual)); break;
            case 2: g.drawOval(Math.min(xInicio, xActual), Math.min(yInicio, yActual), Math.abs(xInicio - xActual), Math.abs(yInicio - yActual)); break;
        }
    }

    private void redibujarTrazos(Graphics g) {
        Nodo actual = listaTrazos.getCabeza();
        while (actual != null) {
            g.setColor(actual == trazoSeleccionado ? Color.RED : Color.WHITE);
            switch (actual.getTipo()) {
                case 0: g.drawLine(actual.getX1(), actual.getY1(), actual.getX2(), actual.getY2()); break;
                case 1: g.drawRect(Math.min(actual.getX1(), actual.getX2()), Math.min(actual.getY1(), actual.getY2()), Math.abs(actual.getX1() - actual.getX2()), Math.abs(actual.getY1() - actual.getY2())); break;
                case 2: g.drawOval(Math.min(actual.getX1(), actual.getX2()), Math.min(actual.getY1(), actual.getY2()), Math.abs(actual.getX1() - actual.getX2()), Math.abs(actual.getY1() - actual.getY2())); break;
            }
            actual = actual.getSiguiente();
        }
    }
}
