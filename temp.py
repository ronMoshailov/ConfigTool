import sys
from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QComboBox


class Window(QWidget):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("Drop Down Example")

        layout = QVBoxLayout()

        self.combo = QComboBox()

        # הוספת אפשרויות
        self.combo.addItem("Apple")
        self.combo.addItem("Banana")
        self.combo.addItem("Orange")

        layout.addWidget(self.combo)

        self.setLayout(layout)


app = QApplication(sys.argv)
window = Window()
window.show()
app.exec()