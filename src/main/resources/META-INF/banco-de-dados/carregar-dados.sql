// Código utilizado para os pacotes: sistemausuarios e cadastrocliente
--insert into dominio (id, nome) values (1, 'Banco de Dados');
--insert into dominio (id, nome) values (2, 'LDAP');
--
--insert into usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (1, 'Cal Lightman', 'cal', '123', 1, sysdate());
--insert into usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (2, 'Gillian Foster', 'gillian', '123', 1, sysdate());
--insert into usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (3, 'Ria Torres', 'ria', '123', 1, sysdate());
--insert into usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (4, 'Eli Locker', 'eli', '123', 1, sysdate());
--insert into usuario (id, nome, login, senha, dominio_id, ultimoAcesso) values (5, 'Emily Lightman', 'emily', '123', 1, sysdate());
--
--insert into configuracao(usuario_id, receberNotificacoes, encerrarSessaoAutomaticamente) values (1, false, false);

//Código utilizado para o pacote sistemafuncionarios
--insert into funcionario (id, versao, nome, salario, bancoDeHoras, valorHoraExtra) values (1, 0, 'Cal Lightman', 5000, 20, 0);

//Código utilizado para o pacote blog
--insert into artigo(id, titulo, conteudo) values (1, 'Título do Artigo', 'Conteúdo do artigo');
--
--insert into dominio (id, nome) values (1, 'Banco de Dados');
--insert into dominio (id, nome) values (2, 'LDAP');

//Código utilizado para o metadados
insert into usuario (id, nome, login, senha, dominio_id, ultimo_acesso) values (1, 'Alexandre Afonso', 'alexandre', '123', 1, sysdate());