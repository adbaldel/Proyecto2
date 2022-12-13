public class FIFO extends GestorDeMemoria {

    int primero;



    public FIFO() {
        super();

        primero = 0;

        mostrarIteracion(false);
    }



    public int iterar(int pagina) {
        int marco = calcularMarco(pagina);

        if(marco >= 0) {
            primero++;
            if(primero == numeroDeMarcos) { primero = 0; }

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

        return primero;
    }
}
