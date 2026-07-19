package m0547;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 239                                                                     	*
 *  Junction Name  : Varburg / Rains / HaTkuma Yahiam - Maavar Hazaya							*
 *  Equipmentno.   : h239                                                                    	*
 *                                                                                              *
 ************************************************************************************************/
import uhr.Sondertag;
import uhr.TagesPlan;

public class InitHolidays {

	public static void setHolidays(TagesPlan fr, TagesPlan sa, TagesPlan kipurEve, TagesPlan kipur, TagesPlan blink, boolean isKipur, boolean isBlinkingHaifa)
	{
		if (!Var.controller.isAppHaifa()) {
			isBlinkingHaifa = false;
		}
		
		Sondertag entry_11  = new Sondertag("Rosh Hashana Eve 2015"  , 13,  9, 2015);
		Sondertag entry_21  = new Sondertag("Rosh Hashana Eve 2016"  ,  2, 10, 2016);
		Sondertag entry_31  = new Sondertag("Rosh Hashana Eve 2017"  , 20,  9, 2017);
		Sondertag entry_41  = new Sondertag("Rosh Hashana Eve 2018"  ,  9,  9, 2018);
		Sondertag entry_51  = new Sondertag("Rosh Hashana Eve 2019"  , 29,  9, 2019);

		Sondertag entry_12  = new Sondertag("Rosh HaShana A 2015"    , 14,  9, 2015);
		Sondertag entry_22  = new Sondertag("Rosh HaShana A 2016"    ,  3, 10, 2016);
		Sondertag entry_32  = new Sondertag("Rosh HaShana A 2017"    , 21,  9, 2017);
		Sondertag entry_42  = new Sondertag("Rosh HaShana A 2018"    , 10,  9, 2018);
		Sondertag entry_52  = new Sondertag("Rosh HaShana A 2019"    , 30,  9, 2019);

		Sondertag entry_13  = new Sondertag("Rosh HaShana B 2015"    , 15,  9, 2015);
		Sondertag entry_23  = new Sondertag("Rosh HaShana B 2016"    ,  4, 10, 2016);
		Sondertag entry_33  = new Sondertag("Rosh HaShana B 2017"    , 22,  9, 2017);
		Sondertag entry_43  = new Sondertag("Rosh HaShana B 2018"    , 11,  9, 2018);
		Sondertag entry_53  = new Sondertag("Rosh HaShana B 2019"    ,  1, 10, 2019);

		Sondertag entry_14  = new Sondertag("Yom Kipur Eve 2015"     , 22,  9, 2015);
		Sondertag entry_24  = new Sondertag("Yom Kipur Eve 2016"     , 11, 10, 2016);
		Sondertag entry_34  = new Sondertag("Yom Kipur Eve 2017"     , 29,  9, 2017);
		Sondertag entry_44  = new Sondertag("Yom Kipur Eve 2018"     , 18,  9, 2018);
		Sondertag entry_54  = new Sondertag("Yom Kipur Eve 2019"     ,  8, 10, 2019);

		Sondertag entry_15  = new Sondertag("Yom Kipur 2015"         , 23,  9, 2015);
		Sondertag entry_25  = new Sondertag("Yom Kipur 2016"         , 12, 10, 2016);
		Sondertag entry_35  = new Sondertag("Yom Kipur 2017"         , 30,  9, 2017);
		Sondertag entry_45  = new Sondertag("Yom Kipur 2018"         , 19,  9, 2018);
		Sondertag entry_55  = new Sondertag("Yom Kipur 2019"         ,  9, 10, 2019);

		Sondertag entry_16  = new Sondertag("Sukot A Eve 2015"       , 27,  9, 2015);
		Sondertag entry_26  = new Sondertag("Sukot A Eve 2016"       , 16, 10, 2016);
		Sondertag entry_36  = new Sondertag("Sukot A Eve 2017"       ,  4, 10, 2017);
		Sondertag entry_46  = new Sondertag("Sukot A Eve 2018"       , 23,  9, 2018);
		Sondertag entry_56  = new Sondertag("Sukot A Eve 2019"       , 13, 10, 2019);

		Sondertag entry_17  = new Sondertag("Sukot A 2015"  	     , 28,  9, 2015);
		Sondertag entry_27  = new Sondertag("Sukot A 2016"  	     , 17, 10, 2016);
		Sondertag entry_37  = new Sondertag("Sukot A 2017"  	     ,  5, 10, 2017);
		Sondertag entry_47  = new Sondertag("Sukot A 2018" 		     , 24,  9, 2018);
		Sondertag entry_57  = new Sondertag("Sukot A 2019"  	     , 14, 10, 2019);

		Sondertag entry_18  = new Sondertag("Sukot B Eve 2015"       ,  4, 10, 2015);
		Sondertag entry_28  = new Sondertag("Sukot B Eve 2016"       , 23, 10, 2016);
		Sondertag entry_38  = new Sondertag("Sukot B Eve 2017"       , 11, 10, 2017);
		Sondertag entry_48  = new Sondertag("Sukot B Eve 2018"       , 30,  9, 2018);
		Sondertag entry_58  = new Sondertag("Sukot B Eve 2019"       , 20, 10, 2019);

		Sondertag entry_19  = new Sondertag("Sukot B 2015"      	 ,  5, 10, 2015);
		Sondertag entry_29  = new Sondertag("Sukot B 2016"      	 , 24, 10, 2016);
		Sondertag entry_39  = new Sondertag("Sukot B 2017"     		 , 12, 10, 2017);
		Sondertag entry_49  = new Sondertag("Sukot B 2018"       	 ,  1, 10, 2018);
		Sondertag entry_59  = new Sondertag("Sukot B 2019"     		 , 21, 10, 2019);

		Sondertag entry_110 = new Sondertag("Pessah A Eve 2015"      , 22,  4, 2016);
		Sondertag entry_210 = new Sondertag("Pessah A Eve 2016"      , 10,  4, 2017);
		Sondertag entry_310 = new Sondertag("Pessah A Eve 2017"   	 , 30,  3, 2018);
		Sondertag entry_410 = new Sondertag("Pessah A Eve 2018"   	 , 19,  4, 2019);
		Sondertag entry_510 = new Sondertag("Pessah A Eve 2019"   	 ,  8,  4, 2020);

		Sondertag entry_111 = new Sondertag("Pessah A 2016"	         , 23,  4, 2016);
		Sondertag entry_211 = new Sondertag("Pessah A 2017"          , 11,  4, 2017);
		Sondertag entry_311 = new Sondertag("Pessah A 2018"          , 31,  3, 2018);
		Sondertag entry_411 = new Sondertag("Pessah A 2019"          , 20,  4, 2019);
		Sondertag entry_511 = new Sondertag("Pessah A 2020"          ,  9,  4, 2020);

		Sondertag entry_112 = new Sondertag("Pessah B Eve 2016"      , 28,  4, 2016);
		Sondertag entry_212 = new Sondertag("Pessah B Eve 2017"      , 16,  4, 2017);
		Sondertag entry_312 = new Sondertag("Pessah B Eve 2018"      ,  5,  4, 2018);
		Sondertag entry_412 = new Sondertag("Pessah B Eve 2019"    	 , 25,  4, 2019);
		Sondertag entry_512 = new Sondertag("Pessah B Eve 2020"    	 , 14,  4, 2020);

		Sondertag entry_113 = new Sondertag("Pessah B 2016"      	 , 29,  4, 2016);
		Sondertag entry_213 = new Sondertag("Pessah B 2017"      	 , 17,  4, 2017);
		Sondertag entry_313 = new Sondertag("Pessah B 2018"    		 ,  6,  4, 2018);
		Sondertag entry_413 = new Sondertag("Pessah B 2019"       	 , 26,  4, 2019);
		Sondertag entry_513 = new Sondertag("Pessah B 2020"    		 , 15,  4, 2020);

		Sondertag entry_114 = new Sondertag("Yom HaAzmaut Eve 2016"  , 11,  5, 2016);
		Sondertag entry_214 = new Sondertag("Yom HaAzmaut Eve 2017"  ,  1,  5, 2017);
		Sondertag entry_314 = new Sondertag("Yom HaAzmaut Eve 2018"  , 18,  4, 2018);
		Sondertag entry_414 = new Sondertag("Yom HaAzmaut Eve 2019"  ,  8,  5, 2019);
		Sondertag entry_514 = new Sondertag("Yom HaAzmaut Eve 2020"  , 28,  4, 2020);

		Sondertag entry_115 = new Sondertag("Yom HaAzmaut 2016"      , 12,  5, 2016);
		Sondertag entry_215 = new Sondertag("Yom HaAzmaut 2017"      ,  2,  5, 2017);
		Sondertag entry_315 = new Sondertag("Yom HaAzmaut 2018"    	 , 19,  4, 2018);
		Sondertag entry_415 = new Sondertag("Yom HaAzmaut 2019"      ,  9,  5, 2019);
		Sondertag entry_515 = new Sondertag("Yom HaAzmaut 2020"      , 29,  4, 2020);

		Sondertag entry_116 = new Sondertag("Shavuot Eve 2016"       , 11,  6, 2016);
		Sondertag entry_216 = new Sondertag("Shavuot Eve 2017"       , 30,  5, 2017);
		Sondertag entry_316 = new Sondertag("Shavuot Eve 2018"     	 , 19,  5, 2018);
		Sondertag entry_416 = new Sondertag("Shavuot Eve 2019"       ,  8,  6, 2019);
		Sondertag entry_516 = new Sondertag("Shavuot Eve 2020"     	 , 28,  5, 2020);

		Sondertag entry_117 = new Sondertag("Shavuot 2016"      	 , 12,  6, 2016);
		Sondertag entry_217 = new Sondertag("Shavuot 2017"      	 , 31,  5, 2017);
		Sondertag entry_317 = new Sondertag("Shavuot 2018"     		 , 20,  5, 2018);
		Sondertag entry_417 = new Sondertag("Shavuot 2019"       	 ,  9,  6, 2019);
		Sondertag entry_517 = new Sondertag("Shavuot 2020"     		 , 29,  5, 2020);

		entry_11.aktiviere(fr);
		entry_21.aktiviere(fr);
		entry_31.aktiviere(fr);
		entry_41.aktiviere(fr);
		entry_51.aktiviere(fr);
		
		if (!isBlinkingHaifa)
		{
			entry_12.aktiviere(sa);
			entry_22.aktiviere(sa);
			entry_32.aktiviere(sa);
			entry_42.aktiviere(sa);
			entry_52.aktiviere(sa);
		}
		else
		{
			entry_12.aktiviere(blink);
			entry_22.aktiviere(blink);
			entry_32.aktiviere(blink);
			entry_42.aktiviere(blink);
			entry_52.aktiviere(blink);
		}

		entry_13.aktiviere(sa);
		entry_23.aktiviere(sa);
		if (!isBlinkingHaifa) {
			entry_33.aktiviere(sa);
		} else {
			entry_33.aktiviere(blink);
		}
		entry_43.aktiviere(sa);
		entry_53.aktiviere(sa);
		
		if(isKipur) 
		{
			entry_14.aktiviere (kipurEve);
			entry_24.aktiviere (kipurEve);
			entry_34.aktiviere (kipurEve);
			entry_44.aktiviere (kipurEve);
			entry_54.aktiviere (kipurEve);
			        
			entry_15.aktiviere (kipur);
			entry_25.aktiviere (kipur);
			entry_35.aktiviere (kipur);
			entry_45.aktiviere (kipur);
			entry_55.aktiviere (kipur);	
		}
		else
		{
			entry_14.aktiviere (fr);
			entry_24.aktiviere (fr);
			entry_34.aktiviere (fr);
			entry_44.aktiviere (fr);
			entry_54.aktiviere (fr);
			        
			entry_15.aktiviere (sa);
			entry_25.aktiviere (sa);
			entry_35.aktiviere (sa);
			entry_45.aktiviere (sa);
			entry_55.aktiviere (sa);	
		}

		entry_16.aktiviere(fr);
		entry_26.aktiviere(fr);
		entry_36.aktiviere(fr);
		entry_46.aktiviere(fr);
		entry_56.aktiviere(fr);

		entry_17.aktiviere(sa);
		entry_27.aktiviere(sa);
		entry_37.aktiviere(sa);
		entry_47.aktiviere(sa);
		entry_57.aktiviere(sa);

		entry_18.aktiviere(fr);
		entry_28.aktiviere(fr);
		entry_38.aktiviere(fr);
		entry_48.aktiviere(fr);
		entry_58.aktiviere(fr);

		entry_19.aktiviere(sa);
		entry_29.aktiviere(sa);
		entry_39.aktiviere(sa);
		entry_49.aktiviere(sa);
		entry_59.aktiviere(sa);

		entry_110.aktiviere(fr);
		entry_210.aktiviere(fr);
		entry_310.aktiviere(fr);
		entry_410.aktiviere(fr);
		entry_510.aktiviere(fr);

		entry_111.aktiviere(sa);
		entry_211.aktiviere(sa);
		entry_311.aktiviere(sa);
		entry_411.aktiviere(sa);
		entry_511.aktiviere(sa);

		entry_112.aktiviere(fr);
		entry_212.aktiviere(fr);
		entry_312.aktiviere(fr);
		entry_412.aktiviere(fr);
		entry_512.aktiviere(fr);

		if (!isBlinkingHaifa) {
			entry_113.aktiviere(sa);
		} else { 
			entry_113.aktiviere(blink);
		}
		entry_213.aktiviere(sa);
		if (!isBlinkingHaifa) {
			entry_313.aktiviere(sa);
			entry_413.aktiviere(sa);
		} else {
			entry_313.aktiviere(blink);
			entry_413.aktiviere(blink);
		}
		entry_513.aktiviere(sa);
		
		if(!Var.controller.isAppHaifa())
		{
			entry_114.aktiviere(fr);
			entry_214.aktiviere(fr);
			entry_314.aktiviere(fr);
			entry_414.aktiviere(fr);
			entry_514.aktiviere(fr);
	
			entry_115.aktiviere(sa);
			entry_215.aktiviere(sa);
			entry_315.aktiviere(sa);
			entry_415.aktiviere(sa);
			entry_515.aktiviere(sa);
		}
		
		if (!isBlinkingHaifa) {
			entry_116.aktiviere(sa); // falls on Saturday - should be Sat. schedule
		} else {
			entry_116.aktiviere(blink);
		}
		entry_216.aktiviere(fr);
		if (!isBlinkingHaifa) {
			entry_316.aktiviere(sa); // falls on Saturday - should be Sat. schedule
			entry_416.aktiviere(sa); // falls on Saturday - should be Sat. schedule
		} else {
			entry_316.aktiviere(blink); // falls on Saturday - should be Sat. schedule
			entry_416.aktiviere(blink); // falls on Saturday - should be Sat. schedule
		}
		entry_516.aktiviere(fr);

		entry_117.aktiviere(sa);
		entry_217.aktiviere(sa);
		entry_317.aktiviere(sa);
		entry_417.aktiviere(sa);
		if (!isBlinkingHaifa) {
			entry_517.aktiviere(sa);
		} else {
			entry_517.aktiviere(blink);
		}
	}
}