import model.Storage;
import processing.core.PApplet;
import processing.core.PVector;
import de.bezier.data.XlsReader;

public class CalendarMain extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] {"CalendarMain"} );
	}

	private float cy;
	private float cx;
	private Grid grid;

	public void setup() {
		
//		size(1400, 1000);
		size(1200, 600);

		smooth();

		cx = width / 2;
		cy = height / 2; // center

		//	xlsData = new XlsData(this);

		// println( xlsData.data.get(1).activities.get(7).startDay ) ;
		// println( "for T: " + dataSheet.data.get(1).activities.get(9).endDay )
		// ;
		
		DesignFactory.init(this);
		
		new Storage();
		Storage.pa = this;
		Storage.init();
		
		grid = new Grid(this);
		// println( dataSheet.data.get(4).activities.size() );

	}

	public void draw() { // wird in jedem Frame aufgerufen, brauchen wir nur für
							// zoom etc...

		// println("draw");

//		background(25,25,33);
		background(255);

		grid.drawGrid();
		

	//	println("displaywidth"+displayWidth);
		// println("width"+width);
	}
	
	public void keyPressed() {
		
		switch(key) {
			case '1':		grid.setSemester(2014, 1);	break;
			case '2':		grid.setSemester(2014, 2);	break;
			case '0':		grid.setFullYear(2014);		break;
			case 'c':		grid.uChannelOuterRad-=.05f;		break;
			case 'C':		grid.uChannelOuterRad+=.05f;		break;
			case 't':		grid.uTimeLineOuterRad-=.05f;		break;
			case 'T':		grid.uTimeLineOuterRad+=.05f;		break;

			// debug active channels
			case 'y':		Storage.toggleChannel(0);		break;
			case 'x':		Storage.toggleChannel(1);		break;
			case 'o':		Storage.setSingleCampaign(19);		break;
			case 'O':		Storage.setAllCampaignsActive();		break;
				
			}
		}
/*
	PVector coords(double angle, float radius) {

		double radian = (angle / 180f) * Math.PI;

		double x = cx + Math.cos(radian) * radius;
		double y = cy + Math.sin(radian) * radius;

		PVector c = new PVector();
		c.x = (float) x;
		c.y = (float) y;

		return new PVector();

	}
	*/

}
