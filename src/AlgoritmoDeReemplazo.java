public abstract class AlgoritmoDeReemplazo {

    protected int numeroDeMarcos;
    protected int numeroDePaginas;
    protected int[] memoria;
    protected int[][] tablaDePaginas;



    public AlgoritmoDeReemplazo() {
        numeroDeMarcos = 4;
        numeroDePaginas = 8;
        memoria = new int[numeroDeMarcos];
        tablaDePaginas = new int[numeroDePaginas][2];

        inicializarTablaDePaginas();
        inicializarMemoria();
    }



    public abstract int iterar(int pagina);

    protected void liberarDeMemoria(int pagina) {
        tablaDePaginas[pagina][0] = 0;
        tablaDePaginas[pagina][1] = -1;
    }

    protected void cargarEnMemoria(int pagina, int marco) {
        liberarDeMemoria(memoria[marco]);

        memoria[marco] = pagina;

        tablaDePaginas[pagina][0] = 1;
        tablaDePaginas[pagina][1] = marco;
    }

    protected void inicializarTablaDePaginas() {
        for(int i = 0; i < numeroDePaginas; i++) {
            liberarDeMemoria(i);
        }
    }

    protected void inicializarMemoria() {
        cargarEnMemoria(5,0);
        cargarEnMemoria(3,1);
        cargarEnMemoria(2,2);
        cargarEnMemoria(1,3);
    }

    public Tabla getTablaDePaginas() {
        Tabla tablaTablaDePaginas = new Tabla();

        tablaTablaDePaginas.anyadirColumna("Página");
        tablaTablaDePaginas.anyadirColumna("Bit de presencia");
        tablaTablaDePaginas.anyadirColumna("Marco");

        for(int pagina = 0; pagina < numeroDePaginas; pagina++) {
            String marco = "-";

            if(tablaDePaginas[pagina][1] != -1) {
                marco = "F" + tablaDePaginas[pagina][1];
            }

            tablaTablaDePaginas.anyadirEntradaAColumna("Página", String.valueOf(pagina));
            tablaTablaDePaginas.anyadirEntradaAColumna("Bit de presencia", String.valueOf(tablaDePaginas[pagina][0]));
            tablaTablaDePaginas.anyadirEntradaAColumna("Marco", marco);
        }

        return tablaTablaDePaginas;
    }

    public Tabla getMemoriaFisica(boolean falloDePagina) {
        Tabla tablaMemoriaFisica = new Tabla();

        tablaMemoriaFisica.anyadirColumna("Marco");
        tablaMemoriaFisica.anyadirColumna("Página");

        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            tablaMemoriaFisica.anyadirEntradaAColumna("Marco", "F" + marco);
            tablaMemoriaFisica.anyadirEntradaAColumna("Página", String.valueOf(memoria[marco]));
        }

        if(falloDePagina) {
            tablaMemoriaFisica.anyadirEntradaAColumna("Página", "F");
        }

        return tablaMemoriaFisica;
    }
}