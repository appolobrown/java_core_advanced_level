package ru.geekbrains.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ChatController {

    public static final String FILE_CHATLOG = "chat.log";
    Logger logger;

    public ChatController() {
        //Версия как по-хорошему
        logger = Logger.getLogger("chat_Logger");
        FileHandler fh = null;
        try {
            fh = new FileHandler(FILE_CHATLOG);
            logger.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        logger.info(message);

        //и версия, как по-простому. Но такие вещи в UI потоке я бы не стал использовать для логгирования
        try (FileOutputStream fileOutputStream = new FileOutputStream("simple_" + FILE_CHATLOG, true)) {
            Date date = Calendar.getInstance().getTime();
            String logMessage = String.format("%s %s", date.toString(), message);
            fileOutputStream.write(logMessage.getBytes(Charset.defaultCharset()));
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
