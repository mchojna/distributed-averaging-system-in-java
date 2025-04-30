import java.io.IOException;
import java.net.*;

public class Server {
    private final int port;
    private final DatagramSocket socket;
    private final Accumulator accumulator;

    public Server(int port, int number) throws SocketException {
        this.port = port;
        this.socket = new DatagramSocket(port);
        this.accumulator = new Accumulator();
        Logger.log("[Server]: created server (port: " + this.port + " , number: " + number + ")");

        this.accumulator.put(Message.getMessageFromInt(number));
    }

    public boolean getMessage() throws IOException {
        byte[] buffer = new byte[11];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        this.socket.receive(packet);

        Message message = Message.getMessageFromBytes(buffer);
        if (message.getNumber() == -2) {
            return true;
        }
        this.accumulator.put(message);
        this.sendAcknowledgement(packet.getAddress(), packet.getPort());

        int number = message.getNumber();
        Logger.log("[Server]: received message (number: " + number + ")");

        if(number == -1) {
            System.out.println(number);
            this.broadcastMessage(number);
            this.close();
            return false;
        } else if (number == 0) {
            int average = this.accumulator.getAverage();
            System.out.println(average);
            this.broadcastMessage(average);
        } else {
            System.out.println(number);
        }
        return true;
    }

    public void broadcastMessage(int number) {
        try {
            this.socket.setBroadcast(true);
            Message message = Message.getMessageFromInt(number);
            byte[] buffer = message.getBytes();

            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), port);
                this.socket.send(packet);
                Logger.log("[Server]: broadcasted message (number: " + number + ", address: 255.255.255.255)");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.socket.setBroadcast(false);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAcknowledgement(InetAddress address, int port) {
        Message message = Message.getMessageFromInt(-2);
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        try {
            this.socket.send(packet);
            Logger.log("[Server]: sent acknowledgment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close (){
        Logger.log("[Server]: closed server");
        this.socket.close();
    }
}
