package comptes.model.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import comptes.model.db.entity.InitFillMoney;
import comptes.util.log.Logger;

public class SqliteConnector {

	private static Connection connection;
	private static final String FILE_PATH = "res/mydatabase.db";

	public static Connection getInstance() {
		if (connection == null) {
			try {
				boolean isInitialized = dbInitialized();
				connection = DriverManager.getConnection("jdbc:sqlite:" + FILE_PATH);
				connection.setAutoCommit(true);
				if (!isInitialized) {
					initDB();
					InitFillMoney.initFillAll();
				} else {
					Logger.logInfo("DB already initialized");
				}
			} catch (SQLException e) {
				Logger.logError("failed init instance", e);
			}
		}
		return connection;
	}

	public static Connection getInstanceUneTable(String table) {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:" + FILE_PATH);
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				Logger.logError("failed get instance une table", e);
			}
		}
		initUneTable(table);
		return connection;
	}

	private static boolean dbInitialized() {
		File file = new File(FILE_PATH);
		// return false; // init de la db a chaque fois
		return file.exists();
	}

	private static void initDB() {
		Logger.logInfo("init Database");
		Statement statement;
		try {
			statement = connection.createStatement();
			// creation table paramètre
			statement.executeUpdate("drop table if exists parametre");
			statement.executeUpdate("create table parametre (id integer, nomParam string, valParam string)");
			Logger.logInfo("table parametre creee");

			// creation table tiers
			statement.executeUpdate("drop table if exists tiers");
			statement.executeUpdate(
					"create table tiers (id integer primary key autoincrement, libTiers string, derCategDeTiers string)");
			Logger.logInfo("table tiers creee");

			// creation table operation
			statement.executeUpdate("drop table if exists operation");
			statement.executeUpdate(
					"create table operation (id integer primary key autoincrement, typeOpe string, dateOpe string, "
							+ " montantOpe double, categOpeId integer,tiersId integer, detailOpe string , etatOpe string, echID integer, dateOpeLong long)");
			Logger.logInfo("table operation creee");

			// creation table categorie
			statement.executeUpdate("drop table if exists categorie");
			statement.executeUpdate("create table categorie (id integer primary key autoincrement, libCateg string)");
			Logger.logInfo("table categorie creee");

			// creation table bnp
			statement.executeUpdate("drop table if exists bnp");
			statement.executeUpdate(
					"create table bnp (id integer primary key autoincrement, dateBnp string, libCourtBnp string, libTypeOpeBnp string, libOpeBnp string, "
							+ "montantBnp double, etatBnp string,typeOpeBnp string,"
							+ "dateBnpCalc long,chqNumberBnp string  )");
			Logger.logInfo("table bnp creee");

			// creation table matching
			statement.executeUpdate("drop table if exists matching");
			statement.executeUpdate(
					"create table matching (id integer primary key autoincrement, libTier string, libBnp string)");
			statement.close();
			Logger.logInfo("table matching creee");
			// creation table echeancier
			statement.executeUpdate("drop table if exists echeancier");
			statement.executeUpdate(
					"create table echeancier (id integer primary key autoincrement, typeEch string, tiersEchId integer, categEchId integer,  dateEch string, montantEch double,nbEch integer, dateEchLong long)");
			statement.close();
			Logger.logInfo("table echeancier creee");
			// creation table der_rappro
						statement.executeUpdate("drop table if exists der_rappro");
						statement.executeUpdate(
								"create table der_rappro (id integer primary key autoincrement, derSolde double, dateDerRappro String, dateDerRapproLong long");
						statement.close();
						Logger.logInfo("table der_rappro creee");
		} catch (SQLException e) {
			Logger.logError("pb sql dans une table", e);
		} finally {
			// try {
			//// connection.close();
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// Logger.logError("pb sql connection.close", e);
			// }
		}
	}

	public static void initUneTable(String choix) {
		Logger.logInfo("init initUneTable : " + choix);
		Statement statement;
		try {
			switch (choix) {
			case "parametre":
				Logger.logInfo(" creation table paramètre");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists parametre");
				statement.executeUpdate("create table parametre (id integer, nomParam string, valParam string)");
				Logger.logInfo("table parametre creee");
				statement.close();
				break;
			case "tiers":
				Logger.logInfo(" creation table tiers");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists tiers");
				statement.executeUpdate(
						"create table tiers (id integer primary key autoincrement, libTiers string, derCategDeTiers string)");
				Logger.logInfo("table tiers creee");
				statement.close();
				break;
			case "operation":
				Logger.logInfo(" creation table operation");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists operation");
				statement.executeUpdate(
						"create table operation (id integer primary key autoincrement, typeOpe string, dateOpe string, "
								+ " montantOpe double, categOpeId integer,tiersId integer, detailOpe string , etatOpe string, echID integer, dateOpeLong long)");
				Logger.logInfo("table operation creee");
				statement.close();
				break;
			case "categorie":
				Logger.logInfo(" creation table categorie");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists categorie");
				statement.executeUpdate(
						"create table categorie (id integer primary key autoincrement, libCateg string)");
				Logger.logInfo("table categorie creee");
				statement.close();
				break;
			case "bnp":
				Logger.logInfo(" creation table bnp");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists bnp");
				statement.executeUpdate(
						"create table bnp (id integer primary key autoincrement, dateBnp string, libCourtBnp string, libTypeOpeBnp string, libOpeBnp string, montantBnp double, etatBnp string,typeOpeBnp string,dateBnpCalc long,chqNumberBnp string  )");
				Logger.logInfo("table bnp creee");
				statement.close();
				break;
			case "matching":
				Logger.logInfo(" creation table matching");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists matching");
				statement.executeUpdate(
						"create table matching (id integer primary key autoincrement, libTier string, libBnp string)");
				statement.close();
				break;
			case "echeancier":
				Logger.logInfo(" creation table echeancier");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists echeancier");
				statement.executeUpdate(
						"create table echeancier (id integer primary key autoincrement, typeEch string, tiersEchId integer, categEchId integer,  dateEch string, montantEch double,nbEch integer, dateEchLong long)");
				statement.close();
				break;
			case "der_rappro":
				Logger.logInfo(" creation table der_rappro");
				statement = connection.createStatement();
				statement.executeUpdate("drop table if exists der_rappro");
				statement.executeUpdate(
						"create table der_rappro (id integer primary key autoincrement, derSolde double, dateDerRappro String, dateDerRapproLong long)");
				Logger.logInfo("table der_rappro creee");
				statement.close();
				break;
			default:
				throw new IllegalArgumentException("Dans initUneTable de Sqlite connector : choix init Table erroné");
			}
		} catch (SQLException e) {
		} finally {
			// try {
			//// connection.close();
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// Logger.logError("pb sql connection.close", e);
			// }

		}
	}
}
