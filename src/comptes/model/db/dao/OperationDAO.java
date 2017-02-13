package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
			statement.setLong(10, myOperation.getDateOpe().toLongValue());
			statement.executeUpdate();
			// System.out.println("dans Operation DAO create arrive après
			// execute statement de create operation");
		} catch (SQLException e) {
			System.out.println("SQL Exception dans Operation DAO create ");
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("SQL Exception dans Operation DAO create sur le statement Close ");
				e.printStackTrace();
			}
		}
	}

	public Operation find(int id) {
		Operation myOperation = null;
		try {
			// System.out.println("debut try find operation");
			// System.out.println("select unique Operation début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM operation WHERE id = '" + id + "'");
			// System.out.println("rs = " + rs.getInt("id"));
			if (rs.next()) {
				myOperation = operationFromRow(rs);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception dans Operation DAO find  ");
			e.printStackTrace();
		}
		return myOperation;
	}
	//

	public ArrayList<Operation> findAll() {
		Operation myOperation;
		ArrayList<Operation> myOperationList = null;
		try {
			myOperationList = new ArrayList<Operation>();
			// System.out.println("select all Operation début try");
			Statement statement = connection.createStatement();
			// ResultSet rs = statement.executeQuery("SELECT * FROM operation
			// where dateOpeLong > 14609");
			ResultSet rs = statement.executeQuery("SELECT * FROM operation ");
			// System.out.println("rs = " + rs.getInt("id"));
			while (rs.next()) {
				myOperation = operationFromRow(rs);
				myOperationList.add(myOperation);
			}
			statement.close();

		} catch (SQLException e) {
			System.out.println("SQL Exception dans Operation DAO findAll ");
			e.printStackTrace();
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

	// nico est ce ici qu'il fallait créer cette fonction ?
	public ArrayList<OperationBO> findAllOpeBO() {
		OperationBO myOperationBO = null;
		Operation myOperation = null;
		Tiers myTiers = null;
		Categorie myCategorie = null;
		ArrayList<OperationBO> myOperationBOList = null;
		try {
			myOperationBOList = new ArrayList<OperationBO>();
			// System.out.println("select all Operation début try");
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
			System.out.println("SQL Exception dans Operation DAO findAllOpeBO ");
			e.printStackTrace();
		}
		return myOperationBOList;
	}

	// //nico est ce ici qu'il fallait créer cette fonction ?
	// public ArrayList<OperationBO> findOpeBOTiers(String libTiersParam) {
	// OperationBO myOperationBO = null;
	// Operation myOperation=null;
	// Tiers myTiers = null;
	// Categorie myCategorie = null;
	// ArrayList<OperationBO> myOperationBOList = null;
	// try {
	// myOperationBOList = new ArrayList<OperationBO>();
	// // System.out.println("select all Operation début try");
	// Statement statement = connection.createStatement();
	// System.out.println("dans operationDAO findOpeBOTiers : le libTiers à
	// filtrer: "+ libTiersParam );
	// ResultSet rs = statement.executeQuery("SELECT * FROM operation as o INNER
	// JOIN tiers as t on o.tiersId "
	// + "= t.id INNER JOIN categorie as c on o.categOpeId = c.id where
	// t.libTiers='"+libTiersParam+"'");
	// // System.out.println("rs = " + rs.getInt("id"));
	// while (rs.next()) {
	// myOperation = new Operation(rs.getInt(1), rs.getString(2),
	// rs.getString(3),
	// rs.getDouble(4), rs.getInt(5), rs.getInt(6),
	// rs.getString(7), rs.getString(8), rs.getInt(9), rs.getLong(10));
	// myOperationBO = new OperationBO(myOperation);
	// myTiers=new Tiers(rs.getInt(11),rs.getString(12),rs.getInt(13));
	// myOperationBO.setTiersBO(myTiers);
	// System.out.println("dans operationDAO findOpeBOTiers : le tiers : " +
	// myTiers);
	// myCategorie=new Categorie(rs.getInt("id"), rs.getString("libCateg"),
	// rs.getString("libSousCateg"), rs.getInt("derOpeId"));
	// myOperationBO.setCategorieBo(myCategorie);
	// myOperationBOList.add(myOperationBO);
	// }
	// statement.close();
	//
	// } catch (SQLException e) {
	// System.out.println("dans operationDAO findOpeBOTiers : findOpeBOTiers
	// KO");
	// e.printStackTrace();
	// }
	// return myOperationBOList;
	// }
	//
	// //moi si cette fonction marche, supprimer findOpeBOTiers

	// nico est ce ici qu'il fallait créer cette fonction ?

	public ArrayList<OperationBO> findOpeBOFiltre(String whereClause) {
		OperationBO myOperationBO = null;
		Operation myOperation = null;
		Tiers myTiers = null;
		Categorie myCategorie = null;
		ArrayList<OperationBO> myOperationBOList = null;
		try {
			myOperationBOList = new ArrayList<OperationBO>();
			// System.out.println("select all Operation début try");
			Statement statement = connection.createStatement();
			// System.out.println("dans operationDAO findOpeBOFiltre : la where
			// clause : "+ whereClause );
			ResultSet rs = statement.executeQuery("SELECT * FROM operation as o INNER JOIN tiers as t on o.tiersId "
					+ "= t.id INNER JOIN categorie as c on o.categOpeId = c.id " + whereClause);
			// System.out.println("rs = " + rs.getInt("id"));
			while (rs.next()) {
				myOperation = operationFromRow(rs);
				myOperationBO = new OperationBO(myOperation);
				myTiers = new Tiers(rs.getInt(11), rs.getString(12), rs.getString(13));
				myOperationBO.setTiersBO(myTiers);
				// System.out.println("dans operationDAO findOpeBOFiltre : le
				// tiers : " + myTiers);
				myCategorie = new Categorie(rs.getInt(14), rs.getString(15));
				// System.out.println("dans operationDAO findOpeBOFiltre : la
				// categorie : " + myCategorie);
				myOperationBO.setCategorieBo(myCategorie);
				myOperationBOList.add(myOperationBO);
			}
			statement.close();

		} catch (SQLException e) {
			System.out.println("dans operationDAO findOpeBOFiltre : findOpeBOFiltre KO");
			e.printStackTrace();
		}
		return myOperationBOList;
	}

	public void update(Operation myOperation) {
		Statement statement = null;
		try {
			// System.out.println("debut try update operation");
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE operation SET typeOpe='" + myOperation.getTypeOpe() + "',dateOpe='"
					+ myOperation.getDateOpe().toDbFormat() + "',montantOpe=" + myOperation.getMontantOpe() + ",categOpeId="
					+ myOperation.getCategOpeId() + ",tiersID=" + myOperation.getTiersId() + ", detailOpe='"
					+ myOperation.getDetailOpe() + "',etatOpe='" + myOperation.getEtatOpe() + "', echId="
					+ myOperation.getEchId() + myOperation.getDateOpe().toLongValue() + " where Id=" + myOperation.getId());
			// System.out.println("Dans update operation dateOpe= " +
			// myOperation.getDateOpe());
			// System.out.println("Dans update operation dateOpelong = " +
			// myOperation.getDateOpeLong());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				// connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void delete(Operation myOperation) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from operation where Id=" + myOperation.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
}
