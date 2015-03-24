/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conference.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vladimír Antoš <vladimirantos@seznam.cz>
 * @version 1.0
 */
//@ResponseStatus(value=HttpStatus.BAD_GATEWAY)
public class BadGatewayException extends BadRequestException{
    
    public BadGatewayException(){
        super(502);
    }
    
    public BadGatewayException(String message){
        super(message, 502);
    }
}
