# REST-API
Denne modulen inneholder restserveren og REST-APIet til Foodie. Vi har valgt å bruke spring-boot for å implementere REST-APIet vårt. 
Spring-boot er et populært rammeverk for web-applikasjoner. Spring-boot gjør setup og konfigureringen av restserveren og REST-APIet enklere.


# Oppsett av restserveren
CookbookApplication - Starter restserveren, ved å kalle på SpringApplication.run
CookbookController - Har metoder for GET, POST, PUT OG DELETE. 
CookbookService - Definerer pathen vår. Håndterer operasjonene vi bruker i kontrolleren ved å kalle på funksjonene i core.


Vårt base endpoint er "/cookbook" og metodene i CookbookController er:
- GET: For å hente ut hele kokeboken ("/cookbook")
- POST: Legger til en oppskrift i kokeboken("/cookbook/{name}")
- PUT: Redigerer en oppskrift i kokeboken("/cookbook/{name}/edit")
- DELETE: Sletter en oppskrift i kokeboken("/cookbook/{name}")

```plantuml
client -> CookbookService: GET /cookbook
CookbookService --> client: {"name":"Cookbook","recipes": []}
client -> CookbookService: POST /cookbook/Pizza ~{"name":"Pizza","description":"The best homemade pizza","portions":2,"fav":false,"label":"Dinner","ingredients":[{"name":"Grandis","amount":2.0,"unit":"stk"},{"name":"Cola","amount":2.0,"unit":"stk"}
CookbookService --> client: true
client -> CookbookService: GET /cookbook
CookbookService --> client: {"name":"Cookbook","recipes":[{"name":"Pizza","description":"The best homemade pizza","portions":2,"fav":false,"label":"Dinner","ingredients":[{"name":"Grandis","amount":2.0,"unit":"stk"},{"name":"Cola","amount":2.0,"unit":"stk"}]}
client -> CookbookService: PUT /cookbook/Pizza/edit {"name":"Pizza","description":"The best pizza","portions":2,"fav":false,"label":"Dinner","ingredients":[{"name":"BigOne","amount":2.0,"unit":"stk"},{"name":"Pepsi","amount":2.0,"unit":"stk"}
CookbookService --> client: true
client -> CookbookService: GET /cookbook
CookbookService --> client: {"name":"Cookbook","recipes":[{"name":"Pizza","description":"The best pizza","portions":2,"fav":false,"label":"Dinner","ingredients":[{"name":"BigOne","amount":2.0,"unit":"stk"},{"name":"Pepsi","amount":2.0,"unit":"stk"}]}
```


# Hvordan starte restserveren
For å starte restserveren kjører du først `mvn install` på hele foodie mappen. Deretter går du inn i rest mappen (`cd rest`) og kjører kommandoen `mvn spring-boot:run`. Da er serveren skurdd på. For å terminere serveren holder du inne `ctrl + C`.

# Testing
Vi har tre tester for å teste funkjsonaliteten i REST-API klassene:
- CookbookControllerTest - enhetstest for cookbookController-klassen
- CookbookServiceTest - enhetstest for cookbookService-klassen
- IntegrationTest - integrasjonstest for å teste at sammenkoblngen mellom CookbookController og CookbokService
- Ende-til-ende-test ligger i ui-test-mappen.



