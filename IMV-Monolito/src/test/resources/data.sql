-- Necessidades
INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento) VALUES
('12345-678', 'CidadeTeste', 'ST', 'BairroTeste', 123, 'ComplementoTeste');

INSERT INTO necessidades (titulo, descricao, pessoas_afetadas, desastre, urgencia, status, data_envio, ativo, endereco_id)
VALUES ('Ajuda Urgente', 'Precisamos de apoio imediato', 20, 'INCENDIO', 'ALTA', 'ANALISE', '2025-05-30', true, 1);


-- Solicitacoes
INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento) VALUES
('12345-678', 'CidadeTeste', 'ST', 'BairroTeste', 123, 'ComplementoTeste');

INSERT INTO solicitacoes_ajudas
(nome_solicitante, email_solicitante, telefone_solicitante, descricao, data_envio, ativo, data_acontecimento, pessoas_afetadas, endereco_id, desastre, urgencia, status, outros)
VALUES
('Requester', 'requester@example.com', '123456789', 'Descrição da solicitação', '2025-05-30', TRUE, '2025-05-29', 10, 2, 'INCENDIO', 'ALTA', 'ANALISE', 'Ajuda adicional necessária');

INSERT INTO solicitacoes_ajudas_ajuda_requerida (solicitacao_id, ajuda_requerida) VALUES (1, 'OUTROS');


-- Voluntarios
INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento) VALUES
('12345-678', 'CidadeTeste', 'ST', 'BairroTeste', 123, 'ComplementoTeste');

INSERT INTO voluntarios (nome, email, cpf, telefone, senha, ativo, data_nascimento, data_cadastro, missoes_concluidas, vidas_impactadas, role, endereco_id, habilidade_outro)
VALUES ('Tester test', 'test@example.com', '12345678901', '+55 (00) 98765-4321', '$2a$10$SahsILPSgtAb4euONvEgl.eDWPI7An5FqM/Jm/TLMg4eepEA3zpFC', TRUE, '1990-01-01', '2025-05-01', 0, 0, 'USER', 3, 'Other skill');