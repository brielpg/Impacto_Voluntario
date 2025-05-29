------ INSERT USER ADMIN

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('cep', 'cidade', 'uf', 'bairro', 123, 'complemento');

INSERT INTO voluntarios (nome, email, cpf, telefone, senha, ativo, data_nascimento, data_cadastro, missoes_concluidas, vidas_impactadas, role, endereco_id, habilidade_outro)
VALUES (
  'Admin',
  'admin@email.com',
  '01234567890',
  '+55 (00) 12345-6789',
  '$2a$10$SahsILPSgtAb4euONvEgl.eDWPI7An5FqM/Jm/TLMg4eepEA3zpFC', -- senha: admin
  1, -- true
  TO_DATE('1980-06-15', 'YYYY-MM-DD'),
  TO_DATE('2025-05-01', 'YYYY-MM-DD'),
  0,
  0,
  'ADMIN',
  1,
  'Admin'
);

INSERT INTO voluntario_habilidades (voluntario_id, habilidade)
VALUES (1, 'OUTRO');


------ INSERT NECESSIDADES

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('00000-000', 'São Paulo', 'SP', 'Centro', 100, 'Sala 02');

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('00000-000', 'Rio de Janeiro', 'RJ', 'Copacabana', 200, 'Ap 05');

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('00000-000', 'Belo Horizonte', 'MG', 'Savassi', 300, NULL);

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('00000-000', 'Curitiba', 'PR', 'Batel', 400, 'Casa Fundos');


INSERT INTO necessidades (titulo, pessoas_afetadas, descricao, endereco_id, desastre, urgencia, status, data_envio, ativo, habilidade_outro, qtd_voluntarios)
VALUES ('Suporte Médico em Região Alagada', 10, 'Descricao braba Descricao braba Descricao braba', 2, 'ENCHENTE', 'ALTISSIMA', 'ANDAMENTO', TO_DATE('28-05-2025', 'DD-MM-YYYY'), 1, 'Primeiros Socorros', 0);

INSERT INTO necessidades (titulo, pessoas_afetadas, descricao, endereco_id, desastre, urgencia, status, data_envio, ativo, habilidade_outro, qtd_voluntarios)
VALUES ('Resgate em Área de Terremoto', 20, 'Descricao braba Descricao braba Descricao braba', 3, 'TERREMOTO', 'ALTA', 'ANDAMENTO', TO_DATE('25-05-2025', 'DD-MM-YYYY'), 1, NULL, 0);

INSERT INTO necessidades (titulo, pessoas_afetadas, descricao, endereco_id, desastre, urgencia, status, data_envio, ativo, habilidade_outro, qtd_voluntarios)
VALUES ('Distribuição de Alimentos em Região de Seca', 15, 'Descricao braba Descricao braba Descricao braba', 4, 'SECA', 'MEDIA', 'ANDAMENTO', TO_DATE('20-05-2025', 'DD-MM-YYYY'), 1, 'Organização de Estoques', 0);

INSERT INTO necessidades (titulo, pessoas_afetadas, descricao, endereco_id, desastre, urgencia, status, data_envio, ativo, habilidade_outro, qtd_voluntarios)
VALUES ('Suporte Psicológico para Vítimas de Incêndio', 5, 'Descricao braba Descricao braba Descricao braba', 5, 'INCENDIO', 'BAIXA', 'ANDAMENTO', TO_DATE('10-05-2025', 'DD-MM-YYYY'), 1, NULL, 0);


INSERT INTO necessidade_habilidades (necessidade_id, habilidade) VALUES (1, 'ENFERMEIRO');
INSERT INTO necessidade_habilidades (necessidade_id, habilidade) VALUES (2, 'RESGATE');
INSERT INTO necessidade_habilidades (necessidade_id, habilidade) VALUES (3, 'COZINHA');
INSERT INTO necessidade_habilidades (necessidade_id, habilidade) VALUES (4, 'EDUCACAO');


------ INSERT SOLICITACOES DE AJUDA

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('00000-000', 'Recife', 'PE', 'Boa Viagem', 123, NULL);

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('00000-000', 'Manaus', 'AM', 'Adrianópolis', 456, NULL);

INSERT INTO enderecos (cep, cidade, uf, bairro, numero, complemento)
VALUES ('00000-000', 'Fortaleza', 'CE', 'Meireles', 789, NULL);


INSERT INTO solicitacoes_ajudas (nome_solicitante, email_solicitante, telefone_solicitante, descricao, data_envio, ativo, data_acontecimento, pessoas_afetadas, endereco_id, desastre, urgencia, status, outros)
VALUES ('João Silva', 'joao@email.com', '+00 (00) 00000-0000', 'Necessidade de resgate após deslizamento', TO_DATE('29-05-2025', 'DD-MM-YYYY'), 1, TO_DATE('28-05-2025', 'DD-MM-YYYY'), 8, 6, 'DESLIZAMENTO', 'ALTISSIMA', 'ANALISE', NULL);

INSERT INTO solicitacoes_ajudas (nome_solicitante, email_solicitante, telefone_solicitante, descricao, data_envio, ativo, data_acontecimento, pessoas_afetadas, endereco_id, desastre, urgencia, status, outros)
VALUES ('Maria Souza', 'maria@email.com', '+11 (11) 11111-1111', 'Pedido de apoio médico para vítimas de vendaval', TO_DATE('29-05-2025', 'DD-MM-YYYY'), 1, TO_DATE('27-05-2025', 'DD-MM-YYYY'), 12, 7, 'VENDAVAL', 'ALTA', 'ANALISE', 'Necessário envio de primeiros socorros');

INSERT INTO solicitacoes_ajudas (nome_solicitante, email_solicitante, telefone_solicitante, descricao, data_envio, ativo, data_acontecimento, pessoas_afetadas, endereco_id, desastre, urgencia, status, outros)
VALUES ('Carlos Lima', 'carlos@email.com', '+22 (22) 22222-2222', 'Distribuição de alimentos para vítimas de seca', TO_DATE('29-05-2025', 'DD-MM-YYYY'), 1, TO_DATE('20-05-2025', 'DD-MM-YYYY'), 20, 8, 'SECA', 'MEDIA', 'ANALISE', 'Ajuda logística para transporte de alimentos');


INSERT INTO solicitacoes_ajudas_ajuda_requerida (solicitacao_id, ajuda_requerida) VALUES (1, 'RESGATE_PESSOAS');
INSERT INTO solicitacoes_ajudas_ajuda_requerida (solicitacao_id, ajuda_requerida) VALUES (2, 'AJUDA_MEDICA');
INSERT INTO solicitacoes_ajudas_ajuda_requerida (solicitacao_id, ajuda_requerida) VALUES (3, 'ALIMENTOS_AGUA');