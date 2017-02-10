package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comptes.model.db.entity.DerRappro;
import comptes.util.MyDate;
import comptes.util.log.LogRappro;

public class DerRapproDAO extends DAO<DerRappro> {
	public void create(DerRappro myDerRappro) {
		PreparedStatement statement=null;
		LogRappro.logDebug("derRappro ; '" +myDerRappro	+"'");
		try {

			statement = connection.prepareStatement("INSERT INTO der_rappro (id , derSolde , dateDerRappro , dateDerRapproLong ) VALUES(?,?,?,?)");

			statement.setDouble(2, myDerRappro.getDerSolde());
			statement.setString(3, myDerRappro.getDateDerRappro().toDbFormat());
			statement.setLong(4, myDerRappro.getDateDerRappro().toLongValue());
			statement.executeUpdate();
			LogRappro.logDebug(" DAO create derrappro");	
		} catch (SQLException e) {
			LogRappro.logError("SQL Exception dans DerRappro DAO create ", e);
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogRappro.logError("SQL Exception dans echeancier DAO create sur close statement", e);
			}
		}
	}

	public DerRappro find(int id) {
		DerRappro myDerRappro = null;
		try {
			LogRappro.logDebug("debut try find DerRapproDAO");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM der_rappro WHERE id = '" + id + "'");
			LogRappro.logDebug("rs = " + rs.getInt("id"));
			if (rs.next()) {
				myDerRappro = new DerRappro(rs.getInt("id"), rs.getDouble("derSolde"), new MyDate(rs.getString("dateDerRappro")),rs.getLong("dateDerRapproLong"));
			}
		} catch (SQLException e) {
			LogRappro.logError("SQL Exception dans DerRappro DAO find", e);
		}
		return myDerRappro;
	}
	//
	public void update(DerRappro myDerRappro) {
		Statement statement = null;
		try {
			LogRappro.logDebug("debut try update DerRappro");
			LogRappro.logDebug("myDerRappro" + myDerRappro);
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE der_rappro SET derSolde='" + myDerRappro.getDerSolde()
			+ "',dateDerRappro='" + myDerRappro.getDateDerRappro() + "',dateDerRapproLong=" + myDerRappro.getDateDerRappro().toLongValue()
					+ " where Id=" +myDerRappro.getId());
			LogRappro.logDebug("dans try update DerRappro : Update : UPDATE der_rappro SET derSolde='" + myDerRappro.getDerSolde()
			+ "',dateDerRappro='" + myDerRappro.getDateDerRappro() + "',dateDerRapproLong=" + myDerRappro.getDateDerRappro().toLongValue()
					+ " where Id=" +myDerRappro.getId());
		} catch (SQLException e) {
			LogRappro.logError("SQL Exception dans DerRappro DAO update", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogRappro.logError("SQL Exception dans DerRappro DAO update sur statement close", e);
			}
		}
	}

	public void delete(DerRappro myDerRappro) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from der_rappro where Id=" + myDerRappro.getId());
		} catch (SQLException e) {
			LogRappro.logError("SQL Exception dans DerRappro DAO delete", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogRappro.logError("SQL Exception dans DerRappro DAO update statement close", e);			}
		}

	}

	@Override
	public ArrayList<DerRappro> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
