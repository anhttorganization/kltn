package vn.edu.vnua.dse.stcalendar.vo;

import java.sql.Date;

@SuppressWarnings("serial")
public class MySqlDate extends Date {

	@SuppressWarnings("deprecation")
	public MySqlDate(int year, int month, int day) {
		super(year, month, day);
		System.out.println("-----------------------");
		System.out.println(new Date(year, month, day));
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	public MySqlDate() {
		super(0, 0, 0);		
	}

	public String toString() {
		@SuppressWarnings("deprecation")
		int year = super.getYear() + 1900;
		@SuppressWarnings("deprecation")
		int month = super.getMonth() + 1;
		@SuppressWarnings("deprecation")
		int day = super.getDate();
		
		char buf[] = new char[10];
        formatDecimalInt(day, buf, 0, 2);
        buf[2] = '/';
        formatDecimalInt(month, buf, 3, 2);
        buf[5] = '/';
        formatDecimalInt(year, buf, 6, 4);

        return new String(buf);
	}
	
	//copy
    static void formatDecimalInt(int val, char[] buf, int offset, int len) {
        int charPos = offset + len;
        do {
            buf[--charPos] = (char)('0' + (val % 10));
            val /= 10;
        } while (charPos > offset);
    }
}


