import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVParser {

    public static ArrayList<IndexOfHappiness> parseCSV(String path) {
        var parseCSV = new ArrayList<IndexOfHappiness>();
        try {
            List<String> fileLines = Files.readAllLines(Paths.get(path));
            var names = IndexOfHappiness.class.getDeclaredFields();
            for (String fileLine : fileLines.subList(1, fileLines.size())) {
                String[] splitText = fileLine.split(",");
                Map<String, String> row = IntStream.range(0, names.length)
                        .boxed()
                        .collect(Collectors.toMap(i -> names[i].getName(), i -> splitText[i]));
                parseCSV.add(new IndexOfHappiness(row));
            }
        } catch(IOException e) {
            throw new IllegalArgumentException();
        }
        return parseCSV;
    }
}
