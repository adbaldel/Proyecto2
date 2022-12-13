public class Reloj extends GestorDeMemoria {

    int[] reloj;
    int puntero;



    public Reloj() {
        super();

        reloj = new int[numeroDeMarcos];
        puntero = 0;

        mostrarIteracion(false);
    }


    @Override
    public int iterar(int pagina) {
        int marco = calcularMarco(pagina);

        if(marco >= 0) {
            reloj[marco] = 1;

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

        int marco;

        marco = -1;
        while(marco == -1) {
            if(reloj[puntero] == 0) { marco = puntero; }
            else { reloj[puntero] = 0; }
        }

        return marco;
    }

    @Override
    protected void mostrarIteracion(boolean falloDePagina) {
        if(falloDePagina) {
            System.out.println("       \t  F  ");
        }

        System.out.println("+-----+\t+---+");

        for(int marco = 0; marco < numeroDeMarcos; marco++) {
            System.out.println("| " + tablaDePaginas[marco][0] + " " + tablaDePaginas[marco][1] + " |\t| " + memoria[marco] + " |" + " " + reloj[marco]);
            System.out.println("+-----+\t+---+");
        }

        for(int pagina = numeroDeMarcos; pagina < numeroDePaginas; pagina++) {
            System.out.println("| " + tablaDePaginas[pagina][0] + " " + tablaDePaginas[pagina][1] + " |");
            System.out.println("+-----+");
        }

        System.out.println("       \t     ");
    }
}
