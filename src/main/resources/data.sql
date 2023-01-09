INSERT INTO USUARIO(nome, email, senha) VALUES('usuario', 'usuario@email.com', 
'$2a$10$Lxy6BlS/F2mnU6RjDguYvOZQe2momQ2LRiY24ZIQkwVz16WoCvmDq');

INSERT INTO DESPESAS(descricao, valor, data, categoria) VALUES
	('trakinas', 3.59, '2022-09-05', 'ALIMENTACAO'), 
    ('rapadura', 1.0, '2022-09-05', 'ALIMENTACAO'),
    ('rapadura', 5.0, '2023-09-05', 'ALIMENTACAO'),
    ('plano de saude', 1000, '2022-09-10', 'SAUDE');
    
INSERT INTO RECEITAS(descricao, valor, data) VALUES('presente da vó', 1000.0, '2022-09-05 10:00:00');
INSERT INTO RECEITAS(descricao, valor, data) VALUES('mesada', 2000.0, '2022-09-05 10:00:00');