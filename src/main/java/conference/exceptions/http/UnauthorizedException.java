/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conference.exceptions.http;

/**
 * @author Vladimír Antoš <vladimirantos@seznam.cz>
 * @version 1.0
 */
//@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Unauthorized")
public class UnauthorizedException extends BadRequestException{
    public UnauthorizedException(){
        super(401);
    }
    
    public UnauthorizedException(String message){
        super(message, 401);
    }
}
