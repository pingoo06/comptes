package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import comptes.model.db.entity.Categorie;
import comptes.util.log.LogCategorie;

public class CategorieDAO extends DAO<Categorie> {
	public void create(Categorie myCategorie) {
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("INSERT INTO categorie (id,  libCateg)VALUES(?,?)");
			statement.setString(2, myCategorie.getLibCateg());
			statement.executeUpdate();
			// statement.close();
		} catch (SQLException e) {
			LogCategorie.logError("create categ KO", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogCategorie.logError("close statement de create categ KO", e);
			}
		}
	}

	public Categorie find(int id) {
		Categorie myCategorie = null;
		try {
			// System.out.println("select unique Categorie début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM categorie WHERE id = '" + id + "'");
			// System.out.println("rs = " + rs.getInt("id"));
			if (rs.next()) {
				myCategorie = new Categorie(rs.getInt("id"), rs.getString("libCateg"));
			}
		} catch (SQLException e) {
			LogCategorie.logError("select unique Categorie KO",e);
		}
		return myCategorie;
	}
	//

	public int findLib(String libCateg) {
		int id = 0;
		String lib = "";
		lib = libCateg;
		try {
			id = 0;
			// System.out.println("select lib Categ début try");
			Statement statement = connection.createStatement();
			LogCategorie.logDebug("Dans CategorieDAO : select : SELECT * FROM categorie WHERE libCateg = '" + libCateg + "'" );
			ResultSet rs = statement.executeQuery("SELECT * FROM categorie WHERE libCateg = '" + libCateg + "'");
			if (rs.next()) {
				id = rs.getInt(1);
				// System.out.println("select lib Categ");
				// System.out.println("id = " + id );
			}
		} catch (SQLException e) {
			LogCategorie.logError("findlib categ KO libCateg = '" + libCateg , e);
		}
		return id;
	}

	//
	public ArrayList<Categorie> findAll() {
		Categorie myCategorie = null;
		ArrayList<Categorie> myCategorieList = null;
		try {
			myCategorieList = new ArrayList<Categorie>();
			LogCategorie.logDebug("select all Categorie début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM categorie");
			while (rs.next()) {
				myCategorie = new Categorie(rs.getInt("id"), rs.getString("libCateg"));
				myCategorieList.add(myCategorie);
			}
			Collections.sort(myCategorieList);
			statement.close();

		} catch (SQLException e) {
			LogCategorie.logError("select unique categorie KO",e);
		}
		return myCategorieList;
	}

	public void update(Categorie myCategorie) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE categorie SET libCateg='" + myCategorie.getLibCateg() + "' where Id="
					+ myCategorie.getId());
		} catch (SQLException e) {
			LogCategorie.logError("update categ KO", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogCategorie.logError("statement close de update categ KO", e);
			}
		}
	}

	public void delete(Categorie myCategorie) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from categorie where Id=" + myCategorie.getId());
		} catch (SQLException e) {
			LogCategorie.logError("delete categ KO", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				LogCategorie.logError("statement.close de delete categ KO", e);
			}
		}
	}

}
