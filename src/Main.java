import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // D1 89 57 C3 FA 7A 96 C1 82 02 0F 64 0A D6 42 16 8D 17 49 D2 D1 DF 35 CD 7A C0 29 90 85 AE

    static String[] logicaHexadecimal;
    static String[] logicaBinario;

    static int[][] secuenciaDePaginasYDesplazamientosDireccionesLogicas;
    static int[] secuenciaDePaginasDireccionesLogicas;

    static String[] fisicaBinario;
    static String[] fisicaHexadecimal;

    static int[][] secuenciaDePaginasYDesplazamientosDireccionesFisicas;
    static int[] secuenciaDePaginasDireccionesFisicas;

    static Tabla tabla;

    static ArrayList<Integer> iteracionesEntreFallosDePagina;



    public static void main(String[] args) {

        tabla = new Tabla();

        tabla.anyadirColumna("Lógica (hexadecimal)");
        tabla.anyadirColumna("Lógica (binario)");
        tabla.anyadirColumna("Tabla de páginas");
        tabla.anyadirColumna("Memoria física");
        tabla.anyadirColumna("Física (binario)");
        tabla.anyadirColumna("Física (hexadecimal)");

        tabla.anyadirEntradaAColumna("Lógica (hexadecimal)", "-");
        tabla.anyadirEntradaAColumna("Lógica (binario)", "-" + '\n'
                + '\n'
                + "(Nº página, Desplazamiento)");

        tabla.anyadirEntradaAColumna("Física (binario)", "-" + '\n'
                + '\n'
                + "(Nº página, Desplazamiento)");
        tabla.anyadirEntradaAColumna("Física (hexadecimal)", "-");

        leerDireccionesLogicas();
        //mostrarDireccionesLogicas();

        AlgoritmoDeReemplazo algoritmoDeReemplazo = seleccionarAlgoritmoDeReemplazo();
        while(algoritmoDeReemplazo != null) {
            ejecutarAlgoritmoDeReemplazo(algoritmoDeReemplazo);


            //mostrarDireccionesFisicas();

            System.out.println(tabla);

            System.out.println("Numero de fallos de pagina: " + iteracionesEntreFallosDePagina.size());
            System.out.println("Media de fallos de pagina por iteracion: " + (iteracionesEntreFallosDePagina.size()+.0)/logicaHexadecimal.length);
            System.out.println("Numero de iteraciones ocurridas entre los fallos de pagina: " + iteracionesEntreFallosDePagina);
            System.out.println("Media de iteraciones ocurridas entre los fallos de pagina: " + media(iteracionesEntreFallosDePagina));
            System.out.println();


            algoritmoDeReemplazo = seleccionarAlgoritmoDeReemplazo();
        }
    }



    private static void leerDireccionesLogicas() {
        Scanner lector = new Scanner(System.in);

        // Se pide la entrada en hexadecimal por pantalla
        System.out.println("Introduce la secuencia de paginas en hexadecimal separada por espacios (al finalizar pulas enter):");
        logicaHexadecimal = lector.nextLine().split(" ");

        // Se transforma a binario
        logicaBinario = new String[logicaHexadecimal.length];
        for(int i = 0; i < logicaHexadecimal.length; i++) {
            String logicaBinarioI = Integer.toBinaryString(Integer.parseInt(logicaHexadecimal[i], 16));
            while(logicaBinarioI.length() < 8) { logicaBinarioI = "0" + logicaBinarioI; }

            logicaBinario[i] = logicaBinarioI.substring(0,3) + " " + logicaBinarioI.substring(3, 8);
        }

        calcularPaginasYDesplazamientosDireccionesLogicas();
    }

    private static void mostrarDireccionesLogicas() {
        System.out.println("Direcciones logicas en binario: " + Arrays.toString(logicaBinario));
        System.out.println("Direcciones logicas en hexadecimal: " + Arrays.toString(logicaHexadecimal));
        mostrarPaginasYDesplazamientosDireccionesLogicas();
    }

    private static void calcularPaginasYDesplazamientosDireccionesLogicas() {
        secuenciaDePaginasYDesplazamientosDireccionesLogicas = new int[logicaHexadecimal.length][2];
        secuenciaDePaginasDireccionesLogicas = new int[logicaHexadecimal.length];

        for(int i = 0; i < logicaHexadecimal.length; i++) {
            secuenciaDePaginasYDesplazamientosDireccionesLogicas[i][0] = Integer.parseInt(logicaBinario[i].substring(0, 3), 2);
            secuenciaDePaginasYDesplazamientosDireccionesLogicas[i][1] = Integer.parseInt(logicaBinario[i].substring(3+1, 8+1), 2);

            secuenciaDePaginasDireccionesLogicas[i] = secuenciaDePaginasYDesplazamientosDireccionesLogicas[i][0];

            // Y las guardamos en la tabla
            tabla.anyadirEntradaAColumna("Lógica (hexadecimal)", logicaHexadecimal[i]);
            tabla.anyadirEntradaAColumna("Lógica (binario)", logicaBinario[i] + '\n'
                    + '\n'
                    + "(" + secuenciaDePaginasYDesplazamientosDireccionesLogicas[i][0] + ", " + secuenciaDePaginasYDesplazamientosDireccionesLogicas[i][1] + ")");
        }
    }


    private static void mostrarPaginasYDesplazamientosDireccionesLogicas() {
        System.out.print("Direcciones logicas traducidas a pagina desplazamiento: [");

        for(int i = 0; i < logicaHexadecimal.length-1; i++) {
            System.out.print("(" + secuenciaDePaginasYDesplazamientosDireccionesLogicas[i][0] + " " + secuenciaDePaginasYDesplazamientosDireccionesLogicas[i][1] + "), ");
        }

        System.out.println("( " + secuenciaDePaginasYDesplazamientosDireccionesLogicas[logicaHexadecimal.length-1][0] + " " + secuenciaDePaginasYDesplazamientosDireccionesLogicas[logicaHexadecimal.length-1][1] + ")]");
    }

    private static void mostrarDireccionesFisicas() {
        System.out.println("Direcciones fisicas en binario" + Arrays.toString(fisicaBinario));
        System.out.println("Direcciones fisicas en hexadecimal" + Arrays.toString(fisicaHexadecimal));
        mostrarPaginasYDesplazamientosDireccionesFisicas();
    }

    private static void calcularPaginasYDesplazamientosDireccionesFisicas() {
        secuenciaDePaginasYDesplazamientosDireccionesFisicas = new int[logicaHexadecimal.length][2];
        secuenciaDePaginasDireccionesFisicas = new int[logicaHexadecimal.length];

        for(int i = 0; i < logicaHexadecimal.length; i++) {
            secuenciaDePaginasYDesplazamientosDireccionesFisicas[i][0] = Integer.parseInt(fisicaBinario[i].substring(0, 2), 2);
            secuenciaDePaginasYDesplazamientosDireccionesFisicas[i][1] = Integer.parseInt(fisicaBinario[i].substring(2+1, 7+1), 2);

            secuenciaDePaginasDireccionesFisicas[i] = secuenciaDePaginasYDesplazamientosDireccionesFisicas[i][0];

            // Y las guardamos en la tabla
            tabla.anyadirEntradaAColumna("Física (binario)", fisicaBinario[i] + '\n'
                    + '\n'
                    + "(" + secuenciaDePaginasYDesplazamientosDireccionesFisicas[i][0] + ", " + secuenciaDePaginasYDesplazamientosDireccionesFisicas[i][1] + ")");
            tabla.anyadirEntradaAColumna("Física (hexadecimal)", fisicaHexadecimal[i]);
        }
    }

    private static void mostrarPaginasYDesplazamientosDireccionesFisicas() {
        System.out.print("Direcciones fisicas traducidas a pagina desplazamiento: [");

        for(int i = 0; i < logicaHexadecimal.length-1; i++) {
            System.out.print("(" + secuenciaDePaginasYDesplazamientosDireccionesFisicas[i][0] + " " + secuenciaDePaginasYDesplazamientosDireccionesFisicas[i][1] + "), ");
        }

        System.out.println("( " + secuenciaDePaginasYDesplazamientosDireccionesFisicas[logicaHexadecimal.length-1][0] + " " + secuenciaDePaginasYDesplazamientosDireccionesFisicas[logicaHexadecimal.length-1][1] + ")]");
    }

    private static AlgoritmoDeReemplazo seleccionarAlgoritmoDeReemplazo() {
        Scanner lector;
        int elegido;
        AlgoritmoDeReemplazo algoritmoDeReemplazo;

        lector = new Scanner(System.in);

        System.out.println("Elije un algoritmo (0 Belady, 1 FIFO, 2 LRU, 3 Reloj): ");
        elegido = Integer.parseInt(lector.nextLine());

        switch(elegido) {
            case 0:
            {
                algoritmoDeReemplazo = new Belady(secuenciaDePaginasDireccionesLogicas);
                break;
            }
            case 1:
            {
                algoritmoDeReemplazo = new FIFO();
                break;
            }
            case 2:
            {
                algoritmoDeReemplazo = new LRU();
                break;
            }
            case 3:
            {
                algoritmoDeReemplazo = new Reloj();
                break;
            }
            default:
            {
                algoritmoDeReemplazo = null;
            }
        }

        return algoritmoDeReemplazo;
    }

    private static void ejecutarAlgoritmoDeReemplazo(AlgoritmoDeReemplazo algoritmoDeReemplazo) {
        fisicaBinario = new String[logicaHexadecimal.length];
        fisicaHexadecimal = new String[logicaHexadecimal.length];

        iteracionesEntreFallosDePagina = new ArrayList<>();

        tabla.anyadirEntradaAColumna("Tabla de páginas", algoritmoDeReemplazo.getTablaDePaginas().toString());
        tabla.anyadirEntradaAColumna("Memoria física", algoritmoDeReemplazo.getMemoriaFisica(false).toString());

        for(int iteracion = 0; iteracion < secuenciaDePaginasDireccionesLogicas.length; iteracion++) {
            int marco = algoritmoDeReemplazo.iterar(secuenciaDePaginasDireccionesLogicas[iteracion]);

            tabla.anyadirEntradaAColumna("Tabla de páginas", algoritmoDeReemplazo.getTablaDePaginas().toString());

            if(marco < 0) {
                tabla.anyadirEntradaAColumna("Memoria física", algoritmoDeReemplazo.getMemoriaFisica(false).toString());
                marco = -(marco + 1);
            }
            else {
                tabla.anyadirEntradaAColumna("Memoria física", algoritmoDeReemplazo.getMemoriaFisica(true).toString());

                if(iteracionesEntreFallosDePagina.isEmpty()) { iteracionesEntreFallosDePagina.add(iteracion); }
                else { iteracionesEntreFallosDePagina.add(iteracion - iteracionesEntreFallosDePagina.get(iteracionesEntreFallosDePagina.size()-1) -1); }
            }

            String marcoEnBinario = Integer.toBinaryString(marco);
            while(marcoEnBinario.length() < 2) { marcoEnBinario = "0" + marcoEnBinario; }
            String fisicaEnBinario = marcoEnBinario + " " + logicaBinario[iteracion].substring(3+1, 8+1);
            fisicaBinario[iteracion] = fisicaEnBinario;

            String fisicaEnHexadecimal = Integer.toHexString(Integer.parseInt(fisicaEnBinario.replace(" ", ""), 2)).toUpperCase();
            while(fisicaEnHexadecimal.length() < 2) { fisicaEnHexadecimal = "0" + fisicaEnHexadecimal; }
            fisicaHexadecimal[iteracion] = fisicaEnHexadecimal;
        }

        // Y las anyadimos en la tabla
        calcularPaginasYDesplazamientosDireccionesFisicas();
    }

    private static double media(ArrayList<Integer> array) {
        double suma = 0;
        int total = array.size();

        for (int i = 0; i < total; i++) {
            suma += array.get(i);
        }

        return suma / total;
    }
}
