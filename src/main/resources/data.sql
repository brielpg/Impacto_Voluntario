INSERT INTO enderecos (id, cep, cidade, uf, bairro, numero, complemento)
VALUES (1, 'cep', 'cidade', 'uf', 'bairro', 123, 'complemento');

INSERT INTO voluntarios (id, nome, email, cpf, telefone, senha, ativo, data_nascimento, data_cadastro, missoes_concluidas, role, endereco_id, habilidade_outro)
VALUES (
  1,
  'Admin',
  'admin@email.com',
  '01234567890',
  '+55 00 12345-6789',
  '$2a$10$SahsILPSgtAb4euONvEgl.eDWPI7An5FqM/Jm/TLMg4eepEA3zpFC', -- senha: admin
  1,
  TO_DATE('1980-06-15', 'YYYY-MM-DD'),
  TO_DATE('2025-05-01', 'YYYY-MM-DD'),
  0,
  'ADMIN',
  1,
  NULL
);

INSERT INTO voluntario_habilidades (voluntario_id, habilidade)
VALUES (1, 'MEDICO');

INSERT INTO voluntario_habilidades (voluntario_id, habilidade)
VALUES (1, 'ENFERMEIRO');

INSERT INTO voluntario_habilidades (voluntario_id, habilidade)
VALUES (1, 'LOGISTICA');