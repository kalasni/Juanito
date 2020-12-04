/*
 * Copyright (C) 1999 Antonio Luque Estepa  <aluque@bart.us.es>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */


/*********************************************************************
 * La luna llena que precede al domingo de pascua y que es la primera despues
 * del equinoccio de primavera, es llamada "Luna llena de pascua". Es calculada
 * usando dos herramientas: El numero dorado y la epacta.
 * El numero dorado describe la relaci�n entre el numero del a�o y las sucesiones
 * de las etapas lunares durante ese a�o. La epacta mide la edad de la luna durante
 * un determinado d�a.
 * Conociendo el numero dorado de un determiando a�o se puede calcular la epacta.
 * Se asume que todos los a�os se describen con cuatro digitos (1900, 0099, etc).
 * Este programa calcula el domingo de Pascua seg�n la iglesia cat�lica. Este d�a es
 * diferente del calendario griego o del domingo de pascua ortodoxo.
 *
 * Este programa de acuerdo con la licencia que posee (GNU), ha sido modificado
 * en varios aspectos y le han sido a�adido otros metodos con el f�n de adecuarlo
 * a los requerimientos necesarios.
 *
 * @author ocm128
 * @since 6-9-2005
 *
 *********************************************************************/

import java.lang.Math;

public class Easter {

    private Fecha llena; //Luna llena de Pascua
    private Fecha pascua; //fecha de Pascua
    private Fecha valor; // Para valores individuales
    static int anio;
    boolean procesado; //Estamos listos para dar una respuesta?
    private static final int JULIAN5OCT1582 = 577738;

    //Tabla de lunas llenas pascuales dependiendo de la epacta
    //Seguramente habra una forma mejor de hacer esto, pero por ahora yo no la
    // s�
    private static Fecha[] pascal = {

            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
            new Fecha(0, 0), new Fecha(0, 0), new Fecha(0, 0),
    };

    //Numero de dias en cada mes
    private static int monthday[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
            30, 31 };

    // Numero de dias en cada mes siendo el a�o bisiesto
    private static int leapmonthday[] = { 31, 29, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31 };

    private String dayname[] = { "Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday" };

    private static boolean isleap(int anio) {
        //esto parece algo asi como Java ofuscado...
        if (anio <= 1582) {
            return (anio % 4 == 0) ? true : false;
        } else {
            return ((anio % 4 == 0) ? true : false)
            && (((anio % 100 == 0) ? false : true) || ((anio % 400 == 0) ? true
                            : false));
        }

    } // Fin isLeap()

    //numero de a�os bisiestos julianos que no son bisiestos gregorianos desde
    // 1582
    private static int notleapsince1582(int anio) {
        return ((anio >= 1600) ? anio / 100 - 16 : 0)
                - ((anio >= 2000) ? anio / 400 - 4 : 0);
    }

    //numero de a�os bisiestos desde el a�o 1
    private static int leapsince1(int anio) {
        return anio / 4 - notleapsince1582(anio);
    }

    /*
     * inicializa las variables con la tabla de las lunas llenas de pascua
     * dependiendo de la epacta
     */
    private static int inicializar() {
        pascal[0].ponfecha(0, 0);
        pascal[1].ponfecha(12, 4);
        pascal[2].ponfecha(11, 4);
        pascal[3].ponfecha(10, 4);
        pascal[4].ponfecha(9, 4);
        pascal[5].ponfecha(8, 4);
        pascal[6].ponfecha(7, 4);
        pascal[7].ponfecha(6, 4);
        pascal[8].ponfecha(5, 4);
        pascal[9].ponfecha(4, 4);
        pascal[10].ponfecha(3, 4);
        pascal[11].ponfecha(2, 4);
        pascal[12].ponfecha(1, 4);
        pascal[13].ponfecha(31, 3);
        pascal[14].ponfecha(30, 3);
        pascal[15].ponfecha(29, 3);
        pascal[16].ponfecha(28, 3);
        pascal[17].ponfecha(27, 3);
        pascal[18].ponfecha(26, 3);
        pascal[19].ponfecha(25, 3);
        pascal[20].ponfecha(24, 3);
        pascal[21].ponfecha(23, 3);
        pascal[22].ponfecha(22, 3);
        pascal[23].ponfecha(21, 3);
        pascal[24].ponfecha(18, 4);
        pascal[25].ponfecha(0, 0);
        pascal[26].ponfecha(17, 4);
        pascal[27].ponfecha(16, 4);
        pascal[28].ponfecha(15, 4);
        pascal[29].ponfecha(14, 4);
        pascal[30].ponfecha(13, 4);
        //Si epacta==25, la luna pascual se determina por el numero aureo
        return 1;
    } // Fin inicializar()

    public int procesar(int anio) {

        inicializar();
        int gn; //numero aureo
        int ep; //epacta
        int cent = 0; //siglo
        int julianday;
        int weekday;

        cent = ((anio - (anio % 100)) / 100) + 1;
        gn = (anio % 19) + 1;
        ep = (11 * (gn - 1)) % 30;
        ep = (ep - (3 * cent) / 4);
        ep = ep + (8 * cent + 5) / 25;
        ep += 8;
        while (ep <= 0)
            ep += 30;
        while (ep > 30)
            ep -= 30;

       // System.out.println("A�o " + anio + ". Bisiesto? " + isleap(anio));
       // System.out.println("El numero aureo es " + gn + ". La epacta es " + ep);

        llena = pascal[ep];
        if (ep == 25) {
            if (gn > 11) {
                llena.ponfecha(17, 4);
            }
            if (gn <= 11) {
                llena.ponfecha(18, 4);
            }
        }

        //System.out.println("La luna llena pascual es el ");
        //System.out.println(diaLunaLLenaPascual());

        //Ahora hay que calcular el domingo siguiente a la luna llena pascual
        julianday = jday(llena.dia, llena.mes, anio);
        weekday = dayofweek(llena.dia, llena.mes, anio);
        julianday += 7 - weekday;

        pascua = juliantodate(julianday, anio);

        //System.out.println("La pascua es el ");
        //System.out.println(diaPascuaString());
        return 1;

    } // Fin Procesar()

    //Dia de la semana de una fecha (0=domingo, 1=lunes ... 6=sabado)
    private static int dayofweek(int dia, int mes, int anio) {
        long julianday; //Dia juliano total desde 1 Ene 1
        int weekday = 4; //Jueves

        julianday = (anio - 1) * 365 + leapsince1(anio - 1)
                + jday(dia, mes, anio);

        if (julianday < JULIAN5OCT1582) { //Calendario juliano
            weekday = (int) ((julianday - 1 + 6) % 7);
        }
        if (julianday >= JULIAN5OCT1582 + 9) {
            weekday = (int) ((julianday - 1 + 6 - 10) % 7);
        }
        return weekday;

    } // Fin dayofweek()

    //Dia juliano (numero del dia dentro del a�o)
    private static int jday(int dia, int mes, int ano) {
        int temp = 0, i;

        if (isleap(anio)) {
            for (i = 0; i < mes - 1; ++i)
                temp += leapmonthday[i];
            temp += dia;
        } else {
            for (i = 0; i < mes - 1; ++i)
                temp += monthday[i];
            temp += dia;
        }
        return temp;
    } // Fin jday()


    Fecha juliantodate(int julian, int anio) {
        int i;
       // Fecha valor;

        valor = new Fecha(0, 0);
        if (isleap(anio)) {
            for (i = 0; i < 12; ++i) {
                julian -= leapmonthday[i];
                if (julian <= 0) {
                    valor.mes = i + 1;
                    valor.dia = julian + leapmonthday[i];
                    return valor;
                }
            }
        } else {
            for (i = 0; i < 12; ++i) {
                julian -= monthday[i];
                if (julian <= 0) {
                    valor.mes = i + 1;
                    valor.dia = julian + monthday[i];
                    return valor;
                }
            }
        }
        return null;
    } // Fin juliantodate()

    /**
     * Devuelve como String el dia y el mes de la fecha en que cae la luna
     * llena pascual.
     * @author ocm128
     * @return String
     */
    public String diaLunaLLenaPascual() {
        return String.valueOf(llena.dia) + " " + String.valueOf(llena.mes);
    }

    /**
     *  Devuelve como String el dia de la pascua de resurreci�n.
     * @author ocm128
     * @return String
     */
    public String diaPascuaString() {
        return String.valueOf(pascua);
    }

    /**
     * Devuelve el valor de d�a unicamente.
     * @author ocm128
     * @return int
     */
    public int diaPascuaInt() {
        return valor.dia;
    }

    /**
     * Devuelve el valor de mes unicamente.
     *  @author ocm128
     * @return int
     */
    public int mesPascuaInt() {
        return valor.mes;
    }

    /**
     * Esta es la clase donde se introducen y son devueltos como String,
     * el d�a y el mes como juntos.
     */
    static class Fecha {

        public int dia;
        public int mes;

        /* Constructor */
        public Fecha(int d, int m) {
            dia = d;
            mes = m;
        }

        public void ponfecha(int d, int m) {
            dia = d;
            mes = m;
        }

        public String toString() {
            return String.valueOf(dia) + " " + String.valueOf(mes);
        }
    }

    /**
     *  Dejamos el metodo static para poder utilizarlo si fuese el caso,
     *  desde la linea de comandos.
     */
  /*  public static void main(String Args[]) {
            Easter ea = new Easter();
            if (Args.length == 0) {
                System.out.println("Introduce a�o. easter <a�o>");
            } else {
               anio = Integer.parseInt(Args[0]);
               anio = java.lang.Math.abs(anio);
               ea.procesar(anio);
            }
        } */

} // Fin Easter.java
