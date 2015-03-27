package conference.model.repository;

public class EntityExistsException extends RuntimeException{
    public EntityExistsException(String message){
        super(message);
    }
}
