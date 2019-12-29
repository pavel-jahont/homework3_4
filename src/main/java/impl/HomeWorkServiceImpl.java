package impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

import model.Film;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ConnectionRepository;
import service.HomeWorkService;
import service.QueriesSqlProvider;
import util.DurationUtil;

public class HomeWorkServiceImpl implements HomeWorkService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private String dataHomeWork34 = "jd2_homework3_4";
    private String hotFilm = "Hot film!";

    @Override
    public void runFirstSevice() throws Exception {
        ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();
        Connection connection = connectionRepository.getConnection();

        QueriesSqlProvider sqlProvider = new QueriesSqlProviderImpl();
        sqlProvider.createDatabase(connection, dataHomeWork34);

        sqlProvider.useDatabase(connection, dataHomeWork34);

        FilmSqlProviderImpl filmSqlProvider = new FilmSqlProviderImpl();
        filmSqlProvider.createTableFilm(connection);

        connection.setAutoCommit(false);
        for (int i = 0; i < 5; i++) {
            Film film = Film.newBuilder()
                    .name("Film" + i)
                    .duration(new DurationUtil().getDuration())
                    .ticketPrice(100 + i)
                    .build();
            logger.info(film.getName() + " " + film.getDuration() + " " + film.getTicketPrice());
            filmSqlProvider.addFilm(connection, film);
        }
        connection.commit();
        connection.setAutoCommit(true);

        int maxTicketPrice = filmSqlProvider.findMaxTicketPrice(connection);
        logger.info("MAX ticket price is: " + maxTicketPrice);

        int minTicketPrice = filmSqlProvider.findMinTicketPrice(connection);
        logger.info("MIN ticket price is: " + minTicketPrice);

        filmSqlProvider.deleteByTicketPrice(connection, minTicketPrice);

        connection.setAutoCommit(false);
        filmSqlProvider.updateNameByMaxTicketPrice(connection, hotFilm, maxTicketPrice);
        connection.setAutoCommit(true);

        filmSqlProvider.deleteTableFilm(connection);

        sqlProvider.deleteDatabase(connection, dataHomeWork34);
    }

    @Override
    public void runSecondService() {

    }
}