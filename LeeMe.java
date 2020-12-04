//: LeeMe.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *  Esta clase se limita a mostrar una area de texto donde
 *  principalmente se dan unas instrucciones basicas sobre lo que hace
 *  el programa y como utilizarlo.
 *
 * @author ocm128
 * @version 1.0
 */

class LeeMe extends JPanel {

    private JLabel eti;
    private JTextArea jtext;
    private JScrollPane jp;

    // Constructor
    public LeeMe () {

        // Establecemos como setLayout un BorderLayout
        setLayout(new BorderLayout());

        // Color para el fondo
        setBackground(Color.white);

        // Caracteristicas de la JLabel eti
        eti = new JLabel("Instrucciones basicas de funcionamiento",
                JLabel.CENTER);
        eti.setFont(new Font("Times-Roman", Font.BOLD, 17));

        // Caracteristicas del area de texto jtext
        jtext = new JTextArea("Juanito es una aplicaci�n desarrollada en lenguaje Java." +
         "\n\nSu proposito principal es mostrar diversas fechas como" +
         " calendarios de los a�os o meses introducidos, buscar la fecha en la" +
         " que caer� la festividad de San juan del monte de determinado a�o, o lo " +
         " mismo con las fechas de semana santa y fiestas de carnaval." +
         "\n\nSu uso es simple y su cometido tamb�en." +
         "\n\n\nMES ACTUAL" +
         "\nMuestra el mes actual del a�o en curso. Imprime corchetes alrededor del" +
         " dia correspondiente en el que se ejecuta el programa." +
         "\n\nMES DEL A�O" +
         "\nMuestra el mes correspondiente al mes y el a�o solicitados. El a�o " +
         "introducido deber� ser mayor de 13." +
         "\n\nA�O COMPLETO" +
         "\nMuestra todos los meses del a�o solicitado. Su contenido se puede guardar " +
         "en un fichero accediendo en la barra de menu a 'Archivo' -> 'Guardar como'." +
         "\nPara la visualizaci�n correcta del mismo o si se desea imprimir, se debe usar" +
         " un editor con un tipo de fuente de las llamadas monospaced, " +
         " por ejemplo 'Courier'. " +
         "\n\nSAN JUAN DEL MONTE" +
         "\nMuestra el mes y la fecha (lunes de San juan) en la que cae dicha fiesta" +
         " en el a�o solicitado." +
         "\n\nSEMANA SANTA" +
         "\nIdem al anterior exceptuando que la fecha remarcada corresponde a" +
         " viernes santo." +
         "\n\nCARNAVAL" +
         "\nIdem al anterior exceptuando que la fecha remarcada en este caso es" +
         " el sabado correspondiente a la misma."

         );
        jp = new JScrollPane(jtext);
        jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        jtext.setFont(new Font("SansSerif", Font.PLAIN, 14));
        jtext.setLineWrap(true);
        jtext.setWrapStyleWord(true);
        jtext.setEditable(false);
        jtext.setBorder(BorderFactory.createTitledBorder(" Bienvenid@ a Juanito "));

        add(eti, BorderLayout.NORTH);
        add(jp, BorderLayout.CENTER);

    } // fin del constructor

} // Fin class LeeMe
