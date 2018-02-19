package bank.driver;

import bank.Bank;
import bank.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client for making and receiving data from server through sockets.
 *
 * @author Hasan Kara
 */
public class SocketDriver implements RemoteBankDriver {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public void connect(String[] args) throws IOException {
        socket = new Socket(args[0], Integer.valueOf(args[1]));
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("connected...");
    }

    @Override
    public void disconnect() throws IOException {
        // TODO check how to properly close a socket
        socket.close();
        System.out.println("disconnected...");
    }

    @Override
    public Bank getBank() {
        return null;
    }

    @Override
    public String sendCommand(Command command) {
        return null;
    }

    @Override
    public String sendCommand(Command command, String arguments) {
        return null;
    }
}
