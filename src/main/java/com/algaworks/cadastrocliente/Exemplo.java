package com.algaworks.cadastrocliente;

import com.algaworks.cadastrocliente.model.Cliente;
import com.mysql.cj.xdevapi.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Exemplo {

    public static void main(String[] args) {
//      as instancias serão criadas
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//       buscar registro bd

//       Cliente cliente = entityManager.find(Cliente.class, o: 1L);
//       System.out.println(cliente.getNome());

//      adicionar registro
//      Cliente cliente = new Cliente();
//        cliente.setId(3); //não precisa mais deste set pois foi add estratégia ao atributo id
//        cliente.setNome("Eletro Silva");

//        entityManager.getTransaction().begin(); //abriu a transação
//        entityManager.persist(cliente);
//        entityManager.getTransaction().commit(); //commitou antes do flush (internamente)

//      remover
//      Cliente cliente = entityManager.find(Cliente.class,1L);
//        entityManager.getTransaction().begin();
//        entityManager.remove(cliente);
//        entityManager.getTransaction().commit();

//      buscando duas consultas(cash de segundo nível) ele faz apenas uma consulta
//       Cliente cliente =  entityManager.find(Cliente.class, 2L);
//       Cliente cliente1 = entityManager.find(Cliente.class, 2L);

//      alterando dados
//      Cliente cliente =  entityManager.find(Cliente.class, 2L); //pesquisou -> passou a ser gerenciado
//        entityManager.getTransaction().begin();
//        cliente.setNome(cliente.getNome() + " Alterado"); //alterou a propriedade
//
//       alterando dados de objetos não gerenciados (aplicação web por exemplo) obj novo, chamou o merge -> gerencia o obj
        Cliente cliente = new Cliente();
        cliente.setId(2L);
        cliente.setNome("Armazem Estrela");

        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();

//      as instancias são encerradas
          entityManager.close();
          entityManagerFactory.close();
    }
}
