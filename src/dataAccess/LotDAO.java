package dataAccess;
import exception.ConnectionException;
import exception.LotException;
import model.Employee;
import model.Lot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class LotDAO implements LotDAOInterface
{
    private final Connection connection;

    public LotDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public ArrayList<Lot> readAll() throws LotException {
        ArrayList<Lot> lots = new ArrayList<>();
        String sqlInstruction = "SELECT * FROM lot";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            ResultSet data = preparedStatement.executeQuery();
            Lot lot;

            while (data.next())
            {
                int number = data.getInt("number");
                String provenance = data.getString("provenance");
                LocalDate date = data.getDate("receptionDate").toLocalDate();
                Employee employee = data.getObject("employee", Employee.class);

                lots.add(new Lot(number, provenance, date, employee));
            }
            return lots;
        }
        catch (SQLException exception)
        {
            throw new LotException("Erreur de lecture des lots", exception);
        }
    }
}
