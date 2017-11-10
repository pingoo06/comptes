package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import comptes.model.bo.OperationBO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Operation;
import comptes.model.db.entity.Tiers;
import comptes.util.MyDate;
import comptes.util.log.LogOperation;
import comptes.util.log.Logger;

public class OperationDAO extends DAO<Operation> {
	public void create(Operation myOperation) {
		LogOperation.logDebug("Début");
		PreparedStatement statement = null;
		try {
			Logger.logDebug("Dans try dans create operation dans DAO operation");
			statement = connection.prepareStatement(
					"INSERT INTO operation (id,  typeOpe,  dateOpe,  montantOpe,  categOpeId, tiersId, detailOpe, etatOpe, echID, dateOpeLong)VALUES(?,?,?,?,?,?,?,?,?,?)");
			statement.setString(2, myOperation.getTypeOpe());
			statement.setString(3, myOperation.getDateOpe().toDbFormat());
			statement.setDouble(4, myOperation.getMontantOpe());
			statement.setInt(5, myOperation.getCategOpeId());
			statement.setInt(6, myOperation.getTiersId());
			statement.setString(7, myOperation.getDetailOpe());
			statement.setString(8, myOperation.getEtatOpe());
			statement.setInt(9, myOperation.getEchId());
			LogOperation.logDebug("myOperation.getEchId() " + myOperation.getEchId());
			statement.setLong(10, myOperation.getDateOpe().toLongValue());
			statement.executeUpdate();
		} catch (SQLException e) {
			LogOperation.logError("SQL Exception dans Operation DAO create ",e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogOperation.logError("SQL Exception dans Operation DAO create sur le statement Close ",e);
			}
		}
	}

		public int createReturnId (Operation myOperation) {
			LogOperation.logDebug("Début createReturnId");
			create(myOperation) ;
			return findLastId();
		}
		
		
	public Operation find(int id) {
		Operation myOperation = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM operation WHERE id = '" + id + "'");
			if (rs.next()) {
				myOperation = operationFromRow(rs);
			}
		} catch (SQLException e) {
			LogOperation.logError("SQL Exception dans Operation DAO find  ",e);
		}
		return myOperation;
	}

	public int findLastId () {
		int derOpeId = 0;
		try {
			LogOperation.logInfo("Début");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT max(id) FROM operation ");
			if (rs.next()) {
				derOpeId = rs.getInt(1);
				LogOperation.logDebug("derOpeId" + derOpeId);
			}
		} catch (SQLException e) {
			LogOperation.logError("FindLastID Operation KO", e);
		}
		return derOpeId;
	}
	
	public ArrayList<Operation> findAll() {
		Operation myOperation;
		ArrayList<Operation> myOperationList = null;
		try {
			myOperationList = new ArrayList<Operation>();
			LogOperation.logDebug("select all Operation début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM operation ");
			LogOperation.logDebug("rs = " + rs.getInt("id"));
			while (rs.next()) {
				myOperation = operationFromRow(rs);
				myOperationList.add(myOperation);
			}
			statement.close();

		} catch (SQLException e) {
			LogOperation.logError("SQL Exception dans Operation DAO findAll ",e);
		}
		return myOperationList;
	}

	public ArrayList<Operation> findOpeNr() {
		Operation myOperation = null;
		ArrayList<Operation> myOperationList = null;

		myOperationList = new ArrayList<Operation>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs;
			rs = statement.executeQuery("SELECT * FROM  operation where etatOpe='NR' ");
			while (rs.next()) {
				myOperation = operationFromRow(rs);
				myOperationList.add(myOperation);
			}
		} catch (SQLException e) {
			Logger.logError("findOpeNr sql error", e);
		}
		return myOperationList;
	}
	
	public long findDerChq() {
		long derNumChqLong=0;
		try {
			Statement statement = connection.createStatement();
			ResultSet rs;
			rs = statement.executeQuery("select operation.typeOpe from operation where operation.typeOpe > 0 and operation.typeOpe  < 999999999999 and operation.id in (select max (operation.id) from operation where operation.typeOpe > 0 and operation.typeOpe < 999999999999) ");
			while (rs.next()) {
				derNumChqLong = Long.parseLong(rs.getString(1));
			}
		} catch (SQLException e) {
			LogOperation.logError("findDerChq sql error", e);
		}
		return derNumChqLong;
	}

	public ArrayList<OperationBO> findAllOpeBO() {
		OperationBO myOperationBO = null;
		Operation myOperation = null;
		Tiers myTiers = null;
		Categorie myCategorie = null;
		ArrayList<OperationBO> myOperationBOList = null;
		try {
			myOperationBOList = new ArrayList<OperationBO>();
			LogOperation.logDebug("select all Operation début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM operation as o INNER JOIN tiers as t on o.tiersId = t.id INNER JOIN categorie as c on o.categOpeId = c.id");
			while (rs.next()) {
				myOperation = operationFromRow(rs);
				myOperationBO = new OperationBO(myOperation);
				myTiers = new Tiers(rs.getInt(11), rs.getString(12), rs.getString(13));
				myOperationBO.setTiersBO(myTiers);
				myCategorie = new Categorie(rs.getInt(14), rs.getString(15));
				myOperationBO.setCategorieBo(myCategorie);
				myOperationBOList.add(myOperationBO);
			}
			statement.close();
		} catch (SQLException e) {
			LogOperation.logError("SQL Exception dans Operation DAO findAllOpeBO ",e);
		}
		return myOperationBOList;
	}

	public ArrayList<OperationBO> findOpeBOFiltre(String whereClause) {
		OperationBO myOperationBO = null;
		Operation myOperation = null;
		Tiers myTiers = null;
		Categorie myCategorie = null;
		ArrayList<OperationBO> myOperationBOList = null;
		try {
			myOperationBOList = new ArrayList<OperationBO>();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM operation as o INNER JOIN tiers as t on o.tiersId "
					+ "= t.id INNER JOIN categorie as c on o.categOpeId = c.id " + whereClause);
			while (rs.next()) {
				myOperation = operationFromRow(rs);
				myOperationBO = new OperationBO(myOperation);
				myTiers = new Tiers(rs.getInt(11), rs.getString(12), rs.getString(13));
				myOperationBO.setTiersBO(myTiers);
				myCategorie = new Categorie(rs.getInt(14), rs.getString(15));
				myOperationBO.setCategorieBo(myCategorie);
				myOperationBOList.add(myOperationBO);
			}
			statement.close();

		} catch (SQLException e) {
			LogOperation.logError("dans operationDAO findOpeBOFiltre : findOpeBOFiltre KO",e);
		}
		return myOperationBOList;
	}

	public void update(Operation myOperation) {
		Statement statement = null;
		try {
			LogOperation.logDebug("debut try update operation");
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE operation SET typeOpe='" + myOperation.getTypeOpe() + "',dateOpe='"
					+ myOperation.getDateOpe().toDbFormat() + "',montantOpe=" + myOperation.getMontantOpe() + ",categOpeId="
					+ myOperation.getCategOpeId() + ",tiersID=" + myOperation.getTiersId() + ", detailOpe='"
					+ myOperation.getDetailOpe() + "',etatOpe='" + myOperation.getEtatOpe() + "', echId="
					+ myOperation.getEchId() + myOperation.getEchId() + " where Id=" + myOperation.getId());

		} catch (SQLException e) {
			LogOperation.logError("update operation KO",e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogOperation.logError("close statement KO dans update operation",e);
			}
		}
	}

	public void delete(Operation myOperation) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from operation where Id=" + myOperation.getId());
		} catch (SQLException e) {
			LogOperation.logError("delete operation KO ",e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogOperation.logError("close statement KO dans delete operation",e);
			}
		}

	}
	
	public static Operation operationFromRow(ResultSet rs) {
		try {
			return new Operation(rs.getInt("id"), rs.getString("typeOpe"), new MyDate(rs.getString("dateOpe")),
					rs.getDouble("montantOpe"), rs.getInt("categOpeId"), rs.getInt("tiersId"),
					rs.getString("detailOpe"), rs.getString("etatOpe"), rs.getInt("echId"));
		} catch (SQLException e) {
			LogOperation.logError("Error while building operation from resulset", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param somme les montants jusqu'a endDate 
	 * @return le solde a cette date, 0 sinon
	 */
	public double sumOperationUntil(MyDate endDate) {
		Statement statement = null;
		try {
			LogOperation.logDebug("debut calcule solde jusqu'au : "+endDate);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select sum(montantOpe) from operation where dateOpe <='"
					+ endDate.toDbFormat() + "'");
			return rs.getDouble(1);

		} catch (SQLException e) {
			LogOperation.logError("Fail calcul du solde",e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogOperation.logError("close statement KO dans solde operation",e);
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * @return le solde jusqu'a aujourd'hui
	 */
	public double sumOperation() {
		return sumOperationUntil(new MyDate());
	}

	public double sumOperationPointe() {
		Statement statement = null;
		try {
			LogOperation.logDebug("debut calcule solde pointé");
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select sum(montantOpe) from operation where etatOpe = 'X'");
			return rs.getDouble(1);

		} catch (SQLException e) {
			LogOperation.logError("Fail calcul du solde pointé",e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogOperation.logError("close statement KO dans solde operation poité",e);
			}
		}
		return 0;
	}
	
	
}
