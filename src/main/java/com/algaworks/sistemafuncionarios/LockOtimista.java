package com.algaworks.sistemafuncionarios;

import com.algaworks.sistemafuncionarios.model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LockOtimista {

    private static final Integer CALL_LIGHTMAN_ID = 1;

    public static void main(String[] args) {
  /*      // inicia a factory
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Funcionarios-PU");

        // busca um funcionário da base
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Funcionario funcionario = entityManager.find(Funcionario.class, CALL_LIGHTMAN_ID);
        entityManager.close();

        //simulando um usuario que entrou na tela de funcionario na sessao1
        TelaDeFuncionarios sessao1 =
                new TelaDeFuncionarios("SESSAO_1", entityManagerFactory,
                        funcionario, "Call Lightman Moreira");
        sessao1.editarNome();
        sessao1.submeterFormulario();
        sessao1.atualizarTelaParaVerificarNome();
        sessao1.fecharTela();

        //simulando um segundo usuário alterando o mesmo registro submetendo o formulario
        TelaDeFuncionarios sessao2 =
                new TelaDeFuncionarios("SESSAO_2", entityManagerFactory,
                        funcionario, "Call Lightman Silva");

        sessao2.editarNome();
        sessao2.submeterFormulario();

//        atualizou o brownser para ver se o registro que ele alterou foi atualizado no bd
         sessao1.atualizarTelaParaVerificarNome();
         sessao2.atualizarTelaParaVerificarNome();

        sessao1.fecharTela();
        sessao2.fecharTela();

        entityManagerFactory.close();*/
    }

    public static class TelaDeFuncionarios {

        private final String sessao;

        private final EntityManager entityManager;

        private final Funcionario funcionario;

        private final String novoNome;

        public TelaDeFuncionarios(String sessao, EntityManagerFactory entityManagerFactory,
                                  Funcionario funcionario, String novoNome) {
            this.sessao = sessao;
            this.entityManager = entityManagerFactory.createEntityManager();
            this.funcionario = funcionario;
            this.novoNome = novoNome;
        }

        public void editarNome() {
            funcionario.setNome(novoNome);
        }

        public void submeterFormulario() {
            System.out.println(sessao + ": Iniciando tentativa de atualaizar funcionário.");

            try {
                entityManager.getTransaction().begin();
                entityManager.merge(funcionario);
                entityManager.getTransaction().commit();

                System.out.println(sessao + ": Funcionário foi atualizado. ");
            } catch (Exception e) {
                System.out.println(sessao + ": Erro na atualização do funcionário. MSG: " + e.getMessage());

                throw e;
            }
        }

        public void atualizarTelaParaVerificarNome() {
            entityManager.clear();

            Funcionario funcionario = entityManager.find(Funcionario.class, CALL_LIGHTMAN_ID);

            System.out.println(sessao + ": Tela da sessão " + sessao + " atualizada.");
            if (novoNome.equals(funcionario.getNome())) {
                System.out.println(sessao + ": Bom... Foi atualizado certinho. " +
                        "Agora vou continuar meu trabalho.");
            } else {
                System.out.println(sessao + ": Ueh! Não tinha deixado o nome " + funcionario.getNome() +
                        " eu tenho certeza que coloquei " + novoNome);
            }
        }

        public void fecharTela() {
            entityManager.close();
        }
    }
}
