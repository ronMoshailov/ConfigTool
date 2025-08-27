from PyQt6.QtCore import pyqtSignal
from PyQt6.QtWidgets import QPushButton


class DoubleClickButton(QPushButton):
    doubleClicked = pyqtSignal(int)   # סיגנל משלנו

    def __init__(self, text, parent=None):
        super().__init__(text, parent)

    def mouseDoubleClickEvent(self, event):
        self.doubleClicked.emit()  # נפעיל את הסיגנל רק בלחיצה כפולה
        event.accept()             # מסמן ש"צרכנו" את האירוע
