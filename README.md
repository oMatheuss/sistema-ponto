Sistema para Registro de Ponto online

Requisitos: ter alguma ide para importar o projeto e ter o git instalado.

Importar o projeto como maven na ide.


Executar o projeto atraves da classe "SistemaPontoApplication"

Neste primeiro commit temos uma tabela funcionario que pode ser acessada na url: http://localhost:8080/h2-console

E alguns endPoints disponiveis como o de cadastro(Verbo post) de funcionario atraves da url http://localhost:8080/cadastro com o seguinte modelo de json:
"
{"username":"matheus","senha":"123","nome":"Matheus Silva", "email":"',"authorities":["ADMIN", "USER"]}
"
outro endpoint é o de consulta na seguinte url (verbo get) http://localhost:8080/adm/ ou http://localhost:8080/user/, este busca todos cadastrados, (verbo get) http://localhost:8080/adm/busca/{id}
este busca por codigo
