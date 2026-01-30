import Config

class DetectorController:
    def __init__(self, view, model):
        # Fields
        self.view               = view
        self.model              = model
        self.all_moves_names    = None

        # Set View Methods
        self.view.remove_detector_method            = self.remove_detector
        self.view.add_detector_method               = self.add_detector
        self.view.update_variable_name_method       = self.update_variable_name
        self.view.update_detector_type_method       = self.update_detector_type
        self.view.update_move_name_method           = self.update_move_name
        self.view.update_detector_name_method       = self.update_detector_name
        self.view.update_ext_unit_method            = self.update_ext_unit

        # Set Main Controller Methods
        self.get_all_moves_names            = None

    def init_model(self, path):
        with open(path, "r", encoding="utf-8") as file:
            for line in file:
                if line.startswith("//"):
                    continue
                matches = Config.patterns.detectors_pattern.findall(line)
                for var_name, class_name, detector_name, move_name in matches:
                    self.model.new_detector(var_name=var_name, class_name=class_name, detector_name=detector_name, move_name=move_name, ext_unit=0)

    def show_view(self):
        self.view.show_view(self.model.all_detectors, self.model.get_all_types(), self.get_all_moves_names())

    # ============================== CRUD ============================== #
    def add_detector(self, var_name, class_name, detector_name, move_name, ext_unit):
        """
        This method add new move to the model
        """
        self.model.new_detector(var_name=var_name, class_name=class_name, detector_name=detector_name, move_name=move_name, ext_unit=ext_unit)
        self.show_view()

    def update_variable_name(self, old_var_name, new_var_name):
        """
        This method update the variable name
        """
        self.model.set_variable_name(old_var_name, new_var_name)
        self.show_view()

    def update_detector_type(self, new_value, variable_name):
        """
        This method update the variable name
        """
        self.model.set_detector_type(new_value, variable_name)

    def update_move_name(self, new_value, variable_name):
        """
        This method update the variable name
        """
        self.model.set_move_name(new_value, variable_name)

    def update_detector_name(self, variable_name, new_value):
        """
        This method update the name of the detector
        """
        self.model.set_detector_name(variable_name, new_value)

    def update_ext_unit(self, variable_name, value):
        """
        This method update the name of the detector
        """
        self.model.set_ext_unit(variable_name, value)

    def remove_detector(self, variable_name):
        self.model.remove_detector(variable_name)
        self.show_view()

    # ============================== Logic ============================== #
    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_detector_model()

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move that belong to detector
        """
        self.model.rename_move(old_name, new_name)

    # ============================== Write To File ============================== #
    def write_to_file(self, path_init_tk1, path_tk1):
        self._write_to_init_tk1(path_init_tk1)
        self._write_to_tk1(path_tk1)

    def _write_to_init_tk1(self, path):
        code = []
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write detectors here" in line:
                    for detector in self.model.all_detectors:
                        line = ""
                        line += f"\t\ttk.{detector.var_name}"                   # add code
                        line += " " * (12 - len(line))                          # add spaces
                        line += f"= new {detector.class_name}"                  # add code
                        line += " " * (28 - len(line))                          # add spaces
                        line += f"( \"{detector.datector_name}\""               # add code
                        line += " " * (38 - len(line))                          # add spaces
                        line += f", tk.{detector.move_name}"                    # add code
                        line += " " * (46 - len(line))                          # add spaces
                        line += ", true    , true        , true );\n"           # add code
                        code.append(line)
                    continue
                code.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def _write_to_tk1(self, path):
        code = []
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if "write detectors here" in line:
                    # DDETECTOR
                    if len(self.model.get_all_d_detectors()) > 0:
                        line = "\t"
                        line += "public DDetector "
                        for detector in self.model.get_all_d_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # EDETECTOR
                    if len(self.model.get_all_e_detectors()) > 0:
                        line = "\t"
                        line += "public EDetector "
                        for detector in self.model.get_all_e_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # DE_DETECTOR
                    if len(self.model.get_all_de_detectors()) > 0:
                        line = "\t"
                        line += "public DEDetector "
                        for detector in self.model.get_all_de_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # TPDETECTOR
                    if len(self.model.get_all_tp_detectors()) > 0:
                        line = "\t"
                        line += "public TPDetector "
                        for detector in self.model.get_all_tp_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)

                    # QDETECTOR
                    if len(self.model.get_all_q_detectors()) > 0:
                        line = "\t"
                        line += "public QDetector "
                        for detector in self.model.get_all_q_detectors():
                            line += f"{detector.var_name}, "
                        line = line[:-2]
                        line += ";\n"
                        code.append(line)
                    continue
                code.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)
