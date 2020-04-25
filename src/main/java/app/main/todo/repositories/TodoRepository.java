package app.main.todo.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import app.main.todo.csv.utils.CSVReaderUtil;
import app.main.todo.csv.utils.CSVWriterUtil;
import app.main.todo.entities.Todo;
import app.main.todo.generic.utils.GenericUtils;

public class TodoRepository {

	public static void markTodoAsComplete(Integer id, String file) {
		List<String[]> data = CSVReaderUtil.fetchRows(file);
		for (String[] row : data) {
			if (Integer.parseInt(row[0]) == id) {
				row[2] = "true";
				CSVWriterUtil.writeDataAtOnce(file, GenericUtils.getStringListFromRows(data));
				return;
			}
		}
	}

	public static void addTodo(String csvFilePath, String text, String due, Integer priority, String category,
			boolean completed) {
		Todo todo = new Todo(text, due, priority, category, completed, csvFilePath);
		
		
		Path out = Paths.get(csvFilePath);
		String todoString = todo.toString() + "\n";
		
		try {
			Files.write(out, todoString.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}