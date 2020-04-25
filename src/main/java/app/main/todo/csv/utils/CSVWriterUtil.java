package app.main.todo.csv.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import app.main.todo.generic.utils.GenericUtils;

public class CSVWriterUtil {

	public static void writeDataAtOnce(String filePath, List<String> data) {
		try {
			data.add(0, String.join(",", GenericUtils.getHeaders()));
			Path file = Paths.get(filePath);
			Files.write(file, data, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}