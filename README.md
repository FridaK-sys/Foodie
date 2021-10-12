# Foodie-prosjektet 

Dette prosjektet er tilknyttet emnet IT1901 Informatikk prosjektarbeid I.
Selve kodingsprosjektet ligger i modules-template.
Dokumentasjonen for hver release ligger i mappen docs.
Logikken og tester til disse klassene ligger i modules-template/core/src/.
Design av brukergrensesnittet i javafx, kontrollere og filbehandling ligger i modules-template/ui/src.

## Bygging og kjøring av prosjektet
Prosjektet bruker maven til bygging og kjøring. 
For å kjøre prosjektet, bruk `mvn install`, deretter `mvn -pl ui javafx:run` Dette kjører også alt av tester. 

## Verktøy tilknyttet kodekvalitet
Vi har brukt følgende verktøy tilknyttet kodekvalitet:

- [checkstyle](https://checkstyle.sourceforge.io) - sjekker mer overfladiske og stilmessige egenskaper til koden som tekst
- [spotbugs](https://spotbugs.github.io/) - analyserer koden for vanlige feil
- [jacoco](https://www.jacoco.org) - samler inn og presenterer informasjon og testdekningsgrad



