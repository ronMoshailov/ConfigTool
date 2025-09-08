from PyQt6.QtCore import QRectF, Qt
from PyQt6.QtGui import QColor, QPen, QBrush
from PyQt6.QtWidgets import QGraphicsRectItem, QGraphicsTextItem


class Program(QGraphicsRectItem):
    def __init__(self, rect: QRectF):
        super().__init__(rect)
        self.setBrush(QBrush(QColor("#76c893")))
        self.setPen(QPen(Qt.GlobalColor.black))

        # חובה כדי לקבל אירועי עכבר
        self.setFlag(QGraphicsRectItem.GraphicsItemFlag.ItemIsSelectable, True)
        self.setAcceptHoverEvents(True)

        self.resizing = False
        self.resize_side = None
        self.start_pos = None

        # טקסטים
        self.left_text = QGraphicsTextItem(self)
        self.right_text = QGraphicsTextItem(self)
        self.update_text_positions()

    def mousePressEvent(self, event):
        self.start_pos = event.pos()
        rect = self.rect()
        margin = 5  # אזור רגיש קרוב לצד

        if abs(self.start_pos.x() - rect.left()) < margin:
            self.resizing = True
            self.resize_side = "left"
        elif abs(self.start_pos.x() - rect.right()) < margin:
            self.resizing = True
            self.resize_side = "right"
        else:
            self.resizing = False

        event.accept()

    def mouseMoveEvent(self, event):
        if self.resizing:
            rect = self.rect()
            dx = event.pos().x() - self.start_pos.x()

            if self.resize_side == "right":
                rect.setRight(rect.right() + dx)
            elif self.resize_side == "left":
                rect.setLeft(rect.left() + dx)

            if rect.width() >= 5:
                self.setRect(rect)
                self.update_text_positions()

            self.start_pos = event.pos()

        event.accept()

    def mouseReleaseEvent(self, event):
        self.resizing = False
        self.resize_side = None
        event.accept()

    def update_text_positions(self):
        rect = self.rect()
        # מיקום הטקסטים מעל הקצוות
        self.left_text.setPos(rect.left(), rect.top() - 3)
        self.right_text.setPos(rect.right() - 20, rect.top() - 3)

        # עדכון ערכים במספרים (מיקום / 10)
        self.left_text.setPlainText(str(int(rect.left() / 10)))
        self.right_text.setPlainText(str(int(rect.right() / 10)))


