package alt.sim.controller.engine;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.view.PlaneMouseMove;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameEngineImpl implements GameEngine {

    private static final long PERIOD = 400L;
    private SpawnObject spawn;
    private PlaneMouseMove plane;
    private Point2D[] vet;
    private Point2D[] coordinatesTest;

    private PathTransition pathTransition;
    private Path path = new Path();

    private int cont;
    private boolean start;

    public GameEngineImpl(final PlaneMouseMove plane) {
        this.pathTransition = new PathTransition();
        spawn = new SpawnObjectImpl();
        this.plane = plane;

        start = false;
        this.cont = 0;

        path.getElements().add(new MoveTo(0, 0));
        this.vet = this.plane.getPlaneMovement().getPlaneCoordinates();

        pathTransition.setNode(plane.getPlane().getImagePlane());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setDuration(Duration.millis(PERIOD));

        // TEST adding static coordinates
        coordinatesTest = new Point2D[] {
                new Point2D(0, 0),
                new Point2D(196, 60),
                new Point2D(341, 138),
                new Point2D(601, 369),
                new Point2D(773, 119),
                new Point2D(910, 161),
                new Point2D(953, 123),
                new Point2D(1144, 50),
                new Point2D(1600, 1500),
        };

        // TEST insert the static coordinates into path
        for (int i = 1; i < coordinatesTest.length; i++) {
            path.getElements().add(new LineTo(coordinatesTest[i].getX(), coordinatesTest[i].getY()));
        }

    }

    @Override
    public void initGame() {
        spawn.startSpawn();
    }

    @Override
    public void mainLoop() throws IllegalArgumentException {
        long lastTime = System.currentTimeMillis();

        while (true) {
            long current = System.currentTimeMillis();
            int elapsed = (int) (current - lastTime);

            processInput();
            update(elapsed);
            render();

            try {
                waitForNextFrame(current);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lastTime = current;
        }
    }

    /**
     * Calculates how many milliseconds has to wait for next frame.
     * @param current
     * @throws InterruptedException
     * @throws IllegalArgumentException
     */
    protected void waitForNextFrame(final long current) throws InterruptedException, IllegalArgumentException {
        long dt = System.currentTimeMillis() - current;

        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void processInput() {

        if (start) {
            /*
             * for (int i = 0; i < vet.length; i++) { path.getElements().add(new
             * LineTo(vet[i].getX(), vet[i].getY())); }
             */
        }

    }

    @Override
    public void update(final int elapsed) {
        /*
         * if (start) {
         * 
         * if (cont < this.vet.length) {
         * plane.startTransiction(vet[cont].getX(),vet[cont].getY()); cont++; }
         * 
         * // Method Path with AnimationTransition //pathTransition.setPath(path);
         * //pathTransition.play();
         * 
         * // Method Path inserted manually with rotation double angleInclination =
         * plane.getAngleInclination(coordinatesTest[cont], coordinatesTest[cont + 1]);
         * 
         * plane.getPlane().getImagePlane().setRotate(angleInclination);
         * 
         * System.out.println("coordinatesTest[cont + 1]: " + coordinatesTest[cont
         * +1].getX() + " , " + coordinatesTest[cont + 1].getY());
         * plane.getPlane().getImagePlane().setLayoutX(coordinatesTest[cont +
         * 1].getX()); plane.getPlane().getImagePlane().setLayoutY(coordinatesTest[cont
         * + 1].getY());
         * 
         * System.out.println("layout X && Y: " +
         * plane.getPlane().getImagePlane().getLayoutX() + " , " +
         * plane.getPlane().getImagePlane().getLayoutY());
         * 
         * // si può anche inserire come Point2D final coordinatesTest[cont], sembra
         * //funzionare bene uguale plane.centerImagePositionInGame(plane.getPlane(),
         * //coordinatesTest[cont]);
         * 
         * System.out.println("after centerd X && Y" +
         * plane.getPlane().getImagePlane().getLayoutX() + " , " +
         * plane.getPlane().getImagePlane().getLayoutY());
         * 
         * plane.getPlane().getImagePlane().setLayoutX(plane.getPlaneHeadPosition(plane.
         * getPlane()).getX());
         * plane.getPlane().getImagePlane().setLayoutY(plane.getPlaneHeadPosition(plane.
         * getPlane()).getY());
         * 
         * cont++;
         * 
         * }
         */
            
    }

    @Override
    public void render() {
        
          if (start) { this.vet = this.plane.getPlaneMovement().getPlaneCoordinates();
          
          double x = getLineTo(cont).getX(); double y = getLineTo(cont).getY();
          
          start = false; Path path = new Path();
          
          if (this.vet[cont] != null && this.vet[cont + 1] != null) {
          path.getElements().add(new MoveTo(vet[cont].getX(), vet[cont].getY()));
          path.getElements().add(getLineTo(cont + 1));
          
          //System.out.println("Vet value " + vet[cont].getX() + " , " + vet[cont].getY()); 
          //System.out.println("getLineTo " + getLineTo(cont + 1));
          
          cont++; pathTransition.setPath(path); pathTransition.play();
          
          pathTransition.setOnFinished(finisch ->
          this.plane.getPlane().getImagePlane().setLayoutX(x));
          pathTransition.setOnFinished(finisch ->
          this.plane.getPlane().getImagePlane().setLayoutY(y));
          pathTransition.setOnFinished(finisch -> this.setStart(true)); } }
         
    }

    public LineTo getLineTo(final int cont) {
        System.out.println("cont " + cont); 
        System.out.println("Vet value " + vet[cont].getX() + " , " + vet[cont].getY()); 

        return new LineTo(this.vet[cont].getX(), this.vet[cont].getY());
    }

    public void setStart(final boolean start) {
        this.start = start;
    }


    public void setCoordinate(final Point2D[] vet) {
        /*
         * for (int i = 0; i < vet.length; i++) {
         * System.out.println("vet in GameEngine: " + vet[i]); }
         */

        this.vet = vet;
    }

}
