from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QPushButton, QVBoxLayout
import sys

app = QApplication(sys.argv)

window = QWidget()                              # המנוע הראשי של האפליקציה, אחראי על הרצת הלולאה הגרפית
window.setWindowTitle("ConfigTool")
window.setGeometry(300, 200, 400, 200)

layout = QVBoxLayout()
label = QLabel("שלום עולם!")
label2 = QLabel("שלום עולם2!")
label3 = QLabel("שלום עולם3!")
button = QPushButton("לחץ עליי")

layout.addWidget(label)
layout.addWidget(label2)
layout.addWidget(label3)
layout.addWidget(button)
window.setLayout(layout)

window.show()
sys.exit(app.exec_())

# ------------------------------------------------------------------------- #
# QApplication – המנוע הראשי של האפליקציה
# אחראי על הרצת הלולאה הגרפית
# חייב להיות ראשון – מפעיל את כל החלון

# QWidget – בסיס לכל רכיב גרפי (חלון, תיבה, אזור וכו')
# החלון הראשי שלך יורש ממנו

# QLabel – תווית טקסט (כמו <label> ב־HTML)
# מציג הודעות, שמות שדות, כותרות

# QPushButton – כפתור לחיץ
# משמש לפעולות כמו: שמור, הוסף, מחק וכו'

# QVBoxLayout – פריסת רכיבים אנכית (אחד מתחת לשני)
# קובע איך הרכיבים מסתדרים בתוך החלון

