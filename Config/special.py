from PyQt6.QtWidgets import QScrollArea, QWidget

import re

def init_scroll(scroll_layout):
    """
    This method initializes the scroll area.

    :param scroll_layout: Layout of the scroll area.
    :return: QScrollArea
    """
    scroll_area = QScrollArea()            # create the container of the scroll bar. (get only widget)
    scroll_area.setWidgetResizable(True)   # make the scroll area resizable
    scroll_area.setObjectName("scroll_area")  #

    scroll_content = QWidget()                     # create the widget that will be in the layout.
    scroll_content.setObjectName("ScrollContent")  #

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

def clean_text(text):
    """
    This method clear any non-letter.

    :param text: text to clean
    :return: the cleaned text
    """
    return re.sub(r'[^A-Za-z0-9א-ת]', '', text)

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

def get_space(min_value, max_value, my_string):
    return " " * max(min_value, max_value+1 - len(str(my_string)))

