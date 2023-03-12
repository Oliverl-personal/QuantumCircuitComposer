package model.gates;

import model.bits.Qubit;
import org.ejml.data.CMatrixRMaj;
import org.json.JSONObject;

import static java.lang.Math.sqrt;

// The Hadamard gate transforms the qubit into a superposition of both states, with equal
// probabilities of measuring either 0 or 1
//       ┌───┐
//  q_0: ┤ H ├
//       └───┘
public class HGate implements Gate {

    public static final String name = "Hadamard Gate";
    public static final String ID = "h";
    private static final String ROTATION = "1/sqrt(2)";
    private static CMatrixRMaj matrix;

    //MODIFIES: this
    //EFFECTS:  sets up the matrix for the gate
    public HGate() {
        matrix = new CMatrixRMaj(2, 2);
        this.matrix.set(0,0, (float) (1 / sqrt(2)), 0);
        this.matrix.set(1,0, (float) (1 / sqrt(2)), 0);
        this.matrix.set(0,1, (float) (1 / sqrt(2)), 0);
        this.matrix.set(1,1, (float) (-1 / sqrt(2)), 0);
    }

    //EFFECTS:  returns the rotation of gate
    @Override
    public String getRotation() {
        return ROTATION;
    }

    //EFFECTS:  Returns the name of gate
    @Override
    public String getName() {
        return name;
    }


    @Override
    public CMatrixRMaj getMatrix() {
        return this.matrix;
    }

    //EFFECTS:  Returns the ID of gate
    @Override
    public String getID() {
        return ID;
    }

    // Adapted from JsonSerializationDemo Repo
    //EFFECTS:  Returns the json object populated with gate ID key
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ID", ID);
        return json;
    }
}
