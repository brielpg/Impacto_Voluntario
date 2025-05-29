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