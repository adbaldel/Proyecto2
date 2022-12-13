public class LRU extends GestorDeMemoria {

    int[] tiempoSinUsar;



    public LRU() {
        super();

        tiempoSinUsar = new int[numeroDeMarcos];
        tiempoSinUsar[0] = 3;
        tiempoSinUsar[1] = 2;
        tiempoSinUsar[2] = 1;
        tiempoSinUsar[3] = 0;

        mostrarIteracion(false);
    }



    public int iterar(int pagina) {
        int marco = calcularMarco(pagina);

        if(marco >= 0) {
            tiempoSinUsar[marco] = 0;
            for(int i = 0; i < numeroDeMarcos; i++) {
                if(i != marco) {
                    tiempoSinUsar[i]++;
                }
            }

            cargarEnMemoria(pagina, marco);
        }

        mostrarIteracion(marco >= 0);

        return marco;
    }


    private int calcularMarco(int pagina) {
        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            if(pagina == memoria[marco]) { return -marco-1; }
        }
        //---------------------------------------------------

        int max;

        max = 0;

        for(int marco = 1; marco < numeroDeMarcos; marco++) {
            if(tiempoSinUsar[marco] > tiempoSinUsar[max]) { max = marco; }
        }

        return max;
    }
}
