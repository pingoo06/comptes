package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comptes.model.db.entity.Tiers;
import comptes.util.log.LogTiers;

public class TiersDAO extends DAO<Tiers> {
	public void create(Tiers myTiers) {
		PreparedStatement statement = null;
		try {
//			System.out.println("Début create tiers : Tiers" + myTiers);
			statement = connection.prepareStatement("INSERT INTO tiers (Id,libTiers,derCategDeTiers)VALUES(?,?,?)");
			statement.setString(2, myTiers.getLibTiers());
			statement.setString(3, myTiers.getDerCategDeTiers());
//			System.out.println("dans create tiersDAO" + statement.toString());
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("create tiers KO");
			e.printStackTrace();
		} finally {
			try {
				statement.close();
//				connection.close();
			} catch (SQLException e) {
				System.out.println("Dans create de TiersDAO connexion close : SQL Exception");
				e.printStackTrace();
			}
		}
	}

	public Tiers find(int id) {
		Tiers myTiers = null;
		try {
			// System.out.println("select unique tiers début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM tiers WHERE id = '" + id + "'");
			// System.out.println("rs = " + rs.getInt("id"));
			if (rs.next()) {
				myTiers = new Tiers(rs.getInt(1), rs.getString(2), rs.getString(3));
				// System.out.println("select unique tiers");
				// System.out.println("id = " + myTiers.getId());
				// System.out.println("tiers = " + myTiers.getLibTiers());
				// System.out.println("derCategDeTiers = " + myTiers.getderCategDeTiers());
			}
		} catch (SQLException e) {
			System.out.println("Dans find de Tiesr DAO : KO SQL exception ");
			e.printStackTrace();
		}
		return myTiers;
	}
	//

	public int findLib(String libTiers) {
		int id = 0;
		try {
			// System.out.println("select lib tiers début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM tiers WHERE libTiers = '" + libTiers + "'");
			if (rs.next()) {
				id = rs.getInt(1);
				// System.out.println("select lib tiers");
				// System.out.println("id = " + id );
			}
		} catch (SQLException e) {
			System.out.println("Dans findLib de TiersDAO : SQL Exception");
			e.printStackTrace();
		}
		return id;
	}
	//

	public ArrayList<Tiers> findAll() {
		Tiers myTiers = null;
		ArrayList<Tiers> myTiersList = null;
		try {
			myTiersList = new ArrayList<Tiers>();
			LogTiers.logDebug("select all tiers début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM tiers");
			while (rs.next()) {
				LogTiers.logDebug("rs = " + rs.getInt("id"));
				myTiers = new Tiers(rs.getInt(1), rs.getString(2), rs.getString(3));
				myTiersList.add(myTiers);
			}
			statement.close();

		} catch (SQLException e) {
			LogTiers.logError("Dans findAll de TiersDAO : SQL Exception", e);
		}
		return myTiersList;
	}

	public void update(Tiers myTiers) {
		Statement statement = null;
		try {
//			System.out.println("Dans update Tiers de Ties DAO  myTiers.getDerCategDeTiers()" + myTiers.getDerCategDeTiers());
//			System.out.println("UPDATE tiers SET libTiers='" + myTiers.getLibTiers() + "',derCategDeTiers='"
//					+ myTiers.getDerCategDeTiers() + "' where Id=" + myTiers.getId());
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE tiers SET libTiers='" + myTiers.getLibTiers() + "',derCategDeTiers='"
					+ myTiers.getDerCategDeTiers() + "' where Id=" + myTiers.getId());

		} catch (SQLException e) {
			System.out.println("Dans update de TiersDAO : SQL Exception");
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Dans update de TiersDAO statement Close : SQL Exception");
				e.printStackTrace();
			}
		}
	}

	public void delete(Tiers myTiers) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from tiers where Id=" + myTiers.getId());
		} catch (SQLException e) {
			System.out.println("Dans delete de TiersDAO : SQL Exception");
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Dans delete de TiersDAO statement close : SQL Exception");
				e.printStackTrace();
			}
		}
	}
}
