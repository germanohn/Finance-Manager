USE `ledger_test`;

DROP TABLE IF EXISTS `despesas`;
CREATE TABLE `despesas` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `valor` double NOT NULL,
  `data` datetime NOT NULL,
  `categoria` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO DESPESAS(descricao, valor, data, categoria) VALUES
	('trakinas', 3.59, '2022-09-05 10:00:00', 'ALIMENTACAO'), 
    ('rapadura', 1.0, '2022-09-05 10:00:00', 'ALIMENTACAO');


select * from despesas;