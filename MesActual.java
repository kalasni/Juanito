//: MesActual.java

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *   Esta clase se encarga de mostrar los componentes necesarios para
 *   la visualizaci�n del mes actual del a�o en curso.
 *   Imprime corchetes alrededor del dia correspondiente en el que se
 *   ejecuta el programa.
 *
 *   @author ocm128
 *   @version 1.0
 *   @since 21-06-2005
 *
 */
class MesActual extends JPanel {

    // Etiquetas
    private JLabel label;
    private JLabel imagen;

    // JButtons
    private JButton jbaceptar;
    private JButton jborra;

    private JTextArea jtarea;

    // paneles
    private JPanel jpsalida;
    private JPanel jpaceptar;
    private JPanel jpimagen;

    Calendar ca = Calendar.getInstance(); // Instancia de Calendar
    private CalendarPage calenpage;

    /* Constructor */
    public MesActual() {

        setLayout(new BorderLayout());
        calenpage = new CalendarPage();

        // Inicializar campos
        jtarea = new JTextArea();
        label = new JLabel("Mostrar mes actual");
        imagen = new JLabel("", new ImageIcon("imagenes/calendarioazteca.jpg"),
                JLabel.CENTER);
        jbaceptar = new JButton("OK");
        jborra = new JButton("BORRAR");

        paneSalida();
        paneAceptar();
        paneImagen();

        add(jpsalida, BorderLayout.CENTER);
        add(jpimagen, BorderLayout.EAST);
        add(jpaceptar, BorderLayout.SOUTH);

    }

    /* Panel con el JTextArea para la salida de los datos */
    private void paneSalida() {

        jpsalida = new JPanel();
        jpsalida.setBackground(Color.white);
        jtarea.setFont(new Font("Courier", Font.PLAIN, 16));
        jtarea.setMinimumSize(new Dimension(370, 330));
        jtarea.setPreferredSize(new Dimension(370, 330));
        jtarea.setMaximumSize(new Dimension(370, 330));
        jtarea.setAlignmentX(CENTER_ALIGNMENT);

        jpsalida.setLayout(new BoxLayout(jpsalida, BoxLayout.Y_AXIS));
        jpsalida.add(jtarea);
        jpsalida.setBorder(BorderFactory.createEmptyBorder(50, 10, 0, 10));
        jtarea.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    /* Panel con los botones */
    private void paneAceptar() {

        jpaceptar = new JPanel();
        jbaceptar.setPreferredSize(new Dimension(90, 25));

        jpaceptar.setLayout(new BoxLayout(jpaceptar, BoxLayout.X_AXIS));
        jpaceptar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jpaceptar.add(Box.createHorizontalGlue());
        jpaceptar.add(label);
        jpaceptar.add(Box.createRigidArea(new Dimension(20, 140)));
        jpaceptar.add(jbaceptar);
        jpaceptar.add(Box.createRigidArea(new Dimension(20, 140)));
        jpaceptar.add(jborra);
        jpaceptar.add(Box.createHorizontalGlue());

        jbaceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calenpage.print_forma_corta(ca.get(Calendar.MONTH) + 1,
                        ca.get(Calendar.YEAR));
                jtarea.setText(calenpage.devuelveCadena().toString());
            }
        });

        jborra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Borra el JTextArea y el StringBuffer
                jtarea.setText(" ");
                calenpage.borraBuffer();
            }
        });

    }

    /* Panel con la imagen del calendario */
    private void paneImagen() {

        jpimagen = new JPanel();
        jpimagen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        jpimagen.setBackground(Color.white);
        jpimagen.setLayout(new BorderLayout());
        jpimagen.add(imagen, BorderLayout.CENTER);

    }
} // Fin class MesActual
