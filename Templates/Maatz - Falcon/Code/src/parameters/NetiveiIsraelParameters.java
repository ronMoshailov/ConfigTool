package parameters;

import m0547.Var;
import core.parameters.Parameters;
import core.parameters.ParametersIndex;
import vtvar.VtVarStrukt;

/**
 * @author Ilia Butvinnik
 * @version 1.0.0
 * @since 01/09/2025
 */
public class NetiveiIsraelParameters extends Parameters {

    public static final int TYPE_DURATION   = 0;
    public static final int TYPE_ABSOLUTE   = 1;
    public static final int TYPE_COMPLEMENT = 2;
    
    public static final int GW_INDEPENDENT     = 0;
    public static final int GW_MASTER          = 1;
    public static final int GW_SLAVE           = 2;
    public static final int GW_GLOBAL_CLOCK    = 3;
    public static final int GW_SUBMASTER       = 4;
    public static final int GW_LOCAL_SUBMASTER = 5;
    
    public static final int DETECTOR_SET           =  1;
    public static final int DETECTOR_SET_DEMAND    =  2;
    public static final int DETECTOR_SET_EXTENSION =  3;
    
    public static final int DETECTORS_MAX_QUANTITY = 64;
    
    public NetiveiIsraelParameters() {
        super(Var.controller.nodes.length * 100 + 100);
        parametersDetectors = new int[DETECTORS_MAX_QUANTITY];
        setIncludesPhLen(false); // TODO: update this flag according to if you want the stage-related parameters to be considered as if they contain the stage's phLen
    }
    
    
    private final int[] parameters_indexes = {    1,    2,    3,    4,    5,    6,    7,    8,    9,   10,   11,   12,   13,   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,   26,   27,   28,   29,   30,   31,   32,   33,   34,   35,   36,   37,   38,   39,   40,   41,   42,   43,   44,   45,   46,   47,   48,   49,   50,   51,   52,   53,   54,   55,   56,   57,   58,   59,   60,   61,   62,   63,   64,   65,   66,   67,   68,   69,   70,   71,   72,   73,   74,   75,   76,   77,   78,   79,   80,   81,   82,   83,   84,   85,   86,   87,   88,   89,   90,   91,   92,   93,   94,   95,   96,   97,   98,   99,  100,  101,  102,  103,  104,  105,  106};
    //                                          VER STRCT CYCLE    IS    SY    SY    SY  SYNC  SP01  SP01  SP01  SP02  SP02  SP02  SP03  SP03  SP03  SP04  SP04  SP04  SP05  SP05  SP05  SP06  SP06  SP06  SP07  SP07  SP07  SP08  SP08  SP08  SP09  SP09  SP09  SP10  SP10  SP10  SP11  SP11  SP11  SP12  SP12  SP12  SP13  SP13  SP13  SP14  SP14  SP14  SP15  SP15  SP15  SP16  SP16  SP16  SP17  SP17  SP17  SP18  SP18  SP18  SP19  SP19  SP19  SP20  SP20  SP20  SP21  SP21  SP21  SP22  SP22  SP22  SP23  SP23  SP23  SP24  SP24  SP24  SP25  SP25  SP25  SP26  SP26  SP26    SY    SY    SY    N1    N2    N3    N4    N5    N6    N7    N8    N9   N10   N11    E1    E2    D3    E3    D4    E4
    //                                                         MASTER   MIN   MAX  TYPE  OFF-   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX   MIN  TYPE   MAX                                                                                                                                        
    //                                                                                    SET                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ALT   ALT   ALT                                                                                                                                        
    static int[]        DVI35_P01          = {    0,    1,  120,    2,   22,  160,   10,    0,    0,    0,    8,    0,   10,   69,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,   22,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P01
    static int[]        DVI35_P02          = {    0,    1,   90,    2,   13,  120,   10,    0,    0,    0,    5,    0,   10,   50,    0,    0,    7,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,   13,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P02
    static int[]        DVI35_P03          = {    0,    1,  120,    2,   20,  160,   10,    0,    0,    0,   21,    0,   10,   68,    0,    0,   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P03
    static int[]        DVI35_P04          = {    0,    1,   70,    2,    3,  100,   10,    0,    0,    0,    6,    0,   10,   43,    0,    0,    4,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P04
    static int[]        DVI35_P05          = {    0,    1,  130,    2,   10,  175,   10,    0,    0,    0,   40,    0,   10,   91,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,   10,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P05
    static int[]        DVI35_P06          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P06
    static int[]        DVI35_P07          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P07
    static int[]        DVI35_P08          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P08
    static int[]        DVI35_P09          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P09
    static int[]        DVI35_P10          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P10
    static int[]        DVI35_P11          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P11
    static int[]        DVI35_P12          = {    0,    1,   90,    0,    0,    0,    0,    0,    0,    0,   18,    0,    0,   23,    0,    0,    7,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P12
    static int[]        DVI35_P13          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   41,    0,    0,   25,    0,    0,   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P13
    static int[]        DVI35_P14          = {    0,    1,   70,    0,    0,    0,    0,    0,    0,    0,    9,    0,    0,   15,    0,    0,    4,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P14
    static int[]        DVI35_P15          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   39,    0,    0,   17,    0,    0,   22,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P15
    static int[]        DVI35_P16          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   14,    0,    0,   52,    0,    0,   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P16
    static int[]        DVI35_P17          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P17
    static int[]        DVI35_P18          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P18
    static int[]        DVI35_P19          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P19
    static int[]        DVI35_P20          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P20
    static int[]        DVI35_P21          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P21
    static int[]        DVI35_P22          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P22
    static int[]        DVI35_P23          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P23
    static int[]        DVI35_P24          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P24
    static int[]        DVI35_P25          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P25
    static int[]        DVI35_P26          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P26
    static int[]        DVI35_P27          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P27
    static int[]        DVI35_P28          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P28
    static int[]        DVI35_P29          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P29
    static int[]        DVI35_P30          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P30
    static int[]        DVI35_P31          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P31
    static int[]        DVI35_P32          = {    0,    1,  120,    0,    0,    0,    0,    0,    0,    0,   30,    0,    0,   39,    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // P32
                                                         
//    static int[]        DVI35_P32          = {    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // Skeletons Structure #1
//    static int[]        DVI35_P32          = {    0,    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0}; // Skeletons Structure #2
                            
    // Gap / Jam units. Counted in tenth of a second (e.g. a gap of 2.3s would be set as 23)
    static int[]      DVI35_GAP01  /*E1 */ = {   10 };
    static int[]      DVI35_GAP02  /*E2 */ = {   10 };
    static int[]      DVI35_GAP03  /*D3 */ = {    0 };
    static int[]      DVI35_GAP04  /*E3 */ = {   10 };
    static int[]      DVI35_GAP05  /*D4 */ = {    0 };
    static int[]      DVI35_GAP06  /*E4 */ = {   10 };
    static int[]      DVI35_GAP07  /*   */ = {    0 };
    static int[]      DVI35_GAP08  /*   */ = {    0 };
    static int[]      DVI35_GAP09  /*   */ = {    0 };
    static int[]      DVI35_GAP10  /*   */ = {    0 };
    static int[]      DVI35_GAP11  /*   */ = {    0 };
    static int[]      DVI35_GAP12  /*   */ = {    0 };
    static int[]      DVI35_GAP13  /*   */ = {    0 };
    static int[]      DVI35_GAP14  /*   */ = {    0 };
    static int[]      DVI35_GAP15  /*   */ = {    0 };
    static int[]      DVI35_GAP16  /*   */ = {    0 };
    static int[]      DVI35_GAP17  /*   */ = {    0 };
    static int[]      DVI35_GAP18  /*   */ = {    0 };
    static int[]      DVI35_GAP19  /*   */ = {    0 };
    static int[]      DVI35_GAP20  /*   */ = {    0 };
    static int[]      DVI35_GAP21  /*   */ = {    0 };
    static int[]      DVI35_GAP22  /*   */ = {    0 };
    static int[]      DVI35_GAP23  /*   */ = {    0 };
    static int[]      DVI35_GAP24  /*   */ = {    0 };
    static int[]      DVI35_GAP25  /*   */ = {    0 };
    static int[]      DVI35_GAP26  /*   */ = {    0 };
    static int[]      DVI35_GAP27  /*   */ = {    0 };
    static int[]      DVI35_GAP28  /*   */ = {    0 };
    static int[]      DVI35_GAP29  /*   */ = {    0 };
    static int[]      DVI35_GAP30  /*   */ = {    0 };
    static int[]      DVI35_GAP31  /*   */ = {    0 };
    static int[]      DVI35_GAP32  /*   */ = {    0 };
    static int[]      DVI35_GAP33  /*   */ = {    0 };
    static int[]      DVI35_GAP34  /*   */ = {    0 };
    static int[]      DVI35_GAP35  /*   */ = {    0 };
    static int[]      DVI35_GAP36  /*   */ = {    0 };
    static int[]      DVI35_GAP37  /*   */ = {    0 };
    static int[]      DVI35_GAP38  /*   */ = {    0 };
    static int[]      DVI35_GAP39  /*   */ = {    0 };
    static int[]      DVI35_GAP40  /*   */ = {    0 };
    static int[]      DVI35_GAP41  /*   */ = {    0 };
    static int[]      DVI35_GAP42  /*   */ = {    0 };
    static int[]      DVI35_GAP43  /*   */ = {    0 };
    static int[]      DVI35_GAP44  /*   */ = {    0 };
    static int[]      DVI35_GAP45  /*   */ = {    0 };
    static int[]      DVI35_GAP46  /*   */ = {    0 };
    static int[]      DVI35_GAP47  /*   */ = {    0 };
    static int[]      DVI35_GAP48  /*   */ = {    0 };
    static int[]      DVI35_GAP49  /*   */ = {    0 };
    static int[]      DVI35_GAP50  /*   */ = {    0 };
    static int[]      DVI35_GAP51  /*   */ = {    0 };
    static int[]      DVI35_GAP52  /*   */ = {    0 };
    static int[]      DVI35_GAP53  /*   */ = {    0 };
    static int[]      DVI35_GAP54  /*   */ = {    0 };
    static int[]      DVI35_GAP55  /*   */ = {    0 };
    static int[]      DVI35_GAP56  /*   */ = {    0 };
    static int[]      DVI35_GAP57  /*   */ = {    0 };
    static int[]      DVI35_GAP58  /*   */ = {    0 };
    static int[]      DVI35_GAP59  /*   */ = {    0 };
    static int[]      DVI35_GAP60  /*   */ = {    0 };
    static int[]      DVI35_GAP61  /*   */ = {    0 };
    static int[]      DVI35_GAP62  /*   */ = {    0 };
    static int[]      DVI35_GAP63  /*   */ = {    0 };
    static int[]      DVI35_GAP64  /*   */ = {    0 };
    
    /**
     * The IsMaster parameter can have the following values:
     *   - 0: Independent (no green-wave)
     *   - 1: Master
     *   - 2: Slave
     *   - 3: Clock based green-wave
     *   - 4: Submaster (Master + Slave)
     *   - 5: Submaster that should work only in a Master mode
     */
    
    private static int index = 1;
    public static ParametersIndex PARAM_VER_NO            = new ParametersIndex(index++); //   1
    public static ParametersIndex PARAM_STRUCT_NO         = new ParametersIndex(index++); //   2
    public static ParametersIndex PARAM_CYMAX             = new ParametersIndex(index++); //   3
    public static ParametersIndex PARAM_ISMASTER          = new ParametersIndex(index++); //   4
    public static ParametersIndex PARAM_SY_MIN            = new ParametersIndex(index++); //   5
    public static ParametersIndex PARAM_SY_MAX            = new ParametersIndex(index++); //   6
    public static ParametersIndex PARAM_SY_TIMERS         = new ParametersIndex(index++); //   7
    public static ParametersIndex PARAM_SY_OFFSET         = new ParametersIndex(index++); //   8
    public static ParametersIndex PARAM_SP01_MIN          = new ParametersIndex(index++); //   9
    public static ParametersIndex PARAM_SP01_TIMERS       = new ParametersIndex(index++); //  10
    public static ParametersIndex PARAM_SP01_MAX          = new ParametersIndex(index++); //  11
    public static ParametersIndex PARAM_SP02_MIN          = new ParametersIndex(index++); //  12
    public static ParametersIndex PARAM_SP02_TIMERS       = new ParametersIndex(index++); //  13
    public static ParametersIndex PARAM_SP02_MAX          = new ParametersIndex(index++); //  14
    public static ParametersIndex PARAM_SP03_MIN          = new ParametersIndex(index++); //  15
    public static ParametersIndex PARAM_SP03_TIMERS       = new ParametersIndex(index++); //  16
    public static ParametersIndex PARAM_SP03_MAX          = new ParametersIndex(index++); //  17
    public static ParametersIndex PARAM_SP04_MIN          = new ParametersIndex(index++); //  18
    public static ParametersIndex PARAM_SP04_TIMERS       = new ParametersIndex(index++); //  19
    public static ParametersIndex PARAM_SP04_MAX          = new ParametersIndex(index++); //  20
    public static ParametersIndex PARAM_SP05_MIN          = new ParametersIndex(index++); //  21
    public static ParametersIndex PARAM_SP05_TIMERS       = new ParametersIndex(index++); //  22
    public static ParametersIndex PARAM_SP05_MAX          = new ParametersIndex(index++); //  23
    public static ParametersIndex PARAM_SP06_MIN          = new ParametersIndex(index++); //  24
    public static ParametersIndex PARAM_SP06_TIMERS       = new ParametersIndex(index++); //  25
    public static ParametersIndex PARAM_SP06_MAX          = new ParametersIndex(index++); //  26
    public static ParametersIndex PARAM_SP07_MIN          = new ParametersIndex(index++); //  27
    public static ParametersIndex PARAM_SP07_TIMERS       = new ParametersIndex(index++); //  28
    public static ParametersIndex PARAM_SP07_MAX          = new ParametersIndex(index++); //  29
    public static ParametersIndex PARAM_SP08_MIN          = new ParametersIndex(index++); //  30
    public static ParametersIndex PARAM_SP08_TIMERS       = new ParametersIndex(index++); //  31
    public static ParametersIndex PARAM_SP08_MAX          = new ParametersIndex(index++); //  32
    public static ParametersIndex PARAM_SP09_MIN          = new ParametersIndex(index++); //  33
    public static ParametersIndex PARAM_SP09_TIMERS       = new ParametersIndex(index++); //  34
    public static ParametersIndex PARAM_SP09_MAX          = new ParametersIndex(index++); //  35
    public static ParametersIndex PARAM_SP10_MIN          = new ParametersIndex(index++); //  36
    public static ParametersIndex PARAM_SP10_TIMERS       = new ParametersIndex(index++); //  37
    public static ParametersIndex PARAM_SP10_MAX          = new ParametersIndex(index++); //  38
    public static ParametersIndex PARAM_SP11_MIN          = new ParametersIndex(index++); //  39
    public static ParametersIndex PARAM_SP11_TIMERS       = new ParametersIndex(index++); //  40
    public static ParametersIndex PARAM_SP11_MAX          = new ParametersIndex(index++); //  41
    public static ParametersIndex PARAM_SP12_MIN          = new ParametersIndex(index++); //  42
    public static ParametersIndex PARAM_SP12_TIMERS       = new ParametersIndex(index++); //  43
    public static ParametersIndex PARAM_SP12_MAX          = new ParametersIndex(index++); //  44
    public static ParametersIndex PARAM_SP13_MIN          = new ParametersIndex(index++); //  45
    public static ParametersIndex PARAM_SP13_TIMERS       = new ParametersIndex(index++); //  46
    public static ParametersIndex PARAM_SP13_MAX          = new ParametersIndex(index++); //  47
    public static ParametersIndex PARAM_SP14_MIN          = new ParametersIndex(index++); //  48
    public static ParametersIndex PARAM_SP14_TIMERS       = new ParametersIndex(index++); //  49
    public static ParametersIndex PARAM_SP14_MAX          = new ParametersIndex(index++); //  50
    public static ParametersIndex PARAM_SP15_MIN          = new ParametersIndex(index++); //  51
    public static ParametersIndex PARAM_SP15_TIMERS       = new ParametersIndex(index++); //  52
    public static ParametersIndex PARAM_SP15_MAX          = new ParametersIndex(index++); //  53
    public static ParametersIndex PARAM_SP16_MIN          = new ParametersIndex(index++); //  54
    public static ParametersIndex PARAM_SP16_TIMERS       = new ParametersIndex(index++); //  55
    public static ParametersIndex PARAM_SP16_MAX          = new ParametersIndex(index++); //  56
    public static ParametersIndex PARAM_SP17_MIN          = new ParametersIndex(index++); //  57
    public static ParametersIndex PARAM_SP17_TIMERS       = new ParametersIndex(index++); //  58
    public static ParametersIndex PARAM_SP17_MAX          = new ParametersIndex(index++); //  59
    public static ParametersIndex PARAM_SP18_MIN          = new ParametersIndex(index++); //  60
    public static ParametersIndex PARAM_SP18_TIMERS       = new ParametersIndex(index++); //  61
    public static ParametersIndex PARAM_SP18_MAX          = new ParametersIndex(index++); //  62
    public static ParametersIndex PARAM_SP19_MIN          = new ParametersIndex(index++); //  63
    public static ParametersIndex PARAM_SP19_TIMERS       = new ParametersIndex(index++); //  64
    public static ParametersIndex PARAM_SP19_MAX          = new ParametersIndex(index++); //  65
    public static ParametersIndex PARAM_SP20_MIN          = new ParametersIndex(index++); //  66
    public static ParametersIndex PARAM_SP20_TIMERS       = new ParametersIndex(index++); //  67
    public static ParametersIndex PARAM_SP20_MAX          = new ParametersIndex(index++); //  68
    public static ParametersIndex PARAM_SP21_MIN          = new ParametersIndex(index++); //  69
    public static ParametersIndex PARAM_SP21_TIMERS       = new ParametersIndex(index++); //  70
    public static ParametersIndex PARAM_SP21_MAX          = new ParametersIndex(index++); //  71
    public static ParametersIndex PARAM_SP22_MIN          = new ParametersIndex(index++); //  72
    public static ParametersIndex PARAM_SP22_TIMERS       = new ParametersIndex(index++); //  73
    public static ParametersIndex PARAM_SP22_MAX          = new ParametersIndex(index++); //  74
    public static ParametersIndex PARAM_SP23_MIN          = new ParametersIndex(index++); //  75
    public static ParametersIndex PARAM_SP23_TIMERS       = new ParametersIndex(index++); //  76
    public static ParametersIndex PARAM_SP23_MAX          = new ParametersIndex(index++); //  77
    public static ParametersIndex PARAM_SP24_MIN          = new ParametersIndex(index++); //  78
    public static ParametersIndex PARAM_SP24_TIMERS       = new ParametersIndex(index++); //  79
    public static ParametersIndex PARAM_SP24_MAX          = new ParametersIndex(index++); //  80
    public static ParametersIndex PARAM_SP25_MIN          = new ParametersIndex(index++); //  81
    public static ParametersIndex PARAM_SP25_TIMERS       = new ParametersIndex(index++); //  82
    public static ParametersIndex PARAM_SP25_MAX          = new ParametersIndex(index++); //  83
    public static ParametersIndex PARAM_SP26_MIN          = new ParametersIndex(index++); //  84
    public static ParametersIndex PARAM_SP26_TIMERS       = new ParametersIndex(index++); //  85
    public static ParametersIndex PARAM_SP26_MAX          = new ParametersIndex(index++); //  86
    public static ParametersIndex PARAM_SY_MIN_ALT        = new ParametersIndex(index++); //  87
    public static ParametersIndex PARAM_SY_TIMERS_ALT     = new ParametersIndex(index++); //  88
    public static ParametersIndex PARAM_SY_MAX_ALT        = new ParametersIndex(index++); //  89
    public static ParametersIndex PARAM_N1                = new ParametersIndex(index++); //  90
    public static ParametersIndex PARAM_N2                = new ParametersIndex(index++); //  91
    public static ParametersIndex PARAM_N3                = new ParametersIndex(index++); //  92
    public static ParametersIndex PARAM_N4                = new ParametersIndex(index++); //  93
    public static ParametersIndex PARAM_N5                = new ParametersIndex(index++); //  94
    public static ParametersIndex PARAM_N6                = new ParametersIndex(index++); //  95
    public static ParametersIndex PARAM_N7                = new ParametersIndex(index++); //  96
    public static ParametersIndex PARAM_N8                = new ParametersIndex(index++); //  97
    public static ParametersIndex PARAM_N9                = new ParametersIndex(index++); //  98
    public static ParametersIndex PARAM_N10               = new ParametersIndex(index++); //  99
    public static ParametersIndex PARAM_N11               = new ParametersIndex(index++); // 100
    public static ParametersIndex PARAM_DM_EX_DET_01      = new ParametersIndex(index++); // 101
    public static ParametersIndex PARAM_DM_EX_DET_02      = new ParametersIndex(index++); // 102
    public static ParametersIndex PARAM_DM_EX_DET_03      = new ParametersIndex(index++); // 103
    public static ParametersIndex PARAM_DM_EX_DET_04      = new ParametersIndex(index++); // 104
    public static ParametersIndex PARAM_DM_EX_DET_05      = new ParametersIndex(index++); // 105
    public static ParametersIndex PARAM_DM_EX_DET_06      = new ParametersIndex(index++); // 106
    public static ParametersIndex PARAM_DM_EX_DET_07      = new ParametersIndex(index++); // 107
    public static ParametersIndex PARAM_DM_EX_DET_08      = new ParametersIndex(index++); // 108
    public static ParametersIndex PARAM_DM_EX_DET_09      = new ParametersIndex(index++); // 109
    public static ParametersIndex PARAM_DM_EX_DET_10      = new ParametersIndex(index++); // 110
    public static ParametersIndex PARAM_DM_EX_DET_11      = new ParametersIndex(index++); // 111
    public static ParametersIndex PARAM_DM_EX_DET_12      = new ParametersIndex(index++); // 112
    public static ParametersIndex PARAM_DM_EX_DET_13      = new ParametersIndex(index++); // 113
    public static ParametersIndex PARAM_DM_EX_DET_14      = new ParametersIndex(index++); // 114
    public static ParametersIndex PARAM_DM_EX_DET_15      = new ParametersIndex(index++); // 115
    public static ParametersIndex PARAM_DM_EX_DET_16      = new ParametersIndex(index++); // 116
    public static ParametersIndex PARAM_DM_EX_DET_17      = new ParametersIndex(index++); // 117
    public static ParametersIndex PARAM_DM_EX_DET_18      = new ParametersIndex(index++); // 118
    public static ParametersIndex PARAM_DM_EX_DET_19      = new ParametersIndex(index++); // 119
    public static ParametersIndex PARAM_DM_EX_DET_20      = new ParametersIndex(index++); // 120
    public static ParametersIndex PARAM_DM_EX_DET_21      = new ParametersIndex(index++); // 121
    public static ParametersIndex PARAM_DM_EX_DET_22      = new ParametersIndex(index++); // 122
    public static ParametersIndex PARAM_DM_EX_DET_23      = new ParametersIndex(index++); // 123
    public static ParametersIndex PARAM_DM_EX_DET_24      = new ParametersIndex(index++); // 124
    public static ParametersIndex PARAM_DM_EX_DET_25      = new ParametersIndex(index++); // 125
    public static ParametersIndex PARAM_DM_EX_DET_26      = new ParametersIndex(index++); // 126
    public static ParametersIndex PARAM_DM_EX_DET_27      = new ParametersIndex(index++); // 127
    public static ParametersIndex PARAM_DM_EX_DET_28      = new ParametersIndex(index++); // 128
    public static ParametersIndex PARAM_DM_EX_DET_29      = new ParametersIndex(index++); // 129
    public static ParametersIndex PARAM_DM_EX_DET_30      = new ParametersIndex(index++); // 130
    public static ParametersIndex PARAM_DM_EX_DET_31      = new ParametersIndex(index++); // 131
    public static ParametersIndex PARAM_DM_EX_DET_32      = new ParametersIndex(index++); // 132
    public static ParametersIndex PARAM_DM_EX_DET_33      = new ParametersIndex(index++); // 133
    public static ParametersIndex PARAM_DM_EX_DET_34      = new ParametersIndex(index++); // 134
    public static ParametersIndex PARAM_DM_EX_DET_35      = new ParametersIndex(index++); // 135
    public static ParametersIndex PARAM_DM_EX_DET_36      = new ParametersIndex(index++); // 136
    public static ParametersIndex PARAM_DM_EX_DET_37      = new ParametersIndex(index++); // 137
    public static ParametersIndex PARAM_DM_EX_DET_38      = new ParametersIndex(index++); // 138
    public static ParametersIndex PARAM_DM_EX_DET_39      = new ParametersIndex(index++); // 139
    public static ParametersIndex PARAM_DM_EX_DET_40      = new ParametersIndex(index++); // 140
    public static ParametersIndex PARAM_DM_EX_DET_41      = new ParametersIndex(index++); // 141
    public static ParametersIndex PARAM_DM_EX_DET_42      = new ParametersIndex(index++); // 142
    public static ParametersIndex PARAM_DM_EX_DET_43      = new ParametersIndex(index++); // 143
    public static ParametersIndex PARAM_DM_EX_DET_44      = new ParametersIndex(index++); // 144
    public static ParametersIndex PARAM_DM_EX_DET_45      = new ParametersIndex(index++); // 145
    public static ParametersIndex PARAM_DM_EX_DET_46      = new ParametersIndex(index++); // 146
    public static ParametersIndex PARAM_DM_EX_DET_47      = new ParametersIndex(index++); // 147
    public static ParametersIndex PARAM_DM_EX_DET_48      = new ParametersIndex(index++); // 148
    public static ParametersIndex PARAM_DM_EX_DET_49      = new ParametersIndex(index++); // 149
    public static ParametersIndex PARAM_DM_EX_DET_50      = new ParametersIndex(index++); // 150
    public static ParametersIndex PARAM_DM_EX_DET_51      = new ParametersIndex(index++); // 151
    public static ParametersIndex PARAM_DM_EX_DET_52      = new ParametersIndex(index++); // 152
    public static ParametersIndex PARAM_DM_EX_DET_53      = new ParametersIndex(index++); // 153
    public static ParametersIndex PARAM_DM_EX_DET_54      = new ParametersIndex(index++); // 154
    public static ParametersIndex PARAM_DM_EX_DET_55      = new ParametersIndex(index++); // 155
    public static ParametersIndex PARAM_DM_EX_DET_56      = new ParametersIndex(index++); // 156
    public static ParametersIndex PARAM_DM_EX_DET_57      = new ParametersIndex(index++); // 157
    public static ParametersIndex PARAM_DM_EX_DET_58      = new ParametersIndex(index++); // 158
    public static ParametersIndex PARAM_DM_EX_DET_59      = new ParametersIndex(index++); // 159
    public static ParametersIndex PARAM_DM_EX_DET_60      = new ParametersIndex(index++); // 160
    public static ParametersIndex PARAM_DM_EX_DET_61      = new ParametersIndex(index++); // 161
    public static ParametersIndex PARAM_DM_EX_DET_62      = new ParametersIndex(index++); // 162
    public static ParametersIndex PARAM_DM_EX_DET_63      = new ParametersIndex(index++); // 163
    public static ParametersIndex PARAM_DM_EX_DET_64      = new ParametersIndex(index++); // 164
    public static ParametersIndex PARAM_DM_EX_DET_65      = new ParametersIndex(index++); // 165
    public static ParametersIndex PARAM_DM_EX_DET_66      = new ParametersIndex(index++); // 166
    public static ParametersIndex PARAM_DM_EX_DET_67      = new ParametersIndex(index++); // 167
    public static ParametersIndex PARAM_DM_EX_DET_68      = new ParametersIndex(index++); // 168
    public static ParametersIndex PARAM_DM_EX_DET_69      = new ParametersIndex(index++); // 169
    public static ParametersIndex PARAM_DM_EX_DET_70      = new ParametersIndex(index++); // 170
    public static ParametersIndex PARAM_DM_EX_DET_71      = new ParametersIndex(index++); // 171
    public static ParametersIndex PARAM_DM_EX_DET_72      = new ParametersIndex(index++); // 172
    public static ParametersIndex PARAM_DM_EX_DET_73      = new ParametersIndex(index++); // 173
    public static ParametersIndex PARAM_DM_EX_DET_74      = new ParametersIndex(index++); // 174
    public static ParametersIndex PARAM_DM_EX_DET_75      = new ParametersIndex(index++); // 175
    public static ParametersIndex PARAM_DM_EX_DET_76      = new ParametersIndex(index++); // 176
    public static ParametersIndex PARAM_DM_EX_DET_77      = new ParametersIndex(index++); // 177
    public static ParametersIndex PARAM_DM_EX_DET_78      = new ParametersIndex(index++); // 178
    public static ParametersIndex PARAM_DM_EX_DET_79      = new ParametersIndex(index++); // 179
    public static ParametersIndex PARAM_DM_EX_DET_80      = new ParametersIndex(index++); // 180
    public static ParametersIndex PARAM_DM_EX_DET_81      = new ParametersIndex(index++); // 181
    public static ParametersIndex PARAM_DM_EX_DET_82      = new ParametersIndex(index++); // 182
    public static ParametersIndex PARAM_DM_EX_DET_83      = new ParametersIndex(index++); // 183
    public static ParametersIndex PARAM_DM_EX_DET_84      = new ParametersIndex(index++); // 184
    public static ParametersIndex PARAM_DM_EX_DET_85      = new ParametersIndex(index++); // 185
    public static ParametersIndex PARAM_DM_EX_DET_86      = new ParametersIndex(index++); // 186
    public static ParametersIndex PARAM_DM_EX_DET_87      = new ParametersIndex(index++); // 187
    public static ParametersIndex PARAM_DM_EX_DET_88      = new ParametersIndex(index++); // 188
    public static ParametersIndex PARAM_DM_EX_DET_89      = new ParametersIndex(index++); // 189
    public static ParametersIndex PARAM_DM_EX_DET_90      = new ParametersIndex(index++); // 190
    public static ParametersIndex PARAM_DM_EX_DET_91      = new ParametersIndex(index++); // 191
    public static ParametersIndex PARAM_DM_EX_DET_92      = new ParametersIndex(index++); // 192
    public static ParametersIndex PARAM_DM_EX_DET_93      = new ParametersIndex(index++); // 193
    public static ParametersIndex PARAM_DM_EX_DET_94      = new ParametersIndex(index++); // 194
    public static ParametersIndex PARAM_DM_EX_DET_95      = new ParametersIndex(index++); // 195
    public static ParametersIndex PARAM_DM_EX_DET_96      = new ParametersIndex(index++); // 196
    public static ParametersIndex PARAM_DM_EX_DET_97      = new ParametersIndex(index++); // 197
    public static ParametersIndex PARAM_DM_EX_DET_98      = new ParametersIndex(index++); // 198
    public static ParametersIndex PARAM_DM_EX_DET_99      = new ParametersIndex(index++); // 199
    public static ParametersIndex PARAM_DM_EX_DET_100     = new ParametersIndex(index++); // 200
    public static ParametersIndex PARAM_TK2_VER_NO        = new ParametersIndex(index++); // 201
    public static ParametersIndex PARAM_TK2_STRUCT_NO     = new ParametersIndex(index++); // 202
    public static ParametersIndex PARAM_TK2_CYMAX         = new ParametersIndex(index++); // 203
    public static ParametersIndex PARAM_TK2_ISMASTER      = new ParametersIndex(index++); // 204
    public static ParametersIndex PARAM_TK2_SY_MIN        = new ParametersIndex(index++); // 205
    public static ParametersIndex PARAM_TK2_SY_MAX        = new ParametersIndex(index++); // 206
    public static ParametersIndex PARAM_TK2_SY_TIMERS     = new ParametersIndex(index++); // 207
    public static ParametersIndex PARAM_TK2_SY_OFFSET     = new ParametersIndex(index++); // 208
    public static ParametersIndex PARAM_TK2_SP01_MIN      = new ParametersIndex(index++); // 209
    public static ParametersIndex PARAM_TK2_SP01_TIMERS   = new ParametersIndex(index++); // 210
    public static ParametersIndex PARAM_TK2_SP01_MAX      = new ParametersIndex(index++); // 211
    public static ParametersIndex PARAM_TK2_SP02_MIN      = new ParametersIndex(index++); // 212
    public static ParametersIndex PARAM_TK2_SP02_TIMERS   = new ParametersIndex(index++); // 213
    public static ParametersIndex PARAM_TK2_SP02_MAX      = new ParametersIndex(index++); // 214
    public static ParametersIndex PARAM_TK2_SP03_MIN      = new ParametersIndex(index++); // 215
    public static ParametersIndex PARAM_TK2_SP03_TIMERS   = new ParametersIndex(index++); // 216
    public static ParametersIndex PARAM_TK2_SP03_MAX      = new ParametersIndex(index++); // 217
    public static ParametersIndex PARAM_TK2_SP04_MIN      = new ParametersIndex(index++); // 218
    public static ParametersIndex PARAM_TK2_SP04_TIMERS   = new ParametersIndex(index++); // 219
    public static ParametersIndex PARAM_TK2_SP04_MAX      = new ParametersIndex(index++); // 220
    public static ParametersIndex PARAM_TK2_SP05_MIN      = new ParametersIndex(index++); // 221
    public static ParametersIndex PARAM_TK2_SP05_TIMERS   = new ParametersIndex(index++); // 222
    public static ParametersIndex PARAM_TK2_SP05_MAX      = new ParametersIndex(index++); // 223
    public static ParametersIndex PARAM_TK2_SP06_MIN      = new ParametersIndex(index++); // 224
    public static ParametersIndex PARAM_TK2_SP06_TIMERS   = new ParametersIndex(index++); // 225
    public static ParametersIndex PARAM_TK2_SP06_MAX      = new ParametersIndex(index++); // 226
    public static ParametersIndex PARAM_TK2_SP07_MIN      = new ParametersIndex(index++); // 227
    public static ParametersIndex PARAM_TK2_SP07_TIMERS   = new ParametersIndex(index++); // 228
    public static ParametersIndex PARAM_TK2_SP07_MAX      = new ParametersIndex(index++); // 229
    public static ParametersIndex PARAM_TK2_SP08_MIN      = new ParametersIndex(index++); // 230
    public static ParametersIndex PARAM_TK2_SP08_TIMERS   = new ParametersIndex(index++); // 231
    public static ParametersIndex PARAM_TK2_SP08_MAX      = new ParametersIndex(index++); // 232
    public static ParametersIndex PARAM_TK2_SP09_MIN      = new ParametersIndex(index++); // 233
    public static ParametersIndex PARAM_TK2_SP09_TIMERS   = new ParametersIndex(index++); // 234
    public static ParametersIndex PARAM_TK2_SP09_MAX      = new ParametersIndex(index++); // 235
    public static ParametersIndex PARAM_TK2_SP10_MIN      = new ParametersIndex(index++); // 236
    public static ParametersIndex PARAM_TK2_SP10_TIMERS   = new ParametersIndex(index++); // 237
    public static ParametersIndex PARAM_TK2_SP10_MAX      = new ParametersIndex(index++); // 238
    public static ParametersIndex PARAM_TK2_SP11_MIN      = new ParametersIndex(index++); // 239
    public static ParametersIndex PARAM_TK2_SP11_TIMERS   = new ParametersIndex(index++); // 240
    public static ParametersIndex PARAM_TK2_SP11_MAX      = new ParametersIndex(index++); // 241
    public static ParametersIndex PARAM_TK2_SP12_MIN      = new ParametersIndex(index++); // 242
    public static ParametersIndex PARAM_TK2_SP12_TIMERS   = new ParametersIndex(index++); // 243
    public static ParametersIndex PARAM_TK2_SP12_MAX      = new ParametersIndex(index++); // 244
    public static ParametersIndex PARAM_TK2_SP13_MIN      = new ParametersIndex(index++); // 245
    public static ParametersIndex PARAM_TK2_SP13_TIMERS   = new ParametersIndex(index++); // 246
    public static ParametersIndex PARAM_TK2_SP13_MAX      = new ParametersIndex(index++); // 247
    public static ParametersIndex PARAM_TK2_SP14_MIN      = new ParametersIndex(index++); // 248
    public static ParametersIndex PARAM_TK2_SP14_TIMERS   = new ParametersIndex(index++); // 249
    public static ParametersIndex PARAM_TK2_SP14_MAX      = new ParametersIndex(index++); // 250
    public static ParametersIndex PARAM_TK2_SP15_MIN      = new ParametersIndex(index++); // 251
    public static ParametersIndex PARAM_TK2_SP15_TIMERS   = new ParametersIndex(index++); // 252
    public static ParametersIndex PARAM_TK2_SP15_MAX      = new ParametersIndex(index++); // 253
    public static ParametersIndex PARAM_TK2_SP16_MIN      = new ParametersIndex(index++); // 254
    public static ParametersIndex PARAM_TK2_SP16_TIMERS   = new ParametersIndex(index++); // 255
    public static ParametersIndex PARAM_TK2_SP16_MAX      = new ParametersIndex(index++); // 256
    public static ParametersIndex PARAM_TK2_SP17_MIN      = new ParametersIndex(index++); // 257
    public static ParametersIndex PARAM_TK2_SP17_TIMERS   = new ParametersIndex(index++); // 258
    public static ParametersIndex PARAM_TK2_SP17_MAX      = new ParametersIndex(index++); // 259
    public static ParametersIndex PARAM_TK2_SP18_MIN      = new ParametersIndex(index++); // 260
    public static ParametersIndex PARAM_TK2_SP18_TIMERS   = new ParametersIndex(index++); // 261
    public static ParametersIndex PARAM_TK2_SP18_MAX      = new ParametersIndex(index++); // 262
    public static ParametersIndex PARAM_TK2_SP19_MIN      = new ParametersIndex(index++); // 263
    public static ParametersIndex PARAM_TK2_SP19_TIMERS   = new ParametersIndex(index++); // 264
    public static ParametersIndex PARAM_TK2_SP19_MAX      = new ParametersIndex(index++); // 265
    public static ParametersIndex PARAM_TK2_SP20_MIN      = new ParametersIndex(index++); // 266
    public static ParametersIndex PARAM_TK2_SP20_TIMERS   = new ParametersIndex(index++); // 267
    public static ParametersIndex PARAM_TK2_SP20_MAX      = new ParametersIndex(index++); // 268
    public static ParametersIndex PARAM_TK2_SP21_MIN      = new ParametersIndex(index++); // 269
    public static ParametersIndex PARAM_TK2_SP21_TIMERS   = new ParametersIndex(index++); // 270
    public static ParametersIndex PARAM_TK2_SP21_MAX      = new ParametersIndex(index++); // 271
    public static ParametersIndex PARAM_TK2_SP22_MIN      = new ParametersIndex(index++); // 272
    public static ParametersIndex PARAM_TK2_SP22_TIMERS   = new ParametersIndex(index++); // 273
    public static ParametersIndex PARAM_TK2_SP22_MAX      = new ParametersIndex(index++); // 274
    public static ParametersIndex PARAM_TK2_SP23_MIN      = new ParametersIndex(index++); // 275
    public static ParametersIndex PARAM_TK2_SP23_TIMERS   = new ParametersIndex(index++); // 276
    public static ParametersIndex PARAM_TK2_SP23_MAX      = new ParametersIndex(index++); // 277
    public static ParametersIndex PARAM_TK2_SP24_MIN      = new ParametersIndex(index++); // 278
    public static ParametersIndex PARAM_TK2_SP24_TIMERS   = new ParametersIndex(index++); // 279
    public static ParametersIndex PARAM_TK2_SP24_MAX      = new ParametersIndex(index++); // 280
    public static ParametersIndex PARAM_TK2_SP25_MIN      = new ParametersIndex(index++); // 281
    public static ParametersIndex PARAM_TK2_SP25_TIMERS   = new ParametersIndex(index++); // 282
    public static ParametersIndex PARAM_TK2_SP25_MAX      = new ParametersIndex(index++); // 283
    public static ParametersIndex PARAM_TK2_SP26_MIN      = new ParametersIndex(index++); // 284
    public static ParametersIndex PARAM_TK2_SP26_TIMERS   = new ParametersIndex(index++); // 285
    public static ParametersIndex PARAM_TK2_SP26_MAX      = new ParametersIndex(index++); // 286
    public static ParametersIndex PARAM_TK2_SY_MIN_ALT    = new ParametersIndex(index++); // 287
    public static ParametersIndex PARAM_TK2_SY_TIMERS_ALT = new ParametersIndex(index++); // 288
    public static ParametersIndex PARAM_TK2_SY_MAX_ALT    = new ParametersIndex(index++); // 289
    public static ParametersIndex PARAM_TK2_N1            = new ParametersIndex(index++); // 290
    public static ParametersIndex PARAM_TK2_N2            = new ParametersIndex(index++); // 291
    public static ParametersIndex PARAM_TK2_N3            = new ParametersIndex(index++); // 292
    public static ParametersIndex PARAM_TK2_N4            = new ParametersIndex(index++); // 293
    public static ParametersIndex PARAM_TK2_N5            = new ParametersIndex(index++); // 294
    public static ParametersIndex PARAM_TK2_N6            = new ParametersIndex(index++); // 295
    public static ParametersIndex PARAM_TK2_N7            = new ParametersIndex(index++); // 296
    public static ParametersIndex PARAM_TK2_N8            = new ParametersIndex(index++); // 297
    public static ParametersIndex PARAM_TK2_N9            = new ParametersIndex(index++); // 298
    public static ParametersIndex PARAM_TK2_N10           = new ParametersIndex(index++); // 299
    public static ParametersIndex PARAM_TK2_N11           = new ParametersIndex(index++); // 300
    public static ParametersIndex PARAM_TK3_VER_NO        = new ParametersIndex(index++); // 301
    public static ParametersIndex PARAM_TK3_STRUCT_NO     = new ParametersIndex(index++); // 302
    public static ParametersIndex PARAM_TK3_CYMAX         = new ParametersIndex(index++); // 303
    public static ParametersIndex PARAM_TK3_ISMASTER      = new ParametersIndex(index++); // 304
    public static ParametersIndex PARAM_TK3_SY_MIN        = new ParametersIndex(index++); // 305
    public static ParametersIndex PARAM_TK3_SY_MAX        = new ParametersIndex(index++); // 306
    public static ParametersIndex PARAM_TK3_SY_TIMERS     = new ParametersIndex(index++); // 307
    public static ParametersIndex PARAM_TK3_SY_OFFSET     = new ParametersIndex(index++); // 308
    public static ParametersIndex PARAM_TK3_SP01_MIN      = new ParametersIndex(index++); // 309
    public static ParametersIndex PARAM_TK3_SP01_TIMERS   = new ParametersIndex(index++); // 310
    public static ParametersIndex PARAM_TK3_SP01_MAX      = new ParametersIndex(index++); // 311
    public static ParametersIndex PARAM_TK3_SP02_MIN      = new ParametersIndex(index++); // 312
    public static ParametersIndex PARAM_TK3_SP02_TIMERS   = new ParametersIndex(index++); // 313
    public static ParametersIndex PARAM_TK3_SP02_MAX      = new ParametersIndex(index++); // 314
    public static ParametersIndex PARAM_TK3_SP03_MIN      = new ParametersIndex(index++); // 315
    public static ParametersIndex PARAM_TK3_SP03_TIMERS   = new ParametersIndex(index++); // 316
    public static ParametersIndex PARAM_TK3_SP03_MAX      = new ParametersIndex(index++); // 317
    public static ParametersIndex PARAM_TK3_SP04_MIN      = new ParametersIndex(index++); // 318
    public static ParametersIndex PARAM_TK3_SP04_TIMERS   = new ParametersIndex(index++); // 319
    public static ParametersIndex PARAM_TK3_SP04_MAX      = new ParametersIndex(index++); // 320
    public static ParametersIndex PARAM_TK3_SP05_MIN      = new ParametersIndex(index++); // 321
    public static ParametersIndex PARAM_TK3_SP05_TIMERS   = new ParametersIndex(index++); // 322
    public static ParametersIndex PARAM_TK3_SP05_MAX      = new ParametersIndex(index++); // 323
    public static ParametersIndex PARAM_TK3_SP06_MIN      = new ParametersIndex(index++); // 324
    public static ParametersIndex PARAM_TK3_SP06_TIMERS   = new ParametersIndex(index++); // 325
    public static ParametersIndex PARAM_TK3_SP06_MAX      = new ParametersIndex(index++); // 326
    public static ParametersIndex PARAM_TK3_SP07_MIN      = new ParametersIndex(index++); // 327
    public static ParametersIndex PARAM_TK3_SP07_TIMERS   = new ParametersIndex(index++); // 328
    public static ParametersIndex PARAM_TK3_SP07_MAX      = new ParametersIndex(index++); // 329
    public static ParametersIndex PARAM_TK3_SP08_MIN      = new ParametersIndex(index++); // 330
    public static ParametersIndex PARAM_TK3_SP08_TIMERS   = new ParametersIndex(index++); // 331
    public static ParametersIndex PARAM_TK3_SP08_MAX      = new ParametersIndex(index++); // 332
    public static ParametersIndex PARAM_TK3_SP09_MIN      = new ParametersIndex(index++); // 333
    public static ParametersIndex PARAM_TK3_SP09_TIMERS   = new ParametersIndex(index++); // 334
    public static ParametersIndex PARAM_TK3_SP09_MAX      = new ParametersIndex(index++); // 335
    public static ParametersIndex PARAM_TK3_SP10_MIN      = new ParametersIndex(index++); // 336
    public static ParametersIndex PARAM_TK3_SP10_TIMERS   = new ParametersIndex(index++); // 337
    public static ParametersIndex PARAM_TK3_SP10_MAX      = new ParametersIndex(index++); // 338
    public static ParametersIndex PARAM_TK3_SP11_MIN      = new ParametersIndex(index++); // 339
    public static ParametersIndex PARAM_TK3_SP11_TIMERS   = new ParametersIndex(index++); // 340
    public static ParametersIndex PARAM_TK3_SP11_MAX      = new ParametersIndex(index++); // 341
    public static ParametersIndex PARAM_TK3_SP12_MIN      = new ParametersIndex(index++); // 342
    public static ParametersIndex PARAM_TK3_SP12_TIMERS   = new ParametersIndex(index++); // 343
    public static ParametersIndex PARAM_TK3_SP12_MAX      = new ParametersIndex(index++); // 344
    public static ParametersIndex PARAM_TK3_SP13_MIN      = new ParametersIndex(index++); // 345
    public static ParametersIndex PARAM_TK3_SP13_TIMERS   = new ParametersIndex(index++); // 346
    public static ParametersIndex PARAM_TK3_SP13_MAX      = new ParametersIndex(index++); // 347
    public static ParametersIndex PARAM_TK3_SP14_MIN      = new ParametersIndex(index++); // 348
    public static ParametersIndex PARAM_TK3_SP14_TIMERS   = new ParametersIndex(index++); // 349
    public static ParametersIndex PARAM_TK3_SP14_MAX      = new ParametersIndex(index++); // 350
    public static ParametersIndex PARAM_TK3_SP15_MIN      = new ParametersIndex(index++); // 351
    public static ParametersIndex PARAM_TK3_SP15_TIMERS   = new ParametersIndex(index++); // 352
    public static ParametersIndex PARAM_TK3_SP15_MAX      = new ParametersIndex(index++); // 353
    public static ParametersIndex PARAM_TK3_SP16_MIN      = new ParametersIndex(index++); // 354
    public static ParametersIndex PARAM_TK3_SP16_TIMERS   = new ParametersIndex(index++); // 355
    public static ParametersIndex PARAM_TK3_SP16_MAX      = new ParametersIndex(index++); // 356
    public static ParametersIndex PARAM_TK3_SP17_MIN      = new ParametersIndex(index++); // 357
    public static ParametersIndex PARAM_TK3_SP17_TIMERS   = new ParametersIndex(index++); // 358
    public static ParametersIndex PARAM_TK3_SP17_MAX      = new ParametersIndex(index++); // 359
    public static ParametersIndex PARAM_TK3_SP18_MIN      = new ParametersIndex(index++); // 360
    public static ParametersIndex PARAM_TK3_SP18_TIMERS   = new ParametersIndex(index++); // 361
    public static ParametersIndex PARAM_TK3_SP18_MAX      = new ParametersIndex(index++); // 362
    public static ParametersIndex PARAM_TK3_SP19_MIN      = new ParametersIndex(index++); // 363
    public static ParametersIndex PARAM_TK3_SP19_TIMERS   = new ParametersIndex(index++); // 364
    public static ParametersIndex PARAM_TK3_SP19_MAX      = new ParametersIndex(index++); // 365
    public static ParametersIndex PARAM_TK3_SP20_MIN      = new ParametersIndex(index++); // 366
    public static ParametersIndex PARAM_TK3_SP20_TIMERS   = new ParametersIndex(index++); // 367
    public static ParametersIndex PARAM_TK3_SP20_MAX      = new ParametersIndex(index++); // 368
    public static ParametersIndex PARAM_TK3_SP21_MIN      = new ParametersIndex(index++); // 369
    public static ParametersIndex PARAM_TK3_SP21_TIMERS   = new ParametersIndex(index++); // 370
    public static ParametersIndex PARAM_TK3_SP21_MAX      = new ParametersIndex(index++); // 371
    public static ParametersIndex PARAM_TK3_SP22_MIN      = new ParametersIndex(index++); // 372
    public static ParametersIndex PARAM_TK3_SP22_TIMERS   = new ParametersIndex(index++); // 373
    public static ParametersIndex PARAM_TK3_SP22_MAX      = new ParametersIndex(index++); // 374
    public static ParametersIndex PARAM_TK3_SP23_MIN      = new ParametersIndex(index++); // 375
    public static ParametersIndex PARAM_TK3_SP23_TIMERS   = new ParametersIndex(index++); // 376
    public static ParametersIndex PARAM_TK3_SP23_MAX      = new ParametersIndex(index++); // 377
    public static ParametersIndex PARAM_TK3_SP24_MIN      = new ParametersIndex(index++); // 378
    public static ParametersIndex PARAM_TK3_SP24_TIMERS   = new ParametersIndex(index++); // 379
    public static ParametersIndex PARAM_TK3_SP24_MAX      = new ParametersIndex(index++); // 380
    public static ParametersIndex PARAM_TK3_SP25_MIN      = new ParametersIndex(index++); // 381
    public static ParametersIndex PARAM_TK3_SP25_TIMERS   = new ParametersIndex(index++); // 382
    public static ParametersIndex PARAM_TK3_SP25_MAX      = new ParametersIndex(index++); // 383
    public static ParametersIndex PARAM_TK3_SP26_MIN      = new ParametersIndex(index++); // 384
    public static ParametersIndex PARAM_TK3_SP26_TIMERS   = new ParametersIndex(index++); // 385
    public static ParametersIndex PARAM_TK3_SP26_MAX      = new ParametersIndex(index++); // 386
    public static ParametersIndex PARAM_TK3_SY_MIN_ALT    = new ParametersIndex(index++); // 387
    public static ParametersIndex PARAM_TK3_SY_TIMERS_ALT = new ParametersIndex(index++); // 388
    public static ParametersIndex PARAM_TK3_SY_MAX_ALT    = new ParametersIndex(index++); // 389
    public static ParametersIndex PARAM_TK3_N1            = new ParametersIndex(index++); // 390
    public static ParametersIndex PARAM_TK3_N2            = new ParametersIndex(index++); // 391
    public static ParametersIndex PARAM_TK3_N3            = new ParametersIndex(index++); // 392
    public static ParametersIndex PARAM_TK3_N4            = new ParametersIndex(index++); // 393
    public static ParametersIndex PARAM_TK3_N5            = new ParametersIndex(index++); // 394
    public static ParametersIndex PARAM_TK3_N6            = new ParametersIndex(index++); // 395
    public static ParametersIndex PARAM_TK3_N7            = new ParametersIndex(index++); // 396
    public static ParametersIndex PARAM_TK3_N8            = new ParametersIndex(index++); // 397
    public static ParametersIndex PARAM_TK3_N9            = new ParametersIndex(index++); // 398
    public static ParametersIndex PARAM_TK3_N10           = new ParametersIndex(index++); // 399
    public static ParametersIndex PARAM_TK3_N11           = new ParametersIndex(index++); // 400
    
    public ParametersIndex PARAM_FIRST_DETECTOR    = PARAM_DM_EX_DET_01;
    public final static int MAX_DETECTORS = 100;
    
    public int getMinBySP(int nodeId, int sp) {
        if (sp < 1 || sp > 26) {
            return 0;
        }
        
        switch (nodeId) {
            case 2:
                return parameters[NetiveiIsraelParameters.PARAM_TK2_SP01_MIN.getIndex() + (sp - 1) * 3 - 1];
            case 3:
                return parameters[NetiveiIsraelParameters.PARAM_TK3_SP01_MIN.getIndex() + (sp - 1) * 3 - 1];
            default:
                return parameters[NetiveiIsraelParameters.PARAM_SP01_MIN.getIndex() + (sp - 1) * 3 - 1];
        }
    }
    
    public int getMaxBySP(int nodeId, int sp) {
        if (sp < 1 || sp > 26) {
            return 0;
        }
        
        switch (nodeId) {
            case 2:
                return parameters[NetiveiIsraelParameters.PARAM_TK2_SP01_MAX.getIndex() + (sp - 1) * 3 - 1];
            case 3:
                return parameters[NetiveiIsraelParameters.PARAM_TK3_SP01_MAX.getIndex() + (sp - 1) * 3 - 1];
            default:
                return parameters[NetiveiIsraelParameters.PARAM_SP01_MAX.getIndex() + (sp - 1) * 3 - 1];
        }
    }
    
    public int getMinTypeBySP(int nodeId, int sp) {
        if (sp < 1 || sp > 26) {
            return 0;
        }
        
        switch (nodeId) {
            case 2:
                return getMinType(parameters[NetiveiIsraelParameters.PARAM_TK2_SP01_TIMERS.getIndex() + (sp - 1) * 3 - 1]);
            case 3:
                return getMinType(parameters[NetiveiIsraelParameters.PARAM_TK3_SP01_TIMERS.getIndex() + (sp - 1) * 3 - 1]);
            default:
                return getMinType(parameters[NetiveiIsraelParameters.PARAM_SP01_TIMERS.getIndex() + (sp - 1) * 3 - 1]);
        }
    }
    
    public int getMaxTypeBySP(int nodeId, int sp) {
        if (sp < 1 || sp > 26) {
            return 0;
        }
        
        switch (nodeId) {
            case 2:
                return getMaxType(parameters[NetiveiIsraelParameters.PARAM_TK2_SP01_TIMERS.getIndex() + (sp - 1) * 3 - 1]);
            case 3:
                return getMaxType(parameters[NetiveiIsraelParameters.PARAM_TK3_SP01_TIMERS.getIndex() + (sp - 1) * 3 - 1]);
            default:
                return getMaxType(parameters[NetiveiIsraelParameters.PARAM_SP01_TIMERS.getIndex() + (sp - 1) * 3 - 1]);
        }
    }
    
    public int getMinType(int value) {
        if (value > 100) {
            return value;
        } else {
            return value % 10;
        }
    }
    
    public int getMaxType(int value) {
        if (value > 100) {
            return value;
        } else {
            return value / 10;
        }
    }

	public int getCycle() {
        return parameters[PARAM_CYMAX.getIndex() - 1];
	}
	
    public int getCycle(int nodeId) {
        if (nodeId < 1 || nodeId > Var.controller.nodes.length) {
            nodeId = 1;
        }
        
        switch (nodeId) {
            case 2: return parameters[PARAM_TK2_CYMAX.getIndex() - 1];
            case 3: return parameters[PARAM_TK3_CYMAX.getIndex() - 1];
            default: return getCycle();
        }
    }
    
    public int getStructure() {
        return parameters[PARAM_STRUCT_NO.getIndex() - 1];
    }
    
    public int getStructure(int nodeId) {
        if (nodeId < 1 || nodeId > Var.controller.nodes.length) {
            nodeId = 1;
        }
        
        switch (nodeId) {
            case 2: return parameters[PARAM_TK2_STRUCT_NO.getIndex() - 1];
            case 3: return parameters[PARAM_TK3_STRUCT_NO.getIndex() - 1];
            default: return getStructure();
        }
    }
    
    public int getOffset() {
        return parameters[PARAM_SY_OFFSET.getIndex() - 1];
    }
    
    public int getOffset(int nodeId) {
        if (nodeId < 1 || nodeId > Var.controller.nodes.length) {
            nodeId = 1;
        }
        
        switch (nodeId) {
            case 2: return parameters[PARAM_TK2_SY_OFFSET.getIndex() - 1];
            case 3: return parameters[PARAM_TK3_SY_OFFSET.getIndex() - 1];
            default: return getOffset();
        }
    }
    
    public boolean isGWEnable(int programNumber) {
        return isGWEnable(1, programNumber);
    }
    
    public boolean isGWEnable(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 3) {
                    return parameters[PARAM_TK3_ISMASTER.getIndex() - 1] != 0;
                } else if (nodeId == 2) {
                    return parameters[PARAM_TK2_ISMASTER.getIndex() - 1] != 0;
                } else {
                    return parameters[PARAM_ISMASTER    .getIndex() - 1] != 0;
                }
            } else {
                return getParameters(programNumber)[PARAM_ISMASTER.getIndex() - 1] != GW_INDEPENDENT;
            }
        }
        return false;
    }
    
    public boolean isMaster(int programNumber) {
        return isMaster(1, programNumber);
    }
    
    public boolean isMaster(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 3) {
                    return parameters[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_MASTER || parameters[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else if (nodeId == 2) {
                    return parameters[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_MASTER || parameters[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else {
                    return parameters[PARAM_ISMASTER    .getIndex() - 1] == GW_MASTER || parameters[PARAM_ISMASTER    .getIndex() - 1] == GW_LOCAL_SUBMASTER;
                }
            } else {
                if (nodeId == 3) {
                    return getParameters(programNumber)[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_MASTER || getParameters(programNumber)[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_MASTER || getParameters(programNumber)[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else {                          
                    return getParameters(programNumber)[PARAM_ISMASTER    .getIndex() - 1] == GW_MASTER || getParameters(programNumber)[PARAM_ISMASTER    .getIndex() - 1] == GW_LOCAL_SUBMASTER;
                }
            }
        }
        return false;
    }
    
    public boolean isSlave(int programNumber) {
        return isSlave(1, programNumber);
    }
    
    public boolean isSlave(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 3) {
                    return parameters[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_SLAVE;
                } else if (nodeId == 2) {
                    return parameters[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_SLAVE;
                } else {
                    return parameters[PARAM_ISMASTER    .getIndex() - 1] == GW_SLAVE;
                }
            } else {
                if (nodeId == 3) {
                    return getParameters(programNumber)[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_SLAVE;
                } else if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_SLAVE;
                } else {
                    return getParameters(programNumber)[PARAM_ISMASTER    .getIndex() - 1] == GW_SLAVE;
                }
            }
        }
        return false;
    }
    
    public boolean isClockSync(int programNumber) {
        return isClockSync(1, programNumber);
    }
    
    public boolean isClockSync(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 3) {
                    return parameters[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_GLOBAL_CLOCK;
                } else if (nodeId == 2) {
                    return parameters[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_GLOBAL_CLOCK;
                } else {
                    return parameters[PARAM_ISMASTER    .getIndex() - 1] == GW_GLOBAL_CLOCK;
                }
            } else {
                if (nodeId == 3) {
                    return getParameters(programNumber)[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_GLOBAL_CLOCK;
                } else if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_GLOBAL_CLOCK;
                } else {
                    return getParameters(programNumber)[PARAM_ISMASTER    .getIndex() - 1] == GW_GLOBAL_CLOCK;
                }
            }
        }
        return false;
    }
    
    public boolean isSubmaster(int programNumber) {
        return isSubmaster(1, programNumber);
    }
    
    public boolean isSubmaster(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 3) {
                    return parameters[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_SUBMASTER;
                } else if (nodeId == 2) {
                    return parameters[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_SUBMASTER;
                } else {
                    return parameters[PARAM_ISMASTER    .getIndex() - 1] == GW_SUBMASTER;
                }
            } else {
                if (nodeId == 3) {
                    return getParameters(programNumber)[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_SUBMASTER;
                } else if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_SUBMASTER;
                } else {
                    return getParameters(programNumber)[PARAM_ISMASTER    .getIndex() - 1] == GW_SUBMASTER;
                }
            }
        }
        return false;
    }
    
    public boolean isLocalSubmaster(int programNumber) {
        return isLocalSubmaster(1, programNumber);
    }
    
    public boolean isLocalSubmaster(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 3) {
                    return parameters[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else if (nodeId == 2) {
                    return parameters[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else {
                    return parameters[PARAM_ISMASTER    .getIndex() - 1] == GW_LOCAL_SUBMASTER;
                }
            } else {
                if (nodeId == 3) {
                    return getParameters(programNumber)[PARAM_TK3_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_TK2_ISMASTER.getIndex() - 1] == GW_LOCAL_SUBMASTER;
                } else {
                    return getParameters(programNumber)[PARAM_ISMASTER    .getIndex() - 1] == GW_LOCAL_SUBMASTER;
                }
            }
        }
        return false;
    }

    public boolean isSetDemandDetector(int nodeId, int parametersId) {
        if (parametersId < 1 || parametersId > MAX_DETECTORS) {
            return false;
        }
        
        return parameters[PARAM_FIRST_DETECTOR.getIndex() - 1 + parametersId - 1] == DETECTOR_SET ||
               parameters[PARAM_FIRST_DETECTOR.getIndex() - 1 + parametersId - 1] == DETECTOR_SET_DEMAND;
    }
    
    public boolean isSetExtensionDetector(int nodeId, int parametersId) {
        if (parametersId < 1 || parametersId > MAX_DETECTORS) {
            return false;
        }
        
        return parameters[PARAM_FIRST_DETECTOR.getIndex() - 1 + parametersId - 1] == DETECTOR_SET ||
               parameters[PARAM_FIRST_DETECTOR.getIndex() - 1 + parametersId - 1] == DETECTOR_SET_EXTENSION;
    }
    
    public boolean isDisabledDetector(int nodeId, int parametersId) {
        return false;
    }
	
	public void initializeParameters()
	{
        pars_P01 = VtVarStrukt.init(Var.tk1, "DVI35_P01", initializeParameters(DVI35_P01, parameters_indexes, new int[] {  1, 51 }), true, true, true);
        pars_P02 = VtVarStrukt.init(Var.tk1, "DVI35_P02", initializeParameters(DVI35_P02, parameters_indexes, new int[] {  2, 52 }), true, true, true);
        pars_P03 = VtVarStrukt.init(Var.tk1, "DVI35_P03", initializeParameters(DVI35_P03, parameters_indexes, new int[] {  3, 53 }), true, true, true);
        pars_P04 = VtVarStrukt.init(Var.tk1, "DVI35_P04", initializeParameters(DVI35_P04, parameters_indexes, new int[] {  4, 54 }), true, true, true);
        pars_P05 = VtVarStrukt.init(Var.tk1, "DVI35_P05", initializeParameters(DVI35_P05, parameters_indexes, new int[] {  5, 55 }), true, true, true);
        pars_P06 = VtVarStrukt.init(Var.tk1, "DVI35_P06", initializeParameters(DVI35_P06, parameters_indexes, new int[] {  6, 56 }), true, true, true);      
        pars_P07 = VtVarStrukt.init(Var.tk1, "DVI35_P07", initializeParameters(DVI35_P07, parameters_indexes, new int[] {  7, 57 }), true, true, true);
        pars_P08 = VtVarStrukt.init(Var.tk1, "DVI35_P08", initializeParameters(DVI35_P08, parameters_indexes, new int[] {  8, 58 }), true, true, true);                      
        pars_P09 = VtVarStrukt.init(Var.tk1, "DVI35_P09", initializeParameters(DVI35_P09, parameters_indexes, new int[] {  9, 59 }), true, true, true);
        pars_P10 = VtVarStrukt.init(Var.tk1, "DVI35_P10", initializeParameters(DVI35_P10, parameters_indexes, new int[] { 10, 60 }), true, true, true);      
        pars_P11 = VtVarStrukt.init(Var.tk1, "DVI35_P11", initializeParameters(DVI35_P11, parameters_indexes, new int[] { 11, 61 }), true, true, true);
        pars_P12 = VtVarStrukt.init(Var.tk1, "DVI35_P12", initializeParameters(DVI35_P12, parameters_indexes, new int[] { 12, 62 }), true, true, true);
        pars_P13 = VtVarStrukt.init(Var.tk1, "DVI35_P13", initializeParameters(DVI35_P13, parameters_indexes, new int[] { 13, 63 }), true, true, true);
        pars_P14 = VtVarStrukt.init(Var.tk1, "DVI35_P14", initializeParameters(DVI35_P14, parameters_indexes, new int[] { 14, 64 }), true, true, true);
        pars_P15 = VtVarStrukt.init(Var.tk1, "DVI35_P15", initializeParameters(DVI35_P15, parameters_indexes, new int[] { 15, 65 }), true, true, true);      
        pars_P16 = VtVarStrukt.init(Var.tk1, "DVI35_P16", initializeParameters(DVI35_P16, parameters_indexes, new int[] { 16, 66 }), true, true, true);      
        pars_P17 = VtVarStrukt.init(Var.tk1, "DVI35_P17", initializeParameters(DVI35_P17, parameters_indexes, new int[] { 17, 67 }), true, true, true);      
        pars_P18 = VtVarStrukt.init(Var.tk1, "DVI35_P18", initializeParameters(DVI35_P18, parameters_indexes, new int[] { 18, 68 }), true, true, true);      
        pars_P19 = VtVarStrukt.init(Var.tk1, "DVI35_P19", initializeParameters(DVI35_P19, parameters_indexes, new int[] { 19, 69 }), true, true, true);      
        pars_P20 = VtVarStrukt.init(Var.tk1, "DVI35_P20", initializeParameters(DVI35_P20, parameters_indexes, new int[] { 20, 70 }), true, true, true);      
        pars_P21 = VtVarStrukt.init(Var.tk1, "DVI35_P21", initializeParameters(DVI35_P21, parameters_indexes, new int[] { 21, 71 }), true, true, true);      
        pars_P22 = VtVarStrukt.init(Var.tk1, "DVI35_P22", initializeParameters(DVI35_P22, parameters_indexes, new int[] { 22, 72 }), true, true, true);      
        pars_P23 = VtVarStrukt.init(Var.tk1, "DVI35_P23", initializeParameters(DVI35_P23, parameters_indexes, new int[] { 23, 73 }), true, true, true);      
        pars_P24 = VtVarStrukt.init(Var.tk1, "DVI35_P24", initializeParameters(DVI35_P24, parameters_indexes, new int[] { 24, 74 }), true, true, true);      
        pars_P25 = VtVarStrukt.init(Var.tk1, "DVI35_P25", initializeParameters(DVI35_P25, parameters_indexes, new int[] { 25, 75 }), true, true, true);      
        pars_P26 = VtVarStrukt.init(Var.tk1, "DVI35_P26", initializeParameters(DVI35_P26, parameters_indexes, new int[] { 26, 76 }), true, true, true);      
        pars_P27 = VtVarStrukt.init(Var.tk1, "DVI35_P27", initializeParameters(DVI35_P27, parameters_indexes, new int[] { 27, 77 }), true, true, true);      
        pars_P28 = VtVarStrukt.init(Var.tk1, "DVI35_P28", initializeParameters(DVI35_P28, parameters_indexes, new int[] { 28, 78 }), true, true, true);      
        pars_P29 = VtVarStrukt.init(Var.tk1, "DVI35_P29", initializeParameters(DVI35_P29, parameters_indexes, new int[] { 29, 79 }), true, true, true);      
        pars_P30 = VtVarStrukt.init(Var.tk1, "DVI35_P30", initializeParameters(DVI35_P30, parameters_indexes, new int[] { 30, 80 }), true, true, true);      
        pars_P31 = VtVarStrukt.init(Var.tk1, "DVI35_P31", initializeParameters(DVI35_P31, parameters_indexes, new int[] { 31, 81 }), true, true, true);      
        pars_P32 = VtVarStrukt.init(Var.tk1, "DVI35_P32", initializeParameters(DVI35_P32, parameters_indexes, new int[] { 32, 82 }), true, true, true);

        gap_Det01 = VtVarStrukt.init(Var.tk1, "DVI35_GAP01", initializeDetectorsParameters(DVI35_GAP01), true, true, true);
        gap_Det02 = VtVarStrukt.init(Var.tk1, "DVI35_GAP02", initializeDetectorsParameters(DVI35_GAP02), true, true, true);
        gap_Det03 = VtVarStrukt.init(Var.tk1, "DVI35_GAP03", initializeDetectorsParameters(DVI35_GAP03), true, true, true);
        gap_Det04 = VtVarStrukt.init(Var.tk1, "DVI35_GAP04", initializeDetectorsParameters(DVI35_GAP04), true, true, true);
        gap_Det05 = VtVarStrukt.init(Var.tk1, "DVI35_GAP05", initializeDetectorsParameters(DVI35_GAP05), true, true, true);
        gap_Det06 = VtVarStrukt.init(Var.tk1, "DVI35_GAP06", initializeDetectorsParameters(DVI35_GAP06), true, true, true);
        gap_Det07 = VtVarStrukt.init(Var.tk1, "DVI35_GAP07", initializeDetectorsParameters(DVI35_GAP07), true, true, true);
        gap_Det08 = VtVarStrukt.init(Var.tk1, "DVI35_GAP08", initializeDetectorsParameters(DVI35_GAP08), true, true, true);
        gap_Det09 = VtVarStrukt.init(Var.tk1, "DVI35_GAP09", initializeDetectorsParameters(DVI35_GAP09), true, true, true);
        gap_Det10 = VtVarStrukt.init(Var.tk1, "DVI35_GAP10", initializeDetectorsParameters(DVI35_GAP10), true, true, true);
        gap_Det11 = VtVarStrukt.init(Var.tk1, "DVI35_GAP11", initializeDetectorsParameters(DVI35_GAP11), true, true, true);
        gap_Det12 = VtVarStrukt.init(Var.tk1, "DVI35_GAP12", initializeDetectorsParameters(DVI35_GAP12), true, true, true);
        gap_Det13 = VtVarStrukt.init(Var.tk1, "DVI35_GAP13", initializeDetectorsParameters(DVI35_GAP13), true, true, true);
        gap_Det14 = VtVarStrukt.init(Var.tk1, "DVI35_GAP14", initializeDetectorsParameters(DVI35_GAP14), true, true, true);
        gap_Det15 = VtVarStrukt.init(Var.tk1, "DVI35_GAP15", initializeDetectorsParameters(DVI35_GAP15), true, true, true);
        gap_Det16 = VtVarStrukt.init(Var.tk1, "DVI35_GAP16", initializeDetectorsParameters(DVI35_GAP16), true, true, true);
        gap_Det17 = VtVarStrukt.init(Var.tk1, "DVI35_GAP17", initializeDetectorsParameters(DVI35_GAP17), true, true, true);
        gap_Det18 = VtVarStrukt.init(Var.tk1, "DVI35_GAP18", initializeDetectorsParameters(DVI35_GAP18), true, true, true);
        gap_Det19 = VtVarStrukt.init(Var.tk1, "DVI35_GAP19", initializeDetectorsParameters(DVI35_GAP19), true, true, true);
        gap_Det20 = VtVarStrukt.init(Var.tk1, "DVI35_GAP20", initializeDetectorsParameters(DVI35_GAP20), true, true, true);
        gap_Det21 = VtVarStrukt.init(Var.tk1, "DVI35_GAP21", initializeDetectorsParameters(DVI35_GAP21), true, true, true);
        gap_Det22 = VtVarStrukt.init(Var.tk1, "DVI35_GAP22", initializeDetectorsParameters(DVI35_GAP22), true, true, true);
        gap_Det23 = VtVarStrukt.init(Var.tk1, "DVI35_GAP23", initializeDetectorsParameters(DVI35_GAP23), true, true, true);
        gap_Det24 = VtVarStrukt.init(Var.tk1, "DVI35_GAP24", initializeDetectorsParameters(DVI35_GAP24), true, true, true);
        gap_Det25 = VtVarStrukt.init(Var.tk1, "DVI35_GAP25", initializeDetectorsParameters(DVI35_GAP25), true, true, true);
        gap_Det26 = VtVarStrukt.init(Var.tk1, "DVI35_GAP26", initializeDetectorsParameters(DVI35_GAP26), true, true, true);
        gap_Det27 = VtVarStrukt.init(Var.tk1, "DVI35_GAP27", initializeDetectorsParameters(DVI35_GAP27), true, true, true);
        gap_Det28 = VtVarStrukt.init(Var.tk1, "DVI35_GAP28", initializeDetectorsParameters(DVI35_GAP28), true, true, true);
        gap_Det29 = VtVarStrukt.init(Var.tk1, "DVI35_GAP29", initializeDetectorsParameters(DVI35_GAP29), true, true, true);
        gap_Det30 = VtVarStrukt.init(Var.tk1, "DVI35_GAP30", initializeDetectorsParameters(DVI35_GAP30), true, true, true);
        gap_Det31 = VtVarStrukt.init(Var.tk1, "DVI35_GAP31", initializeDetectorsParameters(DVI35_GAP31), true, true, true);
        gap_Det32 = VtVarStrukt.init(Var.tk1, "DVI35_GAP32", initializeDetectorsParameters(DVI35_GAP32), true, true, true);
        gap_Det33 = VtVarStrukt.init(Var.tk1, "DVI35_GAP33", initializeDetectorsParameters(DVI35_GAP33), true, true, true);
        gap_Det34 = VtVarStrukt.init(Var.tk1, "DVI35_GAP34", initializeDetectorsParameters(DVI35_GAP34), true, true, true);
        gap_Det35 = VtVarStrukt.init(Var.tk1, "DVI35_GAP35", initializeDetectorsParameters(DVI35_GAP35), true, true, true);
        gap_Det36 = VtVarStrukt.init(Var.tk1, "DVI35_GAP36", initializeDetectorsParameters(DVI35_GAP36), true, true, true);
        gap_Det37 = VtVarStrukt.init(Var.tk1, "DVI35_GAP37", initializeDetectorsParameters(DVI35_GAP37), true, true, true);
        gap_Det38 = VtVarStrukt.init(Var.tk1, "DVI35_GAP38", initializeDetectorsParameters(DVI35_GAP38), true, true, true);
        gap_Det39 = VtVarStrukt.init(Var.tk1, "DVI35_GAP39", initializeDetectorsParameters(DVI35_GAP39), true, true, true);
        gap_Det40 = VtVarStrukt.init(Var.tk1, "DVI35_GAP40", initializeDetectorsParameters(DVI35_GAP40), true, true, true);
        gap_Det41 = VtVarStrukt.init(Var.tk1, "DVI35_GAP41", initializeDetectorsParameters(DVI35_GAP41), true, true, true);
        gap_Det42 = VtVarStrukt.init(Var.tk1, "DVI35_GAP42", initializeDetectorsParameters(DVI35_GAP42), true, true, true);
        gap_Det43 = VtVarStrukt.init(Var.tk1, "DVI35_GAP43", initializeDetectorsParameters(DVI35_GAP43), true, true, true);
        gap_Det44 = VtVarStrukt.init(Var.tk1, "DVI35_GAP44", initializeDetectorsParameters(DVI35_GAP44), true, true, true);
        gap_Det45 = VtVarStrukt.init(Var.tk1, "DVI35_GAP45", initializeDetectorsParameters(DVI35_GAP45), true, true, true);
        gap_Det46 = VtVarStrukt.init(Var.tk1, "DVI35_GAP46", initializeDetectorsParameters(DVI35_GAP46), true, true, true);
        gap_Det47 = VtVarStrukt.init(Var.tk1, "DVI35_GAP47", initializeDetectorsParameters(DVI35_GAP47), true, true, true);
        gap_Det48 = VtVarStrukt.init(Var.tk1, "DVI35_GAP48", initializeDetectorsParameters(DVI35_GAP48), true, true, true);
        gap_Det49 = VtVarStrukt.init(Var.tk1, "DVI35_GAP49", initializeDetectorsParameters(DVI35_GAP49), true, true, true);
        gap_Det50 = VtVarStrukt.init(Var.tk1, "DVI35_GAP50", initializeDetectorsParameters(DVI35_GAP50), true, true, true);
        gap_Det51 = VtVarStrukt.init(Var.tk1, "DVI35_GAP51", initializeDetectorsParameters(DVI35_GAP51), true, true, true);
        gap_Det52 = VtVarStrukt.init(Var.tk1, "DVI35_GAP52", initializeDetectorsParameters(DVI35_GAP52), true, true, true);
        gap_Det53 = VtVarStrukt.init(Var.tk1, "DVI35_GAP53", initializeDetectorsParameters(DVI35_GAP53), true, true, true);
        gap_Det54 = VtVarStrukt.init(Var.tk1, "DVI35_GAP54", initializeDetectorsParameters(DVI35_GAP54), true, true, true);
        gap_Det55 = VtVarStrukt.init(Var.tk1, "DVI35_GAP55", initializeDetectorsParameters(DVI35_GAP55), true, true, true);
        gap_Det56 = VtVarStrukt.init(Var.tk1, "DVI35_GAP56", initializeDetectorsParameters(DVI35_GAP56), true, true, true);
        gap_Det57 = VtVarStrukt.init(Var.tk1, "DVI35_GAP57", initializeDetectorsParameters(DVI35_GAP57), true, true, true);
        gap_Det58 = VtVarStrukt.init(Var.tk1, "DVI35_GAP58", initializeDetectorsParameters(DVI35_GAP58), true, true, true);
        gap_Det59 = VtVarStrukt.init(Var.tk1, "DVI35_GAP59", initializeDetectorsParameters(DVI35_GAP59), true, true, true);
        gap_Det60 = VtVarStrukt.init(Var.tk1, "DVI35_GAP60", initializeDetectorsParameters(DVI35_GAP60), true, true, true);
        gap_Det61 = VtVarStrukt.init(Var.tk1, "DVI35_GAP61", initializeDetectorsParameters(DVI35_GAP61), true, true, true);
        gap_Det62 = VtVarStrukt.init(Var.tk1, "DVI35_GAP62", initializeDetectorsParameters(DVI35_GAP62), true, true, true);
        gap_Det63 = VtVarStrukt.init(Var.tk1, "DVI35_GAP63", initializeDetectorsParameters(DVI35_GAP63), true, true, true);
        gap_Det64 = VtVarStrukt.init(Var.tk1, "DVI35_GAP64", initializeDetectorsParameters(DVI35_GAP64), true, true, true);
	}

    public boolean isDuration(int type) {
        return TYPE_DURATION == type;
    }

    public boolean isAbsolute(int type) {
        return TYPE_ABSOLUTE == type;
    }

    public boolean isComplement(int type) {
        return TYPE_COMPLEMENT == type;
    }
    
    public int isByMove(int type) {
        return type > 100 ? type % 100 : 0;
    }

    /**
     * Returns the index of the Dx Enabled/Active parameter if exists and Var.NONE otherwise
     */
    public int getDxActive() {
        // TODO: update index if Dx is required
        return Var.NONE;
    }
}
