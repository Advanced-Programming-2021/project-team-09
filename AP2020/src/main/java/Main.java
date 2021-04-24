import com.gilecode.yagson.YaGson;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import model.User;
import java.util.Properties;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = System.getProperty("os.name").startsWith("Windows")?"src\\resources\\card-infos\\SpellTrap.csv":"src/resources/card-infos/SpellTrap.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader("src\\resources\\card-infos\\SpellTrap.csv"));
            List<String[]> temp = reader.readAll();
            for (String[] a : temp) System.out.println(a[0] + ".");

        } catch (IOException | CsvException e) {

        }
        System.out.println();
    }
}
