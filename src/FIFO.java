public class FIFO extends AlgoritmoDeReemplazo {

    int primero;



    public FIFO() {
        super();

        primero = 0;
    }



    public int iterar(int pagina) {
        int marco = calcularMarco(pagina);

        if(marco >= 0) {
            primero++;
            if(primero == numeroDeMarcos) { primero = 0; }

            cargarEnMemoria(pagina, marco);
        }

        return marco;
    }


    private int calcularMarco(int pagina) {
        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            if(pagina == memoria[marco]) { return -marco-1; }
        }
        //---------------------------------------------------

        return primero;
    }
}