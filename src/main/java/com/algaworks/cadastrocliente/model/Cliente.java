package com.algaworks.cadastrocliente.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cli_id")
    private Long id;

//    @Column(name = "cli_nome")
    private String nome;



    public void setId(Long id) {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
