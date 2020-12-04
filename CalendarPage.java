//: CalendarPage.java

import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.*;
import java.text.*;

/**
 * Esta es la clase donde van los metodos principales como el caso de imprimir
 * el a�o completo, el mes del a�o indicado o la fecha actual.
 * Los metodos principales de este clase est�n sacados del programa cal2
 * para uso en consola realizado por Luis Colorado en lenguaje C.
 * Basicamente la logica es la misma, si bien he tenido que modificar algunas
 * partes para transcribirlo eficientemente a Java.
 *
 * El calendario tiene en cuenta la correci�n realizada en 1752 para cambiar
 * del calendario juliano (bisiestos cada cuatro a�os) al calendario gregoriano
 * (bisiestos multiplos de 4 menos m�ltiplos de 100 menos m�ltiplos de 400)
 * de eliminar los dias 3 al 13 de septiembre de 1752.
 *
 * No se podr� representar un a�o anterior al 13.
 *
 * @since 16-06-05
 * @author ocm128
 * @version 1.0
 */
public class CalendarPage {

    private StringBuffer temp;
    private String cadena;
    private String diaDomResu;
    private String mes_ss;
     int mes_sj;
     int mes_car;
    private Easter ea;

    /* Meses */
    String [] meses = {
          "Ene", "Feb", "Mar",
          "Abr", "May", "Jun",
          "Jul", "Ago", "Sep",
          "Oct", "Nov", "Dic"
    };

    /* Constructor */
    public CalendarPage() {
        ea = new Easter();
        temp = new StringBuffer();
        cadena = new String();
    }

    /**
     * Imprime los doce meses del a�o introducido como parametro.
     * @param int anio
     * @return void
     */
    public void print_anio(int anio) {

        int i, j,  l;
        int [] dia = new int[3];
        int [] desfase = new int[3];
        int [] mes = new int[3];
        temp.append("\n\n\n");
        if(anio < 100) {
		    temp.append("                               " + "00" + anio).append("\n");
		}
		if(anio > 100 && anio < 1000) {
		    temp.append("                               " + "0" + anio).append("\n");
		}
		if(anio > 1000) {
		    temp.append("                               " + anio).append("\n"); // 31 espacios
		}
        temp.append("                               ====").append("\n");
        temp.append("---------------------------------------------" +
                "----------------------"); // 70 caracteres
        temp.append("\n");
        for( i = 0; i < 3; i++) {
            mes[i] = i;
            dia[i] = 1;
            desfase[i] = primero_de_mes(mes[i] + 1, anio);
        }
        for(i = 0; i < 4; i++) {
            // Imprimimos las cabeceras
            temp.append("\n");
            temp.append("        "); // 9 espacios
            temp.append(meses[mes[0]]);
            temp.append("                     "); // 23 espacios
            temp.append(meses[mes[1]]);
            temp.append("                   "); // 21 espacios
            temp.append(meses[mes[2]]);
            temp.append("\n\n");
            temp.append("Lu Ma Mi Ju Vi Sa Do   " + "Lu Ma Mi Ju Vi Sa Do   " +
                    "Lu Ma Mi Ju Vi Sa Do").append("\n"); // 3 espacios

            for(j = 0; j < 6; j++) { // Fila
                for (int k = 0; k < 3; k++) { // Mes
                    for (l = 0; l < 7; l++) { // Columna
                          if(desfase[k] > 0) {
                              desfase[k]--;
                              temp.append("   "); // 3 espacios (antes 3)
                          }
                          else {
                              if(dia[k] == -1)
                                  temp.append("   "); // 3 espacios
                              else {
                                  if(dia[k] < 10) {
                                      temp.append(" ");
                                      temp.append(dia[k]);
                                      temp.append(" "); // 1 espacios (antes 1)
                                      dia[k] = siguiente(dia[k], mes[k] + 1, anio);
                                  }
                                  else {
                                      temp.append(dia[k]);
                                      temp.append(" ");
                                      dia[k] = siguiente(dia[k], mes[k] + 1, anio);
                                  }
                              }
                          }
                    } // for l
                    if(k != 2)
                        temp.append("  "); // 2 antes 5 espacios
                } // for k
                temp.append("\n");
            }  // for j
            for (j = 0; j< 3; j++) {
                mes [j] += 3;
                dia [j] = 1;
                desfase [j] = primero_de_mes (mes [j] + 1 , anio);
            }
        } // Fin del primer for
		 temp.append("\n");
         temp.append("------------------------------------------------" +
		 "-------------------"); // 45 caracteres
         cadena = temp.toString();
    }


    /**
     * Imprime el mes completo del a�o y mes en curso.
     * Utilizado en la clase MesActual.java.
     * @param int mes
     * @param int anio
     * @return void
     */
    void print_forma_corta (int mes, int anio) {

	    Calendar c = Calendar.getInstance();  // Fecha de hoy.
		int desfase, dia= 1;
		temp.append("\n\n");
		temp.append("              ");  // 14 espacios
		temp.append(meses[mes -1]).append("  ").append(anio);
		temp.append("\n");
		temp.append("              ---------").append("\n\n"); //  9
		temp.append("     Lu  Ma  Mi  Ju  Vi  Sa  Do").append("\n"); // 3 espa
		temp.append("     ==========================").append("\n");
		desfase = primero_de_mes(mes, anio);
		// A�adimos desplazamiento a la dcha de la primera linea
		temp.append("    "); // 3 espacios
		for(int i = 0; ; i++) {
		    int resaltado;
		    if(desfase > 0) {
		        temp.append("    "); // 4 espacios
		        desfase--;
		    }
		    else  {
		        resaltado = c.get(Calendar.DATE);
		        // Imprime corchetes alrededor del dia actual
		        if(dia == resaltado) {
		            if(dia < 10) {
		                temp.append(" [").append(dia).append("]");
		                dia = siguiente(dia, mes , anio);
		            }
		            else {
		                temp.append("[").append(dia).append("]");
		                dia = siguiente(dia, mes , anio);
		           }
		       }
		       else {
		            if(dia < 10) {
                        temp.append("  ").append(dia).append(" "); // 2 y 1 espacios
		                dia = siguiente(dia, mes , anio);
		            }
		            else  {
		                temp.append(" ").append(dia).append(" "); // 1 y 1 espacios
		                dia = siguiente(dia, mes, anio);
		            }
		       }
		    }
		    if(dia == -1) break;
		    if(i % 7 == 6)
		        temp.append("\n").append("    "); // 4 espacios al comienzo
		} // Fin del for
		temp.append("\n\n\n\n");
        cadena = temp.toString();
	}

     /**
       * Metodo que imprime el mes del a�o indicado.
       * @param int mes
       * @param int anio
       * @return void
       */
    void printMesDelAnio (int mes, int anio) {

		int desfase, dia= 1;
		temp.append("\n\n");
		temp.append("              ");  // 14 espacios
		if(anio < 100) {
		    temp.append(meses[mes -1]).append("  ").append("00" + anio);
		}
		if(anio > 100 && anio < 1000) {
		    temp.append(meses[mes -1]).append("  ").append("0" + anio);
		}
		if(anio > 1000) {
		    temp.append(meses[mes -1]).append("  ").append(anio);
		}
		temp.append("\n");
		temp.append("              ---------").append("\n\n"); //  9 espacios
		temp.append("     Lu  Ma  Mi  Ju  Vi  Sa  Do").append("\n"); // 3 esp
		temp.append("     ==========================").append("\n");
		desfase = primero_de_mes(mes, anio);
		// A�adimos desplazamiento a la dcha de la primera linea
		temp.append("    "); // 4 espacios
		for(int i = 0; ; i++) {
		    if(desfase > 0) {
		        temp.append("    "); // 4 espacios
		        desfase--;
		    }
		    else {
		              if(dia < 10) {
                         temp.append("  ").append(dia).append(" "); // 2 y 1 espacios
		                 dia = siguiente(dia, mes , anio);
		              }
		              else  {
		                 temp.append(" ").append(dia).append(" "); // 1 y 1 espacios
		                 dia = siguiente(dia, mes, anio);
		              }
		    }
		    if(dia == -1) break;
		    if(i % 7 == 6)
		        temp.append("\n").append("    "); // 4 espacios
		} // Fin del for
		temp.append("\n\n\n\n");
        cadena = temp.toString();
	}

        /**
         * Metodo que calcula el dia de la semana del 1 de enero del a�o que
         * se le pasa como parametro.
         * @param int
         * @return int
         */
        private int primero_de_enero (int anio) {

             if(anio <= 1752) {
                 int grupo_4; // Grupo de 4 a�os
                 anio--;
                 grupo_4 = anio / 4;
                 anio %= 4;
                 return (grupo_4 * 5 + anio + 5);
             }
             else {
                 int grupo_100, grupo_4;
                 anio--;
                 anio %= 400;
                 grupo_100 = anio / 100;
                 anio %= 100;
                 grupo_4 = anio / 4;
                 anio %= 4;
                 return ((grupo_100 + grupo_4) * 5 + anio) % 7;
             }
        }

        /**
         * Metodo que calcula el dia de la semana del primero del mes indicado
         * como primer parametro y del a�o pasado como segundo parametro.
         * @param int mes
         * @param int anio
         * @return int
         */
       private int primero_de_mes (int mes, int anio) {

           int [][]  dias_mes = {
                     // Normal bisiesto 1752
                      {0, 0, 0},  // Enero
                      {3, 3, 3},  // Febrero
                      {3, 4, 4},  // Marzo
                      {6, 0, 0},  // Abril
                      {1, 2, 2},  // Mayo
                      {4, 5, 5},  // Junio
                      {6, 0, 0},  // Julio
                      {2, 3, 3},  // Agosto
                      {5, 6, 6},  // Septiembre
                      {0, 1, 4},  // Octubre
                      {3, 4, 0},  // Noviembre
                      {5, 6, 2},  // Diciembre
					  {0, 0, 0},
					  {3, 3, 3},
                      {3, 4, 4},
             };
              mes--;
              if(anio == 1752) {
                  return (primero_de_enero(anio) +  dias_mes[mes][2]) % 7;
			  }
              else if(bisiesto(anio) == 1) {
                  // Si el a�o es bisiesto
                  return (primero_de_enero(anio) +  dias_mes[mes][1]) % 7;
			  }
			  else
              return  (primero_de_enero(anio) + dias_mes[mes][0]) % 7;
        }

        /**
         * Devuelve el valor del dia siguiente al dia especificado, ya que
         * hay excepciones.
         * @param int dia
         * @param int mes
         * @param int anio
         * @return int
         */
        int siguiente (int dia, int mes, int anio) {
            if(anio < 1)
                return -1;
            /* La excepci�n contemplada en el calendario gregor�ano de
             * eliminar los dias 3 al 13 de septiembre de 1752.
             */
            if(anio == 1752 && mes == 9 && dia == 2)
                return 14;

            switch(mes) {
                  case 1: case 3: case 5: case 7:
                  case 8: case 10: case 12:
                      if (dia < 1 || dia >= 31)
                          return -1;
                      return ++dia;
                  case 4: case 6: case 9: case 11:
                      if(dia < 1 || dia >= 30)
                          return -1;
                      return ++dia;
                  case 2:
                      if(bisiesto(anio) == 1) {
                          if(dia < 1 || dia >= 29)
                              return -1;
                      }
                      else
                          if(dia < 1 || dia >= 28)
                              return -1;
							  if(dia < 32)
                          return ++dia;
                  default:
                      return -1;
            }  // fin switch
        }

        /**
         * Metodo que determina si el a�o pasado como par�metro es bisiesto
         * o no lo es.
         * Los a�os bisiestos son aquellos multiplos de 4 (ej: 2004), y los multiplos
         * de 100 cuando a la vez son multiplos de 400 (ej: 1800 no ser�a bisiesto, 2000
         * s� lo ser�a).
         * @param int anio
         * @return
         */
        int bisiesto (int anio)
        {
            // 1 ser�a bisiesto, 0 no.
             if (anio < 1752) {
        		if (anio % 4 == 0) return 1;
        		return 0;
        	 } /* if */
             if (anio % 400 == 0) return 1;
             if (anio % 100 == 0) return 0;
             if (anio % 4 == 0) return 1;
        	 return 0;
        }
        /**
         * Devuelve el contenido del String "cadena", String donde
         * se introducen los datos formateados para mostrarlos en
         * los JtextArea.
         * @return String
         */
        public String devuelveCadena() {
            return cadena;
        }

        /**
         * Borra el contenido del StringBuffer "temp"
         * @return void
         */
        public void borraBuffer() {
            temp.delete(0, temp.length());
        }

  /******************************************************
   *                                                                                                 *
   *    A partir de aqu� van los metodos para calcular y mostrar        *
   *    los dias festivos.                                                                     *
   *																								  *
   * *****************************************************/

        /**
         * Obtiene la fecha del domingo de resurreci�n del array y separa
         * la cadena en el mes y el dia introduciendo cada una de ellas en
         * un String diferente.
         * @param int  annio
         */
        public void semana_santa(int annio) {

            ea.procesar(annio);
            diaDomResu = String.valueOf(ea.diaPascuaInt());
            mes_ss = String.valueOf(ea.mesPascuaInt());

        }

        /**
         * Obtiene y devuelve la fecha de Viernes santo a partir de la fecha del
         * domingo de resurreci�n de ese a�o concreto.
         * Viernes santo ser�a dos dias atr�s.
         * @return int
         */
        public int getDiaSS() {
            int vierneSanto = Integer.parseInt(diaDomResu) -2;
            return vierneSanto;
        }

        /**
         * Obtiene y devuelve la fecha del mes de semana santa de ese a�o
         * concreto introducido en el metodo semana_santa(int annio)
         * @return int
         */
        public int getMesSS() {
            int mesSS = Integer.parseInt(mes_ss);
            return mesSS;
        }

        /**
         * Obtiene y devuelve la fecha del domingo de resurreci�n obtenida en
         * el metodo semana_santa(int annio). Utilizada para calcular otros
         * festivos.
         * @return int
         */
        public int getDomResu() {
            return Integer.parseInt(diaDomResu);
        }

        /**
         * Seg�n el mes en el que cae Semana Santa un a�o concreto, aumenta
         * el mes de San Juan para llevar la cuenta de los dias.
         * @param int cont
         * @return void
         */
        public void mesFes(int cont) {
            mes_sj = cont;
        }

        /**
         * Obtiene el mes de San Juan fijado en mesSJ(int cont).
         * @return int
         */
        public int getMesFes() {
            return mes_sj;
        }

        /**
         * Calcula el dia en el que cae San juan de ese a�o concreto a partir
         * de los datos obtenidos en el metodo semana_santa(anio).
         * @param int anioSJ
         * @return int
         */
        public int diaSJ(int anioSJ) {
            semana_santa(anioSJ);
            int diaFinal = 0;
            int temp = 0;
            if(getMesSS() == 3) {
                mesFes(5);
                diaFinal = ((31 - getDomResu()) + 30);
                diaFinal = 50 - diaFinal;
                temp = diaFinal; 
            }
            if(getMesSS() == 4) {
                diaFinal = (30 - getDomResu());
                diaFinal = 50 - diaFinal; 
                if(diaFinal < 31) {
                    mesFes(5);
                    temp = diaFinal;
                }
                if(diaFinal > 31) {
                    mesFes(6);
                    temp = diaFinal - 31;
                }
            }
            return temp;
        }

       /******************************************
        *                                                                           *
        *     Metodos relativos a la clase Carnaval.java      *
        *                                                                           *
        ******************************************/

        /**
         * Calcula el dia en el que cae Carnaval de ese a�o concreto a partir
         * de los datos obtenidos en el metodo semana_santa(anio).
         * @param int anioCar
         * @return int
         */
        public int diaCar(int anioCar) {
            semana_santa(anioCar);
            int temp = 0;
            int temp2 = 0;
            int diaFinalCar = 0;
            if(getMesSS() == 3) {
                mesFes(2);
                temp = 47 - getDomResu();
                if(bisiesto(anioCar) == 1) {
                    diaFinalCar = 29 - temp;
                }
                else {
                    diaFinalCar = 28 - temp;
                }
            }
            if(getMesSS() == 4 && (getDomResu() > 16)) {
                mesFes(3);
                temp = 47 - getDomResu();
                diaFinalCar = 31 - temp;
            }
            else if (getMesSS() == 4 && (getDomResu() < 17)) {
                mesFes(2);
                temp = 47 - getDomResu();
                if(temp > 31) {
                     temp2 = temp - 31;
                    if(bisiesto(anioCar) == 1) {
                          diaFinalCar = 29 - temp2;
                    }
                    else {
                          diaFinalCar = 28 - temp2;
                    }
               }
               else if (temp == 31) {
                   if(bisiesto(anioCar) == 1) {
                         diaFinalCar = 29;
                   }
                   else {
                         diaFinalCar = 28;
                   }
               }
               else {
                    if(bisiesto(anioCar) == 1) {
                        diaFinalCar = 29 - getDomResu();
                     }
                     else {
                        diaFinalCar = 28 - getDomResu();
                    }
               }
            }
            return diaFinalCar;
        }

        /**
         * Imprime el mes completo del mes y a�o introducidos como parametros.
         * El parametro diaFestivo se utiliza para pasarle el dia festivo que hay
         * que imprimir entre corchetes.
         * @param int mes
         * @param int anio
         * @param int diaFestivo
         * @return void
         */
        void printMesesFestivos (int mes, int anio, int diaFestivo) {

    		int desfase, dia= 1;
    		temp.append("\n\n");
    		temp.append("              ");  // 14 espacios
    		temp.append(meses[mes -1]).append("  ").append(anio);
    		temp.append("\n");
    		temp.append("              ---------").append("\n\n");
    		temp.append("     Lu  Ma  Mi  Ju  Vi  Sa  Do").append("\n");
    		temp.append("     ==========================").append("\n");
    		desfase = primero_de_mes(mes, anio);
    		// A�adimos desplazamiento a la dcha de la primera linea
    		temp.append("    "); // 3 antes
    		for(int i = 0; ; i++) {
    		    if(desfase > 0) {
    		        temp.append("    "); // 4 antes
    		        desfase--;
    		    }
    		    else  {
    		        // Imprime corchetes alrededor del dia actual
    		        if(dia == diaFestivo) {
    		            if(dia < 10) {
    		                temp.append(" [").append(dia).append("]");
    		                dia = siguiente(dia, mes , anio);
    		            }
    		            else {
    		                temp.append("[").append(dia).append("]");
    		                dia = siguiente(dia, mes , anio);
    		           }
    		       }
    		       else {
    		            if(dia < 10) {
                            temp.append("  ").append(dia).append(" "); // 2 y 1 espacios
    		                dia = siguiente(dia, mes , anio);
    		            }
    		            else  {
    		                temp.append(" ").append(dia).append(" "); // 1 y 1 espacios
    		                dia = siguiente(dia, mes, anio);
    		            }
    		       }
    		    }
    		    if(dia == -1) break;
    		    if(i % 7 == 6)
    		        // 4 espacios al comienzo de cada fila
    		        temp.append("\n").append("    ");
    		} // Fin del for
    		temp.append("\n\n\n\n");
            cadena = temp.toString();
    	}

} // Fin class CalendarPage
