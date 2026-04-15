/*
 *    Urheberrecht (C) Swarco Traffic Systems GmbH 2023
 *
 *    Das Urheberrecht dieses Computerprogramms ist Eigentum der Swarco Traffic Systems GmbH, Deutschland.
 *    Die Verwendung dieses Programms oder auch Teilen des Programms sowie das Kopieren des Programms ist nur mit
 *    schriftlicher Genehmigung durch die Swarco Traffic Systems GmbH erlaubt.
 */

package modbus;

public class ModbusSlave {

  public static final int SERIAL_PORT_NR_MIN = 1;
  public static final int SERIAL_PORT_NR_MAX = 6;

  public static final int PARITY_NONE = 0;
  public static final int PARITY_ODD  = 1;
  public static final int PARITY_EVEN = 2;

  public static final int MAX_BITS      = 0xffff;
  public static final int MAX_REGISTERS = 0xffff;

  private static ModbusSlave instance = null;


  private ModbusSlave() {
  }


  /*en
   * Creates an instance of Modbus slave for communication via serial interface (Modbus RTU).
   * 
   * @param serialPortNr number of the serial interface for Modbus communication [SERIAL_PORT_NR_MIN ({@value #SERIAL_PORT_NR_MIN}) - SERIAL_PORT_NR_MAX ({@value #SERIAL_PORT_NR_MAX})]
   * @param baudrate     transmission speed of the serial interface (e.g. 19200)
   * @param parity       parity bit of the serial interface [PARITY_NONE ({@value #PARITY_NONE}), PARITY_ODD ({@value #PARITY_ODD}), PARITY_EVEN ({@value #PARITY_EVEN})]
   * @param dataBits     number of data bits of the serial interface (should be 8)
   * @param stopBits     number of stop bits of the serial interface [1 - 2]
   * @param slaveId      address of the Modbus slave RTU
   * @return ModbusSlave
   */ 
  /**
   * Erzeugt eine Instanz des Modbus-Slave f체r die Kommunikation 체ber eine serielle Schnittstelle (Modbus RTU).
   * 
   * @param serialPortNr Nummer der serielle Schnittstelle f체r die Modbus Kommunikation [SERIAL_PORT_NR_MIN ({@value #SERIAL_PORT_NR_MIN}) - SERIAL_PORT_NR_MAX ({@value #SERIAL_PORT_NR_MAX})]
   * @param baudrate     �?bertragungsgeschwindigkeit der seriellen Schnittstelle in Bit/s (z.B. 19200)
   * @param parity       Parit채tsbit der seriellen Schnittstelle [PARITY_NONE ({@value #PARITY_NONE}), PARITY_ODD ({@value #PARITY_ODD}), PARITY_EVEN ({@value #PARITY_EVEN})]
   * @param dataBits     Anzahl Datenbits der seriellen Schnittstelle (sollte 8 sein)
   * @param stopBits     Anzahl Stoppbits der seriellen Schnittstelle [1 - 2]
   * @param slaveId      Adresse des Modbus-Slave RTU
   * @return ModbusSlave
   */
  public static ModbusSlave createRtu(int serialPortNr, int baudrate, int parity, int dataBits, int stopBits, int slaveId) {
    if (ModbusSlave.instance != null) {
      throw new IllegalStateException("ModbusSlave has already been created.");
    }
    if (serialPortNr < SERIAL_PORT_NR_MIN || serialPortNr > SERIAL_PORT_NR_MAX) {
      throw new IllegalArgumentException("Invalid ModbusSlave-RTU serialPortNr=" + serialPortNr + " (min=" + SERIAL_PORT_NR_MIN + ", max=" + SERIAL_PORT_NR_MAX + ")");
    }
    if (parity < PARITY_NONE || parity > PARITY_EVEN) {
      throw new IllegalArgumentException("Invalid ModbusSlave-RTU parity=" + parity + " (none=" + PARITY_NONE + ", odd=" + PARITY_ODD + ", even=" + PARITY_EVEN + ")");
    }
    int error = nCreateRtu(serialPortNr, baudrate, parity, dataBits, stopBits, slaveId);
    if (error != 0) {
      throw new RuntimeException("ModbusSlave-RTU could not be created (errro=" + error + ").");
    }
    ModbusSlave.instance = new ModbusSlave();
    return ModbusSlave.instance;
  }

  public static ModbusSlave createTcp(int tcpPortNumber) {
    if (ModbusSlave.instance != null) {
      throw new IllegalStateException("ModbusSlave has already been created.");
    }
    int error = nCreateTcp(tcpPortNumber);
    if (error != 0) {
      throw new RuntimeException("ModbusSlave-TCP could not be created (errro=" + error + ").");
    }
    ModbusSlave.instance = new ModbusSlave();
    return ModbusSlave.instance;
  }


  /*en
   * Initializes the memory areas of the Modbus slave for the data exchange with the Modbus master.
   * 
   * @param nbBits           number of bits
   * @param nbInputBits      number of input bits
   * @param nbRegisters      number of registers
   * @param nbInputRegisters number of input registers
   */ 
  /**
   * Initialisiert die Speicherbereiche des Modbus-Slave f체r den Datenaustausch mit dem Modbus-Master.
   * 
   * @param nbBits           Anzahl der Bits
   * @param nbInputBits      Anzahl der Eingabe-Bits
   * @param nbRegisters      Anzahl der Register
   * @param nbInputRegisters Anzahl der Eingabe-Register
   */
  public void initMapping(int nbBits, int nbInputBits, int nbRegisters, int nbInputRegisters) {
    initMapping(0, nbBits, 0, nbInputBits, 0, nbRegisters, 0, nbInputRegisters);
  }

  /*en
   * Initializes the memory areas of the Modbus slave for the data exchange with the Modbus master.
   * 
   * @param startBits           start index of bits
   * @param nbBits              number of bits (from start index)
   * @param startInputBits      start index of input bits
   * @param nbInputBits         number of input bits (from start index)
   * @param startRegisters      start index of registers
   * @param nbRegisters         number of registers (from start index)
   * @param startInputRegisters start index of input registers
   * @param nbInputRegisters    number of input registers (from start index)
   */ 
  /**
   * Initialisiert die Speicherbereiche des Modbus-Slave f체r den Datenaustausch mit dem Modbus-Master.
   * 
   * @param startBits           Start-Index der Bits
   * @param nbBits              Anzahl der Bits (ab Start-Index)
   * @param startInputBits      Start-Index der Eingabe-Bits
   * @param nbInputBits         Anzahl der Eingabe-Bits (ab Start-Index)
   * @param startRegisters      Start-Index der Register
   * @param nbRegisters         Anzahl der Register (ab Start-Index)
   * @param startInputRegisters Start-Index der Eingabe-Register
   * @param nbInputRegisters    Anzahl der Eingabe-Register (ab Start-Index)
   */
  public void initMapping(int startBits, int nbBits, int startInputBits, int nbInputBits, int startRegisters, int nbRegisters, int startInputRegisters, int nbInputRegisters) {
    if (startBits < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter startBits=" + startBits + " (<0).");
    }
    if (startInputBits < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter startInputBits=" + startInputBits + " (<0).");
    }
    if (startRegisters < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter startRegisters=" + startRegisters + " (<0).");
    }
    if (startInputRegisters < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter startInputRegisters=" + startInputRegisters + " (<0).");
    }
    if (nbBits < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter nbBits=" + nbBits + " (<0).");
    }
    if (nbInputBits < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter nbInputBits=" + nbInputBits + " (<0).");
    }
    if (nbRegisters < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter nbRegisters=" + nbRegisters + " (<0).");
    }
    if (nbInputRegisters < 0) {
      throw new IllegalArgumentException("Invalid mapping parameter nbInputRegisters=" + nbInputRegisters + " (<0).");
    }
    if (nbBits > MAX_BITS) {
      throw new IllegalArgumentException("Invalid mapping parameter nbBits=" + nbBits + " (>" + MAX_BITS + ").");
    }
    if (nbInputBits > MAX_BITS) {
      throw new IllegalArgumentException("Invalid mapping parameter nbInputBits=" + nbInputBits + " (>" + MAX_BITS + ").");
    }
    if (nbRegisters > MAX_REGISTERS) {
      throw new IllegalArgumentException("Invalid mapping parameter nbRegisters=" + nbRegisters + " (>" + MAX_REGISTERS + ").");
    }
    if (nbInputRegisters > MAX_REGISTERS) {
      throw new IllegalArgumentException("Invalid mapping parameter nbInputRegisters=" + nbInputRegisters + " (>" + MAX_REGISTERS + ").");
    }
    int error = nInitMapping(startBits, nbBits, startInputBits, nbInputBits, startRegisters, nbRegisters, startInputRegisters, nbInputRegisters);
    if (error != 0) {
      throw new RuntimeException("Mapping could not be initialized (errro=" + error + ").");
    }
  }

  /*en
   * Reads a bit from the bit field of the Modbus slave that can be set by the Mobdus master (with the Modbus functions 0x05 and 0x0F)
   * 
   * @param index  index of the bit to be read
   * @return bit state
   */ 
  /**
   * Liest ein Bit aus dem Bit-Feld des Modbus-Slave das vom Mobdus-Master gesetzt werden kann (mit den Modbus Funktionen 0x05 und 0x0F)
   * 
   * @param index  Index des zu lesenden Bits
   * @return Zustand des Bits
   */
  public boolean getBit(int index) {
    boolean[] bits = new boolean[1];
    nGetBits(index, bits.length, bits);
    return bits[0];
  }
  
  /*en
   * Reads several bit from the bit field of the Modbus slave that can be set by the Mobdus master (with the Modbus functions 0x05 and 0x0F)
   * 
   * @param startIndex  index from which the bits are to be read
   * @param count       number of bits to be read
   * @return array with states of the bits
   */ 
  /**
   * Liest mehrere Bits aus dem Bit-Feld des Modbus-Slave das vom Mobdus-Master gesetzt werden kann (mit den Modbus Funktionen 0x05 und 0x0F)
   * 
   * @param startIndex  Index ab dem die Bits gelesen werden sollen
   * @param count       Anzahl der Bits die gelesen werden sollen
   * @return Array mit Zust채nden der Bits
   */
  public boolean[] getBits(int startIndex, int count) {
    boolean[] bits = new boolean[count];
    nGetBits(startIndex, bits.length, bits);
    return bits;
  }

  /*en
   * Sets a bit in the bit field of the Modbus slave that can be read by the Mobdus master (with the Modbus function 0x02)
   * 
   * @param index  index of the bit to be set
   * @param bit    state of the bit to be set
   */ 
  /**
   * Setzt ein Bit in dem Bit-Feld des Modbus-Slave das vom Mobdus-Master gelesen werden kann (mit der Modbus Funktion 0x02)
   * 
   * @param index  Index des zu setzenden Bits
   * @param bit    Zustand des zu setzenden Bits
   */
  public void setInputBit(int index, boolean bit) {
    boolean[] inputBits = {bit};
    nSetInputBits(index, inputBits.length, inputBits);
  }
  
  /*en
   * Sets several bits in the bit field of the Modbus slave that can be read by the Mobdus master (with the Modbus function 0x02)
   * 
   * @param startIndex   index of the first bit to be set
   * @param inputBits    states of the bit to be set
   */ 
  /**
   * Setzt mehrere Bits in dem Bit-Feld des Modbus-Slave das vom Mobdus-Master gelesen werden kann (mit der Modbus Funktion 0x02)
   * 
   * @param startIndex   Index des ersten zu setzenden Bits
   * @param inputBits    Zust채nde der zu setzenden Bits
   */
  public void setInputBits(int startIndex, boolean[] inputBits) {
    nSetInputBits(startIndex, inputBits.length, inputBits);
  }
  
  public int getRegister(int index) {
    int[] registers = new int[1];
    nGetRegisters(index, registers.length, registers);
    return registers[0];
  }
  
  public int[] getRegisters(int startIndex, int count) {
    int[] registers = new int[count];
    nGetRegisters(startIndex, registers.length, registers);
    return registers;
  }
  
  public void setInputRegister(int index, int value) {
    int[] registers = {value};
    nSetInputRegisters(index, registers.length, registers);
  }
  
  public void setInputRegisters(int startIndex, int[] values) {
    nSetInputRegisters(startIndex, values.length, values);
  }


  /* ********************************************************************************************* */
  /* Native Methoden */
  /* ********************************************************************************************* */

  private final static native int nCreateRtu(int portNr, int baudrate, int parity, int dataBits, int stopBits, int slaveId);
  private final static native int nCreateTcp(int tcpPortNumber);

  private final static native int nInitMapping(int startBits, int nbBits, int startInputBits, int nbInputBits, int startRegisters, int nbRegisters, int startInputRegisters, int nbInputRegisters);

  private final static native int nGetBits(int startIndex, int count, boolean[] bits);
  private final static native int nGetRegisters(int startIndex, int count, int[] registers);

  private final static native int nSetInputBits(int startIndex, int count, boolean[] inputBits);
  private final static native int nSetInputRegisters(int startIndex, int count, int[] inputRegisters);
}
