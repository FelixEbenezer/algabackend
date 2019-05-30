CREATE TABLE lancamento
(
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(50) NOT NULL,
data_vencimento DATE NOT NULL,
data_pagamento DATE,
valor DECIMAL(10,2) NOT NULL,
observacao VARCHAR(100),
tipo VARCHAR(20) NOT NULL,
codigo_categoria BIGINT(20) NOT NULL,
codigo_pessoa BIGINT(20) NOT NULL,
FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
FOREIGN KEY (codigo_pessoa) REFERENCES pessoa (codigo)

) ENGINE=Innodb DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Salário mensal', '2017-06-10',null,6500.00, 'Distribuição de Lucros', 'RECEITA', 1,1);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Bahamas', '2017-12-22','2017-12-22',100.32, null, 'DESPESA', 2,2);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Top Club', '2019-04-10',null,120, null, 'RECEITA', 3,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('CEMIG', '2019-02-20','2019-02-20',110.44, 'Geração', 'RECEITA', 1,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('DMAE', '2017-06-09',null,200.30, null, 'DESPESA', 3,2);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Salário mensal', '2017-06-10',null,6500.00, 'Distribuição de Lucros', 'RECEITA', 1,1);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Bahamas', '2017-12-22','2017-12-22',100.32, null, 'DESPESA', 2,2);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Top Club', '2019-04-10',null,120, null, 'RECEITA', 3,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('CEMIG', '2019-02-20','2019-02-20',110.44, 'Geração', 'RECEITA', 1,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('DMAE', '2017-06-09',null,200.30, null, 'DESPESA', 3,2);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Salário mensal', '2017-06-10',null,6500.00, 'Distribuição de Lucros', 'RECEITA', 1,1);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Bahamas', '2017-12-22','2017-12-22',100.32, null, 'DESPESA', 2,2);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Top Club', '2019-04-10',null,120, null, 'RECEITA', 3,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('CEMIG', '2019-02-20','2019-02-20',110.44, 'Geração', 'RECEITA', 1,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('DMAE', '2018-08-09',null,200.30, null, 'DESPESA', 3,2);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Salário mensal', '2017-06-10',null,6500.00, 'Distribuição de Lucros', 'RECEITA', 1,1);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Bahamas', '2018-12-22','2017-12-22',100.32, null, 'DESPESA', 2,2);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Top Club', '2019-04-10',null,120, null, 'RECEITA', 3,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('CEMIG', '2019-02-20','2019-02-20',110.44, 'Geração', 'RECEITA', 1,3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('DMAE', '2019-06-09',null,200.30, null, 'DESPESA', 3,2);