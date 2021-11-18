# Testkode for persistenslaget

Alle klassene i persistenslaget hører sammen for eksempel bruker CookbookSerializer både RecipeSerializer og IngredientSerializer. Vi lagde likevel tester for hvert av lagene istedenfor kun en samlet integrasjonstest fordi det gjør det tydeligere hvor feilen ligger dersom vi får en feil. Vi kaller disse enhetstester selv om de ikke er uavhengig av andre klasser. Alle testene bruker CookbookModule så denne klassen blir indirekte testet i alle klassene.

AbstractJsonTest - abstrakt klasse med metoder og tilstander for enhetstestene
IngredientJsonTest - enhetstest for IngredientSerializer og IngredientDeserializer
RecipeJsonTest - enhetstest for RecipeSerializer og RecipeDeserializer
CookbookJsonTest - enhetstest for CookbookSerializer og RecipeDeserializer
CookbookPersistenceTest - integrasjonstest for persistenslaget. Tester metodene i CookbookPeristence-klassen og disse bruker alle de andre klassene.
