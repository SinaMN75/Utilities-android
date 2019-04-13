package com.developersian.util.persian;
import java.text.*;
import java.util.*;
public class PersianDate{
public static final int FARVARDIN=1;
public static final int ORDIBEHEST=2;
public static final int KHORDAD=3;
public static final int TIR=4;
public static final int MORDAD=5;
public static final int SHAHRIVAR=6;
public static final int MEHR=7;
public static final int ABAN=8;
public static final int AZAR=9;
public static final int DAY=10;
public static final int BAHMAN=11;
public static final int ESFAND=12;
public static final int AM=1;
public static final int PM=2;
public static final String AM_SHORT_NAME="ق.ظ";
public static final String PM_SHORT_NAME="ب.ظ";
public static final String AM_NAME="قبل از ظهر";
public static final String PM_NAME="بعد از ظهر";
private Long timeInMilliSecond;
private int shYear;
private int shMonth;
private int shDay;
private int grgYear;
private int grgMonth;
private int grgDay;
private int hour;
private int minute;
private int second;
private int[][] grgSumOfDays={{0,31,59,90,120,151,181,212,243,273,304,334,365},{0,31,60,91,121,152,182,213,244,274,305,335,366}};
private int[][] hshSumOfDays={{0,31,62,93,124,155,186,216,246,276,306,336,365},{0,31,62,93,124,155,186,216,246,276,306,336,366}};
private String[] dayNames={"شنبه","یک‌شنبه","دوشنبه","سه‌شنبه","چهارشنبه","پنج‌شنبه","جمعه"};
private String[] monthNames={"فروردین","اردیبهشت","خرداد","تیر","مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند"};
public PersianDate(){
	this.timeInMilliSecond=new Date().getTime();
	this.changeTime();
}
public PersianDate(Long timeInMilliSecond){
	this.timeInMilliSecond=timeInMilliSecond;
	this.changeTime();
}
public PersianDate(Date date){
	this.timeInMilliSecond=date.getTime();
	this.changeTime();
}
public static boolean isJalaliLeap(int year){
	return (new PersianDate().isLeap(year));
}
public static boolean isGrgLeap(int year){
	return (new PersianDate().grgIsLeap(year));
}
public static PersianDate today(){
	PersianDate pdate=new PersianDate();
	pdate.setHour(0).setMinute(0).setSecond(0);
	return pdate;
}
public static PersianDate tomorrow(){
	PersianDate pdate=new PersianDate();
	pdate.addDay(1);
	pdate.setHour(0).setMinute(0).setSecond(0);
	return pdate;
}
public int getShYear(){
	return shYear;
}
public PersianDate setShYear(int shYear){
	this.shYear=shYear;
	this.prepareDate2(this.getShYear(),this.getShMonth(),this.getShDay());
	return this;
}
public int getShMonth(){
	return shMonth;
}
public PersianDate setShMonth(int shMonth){
	this.shMonth=shMonth;
	this.prepareDate2(this.getShYear(),this.getShMonth(),this.getShDay());
	return this;
}
public int getShDay(){
	return shDay;
}
public PersianDate setShDay(int shDay){
	this.shDay=shDay;
	this.prepareDate2(this.getShYear(),this.getShMonth(),this.getShDay());
	return this;
}
public int getGrgYear(){
	return grgYear;
}
public PersianDate setGrgYear(int grgYear){
	this.grgYear=grgYear;
	prepareDate();
	return this;
}
public int getGrgMonth(){
	return grgMonth;
}
public PersianDate setGrgMonth(int grgMonth){
	this.grgMonth=grgMonth;
	prepareDate();
	return this;
}
public int getGrgDay(){
	return grgDay;
}
public PersianDate setGrgDay(int grgDay){
	this.grgDay=grgDay;
	prepareDate();
	return this;
}
public int getHour(){
	return hour;
}
public PersianDate setHour(int hour){
	this.hour=hour;
	prepareDate();
	return this;
}
public int getMinute(){
	return minute;
}
public PersianDate setMinute(int minute){
	this.minute=minute;
	prepareDate();
	return this;
}
public int getSecond(){
	return second;
}
public PersianDate setSecond(int second){
	this.second=second;
	prepareDate();
	return this;
}
public PersianDate initGrgDate(int year,int month,int day){
	return this.initGrgDate(year,month,day,0,0,0);
}
public PersianDate initGrgDate(int year,int month,int day,int hour,int minute,int second){
	this.grgYear=year;
	this.grgMonth=month;
	this.grgDay=day;
	this.hour=hour;
	this.minute=minute;
	this.second=second;
	this.setGrgYear(year).setGrgMonth(month).setGrgDay(day).setHour(hour).setMinute(minute).setSecond(second);
	int[] convert=this.toJalali(year,month,day);
	this.shYear=convert[0];
	this.shMonth=convert[1];
	this.shDay=convert[2];
	this.setShYear(convert[0]).setShMonth(convert[1]).setShDay(convert[2]);
	return this;
}
public PersianDate initJalaliDate(int year,int month,int day){
	return this.initJalaliDate(year,month,day,0,0,0);
}
public PersianDate initJalaliDate(int year,int month,int day,int hour,int minute,int second){
	this.setShYear(year).setShMonth(month).setShDay(day).setHour(hour).setMinute(minute).setSecond(second);
	int[] convert=this.toGregorian(year,month,day);
	this.setGrgYear(convert[0]).setGrgMonth(convert[1]).setGrgDay(convert[2]);
	return this;
}
private PersianDate prepareDate2(int year,int month,int day){
	int[] convert=this.toGregorian(year,month,day);
	this.grgYear=convert[0];
	this.grgMonth=convert[1];
	this.setGrgDay(convert[2]);
	return this;
}
private void prepareDate(){
	String dtStart=""+this.textNumberFilter(""+this.getGrgYear())+"-"+this.textNumberFilter(""+this.getGrgMonth())+"-"+this.textNumberFilter(""+this.getGrgDay())+"T"+this.textNumberFilter(""+this.getHour())+":"+this.textNumberFilter(""+this.getMinute())+":"+this.textNumberFilter(""+this.getSecond())+"Z";
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	int[] convert=this.toJalali(this.getGrgYear(),this.getGrgMonth(),this.getGrgDay());
	this.shYear=convert[0];
	this.shMonth=convert[1];
	this.shDay=convert[2];
	Date date=null;
	try{
		date=format.parse(dtStart);
		this.timeInMilliSecond=date.getTime();
	}catch(ParseException e){
		e.printStackTrace();
	}
}
public Long getTime(){
	return this.timeInMilliSecond;
}
public boolean grgIsLeap(int Year){
	return ((Year%4)==0&&((Year%100)!=0||(Year%400)==0));
}
public boolean isLeap(){
	return this.isLeap(this.shYear);
}
public boolean isLeap(int year){
	double referenceYear=1375;
	double startYear=1375;
	double yearRes=year-referenceYear;
	if(yearRes>0){
		if(yearRes >= 33){
			double numb=yearRes/33;
			startYear=referenceYear+Math.floor(numb)*33;
		}
	}else{
		if(yearRes >= -33){
			startYear=referenceYear-33;
		}else{
			double numb=Math.abs(yearRes/33);
			startYear=referenceYear-(Math.floor(numb)+1)*33;
		}
	}
	double[] leapYears={startYear,startYear+4,startYear+8,startYear+16,startYear+20,startYear+24,startYear+28,startYear+33};
	return (Arrays.binarySearch(leapYears,year)) >= 0;
}
public int[] toJalali(int year,int month,int day){
	int hshDay=0;
	int hshMonth=0;
	int hshElapsed;
	int hshYear=year-621;
	boolean grgLeap=this.grgIsLeap(year);
	boolean hshLeap=this.isLeap(hshYear-1);
	int grgElapsed=grgSumOfDays[(grgLeap?1:0)][month-1]+day;
	int XmasToNorooz=(hshLeap&&grgLeap)?80:79;
	if(grgElapsed<=XmasToNorooz){
		hshElapsed=grgElapsed+286;
		hshYear--;
		if(hshLeap&&!grgLeap)
			hshElapsed++;
	}else{
		hshElapsed=grgElapsed-XmasToNorooz;
		hshLeap=this.isLeap(hshYear);
	}
	if(year >= 2029&&(year-2029)%4==0){
		hshElapsed++;
	}
	for(int i=1;i<=12;i++){
		if(hshSumOfDays[(hshLeap?1:0)][i] >= hshElapsed){
			hshMonth=i;
			hshDay=hshElapsed-hshSumOfDays[(hshLeap?1:0)][i-1];
			break;
		}
	}
	int[] ret={hshYear,hshMonth,hshDay};
	return ret;
}
public int[] toGregorian(int year,int month,int day){
	int grgYear=year+621;
	int grgDay=0;
	int grgMonth=0;
	int grgElapsed;
	boolean hshLeap=this.isLeap(year);
	boolean grgLeap=this.grgIsLeap(grgYear);
	int hshElapsed=hshSumOfDays[hshLeap?1:0][month-1]+day;
	if(month>10||(month==10&&hshElapsed>286+(grgLeap?1:0))){
		grgElapsed=hshElapsed-(286+(grgLeap?1:0));
		grgLeap=grgIsLeap(++grgYear);
	}else{
		hshLeap=this.isLeap(year-1);
		grgElapsed=hshElapsed+79+(hshLeap?1:0)-(grgIsLeap(grgYear-1)?1:0);
	}
	if(grgYear >= 2030&&(grgYear-2030)%4==0)
		grgElapsed--;
	for(int i=1;i<=12;i++){
		if(grgSumOfDays[grgLeap?1:0][i] >= grgElapsed){
			grgMonth=i;
			grgDay=grgElapsed-grgSumOfDays[grgLeap?1:0][i-1];
			break;
		}
	}
	int[] ret={grgYear,grgMonth,grgDay};
	return ret;
}
public int dayOfWeek(){
	return this.dayOfWeek(this);
}
public int dayOfWeek(PersianDate date){
	return this.dayOfWeek(date.toDate());
}
public int dayOfWeek(Date date){
	Calendar cal=Calendar.getInstance();
	cal.setTime(date);
	return cal.get(Calendar.DAY_OF_WEEK);
}
public String monthName(){
	return this.monthName(this.getShMonth());
}
public String monthName(int month){
	return this.monthNames[month-1];
}
public String dayName(){
	return this.dayName(this);
}
public String dayName(PersianDate date){
	return this.dayNames[this.dayOfWeek(date)];
}
public int getMonthDays(){
	return this.getMonthDays(this.getShYear(),this.getShMonth());
}
public int getMonthDays(int Year,int month){
	if(month==12&&!this.isLeap(Year)){
		return 29;
	}
	if(month<=6){
		return 31;
	}else{
		return 30;
	}
}
public int getDayInYear(){
	return this.getDayInYear(this.getShMonth(),getShDay());
}
public int getDayInYear(int month,int day){
	for(int i=1;i<month;i++){
		if(i<=6){
			day+=31;
		}else{
			day+=30;
		}
	}
	return day;
}
public PersianDate addDate(int year,int month,int day,int hour,int minute,int second){
	this.timeInMilliSecond+=(((year*365)+(month*30)+day)*24*3_600*1_000);
	this.timeInMilliSecond+=((second+(hour*3600)+(minute*60))*1_000);
	this.changeTime();
	return this;
}
public PersianDate addDate(int year,int month,int day){
	return this.addDate(year,month,day,0,0,0);
}
public PersianDate addYear(int year){
	return this.addDate(year,0,0,0,0,0);
}
public PersianDate addMonth(int month){
	return this.addDate(0,month,0,0,0,0);
}
public PersianDate addWeek(int week){
	return this.addDate(0,0,(week*7),0,0,0);
}
public PersianDate addDay(int day){
	return this.addDate(0,0,day,0,0,0);
}
public Boolean after(PersianDate dateInput){
	return (this.timeInMilliSecond<dateInput.getTime());
}
public Boolean before(PersianDate dateInput){
	return (!this.after(dateInput));
}
public Boolean equals(PersianDate dateInput){
	return (this.timeInMilliSecond==dateInput.getTime());
}
public int compareTo(PersianDate anotherDate){
	return (this.timeInMilliSecond<anotherDate.getTime()?-1:(this.timeInMilliSecond==anotherDate.getTime()?0:1));
}
public long getDayuntilToday(){
	return this.getDayUntilToday(new PersianDate());
}
public long getDayUntilToday(PersianDate date){
	long[] ret=this.untilToday(date);
	return ret[0];
}
public long[] untilToday(){
	return this.untilToday(new PersianDate());
}
public long[] untilToday(PersianDate date){
	long secondsInMilli=1000;
	long minutesInMilli=secondsInMilli*60;
	long hoursInMilli=minutesInMilli*60;
	long daysInMilli=hoursInMilli*24;
	long different=Math.abs(this.timeInMilliSecond-date.getTime());
	long elapsedDays=different/daysInMilli;
	different=different%daysInMilli;
	long elapsedHours=different/hoursInMilli;
	different=different%hoursInMilli;
	long elapsedMinutes=different/minutesInMilli;
	different=different%minutesInMilli;
	long elapsedSeconds=different/secondsInMilli;
	return new long[]{elapsedDays,elapsedHours,elapsedMinutes,elapsedSeconds};
}
@Override public String toString(){
	return PersianDateFormat.format(this,null);
}
public Date toDate(){
	return new Date(this.timeInMilliSecond);
}
private String textNumberFilter(String date){
	if(date.length()<2){
		return "0"+date;
	}
	return date;
}
private void changeTime(){
	this.initGrgDate(Integer.parseInt(new SimpleDateFormat("yyyy").format(this.timeInMilliSecond)),Integer.parseInt(new SimpleDateFormat("MM").format(this.timeInMilliSecond)),Integer.parseInt(new SimpleDateFormat("dd").format(this.timeInMilliSecond)),Integer.parseInt(new SimpleDateFormat("HH").format(this.timeInMilliSecond)),Integer.parseInt(new SimpleDateFormat("mm").format(this.timeInMilliSecond)),Integer.parseInt(new SimpleDateFormat("ss").format(this.timeInMilliSecond)));
}
public PersianDate startOfDay(PersianDate persianDate){
	persianDate.setHour(0).setMinute(0).setSecond(0);
	return persianDate;
}
public PersianDate startOfDay(){
	return this.startOfDay(this);
}
public PersianDate endOfDay(PersianDate persianDate){
	persianDate.setHour(23).setMinute(59).setSecond(59);
	return persianDate;
}
public PersianDate endOfDay(){
	return this.endOfDay(this);
}
public Boolean isMidNight(PersianDate persianDate){
	return persianDate.isMidNight();
}
public Boolean isMidNight(){
	return (this.hour<12);
}
public String getShortTimeOfTheDay(){
	return (this.isMidNight())?AM_SHORT_NAME:PM_SHORT_NAME;
}
public String getShortTimeOfTheDay(PersianDate persianDate){
	return (persianDate.isMidNight())?AM_SHORT_NAME:PM_SHORT_NAME;
}
public String getTimeOfTheDay(){
	return (this.isMidNight())?AM_NAME:PM_NAME;
}
public String getTimeOfTheDay(PersianDate persianDate){
	return (persianDate.isMidNight())?AM_NAME:PM_NAME;
}
}