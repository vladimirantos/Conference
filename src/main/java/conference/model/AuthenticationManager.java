package conference.model;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationManager implements AuthenticationProvider{

    private static final Logger logger = Logger.getLogger(AuthenticationManager.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        logger.info("Jmeno: "+ name);

        // use the credentials to try to authenticate against the third party system
        if (authenticate(name, password)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        } else {
            throw new AuthenticationServiceException("Unable to auth against third party systems");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    private boolean authenticate(String name, String password){
        return name.equals("test") && password.equals("123456");
    }
}