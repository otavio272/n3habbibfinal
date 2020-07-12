package exemplo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exemplo.modelo.Advogados;

public class AdvogadosDao implements IDao<Advogados> {

	public AdvogadosDao() {
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
				+ "AND t.name = 'Advogados')"
				+ "CREATE TABLE Advogados"
				+ " (id	int	IDENTITY,"
				+ "  dono	VARCHAR(255),"
				+ "	 numero	int,"
				+ "  PRIMARY KEY (id))";
		
		Connection conn = DatabaseAccess.getConnection();
		
		Statement stmt = conn.createStatement();
		stmt.execute(sqlCreate);
	}

	public List<Advogados> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		List<Advogados> Advogadoss = new ArrayList<Advogados>();

		try {
			stmt = conn.createStatement();

			String SQL = "SELECT * FROM Advogados";
	        rs = stmt.executeQuery(SQL);

	        while (rs.next()) {
	        	Advogados d = getAdvogadosFromRs(rs);
	        	
	        	Advogadoss.add(d);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAllAdvogadoss] Erro ao selecionar todos os Advogadoss", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Advogadoss;		
	}
	
	public Advogados getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Advogados Advogados = null;
		
		try {
			String SQL = "SELECT * FROM Advogados WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);

	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	Advogados = getAdvogadosFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[getAdvogadosById] Erro ao selecionar a Advogados por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return Advogados;		
	}
	
	public void insert(Advogados Advogados) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String SQL = "INSERT INTO Advogados (dono, numero) VALUES (?, ?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, Advogados.getDono());
	    	stmt.setInt(2, Advogados.getNumero());
	    	
			
	        stmt.executeUpdate();
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	Advogados.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("[insereAdvogados] Erro ao inserir a Advogados", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Advogados WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("[deleteAdvogados] Erro ao remover a Advogados por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(Advogados Advogados) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Advogados SET dono = ?, numero = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, Advogados.getDono());
	    	stmt.setInt(2, Advogados.getNumero());
	    	stmt.setInt(3, Advogados.getId());
	    	
	        stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("[updateAdvogados] Erro ao atualizar a Advogados", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	private Advogados getAdvogadosFromRs(ResultSet rs) throws SQLException {
		Advogados d = new Advogados();
		d.setId(rs.getInt("id"));
		d.setDono(rs.getString("dono"));
		d.setNumero(rs.getInt("numero"));

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
