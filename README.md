# WineShop
Sistema básico para controle de vendas de vinho

## Tecnologias utilizadas
- Java 8+
- [Jersey](https://jersey.java.net/) (JAX-RS)
- [MentaBean](http://mentabean.soliveirajr.com/) (ORM)
- [MentaContainer](http://mentacontainer.soliveirajr.com/) (IoC)
- [HikariCP](https://brettwooldridge.github.io/HikariCP/) (Pool de conexões)
- [Jackson](https://github.com/FasterXML/jackson) (Provedor JSON)
- [Jetty](http://www.eclipse.org/jetty/) (Servidor web embarcado)
- [H2 Database](http://www.h2database.com/) (Banco de dados)
- [Swagger](http://swagger.io/) (Documentação interativa)

### Para os testes JUnit:
- Grizzly (como servidor web embarcado)
- H2 Database (banco de dados em memória)
- Jersey Client (cliente REST)

## Getting Started
Para rodar o projeto, clone este repositório e execute o seguinte comando:
```
mvn jetty:run
```
A aplicação estará rodando em `http://localhost:8080/wineshop`
Acesse também a documentação interativa da API: `http://localhost:8080/wineshop/docs`
