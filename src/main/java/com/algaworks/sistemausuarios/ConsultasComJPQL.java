package com.algaworks.sistemausuarios;

import com.algaworks.sistemausuarios.dto.UsuarioDTO;
import com.algaworks.sistemausuarios.model.Configuracao;
import com.algaworks.sistemausuarios.model.Dominio;
import com.algaworks.sistemausuarios.model.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ConsultasComJPQL {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Usuarios-PU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        primeirasConsulta(entityManager);
//        escolhendoORetorno(entityManager);
//        fazendoProjecoes(entityManager);
//        passandoParametros(entityManager);
//        fazendoJoins(entityManager);
//        fazendoLeftJoin(entityManager);
//        carregamentoComJoinFetch(entityManager);
//        filtrandoRegistros(entityManager);
//        utilizandoOperadoresLogicos(entityManager);
//        ordenandoResultados(entityManager);
          paginandoResultados(entityManager);

        entityManager.close();
        entityManagerFactory.close();

    }

    public static void paginandoResultados(EntityManager entityManager){
        String jpql = "select u from Usuario u";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setFirstResult(0)  //onde vc quer começar a pegar seus resultados. if FirstResult 2 mostra o 3 e o 4
                .setMaxResults(2);  //em alguns sistemas qtos reg. vc quer ver por página -> MaxResults
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));

    }

    public static void ordenandoResultados(EntityManager entityManager) {
        String jpql = "select u from Usuario u order by u.nome";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

    }

    public static void utilizandoOperadoresLogicos(EntityManager entityManager){
        String jpql = "select u from Usuario u where " +
                " (u.ultimoAcesso > :ontem and u.ultimoAcesso < :hoje) " +
                " or u.ultimoAcesso is null";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter("ontem", LocalDateTime.now().minusDays(1))
                .setParameter("hoje", LocalDateTime.now());
        List<Usuario>  lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", "+ u.getNome()));


    }

    public static void utilizandoOperadorIn(EntityManager entityManager){
        String jpql = "select u from Usuario u where u.id in (:ids)";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter("ids", Arrays.asList(1,2));
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));
    }


    public static  void filtrandoRegistros(EntityManager entityManager){
        //like , is null, is empty, between, >, <, <= , >=, <>

        //Utilizando like, desta forma
       /* String jpql = "select u from Usuario u where u.nome like :nomeUsuario";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter( "nomeUsuario", "Cal%");
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));*/

        //é igual a esta
        /*String jpql = "select u from Usuario u where u.nome like concat(:nomeUsuario, '%')";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter( "nomeUsuario", "Cal");
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));*/

        //IS NULL  = select u from Usuario u where u.senha is null
        //IS EMPTY = select d Dominio d where d.ususarios is empty

        //BETWEEN  = acesso foi entre duas datas

        String jpqlUsandoBet = "select u from Usuario u where u.ultimoAcesso between :ontem and :hoje";
        TypedQuery<Usuario> typedQueryUsandoBet = entityManager.createQuery(jpqlUsandoBet, Usuario.class)
                .setParameter( "ontem", LocalDateTime.now().minusDays(1))
                .setParameter("hoje", LocalDateTime.now());
        List<Usuario> listaUsandoBet = typedQueryUsandoBet.getResultList();
        listaUsandoBet.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void carregamentoComJoinFetch(EntityManager entityManager){
        String jpql = "select u from Usuario u join fetch u.configuracao join fetch u.dominio d";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));
    }
//  todos usuários que tiverem uma relação com configuracao, mas se tiver algum usuario que nao tenha config ele trás mesmo assim
    public static void fazendoLeftJoin(EntityManager entityManager){
        String jpql = "select u,c from Usuario u left join u.configuracao c";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr ->{
            // arr[0] == Usuario
            // arr[1] == Configuracao
                String out = ((Usuario) arr[0]).getNome();
                if (arr[1] == null) {
                    out += ", NULL";
                }else{
                    out += ", " + ((Configuracao) arr[1]).getId();
                }
        System.out.println(out);
    });
}
    //todos usuário que fazem partes de um determinado dominio
    public static void fazendoJoins(EntityManager entityManager){
        //sql -> select u. * from usuario u join dominio d on u.domio_id = d.id;
        String jpql = "select u from Usuario u join u.dominio d where d.id = 1";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));

    }
        public static void passandoParametros(EntityManager entityManager){

        String jpql = "select u from Usuario u where u.id = :idUsuario";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        typedQuery.setParameter("idUsuario", 1) ;                     //passando o parâmetro
        Usuario usuario = typedQuery.getSingleResult();                 //pega resultado
            System.out.println(usuario.getId() + ", " + usuario.getNome()); // manda imprimir


        //buscando por login
        String jpqlLog = "select u from Usuario u where u.login = :loginUsuario";
        TypedQuery<Usuario> typedQueryLog = entityManager.createQuery(jpqlLog, Usuario.class);
        typedQueryLog.setParameter("loginUsuario", "ria") ;                     //passando o parâmetro
        Usuario usuarioLog = typedQueryLog.getSingleResult();                 //pega resultado
            System.out.println(usuarioLog.getId() + ", " + usuarioLog.getNome()); // manda imprimir

        }

        //projeção -> pegar o retorno
        public static void fazendoProjecoes(EntityManager entityManager){
            //pegando o retorno como uma lista ->Array
            String jpqlArr = "select id, login, nome from Usuario";
            TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpqlArr, Object[].class);
            List<Object[]> listaArr = typedQuery.getResultList();
            listaArr.forEach(arr-> System.out.println(String.format("%s, %s, %s", arr)));


            //criando uma classe (DTO) e pegar retorno só dos campos que quiser
            String jpqlDto = "select new com.algaworks.sistemausuarios.dto.UsuarioDTO(id, login, nome)" +
                    "from Usuario";
            TypedQuery<UsuarioDTO> typedQueryDto = entityManager.createQuery(jpqlDto, UsuarioDTO.class);
            List<UsuarioDTO> listaDto = typedQueryDto.getResultList();
            listaDto.forEach(u-> System.out.println("DTO: " + u.getId() + ", " + u.getNome()));
        }

        public static void escolhendoORetorno(EntityManager entityManager){
            String jpql = "select u.dominio from Usuario u where u.id = 1";
            TypedQuery<Dominio> typedQuery = entityManager.createQuery(jpql, Dominio.class);
            Dominio dominio = (Dominio) typedQuery.getSingleResult();
            System.out.println(dominio.getId() + "," + dominio.getNome());

            //buscar apenas os nomes (consulta de String)
            String jpqlNom = "select u.nome from Usuario u";
            TypedQuery<String> typedQueryNom = entityManager.createQuery(jpqlNom, String.class);
            List<String> listaNom = typedQueryNom.getResultList();
            listaNom.forEach(nome-> System.out.println(nome));



         }


        public static void primeirasConsulta(EntityManager entityManager){
            String jpql = "select u from Usuario u";
            TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
            List<Usuario> lista = typedQuery.getResultList();
            lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

            String jpqlSing = "select u from Usuario u where u.id = 1";
            TypedQuery<Usuario> typedQuerySing = entityManager.createQuery(jpqlSing, Usuario.class);
            Usuario usuario = typedQuerySing.getSingleResult();
            System.out.println(usuario.getId() + ", " + usuario.getNome());

            String jpqlCast = "select u from Usuario u where u.id = 1";
            Query query = entityManager.createQuery(jpqlCast);
            Usuario usuario2 = (Usuario) query.getSingleResult();
            System.out.println(usuario2.getId() + ", " + usuario2.getNome());


    }
}
