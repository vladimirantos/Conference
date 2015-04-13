package conference.exceptions;

public class FileNotUploadedException extends ApplicationException{

    public FileNotUploadedException(String message){
        super(message);
    }

    public FileNotUploadedException(String message, int code){
        super(message, code);
    }
}
