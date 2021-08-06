INSERT INTO Usuario( email, senha, data_cadastro)VALUES('email@email.com','123456',NOW());

INSERT INTO Categoria(nome) VALUES('Celulares');

INSERT INTO Caracteristica( nome, descricao ) VALUES('Bateria', '4000ma');

INSERT INTO Produto(nome, valor, quantidade_disponivel, descricao, categoria_id, data_cadastro, dono_id)
VALUES ('Smartphone Motorola', 2000.00, 5,'Smartphone samsung', 1, NOW(), 1);

INSERT INTO Opiniao_Produto( descricao, nota, titulo, produto_id, usuario_id )
VALUES('opiniao', 5, 'minha opiniao', 1, 1);

INSERT INTO Produto_Caracteristicas( produto_id, caracteristicas_id) VALUES( 1, 1 );