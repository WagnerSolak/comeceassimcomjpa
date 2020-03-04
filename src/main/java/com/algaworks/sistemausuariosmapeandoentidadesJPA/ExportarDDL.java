package com.algaworks.sistemausuariosmapeandoentidadesJPA;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ExportarDDL {

    public static void main(String[] args) {

        Map<String, String> propriedades = new HashMap<>();
        propriedades.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");
        propriedades.put("javax.persistence.schema-generation.scripts.create-target", "C:/dev/scriptsParaIntellij/script-criacao.sql");
        propriedades.put("javax.persistence.schema-generation.scripts.drop-target", "C:/dev/scriptsParaIntellij/script-remocao.sql");


        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Usuarios-PU", propriedades);



        entityManagerFactory.close();
    }
}
