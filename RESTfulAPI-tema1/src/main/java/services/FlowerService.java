package services;

import dataprovider.Data;
import enums.FlowerStatusEnum;
import exceptions.NotFoundException;
import models.Flower;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlowerService {

    public static List<Flower> findFlowersByUserMail (String userMail) {
        String query = "SELECT flower_id, name, kind, planting_date, owner_email, status FROM flowers WHERE owner_email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            statement.setString(1, userMail);

            ResultSet resultSet = statement.executeQuery();

            List<Flower> allFlowers = new ArrayList<>();

            while (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt("flower_id"));
                flower.setName(resultSet.getString("name"));
                flower.setKind(resultSet.getString("kind"));
                flower.setPlantingDate(resultSet.getDate("planting_date"));
                flower.setOwnerEmail(resultSet.getString("owner_email"));
                flower.setStatus(resultSet.getString("status"));
                allFlowers.add(flower);
            }

            return allFlowers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Flower findFlowerById(int id) {

        String query = "SELECT flower_id, name, kind, planting_date, owner_email, status FROM flowers WHERE flower_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt("flower_id"));
                flower.setName(resultSet.getString("name"));
                flower.setKind(resultSet.getString("kind"));
                flower.setPlantingDate(resultSet.getDate("planting_date"));
                flower.setOwnerEmail(resultSet.getString("owner_email"));
                flower.setStatus(resultSet.getString("status"));
                return flower;
            } else {
                return null; // Flower with the specified ID not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Flower> getAllFlowers () throws NotFoundException {
        String query = "SELECT flower_id, name, kind, planting_date, owner_email, status FROM flowers";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            List<Flower> allFlowers = new ArrayList<>();

            while (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt("flower_id"));
                flower.setName(resultSet.getString("name"));
                flower.setKind(resultSet.getString("kind"));
                flower.setPlantingDate(resultSet.getDate("planting_date"));
                flower.setOwnerEmail(resultSet.getString("owner_email"));
                flower.setStatus(resultSet.getString("status"));
                allFlowers.add(flower);
            }

            if (allFlowers.isEmpty()) {
                throw new NotFoundException("Cannot find flowers!");
            }

            return allFlowers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static void saveFlower(Flower flower) {

        String query = "INSERT INTO flowers (name, kind, planting_date, owner_email, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            
            statement.setString(1, flower.getName());
            statement.setString(2, flower.getKind());
            statement.setDate(3, new Date(flower.getPlantingDate().getTime()));
            statement.setString(4, flower.getOwnerEmail());
            statement.setString(5, FlowerStatusEnum.CULTIVATION_PROCESS.name().toLowerCase());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateFlower(Flower flower) {

        String query = "UPDATE flowers SET name = ?, kind = ?, planting_date = ?, owner_email = ?, status = ? WHERE flower_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, flower.getName());
            statement.setString(2, flower.getKind());
            statement.setDate(3, new Date(flower.getPlantingDate().getTime()));
            statement.setString(4, flower.getOwnerEmail());
            statement.setString(5, flower.getStatus());
            statement.setInt(6, flower.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteFlower(Flower flower) {

        String query = "DELETE FROM flowers WHERE flower_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, flower.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listFlower (int id, BigDecimal price) throws NotFoundException {
        Flower flower = findFlowerById(id);

        if (flower == null) {
            throw new NotFoundException(String.format("Flower with id: %d not found", id));
        }

        flower.setStatus(FlowerStatusEnum.LISTED.name().toLowerCase());

        updateFlower(flower);
    }

    public static void sellFlower (int id, String sellerMail) throws NotFoundException {
        Flower flower = findFlowerById(id);

        if (flower == null) {
            throw new NotFoundException(String.format("Flower with id: %d not found", id));
        }

        flower.setStatus(FlowerStatusEnum.SELLED.name().toLowerCase());

        updateFlower(flower);
    }

}
