# Kildekode for brukergrensesnittet

Vi har brukt JavaFX og FXML for å utarbeide brukergrensesnittet.

## Arkitektur for datalagring

Vi har to varianter av applikasjonen. Den ene bruker lokale data lagret på fil, mens den andre bruker data håndtert gjennom et REST-API på en server. Derfor har vi to FXML filer Main.fxml og MainRest.fxml og de tilhørende kontroller-klassene **LocalAppController** og **RestAppController**. Siden kontrollerne har en del felles kode har vi laget en abstrakt klasse (AbstractController) som begge arver fra. Datatilgangen er spesifisert i klassene **RemoteCookbookAccess** og **LocalCookbookAccess**, og disse implementerer grensesnittet **CookbookAccess**. Forskjellen er at Local utfører operasjonene lokalt mens Remote gjør det ved å bruke REST-APIet.

![Datatilgang](dataAccess.png)

Samspillet med kontrollerne i UI i illustrert på klassediagrammet under. 

![Kontrollere](controllers.png)

Merk at for å kjøre varianten som bruker REST-APIet så må rest serveren starte. Dette gjøres ved å kjøre kommandoen `mvn spring-boot:run` i rest mappen. Dette er forklart mer i README.md til rest. 

Følgende diagram viser samspillet mellom kontrollerne, datatilgangsobjektet, REST-tjenesten og diverse hjelpeobjekter ?? når man skal redigere en oppskrift i brukergrensesnittet ?


![Sekvensdiagram](sequence.png)
