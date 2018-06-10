/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectogatormi;

import java.awt.Color;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author bryan
 */
public class Juego extends javax.swing.JFrame {
    private String iniciarJuego;
    private int contadorX=0;
    private int contadorO=0;
    private String[][] matriz=new String[3][3];
    private String nombreJugadorX="";
    private String nombreJugadorO="";
    private int verificaGanador=0;
	private boolean fin;
	private  Registry reg;
	private RMIRegistroJuego rmi;
	private JButton[][] botones;
	private String jugador;
    /**
     * Creates new form Juego
     */
    public Juego() {
		try {
			fin = false;
			
			initComponents();
			setSize(1200,600);
			setLocationRelativeTo(null);
			String direccion = JOptionPane.showInputDialog(this, "Introduce la direccion del servidor: ", "Dirección:", JOptionPane.INFORMATION_MESSAGE);
			String puerto = JOptionPane.showInputDialog(this, "Introduce el puero que se usara para jugar: ", "Puerto:", JOptionPane.INFORMATION_MESSAGE);
			
			String name = JOptionPane.showInputDialog(this, "Introduce el nombre de la partida: ", "Nombre:", JOptionPane.INFORMATION_MESSAGE);
			
			reg = LocateRegistry.getRegistry(direccion, Integer.parseInt(puerto));
			rmi = (RMIRegistroJuego) reg.lookup(name);
			this.setTitle("Partida: "+name+", Puerto: "+puerto+", Dirección del servidor: "+direccion);
			obtenerNombres();
			addBotones();
			actualizarMov();
		} catch (RemoteException ex) {
			Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotBoundException ex) {
			Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
	private void addBotones(){
		this.botones = new JButton[3][3];
		this.botones[0][0] = this.jButton1;
		this.botones[0][1] = this.jButton2;
		this.botones[0][2] = this.jButton3;
		this.botones[1][0] = this.jButton4;
		this.botones[1][1] = this.jButton5;
		this.botones[1][2] = this.jButton6;
		this.botones[2][0] = this.jButton7;
		this.botones[2][1] = this.jButton8;
		this.botones[2][2] = this.jButton9;
	}
	
	public void RegistrarNombres(){
		obtenerNombres();
         this.jLabel1.setText("Es turno de : "+this.nombreJugadorX);
	}
    private void obtenerNombres(){
		try {
			if(rmi.getP1() == null){
				String aux = JOptionPane.showInputDialog(this, "Introduce el nombre del jugador X: ", "Nombre del jugador X", JOptionPane.INFORMATION_MESSAGE);
				this.nombreJugadorX=aux;
				rmi.registrarP1(aux);
				this.jugador = "X";
			}else{
				if(rmi.getP2() == null){
					String aux = JOptionPane.showInputDialog(this, "Introduce el nombre del jugador O: ", "Nombre del jugador O", JOptionPane.INFORMATION_MESSAGE);
					this.nombreJugadorO=aux;
					rmi.registrarP2(aux);
					this.jugador="O";
				}
			}
			iniciarJuego = jugador;
			if(this.nombreJugadorX.equals("")){
				nombreJugadorX="Jugador X";
				jLabel2.setText(nombreJugadorX+":");
			}else{
				jLabel2.setText(nombreJugadorX+":");
							
			}
			if(this.nombreJugadorO.equals("")){
				nombreJugadorO="Jugador O";
				jLabel3.setText(nombreJugadorO+":");
			}else{
				jLabel3.setText(nombreJugadorO+":");
			}
			rmi.setTP(nombreJugadorX);
			 Runnable r = new Runnable(){
				@Override
				public void run() {
					try {
						while(!rmi.comprobarRegistro()){
							jLabel2.setText(nombreJugadorX+":");
							jLabel3.setText(nombreJugadorO+":");
						}
						jLabel2.setText(rmi.getP1()+":");
						jLabel3.setText(rmi.getP2()+":");
						nombreJugadorX = rmi.getP1();
						nombreJugadorO = rmi.getP2();

					} catch (RemoteException ex) {
						Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				 
			 };
			Thread t = new Thread(r);
			t.start();
		} catch (RemoteException ex) {
			Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    private void MarcadorJuego(){
        this.jLabelJugadorX.setText(""+this.contadorX);
        this.jLabelJugadorO.setText(""+this.contadorO);
    }

    public void turno(String a){
        String turno=a;
 
		
        this.jLabel1.setText("Es turno de : "+turno);
    }
    private void ganadorX(){
        if(verificaGanador==1){
        JOptionPane.showMessageDialog(this, "El jugador "+this.nombreJugadorX+" es el ganador", "Ganaste!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
        this.contadorX++;
        this.jLabelJugadorX.setText(""+this.contadorX);
        this.jButton1.setEnabled(false);
        this.jButton2.setEnabled(false);
        this.jButton3.setEnabled(false);
        this.jButton4.setEnabled(false);
        this.jButton5.setEnabled(false);
        this.jButton6.setEnabled(false);
        this.jButton7.setEnabled(false);
        this.jButton8.setEnabled(false);
        this.jButton9.setEnabled(false);
        }
    }
    private void ganadorO(){
        if(verificaGanador==2){
        JOptionPane.showMessageDialog(this, "El jugador "+this.nombreJugadorO+" es el ganador", "Ganaste!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
        this.contadorO++;
        this.jLabelJugadorO.setText(""+this.contadorO);
        this.jButton2.setEnabled(false);
        this.jButton3.setEnabled(false);
        this.jButton4.setEnabled(false);
        this.jButton5.setEnabled(false);
        this.jButton6.setEnabled(false);
        this.jButton7.setEnabled(false);
        this.jButton8.setEnabled(false);
        this.jButton9.setEnabled(false);
        }
    }
    
    private void determinarGanador(){
        this.matriz[0][0]=this.jButton1.getText();
        this.matriz[0][1]=this.jButton2.getText();
        this.matriz[0][2]=this.jButton3.getText();
        this.matriz[1][0]=this.jButton4.getText();
        this.matriz[1][1]=this.jButton5.getText();
        this.matriz[1][2]=this.jButton6.getText();
        this.matriz[2][0]=this.jButton7.getText();
        this.matriz[2][1]=this.jButton8.getText();
        this.matriz[2][2]=this.jButton9.getText();
        
        String uno=this.matriz[0][0];
        String dos=this.matriz[0][1];
        String tres=this.matriz[0][2];
        
        String cuatro=this.matriz[1][0];
        String cinco=this.matriz[1][1];
        String seis=this.matriz[1][2];
        
        String siete=this.matriz[2][0];
        String ocho=this.matriz[2][1];
        String nueve=this.matriz[2][2];
        
        if(uno == "X" && dos == "X" && tres == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        if(cuatro == "X" && cinco == "X" && seis == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        if(siete == "X" && ocho == "X" && nueve == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        if(uno == "X" && cuatro == "X" && siete == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        if(dos == "X" && cinco == "X" && ocho == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        if(tres == "X" && seis == "X" && nueve == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        if(uno == "X" && cinco == "X" && nueve == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        if(tres == "X" && cinco == "X" && siete == "X"){
            this.verificaGanador=1;
            ganadorX();
			fin = true;
        }
        //O GANADOR
        if(uno == "O" && dos == "O" && tres == "O"){
            this.verificaGanador=2;
            ganadorO();
			fin = true;
        }
        if(cuatro == "O" && cinco == "O" && seis == "O"){
            this.verificaGanador=2;
            ganadorO();
			fin = true;
        }
        if(siete == "O" && ocho == "O" && nueve == "O"){
            this.verificaGanador=2;
            ganadorO();
			fin = true;
        }
        if(uno == "O" && cuatro == "O" && siete == "O"){
            this.verificaGanador=2;
            ganadorO();
			fin = true;
        }
        if(dos == "O" && cinco == "O" && ocho == "O"){
            this.verificaGanador=2;
            ganadorO();
        }
        if(tres == "O" && seis == "O" && nueve == "O"){
            this.verificaGanador=2;
            ganadorO();
			fin = true;
        }
        if(uno == "O" && cinco == "O" && nueve == "O"){
            this.verificaGanador=2;
            ganadorO();
			fin = true;
        }
        if(tres == "O" && cinco == "O" && siete == "O"){
            this.verificaGanador=2;
            ganadorO();
			fin = true;
        }
    }
    private void empateJuego(){
        String uno=this.matriz[0][0];
        String dos=this.matriz[0][1];
        String tres=this.matriz[0][2];
        
        String cuatro=this.matriz[1][0];
        String cinco=this.matriz[1][1];
        String seis=this.matriz[1][2];
        
        String siete=this.matriz[2][0];
        String ocho=this.matriz[2][1];
        String nueve=this.matriz[2][2];
        
            
        
        if(uno!= "" && dos!= "" && tres!= "" && cuatro!= "" && cinco!= "" && seis!= "" && siete!= "" && ocho!= "" && nueve!= "" && this.verificaGanador!=1 && this.verificaGanador!=2){
            JOptionPane.showMessageDialog(this, "El juego ha resultado un empate", "Empate!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
			fin = true;
            this.jButton2.setEnabled(false);
            this.jButton3.setEnabled(false);
            this.jButton4.setEnabled(false);
            this.jButton5.setEnabled(false);
            this.jButton6.setEnabled(false);
            this.jButton7.setEnabled(false);
            this.jButton8.setEnabled(false);
            this.jButton9.setEnabled(false);
        }
        
        this.verificaGanador=0;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabelJugadorX = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabelJugadorO = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jButtonReiniciar = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jButtonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("El turno de: ");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new java.awt.GridLayout(3, 5, 2, 2));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jPanel6.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabelJugadorX.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabelJugadorX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugadorX.setText("0");
        jPanel7.add(jLabelJugadorX, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton4, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton5, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton6, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jPanel11.add(jLabel3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabelJugadorO.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabelJugadorO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugadorO.setText("0");
        jPanel12.add(jLabelJugadorO, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel12);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton7, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel16);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton8, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel17);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton9, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel15);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jButtonReiniciar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonReiniciar.setText("Reiniciar juego");
        jButtonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReiniciarActionPerformed(evt);
            }
        });
        jPanel14.add(jButtonReiniciar, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel14);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jButtonSalir.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonSalir.setText("Salir del juego");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        jPanel13.add(jButtonSalir, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel13);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.jButton4.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(1, 0, 1);
				turno(this.nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(1, 0, 2);
				turno(this.nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton4.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
		this.fin = true;
		this.setVisible(false);
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

		this.jButton1.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(0, 0, 1);
				rmi.setTP(nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(0, 0, 2);
				rmi.setTP(nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        
        
        this.jButton1.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

	
	
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.jButton2.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(0, 1, 1);
				turno(this.nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(0, 1, 2);
				turno(this.nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton2.setEnabled(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.jButton3.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(0, 2, 1);
				rmi.setTP(nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(0, 2, 2);
				rmi.setTP(nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton3.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.jButton5.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(1, 1, 1);
				rmi.setTP(nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(1, 1, 2);
				rmi.setTP(nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton5.setEnabled(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.jButton6.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(1, 2, 1);
				rmi.setTP(nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(1, 2, 2);
				rmi.setTP(nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton6.setEnabled(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.jButton7.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(2, 0, 1);
				rmi.setTP(nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(2, 0, 2);
				rmi.setTP(nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton7.setEnabled(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.jButton8.setText(iniciarJuego);
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(2, 1, 1);
				rmi.setTP(nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(2, 1, 2);
				rmi.setTP(nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton8.setEnabled(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        this.jButton9.setText(iniciarJuego);
        
        if(this.iniciarJuego.equalsIgnoreCase("X")){
			try {
				rmi.registrarMovimiento(2, 2, 1);
				rmi.setTP(nombreJugadorO);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }else{
			try {
				rmi.registrarMovimiento(2, 2, 2);
				rmi.setTP(nombreJugadorX);
			} catch (RemoteException ex) {
				Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        this.jButton9.setEnabled(false);
    }//GEN-LAST:event_jButton9ActionPerformed

	public boolean isFin() {
		return fin;
	}
	
    private void jButtonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReiniciarActionPerformed
		try {
			this.jButton1.setText("");
			this.jButton2.setText("");
			this.jButton3.setText("");
			this.jButton4.setText("");
			this.jButton5.setText("");
			this.jButton6.setText("");
			this.jButton7.setText("");
			this.jButton8.setText("");
			this.jButton9.setText("");
			this.jButton1.setEnabled(true);
			this.jButton2.setEnabled(true);
			this.jButton3.setEnabled(true);
			this.jButton4.setEnabled(true);
			this.jButton5.setEnabled(true);
			this.jButton6.setEnabled(true);
			this.jButton7.setEnabled(true);
			this.jButton8.setEnabled(true);
			this.jButton9.setEnabled(true);
			rmi.setMatrizJuego(new int[3][3]);
			this.fin = false;
			actualizarMov();
		} catch (RemoteException ex) {
			Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
		}
    }//GEN-LAST:event_jButtonReiniciarActionPerformed

	public void marcarMovimiento(){
		try {
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					if(rmi.getMatrizJuego()[i][j] == 1){
						this.botones[i][j].setText("X");
						this.botones[i][j].setEnabled(false);
						this.botones[i][j].setForeground(Color.BLUE);
					}else{
						if(rmi.getMatrizJuego()[i][j] == 2){
							this.botones[i][j].setText("O");
							this.botones[i][j].setEnabled(false);
							this.botones[i][j].setForeground(Color.RED);
						}
					}
				}
			}
		} catch (RemoteException ex) {
			Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void actualizarMov(){
		Runnable r = new Runnable(){
			@Override
			public void run() {
				while (!fin){
					try {
						marcarMovimiento();
						determinarGanador();
						empateJuego();
						jLabel1.setText("Es turno de : "+rmi.getTP());
					} catch (RemoteException ex) {
						Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}	
		};
		
		Thread t = new Thread(r);
		t.start();
	}
	
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Juego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonReiniciar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelJugadorO;
    private javax.swing.JLabel jLabelJugadorX;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
