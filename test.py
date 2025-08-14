import sys
from PyQt5.QtWidgets import QApplication, QWidget, QTableWidget, QTableWidgetItem, QVBoxLayout
from PyQt5.QtCore import Qt

app = QApplication(sys.argv)

# מספר שהמשתמש נותן
n = 5  # נקבל מטריצה בגודל 6x6
size = n + 1

window = QWidget()
layout = QVBoxLayout(window)

table = QTableWidget(size, size)


layout.addWidget(table)
window.show()

sys.exit(app.exec_())
