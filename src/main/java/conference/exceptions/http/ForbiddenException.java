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
public class ForbiddenException extends BadRequestException{
    public ForbiddenException(){
        super(403);
    }
    
    public ForbiddenException(String message){
        super(message, 403);
    }
}
