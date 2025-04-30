import java.util.Arrays;

public class Message {
    private final int size;
    private final String number;

    private Message(String number) {
        this.size = number.getBytes().length;
        this.number = number;
        Logger.log("[Message]: created message (size: "+ this.size + ", message: " + this.number +")");
    }

    public static Message getMessageFromInt(int number){
        return new Message(String.valueOf(number));
    }

    public static Message getMessageFromBytes(byte[] buffer){
        int bufferSize = buffer[0];
        String bufferNumber = new String(buffer, 1, bufferSize);
        return new Message(bufferNumber);
    }

    public byte[] getBytes() {
        byte[] buffer = new byte[1 + this.size];

        buffer[0] = (byte) this.size;
        for (int i = 0; i < this.number.length(); i++) {
            buffer[i + 1] = (byte) this.number.charAt(i);
        }
        Logger.log("[Message]: created message (buffer size: " + buffer.length + ", buffer message size: " + buffer[0]
                + ", buffer message:" + Arrays.toString(buffer) +")");
        return  buffer;
    }

    public int getNumber() {
        Logger.log("[Message]: got number (number: " + this.number + ")");
        return Integer.parseInt(this.number);
    }
}
