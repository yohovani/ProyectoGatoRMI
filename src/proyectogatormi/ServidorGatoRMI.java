/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogatormi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yohovani Vargas
 */
public class ServidorGatoRMI {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ObjetoJuego game = new ObjetoJuego();
			Registry reg = LocateRegistry.createRegistry(1099);
			reg.rebind("Objeto Juego", game);
			System.out.println("Servidor Activo...");
		} catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
}
