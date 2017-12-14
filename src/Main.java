
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marlo
 */
public class Main {
    public static void main(String[] args) throws Exception {
                //Converte o argumento recebido para inteiro (numero da porta)      
        int port = 58083;
        //Cria o DatagramSocket para aguardar mensagens, neste momento o método fica bloqueando
        //até o recebimente de uma mensagem
        DatagramSocket ds = new DatagramSocket(port);     
        System.out.println("Ouvindo a porta: " + port);
        
        //Preparando o buffer de recebimento da mensagem
        byte[] msg = new byte[256];
        //Prepara o pacote de dados
        DatagramPacket pkg = new DatagramPacket(msg, msg.length);     
        //Recebimento da mensagem
        ds.receive(pkg);
        JOptionPane.showMessageDialog(null,new String(pkg.getData()).trim(),
	       "Mensagem recebida",1);
        
        System.out.println("IP: " + pkg.getAddress());
        ds.close(); 
        
        
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        /*Ip do Servidor e porta*/
        Socket clientSocket = new Socket(pkg.getAddress(),1234);
        System.out.println("O cliente se conectou ao servidor!");
        
        /*Lê do Teclado e envia para o servidor*/
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(clientSocket.getOutputStream());
     
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }
        
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
        BufferedReader InFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n' );
        modifiedSentence = InFromServer.readLine();
        
        System.out.println(modifiedSentence);
        clientSocket.close();
        saida.close();
    }
    
}
