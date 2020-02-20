package com.algaworks.sistemausuarios;

import com.algaworks.sistemausuarios.dto.UsuarioDTO;
import com.algaworks.sistemausuarios.model.Dominio;
import com.algaworks.sistemausuarios.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ConsultasComCRITERIA {

    public static void main(String[] args) {

         EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Usuarios-PU");

         EntityManager entityManager = entityManagerFactory.createEntityManager();

//        primeirasConsultas(entityManager);
//        escolhendoORetorno(entityManager);
//        fazendoProjecoes(entityManager);
//        passandoParametros(entityManager);
//        fazendoJoins(entityManager);
//        ordenandoResultados(entityManager);
//        paginandoResultados(entityManager);

        entityManager.close();
        entityManagerFactory.close();


    }

        public static void paginandoResultados(EntityManager entityManager){
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

//           passando a instancia do criteriaBuilder para a cláusula where
            criteriaQuery.select(root);
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nome")));

            TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery)
                    .setFirstResult(4)     //  PRIMEIRO = (PAGINA - 1) * QTDE DE REGISTROS POR PAGINA
                    .setMaxResults(2);
            List<Usuario> lista = typedQuery.getResultList();
            lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));
        }

        public static void ordenandoResultados(EntityManager entityManager){
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

//           passando a instancia do criteriaBuilder para a cláusula where
            criteriaQuery.select(root);
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("login")));

            TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
            List<Usuario> lista = typedQuery.getResultList();
            lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));

        }


        public static void passandoParametros(EntityManager entityManager){
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

//              buscando o login do usuario utilizando a cláusula WHERE

            CriteriaQuery<Usuario>criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

            criteriaQuery.select(root);
//            equal - recebe 2 parâmetros (qual propriedade que estou comprando) segundo valor da propriedade
            criteriaQuery.where(criteriaBuilder.equal(root.get("login"), "ria"));

//            no Criteria em vez de passar jpql, Usuario.class, é passado criteriaQuery
            TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
            Usuario usuario = typedQuery.getSingleResult();
            System.out.println(usuario.getId() + ", " + usuario.getNome());


//              buscando o id do usuario utilizando a cláusula WHERE

//            CriteriaQuery<Usuario>criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
//            Root<Usuario> root = criteriaQuery.from(Usuario.class);
//
//            criteriaQuery.select(root);
//            equal - recebe 2 parâmetros (qual propriedade que estou comprando) segundo valor da propriedade
//            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

//            no Criteria em vez de passar jpql, Usuario.class, é passado criteriaQuery
//            TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
//            Usuario usuario = typedQuery.getSingleResult();
//            System.out.println(usuario.getId() + ", " + usuario.getNome());


        }

//      1) PROJEÇÃO BUSCANDO ATRIBUTOS ESPECÍFICOS DA CLASSE DTO
//      2) PROJEÇÃO BUSCANDO ATRIBUTOS ESPECÍFICOS DA CLASSE USUARIO
        public static void fazendoProjecoes(EntityManager entityManager){

           CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

           CriteriaQuery<UsuarioDTO> criteriaQuery = criteriaBuilder.createQuery(UsuarioDTO.class);
           Root<Usuario> root = criteriaQuery.from(Usuario.class);

            //          CONSTRUIR UM OBJETO e passar os parâmetrosque quer buscar
            criteriaQuery.select(criteriaBuilder.construct(UsuarioDTO.class,
                    root.get("id"), root.get("login"), root.get("nome")));

          TypedQuery<UsuarioDTO> typedQuery = entityManager.createQuery(criteriaQuery);
          List<UsuarioDTO> lista = typedQuery.getResultList();
          lista.forEach(u -> System.out.println(String.format("Pertence a classe: " +
                  u.getClass() +  " com ID: "+ u.getId() +
                  " NOME: " + u.getNome() + " LOGIN: " + u.getLogin())));


//            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
//            Root<Usuario> root = criteriaQuery.from(Usuario.class);
//
//            criteriaQuery.multiselect(root.get("id"), root.get("login"), root.get("nome"));
//
//            TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
//            List<Object[]> lista = typedQuery.getResultList();
//            lista.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));

        }

        public static void escolhendoORetorno(EntityManager entityManager){
              CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

//            CriteriaQuery<Dominio> criteriaQuery = criteriaBuilder.createQuery(Dominio.class);
//            Root<Usuario> root = criteriaQuery.from(Usuario.class);
//
//            criteriaQuery.select(root.get("dominio")); // quero propriedade dominio e não usuário
//
//            TypedQuery<Dominio> typedQuery = entityManager.createQuery(criteriaQuery);
//            List<Dominio> lista = typedQuery.getResultList();
//            lista.forEach(d -> System.out.println(d.getId() + ", " +d.getNome()));

            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

            criteriaQuery.select(root.get("nome"));

            TypedQuery<String> typedQuery = entityManager.createQuery(criteriaQuery);
            List<String> lista = typedQuery.getResultList();
            lista.forEach(nome -> System.out.println( "Nome: " +nome));
        }

        public static void primeirasConsultas(EntityManager entityManager){
//          ajuda a ordenar, fazer filtros, utilizar funções do sql
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<Usuario>criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

            criteriaQuery.select(root);
//          no Criteria em vez de passar jpql, Usuario.class, é passado criteriaQuery
            TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
            List<Usuario> lista = typedQuery.getResultList();
            lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

            /*String jpql = "select u from Usuario u";     u do álias = Root<Usuario>
            TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
            List<Usuario> lista = typedQuery.getResultList();
            lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));*/
        }
    }

