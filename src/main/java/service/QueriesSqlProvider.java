package service;

import java.sql.Connection;

public interface QueriesSqlProvider {

    void useDatabase(Connection connection, String database) throws Exception;

    void createDatabase(Connection connection, String database) throws Exception;

    void deleteDatabase(Connection connection, String database) throws Exception;
}
