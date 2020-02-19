// Código utilizado para os pacotes: sistemausuariose cadastrocliente
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

insert into funcionario (id, versao, nome, salario, bancoDeHoras, valorHoraExtra) values (1, 0, 'Cal Lightman', 5000, 20, 0);
