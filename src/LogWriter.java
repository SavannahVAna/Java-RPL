import java.io.*;

public class LogWriter {
    private BufferedWriter writer;
    public LogWriter(String filename) throws IOException {
        File file = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        this.writer = writer;
        writer.append("---");
    }
    public void log(String message) throws IOException {
        writer.append(message);
    }
}
