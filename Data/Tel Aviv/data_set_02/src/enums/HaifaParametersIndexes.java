/**
 * 
 */
package enums;

/**
 * @author ilia
 *
 */
public class HaifaParametersIndexes {
	public static final HaifaParametersIndexes indGreenWaveOffset 	 = new HaifaParametersIndexes(  0);
	public static final HaifaParametersIndexes indPedMaxRed          = new HaifaParametersIndexes(  1);
	public static final HaifaParametersIndexes indCycleTime          = new HaifaParametersIndexes(  2);
	public static final HaifaParametersIndexes indStructureNumber    = new HaifaParametersIndexes(  3);
	public static final HaifaParametersIndexes indIsCrossMaster      = new HaifaParametersIndexes(  4);
	public static final HaifaParametersIndexes indGapSet             = new HaifaParametersIndexes( 98);
	public static final HaifaParametersIndexes indModeSet            = new HaifaParametersIndexes( 99);
	public static final HaifaParametersIndexes indPedMaxRed2         = new HaifaParametersIndexes(101);
	public static final HaifaParametersIndexes indCycleTime2         = new HaifaParametersIndexes(102);
	public static final HaifaParametersIndexes indStructureNumber2   = new HaifaParametersIndexes(103);
	public static final HaifaParametersIndexes indGapSet2            = new HaifaParametersIndexes(198);
	public static final HaifaParametersIndexes indModeSet2           = new HaifaParametersIndexes(199);
	
	private final int index;
	
	HaifaParametersIndexes(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
