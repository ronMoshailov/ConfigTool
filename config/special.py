import re

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QWidget

from config.style import blue_button_white_text_style, combo_style
from entities.log import Log


def clean_text(text):
    """
    Clear non digits or non characters.

    :param text: text to clean
    :return: the cleaned text
    """
    return re.sub(r'[^A-Za-z0-9א-ת]', '', text)


def create_window(app, title = None):
    """
    This method create new window and return him.

    :param title: The title of the window
    :param app: The app
    :return:
    """
    if title is None:
        Log.warning("WARNING: Your title is 'None' so the window has no title")
    window = QWidget()
    window.setWindowTitle(title)
    window.setWindowState(Qt.WindowMaximized)
    # screen = app.primaryScreen().availableGeometry().center()
    # window.setGeometry(screen.x(), screen.y() // 2, 800, 600)  # (X, Y, Width, Height)
    return window


def set_blue_button_white_text_style(btn_list):
    """
    This method set button to blue color and white text style.

    :param btn_list: List of buttons
    :return: None
    """
    for btn in btn_list:
        btn.setStyleSheet(blue_button_white_text_style)


def make_checkable(btn_list):
    """
    This method make buttons checkable.

    :return: None
    """
    for btn in btn_list:
        btn.setCheckable(True)


def set_btn_disable(btn_list):
    """
    This method make all the buttons that need to disable at the beginning.

    :return:None
    """
    for btn in btn_list:
        btn.setDisabled(True)


def build_combo(combo, item_list):
    """
    This method set values to combo from the item_list.

    :param combo: The combo box
    :param item_list: The list of values to add to the combo box
    :return: None
    """
    item_list.sort()
    for idx, item in enumerate (item_list):
        combo.addItem(item)
        combo.setItemData(idx, Qt.AlignCenter, Qt.TextAlignmentRole)
    combo.setLayoutDirection(Qt.RightToLeft)
    combo.setStyleSheet(combo_style)


