package match.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    static final SimpleDateFormat yyyyMMdd_format = new SimpleDateFormat("yyyyMMdd");
    static final SimpleDateFormat yyyyMMddHHmmss_format = new SimpleDateFormat("yyyyMMddHHmmss");
	
    
    /***
     * 字符串转日期
     * @param yyyyMMdd
     * @return
     */
    public static Date string2yyyyMMdd(String yyyyMMdd){
    	try {
			return new java.sql.Date(yyyyMMdd_format.parse(yyyyMMdd).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    /***
     * 字符串转日期
     * @param yyyyMMddHHmmss
     * @return
     */
    public static Timestamp string2yyyyMMddHHmmss(String yyyyMMddHHmmss){
    	try {
			return new java.sql.Timestamp(yyyyMMddHHmmss_format.parse(yyyyMMddHHmmss).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}
