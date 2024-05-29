package api;

import authorization.AuthenticationRequest;
import authorization.AuthorizationToken;
import authorization.RegisterRequest;
import exceptions.AuthenticationException;
import exceptions.RegisterConflictException;
import handlers.Response;

import java.security.NoSuchAlgorithmException;


public interface AuthorizationApi {

    Response register(RegisterRequest registerRequest) throws RegisterConflictException, NoSuchAlgorithmException;

    AuthorizationToken authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationException, NoSuchAlgorithmException;
}
