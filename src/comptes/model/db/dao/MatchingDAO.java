package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comptes.model.db.entity.Matching;

public class MatchingDAO extends DAO<Matching> {
	public void create(Matching myMatching) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO matching (id,  libTier,  libBnp)VALUES(?,?,?)");
			statement.setString(2, myMatching.getlibTier());
			statement.setString(3, myMatching.getlibBnp());
			statement.executeUpdate();
//			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
//				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Matching find(int id) {
		Matching myMatching = null;
		try {
			// System.out.println("select unique myMatching début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM matching WHERE id = '" + id + "'");
			// System.out.println("rs = " + rs.getInt("id"));
			if (rs.next()) {
				myMatching = new Matching(rs.getInt("id"), rs.getString("libTier"), rs.getString("libBnp"));
			}
		} catch (SQLException e) {
			System.out.println("select unique Matching KO");
			e.printStackTrace();
		}
		return myMatching;
	}
	//

	public ArrayList<Matching> findAll() {
		Matching myMatching = null;
		ArrayList<Matching> myMatchingList = null;
		try {
			myMatchingList = new ArrayList<Matching>();
			// System.out.println("select all Matching début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM matching");
			while (rs.next()) {
				myMatching = new Matching(rs.getInt("id"), rs.getString("libTier"), rs.getString("libBnp"));
				myMatchingList.add(myMatching);
			}
			statement.close();

		} catch (SQLException e) {
			System.out.println("select unique libBnp KO");
			e.printStackTrace();
		}
		return myMatchingList;
	}

	public void update(Matching myMatching) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE matching SET libTier='" + myMatching.getlibTier() + "',libBnp='"
					+ myMatching.getlibBnp() + "' where Id=" + myMatching.getId());
			// statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
//				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void delete(Matching myMatching) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from matching where Id=" + myMatching.getId());
			// statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
//				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}