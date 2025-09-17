import re

from PyQt6.QtWidgets import QScrollArea, QWidget

"""
This file holds all function that can be used generally.
"""

def clean_text(text):
    """
    This method clear any non-letter.

    :param text: text to clean
    :return: the cleaned text
    """
    return re.sub(r'[^A-Za-z0-9א-ת]', '', text)

def is_move_valid(move_name, move_type):
    """
    This method checks if the move is valid.

    :param move_name: name of the move.
    :param move_type: type of the move.
    :return: Ture if the move is valid else False.
    """
    if move_type == "Traffic" or move_type == "Traffic_Flashing":
        if not move_name.startswith("k") or not move_name[1:].isdigit():
            return False, f"שם לא תקין למופע תנועה [{move_name}]"
    elif move_type == "Pedestrian":
        if not move_name.startswith("p") or not move_name[1:].isalpha():
            return False, f"שם לא תקין למופע הולכי רגל [{move_name}]"
    elif move_type == "Blinker" or move_type == "Blinker_Conditional":
        if not move_name.startswith("B") or not move_name[1:].isalpha():
            return False, f"שם לא תקין לבלינקר [{move_name}]"
    return True, ""

def init_scroll(scroll_layout):
    """
    This method initializes the scroll area.

    :param scroll_layout: Layout of the scroll area.
    :return: QScrollArea
    """
    scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
    scroll_area.setWidgetResizable(True)   # make the scroll area resizable

    scroll_content = QWidget()                     # create the widget that will be in the layout.
    scroll_content.setObjectName("scrollContent")  #

    scroll_area.setWidget(scroll_content)     # set 'scroll_area' as father of 'scroll_content'
    scroll_content.setLayout(scroll_layout)        # set the widget as the father of the layout

    return scroll_area

def clear_widget_from_layout(layout_list):
    """
    This method clears the layout from all his widgets.

    :param layout_list: List of layouts to clear.
    :return: None
    """
    for row in layout_list:
        # Warning: 'row.takeAt(0)' remove from first item in the 'row' list, I added just widgets, so I just remove them, it someone will add in the future any layout they will be removed from 'row' but still exist in Qt and because it belongs to Qt the garbage collector will not remove them
        while row.count():  # return how many 'QLayoutItem' exist in the list of 'QLayoutItem'
            item = row.takeAt(0)  # get the first QLayoutItem of the layout 'row' and remove him from the list of the 'QLayoutItem'
            widget = item.widget()  # get the widget
            if widget:
                widget.deleteLater()  # remove widget (when widget remove -> all his layouts and children removed also), safer not remove now so he will be removed safely later by Qt

def set_btn_disable(btn_list):
    """
    This method make all the buttons that need to disabled.

    :return:None
    """
    for btn in btn_list:
        btn.setDisabled(True)

def set_property(class_name, value_name, item_list):
    """
    This method set property to objects.

    :param class_name: class name
    :param value_name: value name
    :param item_list: list of objects to set property
    :return: None
    """
    for item in item_list:
        item.setProperty(class_name, value_name)
