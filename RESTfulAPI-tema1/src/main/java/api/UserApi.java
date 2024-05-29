package api;

import exceptions.NotFoundException;
import handlers.Response;
import models.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserApi {
    User getUserByEmail( String email) throws NotFoundException;
    Response updateUser(User user) throws NotFoundException, NoSuchAlgorithmException;

    Response deleteUser(String email) throws NotFoundException;

}
