/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogatormi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Yohovani Vargas
 */
public interface RMIRegistroJuego extends Remote{
	
	void registrarMovimiento(int x,int y,int z) throws RemoteException;
	int[][] getMatrizJuego() throws RemoteException;
	String imprimirMatriz() throws RemoteException;
	void registrarP1(String p)  throws RemoteException;
	String getP1() throws RemoteException;
	void registrarP2(String p)  throws RemoteException;
	String getP2() throws RemoteException;
	boolean comprobarRegistro() throws RemoteException;
	int getTurno() throws RemoteException;
	void setTurno(int x) throws RemoteException;
	void setMatrizJuego(int[][] x) throws RemoteException;
	String getTP() throws RemoteException;
	void setTP(String v) throws RemoteException;
	
}
