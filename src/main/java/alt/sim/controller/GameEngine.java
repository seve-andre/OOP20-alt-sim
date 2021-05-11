package alt.sim.controller;

public interface GameEngine {
    /**
     * Capture frames of view once every "Period".
     */
    void mainLoop();
    /**
     * Detect the action executed by the mouse. 
     */
    void processInput();
    /**
     * Update model state following input commands. 
     */
    void update();
    /**
     * Update view interface. 
     */
    void render();

}
