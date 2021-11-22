# Testkode for persistenslaget

Klassene i persistenslaget samhandler, for eksempel bruker `CookbookSerializer` både `RecipeSerializer` og `IngredientSerializer`. Likevel er det utarbeidet tester for hver av klassene framfor en samlet integrasjonstest. Dermed blir det tydeligere å finne ut av hvilken klasse feilen ligger i. Vi kaller disse enhetstester selv om de ikke er uavhengige av andre klasser. Alle testene benytter `CookbookModule`, så denne klassen blir testet indirekte.

- `AbstractJsonTest` - abstrakt klasse med metoder og tilstander for enhetstestene
- `IngredientJsonTest` - enhetstest for IngredientSerializer og IngredientDeserializer
- `RecipeJsonTest` - enhetstest for RecipeSerializer og RecipeDeserializer
- `CookbookJsonTest` - enhetstest for CookbookSerializer og RecipeDeserializer
- `CookbookPersistenceTest` - integrasjonstest for persistenslaget. Tester metodene i CookbookPeristence-klassen og disse bruker alle de andre klassene.
