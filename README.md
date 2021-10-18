
# product-ms
Spring Boot microservice

Java 11 [AdoptOpenJDK - binários OpenJDK Open Source, pré-compilados](https://adoptopenjdk.net/)
Maven 3.8.1
Docker 20.10.7
PostgreSQL 13
### Projeto

Caso não vá usar docker, não esqueça adicionar a url do banco no application.properties, no projeto ela está sendo passada no docker-compose.yml

Buildar o projeto
> mvn clean package

### Docker compose

> docker build -t product-ms-app .
> docker-compose up

