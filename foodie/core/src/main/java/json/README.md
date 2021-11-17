# Kildekode for JSON

Vi bruker Jackson biblioteket for å implementere serialisering og deserialisering klasser.
Jackson er et rammeverk for å skrive og lese JSON objekter. Serialiseringsklassene brukes for å serialisere objekter til JSON. Altså å konvertere et objekt til en JSON string. Deserialiseringsklassene er den inverse operasjonen. Altså å konvertere en JSON string til det opprinnelige objektet. 

CookbookModule er en SimpleModule som inneholder serialisering og deserialisering klassene. CookbookPersictence bruker igjen CookbookModule til å skrive til fil. 

Vi har valgt å bruke `implisitt lagring` ettersom brukeren ikke har behov for å vite hvor kokeboken deres blir lagret. Dessutten risikerer ikke brukeren å miste endringene deres, for eksempel hvis de merker en oppskrift som favoritt. Dette gir en lettere brukeropplevelse. 
 


