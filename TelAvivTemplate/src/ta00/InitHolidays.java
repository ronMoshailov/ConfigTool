package ta172;
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
		
		Sondertag entry_11 = new Sondertag("Rosh Hashana Eve 2020", 18,   9, 2020); // fr
		Sondertag entry_21 = new Sondertag("Rosh Hashana Eve 2021",  6,   9, 2021); // mo
		Sondertag entry_31 = new Sondertag("Rosh Hashana Eve 2022", 25,   9, 2022); // su
		Sondertag entry_41 = new Sondertag("Rosh Hashana Eve 2023", 15,   9, 2023); // fr
		Sondertag entry_51 = new Sondertag("Rosh Hashana Eve 2024",  2,  10, 2024); // we

		Sondertag entry_12 = new Sondertag("Rosh Hashana A 2020", 19,   9, 2020); // sa
		Sondertag entry_22 = new Sondertag("Rosh Hashana A 2021",  7,   9, 2021); // tu
		Sondertag entry_32 = new Sondertag("Rosh Hashana A 2022", 26,   9, 2022); // mo
		Sondertag entry_42 = new Sondertag("Rosh Hashana A 2023", 16,   9, 2023); // sa
		Sondertag entry_52 = new Sondertag("Rosh Hashana A 2024",  3,  10, 2024); // th

		Sondertag entry_13 = new Sondertag("Rosh Hashana B 2020", 20,   9, 2020); // su
		Sondertag entry_23 = new Sondertag("Rosh Hashana B 2021",  8,   9, 2021); // we
		Sondertag entry_33 = new Sondertag("Rosh Hashana B 2022", 27,   9, 2022); // tu
		Sondertag entry_43 = new Sondertag("Rosh Hashana B 2023", 17,   9, 2023); // su
		Sondertag entry_53 = new Sondertag("Rosh Hashana B 2024",  4,  10, 2024); // fr

		Sondertag entry_14 = new Sondertag("Yom Kipur Eve 2020", 27,   9, 2020); // su
		Sondertag entry_24 = new Sondertag("Yom Kipur Eve 2021", 15,   9, 2021); // we
		Sondertag entry_34 = new Sondertag("Yom Kipur Eve 2022",  4,  10, 2022); // tu
		Sondertag entry_44 = new Sondertag("Yom Kipur Eve 2023", 24,   9, 2023); // su
		Sondertag entry_54 = new Sondertag("Yom Kipur Eve 2024", 11,  10, 2024); // fr

		Sondertag entry_15 = new Sondertag("Yom Kipur 2020", 28,   9, 2020); // mo
		Sondertag entry_25 = new Sondertag("Yom Kipur 2021", 16,   9, 2021); // th
		Sondertag entry_35 = new Sondertag("Yom Kipur 2022",  5,  10, 2022); // we
		Sondertag entry_45 = new Sondertag("Yom Kipur 2023", 25,   9, 2023); // mo
		Sondertag entry_55 = new Sondertag("Yom Kipur 2024", 12,  10, 2024); // sa

		Sondertag entry_16 = new Sondertag("Sukot A Eve 2020",  2,  10, 2020); // fr
		Sondertag entry_26 = new Sondertag("Sukot A Eve 2021", 20,   9, 2021); // mo
		Sondertag entry_36 = new Sondertag("Sukot A Eve 2022",  9,  10, 2022); // su
		Sondertag entry_46 = new Sondertag("Sukot A Eve 2023", 29,   9, 2023); // fr
		Sondertag entry_56 = new Sondertag("Sukot A Eve 2024", 16,  10, 2024); // we

		Sondertag entry_17 = new Sondertag("Sukot A 2020",  3,  10, 2020); // sa
		Sondertag entry_27 = new Sondertag("Sukot A 2021", 21,   9, 2021); // tu
		Sondertag entry_37 = new Sondertag("Sukot A 2022", 10,  10, 2022); // su
		Sondertag entry_47 = new Sondertag("Sukot A 2023", 30,   9, 2023); // sa
		Sondertag entry_57 = new Sondertag("Sukot A 2024", 17,  10, 2024); // th

		Sondertag entry_18 = new Sondertag("Sukot B Eve 2020",  9,  10, 2020); // fr 
		Sondertag entry_28 = new Sondertag("Sukot B Eve 2021", 27,   9, 2021); // mo 
		Sondertag entry_38 = new Sondertag("Sukot B Eve 2022", 16,  10, 2022); // su 
		Sondertag entry_48 = new Sondertag("Sukot B Eve 2023",  6,  10, 2023); // fr 
		Sondertag entry_58 = new Sondertag("Sukot B Eve 2024", 23,  10, 2024); // we 

		Sondertag entry_19 = new Sondertag("Sukot B 2020", 10,  10, 2020); // sa
		Sondertag entry_29 = new Sondertag("Sukot B 2021", 28,   9, 2021); // tu
		Sondertag entry_39 = new Sondertag("Sukot B 2022", 17,  10, 2022); // su
		Sondertag entry_49 = new Sondertag("Sukot B 2023",  7,  10, 2023); // sa
		Sondertag entry_59 = new Sondertag("Sukot B 2024", 24,  10, 2024); // th

		Sondertag entry_110 = new Sondertag("Pessah A Eve 2021", 27,   3, 2021); // sa
		Sondertag entry_210 = new Sondertag("Pessah A Eve 2022", 15,   4, 2022); // fr
		Sondertag entry_310 = new Sondertag("Pessah A Eve 2023",  5,   4, 2023); // we
		Sondertag entry_410 = new Sondertag("Pessah A Eve 2024", 22,   4, 2024); // mo
		Sondertag entry_510 = new Sondertag("Pessah A Eve 2025", 12,   4, 2025); // sa

		Sondertag entry_111 = new Sondertag("Pessah A 2021", 28,   3, 2021); // su
		Sondertag entry_211 = new Sondertag("Pessah A 2022", 16,   4, 2022); // sa
		Sondertag entry_311 = new Sondertag("Pessah A 2023",  6,   4, 2023); // th
		Sondertag entry_411 = new Sondertag("Pessah A 2024", 23,   4, 2024); // tu
		Sondertag entry_511 = new Sondertag("Pessah A 2025", 13,   4, 2025); // su

		Sondertag entry_112 = new Sondertag("Pessah B Eve 2021",  3,   4, 2021); // sa
		Sondertag entry_212 = new Sondertag("Pessah B Eve 2022", 22,   4, 2022); // fr
		Sondertag entry_312 = new Sondertag("Pessah B Eve 2023", 12,   4, 2023); // we
		Sondertag entry_412 = new Sondertag("Pessah B Eve 2024", 29,   4, 2024); // mo
		Sondertag entry_512 = new Sondertag("Pessah B Eve 2025", 19,   4, 2025); // sa

		Sondertag entry_113 = new Sondertag("Pessah B 2021",  4,   4, 2021); // su
		Sondertag entry_213 = new Sondertag("Pessah B 2022", 23,   4, 2022); // sa
		Sondertag entry_313 = new Sondertag("Pessah B 2023", 13,   4, 2023); // th
		Sondertag entry_413 = new Sondertag("Pessah B 2024", 30,   4, 2024); // tu
		Sondertag entry_513 = new Sondertag("Pessah B 2025", 20,   4, 2025); // su

		Sondertag entry_114 = new Sondertag("Yom HaAzmaut Eve 2021", 14,   4, 2021); // we
		Sondertag entry_214 = new Sondertag("Yom HaAzmaut Eve 2022",  4,   5, 2022); // we
		Sondertag entry_314 = new Sondertag("Yom HaAzmaut Eve 2023", 25,   4, 2023); // tu
		Sondertag entry_414 = new Sondertag("Yom HaAzmaut Eve 2024", 13,   5, 2024); // mo
		Sondertag entry_514 = new Sondertag("Yom HaAzmaut Eve 2025", 30,   4, 2025); // we

		Sondertag entry_115 = new Sondertag("Yom HaAzmaut 2021", 15,   4, 2021); // th
		Sondertag entry_215 = new Sondertag("Yom HaAzmaut 2022",  5,   5, 2022); // th
		Sondertag entry_315 = new Sondertag("Yom HaAzmaut 2023", 26,   4, 2023); // fr
		Sondertag entry_415 = new Sondertag("Yom HaAzmaut 2024", 14,   5, 2024); // tu
		Sondertag entry_515 = new Sondertag("Yom HaAzmaut 2025",  1,   5, 2025); // th

		Sondertag entry_116 = new Sondertag("Shavuot Eve 2020", 28,   5, 2020); // th
		Sondertag entry_216 = new Sondertag("Shavuot Eve 2021", 16,   5, 2021); // su
		Sondertag entry_316 = new Sondertag("Shavuot Eve 2022",  4,   6, 2022); // sa
		Sondertag entry_416 = new Sondertag("Shavuot Eve 2023", 25,   5, 2023); // th
		Sondertag entry_516 = new Sondertag("Shavuot Eve 2024", 11,   6, 2024); // tu
		Sondertag entry_616 = new Sondertag("Shavuot Eve 2025",  1,   6, 2025); // su

		Sondertag entry_117 = new Sondertag("Shavuot 2020", 29,   5, 2020); // fr
		Sondertag entry_217 = new Sondertag("Shavuot 2021", 17,   5, 2021); // mo
		Sondertag entry_317 = new Sondertag("Shavuot 2022",  5,   6, 2022); // su
		Sondertag entry_417 = new Sondertag("Shavuot 2023", 26,   5, 2023); // fr
		Sondertag entry_517 = new Sondertag("Shavuot 2024", 12,   6, 2024); // we
		Sondertag entry_617 = new Sondertag("Shavuot 2025",  2,   6, 2025); // mo
		
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
		entry_33.aktiviere(sa);
		entry_43.aktiviere(sa);
		if (!isBlinkingHaifa) {
			entry_53.aktiviere(sa);
		} else {
			entry_53.aktiviere(blink);
		}
		
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

		entry_110.aktiviere(sa);
		entry_210.aktiviere(fr);
		entry_310.aktiviere(fr);
		entry_410.aktiviere(fr);
		entry_510.aktiviere(sa);

		entry_111.aktiviere(sa);
		entry_211.aktiviere(sa);
		entry_311.aktiviere(sa);
		entry_411.aktiviere(sa);
		entry_511.aktiviere(sa);

		entry_112.aktiviere(sa);
		entry_212.aktiviere(fr);
		entry_312.aktiviere(fr);
		entry_412.aktiviere(fr);
		entry_512.aktiviere(sa);

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
		
		entry_116.aktiviere(fr);
		entry_216.aktiviere(fr);
		if (!isBlinkingHaifa) {
			entry_316.aktiviere(sa); // falls on Saturday - should be Sat. schedule
		} else {
			entry_316.aktiviere(blink);
		}
		entry_416.aktiviere(fr);
		entry_516.aktiviere(fr);
		entry_616.aktiviere(fr);

		if (!isBlinkingHaifa) {
			entry_117.aktiviere(sa);
		} else {
			entry_117.aktiviere(blink);
		}
		entry_217.aktiviere(sa);
		entry_317.aktiviere(sa);
		if (!isBlinkingHaifa) {
			entry_417.aktiviere(sa);
		} else {
			entry_417.aktiviere(blink);
		}
		entry_517.aktiviere(sa);
		entry_617.aktiviere(sa);
	}
}