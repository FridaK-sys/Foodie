# Kildekode for filbehandling
Vi har valgt å bruke JSON.simple til filbehandling. JSON.simple er et Java-bibliotek som brukes til JSON prosessering, samt lesing og skriving av JSON-data. 

Klassen `FileHandler` består av metoder for lesing og skriving til fil. Her er det implementert metoder for å skrive én kokebok eller oppskrift til fil, lese oppskriftene fra en kokebok og erstatte én oppskrift.  

Vi har valgt å bruke `implisitt lagring` ettersom brukeren ikke har behov for å vite hvor kokeboken deres blir lagret. Dessutten risikerer ikke brukeren å miste endringene deres, for eksempel hvis de merker en oppskrift som favoritt. Dette gir en lettere brukeropplevelse. 
 


