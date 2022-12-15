public class Reloj extends AlgoritmoDeReemplazo {

    int[] bitsDeModo;
    int puntero;



    public Reloj() {
        super();

        bitsDeModo = new int[numeroDeMarcos];

        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            bitsDeModo[marco] = 1;
        }

        puntero = 0;
    }


    @Override
    public int iterar(int pagina) {
        int marco = calcularMarco(pagina);

        if(marco >= 0) {
            bitsDeModo[marco] = 1;

            cargarEnMemoria(pagina, marco);
        }
        else {
            bitsDeModo[-(marco+1)] = 1;
        }

        return marco;
    }


    private int calcularMarco(int pagina) {
        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            if(pagina == memoria[marco]) { return -marco-1; }
        }
        //---------------------------------------------------

        int marco;

        marco = -1;
        while(marco == -1) {
            if(bitsDeModo[puntero] == 0) { marco = puntero; }
            else {
                bitsDeModo[puntero] = 0;
            }

            puntero++;
            if(puntero == numeroDeMarcos) { puntero = 0; }
        }

        return marco;
    }

    @Override
    public Tabla getMemoriaFisica(boolean falloDePagina) {
        Tabla tablaMemoriaFisica = new Tabla();

        tablaMemoriaFisica.anyadirColumna("Marco");
        tablaMemoriaFisica.anyadirColumna("Página");

        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            String bitDeModo = "";
            if(bitsDeModo[marco] == 1) { bitDeModo = "*"; }

            String marcoString = "F" + marco;
            if(puntero == marco) { marcoString = "->" + marcoString; }

            tablaMemoriaFisica.anyadirEntradaAColumna("Marco", marcoString);
            tablaMemoriaFisica.anyadirEntradaAColumna("Página", memoria[marco] + bitDeModo);
        }

        if(falloDePagina) {
            tablaMemoriaFisica.anyadirEntradaAColumna("Página", "F");
        }

        return tablaMemoriaFisica;
    }
}
