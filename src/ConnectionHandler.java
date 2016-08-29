import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jfabiano on 8/26/2016.
 */
public class ConnectionHandler implements Runnable{
    Socket connection;
    public ConnectionHandler()
    {

    }
    public ConnectionHandler(Socket incomingConnection)
    {
        this.connection = incomingConnection;
    }
    public void run()
    {
        handleInput();
    }
    public void handleInput() {
        try

        {
            String[] inputStringArray;//[1] = username
            String name;
            String inputLine;
            // start a server on a specific port. This is what needs to happen in a thread
            // display information about who just connected to our server
            System.out.println("Incoming connection from " + connection.getInetAddress().getHostAddress());

            // this is how we read from the client
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            // this is how we write back to the client
            PrintWriter outputToClient = new PrintWriter(this.connection.getOutputStream(), true);

            // read from the input until the client disconnects
            //On the server side, make sure the first message that each client sends is their name using a message structure
            //like this: name=name-of-client
           name = inputFromClient.readLine();
            System.out.println(name);
            inputStringArray = name.split("=");
            if(inputStringArray[0].equals("name"))
            {

                outputToClient.println("I have your name. Speak, human.");
                while ((inputLine = inputFromClient.readLine()) != null) {
                    System.out.println(inputStringArray[1] + " says: " + inputLine);
                }

            }
            else
            {
                outputToClient.println("Your initial message did not begin with \"name=\". I don't know who you are, sorry.");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
