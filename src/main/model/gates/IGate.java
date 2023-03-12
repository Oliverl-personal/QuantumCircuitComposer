package model.gates;

import org.ejml.data.CMatrixRMaj;
import org.json.JSONObject;

import static java.lang.Math.sqrt;

// Identity gate does not change the state of the qubit,
// corresponds to a gate wait cycle
//       ┌───┐
//  q_0: ┤ I ├
//       └───┘
public class IGate implements Gate {

    public static final String name = "Identity Gate";
    public static final String ID = "i";
    private String rotation = "0";
    private static CMatrixRMaj matrix;

    //MODIFIES: this
    //EFFECTS:  sets up the matrix for the gate
    public IGate() {
        matrix = new CMatrixRMaj(2, 2);
        this.matrix.set(0,0, 1, 0);
        this.matrix.set(1,0, 0, 0);
        this.matrix.set(0,1, 0, 0);
        this.matrix.set(1,1, 1, 0);
    }

    //EFFECTS:  returns the rotation of this gate
    public String getRotation() {
        return rotation;
    }

    //MODIFIES: this
    //EFFECTS:  sets the rotation of this gate
    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    @Override
    public CMatrixRMaj getMatrix() {
        return this.matrix;
    }

    //EFFECTS:  returns the ID of this gate
    @Override
    public String getID() {
        return this.ID;
    }

    //EFFECTS:  returns the name of this gate
    @Override
    public String getName() {
        return this.name;
    }

    // Adapted from JsonSerializationDemo Repo
    //EFFECTS:  Returns the json object populated with gate ID key and rotation
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ID", ID);
        json.put("rotation", rotation);
        return json;
    }
}
