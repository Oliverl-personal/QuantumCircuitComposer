package model;

import model.exceptions.*;
import model.gates.*;
import model.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

//Class holds a circuit made up of one or more quantum gates
public class QuantumCircuitComposer implements Writable {

    private static final int MAX_GATES = 8;
    private List<Gate> gates;
    String name;

    public QuantumCircuitComposer(String name) {
        gates = new ArrayList<Gate>();
        this.name = name;
    }

    //REQUIRES: gate instances already contained inside the list are not allowed
    //MODIFIES: this
    //EFFECT:   adds the gate into the gates list,
    //          throws OverAmountException if number of gate is greater than 8
    //          throws RepeatedGateException if gate instance is contains existing list
    public void addGate(Gate gate) throws RepeatedGateException,
            OverAmountException, LoadGateErrorException {

        if (this.gates.size() >= MAX_GATES) {
            throw new OverAmountException();
        }

        if (this.gates.contains(gate)) {
            throw new RepeatedGateException();
        } else {
            this.gates.add(gate);
        }
    }

    //EFFECTS:  return count of gates on the circuit
    public int getGateCount() {
        return this.gates.size();
    }

    //EFFECTS:  return name of circuit
    public String getName() {
        return this.name;
    }

    //REQUIRES: gate is contained in the list
    //MODIFIES: This
    //EFFECTS:  remove a gate from circuit
    //          throws NoGateFoundException if gate is not found
    public void removeGate(Gate gate) throws NoGateFoundException {
        if (!this.gates.contains(gate)) {
            throw new NoGateFoundException();
        }
        this.gates.remove(gate);
    }

    //REQUIRES: index value is greater or equal to 1 and less or equal to MAX_GATES
    //MODIFIES: This
    //EFFECTS:  remove the gate based on index from circuit
    //          throws IllegalIndexException if index provided is out of range
    public void removeGate(int index) throws IllegalIndexException {
        if (index > gates.size() || index == 0) {
            throw new IllegalIndexException();
        }
        index--;
        this.gates.remove(index);
    }

    //EFFECTS:  returns list of gates in the circuit
    public List<Gate> getGatesInCircuit() {
        return this.gates;
    }

    //EFFECTS:  returns the gates in the circuit in string
    public String getCircuitInString() {
        String circuit = "--";
        for (Gate gate : this.gates) {
            switch (gate.getID()) {
                case HGate.ID:
                    circuit = circuit + "--H[ " + gate.getRotation() + " ]--";
                    break;
                case YGate.ID:
                    circuit = circuit + "--Y[ " + gate.getRotation() + " ]--";
                    break;
                case IGate.ID:
                    circuit = circuit + "--I[ " + gate.getRotation() + " ]--";
                    break;
                default:
                    circuit = circuit + "--Not[ " + gate.getRotation() + " ]--";
                    break;
            }
        }
        circuit = circuit + "--";
        return circuit;
    }

    // Adapted from JsonSerializationDemo Repo
    // EFFECTS: converts this object to JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("gates", gatesToJson());
        return json;
    }

    // Adapted from JsonSerializationDemo Repo
    // EFFECTS: returns gates in this circuit as a JSON array
    private JSONArray gatesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Gate g : gates) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }
}
