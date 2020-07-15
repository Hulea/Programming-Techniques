import java.io.FileWriter;
import java.io.IOException;

public class InsertValues {

    private String input;
    private String filePath;

    public InsertValues(String input,String filePath) {

        this.input = input;
        this.filePath = filePath;

        try {
            FileWriter g = new FileWriter(filePath, true);
            g.write(this.input /*+ "\n"*/);
            g.close();

        } catch (IOException e) {
            System.out.println("an err has occ");
            e.printStackTrace();

        }
    }


}
