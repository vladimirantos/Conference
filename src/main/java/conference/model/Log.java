package conference.model;

import org.apache.log4j.Logger;

import java.util.Map;

public class Log {
    public static void message(String title, String message, Object sender){
        Logger logger = Logger.getLogger(sender.getClass());
        logger.info(title.toUpperCase() + ": " + message);
    }

    public static void message(String title, Boolean message, Object sender){
        message(title, String.valueOf(message), sender);
    }

        public static void message(String title, Integer message, Object sender){
        message(title, String.valueOf(message), sender);
    }

    public static void message(String title, Map<String,?> messages, Object sender){
        for (Map.Entry<String,?> entry : messages.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            message(title, key+" - "+value, sender);
        }
    }
}
