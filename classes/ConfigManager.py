from classes.Sk24 import Sk24


class ConfigManager:
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(ConfigManager, cls).__new__(cls)
            cls._instance._init()
        return cls._instance

    def _init(self):
        self.main_phases = []
        self.not_main_phases = []
        self.d_detectors = []
        self.e_detectors = []
        self.inter_stages = []
        self.sk24 = []
        self.sk24_idx = 1
        self.io24 = []
        self.io64 = []

    # =============== Add =============== #
    def add_main_phase(self, new_phase):
        self.main_phases.append(new_phase)
        self.print_main_phases()

    def add_not_main_phase(self, new_phase):
        self.not_main_phases.append(new_phase)

    def add_d_detector(self, new_detector):
        self.d_detectors.append(new_detector)

    def add_e_detector(self, new_detector):
        self.e_detectors.append(new_detector)

    def add_inter_stages(self, new_inter_stages):
        self.inter_stages.append(new_inter_stages)

    def add_sk24(self, path):
        sk24 = Sk24(path, self.sk24_idx)
        self.inter_stages.append(new_inter_stages)

    def print_main_phases(self):
        print(f"main phases: {self.main_phases}")