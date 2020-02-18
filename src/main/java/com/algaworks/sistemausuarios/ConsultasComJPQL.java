package com.algaworks.sistemausuarios;

import com.algaworks.sistemausuarios.dto.UsuarioDTO;
import com.algaworks.sistemausuarios.model.Dominio;
import com.algaworks.sistemausuarios.model.Usuario;

import javax.persistence.*;
import java.util.List;

public class ConsultasComJPQL {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Usuarios-PU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //primeirasConsulta(entityManager);
        //escolhendoORetorno(entityManager);
       // fazendoProjecoes(entityManager);
        passandoParametros(entityManager);

        entityManager.close();
        entityManagerFactory.close();

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
