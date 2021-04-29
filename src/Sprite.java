package model;

import application.MainPlaneView;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Descrive l'entità Sprite, ossia la rappresentazione di un oggetto mobile nel gioco (Plane)
 * in questo caso.
 * 
 * 	Describes the Sprite entity, rather than the rappresentation of a dinamic object in game (Plane)
 * 
 *  it'll have a background image.
 *  coordinates x, y for the position in the Map
 *  widht and height for the view rendering
 *  
 * 
*/
public class Sprite {
	public final static String URL_SPRITE = "file:///Users/daniel/eclipse-workspace/PlaneModel/src/application.resources/Plane.png";

	private Image imageSpriteToLoad;
	private ImageView imageSprite;
	private Point2D point; // rappresent the Point = (x,y) in the Map
	
	public Sprite() {
		this(new Point2D(0, 0));
	}
	
	public Sprite(final Point2D point) {
		this.imageSprite = new ImageView(new Image(Sprite.URL_SPRITE));
		this.point = point;
		
		this.imageSprite = SpriteDimension.renderingImageSprite(imageSprite);
	}
	
	public Sprite(final Image image_spriteToLoad, final Point2D point) {
		this.imageSpriteToLoad = image_spriteToLoad;
		this.imageSprite = new ImageView(this.imageSpriteToLoad);
		this.point = point;
		
		this.imageSprite = SpriteDimension.renderingImageSprite(imageSprite);
	}
	
	public ImageView getImagePlane() { 
		return this.imageSprite;
	}
	
	public Point2D getPoint() {
		return this.point;
	}
	
	public void setPoint2D(final Point2D point) {
		this.point = point;
	}
	
}
