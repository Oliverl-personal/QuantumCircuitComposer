package model.persistence;


import model.QuantumCircuitComposer;
import model.exceptions.LoadGateErrorException;
import model.exceptions.OverAmountException;
import model.exceptions.RepeatedGateException;
import model.gates.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Adapted from JsonSerializationDemo Repo
// Represents a reader that reads circuit from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads circuit from file and returns it;
    // throws IOException if an error occurs reading data from file
    public QuantumCircuitComposer read() throws IOException,
            LoadGateErrorException, RepeatedGateException, OverAmountException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCircuit(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses and returns circuit from JSON object
    private QuantumCircuitComposer parseCircuit(JSONObject jsonObject)
            throws LoadGateErrorException, RepeatedGateException, OverAmountException {
        String name = jsonObject.getString("name");
        QuantumCircuitComposer qcc = new QuantumCircuitComposer(name);
        addGates(qcc, jsonObject);
        return qcc;
    }

    // MODIFIES: qcc
    // EFFECTS: parses gates from JSON object and adds them to circuit
    private void addGates(QuantumCircuitComposer qcc, JSONObject jsonObject)
            throws LoadGateErrorException, RepeatedGateException, OverAmountException {
        JSONArray jsonArray = jsonObject.getJSONArray("gates");
        for (Object json : jsonArray) {
            JSONObject nextGate = (JSONObject) json;
            addGate(qcc, nextGate);
        }
    }

    // MODIFIES: qcc
    // EFFECTS: parses gate from JSON object and adds it to circuit
    private void addGate(QuantumCircuitComposer qcc, JSONObject jsonObject)
            throws LoadGateErrorException, RepeatedGateException, OverAmountException {
        String id = jsonObject.getString("ID");
        Gate gate;
        switch (id) {
            case HGate.ID:
                gate = new HGate();
                break;
            case YGate.ID:
                gate = new YGate();
                break;
            case NotGate.ID:
                gate = new NotGate();
                break;
            case IGate.ID:
                gate = new IGate();
                break;
            default:
                throw new LoadGateErrorException();
        }
        qcc.addGate(gate);
    }
}
