public class Belady extends AlgoritmoDeReemplazo {

    int[] secuenciaDePaginas;
    int paginasRecorridas;



    public Belady(int[] secuenciaDePaginas) {
        super();

        this.secuenciaDePaginas = secuenciaDePaginas;
        paginasRecorridas = 0;
    }



    public int iterar(int pagina) {
        int marco = calcularMarco(pagina);

        paginasRecorridas++;

        if(marco >= 0) {
            cargarEnMemoria(pagina, marco);
        }

        return marco;
    }


    private int calcularMarco(int pagina) {
        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            if(pagina == memoria[marco]) { return -marco-1; }
        }
        //---------------------------------------------------

        int[] tiemposHastaReferencia = new int[numeroDeMarcos];
        int max;

        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            for(int i = paginasRecorridas+1; i < secuenciaDePaginas.length; i++) {
                if(secuenciaDePaginas[i] != memoria[marco]) { tiemposHastaReferencia[marco]++; }
                else { break; }
            }
        }

        max = 0;

        for(int marco = 1; marco < numeroDeMarcos; marco++) {
            if(tiemposHastaReferencia[marco] > tiemposHastaReferencia[max]) { max = marco; }
        }

        return max;
    }
}
