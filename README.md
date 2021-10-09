Sistema para Registro de Ponto online

Requisitos: ter alguma ide para importar o projeto e ter o git instalado.

Importar o projeto como maven na ide.


Executar o projeto atraves da classe "SistemaPontoApplication"

Neste primeiro commit temos uma tabela funcionario que pode ser acessada na url: http://localhost:8080/h2-console

E alguns endPoints disponiveis como o de cadastro(Verbo post) de funcionario atraves da url http://localhost:8080/cadastro com o seguinte modelo de json:\
"
{"username":"matheus","senha":"123","nome":"Matheus Silva", "email":"","authorities":["ADMIN", "USER"]}
"
\
Para login na url http://localhost:8080/login/process \
"
{"username":"matheus","password":"123"}
"

outro endpoint é o de consulta na seguinte url:\
(verbo get) http://localhost:8080/adm/ ou http://localhost:8080/user/, este busca pelo do usuario logado\
(verbo get) http://localhost:8080/adm/busca/{id} este busca por codigo


Após cadastro na url /cadastro e login na /login/process, dependendo do atributo authorities será o acesso a /user e /adm.\
Após login será recibido um token (Authorization) que devera ser enviado manualmente de volta no header da requisição.\
Só assim será possicel acessar as urls de consulta.
