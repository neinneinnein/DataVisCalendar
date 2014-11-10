import processing.core.PApplet;


public class DesignFactory {
	
	

	public static int STROKE_COLOR_DARK = 0;
	
	private static PApplet pa;

	public static void init(PApplet _pa) {
		// TODO Auto-generated method stub
		pa = _pa;
		
		initWhiteDesign();
		
		
	}

	private static void initWhiteDesign() {
		// TODO Auto-generated method stub
		STROKE_COLOR_DARK = pa.color(20,20,20);
		
	}

}
