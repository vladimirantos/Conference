package conference.exceptions.http;

/**
 * @author Vladimír Antoš <vladimirantos@seznam.cz>
 * @version 1.0
 */
//@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such page")
public class NotFoundException extends BadRequestException{

    public NotFoundException(){
        super(404);
    }
    
    public NotFoundException(String message){
        super(message, 404);
    }
    
    public NotFoundException(String message, int code){
        super(message, code);
    }
}
