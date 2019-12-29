package service;

import java.sql.Connection;

public interface ConnectionRepository {

    Connection getConnection();
}
