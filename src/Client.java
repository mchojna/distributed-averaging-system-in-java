import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private final DatagramSocket socket;

    public Client (DatagramSocket socket) {
        this.socket = socket;
        Logger.log("[Client]: created client");
    }

    public void sendMessage(int port, int number) {
        Message message = Message.getMessageFromInt(number);
        byte[] buffer = message.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), port);
            this.socket.send(packet);
            Logger.log("[Client]: sent message (number: " + number + ")");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void waitForAcknowledgement(int port, int number) {
        int attempts = 0;
        boolean recieved = false;

        while (attempts < 3 && !recieved) {
            try {
                this.socket.setSoTimeout(2000);

                byte[] buffer = new byte[11];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);

                Message message = Message.getMessageFromBytes(buffer);
                int answer = message.getNumber();

                if (answer == -2) {
                    recieved = true;
                    Logger.log("[Client]: received acknowledgment");
                }

            } catch (IOException e) {
                Logger.log("[Client]: no acknowledgment received (attempt: " + attempts + ")");
                attempts++;
                if (attempts < 3) {
                    this.sendMessage(port, number);
                    Logger.log("[Client]: resent message");
                } else {
                    Logger.log("[Client]: maximum attempts reached");
                }
            }
        }
    }

    public void close() {
        this.socket.close();
    }
}
