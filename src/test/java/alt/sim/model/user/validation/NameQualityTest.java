package alt.sim.model.user.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class NameQualityTest {

    private NameQuality nameQuality = new NameQuality();

    @Test
    void checkName() throws IOException {
        assertEquals(NameResult.CORRECT, nameQuality.checkName("ciao"));
        assertEquals(NameResult.WRONG, nameQuality.checkName("ciao@"));
        assertEquals(NameResult.EMPTY, nameQuality.checkName("    "));
        assertEquals(NameResult.TOO_LONG, nameQuality.checkName("ciaociaociaoc"));
    }

}
