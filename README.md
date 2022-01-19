<h1>Sistema para Registro de Ponto online</h1>

<h2>Detalhes do Projeto</h2>

O protótipo disponibilizado aqui é a minha participação no grupo para a disciplina de projetos integrados 2.<br />

O que está disponivel aqui é: Parte do back-end desenvolvido usando spring-boot e mysql8.<br />

As features inclusas aqui são: implementação da  criação e acesso de contas, diferentes roles e login/logout.

<h2>Instruçôes para rodar o projeto:</h2>

Importar o projeto como maven na ide.<br />
Executar o projeto atraves da classe "SistemaPontoApplication".

<h4>DataBase:</h4>
Criar o Schema <code>db_sistema_ponto</code> no banco de dados.<br/>
Usuario e senha ao banco estão definidos como root.<br />
Olhar <a href="src/main/resources/application.properties">application.properties</a> para mais configs.

<h4>EndPoints disponiveis:</h4>
<ul>
<li> Para cadastro (verbo post) de funcionario atraves da url <code>/cadastro</code> com o modelo de json:</li>
<pre><code>{
    "username":"nome-para-login",
    "senha":"123",
    "nome":"nome",
    "authorities":["USER", "ADMIN"]
}</code></pre>

<li> Para login é a url (verbo post) <code>/login/process</code> com o modelo json:</li>
<pre><code>{
    "username":"nome-para-login",
    "password":"123"
}</code></pre>
Obs: Há um endpoint em <code>/login</code> com um formulario de login basico definido

<li> Para consulta dos propios dados é a url (verbo get) <code>/dados</code>, somente após feito o login.</li>

<li> Para alteração dos propios dados é a url (verbo put) <code>/dados/atualizar</code>, somente após feito o login.</li>
Necessario correção do metodo e da pesquisa realizada no banco, e definir quais dados poderão ser atualizados.

<li> Para consulta de todos os dados de todos os usuarios a url é (verbo get) <code>/adm/listarfuncionarios</code>.</li>
Necessaria authorities:["ADMIN"]
</ul>
As requisições para <code>/login/process</code> e <code>/cadastrar</code> estão permitidas a todos, enquanto nas outras é necessario estar logado.<br/>
