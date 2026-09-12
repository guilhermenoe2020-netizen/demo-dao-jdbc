package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

/**
 * Class responsável por criar os objetos DAO do sistema.
 */
public class DaoFactory {


	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
		
	}
		public static DepartmentDao createDepartmentDao() {
			return new DepartmentDaoJDBC(DB.getConnection());
	   }
}
