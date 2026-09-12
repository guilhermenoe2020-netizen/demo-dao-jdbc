package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Insere um novo vendedor no banco de dados.
	 *
	 * @param obj vendedor que será inserido
	 */
	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		  try {
			 st = conn.prepareStatement(
					 "INSERT INTO seller"
					 + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					 + "VALUES "
					 + "(?, ?, ?, ?, ?)",
					 Statement.RETURN_GENERATED_KEYS);
		  
			st.setString(1, obj.getName()); 
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); 
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
				
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			   DB.closeResultSet(rs);
			   
			}else {
				throw new DbException("ERRO! No rown affected!");
			 }
			
		  }
		   catch (SQLException e) {
			  throw new DbException(e.getMessage());
		   }
			finally {
			  DB.closeStatement(st);
			}	
	}

	
	/**
	 * Atualiza os dados de um vendedor existente.
	 *
	 * @param obj vendedor que será atualizado
	 */
	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		  try {
			 st = conn.prepareStatement(
			    "UPDATE seller "	
				+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
			    + "WHERE Id = ? ");
					
		  
			st.setString(1, obj.getName()); 
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); 
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());	
			
			st.executeUpdate();
		  }
		   catch (SQLException e) {
			  throw new DbException(e.getMessage());
		   }
			finally {
			  DB.closeStatement(st);
			}	
	}
	
	/**
	 * Remove um vendedor pelo seu identificador.
	 *
	 * @param id identificador do vendedor
	*/	

	@Override
	public void deleteById(Integer id) { 
			
	}

	/**
	 * Busca um vendedor pelo seu identificador.
	 *
	 * Realiza um INNER JOIN com a tabela Department para obter
	 * também os dados do departamento ao qual o vendedor pertence.
	 *
	 * @param id identificador do vendedor
	 * @return vendedor encontrado ou null caso não exista
	 */
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
		
		st.setInt(1, id);
		rs = st.executeQuery();
		if(rs.next()) {
			Department dep = instantiateDepartment(rs);
			Seller obj = instantiateSeller(rs, dep);
	        return obj;  
		}
		return null;
		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	

	/**
	 * Cria um objeto Seller a partir dos dados obtidos do ResultSet.
	 *
	 * @param rs resultado da consulta ao banco de dados
	 * @param dep departamento associado ao vendedor
	 * @return objeto Seller preenchido com os dados do banco
	 * @throws SQLException caso ocorra um erro ao acessar os dados
	 */
	private Seller instantiateSeller(ResultSet rs, Department dep) throws  SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	/**
	 * Cria um objeto Department a partir dos dados obtidos do ResultSet.
	 *
	 * @param rs resultado da consulta ao banco de dados
	 * @return objeto Department preenchido com os dados do banco
	 * @throws SQLException caso ocorra um erro ao acessar os dados
	 */
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName")); 
		return dep;
	}

	
	/**
	 * Busca todos os vendedores cadastrados no banco de dados,
	 * juntamente com seus respectivos departamentos
	 *
	 * A consulta utiliza INNER JOIN para relacionar as tabelas
	 * Seller e Department. Os registros retornados são convertidos
	 * em objetos Seller e armazenados em uma lista
	 *
	 * O Map é utilizado para reutilizar os objetos Department já
	 * criados, evitando a criação desnecessária de instâncias
	 * repetidas para o mesmo departamento
	 *
	 * @return lista contendo todos os vendedores cadastrados
	 * e seus respectivos departamentos
	 */
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
	
		rs = st.executeQuery();
		List<Seller> list = new ArrayList<>();
		Map<Integer, Department> map = new HashMap<>();
		
		while(rs.next()) {
			
			Department dep = map.get(rs.getInt("departmentId"));
			
			if(dep == null) {
				dep = instantiateDepartment(rs);
				map.put(rs.getInt("departmentId"), dep);
				
			}
			
			Seller obj = instantiateSeller(rs, dep);
	        list.add(obj);  
		}
		return list;
		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	/**
	 * Busca todos os vendedores pertencentes a um determinado departamento.
	 *
	 * Os departamentos são armazenados em um Map para evitar a criação
	 * repetida do mesmo objeto Department durante a consulta.
	 *
	 * @param department departamento utilizado como filtro
	 * @return lista de vendedores pertencentes ao departamento
	 */
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
		
		st.setInt(1, department.getId());
		rs = st.executeQuery();
		List<Seller> list = new ArrayList<>();
		Map<Integer, Department> map = new HashMap<>();
		
		while(rs.next()) {
			
			Department dep = map.get(rs.getInt("departmentId"));
			
			if(dep == null) {
				dep = instantiateDepartment(rs);
				map.put(rs.getInt("departmentId"), dep);
				
			}
			
			Seller obj = instantiateSeller(rs, dep);
	        list.add(obj);  
		}
		return list;
		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
