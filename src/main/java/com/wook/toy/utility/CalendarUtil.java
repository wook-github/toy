/**
 *
 */
package com.wook.toy.utility;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author dykim
 *
 */


public class CalendarUtil {
	//요일(영어) 배열
	public static final String[] DAY_OF_WEEK = {
		"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"
	};
	//요일(한글) 배열
	public static final String[] DAY_OF_WEEK_KO = {
		"일", "월", "화", "수", "목", "금", "토"
	};

	//요일(영어)
	public static String getDayOfWeek(Calendar cal) {
		if(cal == null) return "";
		return DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}

	//요일(한글)
	public static String getDayOfWeekKo(Calendar cal) {
		if(cal == null) return "";
		return DAY_OF_WEEK_KO[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}


	//주간예보 에서 사용될 3,4,5,6 일후 요일 구하기.
	public static String getDayOfWeekKo(Calendar cal, int i) {
		if(cal == null) return "";
		return DAY_OF_WEEK_KO[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}


	//문자 스트링(년월일시분)을 날자로 변환
	public static Calendar getCalendarInstance(String yyyymmddhhmi) {
		if (yyyymmddhhmi == null) return null;

		Calendar cal = Calendar.getInstance();
		if( cal == null) return null;
		cal.clear();

		if ((yyyymmddhhmi.indexOf("-") == -1) && (yyyymmddhhmi.length() == 12)) {
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymmddhhmi.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymmddhhmi.substring(4, 6)) - 1);
			cal.set(Calendar.DATE, Integer.parseInt(yyyymmddhhmi.substring(6, 8)));
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yyyymmddhhmi.substring(8, 10)));
			cal.set(Calendar.MINUTE, Integer.parseInt(yyyymmddhhmi.substring(10)));
			return cal;
		} else if (yyyymmddhhmi.length() == 16) {
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymmddhhmi.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymmddhhmi.substring(5, 7))- 1);
			cal.set(Calendar.DATE, Integer.parseInt(yyyymmddhhmi.substring(8, 10)));
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yyyymmddhhmi.substring(11, 13)));
			cal.set(Calendar.MINUTE, Integer.parseInt(yyyymmddhhmi.substring(14)));
			return cal;
		} else {
			return null;
		}
	}

	//문자 스트링(년월일)을 날자로 변환
	public static Calendar getCalendarInstance(String yyyymmdd, String delimeter) {
		if (yyyymmdd == null) return null;

		Calendar cal = Calendar.getInstance();
		if( cal == null) return null;
		cal.clear();

		if (yyyymmdd.length() == 8) {
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymmdd.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymmdd.substring(4, 6)) - 1);
			cal.set(Calendar.DATE, Integer.parseInt(yyyymmdd.substring(6, 8)));
			return cal;
		} else if (yyyymmdd.length() == 10) {
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymmdd.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymmdd.substring(5, 7))- 1);
			cal.set(Calendar.DATE, Integer.parseInt(yyyymmdd.substring(8, 10)));
			return cal;
		} else {
			return null;
		}
	}

	//문자스트링(년월일)을 날자로 변환
	public static Calendar getCalendarInstanceFromYYYYMMDD(String yyyymmdd) {
		if(yyyymmdd == null || yyyymmdd.length() != 8) 	return null;

		return getCalendarInstance(yyyymmdd + "0000");
	}

	//	문자스트링(년월일시분초)을 날자로 변환
	public static Calendar getCalendarInstanceFromYYYYMMDDHH24MISS(String yyyymmddhh24miss) {
		Calendar cal = Calendar.getInstance();
		if(cal != null){
			cal.clear();
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymmddhh24miss.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymmddhh24miss.substring(4, 6)) - 1);
			cal.set(Calendar.DATE, Integer.parseInt(yyyymmddhh24miss.substring(6, 8)));
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yyyymmddhh24miss.substring(8, 10)));
			cal.set(Calendar.MINUTE, Integer.parseInt(yyyymmddhh24miss.substring(10, 12)));
			cal.set(Calendar.SECOND, Integer.parseInt(yyyymmddhh24miss.substring(12)));
			return cal;
		}
		else {
			return null;
		}
	}

	//
	public static Calendar getCalendarInstance(Calendar toBeCopyed) {
		if(toBeCopyed == null) return null;

		Calendar cal = Calendar.getInstance();
		if(cal != null){
			cal.setTime(toBeCopyed.getTime());
			return cal;
		}
		else {
			return null;
		}
	}

	public static Calendar getCalendarInstance(Timestamp t, int field, int addedAmount) {
		if (t == null) 	return null;

		Calendar cal = Calendar.getInstance();
		if(cal != null){
			cal.setTime(t);
			cal.add(field, addedAmount);

			return cal;
		}
		else {
			return null;
		}
	}

	public static Calendar getCalendarInstance(Timestamp t) {
		if (t == null) 	return null;

		Calendar cal = Calendar.getInstance();
		if(cal != null){
			cal.setTime(t);

			return cal;
		}
		else {
			return null;
		}
	}

	public static Calendar getDate(Calendar cal, int field, int addedAmount) {
		Calendar copied = getCalendarInstance(cal);
		if(copied != null){
		copied.add(field, addedAmount);

		return 	copied;
		}
		else {
			return null;
		}
	}

	public static String getDateString(Calendar cal, int field, int addedAmount) {
		if (cal == null) 	return "";

		Calendar copied = getCalendarInstance(cal);
		if(copied != null ){
			copied.add(field, addedAmount);

			DecimalFormat df4 = new DecimalFormat("0000");
			DecimalFormat df2 = new DecimalFormat("00");

			return 	df4.format(copied.get(Calendar.YEAR)) + "-"
					+ df2.format(copied.get(Calendar.MONTH) + 1) + "-"
					+ df2.format(copied.get(Calendar.DATE));
		}
		else {
			return "";
		}
	}



	// yyyymmdd
	public static String getDateString() {
		Calendar cal = Calendar.getInstance();
		if(cal != null) {
		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		return 	df4.format(cal.get(Calendar.YEAR))
			  + df2.format(cal.get(Calendar.MONTH) + 1)
			  + df2.format(cal.get(Calendar.DATE));
		}
		else {
			return "";
		}
	}

	// yyyymmdd
	public static String getDateString(Calendar cal) {
		if (cal == null) 	return "";

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		return 	df4.format(cal.get(Calendar.YEAR))
			  + df2.format(cal.get(Calendar.MONTH) + 1)
			  + df2.format(cal.get(Calendar.DATE));
	}


	// yyyymmdd
	public static String getDateString(Calendar cal,String delimeter) {
		if (cal == null) 	return "";

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		return 	df4.format(cal.get(Calendar.YEAR)) + delimeter
			  + df2.format(cal.get(Calendar.MONTH) + 1)+ delimeter
			  + df2.format(cal.get(Calendar.DATE));
	}

	// yyyy/mm/dd
	public static String getDateString(String delimeter) {
		Calendar cal = Calendar.getInstance();
		if(cal != null){
		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		return 	df4.format(cal.get(Calendar.YEAR)) + delimeter
			  + df2.format(cal.get(Calendar.MONTH) + 1) + delimeter
			  + df2.format(cal.get(Calendar.DATE));
		}
		else {
			return "";
		}
	}

	public static String getDateString(Calendar cal, int field, int addedAmount,String delimeter) {
		if (cal == null) 	return "";

		Calendar copied = getCalendarInstance(cal);
		if(copied != null){
			copied.add(field, addedAmount);

			DecimalFormat df4 = new DecimalFormat("0000");
			DecimalFormat df2 = new DecimalFormat("00");

			return 	df4.format(copied.get(Calendar.YEAR)) + delimeter
					+ df2.format(copied.get(Calendar.MONTH) + 1) + delimeter
					+ df2.format(copied.get(Calendar.DATE));
		}
		else {
			return "";
		}
	}

	public static String getTimeString() {
	    Calendar cal = Calendar.getInstance();

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");
		if(cal != null) {
		return 	df4.format(cal.get(Calendar.YEAR)) + "-"
				+ df2.format(cal.get(Calendar.MONTH) + 1) + "-"
				+ df2.format(cal.get(Calendar.DATE)) + " "
				+ df2.format(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ df2.format(cal.get(Calendar.MINUTE)) + " :"
		        + df2.format(cal.get(Calendar.SECOND));
		}
		else {
			return "";
		}
	}

	public static String getTimeString1() {
	    Calendar cal = Calendar.getInstance();

	    if(cal != null){
			DecimalFormat df4 = new DecimalFormat("0000");
			DecimalFormat df2 = new DecimalFormat("00");

			return 	df4.format(cal.get(Calendar.YEAR))
					+ df2.format(cal.get(Calendar.MONTH) + 1)
					+ df2.format(cal.get(Calendar.DATE))
					+ df2.format(cal.get(Calendar.HOUR_OF_DAY))
					+ df2.format(cal.get(Calendar.MINUTE))
			        + df2.format(cal.get(Calendar.SECOND));
	    }
	    else {
	    	return "";
	    }
	}

    public static String getTimeString(Calendar cal, int field, int addedAmount) {
        if (cal == null) return "";

        Calendar copied = getCalendarInstance(cal);
        if(copied != null){
	        copied.add(field, addedAmount);

	        DecimalFormat df4 = new DecimalFormat("0000");
	        DecimalFormat df2 = new DecimalFormat("00");

	        return  df4.format(copied.get(Calendar.YEAR)) + "-"
	                + df2.format(copied.get(Calendar.MONTH) + 1) + "-"
	                + df2.format(copied.get(Calendar.DATE)) + " "
	                + df2.format(copied.get(Calendar.HOUR_OF_DAY)) + ":"
	                + df2.format(copied.get(Calendar.MINUTE));
        }
        else {
        	return "";
        }
    }

	public static String getTimeString(Calendar cal, int field, int addedAmount, int decrement) {
		if (cal == null) return "";
		Calendar copied = getCalendarInstance(cal);
		if(copied != null){
			copied.add(field, addedAmount);

			DecimalFormat df4 = new DecimalFormat("0000");
			DecimalFormat df2 = new DecimalFormat("00");

			return 	df4.format(copied.get(Calendar.YEAR))
					+ df2.format(copied.get(Calendar.MONTH) + 1)
					+ df2.format(copied.get(Calendar.DATE))
					+ df2.format(copied.get(Calendar.HOUR_OF_DAY))
					+ df2.format(copied.get(Calendar.MINUTE)).substring(0,1) + "0";
		}
		else {
			return "";
		}
	}


	public static String getHHString(Calendar cal) {
		if (cal == null) return "";

		Calendar copied = getCalendarInstance(cal);
		DecimalFormat df2 = new DecimalFormat("00");
/*
		if("0".equals(df2.format(copied.get(Calendar.HOUR_OF_DAY)).substring(0,1))){
			return df2.format(copied.get(Calendar.HOUR_OF_DAY)).substring(1,2);
		}
*/
		if(copied != null) {
			return df2.format(copied.get(Calendar.HOUR_OF_DAY));
		}
		else {
			return "";
		}
	}

	public static String getMIString(Calendar cal) {
		if (cal == null) return "";

		Calendar copied = getCalendarInstance(cal);
		DecimalFormat df2 = new DecimalFormat("00");
		if(copied != null){
		return 	df2.format(copied.get(Calendar.MINUTE));
		}
		else {
			return "";
		}
	}

	public static String getTimeString(Timestamp ts, int field, int addedAmount) {
		if (ts == null) return "";

		Calendar cal = getCalendarInstance(ts);

		return getTimeString(cal, field, addedAmount);
	}

	public static String getTimeString(Calendar cal) {
		return getTimeString(cal, Calendar.HOUR_OF_DAY, 0);
	}

	public static String getTimeString(Timestamp t) {
		return getTimeString(t, Calendar.HOUR_OF_DAY, 0);
	}

	public static String getDetailDateString(Calendar cal) {
		if (cal == null) return "";

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		return 	df4.format(cal.get(Calendar.YEAR)) + "년 "
				+ df2.format(cal.get(Calendar.MONTH) + 1) + "월 "
				+ df2.format(cal.get(Calendar.DATE)) + "일 "
				+ df2.format(cal.get(Calendar.HOUR_OF_DAY)) + "시";
	}

	public static String getDetailTimeString(Calendar cal) {
		if (cal == null) return "";

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		return 	df4.format(cal.get(Calendar.YEAR)) + "년 "
				+ df2.format(cal.get(Calendar.MONTH) + 1) + "월 "
				+ df2.format(cal.get(Calendar.DATE)) + "일 "
				+ df2.format(cal.get(Calendar.HOUR_OF_DAY)) + "시 "
				+ df2.format(cal.get(Calendar.MINUTE)) + "분";
	}

// 월/일/요일 반환
	public static String getMonthAndDayString(Calendar cal) {
		if (cal == null) 	return "";
		String dayOfWeek = getDayOfWeekKo(cal);
		DecimalFormat df2 = new DecimalFormat("00");
		return df2.format(cal.get(Calendar.MONTH) + 1) + "월" + df2.format(cal.get(Calendar.DATE)) + "일" + "(" +dayOfWeek + ")";
	}

	//월/일 반환
	public static String getMonthAndDayString(Calendar cal, String s) {
		if (cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		return df2.format(cal.get(Calendar.MONTH) + 1) + "월" + df2.format(cal.get(Calendar.DATE)) + "일";
	}

	//월/일 반환
	public static String getMonthAndDayString(Calendar cal, int s) {
		if (cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		return df2.format(cal.get(Calendar.MONTH) + 1) + "/" + df2.format(cal.get(Calendar.DATE));
	}

	// 시분 반환
	public static String getTimeAndMinutes(Calendar cal) {
		if (cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		return df2.format(cal.get(Calendar.HOUR_OF_DAY)) + "시" + df2.format(cal.get(Calendar.MINUTE)) + "분";
	}
	// 시/분 반환
	public static String getTimeMinutesString(Calendar cal) {
		if (cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		return df2.format(cal.get(Calendar.HOUR_OF_DAY)) + "시&nbsp;" + df2.format(cal.get(Calendar.MINUTE)) + "분";
	}
	/**
* plusMore 만큼의 시간을 더한 00 포맷의 시간 반환.
* @param cal
* @param plusMore
* @return
*/
	public static String getPlusMoreTime(Calendar cal, int plusMore){
		if(cal == null)	return "";

		DecimalFormat df2 = new DecimalFormat("00");
		cal.add(Calendar.HOUR_OF_DAY, plusMore);
		String plusMoreTime = df2.format(cal.get(Calendar.HOUR_OF_DAY));
		cal.add(Calendar.HOUR_OF_DAY, -plusMore);
		return plusMoreTime;
	}

	// 주간예보 에서 사용될 일/요일  반환  (int Parameter 만큼의 앞날 이 반환된다.)
	public static String getAfterThreeDayAndWeekString(Calendar cal, int wannaAfter) {
		if (cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		cal.add(Calendar.DATE, wannaAfter);

		String dayOfWeek = getDayOfWeekKo(cal, wannaAfter);
		String calDate = df2.format(cal.get(Calendar.DATE));

		cal.add(Calendar.DATE, -wannaAfter);

		return calDate + "일" + "(" +dayOfWeek + ")";
	}

	/**
* 3시간 예보 에서 사용될 하루 더한  월, 일, 요일 반환.
* @param cal
* @return
*/
	public static String getPlusOneMonthDayWeek(Calendar cal){
		if(cal == null) return null;

		DecimalFormat df2 = new DecimalFormat("00");
		cal.add(Calendar.DATE, 1);

		String dayOfWeek = getDayOfWeekKo(cal, 1);
		String calMonth = df2.format(cal.get(Calendar.MONTH) + 1);
		String calDay = df2.format(cal.get(Calendar.DAY_OF_MONTH));
		cal.add(Calendar.DATE, -1);
		return calMonth + "월" + calDay + "일(" + dayOfWeek + ")";
	}

	public static String getPlusOneMonthDayWeek(Calendar cal, int i){
		if(cal == null)	return null;

		DecimalFormat df2 = new DecimalFormat("00");
		cal.add(Calendar.DATE, 1);

		String calMonth = df2.format(cal.get(Calendar.MONTH) + 1);
		String calDay = df2.format(cal.get(Calendar.DAY_OF_MONTH));
		cal.add(Calendar.DATE, -1);
		return calMonth + "/" + calDay;
	}

	public static String getMonthDayWeekString(Calendar cal, int wannaAfter) {
		if (cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		cal.add(Calendar.DATE, wannaAfter);

		String dayOfWeek = getDayOfWeekKo(cal, wannaAfter);
		String calDate = df2.format(cal.get(Calendar.DATE));

		cal.add(Calendar.DATE, -wannaAfter);

		return calDate + "일" + "(" +dayOfWeek + ")";
	}

/**
* 위성영상의 UTC 타임을 KST 로 변환한다.
* @param yyyymmddhh24mmss
* @return
*/

	public static String getAdjustKSTTime(String yyyymmddhh24mm){
		Calendar cal = CalendarUtil.getCalendarInstancePlus(yyyymmddhh24mm);
		if(cal != null) {
		Calendar copied = cal;
		copied.add(Calendar.HOUR_OF_DAY, 9);
		cal = null;
		String returnTimeString = CalendarUtil.getYYYYMMDDHH24MI(copied);
		copied = null;

		return returnTimeString;
		} else {
			return "";
		}
	}


	public static String getMinusOneDay(String yyyymmdd){
		Calendar cal = CalendarUtil.getCalendarInstancePlus(yyyymmdd+"0000");
		if(cal != null ){
		Calendar copied = cal;

		copied.add(Calendar.DAY_OF_MONTH, -1);
		cal = null;
		String returnTimeString = CalendarUtil.getYYYYMMDD(copied);

		copied = null;
		return returnTimeString;
		}
		else {
			return "";
		}
	}


	public static Calendar getCalendarInstancePlus(String yyyymmddhhmi) {
		if (yyyymmddhhmi == null) return null;

		Calendar cal = Calendar.getInstance();
		cal.clear();

		if ((yyyymmddhhmi.indexOf("-") == -1) && (yyyymmddhhmi.length() == 12)) {
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymmddhhmi.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymmddhhmi.substring(4, 6)) - 1);
			cal.set(Calendar.DATE, Integer.parseInt(yyyymmddhhmi.substring(6, 8)));
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yyyymmddhhmi.substring(8, 10)));
			cal.set(Calendar.MINUTE, Integer.parseInt(yyyymmddhhmi.substring(10)));
			return cal;
		} else if (yyyymmddhhmi.length() == 16) {
			cal.set(Calendar.YEAR, Integer.parseInt(yyyymmddhhmi.substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(yyyymmddhhmi.substring(5, 7)) - 1);
			cal.set(Calendar.DATE, Integer.parseInt(yyyymmddhhmi.substring(8, 10)));
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(yyyymmddhhmi.substring(11, 13)));
			cal.set(Calendar.MINUTE, Integer.parseInt(yyyymmddhhmi.substring(14)));
			return cal;
		} else {
			return null;
		}
	}

	// "12. 1" 형식으로 반환
	public static String getDateString2(Calendar cal) {
		if (cal == null) return "";

		return (cal.get(Calendar.MONTH) + 1) + ". "
				+ cal.get(Calendar.DATE);
	}

	public static String getTimeStringWithSecond(Calendar cal, int field, int addedAmount) {
		if (cal == null) return "";

		Calendar copied = getCalendarInstance(cal);
		if(copied != null){
		copied.add(field, addedAmount);

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		return 	df4.format(copied.get(Calendar.YEAR)) + "-"
				+ df2.format(copied.get(Calendar.MONTH) + 1) + "-"
				+ df2.format(copied.get(Calendar.DATE)) + " "
				+ df2.format(copied.get(Calendar.HOUR_OF_DAY)) + ":"
				+ df2.format(copied.get(Calendar.MINUTE)) + ":"
				+ df2.format(copied.get(Calendar.SECOND));
		}
		else {
			return "";
		}
	}

	public static String getTimeStringWithSecond(Timestamp ts, int field, int addedAmount) {
		if (ts == null) return "";

		Calendar cal = getCalendarInstance(ts);

		return getTimeStringWithSecond(cal, field, addedAmount);
	}

	public static String getTimeStringWithSecond(Calendar cal) {
		return getTimeStringWithSecond(cal, Calendar.HOUR_OF_DAY, 0);
	}

	public static String getTimeStringWithSecond(Timestamp t) {
		return getTimeStringWithSecond(t, Calendar.HOUR_OF_DAY, 0);
	}

	public static String getYYYYMMDD(Calendar cal) {
		if(cal == null) return "";

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");
		return df4.format(cal.get(Calendar.YEAR)) + df2.format(cal.get(Calendar.MONTH) + 1) + df2.format(cal.get(Calendar.DATE));
	}

	public static String getYYYYMMDDHH24MI(Calendar cal) {
		if(cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		return cal.get(Calendar.YEAR) + df2.format(cal.get(Calendar.MONTH) + 1) + df2.format(cal.get(Calendar.DATE)) + df2.format(cal.get(Calendar.HOUR_OF_DAY)) + df2.format(cal.get(Calendar.MINUTE));
	}

	public static String getYYYYMMDDHH24MISS(Calendar cal) {
		if(cal == null) return "";

		DecimalFormat df2 = new DecimalFormat("00");
		return cal.get(Calendar.YEAR) + df2.format(cal.get(Calendar.MONTH) + 1) + df2.format(cal.get(Calendar.DATE)) + df2.format(cal.get(Calendar.HOUR_OF_DAY)) + df2.format(cal.get(Calendar.MINUTE));
	}


	public static String getYYYYMMDDHH24MI(Calendar cal, int field, int addedAmount) {
		if(cal == null) return "";

		Calendar temp = getCalendarInstance(cal);
		if(temp != null){
			temp.add(field, addedAmount);
			return getYYYYMMDDHH24MI(temp);
		}
		else {
			return "";
		}
	}

	public static String getSysDateYYYYMMDD(){
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);
		String day = dateFormat.format(new java.util.Date());
		return day;
	}


	public static Calendar getSysDateYYYYMMDDHH24MI(){
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyyMMddHH24MI", java.util.Locale.KOREA);
		String day = dateFormat.format(new java.util.Date());
		Calendar cal = CalendarUtil.getCalendarInstance(day);
		return cal;
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if(cal1 == null || cal2 == null) {
			return false;
		}
		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE));
	}

	public static String getYyyyString(){
		return CalendarUtil.getSysDateYYYYMMDD().substring(0,4);
	}

	public static String getMmString(){
		return CalendarUtil.getSysDateYYYYMMDD().substring(4,6);
	}

	public static String getDdString(){
		return CalendarUtil.getSysDateYYYYMMDD().substring(6);
	}


/**
* 육상예보 세부페이지 온도 플래쉬 표출을 위한 -2일 날짜부터 +2일 날짜까지 ..
* @return
*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getPlusThreeFromMinusTwo(){
		String currentDate = CalendarUtil.getSysDateYYYYMMDD();
		Calendar currentCal = CalendarUtil.getCalendarInstanceFromYYYYMMDD(currentDate);

		List tempList = new ArrayList();
		if(currentCal != null) {
			DecimalFormat df4 = new DecimalFormat("0000");
			DecimalFormat df2 = new DecimalFormat("00");

			currentCal.add(Calendar.DATE, -2);
			String minusTwo = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String minusOne = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String current = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String plusOne = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String plusTwo = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));


			tempList.add(minusTwo);
			tempList.add(minusOne);
			tempList.add(current);
			tempList.add(plusOne);
			tempList.add(plusTwo);
		}

		return tempList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getPlusThreeFromMinusTwoEffective(){
		String currentDate = CalendarUtil.getSysDateYYYYMMDD();
		Calendar currentCal = CalendarUtil.getCalendarInstanceFromYYYYMMDD(currentDate);

		List tempList = new ArrayList();

		if(currentCal != null) {
			DecimalFormat df4 = new DecimalFormat("0000");
			DecimalFormat df2 = new DecimalFormat("00");

			currentCal.add(Calendar.DATE, -3);
			String minusTwo = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String minusOne = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String current = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String plusOne = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));
			currentCal.add(Calendar.DATE, 1);
			String plusTwo = df4.format(currentCal.get(Calendar.YEAR)) + df2.format(currentCal.get(Calendar.MONTH) + 1) + df2.format(currentCal.get(Calendar.DAY_OF_MONTH));

			tempList.add(minusTwo);
			tempList.add(minusOne);
			tempList.add(current);
			tempList.add(plusOne);
			tempList.add(plusTwo);
		}
		return tempList;
	}

	//
	public static String dateToString(String date){
		String strRet = "";
		int len = date.trim().length();
		if(len == 6)
			strRet = date.substring(0,4) + "-" + date.substring(4,6);
		else if(len == 8)
			strRet = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);
		else
			strRet = date;
		return strRet;
	}

	// 분기
	public static String getBungi(){
		String bungi;
		int month	= StringUtil.parseInt(getMmString()) ;

		if (month < 4) 			bungi = "1" ;
		else if (month < 7) 		bungi = "2";
		else if (month < 10)		bungi = "3";
		else bungi = "4";

		return bungi;
	}


}
