Sistema para Registro de Ponto online

Importar o projeto como maven na ide.

Executar o projeto atraves da classe "SistemaPontoApplication"

Neste commit temos uma tabela funcionario que pode ser acessada na url: http://localhost:8080/h2-console
(--> necessario implementar o banco sql)

EndPoints disponiveis:

--> Para cadastro(Verbo post) de funcionario atraves da url http://localhost:8080/cadastrar com o seguinte modelo de json:
"
{
    "username":"nome-para-login",
    "senha":"123",
    "nome":"nome",
    "authorities":["USER", "ADMIN"]
}
"
--> Para login é a url (Verbo post) http://localhost:8080/login/process com o seguinte modelo json:
{
    "username":"nome-para-login",
    "password":"123"
}

--> Para consulta dos propios dados é a url (Verbo get) http://localhost:8080/dados, somente após feito o login.

--> Para alteração dos propios dados é a url (Verbo put) http://localhost:8080/dados/atualizar, somente após feito o login.
(--> Necessario correção do metodo e da pesquisa realizada no banco, e definir quais dados poderão ser atualizados.)

--> Para consulta de todos os dados de todos os usuarios a url é (Verbo get) http://localhost:8080/adm/listarfuncionarios.
(--> Necessaria authorities:["ADMIN"])

As requisições para /login/process e /cadastrar estão permitidas a todos, enquanto as outra é necessario estar com o header authorization.
O header authorization é recebido como resposta para /login/process.
