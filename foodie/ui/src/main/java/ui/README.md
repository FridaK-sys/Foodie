# Kildekode for brukergrensesnittet

Vi har brukt JavaFX og FXML for å utarbeide brukergrensesnittet.

## Arkitektur

Vi har to varianter av applikasjonen. Den ene bruker lokale data lagret på fil, mens den andre bruker data håndtert gjennom et REST-API på en server. Derfor har vi to FXML filer (X og Y) og de tilhørende Kontroller klassene (LocalAppController og RestAppController). Siden kontrollerne har en del felles kode har vi laget en abstrakt klasse (AbstractController) som begge arver fra. Datatilgangen er spesifisert i egne klasser som RemoteCookbookAccess og LocalCookbookAccess. Remote og Local implementerer grensesnittet CookbookInterface. Forskjellen er at Local utfører operasjonene lokalt mens Remote gjør det ved å bruke REST-APIet.

Appklassen (CookbookApp) velger hvilken variant som skal brukes. Noe mer...
Dette er illustrert på klassediagrammet. 

![Kontrollere](controllers.png)

Merk at for å kjøre varianten som bruker REST-APIet så må rest serveren starte. Dette gjøres ved å kjøre kommandoen `mvn spring-boot:run` i rest mappen. Dette er forklart mer i README.md til rest. 

Følgende diagram viser samspillet mellom kontrollerne, datatilgangsobjektet, REST-tjenesten og diverse hjelpeobjekter ?? når man skal redigere en oppskrift i brukergrensesnittet ?


![Sekvensdiagram](sequence.png)
