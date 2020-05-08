package app.main.todo.generic.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import app.main.todo.csv.utils.CSVReaderUtil;
import app.main.todo.entities.Todo;
import app.main.todo.sort.comparators.TodoDueDateComparator;
import app.main.todo.sort.comparators.TodoPriorityComparator;

public class GenericUtils {



	public final static String DUE_DATE_FORMAT = "MM/dd/yyyy";

	public static List<String> getHeaders() {
		return Arrays.asList("id", "text", "completed", "due", "priority", "category");
	}

	public static List<String> getStringListFromRows(List<String[]> data) {
		return data.stream().map(d -> String.join(",", d)).collect(Collectors.toList());
	}

	public static void setup(String csvFilePath) throws IOException {
		File csvFile = new File(csvFilePath);
		
		Path path = Paths.get(csvFilePath);
		
		// Create directory if not present
		if (path.getParent() != null) {
			File parent = new File(path.getParent().toString());

			if (!parent.exists()) {
				parent.mkdir();
			}
		}
		
		if (!csvFile.exists()) {

			csvFile.createNewFile();

			File idFile = new File(getIdTrackerFileName(csvFilePath));
			idFile.createNewFile();

			Writer idFileWriter = new FileWriter(getIdTrackerFileName(csvFilePath));
			idFileWriter.write(new Integer(0).toString());
			idFileWriter.close();

			Path out = Paths.get(csvFilePath);

			Files.write(out, (String.join(",", GenericUtils.getHeaders()) + "\n").getBytes(),
					StandardOpenOption.APPEND);
		}
	}

	public static void displayTodos(String file, String category, boolean show_incomplete, boolean sort_by_priority,
			boolean sort_by_date) {

		List<Todo> todoList = CSVReaderUtil.fetchRowsAsTodoList(file);

		
		if (sort_by_priority) {
			Collections.sort(todoList, new TodoPriorityComparator());
		}

		else if (sort_by_date) {
			Collections.sort(todoList, new TodoDueDateComparator());
		}

		TableGenerator tableGenerator = new TableGenerator();

		List<String> headersList = new ArrayList<>();
		headersList.add("id");
		headersList.add("text");
		headersList.add("completed");
		headersList.add("due");
		headersList.add("priority");
		headersList.add("category");

		List<List<String>> rowsList = new ArrayList<>();

		for (Todo todo : todoList) {

			if (category == null || todo.getCategory().equals(category)) {
		 
				if (!show_incomplete || !todo.isCompleted()) {
					List<String> row = new ArrayList<>();

					row.add(todo.getId().toString());
					row.add(todo.getText());

					row.add(String.valueOf(todo.isCompleted()));
					row.add(todo.getDue());
					row.add(todo.getPriority().toString());
					row.add(todo.getCategory());

					rowsList.add(row);
				}
			}
		}
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}
	
	public static String getIdTrackerFileName(String csvFilePath) {
		Path path = Paths.get(csvFilePath);
		Path fileName = path.getFileName();

		if (path.getParent() != null) {
			return path.getParent().toString() + "/" + String.format("id_%s.txt", fileName.toString().split("\\.")[0]);
		} else {
			return String.format("id_%s.txt", fileName.toString().split("\\.")[0]);
		}
	}
	
	public static String getAlphaNumericString(int n) {

		String AlphaNumericString =  "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

}