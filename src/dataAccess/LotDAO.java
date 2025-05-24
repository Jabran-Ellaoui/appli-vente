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

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction); ResultSet data = preparedStatement.executeQuery())
        {
            while (data.next())
            {
                lots.add(new Lot(data.getInt("number"), data.getString("provenance"), data.getDate("reception_date").toLocalDate(), new Employee(data.getInt("manager"))));
            }
            return lots;
        }
        catch (SQLException exception)
        {
            throw new LotException("Erreur de lecture des lots", exception);
        }
    }
}
