class _Detector:
    def __init__(self, var_name: str, class_name: str, datector_name :str, move_name: str, ext_unit:int = 0):
        self.var_name       = var_name
        self.class_name     = class_name
        self.datector_name  = datector_name
        self.move_name      = move_name
        self.ext_unit       = ext_unit

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
        """
        This method return all the possible types of the detectors
        """
        return ["DDetector", "EDetector", "DEDetector", "TPDetector", "QDetector"]

    def get_all_d_detectors(self):
        """
        This method return all the detectors that are demand detector
        """
        return [detector for detector in self.all_detectors if detector.class_name == "DDetector"]

    def get_all_e_detectors(self):
        """
        This method return all the detectors that are extension detector
        """
        return [detector for detector in self.all_detectors if detector.class_name == "EDetector"]

    def get_all_de_detectors(self):
        """
        This method return all the detectors that are demand & extension detector
        """
        return [detector for detector in self.all_detectors if detector.class_name == "DEDetector"]

    def get_all_tp_detectors(self):
        """
        This method return all the detectors that are pedestrian detector
        """
        return [detector for detector in self.all_detectors if detector.class_name == "TPDetector"]

    def get_all_q_detectors(self):
        """
        This method return all the detectors that are queue detector
        """
        return [detector for detector in self.all_detectors if detector.class_name == "QDetector"]

    def set_variable_name(self, old_var_name, new_var_name):
        """
        This method set the new variable name
        """
        for detector in self.all_detectors:
            if detector.var_name == old_var_name:
                detector.var_name = new_var_name
                return

    def set_detector_type(self, new_value, variable_name):
        """
        This method set the new variable name
        """
        for detector in self.all_detectors:
            if detector.var_name == variable_name:
                detector.class_name = new_value
                return

    def set_move_name(self, new_value, variable_name):
        """
        This method set new move name that belong to the detector
        """
        for detector in self.all_detectors:
            if detector.var_name == variable_name:
                detector.move_name = new_value
                return

    def set_detector_name(self, variable_name, new_value):
        """
        This method set new name for the detector
        """
        for detector in self.all_detectors:
            if detector.var_name == variable_name:
                detector.datector_name = new_value
                return

    def set_ext_unit(self, variable_name, new_value):
        """
        This method set new value for the extension unit
        """
        for detector in self.all_detectors:
            if detector.var_name == variable_name:
                detector.ext_unit = new_value
                return

    def remove_detector(self, detector_name):
        """
        This method remove a detector from the model
        """
        for detector in self.all_detectors:
            if detector.var_name == detector_name:
                self.all_detectors.remove(detector)
                return

    # ============================== Logic ============================== #
    def is_detector_exist(self, var_name):
        """
        This method check if a detector already exists in the model
        """
        for detector in self.all_detectors:
            if detector.var_name == var_name:
                return True
        return False

    def rename_move(self, old_name, new_name):
        for detector in self.all_detectors:
            if detector.move_name == old_name:
                detector.move_name = new_name

    def reset_detector_model(self):
        """
        This method reset the data of the model
        """
        self.all_detectors.clear()

