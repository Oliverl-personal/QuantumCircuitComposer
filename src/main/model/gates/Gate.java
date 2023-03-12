package model.gates;

import org.ejml.data.CMatrixRMaj;
import org.json.JSONObject;

public interface Gate {
    //EFFECTS:  returns the ID of the quantum gate
    public CMatrixRMaj getMatrix();

    public String getID();

    //EFFECTS:  returns the name of the quantum gate
    public String getName();

    //EFFECTS:  returns the rotation of the quantum gate
    public String getRotation();

    //EFFECTS: returns JSON object for a given gate
    public JSONObject toJson();
}
