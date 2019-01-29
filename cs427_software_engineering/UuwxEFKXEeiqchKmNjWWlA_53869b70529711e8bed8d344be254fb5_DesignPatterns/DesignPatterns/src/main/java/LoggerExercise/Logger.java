package LoggerExercise;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger instance;
    private static File file;

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
            String today = (new SimpleDateFormat("MMddyyyy")).format(new Date());
            String filename = "log"+today+".log";
            file = new File(filename);
            try {
                PrintWriter pw = new PrintWriter(filename);
                pw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    public void logInFile(Object log) {
        BufferedWriter br = null;
        try {
            FileWriter fr = new FileWriter(file,true);
            br = new BufferedWriter(fr);
            br.append((String)log);
            br.newLine();

            br.flush();
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}