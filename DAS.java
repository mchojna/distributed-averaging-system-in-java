import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class DAS {
    public static void main(String[] args) {
        Logger.state = false;
        Logger.log("[Main]: started");

        if(checkRequirements(args)){
            int PORT = Integer.parseInt(args[0]);
            int NUMBER = Integer.parseInt(args[1]);

            try {
                Server server = new Server(PORT, NUMBER);
                boolean start = true;
                while(start) {
                    start = server.getMessage();
                }
            } catch (SocketException ex1) {
                try {
                    DatagramSocket socket = new DatagramSocket();
                    Client client = new Client(socket);
                    client.sendMessage(PORT, NUMBER);
                    client.waitForAcknowledgement(PORT, NUMBER);
                    client.close();
                } catch (IOException ex3) {
                    throw new RuntimeException();
                }
            }
            catch (IOException ex2) {
                throw new RuntimeException();
            }
        }
        Logger.log("[Main]: finished");
    }

    private static boolean checkRequirements(String[] arr){
        if(arr.length == 2) {
            try{
                int PORT = Integer.parseInt(arr[0]);
                int NUMBER = Integer.parseInt(arr[1]);

                if(PORT > 65535 || PORT < 0) {
                    Logger.log("[Main]: requirements failed 2");
                    return false;
                }
                if(NUMBER < -1) {
                    Logger.log("[Main]: requirements failed 3");
                    return false;
                }

                Logger.log("[Main]: requirements passed (port: " + arr[0] + ", number: " + arr[1] + ")");
                return true;

            } catch (Exception ex) {
                Logger.log("[Main]: requirements failed 4");
                return false;
            }
        } else {
            Logger.log("[Main]: requirements failed 1");
            return false;
        }
    }
}

