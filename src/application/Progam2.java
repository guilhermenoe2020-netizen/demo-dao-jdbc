package application;

import model.dao.DaoFactory;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Progam2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n== test 1: seller findAll ==");
		List<Department> list = departmentDao.findAll();
	    
		for(Department dep : list) {
			System.out.println(dep);
		}
	}
}
