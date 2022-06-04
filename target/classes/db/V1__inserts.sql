USE `tarefas`;

insert  into `lista_tarefa`(`id`,`descricao`,`titulo`) values (1,'Tarefas de casa','Afazeres do Lar'),(2,'Tarefas do trabalho','Trabalho'),(3,'Lista de trabalhos da faculdade','Faculdade');

insert  into `tarefa`(`id`,`ativa`,`descricao`,`titulo`,`lista_tarefa_id`) values (2,'','Quando chegar do trabalho','Limpar o banheiro',1),(3,'','Chuveiro pifou, tem que ser consertado','Consertar chuveiro quebrado',1),(4,'\0','importante não esquecer de comprar café','Fazer mercado',1),(5,'','melhorar pipeline projeto acolhe rio','Melhorar pipeline',2),(6,'','algoritimo grafo','estrutura de dados',3);

insert  into `usuario`(`id`,`cpf`,`email`,`login`,`nome`,`senha`) values (1,'000555544455','teste@teste.com','usuarioteste','Usuário da Silva Teste','$2a$04$qP517gz1KNVEJUTCkUQCY.JzEoXzHFjLAhPQjrg5iP6Z/UmWjvUhq');

