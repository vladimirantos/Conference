/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conference.exceptions;

/**
 * @author Vladimír Antoš <vladimirantos@seznam.cz>
 * @version 1.0
 */
public class ApplicationException extends RuntimeException{
    protected int code;
    
    public ApplicationException(){
        
    }
    
    public ApplicationException(String message){
        super(message);
    }
    
    public ApplicationException(String message, int code){
        super(message);
        this.code = code;
    }
    
    public int getCode(){
        return code;
    }
    
    public String getName(){
        return this.getClass().getSimpleName();
    }
}
