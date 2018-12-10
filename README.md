# PeerToPeer-Messenger
Desktop Peer-To-Peer Messenger Application for LAN use

# Local Area Network (LAN)

A local area network (LAN) is a group of computers and associated devices that share a common communications line or wireless link to a server. Typically, a LAN encompasses computers and peripherals connected to a server within a distinct geographic area such as an office or a commercial establishment. Computers and other mobile devices use a LAN connection to share resources such as a printer or network storage.

# PeerToPeer (P2P) Service

Peer-to-peer (P2P) is a decentralized communications model in which each party has the same capabilities and either party can initiate a communication session. Unlike the client/server model, in which the client makes a service request and the server fulfills the request, the P2P network model allows each node to function as both a client and server.

# About the application

This application was created to provide a safe and faste communication inside a small company by sending simple messages or sharing files, if a user is not connected to the LAN he cannot communicate with the connected users.

When a computer is connected to the LAN and the user lunch the application, each connected user recieve a system notification, and a new green box will be added to the connected-user list.

![alt text](https://github.com/CHEREF-Mehdi/PeerToPeer-Messenger/blob/master/ReadMeImages/Screenshot%20(1).png)

To start a conversation you have to select the user by clicking on its green box  inside the connected user list.

![alt text](https://github.com/CHEREF-Mehdi/PeerToPeer-Messenger/blob/master/ReadMeImages/Screenshot%20(2).png)

File sharing is also supported in this app, the user must select the file using the filechooser UI, the reciever get 2 notifications the first one is a system notification, rised to prevent the user that someone want to share with him a file, the second notification is a confirm dialog box used to accept or decline the operation.

![alt text](https://github.com/CHEREF-Mehdi/PeerToPeer-Messenger/blob/master/ReadMeImages/Screenshot%20(3).png)

![alt text](https://github.com/CHEREF-Mehdi/PeerToPeer-Messenger/blob/master/ReadMeImages/Screenshot%20(4).png)

# Received files

The application will automatically create a sub-folder named Miv-chat located in the default user-folder. 

All the received file will be found inside the Miv-chat folder. 

# UML Sequence diagram
## about the Sequence diagram 
https://www.ibm.com/developerworks/rational/library/3101.html

#### The sequence diagram below is assigned to file sharing task. 
![alt text](https://github.com/CHEREF-Mehdi/PeerToPeer-Messenger/blob/master/ReadMeImages/UML-sequence%20diagram.png)

# Important
You can install this application using set up installer you will find inside the setup-installer folder. 

To use the source code effectively you have to add to the external libraries of the project "jfoenix.jar" that you find inside the main folder.
