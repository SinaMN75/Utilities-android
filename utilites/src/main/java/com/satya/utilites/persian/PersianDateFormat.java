package com.satya.utilites.persian;

import java.text.*;
import java.util.*;

public class PersianDateFormat{
    private String[] key = {"a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y", "z", "A", "L"};
private String pattern;
    private String[] key_parse = {"yyyy", "MM", "dd", "HH", "mm", "ss"};
public PersianDateFormat(String pattern){
	this.pattern=pattern;
}
public PersianDateFormat(){
	pattern="l j F Y H:i:s";
}
public static String format(PersianDate date,String pattern){
	if(pattern==null)
		pattern="l j F Y H:i:s";
    String[] key = {"a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y", "z", "A", "L"};
	String year2=null;
	if((""+date.getShYear()).length()==2){
		year2=""+date.getShYear();
	}else
		if((""+date.getShYear()).length()==3){
			year2=(""+date.getShYear()).substring(2,3);
		}else{
			year2=(""+date.getShYear()).substring(2,4);
		}
    String[] values = {date.getShortTimeOfTheDay(), date.dayName(), "" + date.getShDay(), date.monthName(),
                       "" + date.getShYear(), textNumberFilterStatic("" + date.getHour()),
                       textNumberFilterStatic("" + date.getMinute()), textNumberFilterStatic("" + date.getSecond()),
                       textNumberFilterStatic("" + date.getShDay()), "" + date.getHour(), "" + date.getShMonth(),
                       textNumberFilterStatic("" + date.getShMonth()), "" + date.getMonthDays(), "" + date.dayOfWeek(),
                       year2, "" + date.getDayInYear(), date.getTimeOfTheDay(), (date.isLeap() ? "1" : "0")};
	for(int i=0;i<key.length;i++){
		pattern=pattern.replace(key[i],values[i]);
	}
	return pattern;
}
public static String textNumberFilterStatic(String date){
	if(date.length()<2){
		return "0"+date;
	}
	return date;
}
public String format(PersianDate date){
	String year2=null;
	if((""+date.getShYear()).length()==2){
		year2=""+date.getShYear();
	}else
		if((""+date.getShYear()).length()==3){
			year2=(""+date.getShYear()).substring(2,3);
		}else{
			year2=(""+date.getShYear()).substring(2,4);
		}
    String[] values = {date.isMidNight() ? "ق.ظ" : "ب.ظ", date.dayName(), "" + date.getShDay(), date.monthName(),
                       "" + date.getShYear(), this.textNumberFilter("" + date.getHour()),
                       this.textNumberFilter("" + date.getMinute()), this.textNumberFilter("" + date.getSecond()),
                       this.textNumberFilter("" + date.getShDay()), "" + date.getHour(), "" + date.getShMonth(),
                       this.textNumberFilter("" + date.getShMonth()), "" + date.getMonthDays(), "" + date.dayOfWeek(),
                       year2, "" + date.getDayInYear(), date.getTimeOfTheDay(), (date.isLeap() ? "1" : "0")};
	return this.stringUtils(this.pattern,this.key,values);
}
public PersianDate parse(String date) throws ParseException{
	return this.parse(date,this.pattern);
}
public PersianDate parse(String date,String pattern) throws ParseException{
	ArrayList<Integer> JalaliDate=new ArrayList<Integer>(){{
		add(0);
		add(0);
		add(0);
		add(0);
		add(0);
		add(0);
	}};
	for(int i=0;i<key_parse.length;i++){
		if((pattern.contains(key_parse[i]))){
			int start_temp=pattern.indexOf(key_parse[i]);
			int end_temp=start_temp+key_parse[i].length();
			String dateReplace=date.substring(start_temp,end_temp);
			if(dateReplace.matches("[-+]?\\d*\\.?\\d+")){
				JalaliDate.set(i,Integer.parseInt(dateReplace));
			}else{
				throw new ParseException("Parse Exception",10);
			}
		}
	}
	return new PersianDate().initJalaliDate(JalaliDate.get(0),JalaliDate.get(1),JalaliDate.get(2),JalaliDate.get(3),JalaliDate.get(4),JalaliDate.get(5));
}
public PersianDate parseGrg(String date) throws ParseException{
	return this.parseGrg(date,this.pattern);
}
public PersianDate parseGrg(String date,String pattern) throws ParseException{
	Date dateInGrg=new SimpleDateFormat(pattern).parse(date);
	return new PersianDate(dateInGrg.getTime());
}
private String stringUtils(String text,String[] key,String[] values){
	for(int i=0;i<key.length;i++){
		text=text.replace(key[i],values[i]);
	}
	return text;
}
private String textNumberFilter(String date){
	if(date.length()<2){
		return "0"+date;
	}
	return date;
}
}