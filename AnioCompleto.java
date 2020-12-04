//: AnioCompleto.java

import javax.swing.*;
import javax.swing.text.*;
import java.lang.*;

import java.awt.*;
//import java.util.Hashtable;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *   Esta clase se encarga de mostrar los componentes necesarios para
 *   la visualizaci�n del a�o introducido completo
 *
 *   @author ocm128
 *   @version 1.0
 *   @since 21-07-2005
 *
 */

class AnioCompleto extends JPanel {

    private JLabel lyy;
    private JTextField yy;
    private JTextArea salida;
    private JScrollPane scrollpane;
    private String textoCopiado;
    private JFileChooser jfc;

    // Botones
    private JButton jbentrar;
    private JButton jborrar;

    // Paneles
    private JPanel psalida;
    private JPanel pcampos;

    private final Toolkit kit;
    private Clipboard clipboard;
    private CalendarPage calenpage;

    /* Constructor */
    public AnioCompleto() {

        setLayout(new BorderLayout());
        calenpage = new CalendarPage();

        // Inicializar campos
        salida = new JTextArea();

        scrollpane = new JScrollPane();
        jfc = new JFileChooser();

        kit = Toolkit.getDefaultToolkit();
        clipboard = kit.getSystemClipboard();

        lyy = new JLabel("Introduce el a�o");
        yy = new JTextField(5);
        jbentrar = new JButton("ENTRAR");
        jborrar = new JButton("BORRAR");

        // Paneles
        panelSalida();
        panelEntrada();

        add(psalida, BorderLayout.NORTH);
        add(pcampos, BorderLayout.SOUTH);

    }

    /* Panel con el JTextPane para mostrar la salida */
    private void panelSalida() {

        psalida = new JPanel();

        salida.setFont(new Font("Courier", Font.PLAIN, 16));
        scrollpane = new JScrollPane(salida);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setMinimumSize(new Dimension(290, 560));
        scrollpane.setPreferredSize(new Dimension(290, 560));
        scrollpane.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

        scrollpane.setAlignmentX(CENTER_ALIGNMENT);
        psalida.setLayout(new BoxLayout(psalida, BoxLayout.Y_AXIS));
        psalida.add(scrollpane);
        psalida.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

    }

    /* Panel para introducir el mes y el a�o y mostrar los botones de
     * entrada y borrado de la salida.
     */
    private void panelEntrada() {

        pcampos = new JPanel();

        yy.setMinimumSize(new Dimension(50, 25));
        yy.setPreferredSize(new Dimension(50, 25));
        yy.setMaximumSize(new Dimension(90, 25));

        jbentrar.setPreferredSize(new Dimension(90, 25));
        jborrar.setPreferredSize(new Dimension(90, 25));

        pcampos.setLayout(new BoxLayout(pcampos, BoxLayout.X_AXIS));
        pcampos.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));
        pcampos.add(Box.createHorizontalGlue());
        pcampos.add(lyy);
        pcampos.add(Box.createRigidArea(new Dimension(20, 10)));
        pcampos.add(yy);
        pcampos.add(Box.createRigidArea(new Dimension(20, 10)));
        pcampos.add(jbentrar);
        pcampos.add(Box.createRigidArea(new Dimension(20, 10)));
        pcampos.add(jborrar);
        pcampos.add(Box.createHorizontalGlue());

        jbentrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Integer.parseInt(yy.getText()) < 13) {
                        JOptionPane.showMessageDialog(null, "Introduce a�o mayor que 13",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                         calenpage.print_anio(Integer.parseInt(yy.getText()));
                         salida.setText(calenpage.devuelveCadena().toString());
                         seleccionaTodo();
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "     �Introduce a�o!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jborrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calenpage.borraBuffer();
                salida.setText(" ");
            }
        });
    }

    /**
     * Metodo que copia el contenido del JtextArea "salida",  en el
     * portapapeles del sistema.
     *
     *@return void
     */
    public void seleccionaTodo() {

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String str = this.salida.getText();
        StringSelection dat = new StringSelection(str);
        clipboard.setContents(dat, dat);
    }

    /**
     * Metodo encargado de mostrar un JFileChooser para elegir el nombre
     * y la ruta del fichero, en el que vamos a guardar la salida formateada
     * del JtextArea "salida".
     * Para que la salida quede formateada de la misma manera que en el
     * Jtextarea, ser� necesario visualizarlo con un editor en el que la fuente
     * seleccionada sea alguna de las llamadas "monospaced" (que cualquier
     * caracter ocupa lo mismo).
     *
     * @return void
     */
    public void escribirFichero() {

         int eleccion = jfc.showSaveDialog(AnioCompleto.this);
         if(eleccion == JFileChooser.APPROVE_OPTION) {
              String nombreFichero = jfc.getSelectedFile().getAbsolutePath();

                    /* Transferable clipDatos
=clipboard.getContents(clipboard); */
								String instru = "\nVisualizarlo con un editor en el que "
											+	"la fuente seleccionada sea alguna \n" +
														"de las llamadas monospaced (que cualquier" +
																" caracter ocupa lo mismo)." + "\n\n\n";
								Transferable clipDatos =clipboard.getContents(clipboard);
                if(clipDatos != null) {
                    try {
                        if(clipDatos.isDataFlavorSupported(
                                DataFlavor.stringFlavor)) {
                            textoCopiado =(String)(instru +
															clipDatos.getTransferData(
                                  DataFlavor.stringFlavor));
                        } else {
                            kit.beep();
                        }

                    } catch (Exception ex) {
                        System.err.println("Error cogiendo los datos" + ex);
                    }
                }
              try {
                  BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero));
                  bw.write(textoCopiado);
                  bw.close();
              } catch(IOException io) {
                  JOptionPane.showMessageDialog(null, "Error de escritura en archivo",
                          "Error", JOptionPane.ERROR_MESSAGE);
                 io.printStackTrace();
              }
          }
    }  // Fin escribirFichero()

} // Fin class AnioCompleto