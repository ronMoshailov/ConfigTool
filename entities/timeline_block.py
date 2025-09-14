from PyQt6.QtCore import QRectF, Qt
from PyQt6.QtGui import QColor, QPen, QBrush
from PyQt6.QtWidgets import QGraphicsRectItem, QGraphicsTextItem


class TimelineBlock(QGraphicsRectItem):
    offset = 15
    pixel_unit = 10

    def __init__(self, rect: QRectF, cycle_time=None):
        super().__init__(rect)
        self.setBrush(QBrush(QColor("#76c893")))    # color inside the block
        self.setPen(QPen(Qt.GlobalColor.black))     # color of the border

        self.resizing = False
        self.resize_side = None
        self.start_pos = None

        self.cycle_time = cycle_time

        # טקסטים
        self.left_text = QGraphicsTextItem(self)    # textbox
        self.right_text = QGraphicsTextItem(self)   # textbox
        self.update_text_positions()

    def mousePressEvent(self, event):
        """
        This method fire when the user click on object.

        :param event:
        :return:
        """
        self.start_pos = event.pos()    # save the current click position
        rect = self.rect()              # get the QRectF that define this block
        margin = 20                     # space to effect this block

        if abs(self.start_pos.x() - rect.left()) < margin:
            self.resizing = True
            self.resize_side = "left"
        elif abs(self.start_pos.x() - rect.right()) < margin:
            self.resizing = True
            self.resize_side = "right"
        else:
            self.resizing = False

        event.accept()      # this object 'TimeLineBlock' handled this event, no need to send him next

    def mouseMoveEvent(self, event):
        """
        This method fire when the user click on the object and moving.

        :param event:
        :return:
        """
        if self.resizing:
            rect = self.rect()  # get the QRectF that define this block
            dx = event.pos().x() - self.start_pos.x()

            if self.resize_side == "right":
                new_right = rect.right() + dx
                max_right = int(self.cycle_time.toPlainText()) * self.pixel_unit + self.offset * self.pixel_unit
                if new_right > max_right:
                    rect.setRight(max_right)
                    self.setRect(rect)
                    return
                rect.setRight(new_right)
            elif self.resize_side == "left":
                new_left = rect.left() + dx
                if new_left < 0 * self.pixel_unit + self.offset * self.pixel_unit:
                    return
                rect.setLeft(new_left)

            if rect.width() >= 5:   # true - if the width is at least 5 pixels
                self.setRect(rect)              # draw new rect that replace the current rect
                self.update_text_positions()

            self.start_pos = event.pos()

        event.accept()

    def mouseReleaseEvent(self, event):
        """
        This method fire when the user release the click.

        :param event:
        :return:
        """
        self.resizing = False
        self.resize_side = None
        event.accept()

    def update_text_positions(self):
        """
        This method set the numbers on the rect.

        :return:
        """
        rect = self.rect()
        # set text location
        self.left_text.setPos(rect.left(), rect.top() - 3)
        self.right_text.setPos(rect.right() - 20, rect.top() - 3)

        # set location in units of 10
        self.left_text.setPlainText(str(int(rect.left() / self.pixel_unit - self.offset)))
        self.right_text.setPlainText(str(int(rect.right() / self.pixel_unit - self.offset)))


