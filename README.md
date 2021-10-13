# Foodie-prosjektet 

Dette prosjektet er tilknyttet emnet IT1901 Informatikk prosjektarbeid I.

## Om appen
Appen Foodie lar deg opprette dine egne kokebøker og legge til oppskrifter i disse. Hver oppskrift kan redigeres ved å legge til eller fjerne ingredienser, samt skalere antall porsjoner for oppskriften. Appen er ment for å brukes som en digital samling av kokebøker, og bidra til lettere matlagingsopplevelse i hverdagen. 

## Oppsett av prosjektet 
Selve kodingsprosjektet ligger i modules-template.
Dokumentasjonen for hver release ligger i mappen docs.
Logikken og tester til disse klassene ligger i modules-template/core/src/.
Design av brukergrensesnittet i javafx, kontrollere og filbehandling ligger i modules-template/ui/src.

## Bygging og kjøring av prosjektet
Prosjektet bruker maven til bygging og kjøring. 
For å kjøre prosjektet, bruk `mvn install`, deretter `mvn -pl ui javafx:run` i mappen modules-template. Dette kjører også alt av tester. 

## Verktøy tilknyttet kodekvalitet
Vi har brukt følgende verktøy tilknyttet kodekvalitet:

- **Checkstyle** - sjekker mer overfladiske og stilmessige egenskaper til koden som tekst
- **Spotbugs** - analyserer koden for vanlige feil
- **Jacoco** - samler inn og presenterer informasjon og testdekningsgrad

## Arbeidsvaner og arbeidsflyt
Vi bruker Scrum som en del av vår utviklingsprosess. Scrum er et rammeverk for å støtte smidig utvikling av informasjonssystemer. 
Her struktureres utviklingen i sykler kalt sprinter. For hvert møte setter vi oss et sprint-goal for dagen, og planlegger arbeidsoppgaver i henhold til dette. Her foretar vi også vurdering av tidligere arbeid. Vi møtes minst to ganger i uken for å jobbe sammen med prosjektet. 
Da veksler vi mellom parprogrammering og programmering individuelt. 

Vi har en god og strukturert arbeidsflyt. For hver sprint benytter vi boards på gitlab til å danne en oversikt over alle issues (kodingsoppgaver) som må løses. Her kan man se hvilke issues som må løses, er under arbeid og gjenstår for denne sprinten. Hver issue tildeles et av gruppemedlemmene og merkes med passende labels. Når et gruppemedlem tar på seg et issue, oppretter de en egen branch for dette issuet. Når issuet er løst, og alle gruppemedlemmene er fornøyd med resultatet, så blir denne branchen merget med master-branchen. Vi bruker en milestone tilknyttet hver innlevering i prosjektet. 




