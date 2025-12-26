class Image:
    def __init__(self, name, num, skeleton, sp, is_police, move_list):
        self.image_name = name
        self.image_num = num
        self.skeleton = skeleton
        self.sp = sp
        self.is_police = is_police
        self.move_list = move_list


class ImageModel:
    def __init__(self):
        self.all_images = []

    def new_image(self, name, num, skeleton, sp, is_police = False, move_list = []):
        for image in self.all_images:
            if image.image_name == name:
                return False
        image = Image(name, num, skeleton, sp, is_police, move_list)
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

    def update_image(self, name, skeleton_num, image_number, move_list):
        for image in self.all_images:
            if image.image_name == name:
                image.move_list = move_list
                image.image_num = image_number
                image.skeleton = skeleton_num
                return

    def update_skeleton(self, name, skeleton):
        for image in self.all_images:
            if image.image_name == name:
                image.skeleton = skeleton

