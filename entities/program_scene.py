from PyQt6.QtCore import Qt, QRectF
from PyQt6.QtGui import QPen
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QGraphicsView, QGraphicsScene

from config.colors import black_color, light_blue_color
from controllers.data_controller import DataController
from test import Program


class ProgramScene(QWidget):
    def __init__(self):
        super().__init__()
        self.data_controller = DataController()

        self.scene = QGraphicsScene()
        self.view = QGraphicsView(self.scene)

        # הוספת מלבן עם טקסטים
        rect = Program(QRectF(50, 50, 150, 20)) # x, y, width, height
        self.scene.addItem(rect)
        self.scene.addItem(rect.left_text)
        self.scene.addItem(rect.right_text)

        layout = QVBoxLayout()
        layout.addWidget(self.view)
        self.setLayout(layout)

    def _draw_moves(self):
        row_height = 40

        # draw horizontal lines and move names
        for i in range(len(self.all_moves)):
            # data
            y = i * row_height
            y_center = y + row_height / 2

            # draw line
            self.scene.addLine(0, y, 1500, y, QPen(black_color, 1))

            # draw move name
            text_item = self.scene.addText(self.all_moves[i].name)
            text_item.setPos(0, y_center - 10)


        # draw vertical lines
        # for x in range(0, 801, 50):
        #     self.scene.addLine(x, 0, x, row_height * 10, QPen(light_blue_color, 1, Qt.PenStyle.DashLine))

    def _show_panel(self):
        self.all_moves = self.data_controller.get_all_moves()
        self.all_images = self.data_controller.get_all_images()
        self._draw_moves()





