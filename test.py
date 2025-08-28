from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QTimeEdit
from PyQt6.QtCore import QTime

app = QApplication([])

window = QWidget()
layout = QVBoxLayout(window)

# יצירת תיבת זמן
time_edit = QTimeEdit()
time_edit.setDisplayFormat("HH:mm")       # שעות:דקות בלבד
time_edit.setTime(QTime.currentTime())    # ברירת מחדל: השעה הנוכחית

layout.addWidget(time_edit)

window.show()
app.exec()
