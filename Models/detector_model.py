class _Detector:
    def __init__(self, var_name: str, class_name: str, datector_name :str, move_name: str, ext_unit:int = 0):
        self.var_name = var_name
        self.class_name = class_name
        self.datector_name = datector_name
        self.move_name = move_name
        self.ext_unit = ext_unit

class DetectorModel:
    def __init__(self):
        self.all_detectors = []

    # ============================== CRUD ============================== #
    def new_detector(self, var_name: str, class_name: str, detector_name :str, move_name: str, ext_unit:int = 0):
        if self.is_detector_exist(var_name):
            return False
        detector = _Detector(var_name, class_name, detector_name, move_name, ext_unit)
        self.all_detectors.append(detector)
        return True

    def get_all_types(self):
        return ["DDetector", "EDetector", "DEDetector", "TPDetector", "QDetector"]

    def get_all_d_detectors(self):
        return [detector for detector in self.all_detectors if detector.class_name == "DDetector"]

    def get_all_e_detectors(self):
        return [detector for detector in self.all_detectors if detector.class_name == "EDetector"]

    def get_all_de_detectors(self):
        return [detector for detector in self.all_detectors if detector.class_name == "DEDetector"]

    def get_all_tp_detectors(self):
        return [detector for detector in self.all_detectors if detector.class_name == "TPDetector"]

    def get_all_q_detectors(self):
        return [detector for detector in self.all_detectors if detector.class_name == "QDetector"]

    def rename_move(self, old_name, new_name):
        for detector in self.all_detectors:
            if detector.move_name == old_name:
                detector.move_name = new_name

    # ============================== Logic ============================== #
    def is_detector_exist(self, var_name):
        for detector in self.all_detectors:
            if detector.var_name == var_name:
                return True
        return False

    def reset(self):
        self.all_detectors.clear()

