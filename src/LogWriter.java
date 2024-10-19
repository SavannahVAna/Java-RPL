import java.io.*;

public class LogWriter {
    private BufferedWriter writer;

    public LogWriter(String filename) throws IOException {
        File file = new File(filename);
        this.writer = new BufferedWriter(new FileWriter(file, true));  // Ouvrir le fichier en mode append
        writer.append("---\n");  // Ajouter des retours à la ligne si besoin
    }

    public void log(String message) throws IOException {
        writer.append(message);
        writer.newLine();  // Ajouter un retour à la ligne après chaque message
    }

    public void close() throws IOException {
        if (writer != null) {
            writer.close();  // Fermer le fichier correctement
        }
    }
}