# Kildekode for brukergrensesnittet

Vi har brukt JavaFX og FXML for å utarbeide brukergrensesnittet.

## Arkitektur

Vi har to varianter av applikasjonen. Den ene bruker lokale data lagret på fil, mens den andre bruker data håndtert gjennom et REST-API på en server. Derfor har vi to FXML filer Main.fxml og MainRest.fxml med tilhørende kontroller-klasser **LocalAppController** og **RestAppController**, som arver fra den abstrakte klassen **AbstractController**. Datatilgangen er spesifisert i klassene **RemoteCookbookAccess** og **LocalCookbookAccess**, og disse implementerer grensesnittet **CookbookAccess**. Forskjellen er at Local utfører operasjonene lokalt mens Remote gjør det ved å bruke REST-APIet.

![Datatilgang](dataAccess.png)

Samspillet med kontrollerne til applikasjonen er illustrert på klassediagrammet under. 

![Kontrollere](controllers.png)

Følgende diagram viser samspillet mellom kontrollerne, datatilgangsobjektet, REST-tjenesten og diverse hjelpeobjekter ?? når man skal redigere en oppskrift i brukergrensesnittet ?

![Sekvensdiagram](sequence.png)
