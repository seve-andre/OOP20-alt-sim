package model;

import application.MainPlaneView;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

/** 
 * Static utility class to calculate the scaling of an image that we want to be scaled,
 * in proportion to the size of the screen on which it will be positioned. 
 * 
 *  Es:
 *  
 *  Screen [1024 x 768]
 *  Sprite [424 x 393] raw dimension of the image loaded
 *  
 *  Image must be the 15% of Screen dimension
 *  
 *  dimensioneSprite after the count = [64 x 64]
 * 
 * @author daniel_rodilosso
 *
 */
public final class SpriteDimension {
	//Percent section:
	/** Min persentage range for calculation */
	public final static int PERCENT_MIN_RANGE = 5;
	/** Max persentage range for calculation */
	public final static int PERCENT_MAX_RANGE = 95; 
	/** percentage chosen for rendering */
	public final static int PERCENT_VALUE = 15;
	/** base division */
	public final static int BASE_PERCENT = 100;
	
	//Pane section:
	/** Min range of Screen dimension */
	public final static double DIMENSION_MIN_RANGE = 60;
	/** Max range of Screen dimension  */
	public final static double DIMENSION_MAX_RANGE = 2560; 
	
	//Sprite section:
	/** Min range of Sprite dimension */
	private final static double SPRITE_MIN_RANGE = 16;
	/** Max range of Sprite dimension */
	private final static double SPRITE_MAX_RANGE = 256; 
	
	//Math_Pow section:
	/** power's exponent, chose */
	static final double BASE_POWER = 2; 
	
	private SpriteDimension() { }
	
	
	/** Calculation of a number's persentage, 
	 *  the percentage value must be between (5 and 95)
	 * @param number target for persentage calculation
	 * @param indexPersentage value to calculation
	 * @return result of the calculating, in (int) format for simplicity
	 */
	public static int persentageCalculation(final double number, final double indexPersentage) {
		int persentage;
		
		// check of the Screen Dimension Range
		if(number >= DIMENSION_MIN_RANGE && number <= DIMENSION_MAX_RANGE) {
			try{
				if (indexPersentage >= PERCENT_MIN_RANGE && indexPersentage <= PERCENT_MAX_RANGE) {
					persentage = (int) ((number * indexPersentage) / BASE_PERCENT);
				}else {
					persentage = 0;
				}
			}catch(ArithmeticException artm) {
				System.out.println("ARTM Exception: " + artm);
				return 0;
			}
		}else{
			System.out.println("Dimension in PersentageCalculation Bounded: ");
			return 0;
		}
			
		return persentage;
	}
	
	
	/** Calculation of Image scaling with a some persentage 
	 * 
	 * @param width image width to scaling
	 * @param height image height to scaling
	 * @param indexPersentage value of rendering persentage
	 * @return returned the calculation of scaled images.
	 */
	public static Point2D getScale(final double width, final double height, final int indexPersentage) {	
		Point2D scalePoint; // x = widht || y = height
		
		double widthScale = 0;
		double heightScale = 0;
		
		try {
			widthScale = (double) persentageCalculation(width, indexPersentage);
			heightScale = (double) persentageCalculation(height, indexPersentage);
	
			scalePoint = new Point2D(widthScale, heightScale);
		} catch(ArithmeticException artm) {
			System.out.println("ARTM Exception Persentage: " + artm);
			return null;
		}
		
		return scalePoint;
	}
	
	
	/** Execute the proportion to find the dimension value in scale
	 *  proportion formula: [ pane_width:pane_height = sprite_width:sprite_height ]
	 * 
	 * @param spriteDimensionScaled Sprite dimension already scaled
	 * @param paneDimensionScaled Pane dimension already scaled
	 * @param isSquare define the kind of dimension scale [16x16] = Square
	 * 	[128x64] Rectangle
	 * @return return the proportion between Pane and Sprite in base_power chose 
	 */
	public static Point2D proportionCalculation(final Point2D spriteDimensionScaled, final Point2D paneDimensionScaled, final boolean isSquare) {
		Point2D proportionResult;
		
		double x_unknownWidth;
		double x_unknownHeight;

		// Check the Pane Range
		if(paneDimensionScaled.getX() <= paneDimensionScaled.getY()){
			x_unknownHeight = (spriteDimensionScaled.getY() * paneDimensionScaled.getX()) / spriteDimensionScaled.getX();
			x_unknownWidth = paneDimensionScaled.getX();
			
		}else {
			x_unknownWidth = (spriteDimensionScaled.getX() * paneDimensionScaled.getY() ) / spriteDimensionScaled.getY();
			x_unknownHeight = paneDimensionScaled.getY();

			proportionResult = new Point2D(x_unknownWidth, x_unknownHeight);
		}
		
		proportionResult = new Point2D(x_unknownWidth, x_unknownHeight);
		
		if(isSquare == true) {
			proportionResult = spriteDimensionCalculationSquare(proportionResult);
		}else {
			proportionResult = spriteDimensionCalculationNotSquare(proportionResult);
		}
		
		return proportionResult;
	}
	 
	/** Calculate the unknowVariable in base 2 Rectangle(128x64)
	 * 
	 * @param imageDimensionScaled Image dimension already scaled
	 * @return return the the Image points Scaled in Rectangle format
	 */
	private static Point2D spriteDimensionCalculationNotSquare(final Point2D imageDimensionScaled) {
		Point2D dimensionResult;
		
		int result_width = 0;
		int result_height = 0;
		int esponente = 0;
				
		try {
			esponente = exponentCalcultation((int) imageDimensionScaled.getX());
			result_width = (int) Math.pow(BASE_POWER, esponente);
			
			esponente = exponentCalcultation((int) imageDimensionScaled.getY());
			result_height = (int) Math.pow(BASE_POWER, esponente);
			
			dimensionResult = new Point2D(result_width, result_height);
		} catch(ArithmeticException artm) {
			System.out.println("ARTM Exception Persentage: " + artm);
			return null;
		}
		
		return dimensionResult;
	}
	
	/** Calculate the unknowVariable in base 2 Square(64x64)
	 * 
	 * @param imageDimensionScaled Image dimension already scaled
	 * @return return the the Image points Scaled in Square format
	 */
	private static Point2D spriteDimensionCalculationSquare(final Point2D imageDimensionScaled) {
		Point2D dimensionResult;
		
		int result_width = 0;
		int result_height = 0;
		int esponente = 0;
		
		try {
			esponente = exponentCalcultation((int) imageDimensionScaled.getX());
			result_width = (int) Math.pow(BASE_POWER, esponente);
			
			esponente = exponentCalcultation((int) imageDimensionScaled.getY());
			result_height = (int) Math.pow(BASE_POWER, esponente);
		} catch (ArithmeticException artm) {
			System.out.println("ARTM Exception Persentage: " + artm);
			return null;
		}
		
		// Control the low value to choose
		if (result_width <= result_height) {
			result_height = result_width;
		} else if (result_height < result_width) {
			result_width = result_height;
		}
		
		dimensionResult = new Point2D(result_width, result_height);
		
		return dimensionResult;
	}
	
	/** Calling the several methods of SpriteDimension for putting together all the functions
	 * 
	 * @param imageSprite Sprite selected for the calculation in base 2
	 * @return return the Image rendered to the View
	 */
	public static ImageView renderingImageSprite(final ImageView imageSprite) {
		ImageView imageRendered = imageSprite;
		Point2D imageSpriteCopy = new Point2D(imageSprite.getBoundsInLocal().getWidth(), imageSprite.getBoundsInLocal().getHeight());
		Point2D paneScaled = new Point2D(MainPlaneView.SCREEN_WIDTH, MainPlaneView.SCREEN_HEIGHT);
		
		imageSpriteCopy = SpriteDimension.getScale(imageSpriteCopy.getX(), imageSpriteCopy.getY(), SpriteDimension.PERCENT_VALUE);
		paneScaled = SpriteDimension.getScale(paneScaled.getX(), paneScaled.getY(),  SpriteDimension.PERCENT_VALUE);

		// Calculating Sprite dimension in base 2 Square:
		imageSpriteCopy = SpriteDimension.proportionCalculation(imageSpriteCopy, paneScaled, true);
		
		imageRendered.setFitWidth(imageSpriteCopy.getX());
		imageRendered.setFitHeight(imageSpriteCopy.getY());
		
		return imageRendered;
	}
	
	/** Calculating the exponent of a base power (2049 = 2^12)
	 * 
	 * @param numer of we want find the exponent
	 * @return return the exponent found
	 */
	public static int exponentCalcultation(final int numero) {
		int risultato = 0;
		int esponente = 0;
		
		while(risultato < numero) {
			esponente++;
			risultato = (int) Math.pow(BASE_POWER, esponente);
			
		}
		
		if(risultato == numero) { 
			if(risultato < SPRITE_MIN_RANGE || risultato > SPRITE_MAX_RANGE){
				System.out.println("Sprite Range too hight or too low");
				return 0;
			}else {	
				return esponente; 
			}
		}
		else { 
			if(risultato < SPRITE_MIN_RANGE || risultato > SPRITE_MAX_RANGE){
				System.out.println("Sprite Range too hight or too low");
				return 0;
			}else {
				esponente--; 
			}
		}
		
		return esponente;
	}
	
}
