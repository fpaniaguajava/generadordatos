package com.fernandopaniagua.generadordatos;

import java.util.ArrayList;
import java.util.Iterator;

public class App {
	public static void main(String[] args) {
		System.out.println("Ejecutando el programa de Generación de Datos...");
		if (args.length!=1) {
			System.err.println("ERROR: Se espera un argumento numérico entero");
			System.exit(-1);
		} 
		try {
			int numeroRegistros = Integer.parseInt(args[0]);
			ArrayList<Empleado> empleados = new GeneradorEmpleados().generarEmpleados(numeroRegistros);
			for (Empleado empleado : empleados) {
				System.out.println(empleado);
			}
		} catch (NumberFormatException nfe) {
			System.err.println("ERROR: El argumento debe ser un número entero");
			System.exit(-2);
		}
	}
}