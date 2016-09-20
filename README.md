# WineShop
Back-end REST do sistema básico para controle de vendas de vinho. Acesse o [front-end aqui](https://github.com/klutzer/wineshop-angularjs).

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
Então as chamadas REST poderão ser requisitadas a partir de `http://localhost:8080/wineshop/api/...`

Para saber quais as chamadas podem ser executadas, acesse a documentação interativa da API: `http://localhost:8080/wineshop/docs`

## Por que utilizar MentaBean ao invés de JPA/Hibernate?
Geralmente quando começamos um projeto em Java que envolve comunicação com algum banco de dados relacional, pensamos logo - muitas vezes antes até da escolha do próprio banco de dados - na utilização de ORMs JPA, tal como o Hibernate, certamente a implementação mais conhecida. A escolha do ORM para este projeto não se baseou em frameworks JPA, e vou tentar argumentar o porquê, expondo os pontos que considero importantes a serem analisados na escolha de um ORM:

- **Domínio sobre o framework e seu comportamento:** O MentaBean é um framework ORM construído todo em cima do JDBC, porém não há a necessidade de criar códigos repetitivos, como para controle de índices de Statements ou iteração de ResultSets. Quem define o comportamento do framework, tal como o retorno de cada query executada, eager/lazy loadings, condições dinâmicas, etc.. é o desenvolvedor, e essas informações não podem ser estáticas (XML, Annotations, Properties...). Atualmente, muito do que é executado "por baixo dos panos", com JPA, não é conhecido por quem o utiliza, sendo que muitas vezes o framework que implementa JPA acaba executando códigos que resultam em comportamentos inesperados para o resultado final, como é o caso dos eager loadings. Quando envolvemos arquiteturas desacopladas, como é o caso deste projeto, a tendência é complicar ainda mais.
- **O (não) uso de XML/Annotations:** Ao utilizar Hibernate, por exemplo, ficamos reféns da utilização de XMLs ou Annotations que poluem muito o código com peculiaridades específicas da vinculação das entidades no banco de dados. Estas informações devem ser dinâmicas e não deveriam ficar nas entidades de negócio. É aí que entra o próximo ponto.
- **Configuração programática:** A configuração pode ser simples, orientada a objetos, com auxílio de *fluent APIs* para auxiliar o mapeamento de cada entidade no banco de dados, e devem ficar onde o desenvolvedor entender como melhor localização, de acordo com a estrutura do projeto. Além disso, seria interessante se pudéssemos configurar cada campo sem a utilização de *Strings hardcoded*, que dificultam a refatoração do código.
- **Liberdade com SQLs:** O desenvolvedor deve decidir se utilizar características do banco de dados com o qual trabalha é uma vantagem ou não. Triggers, funções, ou utilização de linguagem nativa são sempre mais performáticas a nível de banco de dados do que quando deixamos o ORM fazer isso por nós. O framework ORM utilizado deve poder conciliar o melhor dos dois mundos, permitindo a integração com o banco de dados com poucas linhas de código mas também flexibilizar a utilização de ferramentas nativas do banco de dados sempre que o desenvolvedor assim o quiser.
- **Código (não) intrusivo:** Uma entidade de negócio deve existir sem códigos do framework dentro dela. Isso permite a modularização do projeto. Ou seja, podemos ter um projeto *common* contendo as regras de negócio e especificações básicas do projeto, independentes do back ou do front-end.
- **Migração gradual:** Com o MentaBean, por exemplo, é possível a utilização de código híbrido, ou seja, podemos usar o ORM apenas em uma parte do projeto, enquanto códigos legados continuam operando. Isso permite uma migração gradual, sem necessidade de reescrever toda a arquitetura de uma só vez.
- **Curva de aprendizado:** Para quem entende SQL, a curva de aprendizado para adoção de um framework deve ser pequena, e não deve atentar a muitos detalhes do framework, mas sim deixar o desenvolvedor ocupado com especificidades da solução que está desenvolvendo, e não do framework ORM que está usando.

Após analisar os itens acima, optou-se pela utilização do MentaBean. Porém, isso não quer dizer que JPA não deve ser utilizado ou que é uma má alternativa, a questão neste caso é apenas demonstrar uma visão um pouco mais simplista do problema que um ORM deve tentar resolver.

Outras informações [aqui](https://virgo47.wordpress.com/2014/10/09/jpa-is-it-worth-it-horror-stories-with-eclipselink-and-hibernate/) e [aqui](http://mentabean.soliveirajr.com/mtw/Page/Intro/pt/mentabean-introducao)