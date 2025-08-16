import re

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QWidget

from config.style import button_style, combo_style


def clean_text(text):
    """
    Clear non digits or non characters.

    :param text: text to clean
    :return: the cleaned text
    """
    return re.sub(r'[^A-Za-z0-9א-ת]', '', text)

def createWindow(app, title):
    """
    This method create new window and return him.

    :param app: The app
    :return:
    """
    window = QWidget()
    window.setWindowTitle(title)
    screen = app.primaryScreen().availableGeometry().center()
    window.setGeometry(screen.x(), screen.y() // 2, 800, 600)  # (X, Y, Width, Height)
    return window

def set_blue_button_white_text_style(btn_list):
    """
    This method set button to blue color and white text style.

    :param btn_list: List of buttons
    :return: None
    """
    for btn in btn_list:
        btn.setStyleSheet(button_style)

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

def build_combo(combo, list):
    for item in list:
        combo.addItem(item)
    combo.setLayoutDirection(Qt.RightToLeft)
    combo.setStyleSheet(combo_style)


