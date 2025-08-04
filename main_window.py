from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QPushButton, QVBoxLayout
import sys

# QApplication – The main engine of the application, responsible for running the graphics loop
app = QApplication(sys.argv)

# QWidget - Base for every graphical element (window, box, area, etc.)
window = QWidget()
window.setWindowTitle("ConfigTool")
window.setGeometry(300, 200, 400, 600)  # (X, Y, Width, Height)

# QVBoxLayout - Vertical layout of components (one below the other)
# QLabel - Text label displays messages, field names, titles
layout = QVBoxLayout()

# Panel - Right
button = QPushButton("הגדר מופעים", window)
button.setGeometry(230, 0, 150, 30)  # (X, Y, Width, Height)

button2 = QPushButton("הגדר מינימום", window)
button2.setGeometry(230, 50, 150, 30)

button3 = QPushButton("הגדר מטריצה", window)
button3.setGeometry(230, 100, 150, 30)

button4 = QPushButton("הגדר פרמטרים", window)
button4.setGeometry(230, 150, 150, 30)

button5 = QPushButton("הגדר מעברים", window)
button5.setGeometry(230, 200, 150, 30)

button6 = QPushButton("הגדר מיפוי", window)
button6.setGeometry(230, 250, 150, 30)


# Panel - Left
button7 = QPushButton("צומת חדש", window)
button7.setGeometry(10, 150, 100, 40)

button8 = QPushButton("הפעל סלייב", window)
button8.setGeometry(10, 200, 100, 40)

button9 = QPushButton("הפעל מאסטר", window)
button9.setGeometry(10, 250, 100, 40)

button10 = QPushButton("dx הפעל", window)
button10.setGeometry(10, 300, 100, 40)

window.show()
sys.exit(app.exec_())

# ------------------------------------------------------------------------- #
