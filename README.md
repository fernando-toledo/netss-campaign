# Netss supporter

Serviço responsável pelas campanhas

## Com profile de desenvolvimento

Para rodar em modo dev primeiro build o projeto:

    ./mvnw clean package -Pdev -Dmaven.test.skip=true

Depois execute o jar:

    java -jar <NOME_APLICATIVO>.jar --spring.profiles.active=dev

## Com profile de prod

    ./mvnw clean package -Pprod -Dmaven.test.skip=true

Logo em seguida execute o app:

    java -jar <NOME_APLICATIVO>.jar --spring.profiles.active=dev


## Testing

    ./mvnw clean test

### Other tests


## Using Docker to simplify development (optional)

É possível executar todo o ambiente com docker

Apenas a infra:
   
    docker-compose -f src/main/docker/app_dev.yml up -d

Com o app também:

Primeiro crie o release do docker com o comando 
    
    ./mvnw verify -Pprod (ou -Pdev) dockerfile:build
    
E depois execute o compose: 
  
    docker-compose -f src/main/docker/app_dev.yml up -d


## Continuous Integration (optional)

Jenkins file 

## Postman

Uma colection do postman está disponível no link:

+ <https://www.getpostman.com/collections/f89cbb99398530c503f2>
    
## Brokers

Vá no endereço <http://localhost:15672/> para verificar filas, exchanges do rabbit

## Database

Existem dois bancos disponíveis:

+ H2: utilizado pelo profile de DEV 
+ PostgreSQL pelo profile de PROD 
    + port: 5432
    + user: netss_campaign


Ambos são versionados pelos mesmo arquivos liquibase na pasta

    src/main/resources/db/changelog
