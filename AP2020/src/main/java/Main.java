import com.gilecode.yagson.YaGson;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            CSVReader reader = new CSVReader(new FileReader("src\\resources\\card-infos\\SpellTrap.csv"));
            List<String[]> tmep = reader.readAll();
            for (String[] a : tmep) System.out.println(a[0] + ".");

        } catch (IOException | CsvException e) {

        }
        System.out.println();
    }
}
