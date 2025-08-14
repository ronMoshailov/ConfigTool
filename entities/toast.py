from PyQt5.QtWidgets import QApplication, QLabel
from PyQt5.QtCore import Qt, QTimer, QPropertyAnimation
import sys


class Toast(QLabel):
    def __init__(self, message, parent=None, duration=2000):
        super().__init__(message, parent)
        self.setStyleSheet("""
            background-color: rgba(0, 0, 0, 180);
            color: white;
            padding: 10px;
            border-radius: 8px;
            font-size: 14px;
        """)
        self.setWindowFlags(Qt.ToolTip)  # לא לוקח פוקוס
        self.adjustSize()

        # מיקום במרכז המסך/חלון
        if parent:
            x = parent.geometry().center().x() - self.width() // 2
            y = parent.geometry().center().y() - self.height() // 2
        else:
            screen = QApplication.primaryScreen().availableGeometry().center()
            x = screen.x() - self.width() // 2
            y = screen.y() - self.height() // 2
        self.move(x, y)

        # הצגה
        self.show()

        # אנימציית דהייה
        self.animation = QPropertyAnimation(self, b"windowOpacity")
        self.animation.setDuration(1000)
        self.animation.setStartValue(1.0)
        self.animation.setEndValue(0.0)

        QTimer.singleShot(duration, self.fade_out)

    def fade_out(self):
        self.animation.start()
        self.animation.finished.connect(self.close)


# if __name__ == "__main__":
#     app = QApplication(sys.argv)
#     main_window = QLabel("חלון ראשי")
#     main_window.resize(300, 200)
#     main_window.show()
#
#     QTimer.singleShot(500, lambda: Toast("הודעה נשלחה בהצלחה", main_window))
#     sys.exit(app.exec_())
