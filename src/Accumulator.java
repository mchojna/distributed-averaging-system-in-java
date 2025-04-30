import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Accumulator {
    List<Message> list;

    public Accumulator() {
        this.list = new ArrayList<>();
        Logger.log("[Accumulator]: created accumulator");
    }

    public void put(Message message){
        this.list.add(message);
        Logger.log("[Accumulator]: added number (number: " + message.getNumber() + ")");
    }

    public int getAverage() {
        int average =  (int) this.list.stream()
                .map(Message::getNumber)
                .mapToInt(Integer::intValue)
                .filter(i -> i > 0)
                .average()
                .orElse(0);

        List<Integer> numberList = this.list.stream()
                .map(Message::getNumber)
                .collect(Collectors.toList());

        Logger.log("[Accumulator]: calculated average (list: " + numberList + ", average (w/o 0): " + average + ")");
        return average;
    }
}
