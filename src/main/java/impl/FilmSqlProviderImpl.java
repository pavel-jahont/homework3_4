package impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Film;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ConnectionRepository;
import service.FilmSqlProvider;

public class FilmSqlProviderImpl implements FilmSqlProvider {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();

    @Override
    public void createTableFilm(Connection connection) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE film(\n" +
                "    name VARCHAR(50) NOT NULL PRIMARY KEY,\n" +
                "    duration INT(11) NULL,\n" +
                "    ticket_price INT(11) NULL\n" +
                "    )")) {
            statement.executeUpdate();
            logger.info("Table film is created");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteByTicketPrice(Connection connection, int ticketPrice) throws Exception {
        try (
                PreparedStatement statement = connection.prepareStatement("DELETE FROM film WHERE ticket_price=?");
        ) {
            statement.setInt(1, ticketPrice);
            int affectedRows = statement.executeUpdate();
            logger.info("Deleted row(s): " + affectedRows);
            if (affectedRows == 0) {
                throw new SQLException(("Deleting film failed, no rows affected."));
            }
        }
    }

    @Override
    public void updateNameByMaxTicketPrice(Connection connection, String name, int maxTicketPrice) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE film SET name=? WHERE ticket_price=?"
        );
        ) {
            statement.setString(1, name);
            statement.setInt(2, maxTicketPrice);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(("Updating film failed, no rows affected."));
            }
            connection.commit();
        }
    }

    @Override
    public void deleteTableFilm(Connection connection) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement("DROP TABLE film")) {
            statement.executeUpdate();
            logger.info("Table film is deleted");
        }
    }

    @Override
    public Film addFilm(Connection connection, Film film) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO film(name, duration, ticket_price) VALUES (?,?,?)"
        );
        ) {
            statement.setString(1, film.getName());
            statement.setInt(2, film.getDuration());
            statement.setInt(3, film.getTicketPrice());
            statement.executeUpdate();
            return film;
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new SQLException("Creating film failed");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new Exception("No connection!");
        }
    }

    @Override
    public int findMaxTicketPrice(Connection connection) throws SQLException {
        int maxTicketPrice = 0;
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT MAX(ticket_price) as max_ticket_price from film");
        ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                maxTicketPrice = rs.getInt("max_ticket_price");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new SQLException("Can not possible to get max ticket price from film");
        }
        return maxTicketPrice;
    }

    @Override
    public int findMinTicketPrice(Connection connection) throws SQLException {
        int minTicketPrice = 0;
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT MIN(ticket_price) as min_ticket_price from film");
        ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                minTicketPrice = rs.getInt("min_ticket_price");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new SQLException("Can not possible to get min ticket price from film");
        }
        return minTicketPrice;
    }

    private Film getFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Integer duration = rs.getInt("duration");
        Integer ticketPrice = rs.getInt("ticket_price");
        return Film.newBuilder()
                .id(id)
                .name(name)
                .duration(duration)
                .ticketPrice(ticketPrice)
                .build();
    }
}