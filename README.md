# PeerToPeer-Messenger
Peer-To-Peer Messenger Application for LAN use

# About the application

This application was created to provide a safe and faste the communication inside a small company by sending simple messages or sharing files, if a user is not connected to the LAN he cannot communicate with the connected users.

When a computer is connecting to the LAN and the user lunch the application a system notification will be sent to the connected users by sending Broadcast (LAN-Broadcast), and a new green box will be added to the connected user list.

![alt text](https://github.com/CHEREF-Mehdi/PeerToPeer-Messenger/blob/master/ReadMeImages/Screenshot%20(5).png)

To start a conversation you have to select the user by clicking on its green box  inside the connected user list

![alt text](https://github.com/CHEREF-Mehdi/PeerToPeer-Messenger/blob/master/ReadMeImages/Screenshot%20(6).png)

File sharing is also possible, the user must select the file using the filechooser UI, the reciever get 2 notifications the first one is a system notification, rised to prevent the user that someone want to share with him a file, the second notification is a confirm dialog box used to accept or decline the operation.

# important
You have to add to the external libraries "jfoenix.jar" that you find inside the main folder.
