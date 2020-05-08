package app.main.todo.csv.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.main.todo.entities.Todo;
import app.main.todo.generic.utils.GenericUtils;

public class CSVReaderUtil {

	public static List<String[]> fetchRows(String file) {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<String[]> rows = new ArrayList<>();
		boolean skipHeaders = false;
		try {

			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {

				String[] row = line.split(cvsSplitBy);

				if (skipHeaders) {
					rows.add(row);
				}
				else {
					skipHeaders = true;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rows;
	}

	public static List<Todo> fetchRowsAsTodoList(String file) {
		try {
			return fetchRows(file)
					.stream()
					.map(todo -> new Todo(Integer.parseInt(todo[0]), todo[1], Boolean.parseBoolean(todo[2]),
							todo[3], Integer.parseInt(todo[4]), todo.length >= 6 ? todo[5] : ""))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public static Integer getNextId(String csvFilePath) {

		try {
			List<Integer> ints = Files.lines(Paths.get(GenericUtils.getIdTrackerFileName(csvFilePath)))
					                  .map(Integer::parseInt)
					                  .collect(Collectors.toList());
			
			int nextId = ints.get(0) + 1;
			Writer idFileWriter = new FileWriter(GenericUtils.getIdTrackerFileName(csvFilePath));
			idFileWriter.write(new Integer(nextId).toString());
			idFileWriter.close();
			return nextId;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}	
}