//: Frontend.java

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;

import java.awt.Toolkit;
import javax.swing.text.*;

/** Esta es la clase donde van la interfaz grafica basica y de
 *   presentaci�n del programa.
 *   En ella es donde se van a mostrar desde un principio las pesta�as
 *   donde van a ir apareciendo los diferentes marcos.
 *   @author ocm128
 *   @version 1.0
 *   @since 16-06-05
 */

 public class Frontend extends JFrame {

     private JTabbedPane tabbedPane;
     private JFileChooser jfc;
     private JMenuItem itemGuardarComo;
     private JMenuItem itemSalir;
     private JMenuItem itemAyuda;
     private AnioCompleto anioComp;

     /* Constructor */
     public Frontend() {
         super("Juanito v1.0");
         anioComp = new AnioCompleto();
         addComponentes();
         this.addWindowListener(new WindowAdapter()
         {
              public void windowClosing(WindowEvent e) {
                  System.exit(0);
              }
         });

     } // Fin del constructor

     /* Construimos todos los componentes */
     private void addComponentes() {

         tabbedPane = new JTabbedPane(SwingConstants.LEFT);
         tabbedPane.setBackground(Color.blue);
         tabbedPane.setForeground(Color.white);

         // Metodo que a�ade las pesta�as individuales
         addTabbedPane();

         // Metodo barra de menu.
         buildMenu();

         this.getContentPane().add(tabbedPane);
         this.pack();
         this.setSize(865, 690);
         this.setResizable(false);
         this.setBackground(Color.white);
         this.setVisible(true);

     }

     private void addTabbedPane() {

         // Creamos las pesta�as con sus titulos.
         tabbedPane.addTab("Leeme", null,  new LeeMe(),
                 "Uso del programa");

         tabbedPane.addTab("Mes actual", null, new MesActual(),
                 "Muestra la fecha y el mes actual");

         tabbedPane.addTab("Mes del a�o", null, new MesDelAnio(),
                 "Muestra el mes del a�o introducido");

         tabbedPane.addTab("A�o completo", null, new AnioCompleto(),
                 "Muestra el a�o introducido completo");

         tabbedPane.addTab("San Juan del Monte", null, new SanJuan(),
                 "Calcular fechas de San Juan del Monte");

         tabbedPane.addTab("Semana Santa", null , new SemanaSanta(),
                 "Calcular Fechas de Semana Santa");

         tabbedPane.addTab("Carnaval", null, new Carnaval(),
                 "Calcular Fechas de Carnaval");
     }

     /* Construimos el menu */
     private void buildMenu() {

         jfc = new JFileChooser();
         JMenuBar mb = new JMenuBar();
         JMenu menu = new JMenu("Archivo");
         itemGuardarComo = new JMenuItem("Guardar como");
         JMenu menu2 = new JMenu("Salir");
         JMenuItem itemSalir = new JMenuItem("Salir");
         JMenu menu3 = new JMenu("Ayuda");
         JMenuItem itemAyuda = new JMenuItem("Acerca de");

         // Cierra la aplicacion al accionar en exit.
         itemSalir.addActionListener(new ActionListener()
         {
             public void actionPerformed(ActionEvent e) {
                 System.exit(0);
             }

          });

         itemGuardarComo.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 anioComp.escribirFichero();
             }
         });

         itemAyuda.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                   acercaDe();
             }

         });

          menu.add(itemGuardarComo);
          menu2.add(itemSalir);
          menu3.add(itemAyuda);
          mb.add(menu);
          mb.add(menu2);
          mb.add(menu3);
          setJMenuBar(mb);

      }

     /**
      * Metodo que muestra la etiqueta de la barra de menu "Ayuda".
      * @autor ocm128
      */
     public void acercaDe () {
         JOptionPane.showMessageDialog(null, "\nJuanito v1.0\n\n" +
         		"by ocm128\n\n" +
                "Email:  tdoc@tutamail.com\n\n",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE);
     }

     public static void main (String [] args) {

         Frontend front = new Frontend();
     }

 } // Fin clase Frontend


