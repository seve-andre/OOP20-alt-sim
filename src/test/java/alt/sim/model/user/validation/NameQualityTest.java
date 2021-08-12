package alt.sim.model.user.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class NameQualityTest {

    private NameQuality nameQuality = new NameQuality();

    @Test
    void checkName() throws IOException {
        assertEquals(NameValidation.CORRECT, nameQuality.checkName("ciao"));
        assertEquals(NameValidation.WRONG, nameQuality.checkName("ciao@"));
        assertEquals(NameValidation.EMPTY, nameQuality.checkName("    "));
        assertEquals(NameValidation.TOO_LONG, nameQuality.checkName("ciaociaociaoc"));
    }

}
