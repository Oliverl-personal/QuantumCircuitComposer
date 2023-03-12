package persistence;

import model.gates.Gate;

import static org.junit.jupiter.api.Assertions.assertEquals;

// test gate fields

public class JsonTest {
    protected void checkGateID(String ID, Gate gate) {
        assertEquals(ID, gate.getID());
    }

}
