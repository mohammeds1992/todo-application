package app.main.todo.driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import app.main.todo.exceptions.InvalidDateFormatException;
import app.main.todo.exceptions.InvalidIntegerException;
import app.main.todo.exceptions.InvalidPriorityException;
import app.main.todo.exceptions.InvalidTodoIdException;
import app.main.todo.exceptions.MandatoryParameterException;
import app.main.todo.generic.utils.CliArgs;
import app.main.todo.generic.utils.GenericUtils;
import app.main.todo.repositories.TodoRepository;

public class Driver {

	public static void main(String[] args) throws IOException {

		try {

			CliArgs cliArgs = new CliArgs(args);

			String csv_file = cliArgs.switchValue("--csv-file");
			boolean add_todo = cliArgs.switchPresent("--add-todo");
			String todo_text = cliArgs.switchValue("--todo-text");
			boolean completed = cliArgs.switchPresent("--completed");
			String due = cliArgs.switchValue("--due");
			String category = cliArgs.switchValue("--category");
			String completed_todo = cliArgs.switchValue("--complete-todo");
			boolean display = cliArgs.switchPresent("--display");
			boolean show_incomplete = cliArgs.switchPresent("--show-incomplete");
			String show_category = cliArgs.switchValue("--show-category");
			boolean sort_by_priority = cliArgs.switchPresent("--sort-by-priority");
			boolean sort_by_date = cliArgs.switchPresent("--sort-by-date");

			if (csv_file == null) {
				throw new MandatoryParameterException("csv-file");
			}
			
			// Setup
			GenericUtils.setup(csv_file);

			
			// Validation of arguments


			if (cliArgs.switchPresent("--complete-todo")) {

				
					// 2,3,4 -> [2,3,4]
					List<Integer> completedTodoIds = Stream.of(completed_todo.split(","))
							.map(Integer::parseInt)
							.collect(Collectors.toList());

					List<Integer> ints = Files.lines(Paths.get(GenericUtils.getIdTrackerFileName(csv_file)))
							.map(Integer::parseInt)
							.collect(Collectors.toList());

					// current is id which is being used so far
					Integer currentId = ints.get(0);

					for (int id : completedTodoIds) {

						// check if todo is valid
						if (id < 0 || id > currentId) {
							throw new InvalidTodoIdException();
						}
					}
			

			}

			Integer priority = 3;

			if (add_todo) {

				if (todo_text == null) {
					throw new MandatoryParameterException("todo_text");
				}

				if (due != null) {

					try {
						SimpleDateFormat format = new SimpleDateFormat(GenericUtils.DUE_DATE_FORMAT);
						format.setLenient(false);
						format.parse(due);
					} catch (ParseException e) {
						throw new InvalidDateFormatException();
					}
				}

				if (cliArgs.switchPresent("--priority")) {

					try {
						priority = cliArgs.switchIntegerValue("--priority");
					} catch (Exception e) {
						throw new InvalidIntegerException();
					}

					if (priority < 1 || priority > 3) {
						throw new InvalidPriorityException();
					}
				}
			}


			// Actual Execution 
			if (add_todo) {
				TodoRepository.addTodo(csv_file, todo_text, due, priority, category, completed);
			}

			if (cliArgs.switchPresent("--complete-todo")) {

				List<Integer> ids = Stream.of(completed_todo.split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());

				for (Integer id : ids) {
					TodoRepository.markTodoAsComplete(id, csv_file);
				}
			}

			if (display) {
				GenericUtils.displayTodos(csv_file, show_category, show_incomplete, sort_by_priority, sort_by_date);
			}
		} catch (Exception e) {
			System.out.println("Aborting the program. Exception occured: " + e.getMessage());
		}
	}
}