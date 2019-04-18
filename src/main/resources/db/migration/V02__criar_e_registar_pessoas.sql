CREATE TABLE pessoa (
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
ativo BOOLEAN NOT NULL,
logradouro VARCHAR(50),
numero VARCHAR(20),
complemento VARCHAR(20),
bairro VARCHAR(20),
cep VARCHAR(20),
cidade VARCHAR(20),
estado VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Felix', true, 'Bols', '00111','20b','cazenga','51','vitry','Grand Est');

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Ebenezer', false, '', '00222','30b','cazenga','51','vitry','Grand Est');

INSERT INTO pessoa (nome, ativo,  bairro, cep, cidade, estado) 
values ('Livni', true, 'Europe','51','Reims','Grand Est');

INSERT INTO pessoa (nome, ativo,  bairro, cep, cidade, estado) 
values ('Barack', false, 'Centre Ville','51','Reims','Grand Est');

INSERT INTO pessoa (nome, ativo, numero, bairro, cep, cidade, estado) 
values ('Ngongo', false, '00444','cazenga','51','vitry','Grand Est');


