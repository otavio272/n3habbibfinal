package exemplo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exemplo.modelo.Casos;

public class CasosDao implements IDao<Casos> {

	public CasosDao() {
		try {
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createTable() throws SQLException {
		final String sqlCreate = "IF NOT EXISTS (" 
				+ "SELECT * FROM sys.tables t JOIN sys.schemas s ON " 
				+ "(t.schema_id = s.schema_id) WHERE s.name = 'dbo'" 
				+ "AND t.name = 'Casos')"
				+ "CREATE TABLE Casos"
				+ " (id	int	IDENTITY,"
				+ "  arquivo	VARCHAR(255),"
				+ "	 denuncias	VARCHAR(10),"
				+ "  PRIMARY KEY (id))";
		
		Connection conn = DatabaseAccess.getConnection();
		
		Statement stmt = conn.createStatement();
		stmt.execute(sqlCreate);
	}

	public List<Casos> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Casos> Casoss = new ArrayList<Casos>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM Casos";
	        rs = stmt.executeQuery(SQL);

	        while (rs.next()) {
	        	Casos d = getCasosFromRs(rs);
	        	
	        	Casoss.add(d);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAllCasoss] Erro ao selecionar todos os Casoss", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Casoss;		
	}
	
	public Casos getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Casos Casos = null;
		
		try {
			String SQL = "SELECT * FROM Casos WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);

	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	Casos = getCasosFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getCasosById] Erro ao selecionar o Casos por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Casos;
	}
	
	public void insert(Casos Casos) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String SQL = "INSERT INTO Casos (arquivo, denuncias) VALUES (?, ?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, Casos.getarquivo());			
	        stmt.executeUpdate();
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	Casos.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[insereCasos] Erro ao inserir a Casos", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Casos WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("[deleteCasos] Erro ao remover a Casos por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(Casos Casos) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Casos SET arquivo = ?, denuncias = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, Casos.getarquivo());
	    	stmt.setString(2, Casos.getdenuncias());
	    	stmt.setInt(3, Casos.getId());
	    	
	        stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("[updateCasos] Erro ao atualizar a Casos", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	private Casos getCasosFromRs(ResultSet rs) throws SQLException {
		Casos d = new Casos();
		d.setId(rs.getInt("id"));
		d.setarquivo(rs.getString("arquivo"));
		d.setdenuncias(rs.getString("denuncias"));

		return d;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) { rs.close(); }
			if (stmt != null) { stmt.close(); }
			if (conn != null) { conn.close(); }
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar recursos.", e);
		}
	}

}
