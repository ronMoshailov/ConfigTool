class Image:
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

    def new_image(self, name, num, skeleton, sp, is_police = False, move_names_list = []):
        for image in self.all_images:
            if image.image_name == name:
                return False
        image = Image(name, num, skeleton, sp, is_police, move_names_list)
        self.all_images.append(image)
        return True

    # def add_image(self, name, num, skeleton, sp):
    #     image = Image(name, num, skeleton, sp,  False, [])
    #     self.all_images.append(image)

    def remove_image(self, name):
        for image in self.all_images:
            if image.image_name == name:
                self.all_images.remove(image)
                return

    def update_image(self, name, skeleton_num, image_number, move_names_list):
        for image in self.all_images:
            if image.image_name == name:
                image.move_names_list = move_names_list
                image.image_num = image_number
                image.skeleton = skeleton_num
                return

    def update_skeleton(self, name, skeleton):
        for image in self.all_images:
            if image.image_name == name:
                image.skeleton = skeleton
                return

    def update_image_number(self, name, image_num):
        for image in self.all_images:
            if image.image_name == name:
                image.image_num = image_num
                return

    def get_images_by_sp(self):
        image_list = []

        for i in range(len(self.all_images)):
            for image in self.all_images:
                if image.sp == i:
                    image_list.append(image.image_name)
                    break
        return image_list

    def is_sp_valid(self):
        highest_sp = 0
        for image in self.all_images:
            if highest_sp < image.sp:
                highest_sp = image.sp

        if highest_sp  == len(self.all_images)-1:
            return True
        return False

    def reset(self):
        self.all_images.clear()

    def update_names(self, old_name, new_name):
        for image in self.all_images:
            for move_name in image.move_names_list:
                if move_name == old_name:
                    move_name = new_name

    def remove_move(self, new_move_name):
        for image in self.all_images:
            for move_name in image.move_names_list:
                if move_name == new_move_name:
                    image.move_names_list.remove(move_name)
                    continue

    def toggle_move(self, image_name, move_name_arg):
        for image in self.all_images:
            if image.image_name == image_name:
                if move_name_arg in image.move_names_list:
                    image.move_names_list.remove(move_name_arg)
                else:
                    image.move_names_list.append(move_name_arg)
                return




