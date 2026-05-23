from PyQt6.QtWidgets import QApplication, QWidget, QListWidget, QVBoxLayout

app = QApplication([])

window = QWidget()
window.setWindowTitle("QListWidget Example")

layout = QVBoxLayout()

# יצירת רשימה
list_widget = QListWidget()

# הוספת פריטים
list_widget.addItem("Apple")
list_widget.addItem("Banana")
list_widget.addItem("Orange")

layout.addWidget(list_widget)

window.setLayout(layout)
window.resize(300, 200)
window.show()

app.exec()