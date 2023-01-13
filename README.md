# Group-Chat-Sockets-JavaFX

Group Messenger Application: Comm App

Video: https://drive.google.com/file/d/1AsXfFfZUVrBZ0urcqJnRiyoCoGUvlrtC/view?usp=sharing

Introduction:
The Comm App comprises two JavaFX applications: One Server GUI and multiple
Client GUIs. Once a Server is run, as many clients as necessary - such as a family -
have their own individual client GUI’s where they form a group chat and can
communicate with one another. The messages sent between client and server are
encrypted using AES and then sent to a Cloud-based store on Azure MySQL
database. This is all viewed by the user on an HCI-involved graphical user interface
of pink and blue colour schemes.

<img width="704" alt="Screenshot 2022-12-17 at 4 42 36 PM" src="https://user-images.githubusercontent.com/86271636/212235618-c7aac183-b003-4828-9fd8-681459c35ddf.png">
<img width="703" alt="Screenshot 2022-12-17 at 4 43 42 PM" src="https://user-images.githubusercontent.com/86271636/212235631-0880f3d3-9e95-492d-bcaf-cf3881d2d4d2.png">


Compiling Client and Server GUI:
To compile both the client and server GUIs, a JavaFX project and its required .jar
files are required. Furthermore, a
- MySQL connector .jar file was required to connect Java to the MySQL
database to save messages
- Commons codec 1.9 .jar file was required for its simple encoder to the
Base64 format that was required for using AES encryption.

Database design:
The messages database was stored on the cloud-based Azure Database for MySQL
flexible server. This was then connected to MySQL Workbench. The design of the
table fields and a sample of the table data are as follows:
Another database for usernames and passwords were designed, but this was not
used in the final design.

Functionality: C Level
Message exchange between server and client using TCP sockets: Completed
A threaded server that can serve multiple chatting clients: Completed
A threaded client: Completed
Within Comm App, both the server and client applications are threaded to allow for
seamless use of the GUI whilst having their corresponding servers open to send and
receive messages.

Functionality: B Level
GUI that allows the user to configure the server and the client part of the application
(i.e. choosing a client name, IP address, port number, etc. ): Completed
Messages are stored in a database on the server side: Completed
Messages are encrypted using Caesar cipher: Messages encrypted using AES
A GUI was created with HCI values in mind and a pink and blue coloured interface
denoting the difference between Server and Client. The messages in the server side
are still encrypted when displayed on the GUI to show the differences.

Functionality: A Level
Clients can exchange messages while the server keeps track of the messages
exchanged using a cloud-based store on Azure Database for MySQL flexible server:
Completed
Messages are sent encrypted using AES: Completed
Before messages are sent from the client or server, they are encrypted. Then once
they are received, they are decrypted using AES: where there is a symmetrical key:
the passwords are the same on both sides. This means that the password key must
be put in the application before the application is given to the clients and the server
moderator.

A secure login service for the client to send the user credentials, and the server
checks them against the saved ones: Attempted but not completed
I created a database table with usernames and passwords. Furthermore, I created
working queries in the DatabaseManager class to be able to check using a boolean
whether or not the username and password was correct and present. However…
The difficulty with my application is that I continued to send messages as strings,
rather than as an object where I would be able to code them for what a specific
message from client to server meant. This made it very difficult to implement
username - password logins without changing the entire structure of the code. If I
had extra time, I would start again with this formulation from the beginning.
Deploying the server application on Azure VM Windows 10: Attempted but not
completed

An Azure account was created and an Azure windows virtual machine was up and
running. I was able to run both the client and the server on the VM. However, I was
unable to attach the server in the VM to the allocated public IP address: spending
hours troubleshooting this to no avail. If the client and server are both in the VM then
I am able to connect them. I was not able to navigate through reducing the firewall
and to allocate the server that I created on the VM to the public IP address
