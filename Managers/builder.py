
from View.image_view import ImageView
from View.io24_view import Io24View
from View.io64_view import Io64View
from View.matrix_view import MatrixView
from View.navigator_view import NavigatorView
from View.detector_view import DetectorView
from View.move_view import MoveView
from View.parameters_ta_view import ParametersTaView
from View.phue_view import PhueView
from View.schedule_view import ScheduleView
from View.settings_view import SettingsView
from View.sk_view import SkView

from Models.detector_model import DetectorModel
from Models.image_model import ImageModel
from Models.matrix_model import MatrixModel
from Models.move_model import MoveModel
from Models.parameters_ta_model import ParametersTaModel
from Models.phue_model import PhueModel
from Models.schedule_model import ScheduleModel
from Models.settings_model import SettingsModel
from Models.sk_model import SkModel
from Models.io64_model import Io64Model
from Models.io24_model import Io24Model

from Controllers.parameters_controller import ParametersTaController
from Controllers.detector_controller import DetectorController
from Controllers.image_controller import ImageController
from Controllers.matrix_controller import MatrixController
from Controllers.move_controller import MoveController
from Controllers.phue_controller import PhueController
from Controllers.schedule_controller import ScheduleController
from Controllers.settings_controller import SettingsController
from Controllers.sk_controller import SkController
from Controllers.io64_controller import Io64Controller
from Controllers.io24_controller import Io24Controller

class SetupBuilder:
    @staticmethod
    def build_models():
        return {"settings": SettingsModel(),
                "move": MoveModel(),
                "matrix": MatrixModel(),
                "sk": SkModel(),
                "io24": Io24Model(),
                "io64": Io64Model(),
                "detector": DetectorModel(),
                "schedule": ScheduleModel(),
                "image": ImageModel(),
                "phue": PhueModel(),
                "parameters_ta": ParametersTaModel(),
                }

    @staticmethod
    def build_views():
        return {"settings": SettingsView(),
                "move": MoveView(),
                "matrix": MatrixView(),
                "sk": SkView(),
                "io24": Io24View(),
                "io64": Io64View(),
                "detector": DetectorView(),
                "schedule": ScheduleView(),
                "image": ImageView(),
                "phue": PhueView(),
                "parameters_ta": ParametersTaView(),
                "navigator": NavigatorView(),
                }

    @staticmethod
    def build_controllers(models, views):
        # =============== Controllers =============== #
        return {"settings": SettingsController(views["settings"], models["settings"]),
                "move": MoveController(views["move"], models["move"]),
                "matrix": MatrixController(views["matrix"], models["matrix"]),
                "sk": SkController(views["sk"], models["sk"]),
                "io24": Io24Controller(views["io24"], models["io24"]),
                "io64": Io64Controller(views["io64"], models["io64"]),
                "detector": DetectorController(views["detector"], models["detector"]),
                "schedule": ScheduleController(views["schedule"], models["schedule"]),
                "image": ImageController(views["image"], models["image"]),
                "phue": PhueController(views["phue"], models["phue"]),
                "parameters_ta": ParametersTaController(views["parameters_ta"], models["parameters_ta"]),
                }

    @staticmethod
    def connect_controllers(controllers):
        # =============== Set Controllers Methods =============== #
        controllers["move"].move_controller.remove_move_from_matrix_method              = controllers["matrix"].remove_move
        controllers["matrix"].matrix_controller.get_move_type                           = controllers["move"].get_move_type
        controllers["parameters_ta"].parameters_ta_controller.get_sp_by_image_method    = controllers["image"].get_sp_by_image
        controllers["detector"].detector_controller.get_all_moves_names                 = controllers["move"].get_all_moves_names

