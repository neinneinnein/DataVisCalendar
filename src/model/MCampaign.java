package model;

import java.util.ArrayList;

import processing.core.PApplet;

public class MCampaign {

	public String campaignName;
	public String categoryName;

	public int campaignStartDay = 6666666;
	public int campaignEndDay = -6666666;

	public int colorCode = 255;

	public static PApplet pa;
	public static Boolean isActive = true;

	private ArrayList<MActivity> activities = new ArrayList<MActivity>();

	public MCampaign(String _campaignName) {
		// TODO Auto-generated constructor stub
		campaignName = _campaignName;


		
		
	}

	public void addActivity(int channelID, String _dateString) {
		// TODO Auto-generated method stub
		System.out.print(".ADD." + _dateString);

		MActivity newActivity = new MActivity(channelID, _dateString);
		activities.add(newActivity);

		if (newActivity.launchDayNumOfYear < campaignStartDay)
			campaignStartDay = newActivity.launchDayNumOfYear;

	}

	public void endActivity(int channelID, String content) {
		// TODO Auto-generated method stub
		// END THE LAST ACTIVITY, ITERATE FROM START

		System.out.print(".END." + content);
		Boolean found = false;
		for (int i = 0; i < activities.size(); i++) {
			if (activities.get(i).endDate == "" && !found) {
				found = true;
				activities.get(i).setEndDate(content);
				if (activities.get(i).endDayNumOfYear > campaignEndDay)
					campaignEndDay = activities.get(i).endDayNumOfYear;

			}
		}

	}

	public ArrayList<MActivity> getActivities() {
		// TODO Auto-generated method stub
		return activities;
	}

	private int getColorForCategory(String _name) {

		_name = _name.toLowerCase();

		if (_name.indexOf("x-cat") != -1)
			return pa.color(224, 167, 166, 255);
		if (_name.indexOf("football") != -1)
			return pa.color(163, 214, 226, 255);
		if (_name.indexOf("running") != -1)
			return pa.color(255,250,129, 255);
		if (_name.indexOf("basketball") != -1)
			return pa.color(197, 131, 73, 255);
		if (_name.indexOf("training") != -1)
			return pa.color(116, 97, 139, 255);
		if (_name.indexOf("outdoor") != -1)
			return pa.color(138, 137,131, 255);
		if (_name.indexOf("originals") != -1)
			return pa.color(21, 135, 201, 255);
		if (_name.indexOf("neo/style") != -1)
			return pa.color(40, 247, 45, 255);


		return pa.color(255, 0, 255, 255);
	}

	public void setCategory(String _categoryName) {
		// TODO Auto-generated method stub
		categoryName = _categoryName;

		colorCode = getColorForCategory(categoryName);

	}

}
