# Kildekode for brukergrensesnittet
Vi har brukt JavaFX og FXML for å lage brukergrensesnittet. 

# Arkitektur
Vi har to varianter av applikasjonen. Den ene bruker lokale data lagret på fil, mens den andre bruker data håndtert gjennom et REST-API på en server. Derfor har vi to FXML filer (X og Y) og de tilhørende Kontroller klasser (LocalAppController og RestAppController). Siden kontrollerne har en del felles kode har vi laget en abstrakt klasse (AbstractController) som begge arver fra. Datatilgangen er spesifisert i egne klasser som RemoteCookbookAccess og LocalCookbookAccess. Remote og Local implementerer grensesnittet CookbookInterface. Forskjellen er at Local gjør operasjonene lokalt mens Remote gjør det med å bruke REST-APIet. 

Appklassen vår (CookbookApp) velger hvilken variant som skal brukes. Noe mer...
Merk at for å kjøre varianten som bruker REST-APIet så må rest serveren starte. Det gjør det med å gå inn i rest mappen også kjøre kommandoen `mvn spring-boot:run`. Dette er også forklart i README filen i rest mappen. 
