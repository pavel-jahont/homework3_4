package impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.QueriesSqlProvider;

public class QueriesSqlProviderImpl implements QueriesSqlProvider {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void useDatabase(Connection connection, String databaseName) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeQuery("use " + databaseName);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new Exception("No connection!");
        }
    }

    @Override
    public void createDatabase(Connection connection, String database) throws Exception {
        int affectedDatabase = 0;
        try (PreparedStatement statement = connection.prepareStatement("CREATE DATABASE  " + database)) {
            affectedDatabase = statement.executeUpdate();
            logger.info("Count of created database(s): " + affectedDatabase);
            logger.info("Database " + database + " is created");
        } catch (SQLException ex) {
            if (affectedDatabase == 0) {
                logger.info("Database already exist!");
            } else {
                logger.error(ex.getMessage(), ex);
                throw new Exception("Inadmissible count of affected rows!");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new Exception("No connection!");
        }
    }

    @Override
    public void deleteDatabase(Connection connection, String database) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE " + database);
            logger.info("Database " + database + " has deleted");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new Exception("No connection!");
        }
    }
}