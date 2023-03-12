package model.gates;

import org.ejml.data.CMatrixRMaj;
import org.json.JSONObject;

//The not gate applies a pi radian rotatiom about the x-axis.
// It is equivalent to a classical bit flip
//       ┌───┐
//  q_0: ┤ x ├
//       └───┘
public class NotGate implements Gate {
    public static final String name = "Not Gate";
    public static final String ID = "not";
    private String rotation = "Pi";
    private static CMatrixRMaj matrix;

    //MODIFIES: this
    //EFFECTS:  sets up the matrix for the gate
    public NotGate() {
        matrix = new CMatrixRMaj(2, 2);
        this.matrix.set(0,0, 0, 0);
        this.matrix.set(1,0, 1, 0);
        this.matrix.set(0,1, 1, 0);
        this.matrix.set(1,1, 0, 0);
    }

    //EFFECTS:  returns the matrix of this gate
    public CMatrixRMaj getMatrix() {
        return this.matrix;
    }

    //EFFECTS:  returns the rotation of this gate
    public String getRotation() {
        return this.rotation;
    }

    //MODIFIES: this
    //EFFECTS:  sets the rotation of this gate
    public void setRotation(String rotation) {
        this.rotation = rotation;
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
        return json;
    }
}
