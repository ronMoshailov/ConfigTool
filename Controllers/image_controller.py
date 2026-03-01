
from Managers.load_data_manager import LoadDataManager
from Managers.write_data_manager import WriteDataManager


class ImageController:
    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model

        # Set View Methods
        self.view.add_image_method                  = self.add_image
        self.view.remove_image_method               = self.remove_image
        self.view.update_stop_point_method          = self.update_stop_point
        self.view.update_skeleton_method            = self.update_skeleton
        self.view.update_image_number_method        = self.update_image_number
        self.view.update_move_assignment_method     = self.update_move_assignment

        # Data
        self.all_moves_names = None

    def init_model(self, path, all_moves_names):
        all_images_data = LoadDataManager.load_images_data(path, all_moves_names)
        for image_name, image_num, image_skeleton, image_sp, is_police, collection in all_images_data:
            self.model.new_image(image_name, image_num, image_skeleton, image_sp, is_police, collection)

    def show_view(self, all_moves_names):
        self.all_moves_names = all_moves_names
        self.view.show_view(self.model.all_images, self.all_moves_names)

    # ============================== CRUD ============================== #
    def add_image(self, name):
        """
        This method add new image to the model
        """
        if not self.model.new_image(name, (len(self.model.all_images) - 1) * 10, 1, len(self.model.all_images)):
            self.view.show_error("התמונה כבר קיימת במערכת")
        self.show_view(self.all_moves_names)

    def fetch_images_by_sp(self):
        # Used By Parameters ta Controller
        """
        This method fetch all the images from the model in the order of SP
        """
        return self.model.get_images_by_sp()

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move
        """
        self.model.rename_move(old_name, new_name)

    def remove_image(self, name):
        """
        This method removes image from the model
        """
        self.model.remove_image(name)
        self.show_view(self.all_moves_names)

    def remove_move(self, move_name):
        # Used By Main Controller
        """
        This method removes a move from all the images of the model
        """
        self.model.remove_move(move_name)

    def update_skeleton(self, image_name, skeleton):
        """
        This method update the skeleton number of the image
        """
        self.model.set_skeleton(image_name, skeleton)

    def update_image_number(self, image_name, skeleton):
        """
        This method update the image number of the image
        """
        self.model.set_image_number(image_name, skeleton)
        self.show_view(self.all_moves_names)

    def update_move_assignment(self, image_name, move_name):
        """
        This method toggle the assignment of a move to the image
        """
        self.model.update_move_assignment(image_name, move_name)

    def get_sp_by_image(self, image_name):
        # Used By Parameters ta Controller
        """
        This method returns a stop point by the image name
        """
        return self.model.get_sp_by_image(image_name)

    def update_stop_point(self, image_name, sp):
        """
        This method updates the stop point of the image
        """
        for image in self.model.all_images:
            if image.image_name == image_name:
                image.sp = int(sp)
                break

    # ============================== Logic ============================== #

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset_image_model()

    # ============================== Write To File ============================== #
    def write_to_file(self, path_tk, path_init_tk, phase_folder_dst):
        """
        This method write the data from the model to the project
        """
        # create files
        WriteDataManager.create_images_file_code(phase_folder_dst, self.model.get_all_images_names())

        # tk1.java
        code = WriteDataManager.create_image_tk1_code(path_tk, self.model.get_all_images_names())
        WriteDataManager.write_code(path_tk, code)

        # init_tk1.java
        code = WriteDataManager.create_image_init_tk1_code(path_init_tk, self.model.all_images)
        WriteDataManager.write_code(path_init_tk, code)





