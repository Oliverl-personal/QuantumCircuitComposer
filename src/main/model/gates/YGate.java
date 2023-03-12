package model.gates;

import org.ejml.data.CMatrixRMaj;
import org.json.JSONObject;

//The Pauli Y equate, respectively, to a rotation around the x, y and z axes of
// the Bloch sphere by pi radians.
//       ┌───┐
//  q_0: ┤ Y ├
//       └───┘
public class YGate implements Gate {
    public static final String name = "Pauli-Y Gate";
    public static final String ID = "y";
    private static final String ROTATION = "Pi";
    private static CMatrixRMaj matrix;

    //MODIFIES: this
    //EFFECTS:  sets up the matrix for the gate
    public YGate() {
        matrix = new CMatrixRMaj(2, 2);
        this.matrix.set(0,0, 0, 0);
        this.matrix.set(1,0, 0, 1);
        this.matrix.set(0,1, 0, -1);
        this.matrix.set(1,1, 0, 0);
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
        return matrix;
    }

    //EFFECTS:  Returns the ID of gate
    @Override
    public String getID() {
        return ID;
    }

    // Adapted from JsonSerializationDemo Repo
    //EFFECTS:  Returns the json object populated with gate ID key.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ID", ID);
        return json;
    }
}
