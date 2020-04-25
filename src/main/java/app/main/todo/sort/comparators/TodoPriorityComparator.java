package app.main.todo.sort.comparators;

import java.util.Comparator;

import app.main.todo.entities.Todo;

public class TodoPriorityComparator implements Comparator<Todo> {

	@Override
	public int compare(Todo o1, Todo o2) {
		return Integer.compare(o1.getPriority(), o2.getPriority());
	}
}