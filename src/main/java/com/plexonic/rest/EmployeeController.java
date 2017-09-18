package com.plexonic.rest;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import java.util.Map;

public class EmployeeController implements ModelDriven<Object>{

	private static final long serialVersionUID = 1L;
	private String id;
	private Object model;
	private UserRepository userRepository = new UserRepository();
	private static Map<String,String> map;
	{
		map = userRepository.findAllUser();
	}
	public HttpHeaders index() {
		model = map;
		userRepository.csvToDBfromZip();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public String add(){
		Integer empId = Integer.parseInt(id);
		User emp = new User("Ramesh", "PQR");
		model = emp;
		return "SUCCESS";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		model = userRepository.getUserById(id);
		this.id = id;
	}

	@Override
	public Object getModel() {
		return model;
	}
} 