combo_style = """
            QComboBox {
                qproperty-alignment: 'AlignRight | AlignVCenter';  /* ליישר טקסט */
                background-color: #ecf0f1;
                border: 2px solid #3498db;
                border-radius: 8px;
                padding: 6px 12px;
                font-size: 14px;
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
            QComboBox::down-arrow {
                image: url(:/qt-project.org/styles/commonstyle/images/arrowdown-16.png);
                width: 12px;
                height: 12px;
            }
            QComboBox QAbstractItemView {
                background-color: #ffffff;
                selection-background-color: #3498db;
                selection-color: white;
                border: 1px solid #2980b9;
            }
        """

blue_button_white_text_style = """
                QPushButton {
                    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                                      stop:0 #5dade2, stop:1 #2e86c1);
                    color: white;
                    border: 2px solid #2471a3;
                    border-radius: 10px;
                    padding: 10px;
                    font-weight: bold;
                    font-size: 14px;
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
    border: 2px solid #3498db;
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
