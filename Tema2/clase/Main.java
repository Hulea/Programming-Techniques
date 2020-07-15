
public class Main {

    public static void main(String[] args) {

        ExtractValues aux = new ExtractValues("in-test-1.txt");
        Queues tmp = new Queues(aux,"test.txt");
        tmp.start();
    }
}