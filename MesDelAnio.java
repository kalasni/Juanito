//: MesDelAnio.java

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** Esta clase se encarga de mostrar los componentes necesarios para
 *   la visualizaci�n del mes y el a�o introducidos en los JTextField
 *   correspondientes.
 *  @author ocm128
 *  @version 1.0
 *  @since 23-6-2005
 *
 */

class MesDelAnio extends JPanel {

    // Etiquetas
    private JLabel lyear;
    private JLabel lmonth;
    private JLabel imagen;

    // JButtons
    private JButton jbaceptar;
    private JButton jborra;

    private JTextArea jtarea;
    private JTextField tyear;
    private JTextField tmonth;
    Calendar ca = Calendar.getInstance();

    // paneles
    private JPanel jpsalida;
    private JPanel jpaceptar;
    private JPanel jpimagen;

    private CalendarPage calenpage;

    /* Constructor */
    public MesDelAnio() {

        setLayout(new BorderLayout());
        calenpage = new CalendarPage();

        // Inicializar campos
        jtarea = new JTextArea();
        tyear = new JTextField();
        tmonth = new JTextField();
        lyear = new JLabel("Introduce a�o");
        lmonth = new JLabel("Introduce mes");
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

    /* Panel de salida para el JTextField */
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

    /* Panel para introducir el mes y a�o */
    private void paneAceptar() {

        jpaceptar = new JPanel();
        jbaceptar.setPreferredSize(new Dimension(90, 25));

        tyear.setPreferredSize(new Dimension(50, 30));
        tyear.setMaximumSize(new Dimension(50, 30));

        tmonth.setPreferredSize(new Dimension(50, 30));
        tmonth.setMaximumSize(new Dimension(50, 30));

        jpaceptar.setLayout(new BoxLayout(jpaceptar, BoxLayout.X_AXIS));
        jpaceptar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jpaceptar.add(Box.createHorizontalGlue());
        jpaceptar.add(lmonth);
        jpaceptar.add(Box.createRigidArea(new Dimension(10, 140)));
        jpaceptar.add(tmonth);
        jpaceptar.add(Box.createRigidArea(new Dimension(10, 140)));
        jpaceptar.add(lyear);
        jpaceptar.add(Box.createRigidArea(new Dimension(10, 140)));
        jpaceptar.add(tyear);
        jpaceptar.add(Box.createRigidArea(new Dimension(50, 140)));
        jpaceptar.add(jbaceptar);
        jpaceptar.add(Box.createRigidArea(new Dimension(20, 140)));
        jpaceptar.add(jborra);
        jpaceptar.add(Box.createHorizontalGlue());

        jbaceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Integer.parseInt(tmonth.getText()) < 1 ||
                            Integer.parseInt(tmonth.getText()) > 12)
                    {
                        JOptionPane.showMessageDialog(null, "Introduce mes entre 1 y 12",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if( Integer.parseInt(tyear.getText()) < 13) {
                        JOptionPane.showMessageDialog(null, "Introduce a�o mayor que 13",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        calenpage.printMesDelAnio( Integer.parseInt(tmonth.getText()),
                                Integer.parseInt(tyear.getText()));
                         jtarea.setText(calenpage.devuelveCadena().toString());
                    }
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, " �Introduce mes y a�o!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jborra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calenpage.borraBuffer();
                jtarea.setText(" ");
            }
        });

    }

    /* Panel donde va la imagen del calendario */
    private void paneImagen() {

        jpimagen = new JPanel();
        jpimagen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        jpimagen.setBackground(Color.white);
        jpimagen.setLayout(new BorderLayout());
        jpimagen.add(imagen, BorderLayout.CENTER);

    }

} // Fin class MesDelAnio
