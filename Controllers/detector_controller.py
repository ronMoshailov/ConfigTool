from PyQt6.QtWidgets import QMessageBox

from Config.patterns import detectors_pattern


class DetectorController:
    """
    This class represents a matrix group.
    """
    def __init__(self, view, model):
        self.view = view
        self.model = model

        self.view.remove_detector_method = self.remove_detector
        self.view.add_detector_method = self.add_detector

    def init_model(self, path):
        """
        This method extracts detector declarations from InitTk1.java.

        :param path: path to "InitTk1.java"
        :return: None
        """
        pattern = detectors_pattern

        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                matches = pattern.findall(line)
                for detector_type, instances in matches:
                    variables = [v.strip() for v in instances.split(",")]
                    for name in variables:
                        self.model.new_detector(name, detector_type)

        # if len(self.moves) == 0:
        #     Log.warning(f"Warning: Moves not found")

    def remove_detector(self, name):
        for detector in self.model.all_detectors:
            if detector.name == name:
                self.model.all_detectors.remove(detector)
                self.show_view()
                return

    def add_detector(self, name, type, ext_time):
        if name.isdigit() and type in ["TPDetector"]:
            QMessageBox.critical(self.view, "שגיאה", f"שם הגלאי לא תקין [tp_{name}]")
            return
        if name.isalpha() and type in ["DDetector", "EDetector", "DEDetector", "QDetector"]:
            type_mapping = {"DDetector": f"d_{name}", "EDetector": f"e_{name}", "DEDetector": f"de_{name}", "QDetector": f"q_{name}"}
            QMessageBox.critical(self.view, "שגיאה", f"שם הגלאי לא תקין [{type_mapping[type]}]")
            return
        type_mapping = {"DDetector": f"d_{name}", "EDetector": f"e_{name}", "TPDetector": f"tp_{name}", "DEDetector": f"de_{name}", "QDetector": f"q_{name}"}
        name = type_mapping[type]
        if not self.model.new_detector(name, type, ext_time):
            QMessageBox.critical(self.view, "שגיאה", "הגלאי כבר קיים במערכת")
            return
        self.show_view()

    def show_view(self):
        self.view.show_view(self.model.all_detectors)

    # def write_to_file(self, path):
    #     is_found = False
    #
    #     with open(path, 'r', encoding='utf-8') as f:
    #         lines = f.readlines()
    #
    #     new_lines = []
    #     for line in lines:
    #         if is_found == False and detectors_pattern.match(line):
    #             is_found = True
    #             self.get_lines(new_lines)
    #         else:
    #             new_lines.append(line)
    #
    #     with open(path, 'w', encoding='utf-8') as f:
    #         f.writelines(new_lines)
    #
    # def get_lines(self, new_lines):
    #     for detector in self.model.all_detectors:
    #         new_lines.append(f"tk.{detector.name} = new DDetector(\"{detector.name.upper()}\", tk.k1, true, true, true);")
