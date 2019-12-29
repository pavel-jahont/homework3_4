package service;

import java.sql.Connection;
import java.sql.SQLException;

import model.Film;

public interface FilmSqlProvider {

    void createTableFilm(Connection connection) throws Exception;

    void deleteByTicketPrice(Connection connection, int ticketPrice) throws Exception;

    void updateNameByMaxTicketPrice(Connection connection, String name, int maxTicketPrice) throws SQLException;

    void deleteTableFilm(Connection connection) throws Exception;

    int findMaxTicketPrice(Connection connection) throws SQLException;

    int findMinTicketPrice(Connection connection) throws SQLException;

    Film addFilm(Connection connection, Film film) throws Exception;
}
