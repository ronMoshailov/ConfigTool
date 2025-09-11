from PyQt6.QtCore import Qt, QRectF
from PyQt6.QtGui import QPen
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QGraphicsView, QGraphicsScene, QPushButton, QGraphicsProxyWidget, \
    QCheckBox

from config.colors import black_color
from controllers.data_controller import DataController
from entities.timeline_block import TimelineBlock


class ProgramScene(QWidget):
    def __init__(self):
        super().__init__()
        self.data_controller = DataController()

        self.scene = QGraphicsScene()
        self.view = QGraphicsView(self.scene)
        self.view.setAlignment(Qt.AlignmentFlag.AlignLeft | Qt.AlignmentFlag.AlignTop) # שה־scene מתחיל מהקצה השמאלי למעלה

        layout = QVBoxLayout()
        layout.addWidget(self.view)
        self.setLayout(layout)

    def _draw_moves(self):
        row_height = 40
        self.scene.setSceneRect(0, 0, 1500, row_height * len(self.all_moves))

        self.row_programs = {}  # key=row_y, value=ProgramBlock

        # draw horizontal lines and move names
        for i in range(len(self.all_moves)):
            # data
            y = i * row_height
            y_center = y + row_height / 2

            # draw line
            self.scene.addLine(0, y, 1500, y, QPen(black_color, 1))

            # draw move name
            text_item = self.scene.addText(self.all_moves[i].name)
            text_item.setPos(100, y_center - 12)

            # add button
            btn = QPushButton("+")
            btn.clicked.connect(lambda _, row_y=y: self.add_program(row_y))

            proxy = QGraphicsProxyWidget() # bridge between QWidget אם QGraphicsScene
            proxy.setWidget(btn)
            proxy.setPos(0, y_center - 12)  # ממקמים בתוך ה־Scene # -↑  +↓
            self.scene.addItem(proxy)

            # add checkbox
            checkbox = QCheckBox()
            checkbox.clicked.connect(lambda checked, row_y=y: self.toggle_program(row_y, checked))

            proxy = QGraphicsProxyWidget() # bridge between QWidget אם QGraphicsScene
            proxy.setWidget(checkbox)
            proxy.setPos(80, y_center - 8)  # ממקמים בתוך ה־Scene   # -↑  +↓
            self.scene.addItem(proxy)

    def _show_panel(self):
        self.all_moves = self.data_controller.get_all_moves()
        self.all_images = self.data_controller.get_all_images()

        #
        self._draw_moves()

    def add_program(self, row_y):
        """יוצר Program חדש בשורה בגובה row_y"""
        rect = QRectF(150, row_y + 10, 150, 20)  # איפה לשים את המלבן
        program_item = TimelineBlock(rect)
        self.scene.addItem(program_item)

    def toggle_program(self, row_y, checked):
        if checked:
            rect = QRectF(0, row_y, 300, 20) # x, y, width, height
            program_item = TimelineBlock(rect)
            self.scene.addItem(program_item)
            self.row_programs[row_y] = program_item
        else:
            if row_y in self.row_programs:
                self.scene.removeItem(self.row_programs[row_y])
                del self.row_programs[row_y]

