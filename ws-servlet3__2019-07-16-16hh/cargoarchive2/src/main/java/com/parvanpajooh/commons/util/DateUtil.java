//package com.parvanpajooh.commons.util;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import com.parvanpajooh.commons.constants.StringPool;
//
//public class DateUtil {
//
//	public static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
//	public static final String PATTERN_NONSTANDARD__yyyyMMdd_hhmm = "yyyy/MM/dd hh:mm";
//	public static final String PATTERN_STANDARD__yyyyMMdd_HHmm = "yyyy/MM/dd HH:mm";
//	public static final String PATTERN_ISO__yyyyMMdd = "yyyy-MM-dd";
//	public static final String PATTERN_ISO__yyyyMMdd_HHmm = "yyyy-MM-dd HH:mm";
//	public static final String PATTERN_ISO__yyyyMMdd_hhmm = "yyyy-MM-dd hh:mm";
//	public static final String PATTERN__yyyyMMdd = "yyyy/MM/dd";
//	
//	public static final String PATTERN_DATE = "yyyy-MM-dd";
//	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm";
//	public static final String PATTERN_DATETIME_yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";
//	public static final String PATTERN_TIME = "HH:mm";
//	public static final String CALENDAR_JALALI = "JALALI";
//	public static final String CALENDAR_GREGORIAN = "GREGORIAN";
//	
//	public static final String SLASH_SEPERATOR = StringPool.SLASH;
//	public static final String DASH_SEPERATOR = StringPool.DASH;
//	public static final String CONST_yyyy = "yyyy";
//	public static final String CONST_MM = "MM";
//	public static final String CONST_dd = "dd";
//	public static final String CONST_HH = "HH";
//	public static final String CONST_mm = "mm";
//	public static final String CONST_ss = "ss";
//	public static final String CONST_13 = "13";
//	public static final String CONST_0 = "0";
//	public static final String CONST_1357 = "1357";
//	public static final String CONST_11 = "11";
//	public static final String CONST_15 = "15";
//	public static final String CONST_00 = "00";
//	
//	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_DATETIME);
//	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_DATE);
//	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_TIME);
//
//	/*private static String convertDateToString(Date date, String pattern) throws ParseException {
//		String strDate = new SimpleDateFormat(pattern, Locale.ENGLISH).format(date);
//		return strDate;
//	}
//
//	private static String convertDateToString(Date date) throws ParseException {
//		String strDate = new SimpleDateFormat(PATTERN_ISO__yyyyMMdd_HHmm, Locale.ENGLISH).format(date);
//		return strDate;
//	}
//
//	private static String convertToPersianDate(Date date, Locale locale, ZoneId zoneId) {
//
//		return convertUtcToPersianDate(date, locale, zoneId, SLASH_SEPERATOR);
//	}*/
//
//	private static String convertUtcToPersianDate(Date utcDate, Locale locale, ZoneId zoneId, String dateSeperator) {
//
//		if (Validator.isNull(utcDate)) {
//			return null;
//		}
//		
//		Instant instant = utcDate.toInstant();
//		
//		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneIdUtil.getDefault());
//		zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
//		
//		Date localDate = Date.from(zonedDateTime.toInstant());
//		
//		//LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneIdUtil.getDefault());
//		//Date localDate = Date.from(ldt.atZone(ZoneIdUtil.getDefault()).withZoneSameLocal(zoneId).toInstant());
//
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(localDate);
//		cal.setTimeZone(TimeZoneUtil.getTimeZone(zoneId));
//
//		SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
//		persianCalendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//		DateFields dfs = persianCalendar.getDateFields();
//
//		return dfs.getYear() + dateSeperator + ((dfs.getMonth() + 1) < 10 ? "0" : StringPool.BLANK)
//				+ (dfs.getMonth() + 1) + dateSeperator + (dfs.getDay() < 10 ? "0" : StringPool.BLANK) + dfs.getDay();
//		
//	}
//	
//	private static String convertUtcToPersianDate(LocalDate utcDate, Locale locale, ZoneId zoneId, String dateSeperator) {
//		
//		if (Validator.isNull(utcDate)) {
//			return null;
//		}
//		
//		LocalDate ldt = utcDate.atStartOfDay(zoneId).toLocalDate();
//		Date localDate = Date.from(ldt.atStartOfDay(zoneId).toInstant());
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(localDate);
//		cal.setTimeZone(TimeZoneUtil.getTimeZone(zoneId));
//		
//		SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
//		persianCalendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//		DateFields dfs = persianCalendar.getDateFields();
//		
//		return dfs.getYear() + dateSeperator + ((dfs.getMonth() + 1) < 10 ? "0" : StringPool.BLANK)
//				+ (dfs.getMonth() + 1) + dateSeperator + (dfs.getDay() < 10 ? "0" : StringPool.BLANK) + dfs.getDay();
//		
//	}
//	
//	private static String convertUtcToPersianDate(LocalDateTime utcDate, Locale locale, ZoneId zoneId, String dateSeperator) {
//		
//		if (Validator.isNull(utcDate)) {
//			return null;
//		}
//		
//		ZonedDateTime zonedDateTime = ZonedDateTime.of(utcDate, ZoneIdUtil.getDefault());
//		zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
//		
//		//ZonedDateTime ldt = utcDate.atZone(ZoneIdUtil.getDefault()).withZoneSameLocal(zoneId);
//		Date localDate = Date.from(zonedDateTime.toInstant());
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(localDate);
//		cal.setTimeZone(TimeZoneUtil.getTimeZone(zoneId));
//		
//		SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
//		persianCalendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//		DateFields dfs = persianCalendar.getDateFields();
//		
//		return dfs.getYear() + dateSeperator + ((dfs.getMonth() + 1) < 10 ? "0" : StringPool.BLANK)
//				+ (dfs.getMonth() + 1) + dateSeperator + (dfs.getDay() < 10 ? "0" : StringPool.BLANK) + dfs.getDay();
//		
//	}
//
//	/*private static String convertUtcToPersianDateTime(Date date, Locale locale, ZoneId zoneId) {
//
//		return convertUtcToPersianDateTime(date, locale, zoneId, SLASH_SEPERATOR);
//	}*/
//
//	private static String convertUtcToPersianDateTime(Date utcDate, Locale locale, ZoneId zoneId, String dateSeperator) {
//
//		if (Validator.isNull(utcDate)) {
//			return null;
//		}
//
//		Instant instant = utcDate.toInstant();
//		
//		LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
//
//		return convertUtcToPersianDate(utcDate, locale, zoneId, dateSeperator) + StringPool.SPACE
//				+ (ldt.getHour() < 10 ? "0" : StringPool.BLANK) + ldt.getHour() + ":"
//				+ (ldt.getMinute() < 10 ? "0" : StringPool.BLANK) + ldt.getMinute();
//	}
//	
//	private static String convertUtcToPersianDateTime(LocalDateTime utcDate, Locale locale, ZoneId zoneId, String dateSeperator) {
//		
//		if (Validator.isNull(utcDate)) {
//			return null;
//		}
//		
//		ZonedDateTime zonedDateTime = ZonedDateTime.of(utcDate, ZoneIdUtil.getDefault());
//		zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
//		
//		//LocalDateTime ldt = utcDate.atZone(ZoneIdUtil.getDefault()).withZoneSameLocal(zoneId).toLocalDateTime();
//		
//		return convertUtcToPersianDate(utcDate, locale, zoneId, dateSeperator) + StringPool.SPACE
//				+ (zonedDateTime.getHour() < 10 ? "0" : StringPool.BLANK) + zonedDateTime.getHour() + ":"
//				+ (zonedDateTime.getMinute() < 10 ? "0" : StringPool.BLANK) + zonedDateTime.getMinute();
//	}
//
//	/*private static Date convertStringTimeToDate(String strTime) {
//
//		if (Validator.isNull(strTime)) {
//			return null;
//		}
//
//		String aMask = "HH:mm";
//
//		int indexOfHour = aMask.indexOf(CONST_HH);
//		int indexOfMinute = aMask.indexOf(CONST_mm);
//		int indexOfSecond = aMask.indexOf(CONST_ss);
//
//		int hour = -1;
//		int minute = -1;
//		int second = -1;
//
//		if (indexOfHour != -1)
//			hour = Integer.parseInt(strTime.substring(indexOfHour, indexOfHour + 2));
//		if (indexOfMinute != -1)
//			minute = Integer.parseInt(strTime.substring(indexOfMinute, indexOfMinute + 2));
//		if (indexOfSecond != -1)
//			second = Integer.parseInt(strTime.substring(indexOfSecond, indexOfSecond + 2));
//
//		Calendar cal = Calendar.getInstance();
//		if (hour != -1)
//			cal.set(Calendar.HOUR_OF_DAY, hour);
//		if (minute != -1)
//			cal.set(Calendar.MINUTE, minute);
//		if (second != -1)
//			cal.set(Calendar.SECOND, second);
//
//		return cal.getTime();
//	}
//
//	private static Date convertStringToDate(String strDate, Locale locale, ZoneId zoneId, String calendarType) {
//
//		if (Validator.isNull(strDate)) {
//			return null;
//		}
//
//		return convertStringToUTCDate(PATTERN__yyyyMMdd, strDate, locale, zoneId, calendarType);
//	}
//
//	private static Date convertStringToDateTime(String strDate, Locale locale, ZoneId zoneId, String calendarType) {
//
//		if (Validator.isNull(strDate)) {
//			return null;
//		}
//
//		return convertStringToUTCDate(PATTERN_STANDARD__yyyyMMdd_HHmm, strDate, locale, zoneId, calendarType);
//	}
//
//	private static Date convertStringToDate(String fromPattern, String dateString, Locale locale) {
//		Date date = null;
//		boolean isPersian = false;
//
//		try {
//			String[] temp = new String[0];
//			if (dateString.indexOf('/') > -1)
//				temp = dateString.split(StringPool.SLASH);
//			else if (dateString.indexOf('-') > -1)
//				temp = dateString.split(StringPool.DASH);
//			for (int i = 0; i < temp.length; i++) {
//				if (temp[i].length() == 4) {
//					if (Integer.parseInt(temp[i]) > 1450) {
//						isPersian = false;
//					} else {
//						isPersian = true;
//					}
//					break;
//				}
//			}
//
//			if (temp.length != 3) {
//				throw new NumberFormatException();
//			}
//			if (isPersian) {
//				if (temp[0].length() < 4 || 1290 > Integer.valueOf(temp[0]) || 1450 < Integer.valueOf(temp[0])) {
//					throw new NumberFormatException();
//				}
//
//			} else {
//				if (temp[0].length() < 4 || 1900 > Integer.valueOf(temp[0]) || 2100 < Integer.valueOf(temp[0])) {
//					throw new NumberFormatException();
//				}
//			}
//			if (temp[1].length() < 2 || 0 > Integer.valueOf(temp[1]) || 13 < Integer.valueOf(temp[1])) {
//				throw new NumberFormatException();
//			}
//			if (temp[2].length() < 2 || 0 > Integer.valueOf(temp[1]) || 32 < Integer.valueOf(temp[1])) {
//				throw new NumberFormatException();
//			}
//
//			if (isPersian) {
//				int year = -1;
//				int month = -1;
//				int day = -1;
//				int hour = -1;
//				int minute = -1;
//				int second = -1;
//
//				int indexOfYear = fromPattern.indexOf(CONST_yyyy);
//				int indexOfMonth = fromPattern.indexOf(CONST_MM);
//				int indexOfDay = fromPattern.indexOf(CONST_dd);
//				int indexOfHour = fromPattern.indexOf(CONST_HH);
//				int indexOfMinute = fromPattern.indexOf(CONST_mm);
//				int indexOfSecond = fromPattern.indexOf(CONST_ss);
//
//				if (indexOfDay != -1)
//					day = Integer.parseInt(dateString.substring(indexOfDay, indexOfDay + 2));
//				if (indexOfMonth != -1)
//					month = Integer.parseInt(dateString.substring(indexOfMonth, indexOfMonth + 2));
//				if (indexOfYear != -1)
//					year = Integer.parseInt(dateString.substring(indexOfYear, indexOfYear + 4));
//				if (indexOfHour != -1)
//					hour = Integer.parseInt(dateString.substring(indexOfHour, indexOfHour + 2));
//				if (indexOfMinute != -1)
//					minute = Integer.parseInt(dateString.substring(indexOfMinute, indexOfMinute + 2));
//				if (indexOfSecond != -1)
//					second = Integer.parseInt(dateString.substring(indexOfSecond, indexOfSecond + 2));
//
//				SimplePersianCalendar pCal = new SimplePersianCalendar();
//				pCal.setDateFields(year, month - 1, day);
//
//				date = pCal.getTime();
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(date);
//				if (hour != -1)
//					cal.set(Calendar.HOUR_OF_DAY, hour);
//				if (minute != -1)
//					cal.set(Calendar.MINUTE, minute);
//				if (second != -1)
//					cal.set(Calendar.SECOND, second);
//
//				date = cal.getTime();
//
//			} else {
//				DateFormat dateFormat = null;
//
//				if (Validator.isNull(fromPattern)) {
//					//dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
//					dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(PATTERN_DATE, locale);
//				} else {
//					dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(fromPattern, locale);
//				}
//				
//				date = dateFormat.parse(dateString);
//			}
//
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//
//		return date;
//	}*/
//	
//	private static Date convertStringToUTCDate(String fromPattern, String dateString, Locale locale, ZoneId zoneId, String calendarType) {
//		return convertStringToUTCDateTime(fromPattern, dateString, locale, zoneId, calendarType);
//	}
//	
//	private static Date convertStringToUTCDateTime(String fromPattern, String dateString, Locale locale, ZoneId zoneId, String calendarType) {
//		Date utcDate = null;
//		
//		try {
//			boolean isPersian = calendarType.equals(CALENDAR_JALALI);
//			
//			String[] temp = new String[0];
//			if (dateString.indexOf('/') > -1)
//				temp = dateString.split(StringPool.SLASH);
//			else if (dateString.indexOf('-') > -1)
//				temp = dateString.split(StringPool.DASH);
//			
//			if (temp.length != 3) {
//				throw new NumberFormatException();
//			}
//			
//			if (isPersian) {
//				if (temp[0].length() < 4 || 1290 > Integer.valueOf(temp[0]) || 1450 < Integer.valueOf(temp[0])) {
//					throw new NumberFormatException();
//				}
//				
//			} else {
//				if (temp[0].length() < 4 || 1900 > Integer.valueOf(temp[0]) || 2100 < Integer.valueOf(temp[0])) {
//					throw new NumberFormatException();
//				}
//			}
//			if (temp[1].length() < 2 || 0 > Integer.valueOf(temp[1]) || 13 < Integer.valueOf(temp[1])) {
//				throw new NumberFormatException();
//			}
//			if (temp[2].length() < 2 || 0 > Integer.valueOf(temp[1]) || 32 < Integer.valueOf(temp[1])) {
//				throw new NumberFormatException();
//			}
//			
//			if (isPersian) {
//				int year = 0;
//				int month = 0;
//				int day = 0;
//				int hour = 0;
//				int minute = 0;
//				int second = 0;
//				
//				int indexOfYear = fromPattern.indexOf(CONST_yyyy);
//				int indexOfMonth = fromPattern.indexOf(CONST_MM);
//				int indexOfDay = fromPattern.indexOf(CONST_dd);
//				int indexOfHour = fromPattern.indexOf(CONST_HH);
//				int indexOfMinute = fromPattern.indexOf(CONST_mm);
//				int indexOfSecond = fromPattern.indexOf(CONST_ss);
//				
//				if (indexOfDay != -1) {
//					day = Integer.parseInt(dateString.substring(indexOfDay, indexOfDay + 2));
//				}
//				
//				if (indexOfMonth != -1) {
//					month = Integer.parseInt(dateString.substring(indexOfMonth, indexOfMonth + 2));
//				} 
//				
//				if (indexOfYear != -1) {
//					year = Integer.parseInt(dateString.substring(indexOfYear, indexOfYear + 4));
//				}
//				
//				if (indexOfHour != -1) {
//					hour = Integer.parseInt(dateString.substring(indexOfHour, indexOfHour + 2));
//				}
//				
//				if (indexOfMinute != -1) {
//					minute = Integer.parseInt(dateString.substring(indexOfMinute, indexOfMinute + 2));
//				}
//				
//				if (indexOfSecond != -1) {
//					second = Integer.parseInt(dateString.substring(indexOfSecond, indexOfSecond + 2));
//				}
//				
//				
//				
//				JalaliCalendar jCalendar = new JalaliCalendar(year, month, day);
//				Calendar calendar = jCalendar.toGregorian();
//				calendar.setTimeZone(TimeZone.getTimeZone(zoneId));
//				calendar.set(Calendar.HOUR_OF_DAY, hour);
//				calendar.set(Calendar.MINUTE, minute);
//				calendar.set(Calendar.SECOND, second);
//				calendar.set(Calendar.MILLISECOND, 0);
//				
//				Date date = calendar.getTime();
//				
//				Instant instant = date.toInstant();
//				
//				//ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneIdUtil.getDefault());
//				//utcDate = Date.from(zonedDateTime.withZoneSameInstant(zoneId).toInstant());
//				utcDate = Date.from(instant);
//				//LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
//				//utcDate = Date.from(ldt.atZone(ZoneIdUtil.getDefault()).toInstant());
//				
//			} else {
//				DateTimeFormatter dateTimeFormatter = null;
//				
//				if (Validator.isNull(fromPattern)) {
//					if(fromPattern.split(StringPool.SPACE).length == 2){
//						fromPattern = PATTERN_DATETIME;
//					} else {
//						fromPattern = PATTERN_DATE;
//					}
//				}
//				
//				dateTimeFormatter = DateTimeFormatter.ofPattern(fromPattern);//fromPattern = "yyyy-MM-dd HH:mm"
//				
//				LocalDateTime ldt = null;
//				
//				if(PATTERN_DATETIME.equals(fromPattern)){
//					ldt = LocalDateTime.parse(dateString, dateTimeFormatter);
//				} else {
//					LocalDate ld = LocalDate.parse(dateString, dateTimeFormatter);
//					ldt = ld.atTime(0,0,0,0);
//				}
//				
//				ZonedDateTime zonedDateTime = ZonedDateTime.of(ldt, zoneId);
//				
//				utcDate = Date.from(zonedDateTime.withZoneSameInstant(ZoneIdUtil.getDefault()).toInstant());
//			}
//			
//			
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//		
//		return utcDate;
//	}
//
//	/**
//	 * 
//	 * @param from
//	 * @param to
//	 * @return
//	 */
//	@Deprecated
//	public static int dayBetweenTwoDates(Date from, Date to) {
//		/*
//		 * Calendar fromCal = Calendar.getInstance(); fromCal.setTime(from);
//		 * 
//		 * Calendar toCal = Calendar.getInstance(); toCal.setTime(to);
//		 * 
//		 * return dayBetweenTwoDates(fromCal, toCal);
//		 */
//
//		//return Days.daysBetween(new DateTime(from), new DateTime(to)).getDays();
//		return getDaysBetween(from, to);
//	}
//
//	/**
//	 * 
//	 * @param from
//	 * @param to
//	 * @return
//	 */
//	@Deprecated
//	public static int dayBetweenTwoDates(Calendar from, Calendar to) {
//		/*
//		 * from.set(Calendar.HOUR_OF_DAY, 23); from.set(Calendar.MINUTE, 59);
//		 * from.set(Calendar.SECOND, 59); from.set(Calendar.MILLISECOND, 0);
//		 * 
//		 * to.set(Calendar.HOUR_OF_DAY, 23); to.set(Calendar.MINUTE, 59);
//		 * to.set(Calendar.SECOND, 59); to.set(Calendar.MILLISECOND, 0);
//		 * 
//		 * return (int) ((to.getTimeInMillis() - from.getTimeInMillis()) /
//		 * 86400000);
//		 */
//
//		//return dayBetweenTwoDates(from.getTime(), to.getTime());
//		return getDaysBetween(from.getTime(), to.getTime());
//	}
//	
//	public static LocalDate convertIntegerUtcDateToDate(Integer date) {
//		if(date == null)
//			return null;
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//		return LocalDate.parse(String.valueOf(date), formatter);
//	}
//
//	public static Integer convertUtcDateToInteger(LocalDate localDate) {
//		if(localDate == null)
//			return null;
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//		return Integer.valueOf(localDate.format(formatter));
//	}
//
//	@Deprecated
//	public static Date convertIntegerDateToDate(Integer date, Locale locale, ZoneId zoneId, String calendarType) {
//		String strDate = convertIntegerDateToString(date);
//		return convertStringToUTCDate(PATTERN_ISO__yyyyMMdd, strDate, locale, zoneId, calendarType);
//	}
//
//	/**
//	 * 
//	 * @param date
//	 *            in 20160206 format
//	 * @return string with ISO format. ex: 2016-02-06
//	 */
//	@Deprecated
//	public static String convertIntegerDateToString(Integer date) {
//		String strTemp = date.toString();
//		StringBuilder sb = new StringBuilder();
//		sb.append(strTemp.charAt(0));
//		sb.append(strTemp.charAt(1));
//		sb.append(strTemp.charAt(2));
//		sb.append(strTemp.charAt(3));
//		sb.append(StringPool.DASH);
//		sb.append(strTemp.charAt(4));
//		sb.append(strTemp.charAt(5));
//		sb.append(StringPool.DASH);
//		sb.append(strTemp.charAt(6));
//		sb.append(strTemp.charAt(7));
//		return sb.toString();
//	}
//
//	/**
//	 * 
//	 * @param date
//	 * @param locale
//	 * @param calendarType
//	 * @return
//	 */
//	public static Integer convertStringDateToInteger(String date, Locale locale, ZoneId zoneId, String calendarType) {
//		
//		if(calendarType.equals(CALENDAR_JALALI)){
//			//TODO
//			return null;
//			
//		} else {
//			Date tmpDate = convertStringToUTCDate(PATTERN_ISO__yyyyMMdd, date, locale, zoneId, calendarType);
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//			return Integer.parseInt(dateFormat.format(tmpDate.getTime()));
//		}
//		
//	}
//
//	/**
//	 * 
//	 * @param time
//	 * @return
//	 */
//	/*
//	 * public static Integer convertStringTimeToInteger(String time) {
//	 * SimpleDateFormat timeFormat = new SimpleDateFormat("hhmm"); return
//	 * Integer.parseInt(timeFormat.format(time)); }
//	 */
//	public static String getTimeFromStringDateTime(String strDateTime) {
//		return strDateTime.substring(11).concat("01").replace(":", "");
//	}
//
//	public static String formatTimeString(String strTime) {
//		StringBuilder sb = new StringBuilder(strTime);
//		sb.insert(2, StringPool.COLON);
//		if (strTime.length() > 5) {
//			sb.insert(5, StringPool.COLON);
//		}
//		return sb.toString();
//	}
//
//	public static int compareTo(Date date1, Date date2) {
//		if (date1 == date2) {
//			return 0;
//		}
//
//		if (date1 == null) {
//			return 1;
//		}
//
//		if (date2 == null) {
//			return -1;
//		}
//
//		return Long.compare(date1.getTime(), date2.getTime());
//	}
//
//	public static boolean equals(Date date1, Date date2) {
//		if (compareTo(date1, date2) == 0) {
//			return true;
//		}
//
//		return false;
//	}
//
//	/*public static String formatDate(String fromPattern, String dateString, Locale locale, String calendarType) throws ParseException {
//
//		Date dateValue = parseDate(fromPattern, dateString, locale , calendarType);
//
//		if(calendarType.equals(CALENDAR_JALALI)){
//			return convertToPersianDate(dateValue, DASH_SEPERATOR);
//			
//		} else {
//			
//			//Format dateFormat = FastDateFormatFactoryUtil.getDate(locale);
//			Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(PATTERN_DATE);
//			
//			return dateFormat.format(dateValue);
//		}
//	}*/
//	
//	public static LocalDate getCurrentDate(String calendarType, ZoneId zoneId) {
//		if(calendarType.equals(CALENDAR_JALALI)){
//			Calendar cal = Calendar.getInstance(TimeZoneUtil.getTimeZone(zoneId));
//			SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
//			persianCalendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//			DateFields dfs = persianCalendar.getDateFields();
//			String dateString = dfs.getYear() + DASH_SEPERATOR + ((dfs.getMonth() + 1) < 10 ? "0" : StringPool.BLANK)
//					+ (dfs.getMonth() + 1) + DASH_SEPERATOR + (dfs.getDay() < 10 ? "0" : StringPool.BLANK) + dfs.getDay();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_ISO__yyyyMMdd);
//			return LocalDate.parse(dateString, formatter);
//		} else {
//			return LocalDate.now(zoneId);
//		}
//	}
//
//	//XXX MO: why same function name returns two different types??????!!!!!!
//	public static String getCurrentDateTime(Locale locale, String calendarType, ZoneId zoneId) {
//		return getDateTime(new Date(), locale , calendarType, zoneId);
//	}
//	
//	public static LocalDateTime getCurrentDateTime(Locale locale, ZoneId zoneId) {
//		
//		Instant instant = Instant.now();
//		
//		return LocalDateTime.ofInstant(instant, zoneId);
//	}
//	
//	public static String getUtcDate(Date utcDate) {
//		
//		Instant instant = utcDate.toInstant();
//		
//		return LocalDateTime.ofInstant(instant, ZoneIdUtil.getDefault()).format(DATE_FORMATTER);
//	}
//	
//	public static String getUtcDate(LocalDate utcDate) {
//		
//		return utcDate.format(DATE_FORMATTER);
//	}
//	
//	public static String getUtcDateTime(Date utcDate) {
//		
//		Instant instant = utcDate.toInstant();
//		
//		return LocalDateTime.ofInstant(instant, ZoneIdUtil.getDefault()).format(DATETIME_FORMATTER);
//	}
//	
//	public static String getUtcDateTime(LocalDateTime utcDate) {
//		
//		return ZonedDateTime.of(utcDate, ZoneIdUtil.getDefault()).format(DATETIME_FORMATTER);
//	}
//	
//	public static String getDate(Date utcDate, Locale locale, String calendarType, ZoneId zoneId) {
//		Instant instant = utcDate.toInstant();
//		
//		if(calendarType.equals(CALENDAR_JALALI)){
//			return convertUtcToPersianDate(utcDate, locale, zoneId, DASH_SEPERATOR);
//			
//		} else {
//			
//			return LocalDateTime.ofInstant(instant, zoneId).format(DATE_FORMATTER);
//		}
//	}
//
//	public static String getDateTime(Date utcDate, Locale locale, String calendarType, ZoneId zoneId) {
//		Instant instant = utcDate.toInstant();
//		
//		if(calendarType.equals(CALENDAR_JALALI)){
//			return convertUtcToPersianDateTime(utcDate, locale, zoneId, DASH_SEPERATOR);
//			
//		} else {
//			
//			return LocalDateTime.ofInstant(instant, zoneId).format(DATETIME_FORMATTER);
//		}
//	}
//	
//	public static String getDate(LocalDate utcDate, Locale locale, String calendarType, ZoneId zoneId) {
//		if(calendarType.equals(CALENDAR_JALALI)){
//			return convertUtcToPersianDate(utcDate, locale, zoneId, DASH_SEPERATOR);
//			
//		} else {
//			
//			return utcDate.atStartOfDay(zoneId).format(DATE_FORMATTER);
//		}
//	}
//	
//	public static String getDate(LocalDateTime utcDate, Locale locale, String calendarType, ZoneId zoneId) {
//		//Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(pattern, locale);
//		//return dateFormat.format(date);
//		if(Validator.isNull(utcDate)){
//			return null;
//		}
//		
//		if(calendarType.equals(CALENDAR_JALALI)){
//			return convertUtcToPersianDate(utcDate, locale, zoneId, DASH_SEPERATOR);
//			
//		} else {
//			
//			ZonedDateTime zonedDateTime = ZonedDateTime.of(utcDate, ZoneIdUtil.getDefault());
//			zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
//			
//			return zonedDateTime.format(DATE_FORMATTER);
//		}
//	}
//	
//	public static String getDateTime(LocalDateTime utcDate, Locale locale, String calendarType, ZoneId zoneId) {
//		//Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(pattern, locale);
//		//return dateFormat.format(date);
//		
//		if(calendarType.equals(CALENDAR_JALALI)){
//			return convertUtcToPersianDateTime(utcDate, locale, zoneId, DASH_SEPERATOR);
//			
//		} else {
//			
//			ZonedDateTime zonedDateTime = ZonedDateTime.of(utcDate, ZoneIdUtil.getDefault());
//			zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
//			
//			//zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).format(DATETIME_FORMATTER)
//			
//			return zonedDateTime.withZoneSameInstant(zoneId).format(DATETIME_FORMATTER);
//		}
//	}
//	
//	public static String getTime(LocalDateTime utcDate, Locale locale, ZoneId zoneId) {
//		ZonedDateTime zonedDateTime = ZonedDateTime.of(utcDate, ZoneIdUtil.getDefault());
//		zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
//		
//		return zonedDateTime.withZoneSameInstant(zoneId).format(TIME_FORMATTER);
//	}
//	
//	public static int getDaysBetween(LocalDate from, LocalDate to) {
//		long days = ChronoUnit.DAYS.between(from, to);
//		
//		return (int) days;
//	}
//	
//	public static int getDaysBetween(LocalDateTime from, LocalDateTime to) {
//		long days = ChronoUnit.DAYS.between(from, to);
//		
//		return (int) days;
//	}
//
//	public static int getDaysBetween(Date date1, Date date2) {
//		return getDaysBetween(date1, date2, null);
//	}
//
//	public static int getDaysBetween(Date date1, Date date2, TimeZone timeZone) {
//
//		if (date1.after(date2)) {
//			Date tempDate = date1;
//
//			date1 = date2;
//			date2 = tempDate;
//		}
//
//		Calendar startCal = null;
//		Calendar endCal = null;
//
//		int offsetDate1 = 0;
//		int offsetDate2 = 0;
//
//		if (timeZone == null) {
//			startCal = new GregorianCalendar();
//			endCal = new GregorianCalendar();
//		} else {
//			startCal = new GregorianCalendar(timeZone);
//			endCal = new GregorianCalendar(timeZone);
//
//			offsetDate1 = timeZone.getOffset(date1.getTime());
//			offsetDate2 = timeZone.getOffset(date2.getTime());
//		}
//
//		startCal.setTime(date1);
//
//		startCal.add(Calendar.MILLISECOND, offsetDate1);
//
//		endCal.setTime(date2);
//
//		endCal.add(Calendar.MILLISECOND, offsetDate2);
//
//		int daysBetween = 0;
//
//		while (CalendarUtil.beforeByDay(startCal.getTime(), endCal.getTime())) {
//			startCal.add(Calendar.DAY_OF_MONTH, 1);
//
//			daysBetween++;
//		}
//
//		return daysBetween;
//	}
//
//	/*public static DateFormat getISO8601Format() {
//		return DateFormatFactoryUtil.getSimpleDateFormat(ISO_8601_PATTERN);
//	}
//
//	public static DateFormat getISOFormat() {
//		return getISOFormat(StringPool.BLANK);
//	}
//
//	public static DateFormat getISOFormat(String text) {
//		String pattern = StringPool.BLANK;
//
//		if (text.length() == 8) {
//			pattern = "yyyyMMdd";
//		} else if (text.length() == 12) {
//			pattern = "yyyyMMddHHmm";
//		} else if (text.length() == 13) {
//			pattern = "yyyyMMdd'T'HHmm";
//		} else if (text.length() == 14) {
//			pattern = "yyyyMMddHHmmss";
//		} else if (text.length() == 15) {
//			pattern = "yyyyMMdd'T'HHmmss";
//		} else if ((text.length() > 8) && (text.charAt(8) == 'T')) {
//			pattern = "yyyyMMdd'T'HHmmssz";
//		} else {
//			pattern = "yyyyMMddHHmmssz";
//		}
//
//		return DateFormatFactoryUtil.getSimpleDateFormat(pattern);
//	}
//
//	public static DateFormat getUTCFormat() {
//		return getUTCFormat(StringPool.BLANK);
//	}
//
//	public static DateFormat getUTCFormat(String text) {
//		String pattern = StringPool.BLANK;
//
//		if (text.length() == 8) {
//			pattern = "yyyyMMdd";
//		} else if (text.length() == 12) {
//			pattern = "yyyyMMddHHmm";
//		} else if (text.length() == 13) {
//			pattern = "yyyyMMdd'T'HHmm";
//		} else if (text.length() == 14) {
//			pattern = "yyyyMMddHHmmss";
//		} else if (text.length() == 15) {
//			pattern = "yyyyMMdd'T'HHmmss";
//		} else {
//			pattern = "yyyyMMdd'T'HHmmssz";
//		}
//
//		return DateFormatFactoryUtil.getSimpleDateFormat(pattern, TimeZoneUtil.getTimeZone(StringPool.UTC));
//	}*/
//
//	/*public static boolean isFormatAmPm(Locale locale) {
//		Boolean formatAmPm = _formatAmPmMap.get(locale);
//
//		if (formatAmPm == null) {
//			SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.SHORT, locale);
//
//			String pattern = simpleDateFormat.toPattern();
//
//			formatAmPm = pattern.contains("a");
//
//			_formatAmPmMap.put(locale, formatAmPm);
//		}
//
//		return formatAmPm;
//	}*/
//
//	public static Date newDate() {
//		return new Date();
//	}
//
//	public static Date newDate(long date) {
//		return new Date(date);
//	}
//
//	public static long newTime() {
//		Date date = new Date();
//
//		return date.getTime();
//	}
//
//	public static Date parseDateTime(String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//
//		return parseDateTime(PATTERN_DATETIME, dateString, locale, zoneId, calendarType);
//	}
//
//	public static Date parseDateTime(String fromPattern, String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//		return convertStringToUTCDateTime(fromPattern, dateString, locale, zoneId, calendarType);
//	}
//	
//	public static Date parseDate(String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//		
//		return parseDate(PATTERN_DATE, dateString, locale, zoneId, calendarType);
//	}
//	
//	public static Date parseDate(String fromPattern, String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//		return convertStringToUTCDate(fromPattern, dateString, locale, zoneId, calendarType);
//	}
//	
//	public static LocalDate parseLocalDate(String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//		
//		return parseLocalDate(PATTERN_DATE, dateString, locale, zoneId, calendarType);
//	}
//	
//	public static LocalDate parseLocalDate(String fromPattern, String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//		Date date = parseDate(fromPattern, dateString, locale, zoneId, calendarType);
//		
//		return convertToLocalDate(date);
//	}
//	
//	public static LocalDateTime parseLocalDateTime(String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//		
//		return parseLocalDateTime(PATTERN_DATETIME, dateString, locale, zoneId, calendarType);
//	}
//	
//	public static LocalDateTime parseLocalDateTime(String fromPattern, String dateString, Locale locale, ZoneId zoneId, String calendarType) throws ParseException {
//		Date date = parseDateTime(fromPattern, dateString, locale, zoneId, calendarType);
//		
//		return convertToLocalDateTime(date);
//	}
//	
//	public static LocalDate convertToLocalDate(Date date){
//		if(date == null){
//			return null;
//		}
//		
//		Instant instant = date.toInstant();
//		 ZoneId defaultZoneId = ZoneId.systemDefault();
//		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, defaultZoneId);
//		
//		return localDateTime.toLocalDate();
//	}
//	
//	public static LocalDateTime convertToLocalDateTime(Date date){
//		if(date == null){
//			return null;
//		}
//		
//		Instant instant = date.toInstant();
//		
//		return LocalDateTime.ofInstant(instant, ZoneIdUtil.getDefault());
//	}
//	
//	//private static final Map<Locale, Boolean> _formatAmPmMap = new HashMap<>();
//	
//	public static void main(String[] args) {
//		String dateStr ;
//		String dateTimeStr;
//		String languageStr;
//		String timeZone;
//		String calendar;
//		LocalDateTime utcLocalDateTime;
//		
//		try {
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//			
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "1395-04-01";
//			calendar = DateUtil.CALENDAR_JALALI;
//			dateTimeStr = dateStr + " 23:59";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//			
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "1395-04-01";
//			calendar = DateUtil.CALENDAR_JALALI;
//			dateTimeStr = dateStr + " 23:59:59";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME_yyyyMMdd_HHmmss,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//			
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "1395-04-01";
//			calendar = DateUtil.CALENDAR_JALALI;
//			dateTimeStr = dateStr + " 00:00";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//			
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "1395-04-01";
//			calendar = DateUtil.CALENDAR_JALALI;
//			dateTimeStr = dateStr + " 00:00:01";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME_yyyyMMdd_HHmmss,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//			
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "2016-08-11";
//			calendar = DateUtil.CALENDAR_GREGORIAN;
//			dateTimeStr = dateStr + " 23:59";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//			
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "2016-08-11";
//			calendar = DateUtil.CALENDAR_GREGORIAN;
//			dateTimeStr = dateStr + " 23:59:59";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME_yyyyMMdd_HHmmss,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//			
//
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "2016-08-11";
//			calendar = DateUtil.CALENDAR_GREGORIAN;
//			dateTimeStr = dateStr + " 00:00";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//			
//
//			languageStr = "fa";
//			timeZone = "Asia/Tehran";
//			dateStr = "2016-08-11";
//			calendar = DateUtil.CALENDAR_GREGORIAN;
//			dateTimeStr = dateStr + " 00:00:01";
//			System.out.println("LOCAL: " + dateTimeStr);
//			utcLocalDateTime = DateUtil.parseLocalDateTime(
//					DateUtil.PATTERN_DATETIME_yyyyMMdd_HHmmss,
//					dateTimeStr, 
//					LocaleUtil.fromLanguageId(languageStr), 
//					ZoneIdUtil.getZoneId(timeZone), 
//					calendar);
//			System.out.println("UTC:   " + dtf.format(utcLocalDateTime));
//			System.out.println();
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}