
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import app.main.todo.csv.utils.CSVReaderUtil;
import app.main.todo.entities.Todo;
import app.main.todo.generic.utils.GenericUtils;
import app.main.todo.repositories.TodoRepository;

public class TodoAppTests {
	
	
    @BeforeClass
    public static void initGlobalResources() {
    	File file = new File("test");
		file.mkdir();
    }

	@Test
	public void testSomeLibraryMethod() {
		assertTrue("someLibraryMethod should return 'true'", true);
	}

	@Test
	public void testSetUpCSVCreation() throws IOException {

		
		String csvFile = "test/" + GenericUtils.getAlphaNumericString(5) + ".csv";
		GenericUtils.setup(csvFile);
		File f = new File(csvFile);
		assertTrue("Return true if CSV creation happen", f.exists());
	}
	
	@Test
	public void testSetUpIDFileCreation() throws IOException {

		File file = new File("test");
		file.mkdir();
		String csvFile = "test/" + GenericUtils.getAlphaNumericString(5) + ".csv";
		GenericUtils.setup(csvFile);
		
		String idFile = GenericUtils.getIdTrackerFileName(csvFile);
		File f = new File(idFile);
		assertTrue("Return true if Id File creation happen", f.exists());
	}
	

	
	@Test
	public void testSetUpIDFileInitialization() throws IOException {

		File file = new File("test");
		file.mkdir();
		String csvFile = "test/" + GenericUtils.getAlphaNumericString(5) + ".csv";
		GenericUtils.setup(csvFile);

		List<Integer> ints = Files.lines(Paths.get(GenericUtils.getIdTrackerFileName(csvFile))).map(Integer::parseInt)
				.collect(Collectors.toList());

		int nextId = ints.get(0);

		assertEquals("Id should be zero", 0, nextId);
	}
	
	
	@Test
	public void testAddTODOSize() throws IOException {

		File file = new File("test");
		file.mkdir();
		String csvFilePath = "test/" + GenericUtils.getAlphaNumericString(5) + ".csv";
		GenericUtils.setup(csvFilePath);

		String text = "Task-1";
		String due = "";
		Integer priority = 1;
		String category = "Work";
		boolean completed = true;

		TodoRepository.addTodo(csvFilePath, text, due, priority, category, completed);

		List<Todo> list = CSVReaderUtil.fetchRowsAsTodoList(csvFilePath);

		assertEquals("Size of todos", 1, list.size());
	}
	
	
	@Test
	public void testAddTODOContentV1() throws IOException {

		File file = new File("test");
		file.mkdir();
		String csvFilePath = "test/" + GenericUtils.getAlphaNumericString(5) + ".csv";
		GenericUtils.setup(csvFilePath);

		String text = "Task-1";
		String due = "14/01/1992";
		Integer priority = 1;
		String category = "Work";
		boolean completed = true;

		TodoRepository.addTodo(csvFilePath, text, due, priority, category, completed);

		List<Todo> list = CSVReaderUtil.fetchRowsAsTodoList(csvFilePath);
		assertEquals("Size Validation", 1, list.size());
		
		Todo todo = list.get(0);
		assertEquals("Text Validation", text, todo.getText());
		assertEquals("Date Validation", due, todo.getDue());
		assertEquals("Category Validation", category, todo.getCategory());
		assertEquals("Priority Validation", priority, todo.getPriority());
		assertEquals("Completed Validation", completed, todo.isCompleted());
	}
	
	@Test
	public void testAddTODOContentV2() throws IOException {

		File file = new File("test");
		file.mkdir();
		String csvFilePath = "test/" + GenericUtils.getAlphaNumericString(5) + ".csv";
		GenericUtils.setup(csvFilePath);

		String text = "Task-1";
		String due = "14/01/1992";
		Integer priority = 3;
		String category = "Home";
		boolean completed = false;

		TodoRepository.addTodo(csvFilePath, text, due, priority, category, completed);

		List<Todo> list = CSVReaderUtil.fetchRowsAsTodoList(csvFilePath);
		assertEquals("Size Validation", 1, list.size());
		
		Todo todo = list.get(0);
		assertEquals("Text Validation", text, todo.getText());
		assertEquals("Date Validation", due, todo.getDue());
		assertEquals("Category Validation", category, todo.getCategory());
		assertEquals("Priority Validation", priority, todo.getPriority());
		assertEquals("Completed Validation", completed, todo.isCompleted());
	}
	
	
	@Test
	public void testMarkTODOAsComplete() throws IOException {

		File file = new File("test");
		file.mkdir();
		String csvFilePath = "test/" + GenericUtils.getAlphaNumericString(5) + ".csv";
		GenericUtils.setup(csvFilePath);

		String text = "Task-1";
		String due = "14/01/1992";
		Integer priority = 3;
		String category = "Home";
		boolean completed = false;

		TodoRepository.addTodo(csvFilePath, text, due, priority, category, completed);

		List<Todo> list = CSVReaderUtil.fetchRowsAsTodoList(csvFilePath);
		assertEquals("Size Validation", 1, list.size());
		
		Todo todo = list.get(0);
		
		TodoRepository.markTodoAsComplete(todo.getId(), csvFilePath);
		list = CSVReaderUtil.fetchRowsAsTodoList(csvFilePath);
		todo = list.get(0);

		assertEquals("Text Validation", text, todo.getText());
		assertEquals("Date Validation", due, todo.getDue());
		assertEquals("Category Validation", category, todo.getCategory());
		assertEquals("Priority Validation", priority, todo.getPriority());
		assertEquals("Completed Validation", true, todo.isCompleted());
	}
	
	
    @AfterClass
    public static void deleteGlobalResources() throws IOException {
		Path out = Paths.get("test");
    	Files.walk(out)
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
    }
}