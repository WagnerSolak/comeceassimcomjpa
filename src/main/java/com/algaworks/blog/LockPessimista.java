package com.algaworks.blog;

import com.algaworks.blog.model.Artigo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

public class LockPessimista {

    private static final Integer ID = 1;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Blog-PU");

        //entendendAsOpcoes(entityManagerFactory);
        //javaEOWorkbeanch(entityManagerFactory);
        //casoMaisPratico(entityManagerFactory);
    }
    public static void casoMaisPratico(EntityManagerFactory entityManagerFactory){
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        // como se João abriu a transação primeiro
        Runnable runnable1Joao = () -> {
            entityManager1.getTransaction().begin();
            log(1, "Imediatamente antes find.");
            Artigo artigo1 = entityManager1.find(
                    Artigo.class, ID, LockModeType.PESSIMISTIC_READ);
            log(1, "Imediatamente após find.");

            artigo1.setConteudo("Alteração do João (TH1)");

            log(1, "Esperando 3 segundos...");
            esperar(3000);
            log(1, "Espera dos 3 segs terminada.");

            log(1, "Imediatamente antes do commit.");
            entityManager1.getTransaction().commit();
            log(1, "Imediatamente após o commit.");
        };

        Runnable runnable2Maria = () -> {
            log(2, "Esperando 100 milis...");
            esperar(100);
            log(2, "Espera dos 100 milis terminada.");

            entityManager2.getTransaction().begin();
            log(2, "Imediatamente antes find.");
            Artigo artigo2 = entityManager2.find(
                    Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
            log(2, "Imediatamente após find.");

            artigo2.setConteudo(artigo2.getConteudo() + " + Alteração da Maria (TH2)");

            log(2, "Imediatamente antes do commit.");
            entityManager2.getTransaction().commit();
            log(2, "Imediatamente após o commit.");
        };

        //duas threads, duas instâncias de entityManager, cada thread sendo uma request diferente
        Thread thread1 = new Thread(runnable1Joao);
        Thread thread2 = new Thread(runnable2Maria);

        thread1.start();
        thread2.start();
    }


    // regra: Lock termina quando da o commit

    public static void entendendAsOpcoes(EntityManagerFactory entityManagerFactory){
        // SITUAÇÃO 1 com LockModeType.PESSIMISTIC_READ
        // Note que o entityManager está sendo encerrado abaixo da transação 2;
        // desta forma foi dado Lock de leitura na T1, ele irá ler normalmente(find da T2), porém ao dar
        //o commit, ele não irá deixar, pois foi dado lock de leitura (SELECT T1, SELECT T2, E dá erro: Timeout and LockPessimist)

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        entityManager1.getTransaction().begin();    // abrindo a transação
        Artigo artigo1 = entityManager1.find(Artigo.class, ID, LockModeType.PESSIMISTIC_READ);
        artigo1.setConteudo("Alteração do João");
        //entityManager1.getTransaction().commit();


        entityManager2.getTransaction().begin();
        Artigo artigo2 = entityManager1.find(Artigo.class, ID);
        artigo1.setConteudo(artigo2.getConteudo() + " Alteração da Maria");
        entityManager2.getTransaction().commit();

        entityManager1.getTransaction().commit();


        // SITUAÇÃO 2 com LockModeType.PESSIMISTIC_WRITE
        // Mesmo resultado da situação 1: Lock wait time out

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        EntityManager entityManager4 = entityManagerFactory.createEntityManager();

        entityManager3.getTransaction().begin();    // abrindo a transação
        Artigo artigo3 = entityManager3.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
        artigo3.setConteudo("Alteração do João");
        //entityManager3.getTransaction().commit();


        entityManager4.getTransaction().begin();
        Artigo artigo4 = entityManager1.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
        artigo4.setConteudo(artigo4.getConteudo() + " Alteração da Maria");
        entityManager4.getTransaction().commit();

        entityManager4.getTransaction().commit();


        //RESUMO:
        // Vamos utilizar o PESSIMISTIC_READ, quando a gente quer fazer nossa pesquisa, garantir o Lock, mas não quer mais
        // nenhuma transação de pesquisar também. Só commita quem têm a transação com Lock de leitura.
        // Vantagem: busca registro, faz  Lock, faz update, commita e todas as outros usuários conseguem pesquisar os reg.,
        // mas não inserem, e assim trabalham com registro "errado"

        // Vamos utilizar o PESSIMISTIC_WRITE, quando a gente quer fazer nossa pesquisa, mas não quer ninguém leia a informação
        // desatualizada, ninguém consegue nem fazer a leitura. Para quando não qero: QUE LEIA NENHUM REG. DESATUALIZADO.
        // Vantagem: quando acabar as operações da T1, o usuário da T2 conseguirá ver os dados corretos e vai consegui comitar.
    }




    //---------------------------------------------------------------------------------------------------------------------//
    // teste realizado no Workbench: rodando o código no java, aguardando, e no workbench rodando:
    //select * from artigo where id = 1; -> OK
    //select * from artigo where id = 1 for share; -> OK
    //select * from artigo where id = 1 for update; -> não rodou pois como é update e esta PESSIMISTIC_READ
    //update artigo set conteudo = 'Alteração Workbench." where id = 1; -> não rodou
    public static void javaEOWorkbeanch(EntityManagerFactory entityManagerFactory){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        log(1, "Imediatamente ANTES find.");
        Artigo artigo1 = entityManager.find(Artigo.class, ID, LockModeType.PESSIMISTIC_READ);
        log(1, "Imediatamente APÓS find.");

        artigo1.setConteudo("Alteração do João (TH1)"); //alteração de uma entidade que está sendo gerenciada

        log(1, "Esperando 15 segundos.");
        esperar(15000);
        log(1, "Espera dos 15 segs terminada.");

        log(1, "Imediatamente antes do commit.");
        entityManager.getTransaction().commit();  // chama o flush e faz o commit no bco
        log(1, "Imediatamente após o commit.");

    }

    private static  void log(Integer thread, String msg){
        System.out.println("[THREAD_" + thread + "] " + msg);
    }

    private static  void esperar(long milesegundos){
        try {
            //Thread.sleep(milesegundos);
        }catch (Exception e){
            e.getMessage();
        }

    }
    //Resultado com PESSIMISTIC WRITE
    // Só rodou  -> select * from artigo where id = 1;
    //---------------------------------------------------------------------------------------------------------------------//

}
