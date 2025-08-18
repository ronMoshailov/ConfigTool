# Set move layout
combo_style = """
            QComboBox {
                background-color: #ecf0f1;
                border: 2px solid #3498db;
                border-radius: 8px;
                font-size: 22px;
                min-width: 173px;
                min-height: 50px;
                font-weight: bold;
                color: #2c3e50;
            }
            QComboBox:hover {
                border: 2px solid #2980b9;
            }
            QComboBox::drop-down {
                subcontrol-origin: padding;
                subcontrol-position: top left;
                width: 25px;
                border-right: 1px solid #3498db;
            }
        """

blue_button_white_text_style = """
                QPushButton {
                    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                                      stop:0 #5dade2, stop:1 #2e86c1);
                    color: white;
                    border: 2px solid #2471a3;
                    border-radius: 10px;
                    padding: 0px;
                    font-weight: bold;
                    font-size: 24px;
                    min-width: 230px;
                    min-height: 50px;
                }

                QPushButton:hover {
                    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                                      stop:0 #5499c7, stop:1 #21618c);
                    border: 2px solid #1b4f72;
                }

                QPushButton:checked {
                    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                                      stop:0 #58d68d, stop:1 #28b463);
                    border: 2px solid #239b56;
                }

                QPushButton:disabled {
                    background-color: #d5d8dc;
                    border: 2px solid #a6acaf;
                    color: #7f8c8d;
                }
            """

min_group_row_style = """
/* הווידג'ט העוטף כל שורה */
#minRowCard {
    background-color: #ecf0f1;
    border: 1px solid #3498db;
    border-radius: 10px;
    padding: 10px;           /* ריווח פנימי יפה */
    margin: 4px 0;           /* רווח בין קלפים */
}

/* פקדי קלט בתוך השורה */
#minRowCard QLineEdit {
    background: #ffffff;
    border: 1px solid #bcd6f0;
    border-radius: 6px;
    padding: 6px 8px;
    font-size: 14px;
    color: #2c3e50;
}
#minRowCard QLineEdit:focus {
    border: 1px solid #3498db;
}

/* כפתור עדכון – תואם לסגנון שלך */
#minRowCard QPushButton {
    background-color: qlineargradient(x1:0,y1:0,x2:0,y2:1,
                                      stop:0 #5dade2, stop:1 #2e86c1);
    color: white;
    border: 2px solid #2471a3;
    border-radius: 10px;
    padding: 6px 12px;
    font-weight: bold;
    font-size: 14px;
}
#minRowCard QPushButton:hover {
    background-color: qlineargradient(x1:0,y1:0,x2:0,y2:1,
                                      stop:0 #5499c7, stop:1 #21618c);
    border: 2px solid #1b4f72;
}
"""

text_field_style = """
            QLineEdit {
                font-size: 32px;
                max-height: 50px;
                padding: 6px 10px;
                border: 2px solid #cccccc;
                border-radius: 6px;
                background-color: #fdfdfd;
                selection-background-color: #3399ff;
            }
            QLineEdit:focus {
                border: 2px solid #3399ff;       /* מסגרת כחולה בזמן פוקוס */
                background-color: #ffffff;
            }
            QLineEdit:disabled {
                background-color: #eeeeee;
                color: #888888;
            }
        """

radio_tile_style = """
QRadioButton {
    qproperty-iconSize: 22px 22px;   /* אם יש גם אייקון */
    spacing: 8px;                    /* רווח בין הבולט/אייקון לטקסט */
    font-weight: bold;
    font-size: 16px;
    color: #2c3e50;
}

QRadioButton::indicator {
    width: 18px; height: 18px;
    border-radius: 9px;
    border: 2px solid #2471a3;
    background: #ffffff;
    margin: 0 6px;
}
QRadioButton::indicator:hover {
    border: 2px solid #1b4f72;
}
QRadioButton::indicator:checked {
    background: #000000;
    border: 2px solid #4060b9;
}
QRadioButton::indicator:disabled {
    background: #e5e7eb;
    border: 2px solid #a6acaf;
}
"""

scroll_style = """
/* הסרת מסגרת ותיאום רקע */
QScrollArea {
    border: none;
    background: transparent;
}
QScrollArea::viewport {
    background: #f6f8fa;              /* רקע עדין לאזור הגלילה */
    border-radius: 8px;
}
QScrollArea::corner {
    background: #f6f8fa;
}

/* סרגל גלילה אנכי */
QScrollBar:vertical {
    width: 10px;
    margin: 4px 4px 4px 2px;           /* top right bottom left */
    background: transparent;
}
QScrollBar::handle:vertical {
    background: qlineargradient(x1:0,y1:0,x2:0,y2:1,
                                stop:0 #5dade2, stop:1 #2e86c1);
    border: 1px solid #2471a3;
    border-radius: 5px;
    min-height: 24px;
}
QScrollBar::handle:vertical:hover {
    background: qlineargradient(x1:0,y1:0,x2:0,y2:1,
                                stop:0 #5499c7, stop:1 #21618c);
}

/* סרגל גלילה אופקי */
QScrollBar:horizontal {
    height: 10px;
    margin: 2px 4px 4px 4px;
    background: transparent;
}
QScrollBar::handle:horizontal {
    background: qlineargradient(x1:0,y1:0,x2:1,y2:0,
                                stop:0 #5dade2, stop:1 #2e86c1);
    border: 1px solid #2471a3;
    border-radius: 5px;
    min-width: 24px;
}
QScrollBar::handle:horizontal:hover {
    background: qlineargradient(x1:0,y1:0,x2:1,y2:0,
                                stop:0 #5499c7, stop:1 #21618c);
}

/* הסתרת כפתורי החיצים של הסקרולבר */
QScrollBar::add-line, QScrollBar::sub-line {
    width: 0px; height: 0px;
    background: none;
    border: none;
}
QScrollBar::add-page, QScrollBar::sub-page {
    background: transparent;
}

/* רקע התוכן הפנימי */
#scrollContent {
    background: #ffffff;
    border: 1px solid #c8d1dc;
    border-radius: 8px;
}
"""
