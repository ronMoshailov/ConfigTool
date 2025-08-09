from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QCheckBox, QLabel
from PyQt5.QtCore import Qt
import sys

app = QApplication(sys.argv)

w = QWidget()
w.setWindowTitle("Checkbox demo")

layout = QVBoxLayout(w)

label = QLabel("מצב: לא מסומן")
chk = QCheckBox("אפשרות כלשהי")
chk.setChecked(False)              # התחלה לא מסומן
# chk.setTristate(True)            # אם תרצה מצב שלישי (לא חובה)

def on_change(state):
    if state == Qt.Checked:
        label.setText("מצב: מסומן")
    elif state == Qt.Unchecked:
        label.setText("מצב: לא מסומן")
    else:
        label.setText("מצב: ביניים")

chk.stateChanged.connect(on_change)   # או chk.toggled.connect(lambda b: ...)
layout.addWidget(chk)
layout.addWidget(label)

w.show()
sys.exit(app.exec_())
