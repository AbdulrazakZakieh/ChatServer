package server;

import serverInterface.Room;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class Server {
    static int port=Registry.REGISTRY_PORT; 
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(port);
        reg.rebind(Room.LOOKUPNAME, new Room_Imp());
        //rebind does not need Exception Clause  
        JOptionPane.showMessageDialog(null, "Server Server is Now Running");
    }
}
