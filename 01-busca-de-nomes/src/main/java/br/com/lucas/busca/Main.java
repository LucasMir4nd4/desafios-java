package br.com.lucas.busca;

public class Main {

    public static void main(String[] args) {

        System.out.println("Sistema de Busca de Nomes - em construção");

        String procurado = "Dave";

        System.out.println("Procurando por: " + procurado);

        long inicio = System.currentTimeMillis();

        String[] arquivos = {
            "signatarios-fev-2002.txt",
            "signatarios-fev-2016.txt",
            "signatarios-out-2014.txt"
        };

        Thread[] threads  = new Thread[arquivos.length];

        for (int index = 0; index < threads.length; index++) {

            BuscaNomeRunnable busca = new BuscaNomeRunnable(arquivos[index], procurado);
            threads[index] = new Thread(busca);
            threads[index].start();

        }

        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        long fim = System.currentTimeMillis();
        System.out.println();
        System.out.println();

        System.out.println("Tempo de execução: "+ (fim - inicio)+"ms");


    }
}
