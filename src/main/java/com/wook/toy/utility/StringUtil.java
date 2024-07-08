/**
 *
 */
package com.wook.toy.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.MathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dykim
 *
 */
public class StringUtil {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(StringUtil.class);

	private StringUtil() {
	}

	public static String replace(String s, String old, String replacement) {
		int i = s.indexOf(old);
		StringBuffer r = new StringBuffer();

		if (i == -1) {
			return s;
		}

		r.append(s.substring(0, i) + replacement);

		if ((i + old.length()) < s.length()) {
			r.append(replace(s.substring(i + old.length(), s.length()), old,
					replacement));
		}

		return r.toString();
	}


	public static String toSqlString(String s) {
		return replace(s, "'", "''");
	}


	public static String nToBr(String s) {
		if(s == null) {
			return "";
		}
		return replace(s, "\n", "<br>");
	}

	public static boolean CheckNumber(Object object) {
		try {
			if(object != null  && object.getClass() == String.class) {
				new BigDecimal(object.toString());
			}
			return true;
		} catch (NumberFormatException ex) {
			return false;
	    }
	}

	public static boolean CheckBigDecimal(Object object) {
		try {
			if(object != null  && object.getClass() == String.class) {
				new BigDecimal(object.toString());
			}
			return true;
		} catch (NumberFormatException ex) {
			return false;
	    }
	}


	public static int parseInt(Object object, int defaultValue) {
		int resultValue = 0;
		try {
			if(object == null ) {
				return defaultValue;
			}
			else if(object.getClass() == String.class) {
				resultValue = parseInt((String)object);
			}
			else if(object.getClass() == Long.class) {
				resultValue = parseInt((Long)object);
			}
			else if(object.getClass() == BigDecimal.class) {
				resultValue = parseInt((BigDecimal)object);
			}
		}catch (MathException e) {
			LOG.debug("parseInt.MathException 발생하였습니다.");
			return defaultValue;
		}
		return resultValue;
	}

	public static int parseInt(Long s) {
		try {
			return Math.toIntExact(s);
		}catch (MathException e) {
			LOG.debug("parseInt.MathException 발생하였습니다.");
		}
		return 0;
	}

	public static int parseInt(BigDecimal s) {
		try {
			if(s != null) {
				return s.intValue();
			}else {
				return 0;
			}
		}catch (MathException e) {
			LOG.debug("parseInt.MathException 발생하였습니다.");
		}
		return 0;
	}


	public static int parseInt(String s) {
		return parseInt(s, -999);
	}

	public static int parseInt(String str, int defaultValue) {
		int result = defaultValue;

		if (str == null) {
			return defaultValue;
		}

		String tempStr = str.trim();

		try {
			result = Integer.parseInt(tempStr);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}

		return result;
	}

	public static long parseLong(String s) {
		return parseLong(s, -999);
	}

	public static long parseLong(String str, long defaultValue) {
		long result = defaultValue;

		if (str == null) {
			return defaultValue;
		}

		String tempStr = str.trim();

		try {
			result = Long.parseLong(tempStr);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}

		return result;
	}

	public static double parseDouble(String s) {
		return parseDouble(s, -999);
	}

	public static double parseDouble(String str, double defaultValue) {
		double result = defaultValue;

		if (str == null) {
			return defaultValue;
		}

		String tempStr = str.trim();
		tempStr = replace(tempStr, "O", "0");

		try {
			result = Double.parseDouble(tempStr);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}

		return result;
	}

	public static boolean parseBoolean(String s, String trueValue) {
		return trueValue != null && trueValue.equals(s);
	}

	public static boolean parseBoolean(String s) {
		return parseBoolean(s, "Y");
	}

	public static String getNotNullAndTrimedString(String s) {
		return s == null ? "" : s.trim();
	}

	public static String intToString(int n) {
		if(n == -999) {
			return "";
		}
		return n + "";
	}

	public static String doubleToString(double d) {
		if(d == -999.0 || d == -999 || d == -99.9) {
			return "";
		}
		return d + "";
	}

	public static String absIntToString(int n) {
		if(n == -999) {
			return "";
		}
		return Math.abs(n) + "";
	}


	public static String nvlString(String param, String defaultvalue){
		if ( param == null || param.equals("null"))
			return defaultvalue;
		else
			return param;
	}



	public static String getTruncatedText(String str, int len) {
		if (str == null || "".equals(str)) {
			return "...";
		}
		int slen = 0, blen = 0;
		char c;

		String tempStr = str;
		try {
			if (str.getBytes("MS949").length > len) {
				while (blen + 1 < len) {
					c = str.charAt(slen);
					blen++;
					slen++;
					if (c > 127) {
						blen++; //2-byte character..
					}
				}
				tempStr =  str.substring(0, slen) + "...";
			}
		} catch (java.io.UnsupportedEncodingException e) {
			tempStr = "";
		}
		return tempStr;
	}

	public static String substringByBytes(String str, int beginBytes, int endBytes) {
	    if (str == null || str.length() == 0) {
	        return "";
	    }

	     if (beginBytes < 0) {
	        beginBytes = 0;
	    }

	    if (endBytes < 1) {
	        return "";
	    }

	    int len = str.length();

	    int beginIndex = -1;
	    int endIndex = 0;

	    int curBytes = 0;
	    String ch = null;
	    for (int i = 0; i < len; i++) {
	        ch = str.substring(i, i + 1);
	        curBytes += ch.getBytes().length;


	        if (beginIndex == -1 && curBytes >= beginBytes) {
	            beginIndex = i;
	        }

	        if (curBytes > endBytes) {
	            break;
	        } else {
	            endIndex = i + 1;
	        }
	    }
	    return str.substring(beginIndex, endIndex);
	}


	public static String nvl(String src) {
		if(src == null || src.equals("null")) {
			return "";
		}
		return src;
	}

	public static String nvl(String src, String replace) {
		if(src == null) {
			return replace;
		}
		return src;
	}

	public static String nvl(Object src) {
		if(src == null) {
			return "&nbsp;";
		}
		return src.toString();
	}

	public static String nvl(Object src, String replace) {
		if(src == null) {
			return replace;
		}
		return src.toString();
	}

	public static String nvl(Map src, String keyName, String replace) {
		if(src == null || src.get(keyName) == null) {
			return replace;
		}
		else {
			return nvl(src.get(keyName), replace);
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] getArrayString(String str, String strToken) {
		Vector vec = new Vector();
		StringTokenizer st = new StringTokenizer(str, strToken);
		while(st.hasMoreTokens()){
			vec.addElement((String)st.nextToken());
		}
		return (String[])vec.toArray(new String[0]);
	}

	public static String round(double dblValue, int nDigit) {
		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(new DecimalFormatSymbols());
		df.setMinimumIntegerDigits(1); //
		df.setMaximumFractionDigits(nDigit); //
		df.setMinimumFractionDigits(nDigit); //
		df.setGroupingUsed(false);
		return df.format(dblValue);
	}

	public static String trim(String param){
		return trim(param,"");
	}

	public static String trim(String param, String defaultvalue){
		if ( param == null || param.trim().equals(""))
			return defaultvalue;
		else
			return param.trim();
	}

	public static String dtos(String strDate) {
		return dtos(strDate, ".");
	}
	public static String dtos(String strDate, String strValue) {
		return dtos(strDate, strValue, strValue, "");
	}
	public static String dtos(String strDate, String strYY, String strMM, String strDD) {
		String tempDate = trim(strDate);
		if (tempDate.length() != 8)
			return tempDate;
		return (tempDate.substring(0,4) + strYY + tempDate.substring(4,6) + strMM + tempDate.substring(6,8) + strDD);
	}


	public static String substring(String src, int ix1, int ix2) {
		if(src == null || src.length() < ix2) {
			return "";
		}

		return src.substring(ix1, ix2);
	}

	public static String substring(String src, int ix) {
		if(src == null || src.length() < ix) {
			return "";
		}
		return src.substring(ix);
	}

	/**
	 * @param str
	 * @return blank 문자열인지 여부
	 */
	public static boolean isBlank(String str) {

		return StringUtils.isBlank(str);
	}

	/**
	 * @param str
	 * @return empty 문자열인지 여부
	 */
	public static boolean isEmpty(String str) {

		return StringUtils.isEmpty(str);
	}

	/**
	 * blank 문자열이 있는지 체크한다.
	 *
	 * @param strArray
	 * @return blank 문자열이 있으면 true, 없으면 false
	 */
	public static boolean checkBlank(String... strArray) {

		for (String str : strArray) {
			if (StringUtils.isBlank(str)) {
				return true;
			}
		}

		return false;
	}

	// + - 월
	public static String getIncrementMonth(String yyyymm, int inc) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, Integer.parseInt(yyyymm.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(yyyymm.substring(4, 6)) - 1);
		cal.add(Calendar.MONTH, inc);

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");
		return df4.format(cal.get(Calendar.YEAR)) + df2.format(cal.get(Calendar.MONTH) + 1);
	}

	// 월 차이
	public static int getMonthDiff(String startMonth, String endMonth) {
		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		sCal.clear();
		eCal.clear();
		sCal.set(Calendar.YEAR, Integer.parseInt(startMonth.substring(0, 4)));
		sCal.set(Calendar.MONTH, Integer.parseInt(startMonth.substring(4, 6)) - 1);
		eCal.set(Calendar.YEAR, Integer.parseInt(endMonth.substring(0, 4)));
		eCal.set(Calendar.MONTH, Integer.parseInt(endMonth.substring(4, 6)) - 1);

		int yearDiff  = eCal.get(Calendar.YEAR)  - sCal.get(Calendar.YEAR);
		int monthDiff = eCal.get(Calendar.MONTH) - sCal.get(Calendar.MONTH);
		return yearDiff * 12 + monthDiff;
	}

	// 천단위 comma 삽입
	public static String getDecimalFormat(double numberValue){
		DecimalFormat df = new DecimalFormat("###,###");

		return df.format(numberValue);
	}

	//
	public static int getByteSize(String str) {
		int enSize = 0;
		int koSize = 0;
		int etcSize = 0;

		char[] charArr = str.toCharArray();

		for (int i = 0; i < charArr.length; ++i) {
			if (charArr[i] >='A' && charArr[i] <= 'Z') {
				++enSize;
			} else if (charArr[i]>='\uAC00' && charArr[i]<='\uD7A3') {
				++koSize;
				++koSize;
			} else {
				++etcSize;
			}
		}
		return (enSize + koSize + etcSize);
	}

	// numeric check
	@SuppressWarnings("unused")
	public static boolean isNumeric(String str)   {
		try  {
			double d = Double.parseDouble(str);
		 } catch(NumberFormatException nfe) {
			return false;
		 }
		return true;
	}

	public static String clobToString(Clob clob) throws  IOException, SQLException {
		if (clob == null) {
			return "";
		}

		BufferedReader br = null;
		StringBuffer strOut = new StringBuffer();
		String str = "";
		try {
			br = new BufferedReader(clob.getCharacterStream());
			while (true) {
				str = br.readLine();
				if (str == null ) break;
				strOut.append(str);
			}
			br.close();
		} catch (IOException e) {
			strOut = new StringBuffer();
		} finally {
			if (br != null) {
				br.close();
			}
		}

		return strOut.toString();
	}

	public static String rPad(String str, int size, String fStr){
		String returnStr = "";
		byte[] b = str.getBytes();
		int len = b.length;
		int tmp = size - len;

		for (int i=0; i < tmp ; i++){
			returnStr += fStr;
		}
		return returnStr;
	}

	public static String lPad(String str, int size, String fStr){
		String  returnStr = "";
		byte[] b = str.getBytes();
		int len = b.length;
		int tmp = size - len;

		for (int i=0; i < tmp ; i++){
			returnStr = fStr + returnStr;
		}
		return returnStr;
	}



	public static String getPhoneFormat(String str){
		if (str == null) {
			return "";
		}

		if (str.trim().length() == 8) {
			return str.trim().replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (str.trim().length() == 12) {
			return str.trim().replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		} else if (str.trim().length() == 10) {
			if("02".equals(str.substring(0, 2))) {													//서울 지역번호의 경우
				return str.trim().replaceFirst("(^[0-9]{2})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
			} else {																				//그외 지역번호의 경우
				return str.trim().replaceFirst("(^[0-9]{3})([0-9]{3})([0-9]{4})$", "$1-$2-$3");
			}
		}
		return str.trim().replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	}


	public static boolean checkExp(String checkMenu, String str){

		String idPattern = "^[a-zA-Z]{1}[a-zA-Z0-9_]{3,11}$";
		String pPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$";
		String telnoPattern = "^0?([0-9]{1,2})-?([0-9]{3,4})-?([0-9]{4})$";
		String mblnoPattern = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$";
		String emailPattern = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

		Pattern patten = null ;
		if("passWord".equals(checkMenu)){
			patten = Pattern.compile(pPattern);
		}
		else if("id".equals(checkMenu)){
			patten = Pattern.compile(idPattern);
		}
		else if("email".equals(checkMenu)){
			patten = Pattern.compile(emailPattern);
		}
		else if("telno".equals(checkMenu)){
			patten = Pattern.compile(telnoPattern);
		}
		else if("mblno".equals(checkMenu)){
			patten = Pattern.compile(mblnoPattern);
		}
		else {
			return false;
		}

		if(patten != null ) {
			Matcher match = patten.matcher(str);

			if(match.matches()){
				return true;
			}
		}
		return false;
	}

	public  static String getOnlyNumber(String str){
		if(str == null ) return null;
		return str.replaceAll("[^0-9]", "");
	}

	public static boolean isStringInteger(String stringToCheck, int radix) {
        if(stringToCheck.isEmpty()) return false;           //Check if the string is empty
        for(int i = 0; i < stringToCheck.length(); i++) {
            if(i == 0 && stringToCheck.charAt(i) == '-') {     //Check for negative Integers
                if(stringToCheck.length() == 1) return false;
                else continue;
            }
            if(Character.digit(stringToCheck.charAt(i),radix) < 0) return false;
        }
        return true;
    }

	public static boolean CheckDataeFormat(String str, String yyyymmdd) {
		boolean returnVal = true;
		String baseFormat = "yyyyMMdd";
		try {
			if(yyyymmdd != null && !"".equals(yyyymmdd)) {
				baseFormat = yyyymmdd;
			}

			if(str == null || str.length() != baseFormat.length()) {
				returnVal = false;
			}
			else {
				SimpleDateFormat sdf = new SimpleDateFormat(baseFormat, Locale.KOREA);
				sdf.setLenient(false);
				sdf.parse(str);
				returnVal = true;
			}
		} catch(ParseException e) {
			returnVal = false;
		} catch(Exception e) {
			returnVal = false;
		}
		return returnVal;
	}

	public static String getPostDataString(HashMap<String, Object> param) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(HashMap.Entry<String, Object> entry : param.entrySet()){
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            try {
				result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
				result.append("=");
	            result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				return new String();
			} catch (Exception e) {
				return new String();
			}
        }

        return result.toString();
    }

	/**
	 * snake_case TO camelCase
	 * @Method Name		: toCamel
	 * @Class Name		: StringUtil
	 * @Description		: 스네이크케이스에서 카멜케이스로 변경
	 * @Author			: SIDMS-DEV-PKY
	 * @Since			: 2023. 7. 18. 오후 1:45:15
	 * @param snake_case
	 * @return
	 */
	public static String toCamel(String snake_case) {
        StringBuilder builder
            = new StringBuilder(snake_case);

        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {

                builder.deleteCharAt(i);
                builder.replace(
                    i, i + 1,
                    String.valueOf(
                        Character.toUpperCase(
                            builder.charAt(i))));
            }
        }

        return builder.toString();
	}

	/**
	 * 테이블명을 underscore에서 camel로 변환한다
	 * @param tblNm String
	 * @param startUppperYn boolean	첫글자 대문자 여부
	 * @return String
	 */
	public static String underscoreToCame(String str, boolean startUppperYn) {
		StringBuilder converted = new StringBuilder();
		boolean nextUpper = false;
		if(str != null) {
			for(int i = 0; i < str.length(); i++) {
				char currentChar = str.charAt(i);
				if (i == 0 && startUppperYn) {
					converted.append(Character.toUpperCase(currentChar));
				} else  if (currentChar == '_') {
					nextUpper = true;
				} else {
					if (nextUpper) {
						converted.append(Character.toUpperCase(currentChar));
						nextUpper = false;
					} else {
						converted.append(Character.toLowerCase(currentChar));
					}
				}
			}
		}

		return converted.toString();
	}

	/**
	 * camelCase TO snake_case
	 * @Method Name		: toSnakeCase
	 * @Class Name		: StringUtil
	 * @Description		: 카멜케이스에서 스네이크케이스로 변경
	 * @Author			: SIDMS-DEV-PKY
	 * @Since			: 2023. 7. 18. 오후 2:16:41
	 * @param camelCase
	 * @return
	 */
	public static String toSnakeCase(String camelCase) {
        Matcher matcher = Pattern.compile("([a-z])([A-Z])").matcher(camelCase);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String format = String.format(
                    "%s_%s",
                    matcher.group(1),
                    matcher.group(2)
            );
            matcher.appendReplacement(sb,format);
        }
        StringBuffer stringBuffer = matcher.appendTail(sb);
        String convert = stringBuffer.toString().toLowerCase();

        return convert;
	}

}
