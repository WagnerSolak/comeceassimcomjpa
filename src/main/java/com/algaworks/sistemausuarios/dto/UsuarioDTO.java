package com.algaworks.sistemausuarios.dto;

import java.util.Objects;
//esta classe foi criado com intuito de utilizar em consulta, e tem que trfegar dados para a rede, e assim cria a classe
public class UsuarioDTO {

    private Integer id;

    private String login;

    private String nome;

    // construtor é necessário para buscar os atributos
    public UsuarioDTO(Integer id, String login, String nome){
        this.id = id;
        this.login = login;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        UsuarioDTO that = (UsuarioDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
