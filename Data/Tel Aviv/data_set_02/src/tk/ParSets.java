package tk;

import pars.Pars_Res;
import pars.Pars_SP00;
import pars.Pars_SP01;
import pars.Pars_SP02;
import pars.Pars_SP03;
import pars.Pars_SP04;
import pars.Pars_SP05;
import pars.Pars_SP06;
import pars.Pars_SP07;
import pars.Pars_SP08;
import pars.Pars_SP09;
import pars.Pars_SP10;
import pars.Pars_SP11;
import pars.Pars_SP12;
import pars.Pars_SP13;
import pars.Pars_SP14;
import pars.Pars_SP15;
import pars.Pars_SP16;
import pars.Pars_SP17;
import pars.Pars_SP18;
import pars.Pars_SP19;
import pars.Pars_SP20;
import vtvar.*;

public class ParSets {
    //global for all applications
    //signsOffsetInOn  - offset (in minutes) for turning the signs ON early
    //signsOffsetInOff - offset (in minutes) for turning the signs OFF later
	public static VarInt signsOffsetInOn;
	public static VarInt signsOffsetInOff;
	
    //for green wave - UL timeout parameter
    public static VtVarStrukt ULFailParam;
	
	//gap time - global for Maatz
	//public static VarInt gap_E_4;
    
	//for global parameters for Maatz
    public static VtVarStrukt glob_P01, glob_P02, glob_P03, glob_P04, glob_P05, glob_P06, glob_P07, glob_P08;
    public static VtVarStrukt glob_P09, glob_P10, glob_P11, glob_P12, glob_P13, glob_P14, glob_P15, glob_P16;
    public static VtVarStrukt glob_P17, glob_P18, glob_P19;
    public static VtVarStrukt Ver_No_P01, Ver_No_P02, Ver_No_P03, Ver_No_P04, Ver_No_P05, Ver_No_P06, Ver_No_P07, Ver_No_P08;
    public static VtVarStrukt Ver_No_P09, Ver_No_P10, Ver_No_P11, Ver_No_P12, Ver_No_P13, Ver_No_P14, Ver_No_P15, Ver_No_P16;
    public static VtVarStrukt Ver_No_P17, Ver_No_P18, Ver_No_P19;
    public static VtVarStrukt Struct_No_P01, Struct_No_P02, Struct_No_P03, Struct_No_P04, Struct_No_P05, Struct_No_P06, Struct_No_P07, Struct_No_P08;
    public static VtVarStrukt Struct_No_P09, Struct_No_P10, Struct_No_P11, Struct_No_P12, Struct_No_P13, Struct_No_P14, Struct_No_P15, Struct_No_P16;
    public static VtVarStrukt Struct_No_P17, Struct_No_P18, Struct_No_P19;
    public static VtVarStrukt CyMax_P01, CyMax_P02, CyMax_P03, CyMax_P04, CyMax_P05, CyMax_P06, CyMax_P07, CyMax_P08;
    public static VtVarStrukt CyMax_P09, CyMax_P10, CyMax_P11, CyMax_P12, CyMax_P13, CyMax_P14, CyMax_P15, CyMax_P16;
    public static VtVarStrukt CyMax_P17, CyMax_P18, CyMax_P19;
    public static VtVarStrukt Offset_P01, Offset_P02, Offset_P03, Offset_P04, Offset_P05, Offset_P06, Offset_P07, Offset_P08;
    public static VtVarStrukt Offset_P09, Offset_P10, Offset_P11, Offset_P12, Offset_P13, Offset_P14, Offset_P15, Offset_P16;
    public static VtVarStrukt Offset_P17, Offset_P18, Offset_P19;

    //for DVI35 parameters
    public static VtVarStrukt pars_DET;
    public static VtVarStrukt pars_P01, pars_P02, pars_P03, pars_P04, pars_P05, pars_P06, pars_P07, pars_P08;
    public static VtVarStrukt pars_P09, pars_P10, pars_P11, pars_P12, pars_P13, pars_P14, pars_P15, pars_P16;
    public static VtVarStrukt pars_P17, pars_P18, pars_P19, pars_P20, pars_P21, pars_P22, pars_P23, pars_P24;
    public static VtVarStrukt pars_P25, pars_P26, pars_P27, pars_P28, pars_P29, pars_P30, pars_P31, pars_P32;
    
    public static VtVarStrukt checkPulse;
    
    //for DVI35 auxiliary arrays
	public static int [] Det_Param;

    //for OCIT parameters
    public static Pars_SP00 pars_sp00;
    public static Pars_SP01 pars_sp01;
    public static Pars_SP02 pars_sp02;
    public static Pars_SP03 pars_sp03;
    public static Pars_SP04 pars_sp04;
    public static Pars_SP05 pars_sp05;
    public static Pars_SP06 pars_sp06;
    public static Pars_SP07 pars_sp07;
    public static Pars_SP08 pars_sp08;
    public static Pars_SP09 pars_sp09;
    public static Pars_SP10 pars_sp10;
    public static Pars_SP11 pars_sp11;
    public static Pars_SP12 pars_sp12;
    public static Pars_SP13 pars_sp13;
    public static Pars_SP14 pars_sp14;
    public static Pars_SP15 pars_sp15;
    public static Pars_SP16 pars_sp16;
    public static Pars_SP17 pars_sp17;
    public static Pars_SP18 pars_sp18;
    public static Pars_SP19 pars_sp19;
    public static Pars_SP20 pars_sp20;
    public static Pars_Det_tk1 pars_det1;
    public static Pars_Det_tk2 pars_det2;
    public static Pars_Det_tk3 pars_det3;
    public static Pars_Res pars_res;

    //For the Police Program
    public static VtVarStrukt r1;
    public static VtVarStrukt r2;
}