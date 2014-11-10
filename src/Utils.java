import java.util.Date;

public class Utils {

	public static int JGREG = 15 + 31 * (10 + 12 * 1582);
	public static double HALFSECOND = 0.5;

	public static int JulianDay(int year, int month, int day) {
		int a = (14 - month) / 12;
		int y = year + 4800 - a;
		int m = month + 12 * a - 3;
		int jdn = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400
				- 32045;
		return jdn;
	}

	public static Date fromJulian(double injulian) {
		int jalpha, ja, jb, jc, jd, je, year, month, day;
		double julian = injulian + HALFSECOND / 86400.0;
		ja = (int) injulian;
		if (ja >= JGREG) {
			jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
			ja = ja + 1 + jalpha - jalpha / 4;
		}

		jb = ja + 1524;
		jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
		jd = 365 * jc + jc / 4;
		je = (int) ((jb - jd) / 30.6001);
		day = jb - jd - (int) (30.6001 * je);
		month = je - 1;
		if (month > 12)
			month = month - 12;
		year = jc - 4715;
		if (month > 2)
			year--;
		if (year <= 0)
			year--;

		return new Date(year, month, day);
		
	}
}
