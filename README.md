[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-908a85?logo=gitpod)](https://gitpod.io/#https://github.com/.../...)

# Foodie-prosjektet 

Dette prosjektet er tilknyttet emnet IT1901 Informatikk prosjektarbeid I.

## Om appen
Foodie lar deg opprette din egen kokebok og legge til oppskrifter i denne. Hver oppskrift kan redigeres ved å legge til eller fjerne ingredienser, samt skalere antall porsjoner. Dessuten kan man sortere oppskrifter basert på merkelapper og angi en oppskrift som favoritt. Appen er ment til å brukes som en digital kokebok for å bidra til en lettere matlagingsopplevelse i hverdagen. 

## Oppsett av prosjektet 
- Selve kodingsprosjektet ligger i mappen `foodie`. Her er prosjektet delt inn i tre moduler: core, ui og restapi. 
- Kjernelogikken og filbehandling, samt tester til disse klassene ligger i mappen `core`. Logikken er skrevet i Java og tilknyttede tester med JUnit 5. Vi har valgt å bruke Jackson-biblioteket for å lese og skrive JSON objekter til fil. 
- Design av brukergrensesnittet er laget med JavaFX og kontroller-klassene som styrer dette ligger i `ui`.
- REST-APIet og restserver ligger i mappen `rest`, og implementert med Spring Boot. 
- Brukerhistorier som viser appens hovedfunksjonalitet ligger i mappen `foodie`. 
- Dokumentasjonen for hver release ligger i mappen `docs`.

## Bygging og kjøring av prosjektet
Prosjektet bruker Maven til bygging og kjøring. 
For å kjøre prosjektet, skriv følgende kommandoer i terminalen. Dette kjører også alt av tester. 
- `cd foodie` 
- `mvn install` 
- `mvn -pl ui javafx:run`


## Verktøy tilknyttet kodekvalitet
Vi har brukt følgende verktøy tilknyttet kodekvalitet:

- **Checkstyle** - sjekker mer overfladiske og stilmessige egenskaper til koden som tekst.
- **Spotbugs** - analyserer koden for vanlige feil.
- **Jacoco** - samler inn og presenterer informasjon og testdekningsgrad.


## Arbeidsvaner og arbeidsflyt
Vi har brukt Scrum som en del av vår utviklingsprosess. Scrum er et rammeverk for å støtte smidig utvikling av informasjonssystemer. 
Her struktureres utviklingen i sykler kalt sprinter. For hvert møte har vi satt et sprint-goal for dagen, og planlagt arbeidsoppgaver i henhold til dette. Her ble det også foretatt en vurdering av tidligere arbeid. Vi har møttes minst to ganger i uken for å jobbe sammen med prosjektet. 
Da har vi vekslet mellom parprogrammering og programmering individuelt. 

Vi har hatt en god og strukturert arbeidsflyt underveis i utviklingsprosessen. Hver milestone i prosjektet er tilknyttet en sprint. For hver sprint har vi benyttet boards på Gitlab for å danne en oversikt over alle issues (kodingsoppgaver) tilknyttet denne sprinten. Her kan man se hvilke issues som gjenstår å løses, er under arbeid og er løst. Hvert issue ble merket med passende labels og tildelt et eller flere av gruppemedlemmene. Når et gruppemedlem tok på seg et issue, opprettet de en branch tilknyttet dette issuet. Etter at et issuet var løst, ble det opprettet en merge request fra tilknyttet branch til master. Vi har brukt code review, slik at et av de andre gruppemedlememmene så over merge requesten og ga en kort tilbakemelding. Dersom vedkomne var fornøyd med endringene, ble branchen merget med master. 




