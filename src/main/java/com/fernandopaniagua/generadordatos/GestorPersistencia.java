package com.fernandopaniagua.generadordatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class GestorPersistencia {
	protected static final String HOST_NAME = "localhost";
	protected static final String PORT = "3306";
	protected static final String DB_NAME = "bbdd_empleados";
	protected static final String USER_NAME = "usuarioapp";
	protected static final String USER_PASSWORD = "patata";
	
	protected Connection conexion = null;
	
	public GestorPersistencia() throws SQLException {
		setConnection();
	}
	
	protected void setConnection() throws SQLException{
		String servidor = String.format("jdbc:mysql://%s:%s/%s",HOST_NAME, PORT, DB_NAME);
		this.conexion = DriverManager.getConnection(servidor, USER_NAME, USER_PASSWORD);
		this.conexion.setAutoCommit(false);//Desactivamos el comportamiento por defecto (autocommit a true)
	}
	
	protected void closeConnection() throws Exception {
		this.conexion.close();
	}
	
	public void add(Empleado e) throws SQLException {
		
		String sql = String.format("INSERT "
				+ "INTO empleados(nombre, apellido1, apellido2, puesto, salarioAnual) "
				+ "VALUES ('%s','%s', '%s', '%s', '%d')", 
				e.getNombre(), e.getApellido1(), e.getApellido2(), e.getPuesto(), e.getSalarioAnual());
		Statement st = conexion.createStatement();
		st.executeUpdate(sql);

	}
	
	public void doCommit() throws SQLException {
		this.conexion.commit();
	}
	public void doRollback() throws SQLException {
		this.conexion.rollback();
	}
}
