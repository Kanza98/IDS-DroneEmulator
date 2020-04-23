package sample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String logTime;
    private String message;
    private String command;
    private String param1;
    private String param2;

    public Message(String message){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date currentTime = new Date();
        logTime = formatter.format(currentTime);

        this.message = message;

        String[] messageArray = message.split(" ");

        command = messageArray[0];

    }


    public Message (byte[] message, int length){
        this(new String(message, 0, length));
    }

    public String getMessage() {
        return message;
    }

    public String getLogTime() {
        return logTime;
    }


    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return logTime + " Message: " + message;
    }
}