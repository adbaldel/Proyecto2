import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // D1 89 57 C3 FA 7A 96 C1 82 02 0F 64 0A D6 42 16 8D 17 49 D2 D1 DF 35 CD 7A C0 29 90 85 AE

    public static void main(String[] args) {

        Scanner lector = new Scanner(System.in);



        String[] entradaEnHexadecimal;
        String[] entradaEnBinario;
        int[] entradaEnDecimal;

        System.out.println("Introduce la secuencia de paginas en hexadecimal separada por espacios (al finalizar pulas enter):");

        entradaEnHexadecimal = lector.nextLine().split(" ");
        entradaEnDecimal = new int[entradaEnHexadecimal.length];
        entradaEnBinario = new String[entradaEnHexadecimal.length];


        for(int i = 0; i < entradaEnHexadecimal.length; i++) {
            entradaEnDecimal[i] = Integer.parseInt(entradaEnHexadecimal[i], 16);
        }

        for(int i = 0; i < entradaEnHexadecimal.length; i++) {
            entradaEnBinario[i] = Integer.toBinaryString(entradaEnDecimal[i]);

            while(entradaEnBinario[i].length() < 8) { entradaEnBinario[i] = "0" + entradaEnBinario[i]; }
        }

        System.out.println("La misma secuencia en binario: " + Arrays.toString(entradaEnBinario));
        System.out.println("La misma secuencia en decimal: " + Arrays.toString(entradaEnDecimal));



        int[][] paginaDesplazamiento = new int[entradaEnHexadecimal.length][2];
        int[] secuenciaDePaginas = new int[entradaEnHexadecimal.length];

        for(int i = 0; i < entradaEnHexadecimal.length; i++) {
            paginaDesplazamiento[i][0] = Integer.parseInt(entradaEnBinario[i].substring(0, 3), 2);
            paginaDesplazamiento[i][1] = Integer.parseInt(entradaEnBinario[i].substring(3, 8), 2);

            secuenciaDePaginas[i] = paginaDesplazamiento[i][0];
        }

        System.out.print("Traducida a pagina desplazamiento: [");

        for(int i = 0; i < entradaEnHexadecimal.length-1; i++) {
            System.out.print("(" + paginaDesplazamiento[i][0] + " " + paginaDesplazamiento[i][1] + "), ");
        }

        System.out.println("( " + paginaDesplazamiento[entradaEnHexadecimal.length-1][0] + " " + paginaDesplazamiento[entradaEnHexadecimal.length-1][1] + ")]");



        int algoritmo;
        GestorDeMemoria gestorDeMemoria;

        System.out.println("Elije un algoritmo (0 Belady, 1 FIFO, 2 LRU, 3 Reloj): ");

        algoritmo = Integer.parseInt(lector.nextLine());

        switch(algoritmo) {
            case 0:
            {
                gestorDeMemoria = new Belady(secuenciaDePaginas);
                break;
            }
            case 1:
            {
                gestorDeMemoria = new FIFO();
                break;
            }
            case 2:
            {
                gestorDeMemoria = new LRU();
                break;
            }
            case 3:
            {
                gestorDeMemoria = new Reloj();
                break;
            }
            default:
            {
                gestorDeMemoria = new FIFO();
            }
        }

        for(int i = 0; i < secuenciaDePaginas.length; i++) {
            int marco = gestorDeMemoria.iterar(secuenciaDePaginas[i]);

            if(marco < 0) { marco = -(marco + 1); }

            String marcoBinario = Integer.toBinaryString(marco);

            while(marcoBinario.length() < 2) { marcoBinario = "0" + marcoBinario; }

            System.out.println("Direccion fisica: " + marco + ", " + paginaDesplazamiento[i][1]);
            System.out.println("Direccion fisica (binario): " + marcoBinario + entradaEnBinario[i].substring(3, 8));
            System.out.println("Direccion fisica (hexadecimal): " + Integer.toHexString(Integer.parseInt(marcoBinario + entradaEnBinario[i].substring(3, 8), 2)));
            System.out.println();
        }
    }
}
