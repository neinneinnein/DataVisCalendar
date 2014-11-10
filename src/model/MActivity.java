package model;

import java.util.Calendar;

public class MActivity {

	public int channelID;
	
	public String launchDate 	= "";
	public String endDate 		= "";
	
	public int launchDayNumOfYear = -99;
	public int endDayNumOfYear = -99;

	public MActivity(int _channelID, String _launchDate) {
		// TODO Auto-generated constructor stub
		channelID 	= _channelID;
		
		launchDayNumOfYear = getDayOfYear(_launchDate);
		endDayNumOfYear = getDayOfYear(_launchDate);

		System.out.println("##launchDayNumOfYear##: "+launchDayNumOfYear);
		
	}
	
	public void setEndDate(String _endDate) {
		// TODO Auto-generated method stub
		
		endDayNumOfYear = getDayOfYear(_endDate);

		System.out.println("##endDayNumOfYear##: "+launchDayNumOfYear);
	}

	private int getDayOfYear(String content) {
		
		String dateString = content.trim().replace("  ", " ");
		
		String[] split = dateString.split(" ");
				
		String monthName = split[0].trim();

		int dayOfMonth = 0;
		
		try {
			dayOfMonth = Integer.parseInt(split[1].trim());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.MONTH, getMonth(monthName));
		cal.set(Calendar.YEAR, 2014);

		return cal.get(Calendar.DAY_OF_YEAR);
		
	}

	private int getMonth(String monthName) {
		
		monthName = monthName.toLowerCase();
		
		if(monthName.indexOf("jan")!=-1)	return 0;
		if(monthName.indexOf("feb")!=-1)	return 1;
		if(monthName.indexOf("mar")!=-1)	return 2;
		if(monthName.indexOf("apr")!=-1)	return 3;
		if(monthName.indexOf("may")!=-1)	return 4;
		if(monthName.indexOf("mai")!=-1)	return 4;
		if(monthName.indexOf("jun")!=-1)	return 5;
		if(monthName.indexOf("jul")!=-1)	return 6;
		if(monthName.indexOf("aug")!=-1)	return 7;
		if(monthName.indexOf("sep")!=-1)	return 8;
		if(monthName.indexOf("oct")!=-1)	return 9;
		if(monthName.indexOf("okt")!=-1)	return 9;
		if(monthName.indexOf("nov")!=-1)	return 10;
		if(monthName.indexOf("dec")!=-1)	return 11;
		if(monthName.indexOf("dez")!=-1)	return 11;
		
		System.out.println("NO MONTH FOUND");
		return 0;
	}

}
