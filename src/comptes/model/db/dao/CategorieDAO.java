package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import comptes.model.db.entity.Categorie;

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
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
			System.out.println("select unique Categorie KO");
			e.printStackTrace();
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
			System.out.println("Dans CategorieDAO : select : SELECT * FROM categorie WHERE libCateg = '" + libCateg + "'" );
			ResultSet rs = statement.executeQuery("SELECT * FROM categorie WHERE libCateg = '" + libCateg + "'");
			if (rs.next()) {
				id = rs.getInt(1);
				// System.out.println("select lib Categ");
				// System.out.println("id = " + id );
			}
		} catch (SQLException e) {
			System.out.println("select lib Categ KO");
			System.out.println("LibCateg = " + lib);
			e.printStackTrace();
		}
		return id;
	}

	//
	public ArrayList<Categorie> findAll() {
		Categorie myCategorie = null;
		ArrayList<Categorie> myCategorieList = null;
		try {
			myCategorieList = new ArrayList<Categorie>();
			// System.out.println("select all Categorie début try");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM categorie");
			while (rs.next()) {
				myCategorie = new Categorie(rs.getInt("id"), rs.getString("libCateg"));
				myCategorieList.add(myCategorie);
			}
			Collections.sort(myCategorieList);
			statement.close();

		} catch (SQLException e) {
			System.out.println("select unique categorie KO");
			e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(Categorie myCategorie) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("Delete from categorie where Id=" + myCategorie.getId());
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

}
