package core;

import m0547.Var;

public class PrintHelper {
    public static void DebugPrint(String str) {
        if (Var.isPrintDebug) {
            System.out.println(str);
        }
    }

    public static void DebugPrintNodeDetails(Node node) {
        if (Var.isPrintDebug) {
            System.out.println("Node #" + node.getNummer() + ":");
            System.out.println("\t- Moves: "
                    + "traffic = " + (node.mainTrafficSgs.size() + node.notMainTrafficSgs.size())
                    + " | pedestrain = " + (node.mainPedestrianSgs.size() + node.notMainPedestrianSgs.size())
                    + " | blinkers = " + (node.mainConditionedBlinkSgs.size() + node.notMainConditionedBlinkSgs.size() + node.regularBlinkSgs.size())
                    + " | FFT = " + node.preemptionLampSgs.size());
            System.out.println("\t- Detectors, Inputs and Outputs: "
                    + "D = " + node.demands.size()
                    + "DE = " + node.demandExtensions.size()
                    + "E = " + node.extensions.size()
                    + "Q = " + node.queues.size()
                    + "TP = " + node.pushButtons.size());
        }
    }

    public static void DebugPrintArray(String name, boolean[] array) {
        if (Var.isPrintDebug) {
            System.out.println(name);
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + "\t");
            }
            System.out.println();
        }
    }

    public static void DebugPrintArray(String name, int[] array) {
        if (Var.isPrintDebug) {
            System.out.println(name);
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + "\t");
            }
            System.out.println();
        }
    }

    public static void DebugPrintArray(String name, int[][] array) {
        if (Var.isPrintDebug) {
            System.out.println(name);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++)
                    System.out.print(array[i][j] + "\t");
            }
            System.out.println();
            if (Var.isPrintDebug) {
            }

            // TODO:
            //    public static void DebugPrintArray(String name, LRTDetector[][] array) {
            //    	System.out.println(name);
            //    	for (int i = 0; i < array.length; i++) {
            //    		for (int j = 0; j < array[i].length; j++)
            //    		System.out.print(array[i][j].getName() + "\t");
            //    	}
            //    	System.out.println();
            //    }
        }
    }
}