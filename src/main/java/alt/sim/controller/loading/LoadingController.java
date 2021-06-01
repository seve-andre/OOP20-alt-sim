package alt.sim.controller.loading;

import java.io.IOException;

import alt.sim.model.user.validation.RecordsValidation;

public class LoadingController {

    /**
     * At first launch, checks user records file existence.
     */
    public void loading() {
        try {
            new RecordsValidation().userRecordsFileValidation();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
