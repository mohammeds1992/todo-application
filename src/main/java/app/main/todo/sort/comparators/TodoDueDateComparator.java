package app.main.todo.sort.comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import app.main.todo.entities.Todo;
import app.main.todo.generic.utils.GenericUtils;

public class TodoDueDateComparator implements Comparator<Todo> {

	@Override
	public int compare(Todo o1, Todo o2) {

		Date date1;
		Date date2;

		try {
			SimpleDateFormat format = new SimpleDateFormat(GenericUtils.DUE_DATE_FORMAT);

			if (o1.getDue() == null && o2.getDue() == null) {
				return 0;
			}

			else if (o1.getDue() == null) {
				return -1;
			}

			else if (o2.getDue() == null) {
				return 1;
			}

			date1 = format.parse(o1.getDue());
			date2 = format.parse(o2.getDue());
			return date1.compareTo(date2);

		} catch (ParseException e) {
			return 0;
		}
	}
}