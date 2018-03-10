package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Log {

    public static final String ARBOT_EXCHANGE_LOG_JSON = "C:/Arbot/Exchange Log.json";
    private static Log logger = new Log();

    private final Logger fileLogger;


    private Log() {
        fileLogger = Logger.getLogger("Activity");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("C:/Arbot/ArbotLog.txt");
            Formatter formatter = new MyFormatter();
            fh.setLevel(Level.ALL);
            fh.setFormatter(formatter);
            fileLogger.addHandler(fh);
            StreamHandler handler = new ConsoleHandler();
            handler.setFormatter(formatter);
            handler.setLevel(Level.ALL);
            fileLogger.addHandler(handler);
            fileLogger.setLevel(Level.ALL);
            fileLogger.setUseParentHandlers(false);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void activity(String message) {
        logger.fileLogger.log(Level.ALL, message);
    }

    public static void print(String message) {
//        logger.messages.onNext(message);
        System.out.println(message);
    }

    public static void debug(String s) {
        if (Constants.DEBUG) {
            print(s);
        }
    }

    public static void writeExchangeToDisk(String s) {
        File file = new File(ARBOT_EXCHANGE_LOG_JSON);
        Path path = Paths.get(ARBOT_EXCHANGE_LOG_JSON);
        boolean justCreated = false;
        String textToAppend = s.substring(0, s.length()-1) + ", [" + System.currentTimeMillis() + "]]" + ",";
        byte[] bytesToAppend = textToAppend.getBytes();
        if (!file.exists()) {
            try {
                justCreated = file.createNewFile();

                Files.write(path, ("[" + textToAppend).getBytes(), StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (justCreated) {
            return;
        }
        try {
            Files.write(path, bytesToAppend, StandardOpenOption.APPEND);
            activity("Inserting log");
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
            e.printStackTrace();
        }
    }

    private static class MyFormatter extends Formatter {
        // Create a DateFormat to format the logger timestamp.
        private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(1000);
            builder.append(df.format(new Date(record.getMillis()))).append(" - ");
            builder.append(formatMessage(record));
            builder.append("\n");
            return builder.toString();
        }

        public String getHead(Handler h) {
            return super.getHead(h);
        }

        public String getTail(Handler h) {
            return super.getTail(h);
        }
    }
}
