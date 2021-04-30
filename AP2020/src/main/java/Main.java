
import controller.*;
import model.card.spell_traps.Trap;
import model.*;
import com.gilecode.yagson.YaGson;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import model.User;
import org.jetbrains.annotations.TestOnly;
import view.LoginMenu;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        LoginMenu.getInstance().run();
    }
}
