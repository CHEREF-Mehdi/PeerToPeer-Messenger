package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static sample.ConnectFriends.clients;

public class Controller implements Initializable {
    public int IndexClient;
    public AnchorPane sildeMSG;
    public Timeline timeline;
    public JFXHamburger slidOnOff;
    public JFXButton btnClient;
    public JFXButton btnNotif;
    public VBox slideDyn;
    public Label NameClient;
    public VBox msgArea;
    public VBox slideStat;
    public JFXTextField TFmsg;
    public JFXButton btnSmsg;
    public JFXButton btnSfile;

    public volatile static Boolean   Confirm=false;


    public boolean SlideStatus=false;
    public boolean SlideDynStatusNotif=false;
    public boolean SlideDynStatusClient=false;
    public boolean SlideMsg=false;

    //public int     SlideMsgBIGSize=false;


    public Thread ListningThread; //TH
    public Thread ServerThread;  //TH2
    public ServerSocket srvSocket;
    public Socket stk;
    public PrintWriter sendmsg;
    public BufferedReader recivemsg;
    public volatile static String ImWith="debut";
    public String IpAdress;
    public String HostName;
    public int    MyPort=8089;
    public Text notfcir;
    public JFXButton cnffff;
    public AnchorPane PopupConfirmRecive;
    public JFXButton AcceptRecive;
    public JFXButton DeclineRecive;
    public Label LabelConfirm;
    public AnchorPane ANnotif;
    public VBox SlideNotif;
    public AnchorPane App;
    public   Socket sckt;
    public Label pr;
    public ScrollPane ScgArea;

    private ArrayList<Notification> notif;
    private volatile static Boolean  Decline=false;
    private Thread ThreadSendFile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.print(System.getProperty("user.home"));
        LabelConfirm.setAlignment(Pos.CENTER);
        NameClient.setAlignment(Pos.CENTER);
        notif =new ArrayList<>();
        InetAddress addr = null;

        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        IpAdress = addr.getHostAddress();
        HostName = addr.getHostName();

        Listner();
        RunServer();

        try {
            send("255.255.255.255",1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        App.setOnMousePressed(e1->  {
            Stage stage = (Stage) TFmsg.getScene().getWindow();
            double x = stage.getX() - e1.getScreenX();
            double y = stage.getY() - e1.getScreenY();

            App.setOnMouseDragged(e2->{
                    stage.setX(e2.getScreenX() + x);
                    stage.setY(e2.getScreenY() + y);
            });
        });
        
    }

    public void send(String ip,int etat) throws UnknownHostException {

        System.out.println("IP address of localhost : " + IpAdress);
        System.out.println("Name of hostname : " + HostName);
        String msg=IpAdress+"%"+HostName+"%"+MyPort+"%"+etat;

        try {

            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp =
                    new DatagramPacket(msg.getBytes(),
                            msg.getBytes().length,
                            new InetSocketAddress(ip,60123));
            ds.send(dp);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void RunServer(){

        ServerThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final int port=8089;


                    srvSocket = new ServerSocket(port);
                    System.out.println("Server is up & ready for connections...");

                    while(true){


                        sckt= srvSocket.accept();

                       Thread t=new Thread(new Runnable() {
                           @Override
                           public void run() {

                               String ClientIP,msg;
                               try {
                                   ScgArea.vvalueProperty().bind(msgArea.heightProperty());

                                   float som=0;
                                   ProgressBar p=new ProgressBar();
                                   p.setMinWidth(msgArea.getWidth());
                                   DataInputStream in = null;
                                   DataOutputStream out = null;
                                   PrintWriter prtW= new PrintWriter(sckt.getOutputStream(),true);
                                   BufferedReader BfRd= new BufferedReader(new InputStreamReader(sckt.getInputStream()));
                                   ClientIP=BfRd.readLine();
                                   String typeComunication=BfRd.readLine();
                                   client CurentClient =ConnectFriends.get(ClientIP);
                                   if(typeComunication.equals("true")){//sending file

                                       Label l;

                                       String [] infofile=BfRd.readLine().split("%");;
                                       displayTray(ConnectFriends.get(ClientIP).getHostName()+" want to send you a file");
                                       Platform.runLater(new Runnable() {
                                           @Override
                                           public void run() {
                                               LabelConfirm.setText(CurentClient.getHostName()+" vous envoye un fichier");
                                           }
                                       });


                                       move(0,86,PopupConfirmRecive);
                                       while (!Confirm){


                                       }
                                       Confirm=false;

                                       if(!Decline){
                                           prtW.println("Go");


                                           try {
                                               in = new DataInputStream(sckt.getInputStream());
                                           } catch (IOException ex) {
                                               System.out.println("Can't get socket input stream. ");
                                           }

                                           try {
                                               out = new DataOutputStream(new FileOutputStream(System.getProperty("user.home")+"\\MivChat\\"+infofile[0]));
                                           } catch (FileNotFoundException ex) {
                                               System.out.println("File not found. "+infofile[0]);
                                               (new File(System.getProperty("user.home")+"\\MivChat\\")).mkdirs();
                                               out = new DataOutputStream(new FileOutputStream(System.getProperty("user.home")+"\\MivChat\\"+infofile[0]));

                                           }



                                           byte[] bytes = new byte[32*1024];

                                           /*ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                                           executor.scheduleAtFixedRate(new Runnable() {

                                               @Override
                                               public void run() {*/
                                                   Platform.runLater(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           msgArea.getChildren().addAll(p);
                                                          // pr.setText((int)(som/1000000)+"Mb");
                                                           //som=0;
                                                       }
                                                   });/*
                                               }
                                           }, 0, 1, TimeUnit.SECONDS);*/


                                           int count;
                                            som=0;
                                           // int perdu
                                           System.out.println("start recieve");
                                           while ((count = in.read(bytes)) > 0) {
                                               som=som+count;
                                               final float x=som;
                                               Platform.runLater(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                     //  pr.setText((int)(som*100/Float.parseFloat(infofile[1]))+"%");
                                                       p.setProgress(x/Float.parseFloat(infofile[1]));
                                                   }
                                               });
                                               out.write(bytes, 0, count);

                                           }
                                           System.out.println("end recieving"+som);

                                           //executor.shutdown();

                                           out.close();
                                           in.close();

                                           prtW.println("receved");
                                           l=CreateLabel("vous avez reçu un fichier de "+CurentClient.getHostName(),Color.BLACK,"a3a6a4",Pos.CENTER);

                                           CurentClient.getMsg().add(new InstantMessage("vous avez reçu un fichier de "+CurentClient.getHostName(),1));
                                       }
                                       else {
                                           prtW.println("Decline");

                                           l=CreateLabel("vous avez refusé l' envoie  de"+CurentClient.getHostName(),Color.BLACK,"a3a6a4",Pos.CENTER);

                                           CurentClient.getMsg().add(new InstantMessage("vous avez refusé l' envoie de"+CurentClient.getHostName(),1));
                                           System.out.println("vous avez refusé l' envoie  de"+CurentClient.getHostName() );
                                           Decline=false;
                                       }
                                       Platform.runLater(new Runnable() {
                                           @Override
                                           public void run() {

                                               msgArea.setSpacing(15);
                                               msgArea.getChildren().addAll(l);
                                           }
                                       });

                                   }else{

                                       System.out.println("User '"+ClientIP+"' is now connected...");

                                       if((msg=BfRd.readLine())!=null){
                                           System.out.println("incomming message from user '"+ClientIP+"': "+msg);

                                           prtW.println("SERVER: <--MESSAGE RECEIVED-->#"+msg+"#");

                                           CurentClient.getMsg().add(new InstantMessage(msg,0));
                                           if(!ClientIP.equals(ImWith)){
                                               CurentClient.setNotification(1);
                                               notfcir.setText(String.valueOf(getNotif()));
                                               ANnotif.setVisible(true);

                                           }
                                           else {
                                               Platform.runLater(new Runnable() {
                                                   @Override public void run() {
                                                       Label l=CreateLabel(" "+msg,Color.BLACK,"a3a6a4",Pos.CENTER_LEFT);

                                                       msgArea.setSpacing(15);
                                                       msgArea.getChildren().addAll(l);
                                                      // ScgArea.setVvalue(1.0);


                                                   }});
                                           }

                                       }

                                   }

                               }catch (IOException e){
                                   e.printStackTrace();
                               } catch (AWTException e) {
                                   e.printStackTrace();
                               }

                           }
                       });
                t.start();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ServerThread.start();
    }

    

    public void Listner(){
        ListningThread=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    byte [] data = new byte[256];
                    DatagramSocket ds = new DatagramSocket(60123);
                    DatagramPacket dp = new DatagramPacket(data,data.length);
                    while (true) {
                        ds.receive(dp);
                        String InfoFriend = new String(dp.getData(),
                                0,dp.getLength());


                        System.out.println("Received "+InfoFriend);
                         String [] infoPeer=InfoFriend.split("%");
                        if(Integer.parseInt(infoPeer[3])!=1)
                        {
                            ArrayList<client> cli=ConnectFriends.clients;
                           for(int i=0;i< cli.size();i++){
                               if(cli.get(i).getAdrIP().equals(infoPeer[0])){
                                   cli.remove(i);
                                   IndexClient=i;
                                   Platform.runLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           if(infoPeer[0].equals(NameClient.getId())){btnCloseAction(null); ImWith="debut";msgArea.getChildren().remove(0,msgArea.getChildren().size());}

                                           slideDyn.getChildren().remove(IndexClient+1);
                                           setnotif();
                                       }
                                   });

                               }
                           }
                        }
                        else {
                            //uncomment below to remove your computer from the connected friend list
                            if(!infoPeer[0].equals(IpAdress)&&!ConnectFriends.find(infoPeer[0])){
                                send(infoPeer[0],1);
                                client freind=new client(infoPeer[0],infoPeer[1],infoPeer[2]);
                                displayTray(infoPeer[1]+" is now connected");
                                clients.add(freind);


                                Platform.runLater(new Runnable() {
                                    @Override public void run() {

                                        JFXButton btnFiend=new JFXButton(infoPeer[1]);
                                        btnFiend.setId(infoPeer[0]);
                                        btnFiend.setOpacity(0.8);
                                        btnFiend.setTextFill(Color.WHITE);

                                        btnFiend.setStyle("-fx-background-color : #0cb754 ; -fx-border-color : #2d364c ; -fx-font-size : 19; -fx-font-weight: bold;");
                                        btnFiend.setOnAction(e->{
                                            ShowSlideMessage(btnFiend,e);

                                        });

                                        btnFiend.setMinWidth(240);
                                        slideDyn.getChildren().add(btnFiend);
                                        slideDyn.setSpacing(3);

                                    }
                                });
                            }

                        }


                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });



        ListningThread.start();

    }


    public void sendfile(String filePath,String fileName,String Ip) throws IOException {
        client CurentClient=ConnectFriends.get(Ip);
        Label l;


        Socket socket = null;
        String respend;

        socket = new Socket(Ip, 8089);
        sendmsg=new PrintWriter(socket.getOutputStream(),true);
        recivemsg=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sendmsg.println(IpAdress);
        sendmsg.println("true");

        File file = new File(filePath);
        long length = file.length();
        sendmsg.println(fileName+"%"+length);
        while((respend=recivemsg.readLine())==null){

        }
        if(respend.equals("Go")){
            l=CreateLabel("vous avez envoyé un  fichier  à "+CurentClient.getHostName(),Color.WHITE,"0cb754",Pos.CENTER);
            CurentClient.getMsg().add(new InstantMessage("vous avez envoyé un fichier  à "+CurentClient.getHostName(),1));

            // Get the size of the file

            byte[] bytes = new byte[32*1024];
            InputStream in = new FileInputStream(file);
            OutputStream out =new DataOutputStream(socket.getOutputStream()) ;
           // System.out.println(length);
            int count;

            //socket.setKeepAlive(true);
            while ((count = in.read(bytes)) > 0) {
                //System.out.println(count);
                out.write(bytes, 0, count);

            }

            System.out.println("out"+count);

            System.out.println("donne");

            out.close();
            in.close();

       try {
           while((respend=recivemsg.readLine())==null){

           }
       }catch (Exception e){

       }

        }
        else {
            l=CreateLabel(CurentClient.getHostName()+" a refusé l'envoie du fichier",Color.BLUE,"00ff25",Pos.CENTER);
            CurentClient.getMsg().add(new InstantMessage(CurentClient.getHostName()+"  a refusé l'envoie du fichier",1));




        }



        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                msgArea.getChildren().addAll(l);

                msgArea.setSpacing(15);
            }
        });



       socket.close();


    }




//////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**********************  Inrerface Graphique*//****************************************************/
    public void close(Event event) throws IOException {
        send("255.255.255.255",0);
       ListningThread.stop(); //TH
        ServerThread.stop();  //TH2

       srvSocket.close();

        Stage s=(Stage)TFmsg.getScene().getWindow();
        s.close();
        System.exit(0);

    }

    public void min(Event event) {
        Stage s=(Stage)TFmsg.getScene().getWindow();
        s.setIconified(true);
    }

    public void slidOnOff(Event event) {
        if(SlideStatus){
            move(-168,0,slideStat);
            SlideStatus=false;
            if(SlideDynStatusClient){
                move(-168,0,slideDyn);

            }
            else {
                if(SlideDynStatusNotif){
                    move(-168,0,SlideNotif);

                }
            }

        }
        else {
            move(168,0,slideStat);
            SlideStatus=true;
            if(SlideDynStatusClient){
                move(168,0,slideDyn);

            }
            else {
                if(SlideDynStatusNotif){
                    move(168,0,SlideNotif);

                }
            }
        }
    }

    public void btnSfileAction(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();

        File selectedFile = fileChooser.showOpenDialog(null);



        if (selectedFile != null) {



            System.out.println("File selected: " + selectedFile.getAbsolutePath());
           ThreadSendFile=new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        sendfile(selectedFile.getAbsolutePath(),selectedFile.getName(),NameClient.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
            ThreadSendFile.start();
        }

        else {

            System.out.println("File selection cancelled.");

        }

    }



    public void btnNotifAction(ActionEvent actionEvent) {
        if(SlideDynStatusNotif){
            move(-409,0,SlideNotif);
            SlideDynStatusNotif=false;


            SlideNotif.getChildren().remove(1,SlideNotif.getChildren().size());
        }
        else {


            if(!SlideDynStatusClient)
            {move(409,0,SlideNotif);
            SlideDynStatusNotif=true;

            }
            else{
                move2(-409,0,slideDyn,SlideNotif);
                SlideDynStatusClient=false;
                SlideDynStatusNotif=true;
            }

            ArrayList<client> cli=ConnectFriends.clients;
            for (int i=0;i<cli.size();i++){

                if(cli.get(i).getNotification()>0){
                    JFXButton b=new JFXButton();

                    b.setId(cli.get(i).getAdrIP());
                    b.setMinWidth(240);
                    b.setText(cli.get(i).getHostName());
                    b.setOpacity(0.8);
                    b.setTextFill(Color.WHITE);

                    b.setStyle("-fx-background-color : #0cb754 ; -fx-border-color : #2d364c ; -fx-font-size : 19; -fx-font-weight: bold;");

                    b.setOnAction(e->{
                        ShowSlideMessage(b,e);
                        for(int j=0;j<SlideNotif.getChildren().size();j++){
                            if(((Button)SlideNotif.getChildren().get(j)).getId().equals(b.getId())){
                                SlideNotif.getChildren().remove(j);break;
                            }
                        }
                    });

                    SlideNotif.getChildren().add(b);
                    SlideNotif.setSpacing(3);

                }
            }
        }



    }

    public void btnClientAction(ActionEvent actionEvent) {

        if(SlideDynStatusClient){
            move(-409,0,slideDyn);
            SlideDynStatusClient=false;

        }
        else {
            if(!SlideDynStatusNotif)
            {move(409,0,slideDyn);
                SlideDynStatusClient=true;

            }
            else{
                move2(-409,0,SlideNotif,slideDyn);
                SlideDynStatusClient=true;
                SlideDynStatusNotif=false;
            }
        }
    }

    public void move(int x, int y, Node a){
        timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(a.translateXProperty(), a.getTranslateX()),
                        new KeyValue(a.translateYProperty(), a.getTranslateY()),
                        new KeyValue(a.scaleXProperty(), 1),
                        new KeyValue(a.scaleYProperty(), 1)),

                new KeyFrame(Duration.seconds(0.2), // set end position at 40s
                        new KeyValue(a.translateXProperty(),  a.getTranslateX()+x),
                        new KeyValue(a.translateYProperty(),  a.getTranslateY()+y),
                        new KeyValue(a.scaleXProperty(), 1),
                        new KeyValue(a.scaleYProperty(), 1)));


        timeline.play();

    }

    public void move2(int x, int y, Node a,Node b){
        timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(a.translateXProperty(), a.getTranslateX()),
                        new KeyValue(a.translateYProperty(), a.getTranslateY()),
                        new KeyValue(a.scaleXProperty(), 1),
                        new KeyValue(a.scaleYProperty(), 1)),

                new KeyFrame(Duration.seconds(0.2), // set end position at 40s
                        new KeyValue(a.translateXProperty(),  a.getTranslateX()+x),
                        new KeyValue(a.translateYProperty(),  a.getTranslateY()+y),
                        new KeyValue(a.scaleXProperty(), 1),
                        new KeyValue(a.scaleYProperty(), 1)));


        timeline.play();
        timeline.setOnFinished(e->{
            move(-x,y,b);
        });

    }

    public void testAction(ActionEvent actionEvent) {
        move(-537,0,sildeMSG);
        SlideMsg=true;
    }

    public void btnCloseAction(ActionEvent actionEvent) {

         ImWith="debut";
            move(+537,0,sildeMSG);

        SlideMsg=false;
    }

    public void cnffffAction(ActionEvent actionEvent) {
        Confirm=true;
    }

    public void displayTray(String msg) throws AWTException, java.net.MalformedURLException {
        //Obtain only one instance of the SystemTray InstantMessage
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        trayIcon.displayMessage("Notification",msg, TrayIcon.MessageType.INFO);
    }

    public void AcceptReciveAction(ActionEvent actionEvent) {
        Confirm=true;
        move(0,-86,PopupConfirmRecive);
    }

    public void DeclineReciveAction(ActionEvent actionEvent) {
        Decline=true;
        Confirm=true;
        move(0,-86,PopupConfirmRecive);

    }

    public Label CreateLabel(String msg,Color Textcolor,String labelColor,Pos position){
        Label l=new Label();

        l.setTextFill(Textcolor);
        l.setAlignment(position);
        l.setStyle(""+labelColor);
        l.setMinWidth((msgArea.getWidth()-5)/2);
        l.setPrefHeight(40);
        l.setMinHeight(40);
        l.setText(msg);
        l.setStyle("-fx-background-radius: 15; -fx-font-size : 19;  -fx-background-color : #"+labelColor);
        return l;
    }

    public int getNotif(){
        int som=0;
        ArrayList<client> Client=ConnectFriends.clients;
        for(int i=0;i<Client.size();i++){
            som+=Client.get(i).getNotification();
        }
        return som;
    }

    public void ShowSlideMessage(Button btnFiend,ActionEvent e){

        NameClient.setId(btnFiend.getId());
        NameClient.setText(btnFiend.getText());
        client CurrentClient=ConnectFriends.get(btnFiend.getId());
        TFmsg.setOnKeyPressed(e2-> {

            if(e2.getCode().toString().equals("ENTER")&&!TFmsg.getText().equals("")){

                String msg;
                try {

                    stk=new Socket(btnFiend.getId(),CurrentClient.getPort());
                    sendmsg=new PrintWriter(stk.getOutputStream(),true);
                    recivemsg=new BufferedReader(new InputStreamReader(stk.getInputStream()));
                    sendmsg.println(IpAdress);
                    sendmsg.println("false");
                    sendmsg.println(TFmsg.getText());

                    while((msg=recivemsg.readLine())==null){

                    }
                    stk.close();
                    HBox  box=new HBox();
                    Label l=CreateLabel(" "+TFmsg.getText(),Color.WHITE,"0cb754",Pos.CENTER_LEFT);
                    Label l2=CreateLabel("",Color.WHITE,"ffffff",Pos.CENTER_RIGHT);
                    l2.setOpacity(0);
                    box.getChildren().addAll(l2,l);
                    msgArea.setSpacing(15);

                    msgArea.getChildren().addAll(box);
                    CurrentClient.getMsg().add(new InstantMessage(TFmsg.getText(),1));
                    TFmsg.setText("");



                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //au demarage de l'app
        if(ImWith.equals("debut")&&getNotif()==0){

            testAction(e);
            ImWith=btnFiend.getId();


        }
        else{
            if(!ImWith.equals(btnFiend.getId()))//demarer une autre discution
            {
                if(!SlideMsg){
                    testAction(e);

                }
                else {

                    move2(+537,0,sildeMSG,sildeMSG);

                }

                ImWith=btnFiend.getId();

                msgArea.getChildren().remove(0,msgArea.getChildren().size());
                ImWith=btnFiend.getId();
                for(int i = 0; i< CurrentClient.getMsg().size(); i++){




                    InstantMessage message=CurrentClient.getMsg().get(i);
                    if(message.getCle()==1){
                        HBox  box=new HBox();
                        Label l=CreateLabel(" "+message.getMsg(),Color.WHITE,"0cb754",Pos.CENTER_LEFT);
                        Label l2=CreateLabel("",Color.WHITE,"ffffff",Pos.CENTER_RIGHT);
                        l2.setOpacity(0);
                        box.getChildren().addAll(l2,l);
                        msgArea.setSpacing(25);

                        msgArea.getChildren().addAll(box);
                    }
                    else {
                        Label l;
                        l=CreateLabel(" "+message.getMsg(),Color.BLACK,"a3a6a4",Pos.CENTER_LEFT);
                        msgArea.setSpacing(25);
                        msgArea.getChildren().addAll(l);
                    }

                }
                CurrentClient.setNotification(0);
                setnotif();
            }
            else //
            {if(!SlideMsg){
                testAction(e);
            }

            }



        }


    }

    public void setnotif(){
        int allNotif=getNotif();
        if(allNotif>0)
            notfcir.setText(String.valueOf(allNotif));
        else {
            ANnotif.setVisible(false);
        }

    }

    public void scrollAction(Event event) {
       // System.out.println(ScgArea.getVvalue());
        ScgArea.vvalueProperty().unbind();
    }

 /*   public void MouveApp(Event event) {
    }*/
}
