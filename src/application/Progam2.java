package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


public class Progam2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n== test 1: findAll ==");
		List<Department> list = departmentDao.findAll();
	    
		for(Department dep : list) {
			System.out.println(dep);
		}
		
		System.out.println("\n== test 2: findById ==");
		Department department = departmentDao.findById(1);
		System.out.println(department);
		
		System.out.println("\n== test 3: delete ==");
		System.out.println("Enter id for delete test");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("delet completed");
		
		sc.close();
	}
}
