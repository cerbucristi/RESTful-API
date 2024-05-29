package api;

import exceptions.NotFoundException;
import handlers.Response;
import models.Flower;

import java.math.BigDecimal;
import java.util.List;

public interface FlowerApi {

    List<Flower> getFlowersByUserEmail (String email);

    List<Flower> getFlowers() throws NotFoundException;

    Flower getFlower(int id) throws NotFoundException;

    Response createFlower(Flower flower);

    Response listFlower(int id, BigDecimal price) throws NotFoundException;

    Response sellFlower(int id, String sellerMail) throws NotFoundException;

    Response updateFlower(Flower flower) throws NotFoundException;

    Response deleteFlower(int id) throws NotFoundException;
    
}
