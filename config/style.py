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

dark_button_style = """
    QPushButton {
        min-width: 160px;
        background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                          stop:0 #1e3c72, stop:0.5 #2a5298, stop:1 #00c6ff);
        /* כהה מאוד למעלה (#1e3c72), כחול בינוני באמצע (#2a5298), טורקיז בהיר למטה (#00c6ff) */
    }

    QPushButton:hover {
        background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                          stop:0 #162f5c, stop:0.5 #24447c, stop:1 #00a1d9);
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
QScrollArea {
    border: 1px solid #3498db;
    border-radius: 5px;
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
    background: #ff00ff;
    border: 1px solid #c8d1dc;
    border-radius: 8px;
}
"""

phase_rows_style = """
/* רקע של ה־ScrollArea נקי */
QScrollArea {
    border: none;
    background: transparent;
}
QScrollArea > QWidget > QWidget {
    background: transparent;
}

/* עטיפות שורות (כל שורה = קטגוריה) */
#rowTraffic, #rowPedestrian, #rowBlinker {
    background: #f8fafc;                 /* רקע עדין */
    border: 1px solid #e2e8f0;           /* מסגרת דקה */
    border-radius: 12px;
    padding: 10px;
    margin-bottom: 12px;
}

/* פס צבע מימין (ב־RTL זה נראה כמו "טאג" צבעוני) */
#rowTraffic    { border-right: 6px solid #27ae60; }  /* ירוק - תנועה */
#rowPedestrian { border-right: 6px solid #8e44ad; }  /* סגול - הולכי רגל */
#rowBlinker    { border-right: 6px solid #e67e22; }  /* כתום - בלינקרים */

/* כרטיס פריט (המופע) */
QFrame#phaseCard {
    background: #ffffff;
    border: 1px solid #d0d7de;
    border-radius: 10px;
    padding: 8px;
    margin: 4px;                          /* רווח קטן בין כרטיסים */
    min-width: 140px;
}
QFrame#phaseCard:hover {
    border: 1px solid #3498db;
    box-shadow: 0px 0px 6px rgba(52,152,219,0.25);   /* לא כל theme תומך, יתעלם אם לא */
}

/* טקסט ואייקון בתוך הכרטיס */
QFrame#phaseCard QLabel {
    font-size: 14px;
    color: #2c3e50;
}

/* כפתור מחיקה מינימליסטי */
QFrame#phaseCard QPushButton {
    border: none;
    background: #ffebee;
    min-width: 28px;
    min-height: 28px;
    border-radius: 6px;
}
QFrame#phaseCard QPushButton:hover {
    background: #ffcdd2;
}
"""

table_style = """
/* טבלה כללית */
QTableWidget {
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 10px;
    gridline-color: #e5e7eb;
    selection-background-color: #dbeafe;   /* כחול עדין בבחירה */
    selection-color: #111827;
    alternate-background-color: #f9fafb;   /* שורות מתחלפות */
}

/* תאים */
QTableWidget::item {
    padding: 4px 6px;
}
QTableWidget::item:hover {
    background: #c2eaff;
}
QTableWidget::item:selected {
    background: #dbeafe;
}

/* כותרות (אופקי ואנכי) */
QHeaderView::section {
    background: #3498db;          /* כחול */
    color: white;                 /* טקסט לבן */
    font-weight: 600;
    padding: 6px 10px;
    border: 1px solid #e5e7eb;
}
QHeaderView::section:horizontal {
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}
QHeaderView::section:vertical {
    /* נותן מראה של “טאגים” בצד */
    border-radius: 0;             /* בלי עיגול בצד שמאל */
}

/* כפתור הפינה השמאלית-עליונה */
QTableCornerButton::section {
    background: #f3f4f6;
    border: 1px solid #e5e7eb;
}

/* פסי גלילה מינימליסטיים */
QScrollBar:vertical {
    background: transparent;
    width: 10px;
    margin: 2px 2px 2px 0;
}
QScrollBar::handle:vertical {
    background: #cbd5e1;
    min-height: 24px;
    border-radius: 6px;
}
QScrollBar::handle:vertical:hover {
    background: #94a3b8;
}
QScrollBar::add-line:vertical,
QScrollBar::sub-line:vertical { height: 0; }

QScrollBar:horizontal {
    background: transparent;
    height: 10px;
    margin: 0 2px 2px 2px;
}
QScrollBar::handle:horizontal {
    background: #cbd5e1;
    min-width: 24px;
    border-radius: 6px;
}
QScrollBar::handle:horizontal:hover {
    background: #94a3b8;
}
QScrollBar::add-line:horizontal,
QScrollBar::sub-line:horizontal { width: 0; }
"""

red_hover = """
QTableWidget::item:hover {
    background: #ff0000;
"""

gray_label_style = """
                    QLabel {
        background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                          stop:0 #fdfefe, stop:1 #ebedef);
        color: #2c3e50;
        border: 1px solid #d5d8dc;
        border-radius: 8px;
        padding: 8px 12px;
        font-size: 24px;
        font-weight: bold;
        min-width: 230px;
    }

    QLabel:hover {
        background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                          stop:0 #f8f9f9, stop:1 #d6dbdf);
        border: 1px solid #b2babb;
    }
"""


scroll_label_style = """
                QLabel {
                    font-size: 36px;
                    font-weight: bold;
                    color: #2c3e50;
                    padding: 4px 6px;
                    border-radius: 6px;
                    background: #cceeff;
                    border: 1px solid #d0d7de;
                    min-height: 60px;
                }
                QLabel:hover {
                    background: #d6eaf8;
                    border: 1px solid #3498db;
                }
            """

btn_remove_style = """
                QPushButton {
                    border: 1px solid black;      /* עובי וצבע גבול */
                    background-color: white;      /* צבע רקע */
                }
                QPushButton:hover {
                    border: 1px solid #3498db;      /* עובי וצבע גבול */
                    background-color: #f5f5f5;    /* רקע במעבר עכבר */
                }
                QPushButton:pressed {
                    background-color: #e0e0e0;    /* רקע בלחיצה */
                }
            """