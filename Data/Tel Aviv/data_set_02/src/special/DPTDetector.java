package special;

import sg.Sg;
import det.Ausgang;
import det.Detektor;
import tk.Var;

public class DPTDetector {
	private Preemption _preemption;

	private Node _tk;

	public static final int first_logical_channel = 41;
	public static final int last_logical_channel = 100;
	public static final int max_channels = 6;
	private static final int MetronitLines = 3;

	private static int _detId = first_logical_channel;

	private Detektor [] channels;
	private int _id;
	private int _num_of_channels;
	private boolean _isBusActivated;
	private boolean _isBusActive;
	private boolean _isBusDeactivated;
	private Ausgang[] _outputs = null;
	private Ausgang[] _okOutputs = null;
	private boolean _isNotLogical;
	private int _setTime = 0;
	private boolean _isManualDemand = false;
	private int _metronitCodes[];
	private boolean allow_detection = true;
	private int max_delta;
	private int time_since_detection;
	private boolean onDown = false;

	public int getMoveNumber() {
		return _preemption.dptData[_id][0];
	}

	public int getDPTType() {
		return _preemption.dptData[_id][1];
	}

	public int getTimeToTarge() {
		return _preemption.dptData[_id][3];
	}

	public DPTDetector(Node tk, Preemption prm, int id, DPTDetector dpt, Sg sg, int dpt_type, int[] bus_lines, int delta) {
		//		channels = new Detektor [max_channels];
		_tk = tk;
		_num_of_channels = dpt._num_of_channels;
		max_delta = dpt.max_delta;
		_id = id;
		_metronitCodes = bus_lines;
		channels = dpt.channels;
		_preemption = prm;
		_preemption.dptData[_id][0] = sg.getNr();
		_preemption.dptData[_id][1] = dpt_type;
		_preemption.dptData[_id][2] = _id;
		_isNotLogical = false;
	}

	/**
	 * 
	 * @param num_of_channels
	 * @param id - index of dpt (from 0 to n-1 when n is number of DPT *logical* detectors (i.e. 6 channels is 1 logical detector)
	 * @param logical_det_channel - if single channel - actual logical channel of detector. else, 0  
	 * @param tk
	 * @param sg
	 * @param sg_name
	 * @param dpt_type
	 */
	public DPTDetector(Node tk, Preemption prm, int num_of_channels, int id, int logical_det_channel, Sg sg, String[] sg_name, int dpt_type, int[] bus_lines, int delta) {
		int i, j;
		_tk = tk;
		if (sg_name.length > 1) {
			max_delta = delta * Var.ONE_SEC;
		} else {
			max_delta = 0;
		}

		channels = new Detektor [ max_channels * sg_name.length];
		_num_of_channels = num_of_channels; 
		_id = id;
		_metronitCodes = bus_lines;
		for (j = 0; j < sg_name.length; j++) {
			for (i = 0; i < _num_of_channels; i++) {
				if (_num_of_channels == 2) {
					if (i == 0) {
						channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, (logical_det_channel + i), sg, 4000, 0);
						_tk.dpt2FirstChannels[_tk.dpt2SecondChannelIndex] = (logical_det_channel + i);
					} else {
						channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, _tk.dpt2SecondChannels[_tk.dpt2SecondChannelIndex++], sg, 4000, 0);
					}
				} else {
					channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, ((_num_of_channels != 1) ? _detId++ : logical_det_channel), sg, 4000, 0);
				}
			}
		}
		_preemption = prm;
		_preemption.dptData[_id][0] = sg.getNr();
		_preemption.dptData[_id][1] = dpt_type;
		_preemption.dptData[_id][2] = _id;
		_isNotLogical = false;
	}

	public DPTDetector(Node tk, Preemption prm, int num_of_channels, int id, int logical_det_channel, Sg sg, String[] sg_name, int dpt_type, int[] bus_lines, int delta, Ausgang[] outputs, Ausgang[] okOutputs) {
		int i, j;
		_tk = tk;
		if (sg_name.length > 1) {
			max_delta = delta * Var.ONE_SEC;
		} else {
			max_delta = 0;
		}

		channels = new Detektor [ max_channels * sg_name.length]; 
		_num_of_channels = num_of_channels;
		_id = id;
		_metronitCodes = bus_lines;
		_outputs = outputs;
		_okOutputs = okOutputs;
		for (j = 0; j < sg_name.length; j++) {
			for (i = 0; i < _num_of_channels; i++) {
				if (_num_of_channels == 2)
					channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, (logical_det_channel + i), sg, 4000, 0);
				else
					channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, ((_num_of_channels != 1) ? _detId++ : logical_det_channel), sg, 4000, 0);
			}
		}
		_preemption = prm;
		_preemption.dptData[_id][0] = sg.getNr();
		_preemption.dptData[_id][1] = dpt_type;
		_preemption.dptData[_id][2] = _id;
		_isNotLogical = false;
	}

	public DPTDetector(Node tk, Preemption prm, int num_of_channels, int id, int logical_det_channel, String[] sg_name, int dpt_type, int[] bus_lines, int delta) {
		int i, j;
		_tk = tk;
		if (sg_name.length > 1) {
			max_delta = delta * Var.ONE_SEC;
		} else {
			max_delta = 0;
		}

		channels = new Detektor [ max_channels * sg_name.length]; 
		_num_of_channels = num_of_channels;
		_id = id;
		_metronitCodes = bus_lines;
		for (j = 0; j < sg_name.length; j++) {
			for (i = 0; i < _num_of_channels; i++) {
				if (_num_of_channels == 2)
					channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, (logical_det_channel + i));
				else
					channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, ((_num_of_channels != 1) ? _detId++ : logical_det_channel));
			}
		}
		_preemption = prm;
		_isNotLogical = true;
	}

	public DPTDetector(Node tk, Preemption prm, int num_of_channels, int id, int logical_det_channel, String[] sg_name, int dpt_type, int[] bus_lines, int delta, Ausgang[] outputs, Ausgang[] okOutputs) {
		int i, j;
		_tk = tk;
		if (sg_name.length > 1) {
			max_delta = delta * Var.ONE_SEC;
		} else {
			max_delta = 0;
		}

		channels = new Detektor [ max_channels * sg_name.length]; 
		_num_of_channels = num_of_channels;
		_id = id;
		_metronitCodes = bus_lines;
		_outputs = outputs;
		_okOutputs = okOutputs;
		for (j = 0; j < sg_name.length; j++) {
			for (i = 0; i < _num_of_channels; i++) {
				if (_num_of_channels == 2)
					channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, (logical_det_channel + i));
				else
					channels[(j*max_channels) + i] = new Detektor(tk, "DPT_" + sg_name[j] + "_" + Integer.toString(dpt_type) + ((_num_of_channels != 1) ? "_" + Integer.toString(i+1): ""), 0, 10, 0, ((_num_of_channels != 1) ? _detId++ : logical_det_channel));
			}
		}
		_preemption = prm;
		_isNotLogical = true;
	}

	public Detektor getChannelByIndex(int ch) {
		if (ch < 1 || ch > channels.length) {
			return channels[0];
		}
		return channels[ch-1];
	}

	public boolean isRegularDemand() {
		if (_num_of_channels == 1 || _num_of_channels == 2) {
			return false;
		}
		return ((!channels[5].belegt()) || (channels[4].belegt()));
	}

	public boolean isBusDetected(int code) {
		if (_metronitCodes.length == MetronitLines) {
			if (code == 1) {
				return true;
			}
		}

		for (int i = 0; i < _metronitCodes.length; i++) {
			if (_metronitCodes[i] == 1) {
				if (code == 4) {
					return true;
				}
			} else if (_metronitCodes[i] == 2) {
				if (code == 5) {
					return true;
				}
			} else if (_metronitCodes[i] == 3) {
				if (code == 6) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean DPTdecode_Activated(Detektor dpt_in1, Detektor dpt_in2, Detektor dpt_in3, Detektor dpt_in4, Detektor dpt_in5, Detektor dpt_in6) {
		if (!allow_detection) {
			return false;
		}

		int tmp = 0;
		if ( dpt_in1.getPosFlanken() > 0)	{	tmp += 1;	} // first  channel is activated
		if ( dpt_in2.getPosFlanken() > 0)	{	tmp += 2;	} // second channel is activated
		if ( dpt_in3.getPosFlanken() > 0)	{	tmp += 4;	} // third  channel is activated
		if ( dpt_in4.getPosFlanken() > 0)	{	tmp += 8;	} // fourth channel is activated
		if (!dpt_in6.belegt())	{	tmp  = 0;	} // sixth  channel is active => error in detector occurred => reset

		return isBusDetected(tmp);
	}

	public boolean DPTdecode_Active(Detektor dpt_in1, Detektor dpt_in2, Detektor dpt_in3, Detektor dpt_in4, Detektor dpt_in5, Detektor dpt_in6) {
		int tmp = 0;
		if ( dpt_in1.belegt() )	{	tmp += 1;	} // first  channel is active
		if ( dpt_in2.belegt() )	{	tmp += 2;	} // second channel is active
		if ( dpt_in3.belegt() )	{	tmp += 4;	} // third  channel is active
		if ( dpt_in4.belegt() )	{	tmp += 8;	} // fourth channel is active
		if (!dpt_in6.belegt())	{	tmp  = 0;	} // sixth  channel is active => error in detector occurred => reset

		return isBusDetected(tmp);
	}

	private boolean isActivated() {
		if (_isNotLogical) {
			if (_num_of_channels == 1) {
				return (channels[0].getPosFlanken() > 0 ) || _isManualDemand;
			} else if (_num_of_channels == 2) {
				return (channels[0].getPosFlanken() > 0 && channels[1].belegt()) || _isManualDemand;
			} else { // detector has 6 channels
				boolean result = false;
				int i;
				for (i = 0; i < (channels.length)/max_channels; i++) {
					if (!result) {
						result = DPTdecode_Activated(channels[(i * max_channels) + 0], channels[(i * max_channels) + 1], channels[(i * max_channels) + 2], channels[(i * max_channels) + 3], channels[(i * max_channels) + 4], channels[(i * max_channels) + 5]) || _isManualDemand;
						if (result && ((channels.length)/max_channels > 1)) {
							allow_detection = false;
						}
					}
				}
				return result; 
			}
		}

		if (_id >= 0 &&
				_id < _preemption.dptData.length && // index of detector does not exceed maximum number of dettectors
				_preemption.dptData[_id][0] != Preemption.NONE) // detector was defined
		{
			if (_num_of_channels == 1) {
				return (channels[0].getPosFlanken() > 0) || _isManualDemand;
			} else if (_num_of_channels == 2) {
				return (channels[0].getPosFlanken() > 0 && channels[1].belegt()) || _isManualDemand;
			} else {
				boolean result = false;
				int i;
				for (i = 0; i < (channels.length)/max_channels; i++) {
					if (!result) {
						result = DPTdecode_Activated(channels[(i * max_channels) + 0], channels[(i * max_channels) + 1], channels[(i * max_channels) + 2], channels[(i * max_channels) + 3], channels[(i * max_channels) + 4], channels[(i * max_channels) + 5]) || _isManualDemand;
						if (result && ((channels.length)/max_channels > 1)) {
							allow_detection = false;
						}
					}
				}
				return result; 
			}
		}
		return false;
	}

	private boolean isActivatedDown() {
		if (_isNotLogical) {
			if (_num_of_channels == 1) {
				return (channels[0].getNegFlanken() > 0 ) || _isManualDemand;
			} else { // detector has 6 channels
				boolean result = false;
				int i;
				for (i = 0; i < (channels.length)/max_channels; i++) {
					if (!result) {
						result = DPTdecode_Activated(channels[(i * max_channels) + 0], channels[(i * max_channels) + 1], channels[(i * max_channels) + 2], channels[(i * max_channels) + 3], channels[(i * max_channels) + 4], channels[(i * max_channels) + 5]) || _isManualDemand;
						if (result && ((channels.length)/max_channels > 1)) {
							allow_detection = false;
						}
					}
				}
				return result; 
			}
		}

		if (_id >= 0 &&
				_id < _preemption.dptData.length && // index of detector does not exceed maximum number of dettectors
				_preemption.dptData[_id][0] != Preemption.NONE) // detector was defined
		{
			if (_num_of_channels == 1) {
				return (channels[0].getNegFlanken() > 0) || _isManualDemand;
			} else {
				boolean result = false;
				int i;
				for (i = 0; i < (channels.length)/max_channels; i++) {
					if (!result) {
						result = DPTdecode_Activated(channels[(i * max_channels) + 0], channels[(i * max_channels) + 1], channels[(i * max_channels) + 2], channels[(i * max_channels) + 3], channels[(i * max_channels) + 4], channels[(i * max_channels) + 5]) || _isManualDemand;
						if (result && ((channels.length)/max_channels > 1)) {
							allow_detection = false;
						}
					}
				}
				return result; 
			}
		}
		return false;
	}

	public boolean isOK() {
		if (channels.length == 1 || _num_of_channels == 1)
			return true;
		else if (channels.length == 2 || _num_of_channels == 2)
			return channels[1].belegt();

		for (int i = 0; i < channels.length; i+= 6)
			if (!channels[i + 5].belegt())
				return false;

		return true;
	}

	public boolean isActive() {
		if (_isNotLogical) {
			if (_num_of_channels == 1) {
				return (channels[0].belegt()) || _isManualDemand;
			} else if (_num_of_channels == 2) {
				return (channels[0].belegt() && channels[1].belegt()) || _isManualDemand;
			} else {
				return DPTdecode_Active(channels[0], channels[1], channels[2], channels[3], channels[4], channels[5]) || _isManualDemand;
			}
		}

		if (_id >= 0 &&
				_id < _preemption.dptData.length && // index of detector does not exceed maximum number of dettectors
				_preemption.dptData[_id][0] != Preemption.NONE) // detector was defined
		{
			if (_num_of_channels == 1) {
				return (channels[0].belegt()) || _isManualDemand;
			} else if (_num_of_channels == 2) {
				return (channels[0].belegt() && channels[1].belegt()) || _isManualDemand;
			} else {
				return DPTdecode_Active(channels[0], channels[1], channels[2], channels[3], channels[4], channels[5]) || _isManualDemand;
			}
		}
		return false;
	}

	private void startCounter(int index, int value, int detectorNumber ) {
		for (int i = 0; i < _preemption.BrtsPerMove; i++ ) {
			if (_preemption.atCounters[index][i] == Preemption.CT_NONE) { // first empty space (next bus will be put here)
				_preemption.atCounters[index][i] = value; // set the length of the far detector
				_preemption.atTypes[index][i] = detectorNumber;
				return;
			}
		}
	}

	/**
	 * @param index: index of move
	 * @param value: assumedTimeFromDetectionToTarget of current detector
	 * @param detectorNumber: index of dpt (1-3) (can't be 4 because cancellation detector doesn't need counter)
	 * 
	 */
	private void updateCounter(int index, int value, int detectorNumber) {
		int closestIndex = Preemption.CT_NONE;
		int diff = 1000*Var.ONE_SEC;
		// find closest to value
		for (int i = 0; i < _preemption.BrtsPerMove; i++) {
			if ((_preemption.atCounters[index][i] != Preemption.CT_NONE) && (detectorNumber > _preemption.atTypes[index][i]))
			{ // first detected bus which detector index is lower than current detected dpt
				int distance = _preemption.atCounters[index][i] - value;
				if (distance < 0) {
					distance = -distance;
				}
				if (distance < diff) {
					diff = distance;
					closestIndex = i;
					break;
				}
			}
		}

		if (closestIndex == Preemption.CT_NONE) {
			startCounter(index, value, detectorNumber);
		} else {
			_preemption.atCounters[index][closestIndex] = value;
			_preemption.atTypes[index][closestIndex] = detectorNumber;
		}
	}

	private void resetCounter(int index) {
		int closestValue = Preemption.CT_NONE;
		int closestIndex = Preemption.NONE;

		for (int i = 0; i < _preemption.BrtsPerMove; i++) {
			if (_preemption.atCounters[ index ][ i ] < closestValue) {
				closestValue = _preemption.atCounters[index][i];
				closestIndex = i;
			}
		}

		if (closestIndex != Preemption.NONE) {
			_preemption.atCounters[index][closestIndex] = Preemption.CT_NONE;
			_preemption.atTypes[index][closestIndex] = Preemption.NONE;
			_preemption.dpt2Timeout[index][closestIndex] = 0;
			_preemption.resetMemBRT(index);
		}
	}

	public void checkDetector() {
		if (_isNotLogical) {
			_isBusActivated = onDown? isActivatedDown(): isActivated();
			if (_isBusDeactivated) {
				_isBusDeactivated = false;
			}
			if (_isBusActive) {
				_isBusDeactivated = true;
			}
			_isBusActive = isActive();
			if (_isBusActive) {
				_isBusDeactivated = false;
			}
			_isManualDemand = false;
			if (_outputs!= null&& _outputs.length>0) {
				if (_setTime < 999000) {
					_setTime++;
				}
				if (_isBusActivated) {
					_setTime = 0;
					for (int i = 0; i < _outputs.length; i++) {
						_outputs[i].setAusgang();
					}
				} else {
					if (_setTime >= 4) {
						for (int i = 0; i < _outputs.length; i++) {
							_outputs[i].resetAusgang();
						}
					}
				}
			}
			return;
		}

		if (_id >= 0 &&
				_id < _preemption.dptData.length && // index of detector does not exceed maximum number of detectors
				_preemption.dptData[_id][0] != Preemption.NONE) // detector was defined
		{
			int moveIndex = _preemption.getMoveIndex( _preemption.dptData[_id][0]);

			if (time_since_detection >= max_delta - _tk.getZyklDauer()) {
				allow_detection = true;
			}

			_isBusActivated = onDown? isActivatedDown(): isActivated();

			if (time_since_detection < 999000) {
				time_since_detection += _tk.getZyklDauer(); 
			}

			if (_isBusActivated) {
				time_since_detection = 0;
			}

			if (_isBusDeactivated) {
				_isBusDeactivated = false;
			}
			if (_isBusActive) {
				_isBusDeactivated = true;
			}
			_isBusActive = isActive();
			if (_isBusActive) {
				_isBusDeactivated = false;
			}
			_isManualDemand = false;

			if (_isBusActivated) {
				// 
				int length = _preemption.getBrtDetectorTime(_preemption.dptData[_id][2]);
				if (_preemption.dptData[_id][1] == 1) // far detector activated
					startCounter(moveIndex, length, 1 );

				if (_preemption.dptData[_id][1] == 2) // near detector activated
					updateCounter(moveIndex, length, 2);

				if (_preemption.dptData[_id][1] == 3) // stopline detector activated
					updateCounter(moveIndex, length, 3);

				if (_preemption.dptData[_id][1] == 4) // cancellation detector activated
					resetCounter(moveIndex);
			}

			/* set output if exists */
			if (_outputs!= null && _outputs.length>0) {
				if (_setTime < 999000) {
					_setTime++;
				}
				if (_isBusActivated) {
					_setTime = 0;
					for (int i = 0; i < _outputs.length; i++) {
						_outputs[i].setAusgang();
					}
				} else {
					if (_setTime >= 4) {
						for (int i = 0; i < _outputs.length; i++) {
							_outputs[i].resetAusgang();
						}
					}
				}
			}
		}

		if (channels.length == 6) {
			if (_okOutputs != null && _okOutputs.length > 0) {
				for (int i = 0 ; i < _okOutputs.length; i++) {
					if (channels[5].belegt())
						_okOutputs[i].setAusgang();
					else
						_okOutputs[i].resetAusgang();
				}
			}
		}
	}

	public boolean isBusActivated() {
		return _isBusActivated;
	}

	public boolean isBusActive() {
		return _isBusActive;
	}

	public boolean isBusDeactivated() {
		return _isBusDeactivated;
	}

	public static int getNumberOfPhysicalDPT() {
		return (int)((_detId - first_logical_channel)/6);
	}

	public void setBusDemand() {
		_isManualDemand = true;
	}

	public void SetAsOffDetection() {
		onDown = true;
	}
}