import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import model.MActivity;
import model.MCampaign;
import model.MChannel;
import model.Storage;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class Grid {

	private float cx;
	private float cy;
	private float gridAngleStep;
	private float radius;
	private PApplet p;
	private PFont font;

	// /------Styles

	// strokeWeight
	public float LINE_WIDTH_THIN = 0.5f;
	public float LINE_WIDTH_MEDIUM = 2f;
	public float LINE_WIDTH_BOLD = 3f;

	// Stroke colors
	public float STORKE_COLOR_DARK = 20;
	public float STROKE_COLOR_BRIGHT = 230;

	public float STROKE_ALPHA1 = 30;

	// textSize
	public float typo1 = 8;
	public float typo2 = 12;
	public float typo3 = 15;

	// UNIT und so unifyed
	public int gesamt;
	public float UNIT;
	public float UNIT_rad;
	public float channelInnerRad;
	public float channelOuterRad;
	public float timeLineInnerRad;
	public float timeLineOuterRad;
	public float timeLineWidth;

	public float channelInnerDiameter;
	public float channelOuterDiameter;
	public float timeLineInnerDiameter;
	public float timeLineOuterDieameter;

	public float _1PrioCampaignRad;
	public float _2PrioCampaignRad;
	public float _3PrioCampaignRad;
	private int gridDayStart;
	private int gridDayEnd;
	private int gridNumDays;
	private int gridYear;
	private Calendar cal;
	private int currentWeek;
	private int gridJulianDayStart;
	private int gridJulianDayEnd;
	private float channelRadSteps;
	
	//
	public float uChannelOuterRad = 0.8f;
	public float uTimeLineOuterRad = .85f;
	private float timeLineStrokeWidth = 0;
	private float timeLineArcRad;

	public Grid( PApplet p) {

		//this.xlsData = xlsData;
		this.p = p;

		radius = p.width / 2 - 50;

		gridYear = 2014;

		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, gridYear);

		gridJulianDayStart 	= Utils.JulianDay(2012,8,1);
		gridJulianDayEnd	= Utils.JulianDay(2014,8,1);
		
//		p.println("gridJulianDay: "+gridJulianDayStart+" / "+gridJulianDayEnd);
//		p.println("fromJulianDay: "+Utils.fromJulian(gridJulianDayStart)+" / "+Utils.fromJulian(gridJulianDayEnd));
		
		setSemester(2014,1);

		font = p.loadFont("AdihausDIN-Cn-12.vlw");

		// drawGrid();
	}

	public void setFullYear(int year) {

		// semesterNum: 1 / 2

		cal.set(Calendar.YEAR, year);

		gridDayStart 	= 1; // day of the year 1..365
		gridDayEnd 		= cal.getActualMaximum(Calendar.DAY_OF_YEAR); // day of the
																	// year
																	// 1..365
		p.println("SET FULL YEAR");

		gridNumDays = gridDayEnd - gridDayStart;
		gridAngleStep = 180f / gridNumDays;

	}

	public void setSemester(int year, int semesterNum) {

		// semesterNum: 1 / 2

		cal.set(Calendar.YEAR, year);

		// TODO: thomas, do it nice please
		if (semesterNum == 1) {
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			int lastSemesterDay = cal.get(Calendar.DAY_OF_YEAR)-1;
			gridDayStart = 1; // day of the year 1..365
			gridDayEnd = lastSemesterDay; // day of the year 1..365
			p.println("SET FIRST SEMESTER");
		} else {
			
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			gridDayStart = cal.get(Calendar.DAY_OF_YEAR); // day of the year 1..365
			gridDayEnd = cal.getActualMaximum(Calendar.DAY_OF_YEAR); // day of the year 1..365
			p.println("SET SECOND SEMESTER");
		}

		gridNumDays = gridDayEnd - gridDayStart;
		gridAngleStep = 180f / gridNumDays;

	}

	public void setQuartal(int year, int quartalNum) {

		// TODO: thomas, do it nice please
		// example for first quarter
		gridDayStart = 1; // day of the year 1..365
		gridDayEnd = 90; // day of the year 1..365
		gridNumDays = gridDayEnd - gridDayStart;
		gridAngleStep = 180f / gridNumDays;

	}

	public void drawGrid() {
		
		
		// TEST TEST DEBUG
		
		/*
		int mx = p.mouseX - p.width / 2;
		int my = p.mouseY - p.height/ 2;
		
		gridDayStart = mx;
		gridDayEnd	= my;
		
		if(gridDayEnd<=gridDayStart)	gridDayEnd = gridDayStart+7;
		
		gridNumDays = gridDayEnd - gridDayStart;
		gridAngleStep = 180f / gridNumDays;
		
		*/
		
		// /TEST TEST DEBUG
		
		
		
		//********************************************************************************************
		
		
		gesamt = p.width;
		UNIT = gesamt - 150;
		UNIT_rad = UNIT / 2;
		
		
		
		channelInnerRad = 0.1f * UNIT_rad;
		channelOuterRad = uChannelOuterRad * UNIT_rad;
		
		timeLineInnerRad = channelOuterRad;
		timeLineOuterRad = uTimeLineOuterRad * UNIT_rad; // - radSteps/2 - timelineStrokeWidth =correctArcPosition
		
		timeLineArcRad = (timeLineInnerRad +(timeLineOuterRad-timeLineInnerRad)/2)*2;
		timeLineStrokeWidth = timeLineOuterRad - timeLineInnerRad;

		channelInnerDiameter = channelInnerRad * 2; 
													
		channelOuterDiameter = channelOuterRad * 2;
		timeLineInnerDiameter = timeLineInnerRad * 2;
		timeLineOuterDieameter = timeLineOuterRad * 2;

		_1PrioCampaignRad = 0.95f * UNIT_rad;
		_2PrioCampaignRad = 1 * UNIT_rad;
		_3PrioCampaignRad = 1f * UNIT_rad;
		
		
		//********************************************************************************************
		cx = (float) p.width / 2; // center of canvas
		cy = (float) p.height - 200;

		 p.fill(0);
		 p.ellipse(cx, cy, 10, 10);
		 p.ellipse(cx + UNIT_rad, cy, 10, 10);
		 p.fill(255, 0, 0);
		 p.ellipse(cx + channelInnerRad, cy, 5, 5);
		 p.fill(0, 255, 0);
		 p.ellipse(cx + channelOuterRad, cy, 5, 5);
		 p.fill(0);
		 p.ellipse(cx + timeLineOuterRad, cy+10, 5, 5);
		 p.ellipse(cx + timeLineArcRad, cy+10, 5, 5);

		 p.ellipse(cx+timeLineInnerRad, cy+10, 5, 5);
		 p.ellipse(cx + _1PrioCampaignRad, cy, 10, 10);
		 p.ellipse(cx + _2PrioCampaignRad, cy, 10, 10);
		 p.ellipse(cx + _3PrioCampaignRad, cy, 10, 10);

		ArrayList<MChannel> channels = Storage.GetAllChannels();

		 channelRadSteps = (channelOuterDiameter - channelInnerDiameter)
				/ channels.size();

		p.noFill();

		// ---------------------------------------------------------------------------
		// -----------------------------------------------------------------CHANNEL
		// BG

		for (int i = 0; i < channels.size(); i++) {

			// grab channel from list
			MChannel channel = channels.get(i);
			// p.println(channel.channelName+" / "+channel.colorCode);

			float rad = channelInnerDiameter+channelRadSteps/2 + i * (channelRadSteps);
			
			channel.gridRadius = rad;

			// DRAW FAT CHANNEL BG
			p.stroke(channel.colorCode);

			p.strokeCap(p.SQUARE);

			p.strokeWeight(channelRadSteps / 2);
			p.arc(cx, cy, rad, rad, p.radians(getCircleDegreeByDay(gridDayStart)),
					p.radians(getCircleDegreeByDay(gridDayEnd)));

			// ---------------------------------------------------------------------------
			// DRAW THIN CHANNEL OUTLINE

			p.stroke(STORKE_COLOR_DARK, 100);
			p.strokeWeight(LINE_WIDTH_THIN);

			p.arc(cx, cy, rad + channelRadSteps / 2, rad + channelRadSteps / 2,
					p.radians(getCircleDegreeByDay(gridDayStart)), p.radians(getCircleDegreeByDay(gridDayEnd)));
			// --------------------------------------------------------------------------
			// DRAW VERTCAL LINES + CHANNEL TEXT

			PVector pos1 = getCirclePos(0, rad / 2 + channelRadSteps / 4);
			PVector pos2 = pos1;
			pos2.y += 20;

			p.strokeWeight(LINE_WIDTH_THIN);
			p.stroke(STORKE_COLOR_DARK, 100);

			p.line(pos1.x, pos1.y + 10, pos2.x, pos2.y - 20); // ---------------line
																// vertical1
			p.line(pos1.x, pos1.y + 10, pos2.x - 100, pos2.y + 100);
			p.line(pos2.x - 100, pos2.y + 100, pos2.x - 100, pos2.y + 400);

			p.pushMatrix();

			// p.translate(p.width/2, p.height/2);
			// p.translate(i*radSteps/2-20,pos2.y-p.height/2+80);

			p.translate(pos2.x - channelRadSteps, pos2.y + 80);
			p.rotate(-p.PI / 4);
			p.textFont(font);
			p.text(channel.channelName+" / "+channel.subChannelName, 0, 0);

			p.popMatrix();

		}

		// *************************LINES************Days***********weeks
		// ************months*************************

		p.noFill();
		p.strokeCap(p.SQUARE);
		p.strokeWeight(LINE_WIDTH_THIN);
		p.stroke(STORKE_COLOR_DARK, 40);

		for (int i = gridDayStart; i < gridDayEnd; i++) { // ------------------daylines

			PVector pos = getCirclePosByDayOfYear(i, channelOuterRad, false );
			PVector posDays = getCirclePosByDayOfYear(i, channelInnerRad, false);
			p.line(posDays.x, posDays.y, pos.x, pos.y);
		}

		

		currentWeek = -99;

		for (int i = gridDayStart; i < gridDayEnd; i++) {
			
			setCalDayOfYear(i);
			
			int dayNum 		= cal.get(Calendar.DAY_OF_MONTH);
			int monthNum 	= cal.get(Calendar.MONTH);
			int weekNum 	= cal.get(Calendar.WEEK_OF_YEAR);
			
			// DRAW WEEK LINE
			if(weekNum!=currentWeek) {
				
				p.strokeWeight(LINE_WIDTH_THIN);
				p.stroke(STORKE_COLOR_DARK, 80);
				
				PVector pos = getCirclePosByDayOfYear(i,
						channelOuterRad + 20, false);
				PVector posWeeks = getCirclePosByDayOfYear(i,
						channelInnerRad, false );
				p.line(pos.x, pos.y, posWeeks.x, posWeeks.y);
				
				currentWeek = weekNum;
				
			}
			
			// DRAW MONTH LINE
			if (dayNum == 1) {

				p.strokeWeight(LINE_WIDTH_MEDIUM);
				p.stroke(STROKE_COLOR_BRIGHT, 200);
				
				PVector pos = getCirclePosByDayOfYear(i,
						channelOuterRad + 20, false);
				PVector posWeeks = getCirclePosByDayOfYear(i,
						channelInnerRad, false );
				p.line(pos.x, pos.y, posWeeks.x, posWeeks.y);
			}

			
		}

		
		drawCampaign();
		drawTimeLine();
		
	} //  -----------------------------------------------------------------------ende draw ---

	private void setCalDayOfYear(int dayOfYear) {
		// TODO Auto-generated method stub
		cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		return;
		
		/*
		if(dayOfYear>0)	{
			// everything is kool
			cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		} else if(dayOfYear<0) {
			// this is not kool
			cal.set(Calendar.DAY_OF_YEAR, 1);
			
			for(int i=dayOfYear; i<1; i++) {
				cal.add(Calendar.DATE, -1);
			}
		}
		*/
	}

	private void drawCampaign() { // ------------------------------------------campaign
									//------------------------------------------ draw
		ArrayList<MChannel> 	channels = 	Storage.GetAllChannels();
		ArrayList<MCampaign> campaignsList = Storage.GetAllCampaigns();
		p.noFill();
		p.stroke(DesignFactory.STROKE_COLOR_DARK);
		p.strokeWeight(LINE_WIDTH_BOLD);

		for (int i = 0; i < campaignsList.size(); i++) {
			MCampaign campaign = campaignsList.get(i);
//			Mcampaign campaign = campaignName.get(i);
			
			if(!campaign.isActive)	continue;
			
			int campaingDayStart = campaign.campaignStartDay;
			int campaingDayEnd = campaign.campaignEndDay;

			int campaignColor = campaign.colorCode;	
			p.stroke(campaignColor);
			
			float startingCampaignDegree = getCircleDegreeByDay(campaingDayStart);
			float endingCampaignDegree = getCircleDegreeByDay(campaingDayEnd);

			
			float radSteps = (_3PrioCampaignRad*2-_1PrioCampaignRad*2) / campaignsList.size();
			float campaignPriorityRad = _1PrioCampaignRad*2 + i * radSteps*5/3 ;  // set campaign spacing

			
			//------------------------------------------ draw campaigns in Priority radius 
			p.strokeWeight(radSteps*1.5f);
			p.arc(cx, cy, campaignPriorityRad,campaignPriorityRad,	p.radians(startingCampaignDegree),p.radians(endingCampaignDegree));
			
			
			p.pushMatrix();
			p.translate(cx, cy);
			p.translate(campaignPriorityRad,campaignPriorityRad);
			p.text(("campaignCategory"+i), 0,0);
			p.popMatrix();

			
			
			// ------------------------------------------------------------------activities
			
			ArrayList<MActivity> activityList = campaign.getActivities();
			p.noFill();

			p.strokeWeight(LINE_WIDTH_THIN);
			
			
			for (int j = 0; j < activityList.size(); j++) {
			
				MActivity activity = activityList.get(j);
				
				int channelID = activity.channelID;
				
				if(!Storage.channelIsActive(channelID))	continue;
				

				float startingActivityDegree 	= getCircleDegreeByDay(activity.launchDayNumOfYear);   // convert launchday to degree
				float endingActivityDegree 		= getCircleDegreeByDay(activity.endDayNumOfYear);
				
				float startingActicityRadian 	= p.radians(startingActivityDegree);		// convert launchDaydegree to radian 
				float endingActivityRadian 		= p.radians(endingActivityDegree);
				
				boolean isSingleDay = false;
				if(activity.launchDayNumOfYear==activity.endDayNumOfYear) {
					isSingleDay = true;
				}
				
				// get channel radius,
				// iterate through all channels
				p.strokeWeight(LINE_WIDTH_BOLD);
				float channelRadius = 0;
				for(int c=0; c<channels.size(); c++) {
					if(channels.get(c).id==channelID) {
						channelRadius = channels.get(c).gridRadius;   // get each channelRadius and set channel activity spacing
						if(isSingleDay) {
							// DRAW ONE SHOT, HELLO THOMAS
							PVector pos = getCirclePosByDayOfYear(activity.launchDayNumOfYear, channelRadius/2, true);
							p.ellipse(pos.x, pos.y, 6, 6);
//							p.fill(campaignColor);
						} else {
							p.noFill();
							p.arc(cx, cy, channelRadius, channelRadius, startingActicityRadian, endingActivityRadian);	// draw channel activity 
						}
						}
						
						
				}
				
				//------------------------------------------ draw activities in channels 

				// define connections between campaigns and activities in channels
				PVector channelActivityStartPos 	= getCirclePos(startingActivityDegree, channelRadius/2);  // save activity channelRad pos
				PVector channelActivityEndPos		= getCirclePos(endingActivityDegree, channelRadius/2);
				
				PVector campaignActivityStartPos 	=getCirclePos(startingActivityDegree, campaignPriorityRad/2); // save activity priority rad
				PVector campaignActivityEndPos 		=getCirclePos(endingActivityDegree, campaignPriorityRad/2); // campaignRad
				
//				
//				PVector campaignForEachDayRect 		= getCirclePos(startingActivityDegree, timeLineInnerRad+j*6 );  // save activity launch timline rad
//				
//				p.fill(campaignColor);
//				p.rect(campaignForEachDayRect.x, campaignForEachDayRect.y, 3, 3);
//////				
////				

				
				// ------------------------------------------------------------------ connection Activity - Campaign --------
				if(isSingleDay) {
					// do nothing;
				} else {
					
					p.strokeWeight(LINE_WIDTH_THIN);
					p.noFill();
					p.line(channelActivityStartPos.x, channelActivityStartPos.y ,campaignActivityStartPos.x,campaignActivityStartPos.y);
					p.stroke(campaignColor,125);
					p.line(channelActivityEndPos.x, channelActivityEndPos.y, campaignActivityEndPos.x, campaignActivityEndPos.y);
				
				}
//				p.ellipse(campaignActivityStartPos.x, campaignActivityStartPos.y, 10, 10);
				
				//TODO check performance - ellipse machts derbe lah
				
			}	// ende j activity list size 
			
			
//			for (int j = 0; j < activityList.size(); j++) {
//				
//				MActivity activity = activityList.get(j);
//				
//				int channelID = activity.channelID;
//				
//				
//				
//				
//				
//			}
		} // ende i campaign list 
	}
	
	private void drawTimeLine(){
		// ----------------------------------------------------------------------------timeline
				// ----------------------------------------------------------------------------background
			

				p.noFill();
				p.strokeCap(p.SQUARE);
				p.strokeWeight(timeLineStrokeWidth);
				p.stroke(123, 132, 132, 123);

				p.arc(cx, cy, timeLineArcRad,
						timeLineArcRad ,
						p.radians(getCircleDegreeByDay(gridDayStart)), p.radians(getCircleDegreeByDay(gridDayEnd)));


				// ----------------------------------------------------------------------------timeline TYPO
				// ----------------------------------------------------------------------------Months & WEEKS
				p.strokeWeight(LINE_WIDTH_THIN);
				p.stroke(0);
				p.fill(20);
				
				currentWeek = -99;

				for (int i = gridDayStart; i < gridDayEnd; i++) {

					// i is a day of the year 1..365
					
					setCalDayOfYear(i);
					
					int dayNum 		= cal.get(Calendar.DAY_OF_MONTH);
					int monthNum 	= cal.get(Calendar.MONTH);
					int weekNum 	= cal.get(Calendar.WEEK_OF_YEAR);
					
					// CHECK WEEKS
					if(weekNum!=currentWeek) {
						
						PVector pos = getCirclePosByDayOfYear(i,	timeLineArcRad/2, true );
						float angle = getCircleDegreeByDay(i);
						p.pushMatrix();
						p.translate(pos.x, pos.y);
						p.rotate(p.radians(angle) + p.radians(90));
						p.text(weekNum, 0, 0);
						p.popMatrix();
						
						currentWeek = weekNum;
						
					}
					
					//TODO check amount of days for each month
					if (dayNum == 1) {

						PVector pos = getCirclePosByDayOfYear(i,	timeLineArcRad/2 +timeLineStrokeWidth/4, true);
						float angle = getCircleDegreeByDay(i);
						
						p.pushMatrix();
						p.translate(pos.x, pos.y);

						String monthName = (new DateFormatSymbols().getMonths()[monthNum]);
						p.textSize(typo2);
						p.rotate(p.radians(angle) + p.radians(90));
						p.text(monthName, 0, 0);
						p.popMatrix();
					}

				}	// end i drawing dayNum, monthNum, weekNum 
				
			

				// p.println("FROM HELL: "+(p.millis()-startmillis));

				// ---------------------------------------------------------------------------timeline TYPO
				// -----------------------------------------------------------------------days
				// as text
				p.textSize(typo2);
				float angle = 180;
				for (int i = gridDayStart; i < gridDayEnd; i += 4) {

					setCalDayOfYear(i);

					angle = getCircleDegreeByDay(i);

					PVector posC = (getCirclePosByDayOfYear(i, timeLineArcRad/2 - timeLineStrokeWidth / 4, true));
					p.pushMatrix();
					p.translate(posC.x, posC.y);
					p.rotate(p.radians(angle) + p.radians(90));
					p.text(cal.get(Calendar.DAY_OF_MONTH), 0, 0);
					p.popMatrix();

					if (cal.get(Calendar.DAY_OF_MONTH) == 1) {

						// START OF DA MONTH!
					}
				}
		
	}

	private PVector getCirclePos(float angle, float radius) {

		double radian = (angle / 180) * (double) Math.PI;

		double x = cx + Math.cos(radian) * radius;
		double y = cy + Math.sin(radian) * radius;

		PVector c = new PVector();
		c.x = (float) x;
		c.y = (float) y;

		return c;
	}

	// ---------------------------------------------------------------------------DAY
	// & DATE PROJECTIONS

	PVector getCirclePosByDate(int _day, int _month, float radius, Boolean centered) {

		cal.set(Calendar.DAY_OF_MONTH, _day);
		cal.set(Calendar.MONTH, _month - 1);

		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		return getCirclePosByDayOfYear(dayOfYear, radius, centered);
	}

	float getCircleDegreeByDay(int _dayNum) {

		int projectedDayNum = _dayNum - gridDayStart;
//		projectedDayNum+=p.mouseX - p.width / 2;
		//
		float angle = (projectedDayNum) * gridAngleStep + 180;

		if (projectedDayNum < 0) 				angle = 180;
		if (projectedDayNum > gridNumDays) 		angle = 360;
		

		return angle;

	}

	PVector getCirclePosByDayOfYear(int _dayNum, float radius, Boolean centered) {

		// PROJECTION OF A SINGLE DAY IN A YEAR, e.g 10.Mai -> Tag 151

		int projectedDayNum = _dayNum - gridDayStart;

//		projectedDayNum+=p.mouseX - p.width / 2;
		// if (projectedDayNum < 0)
		// p.println("WARNING, DAYNUM < 0");

		//

		float angle = (projectedDayNum) * gridAngleStep + 180 + (centered?gridAngleStep/2:0);
		double radian = (angle / 180) * (double) Math.PI;

		double x = cx + Math.cos(radian) * radius;
		double y = cy + Math.sin(radian) * radius;

		if (projectedDayNum < 0) {
			x = cx - radius;
			y = cy - projectedDayNum * gridAngleStep * UNIT * .005f;
		}
		
		if (projectedDayNum >gridNumDays) {
			x = cx + radius;
			y = cy + projectedDayNum * gridAngleStep * UNIT * .005f;
		}

		PVector c = new PVector((float) x, (float) y);

		return c;

	}

}
