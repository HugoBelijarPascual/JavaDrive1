package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainParaCoverage() {
        assertNotNull(app.Main.sc);

        app.Main objetoMain = new app.Main();
        assertNotNull(objetoMain);

    }
}