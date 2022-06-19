-- ==========================================================
-- Author: Lucio Leandro
-- Update date: 05/06/2022
-- Description: Estrutura Inicial do TOM
-- ==========================================================


-- Insert table `role`

INSERT INTO role (name, description, uuid)
VALUES
('Developer', 'Performs the function of implementing and maintaining system functionalities', 'eee2bf76-efeb-11ec-996f-0242ac190002'),
('Product Owner', 'Responsible for the outcome of the project', UUID()),
('Tester', 'Responsible for testing the functionality of the system in order to attest to the quality and proper functioning', UUID());


