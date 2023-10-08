DROP TABLE produtos IF EXISTS;
CREATE TABLE produtos (codigo numeric,
                    descricao VARCHAR(255),
                    preco_unitario numeric,
                    quantidade_estoque int,
                    PRIMARY KEY(codigo)
                    );