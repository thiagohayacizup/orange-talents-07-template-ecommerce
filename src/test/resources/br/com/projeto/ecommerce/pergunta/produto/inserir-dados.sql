INSERT INTO Usuario( email, senha, data_cadastro)VALUES('email@email.com','123456',NOW());

INSERT INTO Categoria(nome) VALUES('Celulares');

INSERT INTO Produto(nome, valor, quantidade_disponivel, descricao, categoria_id, data_cadastro, dono_id)
VALUES ('Smartphone Motorola', 2000.00, 5,'Smartphone samsung', 1, NOW(), 1);