import java.util.ArrayList;

public class Tabla {

    private ArrayList<ArrayList<String>> tabla;
    private int numeroDeColumnas;


    public Tabla() {
        tabla = new ArrayList<>();
        tabla.add(new ArrayList<>());
        numeroDeColumnas = 0;
    }


    public void anyadirColumna(String nombreColumna) {
        for (ArrayList<String> fila : tabla) {
            fila.add("");
        }

        tabla.get(0).remove(numeroDeColumnas);
        tabla.get(0).add(nombreColumna);

        numeroDeColumnas++;
    }

    public void anyadirEntradaAColumna(String nombreColumna, String entrada) {
        int columnaSeleccionada = -1;

        int i = 0;
        for (String nombreColumnaI : tabla.get(0)) {
            if (nombreColumnaI.equals(nombreColumna)) {
                columnaSeleccionada = i;
                break;
            }

            i++;
        }

        if (columnaSeleccionada == -1) {
            return;
        }

        boolean columnaLlena = true;

        for (i = 0; i < tabla.size(); i++) {
            ArrayList<String> fila = tabla.get(i);

            for (int j = 0; j < numeroDeColumnas; j++) {
                String entradaIJ = fila.get(j);

                if (j == columnaSeleccionada && entradaIJ.equals("")) {
                    fila.remove(j);
                    fila.add(j, entrada);

                    columnaLlena = false;
                } else if (j >= columnaSeleccionada) {
                    break;
                }
            }

            if (!columnaLlena) {
                break;
            }
        }

        if (columnaLlena) {
            ArrayList<String> fila = new ArrayList<>();

            for (int j = 0; j < numeroDeColumnas; j++) {
                if (j == columnaSeleccionada) {
                    fila.add(entrada);
                } else {
                    fila.add("");
                }
            }

            tabla.add(fila);
        }
    }

    @Override
    public String toString() { // Caracteres auxiliares ┌ ┐ └ ┘ ├ ┤ ┬ ┴ ┼ ─ │
        int numeroDeFilas = tabla.size();

        int caracteresDeAnchoDeLasColumnasColumnas[] = new int[numeroDeColumnas];
        int caracteresDeAnchoDeLaTabla = 0;

        int caracteresDeAltoDeLasFilas[] = new int[numeroDeFilas];
        int caracteresDeAltoDeLaTabla = 0;

        String[] filasTablaString;
        String tablaString = "";


        for (int columna = 0; columna < numeroDeColumnas; columna++) {
            caracteresDeAnchoDeLasColumnasColumnas[columna] = tabla.get(0).get(columna).length(); // Cada entrada tiene el ancho de la entrada más dos espacios en blanco a cada lado

            for (ArrayList<String> fila : tabla) {
                String[] filasDeLaEntrada = fila.get(columna).split("\n");

                for (String filaDeLaEntrada : filasDeLaEntrada) {
                    if (filaDeLaEntrada.length() > caracteresDeAnchoDeLasColumnasColumnas[columna]) {
                        caracteresDeAnchoDeLasColumnasColumnas[columna] = filaDeLaEntrada.length();
                    }
                }
            }
        }

        for (int anchoColumna : caracteresDeAnchoDeLasColumnasColumnas) {
            caracteresDeAnchoDeLaTabla += anchoColumna;
        }


        for (int fila = 0; fila < numeroDeFilas; fila++) {
            caracteresDeAltoDeLasFilas[fila] = tabla.get(fila).get(0).split("\n").length;

            for (String entrada : tabla.get(fila)) {
                if (entrada.split("\n").length > caracteresDeAltoDeLasFilas[fila]) {
                    caracteresDeAltoDeLasFilas[fila] = entrada.split("\n").length;
                }
            }
        }

        for (int altoFila : caracteresDeAltoDeLasFilas) {
            caracteresDeAltoDeLaTabla += altoFila;
        }


        filasTablaString = new String[caracteresDeAltoDeLaTabla + numeroDeFilas + 1]; // Cada entrada tiene caracteresDeAlto[fila] de alto + 1 de alto en la barra de abajo. Además, hay que sumar la barra de arriba.

        filasTablaString[0] = calcularBorde(1, caracteresDeAnchoDeLasColumnasColumnas);

        int desplazamiento = 1;
        for (int fila = 0; fila < numeroDeFilas; fila++) {
            String[][] entradas = calcularEntradas(fila, caracteresDeAltoDeLasFilas[fila], caracteresDeAnchoDeLasColumnasColumnas);
            for (int lineaDeEntrada = 0; lineaDeEntrada < caracteresDeAltoDeLasFilas[fila]; lineaDeEntrada++) {
                filasTablaString[desplazamiento + lineaDeEntrada] = calcularFila(entradas[lineaDeEntrada], caracteresDeAnchoDeLasColumnasColumnas);
            }

            if (fila == numeroDeFilas - 1) {
                filasTablaString[desplazamiento + caracteresDeAltoDeLasFilas[fila]] = calcularBorde(-1, caracteresDeAnchoDeLasColumnasColumnas);
            } else {
                filasTablaString[desplazamiento + caracteresDeAltoDeLasFilas[fila]] = calcularBorde(0, caracteresDeAnchoDeLasColumnasColumnas);
            }

            desplazamiento += caracteresDeAltoDeLasFilas[fila] + 1;
        }

        for (String fila : filasTablaString) {
            tablaString += fila + '\n';
        }

        return tablaString;
    }

    private String[][] calcularEntradas(int fila, int altoFila, int[] caracteresDeAnchoColumnas) {// si se quiere las entradas vacías poner fila negativa
        String[][] entradas = new String[altoFila][numeroDeColumnas];
        ArrayList<String> filasDeEntradas = tabla.get(fila);

        for (int columna = 0; columna < numeroDeColumnas; columna++) {
            String filasDeEntrada[] = filasDeEntradas.get(columna).split("\n");
            int diferenciaAlto = altoFila - filasDeEntrada.length + 1;

            for (int filaDeFila = 0; filaDeFila < altoFila; filaDeFila++) {
                String filaDeEntrada;

                if (filaDeFila < diferenciaAlto / 2) {
                    filaDeEntrada = new String();
                } else if (filaDeFila < diferenciaAlto / 2 + filasDeEntrada.length) {
                    filaDeEntrada = filasDeEntrada[filaDeFila - diferenciaAlto / 2];
                } else {
                    filaDeEntrada = new String();
                }

                entradas[filaDeFila][columna] = calcularAnchoFilaEntrada(filaDeEntrada, caracteresDeAnchoColumnas[columna]);
            }
        }

        return entradas;
    }

    private String calcularAnchoFilaEntrada(String filaEntrada, int anchoColumna) {
        String filaEntradaAncha = filaEntrada;

        int diferenciaAncho = anchoColumna - filaEntrada.length();

        for (int caracterColumna = 0; caracterColumna < anchoColumna; caracterColumna++) {
            if (caracterColumna < diferenciaAncho / 2) {
                filaEntradaAncha = ' ' + filaEntradaAncha;
            } else if (caracterColumna >= diferenciaAncho / 2 + filaEntrada.length()) {
                filaEntradaAncha += ' ';
            }
        }

        return filaEntradaAncha;
    }

    private String[] calcularEntradasVacias(int[] caracteresDeAnchoColumnas) {
        String[] entradas = new String[numeroDeColumnas];

        for (int columna = 0; columna < numeroDeColumnas; columna++) {
            entradas[columna] = new String();

            for (int caracterColumna = 0; caracterColumna < caracteresDeAnchoColumnas[columna]; caracterColumna++) {
                entradas[columna] += ' ';
            }
        }

        return entradas;
    }

    private String calcularFila(String[] entradas, int[] caracteresDeAnchoColumnas) {
        String fila = new String();

        fila += "│  ";

        for (int columna = 0; columna < numeroDeColumnas; columna++) {
            for (int caracterColumna = 0; caracterColumna < caracteresDeAnchoColumnas[columna]; caracterColumna++) {
                fila += entradas[columna].charAt(caracterColumna);
            }

            if (columna == numeroDeColumnas - 1) {
                fila += "  │";
            } else {
                fila += "  │  ";
            }
        }

        return fila;
    }

    private String calcularBorde(int posicion, int[] caracteresDeAnchoColumnas) { // 1 principio, 0 intermedio, -1 final
        String borde = new String();

        if (posicion == 1) {
            borde += "┌──";

            for (int columna = 0; columna < numeroDeColumnas; columna++) {
                for (int caracterColumna = 0; caracterColumna < caracteresDeAnchoColumnas[columna]; caracterColumna++) {
                    borde += '─';
                }

                if (columna == numeroDeColumnas - 1) {
                    borde += "──┐";
                } else {
                    borde += "──┬──";
                }
            }
        } else if (posicion == 0) {
            borde += "├──";

            for (int columna = 0; columna < numeroDeColumnas; columna++) {
                for (int caracterColumna = 0; caracterColumna < caracteresDeAnchoColumnas[columna]; caracterColumna++) {
                    borde += '─';
                }

                if (columna == numeroDeColumnas - 1) {
                    borde += "──┤";
                } else {
                    borde += "──┼──";
                }
            }
        } else if (posicion == -1) {
            borde += "└──";

            for (int columna = 0; columna < numeroDeColumnas; columna++) {
                for (int caracterColumna = 0; caracterColumna < caracteresDeAnchoColumnas[columna]; caracterColumna++) {
                    borde += '─';
                }

                if (columna == numeroDeColumnas - 1) {
                    borde += "──┘";
                } else {
                    borde += "──┴──";
                }
            }
        }

        return borde;
    }
}