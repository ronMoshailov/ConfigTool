class Detector:
    def __init__(self, var_name: str, class_name: str, datector_name :str, move_name: str, ext_unit:int = 0):
        self.var_name = var_name
        self.class_name = class_name
        self.datector_name = datector_name
        self.move_name = move_name
        self.ext_unit = ext_unit

class DetectorModel:
    def __init__(self):
        self.all_detectors = []

    def new_detector(self, var_name: str, class_name: str, datector_name :str, move_name: str, ext_unit:int = 0):
        if self.is_detector_exist(var_name):
            return False
        detector = Detector(var_name, class_name, datector_name, move_name, ext_unit)
        self.all_detectors.append(detector)
        return True

    def is_detector_exist(self, var_name):
        for detector in self.all_detectors:
            if detector.var_name == var_name:
                return True
        return False