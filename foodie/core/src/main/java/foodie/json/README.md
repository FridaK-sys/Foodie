# Kildekode for persistenslaget

Vi bruker **Jackson** biblioteket for å implementere serialisering og deserialisering klasser.
Jackson er et rammeverk for å skrive og lese JSON objekter. Serialiseringsklassene brukes for å serialisere objekter til JSON. Serialisering betyr å konvertere et objekt til en JSON string. Deserialiseringsklassene er den inverse operasjonen, altså å konvertere en JSON string til det opprinnelige objektet. 

- `CookbookModule` er en SimpleModule som inneholder serialisering og deserialisering klassene. 
- `CookbookPersistence` bruker `CookbookModule` til å skrive til og lese fra fil.

Appen vår benytter seg av implisitt lagring. Forskjellen på implisitt og eksplisitt lagring er et ved implisitt lagres dataen automatisk og brukeren har ikke noe forhold til hvor det blir lagret. Eksplisitt lagring er at du selv velger når du skal lagre og eventuellt hvor det blir lagret. Vi velger implisitt lagring i appen vår fordi da risikerer ikke brukeren å miste endringene sine. Dette gir en lettere brukeropplevelse da brukeren slipper å tenke på å lagre endringe sine hele tiden. 
 


