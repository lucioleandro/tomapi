-- ==========================================================
-- Author: Lucio Leandro
-- Update date: 05/06/2022
-- Description: Estrutura Inicial do TOM
-- ==========================================================


-- Insert table `role`

INSERT INTO role (name, description, uuid)
VALUES
('Developer', 'Exerce a função de implementar e dar manutenção em funcionalidades do sistema', UUID()),
('Product Owner', 'Responsável pelo resultado do projeto', UUID()),
('Tester', 'Responsável por testar as funcionalidades do sistema afim de atestar a qualidade e bom funcionamento', UUID());


