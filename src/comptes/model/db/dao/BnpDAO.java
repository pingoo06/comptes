package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Bnp.OperationType;
import comptes.util.MyDate;
import comptes.util.log.LogBnp;

public class BnpDAO extends DAO<Bnp> {
	public void create(Bnp myBnp) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(
					"INSERT INTO bnp (id, dateBnp, libCourtBnp,  libTypeOpeBnp,  libOpeBnp,  montantBnp,etatBnp,typeOpeBnp,"
							+ "dateBnpCalc,chqNumberBnp)VALUES(?,?,?,?,?,?,?,?,?,?)");
			statement.setString(2, myBnp.getDateBnp().toDbFormat());
			statement.setString(3, myBnp.getLibCourtBnp());
			statement.setString(4, myBnp.getLibTypeOpeBnp());
			statement.setString(5, myBnp.getLibOpeBnp());
			statement.setDouble(6, myBnp.getMontantBnp());
			statement.setString(7, myBnp.getEtatBnp());
			statement.setString(8, myBnp.getTypeOpeBnp().toString());
			statement.setLong(9, myBnp.getDateBnpCalc().toLongValue());
			statement.setString(10, myBnp.getChqNumberBnp());
			statement.executeUpdate();
		} catch (SQLException e) {
			LogBnp.logError("failed to create BNP", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogBnp.logError("failed to create BNP", e);

			}
		}
	}

	public Bnp find(int id) {
		Bnp myBnp = null;
		try {
			LogBnp.logDebug("select unique Bnp debut try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM bnp WHERE id = '" + id + "'");
			LogBnp.logDebug("rs = " + rs.getInt("id"));
			if (rs.next()) {
				myBnp = new Bnp(rs.getInt("id"), new MyDate(rs.getString("dateBnp")), rs.getString("libCourtBnp"),
						rs.getString("libTypeOpeBnp"), rs.getString("libOpeBnp"), rs.getDouble("montantBnp"),
						rs.getString("etatBnp"), OperationType.valueOf(rs.getString("typeOpeBnp")),
						new MyDate(rs.getLong("dateBnpCalc")), rs.getString("chqNumberBnp"));
			}
		} catch (SQLException e) {
			LogBnp.logError("failed to create BNP", e);
		}
		return myBnp;
	}
	//

	public ArrayList<Bnp> findAll() {
		Bnp myBnp = null;
		ArrayList<Bnp> myBnpList = null;
		try {
			myBnpList = new ArrayList<Bnp>();
			LogBnp.logDebug("select all Bnp debut try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM bnp");
			while (rs.next()) {
				myBnp = new Bnp(rs.getInt("id"), new MyDate(rs.getString("dateBnp")), rs.getString("libCourtBnp"),
						rs.getString("libTypeOpeBnp"), rs.getString("libOpeBnp"), rs.getDouble("montantBnp"),
						rs.getString("etatBnp"), OperationType.valueOf(rs.getString("typeOpeBnp")),
						new MyDate(rs.getLong("dateBnpCalc")), rs.getString("chqNumberBnp"));
				myBnpList.add(myBnp);
			}
			statement.close();

		} catch (SQLException e) {
			LogBnp.logError("failed to create BNP", e);
		}
		return myBnpList;
	}
	
	

	public void update(Bnp myBnp) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE bnp SET dateBnp ='" + myBnp.getDateBnp().toDbFormat() + "', libCourtBnp='"
					+ myBnp.getLibCourtBnp() + "',libTypeOpeBnp='" + myBnp.getLibTypeOpeBnp() + "',libOpeBnp='"
					+ myBnp.getLibOpeBnp() + "',montantBnp=" + myBnp.getMontantBnp() + ",etatBnp='" + myBnp.getEtatBnp()
					+ "typeOpeBnp" + myBnp.getTypeOpeBnp() + "dateBnpCalc" + myBnp.getDateBnpCalc().toLongValue() + "chqNumberBnp"
					+ myBnp.getChqNumberBnp() + "' where Id=" + myBnp.getId());
		} catch (SQLException e) {
			LogBnp.logError("failed to create BNP", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogBnp.logError("failed to create BNP", e);
			}
		}
	}

	public void delete(Bnp myBnp) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from bnp where Id=" + myBnp.getId());
		} catch (SQLException e) {
			LogBnp.logError("failed to create BNP", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogBnp.logError("failed to create BNP", e);
			}
		}
	}
	
	public void truncate() {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from bnp");
		} catch (SQLException e) {
			LogBnp.logError("failed to truncate Table BNP", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogBnp.logError("failed to truncate BNP", e);
			}
		}
	}
	

	public boolean isFull() {
		int res=0;
		try {
			LogBnp.logDebug("début isFull de Bnp");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(1) FROM bnp ");
			LogBnp.logDebug("rs = " + rs.getInt(1));
			res=rs.getInt(1);
		} 
		catch (SQLException e) {
			LogBnp.logError("failed isFull BNP", e);
		}
		return  res != 0;
	}
}
