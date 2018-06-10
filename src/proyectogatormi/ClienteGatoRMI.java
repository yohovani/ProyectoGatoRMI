/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogatormi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yohov
 */
public class ClienteGatoRMI {
	public static void main(String[] ssa){
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.0.17", 1099);
			RMIRegistroJuego rmi;
			rmi = (RMIRegistroJuego) reg.lookup("Objeto Juego");
			
		} catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		} catch (NotBoundException ex) {
			System.out.println(ex.getMessage());
		}
	}	
}
