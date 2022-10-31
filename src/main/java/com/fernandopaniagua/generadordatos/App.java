package com.fernandopaniagua.generadordatos;

import java.sql.SQLException;
import java.util.ArrayList;

public class App {
	public static void main(String[] args) {
		System.out.println("Ejecutando el programa de Generación de Datos...");
		if (args.length!=1) {
			System.err.println("ERROR: Se espera un argumento numérico entero");
			System.exit(-1);
		} 
		GestorPersistencia gp = null;
		try {
			int numeroRegistros = Integer.parseInt(args[0]);
			ArrayList<Empleado> empleados = new GeneradorEmpleados().generarEmpleados(numeroRegistros);
			//EMPEZAMOS A TOMAR LOS TIEMPOS
			long startTime = new java.util.Date().getTime();
			gp = new GestorPersistencia();
			int numeroRegistrosInsertados = 0;
			for (Empleado empleado : empleados) {
				gp.add(empleado);
				numeroRegistrosInsertados++;
				if (numeroRegistrosInsertados%100==0) {
					System.out.println(String.format("%d registros insertados", numeroRegistrosInsertados));
				}
			}
			gp.doCommit();//*****COMMIT*****
			//FINALIZAMOS LA TOMA DE TIEMPOS
			long endTime = new java.util.Date().getTime();
			long totalTime = (endTime - startTime)/1000;
			System.out.println("El proceso completo ha tardado " + totalTime + " segundos.");
		} catch (NumberFormatException nfe) {
			System.err.println("ERROR: El argumento debe ser un número entero");
			System.exit(-2);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR: Error en la inserción en la base de datos");
			try {
				gp.doRollback();//*****ROLLBACK*****
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.err.println("ERROR: Error al hacer el rollback de la base de datos");
				System.exit(-5);
			}
			System.exit(-3);
		} finally {
			try {
				gp.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("ERROR: Error al cerrar la base de datos");
				System.exit(-4);
			}
			
		}
	}
}