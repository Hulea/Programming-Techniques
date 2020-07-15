import java.io.FileWriter;
import java.io.IOException;

public class WriteInFile {

    private String input;
    private String filePath;
    boolean append;

    public WriteInFile(String input,String filePath,boolean append){
        this.input = input;
        this.filePath = filePath;
        this.append = append;

        try{
            FileWriter g = new FileWriter(filePath,append);
            g.write(this.input);
            g.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
