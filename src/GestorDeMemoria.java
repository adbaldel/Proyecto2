public abstract class GestorDeMemoria {

    protected int numeroDeMarcos;
    protected int numeroDePaginas;
    protected int[] memoria;
    protected int[][] tablaDePaginas;



    public GestorDeMemoria() {
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
        tablaDePaginas[pagina][1] = 9;
    }

    protected void cargarEnMemoria(int pagina, int marco) {
        memoria[marco] = pagina;

        tablaDePaginas[pagina][0] = 0;
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

    protected void mostrarIteracion(boolean falloDePagina) {
        if(falloDePagina) {
            System.out.println("       \t  F  ");
        }

        System.out.println("+-----+\t+---+");

        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            System.out.println("| " + tablaDePaginas[marco][0] + " " + tablaDePaginas[marco][1] + " |\t| " + memoria[marco] + " |");
            System.out.println("+-----+\t+---+");
        }

        for(int pagina = numeroDeMarcos; pagina < numeroDePaginas; pagina++) {
            System.out.println("| " + tablaDePaginas[pagina][0] + " " + tablaDePaginas[pagina][1] + " |");
            System.out.println("+-----+");
        }

        System.out.println("       \t     ");
    }
}
