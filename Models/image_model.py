class _Image:
    def __init__(self, name: str, num: str, skeleton: str, sp: str, is_police: bool, move_names_list: list):
        self.image_name = name
        self.image_num = num
        self.skeleton = skeleton
        self.sp = sp
        self.is_police = is_police
        self.move_names_list = move_names_list


class ImageModel:
    def __init__(self):
        self.all_images = []

    # ============================== CRUD ============================== #
    def new_image(self, name, num, skeleton, sp, is_police = False, move_names_list = []):
        """
        This method create a new image
        """
        for image in self.all_images:
            if image.image_name == name:
                return False
        image = _Image(name, num, skeleton, sp, is_police, move_names_list)
        self.all_images.append(image)
        return True

    def get_images_by_sp(self):
        """
        This method return a list of images in the order of their stop point
        """
        image_list = []
        for i in range(len(self.all_images)):
            for image in self.all_images:
                if image.sp == i:
                    image_list.append(image.image_name)
                    break
        return image_list

    def get_sp_by_image(self, image_name):
        """
        This method return the sp (stop point) of the image
        """
        for image in self.all_images:
            if image.image_name == image_name:
                return image.sp
        return None

    def update_move_assignment(self, image_name, move_name_arg):
        """
        This method append/remove move from image
        """
        for image in self.all_images:
            if image.image_name == image_name:
                if move_name_arg in image.move_names_list:
                    image.move_names_list.remove(move_name_arg)
                else:
                    image.move_names_list.append(move_name_arg)
                return

    # def update_image(self, name, skeleton_num, image_number, move_names_list):
    #     """
    #     This method append/remove move from image
    #     """
    #     for image in self.all_images:
    #         if image.image_name == name:
    #             image.move_names_list = move_names_list
    #             image.image_num = image_number
    #             image.skeleton = skeleton_num
    #             return

    def set_skeleton(self, name, skeleton):
        """
        This method set skeleton number to image
        """
        for image in self.all_images:
            if image.image_name == name:
                image.skeleton = skeleton
                return

    def set_image_number(self, name, image_num):
        """
        This method set image number to image
        """
        for image in self.all_images:
            if image.image_name == name:
                image.image_num = image_num
                return

    def remove_image(self, name):
        """
        This method removes image from the model
        """
        for image in self.all_images:
            if image.image_name == name:
                self.all_images.remove(image)
                return

    # ============================== Logic ============================== #
    def is_sp_valid(self):
        """
        This method check if the stop points of all the images starting from 1 to len(all_images)
        """
        highest_sp = 0
        for image in self.all_images:
            if highest_sp < image.sp:
                highest_sp = image.sp

        if highest_sp  == len(self.all_images)-1:
            return True
        return False

    def reset_image_model(self):
        """
        This method clear all the data from the model
        """
        self.all_images.clear()

    def rename_move(self, old_name, new_name):
        """
        This method rename a move from all the images
        """
        for image in self.all_images:
            for i, move_name in enumerate(image.move_names_list):
                if move_name == old_name:
                    image.move_names_list[i] = new_name

    def remove_move(self, new_move_name):
        """
        This method removes a move from all the images in the model
        """
        for image in self.all_images:
            for move_name in image.move_names_list:
                if move_name == new_move_name:
                    image.move_names_list.remove(move_name)
                    continue

