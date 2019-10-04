CREATE TABLE estado (
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estado (codigo, nome) VALUES(1, 'Luanda');
INSERT INTO estado (codigo, nome) VALUES(2, 'Bengo');
INSERT INTO estado (codigo, nome) VALUES(3, 'Zaire');
INSERT INTO estado (codigo, nome) VALUES(4, 'Uige');
INSERT INTO estado (codigo, nome) VALUES(5, 'Cabinda');

CREATE TABLE cidade (
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
codigo_estado BIGINT(20) NOT NULL,
FOREIGN KEY (codigo_estado) REFERENCES estado(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (1, 'Cazenga', 1);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (2, 'Cacuaco', 1);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (3, 'Belas', 1);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (4, 'Caxito', 2);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (5, 'Soyo', 3);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (6, 'Maquela', 4);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (7, 'Belize', 5);


ALTER TABLE pessoa DROP COLUMN cidade;
ALTER TABLE pessoa DROP COLUMN estado;
ALTER TABLE pessoa ADD COLUMN codigo_cidade BIGINT(20);
ALTER TABLE pessoa ADD CONSTRAINT fk_pessoa_cidade FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo);

UPDATE pessoa SET codigo_cidade=2;