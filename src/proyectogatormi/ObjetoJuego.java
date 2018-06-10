/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogatormi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Yohovani Vargas
 */
public class ObjetoJuego extends UnicastRemoteObject implements RMIRegistroJuego{
	private int[][] matrizJuego;
	private String jugador1;
	private String jugador2;
	private int turno;
	private String tp;

	public ObjetoJuego() throws RemoteException  {
            super();
			this.matrizJuego = new int[3][3];
			turno = 1;
        }

	public void registrarMovimiento(int x, int y, int z ) {
		this.matrizJuego[x][y]=z;
	}

	@Override
	public int[][] getMatrizJuego() throws RemoteException {
		return this.matrizJuego;
	}

	@Override
	public String imprimirMatriz() throws RemoteException {
		String aux="";
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				aux+=this.matrizJuego[i][j]+" ";
			}
			aux+="\n";
		}
		return aux;
	}

	@Override
	public void registrarP1(String p) throws RemoteException {
		this.jugador1 = p;
	}

	@Override
	public String getP1() throws RemoteException {
		return this.jugador1;
	}

	@Override
	public void registrarP2(String p) throws RemoteException {
		this.jugador2 = p;
	}

	@Override
	public String getP2() throws RemoteException {
		return this.jugador2;
	}

	@Override
	public boolean comprobarRegistro() throws RemoteException {
		if(this.jugador1 != null && this.jugador2 != null){
			return true;
		}
		return false;
	}

	@Override
	public int getTurno() throws RemoteException {
		return this.turno;
	}

	@Override
	public void setTurno(int x) throws RemoteException {
		this.setTurno(x);
	}

	@Override
	public void setMatrizJuego(int[][] x) throws RemoteException {
		this.matrizJuego = x;
	}

	@Override
	public String getTP() throws RemoteException {
		return this.tp;
	}

	@Override
	public void setTP(String v) throws RemoteException {
		this.tp = v;
	}

	
	
}
