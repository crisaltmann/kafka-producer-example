package br.com.crisaltmann.stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvStockParser {

	private static final String CSV_NAME = "wege3.csv";
	
	private static final String CODE = "WEGE3";
	
	private static final String SEPARATOR = ";";
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static void main(String[] args) {
		System.out.println(new CsvStockParser().parse());
	}
	
	public List<Stock> parse() {
		return loadFileLines().stream()
				.filter(line -> !line.contains("Data"))
				.map(line -> parseStock(line))
				.collect(Collectors.toList());
	}
	
	private List<String> loadFileLines() {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader(
						getClass().getClassLoader().getResource(CSV_NAME).getFile()))) {
			String temp = null;
			while ((temp = br.readLine()) != null)
				lines.add(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	private Stock parseStock(String line) {
		String[] data = line.replaceAll(",", ".")
				.replaceAll("n/d", "0")
				.split(SEPARATOR);
		return createStock(data);
	}
	
	private Stock createStock(String[] data) {
		try {
			return new Stock(CODE,
				LocalDate.parse(data[0], FORMATTER), 
				new BigDecimal(data[2]), 
				new BigDecimal(data[4]), 
			    new BigDecimal(data[5]),
			    new BigDecimal(data[6]), 
			    new BigDecimal(data[7]),
			    parseInteger(data[8]), 
			    parseInteger(data[9]));
		} catch (Exception e) {
			System.out.println("Error parse: " + Arrays.asList(data).toString());
			throw e;
		}
	}
	
	private Integer parseInteger(String data) {
		if (data == null || data.isEmpty())
			return 0;
		else
			return Integer.valueOf(data);
	}
	
}
