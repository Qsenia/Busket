import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    protected List<String[]> log = new ArrayList<>();

    public void log(int productNum, int amount) {
        //покупатель добавил покупку, то это действие должно быть там сохранено
        log.add(new String[]{"" + productNum, "" + amount});
    }

    // Также у объекта этого класса должен быть метод
    //для сохранения всего журнала действия в файл в формате
    public void exportAsCSV(File txtFile) throws IOException {
        if (!txtFile.exists()) {
            log.add(0, new String[]{"productNum, amount"});
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeAll(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
