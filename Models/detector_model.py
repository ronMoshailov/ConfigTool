class Detector:
    def __init__(self, name: str, type: str, ext_unit:int = None):
        self.name = name
        self.type = type
        self.ext_unit = ext_unit

class DetectorModel:
    def __init__(self):
        self.all_detectors = []

    def new_detector(self, name: str, type: str, ext_unit:int = None):
        if self.is_detector_exist(name, type):
            return False
        detector = Detector(name, type, ext_unit)
        self.all_detectors.append(detector)
        return True

    def is_detector_exist(self, name, type):
        for detector in self.all_detectors:
            if detector.name == name and detector.type == type:
                return True
        return False