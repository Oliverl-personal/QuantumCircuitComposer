package model.persistence;

import model.QuantumCircuitComposer;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of Circuit to file
// Adapted from JsonSerializationDemo Repo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter fileWriter;
    private String destinationFile;

    // EFFECTS: constructs writer, stores destinationFile path
    public JsonWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: opens writer;
    // throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        fileWriter = new PrintWriter(new File(destinationFile));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of circuit to file
    public void write(QuantumCircuitComposer qcc) {
        JSONObject json = qcc.toJson();
        fileWriter.print(json.toString(TAB));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        fileWriter.close();
    }

}
