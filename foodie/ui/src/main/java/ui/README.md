# Kildekode for brukergrensesnittet

Vi har brukt JavaFX og FXML for å utarbeide brukergrensesnittet.
Brukergrensesnittet består av en kokebok i form av et **Cookbook**-objekt med en liste av oppskrifter. Oppskriftene viser inneholdet i et **Recipe**-objekt. Bukeren har mulighet til å legge til og fjerne oppskrifter samt redigere disse. Hver oppskrift inneholder en liste av ingredienser representert ved et **Ingredient**-objekt.  

## Arkitektur

Vi har to varianter av applikasjonen. Den ene bruker lokale data lagret på fil, mens den andre benytter data håndtert gjennom REST-APIet på en server. Derfor har vi to kontroller-klasser **LocalAppController** og **RestAppController**. Datatilgangen er spesifisert i klassene **RemoteCookbookAccess** og **LocalCookbookAccess**, og disse implementerer grensesnittet **CookbookAccess**. 

![Datatilgang](dataAccess.png)

Samspillet med kontrollerne til applikasjonen er illustrert på klassediagrammet under. 

![Kontrollere](controllers.png)

Følgende diagram viser samspillet mellom kjernemodulen, brukergrensesnittmodulen, REST-tjenesten når en bruker oppretter en ny oppskrift og legger denne til i kokeboken.

![Sekvensdiagram](sequence.png)
