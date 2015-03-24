package conference.exceptions.http;

/**
 * @author Vladimír Antoš <vladimirantos@seznam.cz>
 * @version 1.0
 */
public class BadRequestException extends RuntimeException{
    
    protected int code;
    
    public BadRequestException(){
        
    }
    
    public BadRequestException(int code){
        this.code = code;
    }
    
    public BadRequestException(String message, int code){
        super(message);
        this.code = code;
    }
    
    public int getCode(){
        return code;
    }
}
