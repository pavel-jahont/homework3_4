package impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ConnectionRepository;
import util.PropertyUtil;

import static service.ConnectionConstant.DATABASE_PASSWORD;
import static service.ConnectionConstant.DATABASE_URL;
import static service.ConnectionConstant.DATABASE_USERNAME;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private PropertyUtil propertyService = new PropertyUtil();

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    propertyService.getProperty(DATABASE_URL),
                    propertyService.getProperty(DATABASE_USERNAME),
                    propertyService.getProperty(DATABASE_PASSWORD)
            );
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("App cannot get connection to database");
        }
    }
}