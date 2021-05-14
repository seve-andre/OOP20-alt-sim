package alt.sim.model.user.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NameQualityTest {

    private NameQuality nameQuality = new NameQuality();

    @Test
    public void checkName() {
        assertEquals(NameValidation.CORRECT, nameQuality.checkName("ciao"));
        assertEquals(NameValidation.WRONG, nameQuality.checkName("ciao@"));
        assertEquals(NameValidation.EMPTY, nameQuality.checkName("    "));
        assertEquals(NameValidation.TOO_LONG, nameQuality.checkName("ciaociaociaoc"));
    }

}
