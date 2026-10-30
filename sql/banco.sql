-- =====================================================================
-- Wheeler, Rawson & Co. - Script de criação do banco de dados
-- Uso (no terminal):
--   psql -U postgres -f sql/banco.sql
-- =====================================================================

-- Força o psql a ler este arquivo como UTF-8. Sem isto, no Windows o psql usa
-- o codepage do terminal (Windows-1252) e os acentos são gravados quebrados.
\encoding UTF8

DROP DATABASE IF EXISTS wheelerrawson;
CREATE DATABASE wheelerrawson ENCODING 'UTF8';

\c wheelerrawson

-- a conexão foi refeita pelo \c: garante UTF-8 de novo
\encoding UTF8

CREATE TABLE usuario (
    id            SERIAL       PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    endereco      VARCHAR(200) NOT NULL,
    email         VARCHAR(100) NOT NULL,
    login         VARCHAR(50)  NOT NULL UNIQUE,
    senha         VARCHAR(100) NOT NULL,
    administrador BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE categoria (
    id        SERIAL       PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE produto (
    id           SERIAL           PRIMARY KEY,
    descricao    VARCHAR(100)     NOT NULL,
    detalhes     VARCHAR(300),
    preco        DOUBLE PRECISION NOT NULL CHECK (preco >= 0),
    foto         VARCHAR(255),
    quantidade   INTEGER          NOT NULL CHECK (quantidade >= 0),
    categoria_id INTEGER          NOT NULL REFERENCES categoria (id)
);

CREATE TABLE venda (
    id         SERIAL    PRIMARY KEY,
    data_hora  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_id INTEGER   NOT NULL REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE venda_produto (
    venda_id   INTEGER          NOT NULL REFERENCES venda (id) ON DELETE CASCADE,
    produto_id INTEGER          NOT NULL REFERENCES produto (id),
    preco      DOUBLE PRECISION NOT NULL,
    quantidade INTEGER          NOT NULL CHECK (quantidade > 0),
    PRIMARY KEY (venda_id, produto_id)
);

-- ---------------------------------------------------------------------
-- Dados iniciais
-- ---------------------------------------------------------------------

-- Administrador (inserido direto no banco, conforme o enunciado)
INSERT INTO usuario (nome, endereco, email, login, senha, administrador) VALUES
    ('Leopold Strauss', 'Acampamento de Horseshoe Overlook', 'admin@wheelerrawson.com', 'admin', 'admin123', TRUE);

INSERT INTO categoria (descricao) VALUES
    ('Armas e Munições'),      -- 1
    ('Remédios e Tônicos'),    -- 2
    ('Vestuário'),             -- 3
    ('Provisões'),             -- 4
    ('Equipamentos');          -- 5

INSERT INTO produto (descricao, detalhes, preco, foto, quantidade, categoria_id) VALUES
    ('Revólver de Ação Dupla', 'Confiável, rápido e letal. A melhor engenharia para a sua proteção nas fronteiras implacáveis.', 65.00, NULL, 3, 1),
    ('Tônico Milagroso', 'Restaura a saúde, a energia e a concentração num piscar de olhos. Receita rigorosamente patenteada!', 3.50, NULL, 40, 2),
    ('Pomada para Cavalo', 'Alivia o cansaço e melhora a pelagem do seu fiel companheiro após longas viagens.', 1.75, NULL, 25, 2),
    ('Botas de Couro Resistente', 'O par perfeito para cavalgar pelas planícies. Recomenda-se o uso regular de graxa ou pasta para maior durabilidade.', 14.50, NULL, 12, 3),
    ('Feijões Enlatados', 'A refeição clássica do acampamento. Sustento garantido para longas noites ao relento.', 1.20, NULL, 100, 4),
    ('Fumo de Mascar Premium', 'O melhor tabaco de Kentucky. Previne a fadiga e mantém a mente afiada durante a guarda noturna.', 2.00, NULL, 30, 4),
    ('Café Moído na Hora', 'Imprescindível para as manhãs frias de Ambarino. Aroma intenso e sabor forte.', 1.50, NULL, 60, 4),
    ('Óleo de Arma', 'Mantenha o seu equipamento limpo e sem ferrugem para evitar falhas no momento em que mais precisar.', 1.50, NULL, 50, 5),
    ('Biscoitos Salgados', 'Ração seca e durável. Excelente para guardar na sela durante expedições prolongadas.', 0.50, NULL, 80, 4),
    ('Chapéu de Vaqueiro', 'Aba larga contra o sol do deserto de New Austin.', 9.00, NULL, 0, 3);
