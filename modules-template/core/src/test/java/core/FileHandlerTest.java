public class FileReaderTest {
	private Cookbook cookbook;
	private Recipe recipe1;
	private Recipe recipe2;
	private FileReader fileReader;
	File tempFile;
	Path path;

	@TempDir // For å oprette midlertidige filer som slettes etter testene
	Path tempDir;

	@BeforeEach
	public void setup() {
		fileReader = new FileReader();
		recipe1 = new Recipe("recipe1", 2);
		recipe1.addIngredient("Aspareges", 2, "stk");
        recipe1.setDescription("Kok aspargesene");

		recipe2 = new Recipe("recipe2", 2);
		recipe2.addIngredient("Egg", 3, "Stk");
		recipe2.setDescription("Kok eggene");

		cookbook = new Cookbook();
		cookbook.addRecipe(recipe1);
		cookbook.addRecipe(recipe2);

		try {
			path = tempDir.resolve("new-file-test");
		} catch (InvalidPathException e) {
			fail("Could not create temporary file");
		}
		tempFile = path.toFile();
	}

	public boolean compareCookbooks(Cookbook c1, Cookbook c2) {
		StringBuffer stringBuffer1 = new StringBuffer();
		StringBuffer stringBuffer2 = new StringBuffer();

		for (Recipe r : c1.getRecipes()) {
			stringBuffer1.append(r.toString());
			stringBuffer1.append(" ");
		}
		for (Recipe r : c2.getRecipes()) {
			stringBuffer2.append(r.toString());
			stringBuffer2.append(" ");
		}
		String string1 = stringBuffer1.toString();
		String string2 = stringBuffer2.toString();
		return (string1.equals(string2));
	}

	public boolean compareFiles(Path p1, Path p2) throws IOException {
		byte[] fileToTest = null, tempFileTest = null;

		fileToTest = Files.readAllBytes(p1);
		tempFileTest = Files.readAllBytes(p2);

		return (Arrays.equals(fileToTest, tempFileTest));
	}

	@Test
	public void testReadRecipeFromFile() throws FileNotFoundException {
		Cookbook newCookbook = new Cookbook();
		FileHandler.readRecipeFromFile("src/main/java/test/java/resources/test/FileHandlerFile.txt", newCookbook);
		assertTrue(compareCookbooks(cookbook, newCookbook));
	}

	public void saveRecipeToFile() throws IOException {
		FileHandler.writeRecipeToFile(tempFile.getAbsolutePath(), recipe1);
		FileHandler.writeRecipeToFile(tempFile.getAbsolutePath(), recipe2);
	}


	@Test
	public void testWriteRecipesToFile() throws IOException {
		FileHandler.writeRecipesToFile(tempFile.getAbsolutePath(), cookbook);

		assertTrue(compareFileString(Path.of("src/main/java/test/java/resources/test/FileHandlerFile.txt"), path));

	}

	@Test
	public void testFileNotFoundException() {
		assertThrows(FileNotFoundException.class, () -> FileHandler.getRecipeFromFile("stopid", cookbook),
				"FileNotFoundException should be thrown when file does not exist!");
	}
}