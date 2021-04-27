package model;

import javafx.geometry.Point2D;

// Probabilmente sarà una classe di utilità Static
public final class SpriteDimension {
	//Percent section
	public final static int PERCENT_MIN_RANGE = 5;
	public final static int PERCENT_MAX_RANGE = 95;
	public final static int PERCENT_VALUE = 5; // quantità percentuale per il ridimensionamento immagine
	public final static int BASE_PERCENT = 100; // base della divisione percentuale
	
	//Pane section:
	public final static double DIMENSION_MIN_RANGE = 60;
	public final static double DIMENSION_MAX_RANGE = 2560;
	
	//Math_Pow section:
	static final double BASE_POWER = 2;
	
	private SpriteDimension() { }
	
	/** Calcolo della percentuale di un numero, 
	 *  la percentuale DEVE ESSERE compresa tra 5 e 95
	 * @param number
	 * @param indexPersentage
	 * @return ritorna la percentuale calcolata, valore int per avere numeri semplici
	 */
	public static int persentageCalculation(final double number, final double indexPersentage) {
		int persentage;
		
		// la DIMENSIONE dello SCHERMO deve essere di questo Range Min = 60 && Max 2560
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
			return 0;
		}
			
		return persentage;
	}
	
	/** Calcolo del ridimensionamento in scala di un immagine con una certa percentuale
	 * 
	 * @param width larghezza dell'immagine da scalare
	 * @param height altezza dell'immagine da scalare
	 * @param indexPersentage percentuale ridimensionamento
	 * @return
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
	
	/** Esegue la proporzione per calcolare la dimensione in scala 
	 * dell'immagine trovata pane_width:pane_height = sprite_width:sprite_height
	 * 
	 * @param spriteDimensionScaled dimensione Sprite già scalata
	 * @param paneDimensionScaled dimensione Pane già scalato
	 * @param isSquare indica se si vuole un ridimensionamento per un oggetto quadrato o rettangolare
	 * @return restituisce la proporzione tra Pane e Sprite in potenze del 2
	 */
	public static Point2D proportionCalculation(final Point2D spriteDimensionScaled, final Point2D paneDimensionScaled, final boolean isSquare) {
		Point2D proportionResult;
		
		double x_unknownWidth;
		double x_unknownHeight;

		// controlla il valore minore tra Pane width e Pane height per eseguire poi la proporzione
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
	
	
	/** Calcolo per dimensioni sempre sulla scala delle potenze del 2 su base rettangolare
	 * 
	 * @param imageDimensionScaled dimensione Immagine già scalata
	 * @return restituisce il ridimensionamento su base rettangolare dell'immagine 
	 */
	public static Point2D spriteDimensionCalculationNotSquare(final Point2D imageDimensionScaled) {
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
	
	/** Calcolo per dimensioni sempre sulla scala delle potenze del 2 ma con dimensione Quadrata.
	 * 
	 * @param imageDimensionScaled dimensione Immagine già scalata
	 * @return restituisce il ridimensionamento su base quadrata dell'immagine 
	 */
	public static Point2D spriteDimensionCalculationSquare(final Point2D imageDimensionScaled) {
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
		
		// questa verifica serve per ottenere la dimensione minima in scala
		if (result_width <= result_height) {
			result_height = result_width;
		} else if (result_height < result_width) {
			result_width = result_height;
		}
		
		dimensionResult = new Point2D(result_width, result_height);
		
		return dimensionResult;
	}
	
	/** Calcola l'esponente della potenza di un dato numero (2049 = 2^12)
	 * 
	 * @param numero potenza di cui bisogna trovare l'esponente
	 * @return restituisce l'esponente del numero passato
	 */
	public static int exponentCalcultation(final int numero) {
		int risultato = 0;
		int esponente = 0;
		
		while(risultato < numero) {
			esponente++;
			risultato = (int) Math.pow(BASE_POWER, esponente);
		}
		
		if(risultato == numero) { 
			return esponente; 
		}
		else { esponente--; }
		
		return esponente;
	}
	
}
