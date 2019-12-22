package server;

import serverInterface.Room;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import clientInterface.Member;


public class Room_Imp extends UnicastRemoteObject implements Room {
    ArrayList<Member> roomMembers;  
    String Message;
   //---------------------------------------------------------------------------
    public Room_Imp() throws RemoteException {
      roomMembers = new ArrayList<>();
      Message = "";
    }
   //---------------------------------------------------------------------------
    @Override
    public void  signIn(Member newMember) throws RemoteException {
      roomMembers.add(newMember);
    }
  //----------------------------------------------------------------------------   
    @Override
    public void sendToAll(Member sender,String message) throws RemoteException {
      this.Message = sender.getMemberName() + " >> " + message;   
      Member reciever;
      for(int i = roomMembers.size() - 1; i >= 0; i--) { 
          reciever = roomMembers.get(i);
          try{ 
               reciever.receiveText(this.Message);
              }catch (RemoteException ex) {
                roomMembers.remove(reciever);
              }
        }// for (int i=....
    }
    //-------------------------------------------------------------------------- 
    @Override
   public void sendToMember(Member sender, int recieverID, String message) throws RemoteException{
     Member  reciever = null;
     for(Member member : roomMembers) {
        if(member.getID() == recieverID) {
           reciever = member;
           break;
          }
       }
        //.....................
      
         try{
               if (reciever != null) {
               sender.receiveText("[private] "+ sender.getMemberName() + " >> "+ message);
               reciever.receiveText("[private] "+ sender.getMemberName()+" >> "+ message);
               }
            }catch(RemoteException ex){
                roomMembers.remove(reciever);    
            }
    } 
    //--------------------------------------------------------------------------
    @Override
     public void signOut(Member member) throws RemoteException {
       roomMembers.remove(member);
     }
 }
//==============================================================================