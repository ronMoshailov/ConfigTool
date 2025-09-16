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
                    font-size: 18px;
                    min-width: 50px;
                    min-height: 36px;
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


# ========== panel style ========== #
navigator_panel_style =  """
            /* ********************************* root ********************************* */
            QWidget#root_panel {
                background: qlineargradient(x1:0, y1:0, x2:1, y2:1, stop:0 #7ea1ff, stop:1 #d6c4fd);
                border: 1px solid #793ff8;
                border-radius: 20px;
            }
            
            /* ********************************* Button container ********************************* */
            QWidget#btn_container {
                background: qlineargradient(x1:0, y1:0, x2:1, y2:1, stop:0 #7ea1ff, stop:1 #b069f1);
                border-radius: 20px;
            }

            /* ********************************* QLineEdit ********************************* */
            QLineEdit#name_textbox, QLineEdit#log_textbox{
                background-color: white; 
                border-radius: 10px; 
                min-height: 30px; 
                font-weight: bold; 
                font-size: 18px;
            }
        
            /* ********************************* QPushButton ********************************* */
            QPushButton[class="navigator_button"] {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5dade2, stop:1 #2e86c1);
                color: white;
                border: 2px solid #2471a3;
                border-radius: 10px;
                padding: 0px;
                font-weight: bold;
                font-size: 18px;
                min-width: 150px;
                min-height: 36px;
            }

            QPushButton[class="navigator_button"]:hover {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5499c7, stop:1 #21618c);
                border: 2px solid #1b4f72;
            }
    
            QPushButton[class="navigator_button"]:disabled {
                background-color: #d5d8dc;
                border: 2px solid #a6acaf;
                color: #7f8c8d;
            }
            
            QPushButton#MainButton {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #E2925D, stop:1 #C1692E);
                color: white;
                border: 2px solid #2471a3;
                border-radius: 10px;
                padding: 0px;
                font-weight: bold;
                font-size: 18px;
                min-width: 150px;
                min-height: 36px;
            }

            QPushButton#MainButton:hover {
                background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #C78254, stop:1 #8C4C21);
                border: 2px solid #1b4f72;
            }
    
            QPushButton#MainButton:disabled {
                background-color: #d5d8dc;
                border: 2px solid #a6acaf;
                color: #7f8c8d;
            }

        """

move_panel_style =  """
        /* ********************************* panel ********************************* */
        #movePanel{
            border-radius: 20px;
            border: 1px solid #1a98ff;
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0   #94cfff, stop:1   #f0f8ff);
        }

        /* ********************************* gray label ********************************* */
        QLabel#gray_label {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #fdfefe, stop:1 #ebedef);
            color: #2c3e50;
            border: 1px solid #d5d8dc;
            border-radius: 8px;
            padding: 8px 12px;
            font-size: 24px;
            font-weight: bold;
            min-width: 170px;
        }

        QLabel#gray_label:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #f8f9f9, stop:1 #d6dbdf);
            border: 1px solid #b2babb;
        }
            
        /* ********************************* text field ********************************* */
        QLineEdit#text_box {
            font-size: 32px;
            max-height: 50px;
            padding: 6px 10px;
            border: 2px solid #cccccc;
            border-radius: 6px;
            background: #fdfdfd;
            selection-background: #3399ff;
        }
        QLineEdit#text_box:focus {
            border: 2px solid #3399ff;       /* מסגרת כחולה בזמן פוקוס */
            background: #ffffff;
        }
        QLineEdit#text_box:disabled {
            background: #eeeeee;
            color: #888888;
        }

        /* ********************************* Radio Button ********************************* */
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

        /* ********************************* scroll ********************************* */
        QScrollArea {
            background: transparent;
            border: None;
        }

        QScrollBar:vertical {
            width: 10px;
            margin: 4px 4px 4px 2px;           /* top right bottom left */
            background: transparent;
        }
        QScrollBar::handle:vertical {
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #5dade2, stop:1 #2e86c1);
            border: 1px solid #2471a3;
            border-radius: 5px;
            min-height: 24px;
        }
        QScrollBar::handle:vertical:hover {
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #5499c7, stop:1 #21618c);
        }
        QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {
            height: 0; /* remove the scrollbar buttons */
        } 


        QScrollBar:horizontal {
            height: 10px;
            margin: 2px 4px 4px 4px;
            background: transparent;
        }
        QScrollBar::handle:horizontal {
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0, stop:0 #5dade2, stop:1 #2e86c1);
            border: 1px solid #2471a3;
            border-radius: 5px;
            min-width: 24px;
        }
        QScrollBar::handle:horizontal:hover {
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0, stop:0 #5499c7, stop:1 #21618c);
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

        #scrollContent {
            background: transparent;
            /* border-top: 1px solid #005aa3; */
            /* border-radius: 25px; */
            /* border-radius: 10px; */
            /* background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #0087f5, stop:1 #eff6fb); */
        }

        QLabel#scroll_label {
            font-size: 36px;
            font-weight: bold;
            color: black;
            padding: 4px 6px;
            border-radius: 30px;
            background: white;
            border: 1px solid #4559a1;
            min-height: 60px;
            min-width: 150px;
        }
        QLabel#scroll_label:hover {
            background: #d6eaf8;
        }
        
        QPushButton#btn_remove_move {
            background: transparent;
            border: None;
            
        }
        
        QPushButton#btn_remove_move:hover {
            border: 1px solid #3498db;      /* עובי וצבע גבול */
            background: #f5f5f5;    /* רקע במעבר עכבר */
        }
        QPushButton#btn_remove_move:pressed {
            background: #e0e0e0;    /* רקע בלחיצה */
        }
        
        /* ********************************* QPushButton ********************************* */
        QPushButton#add_button {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5dade2, stop:1 #2e86c1);
            color: white;
            border: 2px solid #2471a3;
            border-radius: 10px;
            padding: 0px;
            font-weight: bold;
            font-size: 18px;
            min-width: 150px;
            min-height: 36px;
        }

        QPushButton#add_button:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5499c7, stop:1 #21618c);
            border: 2px solid #1b4f72;
        }

        QPushButton#add_button:checked {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #58d68d, stop:1 #28b463);
            border: 2px solid #239b56;
        }

        QPushButton#add_button:disabled {
            background: #d5d8dc;
            border: 2px solid #a6acaf;
            color: #7f8c8d;
        }
        """

min_green_panel_style = """
        /* ********************************* QWidget ********************************* */
        QWidget#root {
            border: 1px solid #04c83b;   /* כחול עדין */
            border-radius: 20px; 
            background-color: qlineargradient(
                x1:0, y1:0, x2:0, y2:1, 
                stop:0 #e1fde7,   /* תכלת בהיר */
                stop:1 #eef2ff    /* סגול-אפור רך */
            );
        }
        
        /* ********************************* QFrame ********************************* */
        QFrame#card {
            background: #fdfdfd; /* אפור-לבן – שונה מהרקע */
            border: 1px solid #d1d5db;
            border-radius: 14px;
            padding: 0px 22px;
            box-shadow: 0px 2px 6px rgba(0,0,0,0.06);
            transition: all 0.25s ease-in-out;
        }
        QFrame#card:hover {
            border: 1px solid #2861ff;   /* סגול */
            background: #faf5ff;         /* נותן הדגשה עדינה */
        }
        
        /* ********************************* QFrame -> QLabel ********************************* */
        QFrame#card QLabel {
            color: #111827;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 6px;
            padding: 5px 12px;
        }
        
        /* ********************************* QFrame -> QLineEdit ********************************* */
        QFrame#card QLineEdit {
            background: #f9fafb;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            padding: 5px 12px;
            font-size: 18px;
        }
        QFrame#card QLineEdit:focus {
            border: 1px solid #3b82f6;
            background: #ffffff;
        }
        QFrame#card QLineEdit[readOnly="true"] {
            background: #fddfdd;
            color: #9ca3af;
        }
        
        /* ********************************* QPushButton ********************************* */
        QPushButton#update_button {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #4CAF50, stop:1 #388E3C);
            color: white;
            border: 2px solid #2e7d32;
            border-radius: 15px;
            padding: 6px 12px;
            font-size: 22px;
            font-weight: bold;
        }
        
        QPushButton#update_button:hover {
            background-color: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #66BB6A,   /* ירוק יותר בהיר */
                stop:1 #43A047
            );
        }
        
        QPushButton#update_button:pressed {
            background-color: #2e7d32;
            border: 2px solid #1b5e20;
        }
        """

matrix_panel_style = """
        /* ********************************* QTableWidget ********************************* */
        QTableWidget {
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 10px;
            gridline-color: #e5e7eb;
            alternate-background-color: #f9fafb;   /* color the even row */
            padding: 10px;
        }

        /* cells */
        
        QTableWidget::item:hover {
            background: #c2eaff;
        }

        /* ********************************* QHeaderView ********************************* */
        /* כותרות (אופקי ואנכי) */
        QHeaderView::section {
            background: #3498db;
            color: white;
            font-weight: bold;
            font-size: 24px;
            padding: 6px 10px;
            border: 1px solid #e5e7eb;
        }
        QHeaderView::section:horizontal {
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }
        QHeaderView::section:hover {
            background: #2563eb;   /* כחול כהה יותר */
        }

        /* ********************************* QScrollBar ********************************* */
        /* פסי גלילה מינימליסטיים */
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
        
        /* remove the scrollbar buttons */
        QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {
            height: 0; 
        } 

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
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0, stop:0 #5499c7, stop:1 #21618c);
        }

        /* remove the scrollbar buttons */
        QScrollBar::add-line:horizontal, QScrollBar::sub-line:horizontal { 
            width: 0; 
        }
        
        /* ********************************* QPushButton ********************************* */
        QPushButton#update_button {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #4CAF50, stop:1 #388E3C);
            color: white;
            border: 2px solid #2e7d32;
            border-radius: 15px;
            padding: 6px 12px;
            font-size: 22px;
            font-weight: bold;
        }
        
        QPushButton#update_button:hover {
            background-color: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #66BB6A,   /* ירוק יותר בהיר */
                stop:1 #43A047
            );
        }
        
        QPushButton#update_button:pressed {
            background-color: #2e7d32;
            border: 2px solid #1b5e20;
        }
        """

detector_panel_style = """
        /* ********************************* panel ********************************* */
        #detectorPanel{
            border-radius: 20px;
            border: 1px solid #ff8c1a;
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0   #fbc898, stop:1   #f0f8ff);
        }

        /* ********************************* gray label ********************************* */
        QLabel#gray_label {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #fdfefe, stop:1 #ebedef);
            color: #2c3e50;
            border: 1px solid #d5d8dc;
            border-radius: 8px;
            padding: 8px 12px;
            font-size: 24px;
            font-weight: bold;
            min-width: 170px;
        }

        QLabel#gray_label:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #f8f9f9, stop:1 #d6dbdf);
            border: 1px solid #b2babb;
        }
        
        QLabel#little_title {
            color: #2c3e50;         /* צבע כהה ואלגנטי */
            font-weight: bold;      /* הדגשה */
            letter-spacing: 1px;    /* ריווח קטן בין אותיות */
            border-bottom: 1px solid #1a98ff;  /* קו דק מתחת */
            padding-bottom: 2px;    /* ריווח קטן מהקו */
        }

        /* ********************************* text field ********************************* */
        QLineEdit#text_box {
            font-size: 32px;
            max-height: 50px;
            padding: 6px 10px;
            border: 2px solid #cccccc;
            border-radius: 6px;
            background: #fdfdfd;
            selection-background: #3399ff;
        }
        QLineEdit#text_box:focus {
            border: 2px solid #3399ff;       /* מסגרת כחולה בזמן פוקוס */
            background: #ffffff;
        }
        QLineEdit#text_box:disabled {
            background: #eeeeee;
            color: #888888;
        }

        /* ********************************* Radio Button ********************************* */
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

        /* ********************************* scroll ********************************* */
        QScrollArea {
            background: transparent;
            border: None;
        }

        QScrollBar:vertical {
            width: 10px;
            margin: 4px 4px 4px 2px;           /* top right bottom left */
            background: transparent;
        }
        QScrollBar::handle:vertical {
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #5dade2, stop:1 #2e86c1);
            border: 1px solid #2471a3;
            border-radius: 5px;
            min-height: 24px;
        }
        QScrollBar::handle:vertical:hover {
            background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #5499c7, stop:1 #21618c);
        }
        QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {
            height: 0; /* remove the scrollbar buttons */
        } 


        QScrollBar:horizontal {
            height: 10px;
            margin: 2px 4px 4px 4px;
            background: transparent;
        }
        QScrollBar::handle:horizontal {
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0, stop:0 #5dade2, stop:1 #2e86c1);
            border: 1px solid #2471a3;
            border-radius: 5px;
            min-width: 24px;
        }
        QScrollBar::handle:horizontal:hover {
            background: qlineargradient(x1:0,y1:0,x2:1,y2:0, stop:0 #5499c7, stop:1 #21618c);
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

        #scrollContent {
            background: transparent;
            /* border-top: 1px solid #005aa3; */
            /* border-radius: 25px; */
            /* border-radius: 10px; */
            /* background: qlineargradient(x1:0,y1:0,x2:0,y2:1, stop:0 #0087f5, stop:1 #eff6fb); */
        }

        QLabel#scroll_label {
            font-size: 36px;
            font-weight: bold;
            color: black;
            padding: 4px 6px;
            border-radius: 30px;
            background: white;
            border: 1px solid #4559a1;
            min-height: 60px;
            min-width: 150px;
        }
        QLabel#scroll_label:hover {
            background: #d6eaf8;
        }

        QPushButton#btn_remove_move {
            background: transparent;
            border: None;

        }

        QPushButton#btn_remove_move:hover {
            border: 1px solid #3498db;      /* עובי וצבע גבול */
            background: #f5f5f5;    /* רקע במעבר עכבר */
        }
        QPushButton#btn_remove_move:pressed {
            background: #e0e0e0;    /* רקע בלחיצה */
        }

        /* ********************************* QPushButton ********************************* */
        QPushButton#add_button {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5dade2, stop:1 #2e86c1);
            color: white;
            border: 2px solid #2471a3;
            border-radius: 10px;
            padding: 0px;
            font-weight: bold;
            font-size: 18px;
            min-width: 150px;
            min-height: 36px;
        }

        QPushButton#add_button:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5499c7, stop:1 #21618c);
            border: 2px solid #1b4f72;
        }

        QPushButton#add_button:checked {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #58d68d, stop:1 #28b463);
            border: 2px solid #239b56;
        }

        QPushButton#add_button:disabled {
            background: #d5d8dc;
            border: 2px solid #a6acaf;
            color: #7f8c8d;
        }
        """

schedule_panel_style = """
        /* ********************************* panel ********************************* */
        #schedulePanel {
            border-radius: 20px;
            border: 1px solid #1a98ff;
            background: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #94cfff,
                stop:1 #f0f8ff
            );
        }
        
        /* ********************************* column wrap ********************************* */
        #column_wrap {
            border-radius: 15px;
            border: 1px solid #0077d7;
            background: qlineargradient( x1:0, y1:0, x2:0, y2:1, stop:0 #cce7ff, stop:1 #ffffff );
            padding: 6px;

        }
        
        /* ********************************* title ********************************* */
        QLabel#title {
            font-size: 20px;
            font-weight: bold;
            color: #2c3e50;
            padding: 6px 10px;
            border-radius: 10px;
            background: qlineargradient( x1:0, y1:0, x2:0, y2:1, stop:0 #d2e1ff, stop:1 #f0f8ff );
            border: 1px solid #1a98ff;
        }
        QLabel#title:hover {
            background: qlineargradient( x1:0, y1:0, x2:0, y2:1, stop:0 #eaf2ff, stop:1 #ffffff );
            border: 1px solid #3498db;
        }
        
        /* ********************************* table ********************************* */
        QHeaderView::section {
            background: #d6eaff;
            border: 1px solid #a6c8ff;
            padding: 4px;
            font-weight: bold;
            color: #2c3e50;
        }
        QHeaderView::section:hover {
            background: #ecf5ff;
        }
        
        /* ********************************* buttons ********************************* */
        QPushButton#remove_button {
            background-color: #e74c3c;   /* אדום */
            color: white;
            border: none;
            border-radius: 14px;
            padding: 2px 6px;
            font-size: 22px;
        }
        QPushButton#remove_button:hover {
            background-color: #c0392b;
        }
        
        QPushButton[class="add_row_button"] {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #76ff03, stop:1 #5ccb15);
            border: 1px solid #00467e;
            border-radius: 20px;
            padding: 6px 8px;
            font-size: 20px;
            font-weight: bold;
        }
        
        QPushButton[class="add_row_button"]:hover {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5fd000, stop:1 #2a662d);
            color: white;
        }
        
        QPushButton[class="add_row_button"]:disabled {
            background-color: red;
        }
        
        QPushButton#update_button {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #6ee96e, stop:1 #4caf50);
            color: white;
            border: none;
            border-radius: 20px;
            padding: 6px 6px;
            font-size: 26px;
            font-weight: bold;
        }
        
        QPushButton#update_button:hover {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5fba63, stop:1 #338237);
        }

        /* ********************************* QTimeEdit ********************************* */
        QTimeEdit#edit_time {
            border: 1px solid #a6c8ff;
            border-radius: 6px;
            padding: 2px 4px;
            background: #f9fcff;
            selection-background-color: #1a98ff;
            font-size: 16px;
        }
        
        /* ********************************* QComboBox ********************************* */
        QComboBox#combo_num_prog {
            border: 1px solid #a6c8ff;
            border-radius: 6px;
            padding: 2px 6px;
            background: #ffffff;
            font-size: 16px;
        }
        QComboBox#combo_num_prog::drop-down {
            border-left: none;
        }
        QComboBox#combo_num_prog QAbstractItemView {
            border: 1px solid #a6c8ff;
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:1 #f1f6ff, stop:0 #ffffff);
        }
        
        QComboBox#combo_num_prog QScrollBar:vertical {
            border: none;
            background: transparent;
            width: 8px;
            margin: 2px 0;
        }
        
        QComboBox#combo_num_prog QScrollBar::handle:vertical {
            background: #a6c8ff;      /* הצבע של הידית */
            border-radius: 4px;
            min-height: 20px;
        }
        
        QComboBox#combo_num_prog QScrollBar::handle:vertical:hover {
            background: #7ea1ff;      /* צבע בהיר יותר במעבר */
        }
        
        QComboBox#combo_num_prog QScrollBar::add-line:vertical,
        QComboBox#combo_num_prog QScrollBar::sub-line:vertical {
            height: 0px;  /* בלי חצים למעלה/למטה */
        }
        
        /* ********************************* QCheckBox ********************************* */
        QCheckBox#check_box {
            border: 1px solid #034fe8;
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:0.5, x3:0, y3:1, stop:0 #b4ccfe, stop:1 #eff4ff, stop:2 #b4ccfe);
            border-radius: 15px;
            padding: 6px 12px;
            spacing: 8px;
            font-size: 18px;
            color: #2c3e50;
        }
        
        QCheckBox#check_box::indicator {
            width: 18px;
            height: 18px;
            border-radius: 4px;
            border: 2px solid #7ea1ff;     /* כחול תואם ל־UI שלך */
            background: #ffffff;
        }
        
        QCheckBox#check_box::indicator:hover {
            border: 2px solid #1a98ff;     /* כחול כהה יותר במעבר */
        }
        
        QCheckBox#check_box::indicator:checked {
            background-color: #0077d8;     /* כחול מלא */
            border: 2px solid #0077d8;
        }
        """

sk_panel_style = """
/* ********************************* title ********************************* */
QLabel#title {
        font-size: 36px;
        font-weight: bold;
        color: #2c3e50;
        padding: 4px 6px;
        border-radius: 6px;
        background: #d2e1ff;
        border: 1px solid #d0d7de;
        min-height: 60px;
    }
    QLabel#title:hover {
        background: #d6eaf8;
        border: 1px solid #3498db;
    }
    /* ********************************* QCheckBox ********************************* */
    QCheckBox#checkbox_comment{
        margin-left:auto; 
        margin-right:auto;
    }
    /* ********************************* QPushButton ********************************* */
    QPushButton#remove_button {
        background-color: #e74c3c;       /* אדום */
        color: white;                    /* טקסט לבן */
        border: none;                    /* בלי גבול */
        border-radius: 12px;             /* פינות מעוגלות */
        padding: 6px 14px;               /* ריווח פנימי */
        font-size: 14px;                 /* גודל טקסט */
        font-weight: bold;               /* טקסט מודגש */
    }
    QPushButton#remove_button:hover {
        background-color: #c0392b;       /* אדום כהה יותר ב-hover */
    }
    QPushButton#remove_button:pressed {
        background-color: #a93226;       /* עוד כהה יותר בלחיצה */
    }

    QPushButton#add_button {
        background-color: #27ae60;       /* ירוק */
        color: white;                    /* טקסט לבן */
        border: none;                    /* בלי גבול */
        border-radius: 12px;             /* פינות מעוגלות */
        padding: 6px 14px;               /* ריווח פנימי */
        font-size: 14px;                 /* גודל טקסט */
        font-weight: bold;               /* טקסט מודגש */
    }
    QPushButton#add_button:hover {
        background-color: #1e8449;       /* ירוק כהה יותר ב-hover */
    }
    QPushButton#add_button:pressed {
        background-color: #196f3d;       /* עוד כהה יותר בלחיצה */
    }

    QPushButton#update_button {
        background-color: #29add6;       /* ירוק */
        color: white;                    /* טקסט לבן */
        border: none;                    /* בלי גבול */
        border-radius: 12px;             /* פינות מעוגלות */
        padding: 6px 14px;               /* ריווח פנימי */
        font-size: 14px;                 /* גודל טקסט */
        font-weight: bold;               /* טקסט מודגש */
    }
    QPushButton#update_button:hover {
        background-color: #218cad;       /* ירוק כהה יותר ב-hover */
    }
    QPushButton#update_button:pressed {
        background-color: #1a6d86;       /* עוד כהה יותר בלחיצה */
    }

"""

image_panel_style = """
        /* ********************************* panel ********************************* */
        #imagePanel {
            border-radius: 20px;
            border: 1px solid #1a98ff;
            background: qlineargradient(
                x1:0, y1:0, x2:0, y2:1,
                stop:0 #751de2,
                stop:1 #5e9ff3
            );
        }

        /* ********************************* scroll content ********************************* */
        #scrollContent {
            background: transparent;
            padding: 10px;
            border-radius: 20px;
        }

        /* ********************************* column wrap ********************************* */
        #column_wrap {
            border-radius: 15px;
            border: 1px solid #0077d7;
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                        stop:0 #eaf4ff,
                                        stop:1 #ffffff);
            padding: 8px;
            margin: 6px;
        }

        /* ********************************* title ********************************* */
        QLabel#title {
            font-size: 18px;
            font-weight: bold;
            color: #2c3e50;
            padding: 6px 10px;
            border-radius: 10px;
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                        stop:0 #d2e1ff,
                                        stop:1 #f0f8ff);
            border: 1px solid #1a98ff;
        }
        QLabel#title:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                        stop:0 #eaf2ff,
                                        stop:1 #ffffff);
            border: 1px solid #3498db;
        }

        /* ********************************* table ********************************* */
        QTableWidget#tbl {
            border: 1px solid #a6c8ff;
            border-radius: 8px;
            gridline-color: #cdddf5;
            background-color: #ffffff;
        }

        QHeaderView::section {
            background: #d6eaff;
            border: 1px solid #a6c8ff;
            padding: 4px;
            font-weight: bold;
            color: #2c3e50;
        }

        QHeaderView::section:hover {
            background: #ecf5ff;
        }

        /* ********************************* buttons ********************************* */
        QPushButton#add_button {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #9aff57,
                                              stop:1 #5ccb15);
            border: 1px solid #3a8f22;
            border-radius: 18px;
            padding: 6px 12px;
            font-size: 18px;
            font-weight: bold;
            color: #2c3e50;
        }
        QPushButton#add_button:hover {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #7cd63e,
                                              stop:1 #3f8f1a);
            color: white;
        }
        
        QPushButton#update_button {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #27d6f7,
                                              stop:1 #068faa);
            border: 1px solid #3a8f22;
            border-radius: 18px;
            padding: 6px 12px;
            font-size: 18px;
            font-weight: bold;
            color: #2c3e50;
        }
        QPushButton#update_button:hover {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #40b9da,
                                              stop:1 #1f73ba);
            color: white;
        }

        QPushButton#remove_button {
            background-color: #e74c3c;
            color: white;
            border: none;
            border-radius: 14px;
            padding: 4px 8px;
            font-size: 16px;
        }
        QPushButton#remove_button:hover {
            background-color: #c0392b;
        }

        QPushButton#add_button {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #76ff03,
                                              stop:1 #5ccb15);
            border: 1px solid #00467e;
            border-radius: 16px;
            padding: 6px 10px;
            font-size: 16px;
            font-weight: bold;
        }
        QPushButton#add_button:hover {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #5fd000,
                                              stop:1 #2a662d);
            color: white;
        }

        /* ********************************* line edit ********************************* */
        QLineEdit {
            border: 1px solid #a6c8ff;
            border-radius: 6px;
            padding: 4px 8px;
            background: #ffffff;
            font-size: 16px;
            text-align: right;
        }
        QLineEdit:focus {
            border: 1px solid #1a98ff;
            background: #f9fcff;
        }

        /* ********************************* checkbox ********************************* */
        QCheckBox#checkbox_comment::indicator {
            width: 18px;
            height: 18px;
            border-radius: 4px;
            border: 2px solid #7ea1ff;
            background: #ffffff;
        }
        QCheckBox#checkbox_comment::indicator:hover {
            border: 2px solid #1a98ff;
        }
        QCheckBox#checkbox_comment::indicator:checked {
            background-color: #0077d8;
            border: 2px solid #0077d8;
        }
"""

program_panel_style = """
        /* ********************************* QLabel ********************************* */
        QLabel#title {
            border: 1px solid #a0c4ff;
            color: #003366;
            font-weight: bold;
        }

        /* ********************************* QWidget ********************************* */
        QWidget#root {
            border: 1px solid #90caf9;
            border-radius: 20px; 
            background-color: qlineargradient(
                x1:0, y1:0, x2:0, y2:1, 
                stop:0 #ffffff,    /* לבן */
                stop:1 #fff5d8     /* תכלת בהיר */
            );
        }

        /* ********************************* QPushButton ********************************* */
        QPushButton {
            background-color: #64b5f6;   /* כחול פסטל */
            border: none;
            border-radius: 8px;
            color: white;
            font-weight: bold;
            font-size: 14px;
            padding: 4px 8px;
            min-width: 24px;
        }
        QPushButton:hover {
            background-color: #42a5f5;   /* hover – כחול קצת כהה יותר */
        }
        QPushButton:pressed {
            background-color: #1e88e5;   /* pressed – כחול עמוק */
        }

        /* ********************************* QCheckBox ********************************* */
        QCheckBox::indicator {
            width: 16px;
            height: 16px;
        }
        QCheckBox::indicator:unchecked {
            border: 2px solid #90caf9;
            background: white;
            border-radius: 4px;
        }
        QCheckBox::indicator:checked {
            border: 2px solid #42a5f5;
            background: #64b5f6;
            border-radius: 4px;
        }
        /* ********************************* QLabel ********************************* */
        QLabel{
            font-weight: bold;
        }

        /* ********************************* QComboBox ********************************* */
        QComboBox {
            background-color: #ffffff;
            border: 1px solid #90caf9;
            border-radius: 6px;
            padding: 4px;
            min-width: 80px;
        }
        QComboBox:hover {
            border: 1px solid #42a5f5;
        }
        QComboBox::drop-down {
            border-left: 1px solid #90caf9;
            width: 20px;
        }
        QComboBox::down-arrow {
            image: none;
            border: none;
            background: #64b5f6;
        }
"""

settings_panel_style = """
        /* ********************************* QLabel ********************************* */
        #SettingsPanel{
            border-radius: 20px;
            border: 1px solid #561151;
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0   #f4c8f1, stop:1   #8c1c84);
        }

        QPushButton[class="settings_button"] {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5dade2, stop:1 #2e86c1);
            color: white;
            border: 2px solid #2471a3;
            border-radius: 10px;
            padding: 0px;
            font-weight: bold;
            font-size: 18px;
            min-width: 150px;
            min-height: 36px;
        }
        
        QPushButton[class="settings_button"]:hover {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #5499c7, stop:1 #21618c);
            border: 2px solid #1b4f72;
        }

        QPushButton[class="settings_button"]:checked {
            background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                              stop:0 #58d68d, stop:1 #28b463);
            border: 2px solid #239b56;
        }

        QPushButton[class="settings_button"]:disabled {
            background-color: #d5d8dc;
            border: 2px solid #a6acaf;
            color: #7f8c8d;
        }
                
        QLabel {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #fdfefe, stop:1 #ebedef);
            color: #2c3e50;
            border: 1px solid #d5d8dc;
            border-radius: 8px;
            padding: 8px 12px;
            font-size: 24px;
            font-weight: bold;
            min-width: 220px;
        }

        QLabel:hover {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #f8f9f9, stop:1 #d6dbdf);
            border: 1px solid #b2babb;
        }
        
        QLineEdit{
            background-color: white; 
            border-radius: 10px; 
            min-height: 50px; 
            font-weight: bold; 
            font-size: 18px;
            max-width: 400px;
        }



                

        """

inter_stage_panel_style = """
/* ********************************* QWidget root ********************************* */
QWidget#root {
    border-radius: 20px;
    border: 1px solid #1a98ff;
    background: qlineargradient(
        x1:0, y1:0, x2:0, y2:1,
        stop:0 #e6f2ff,
        stop:1 #f9fcff
    );
}

QWidget#scroll_area{
    border: 1px solid black;

}
/* ********************************* title ********************************* */
QLabel#title {
    font-size: 28px;
    font-weight: bold;
    color: #2c3e50;
    padding: 6px 12px;
    border-radius: 12px;
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #d2e1ff, stop:1 #f0f8ff);
    border: 1px solid #1a98ff;
}
QLabel#title:hover {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #eaf2ff, stop:1 #ffffff);
    border: 1px solid #3498db;
}

/* ********************************* QTableWidget ********************************* */
QTableWidget {
    border: 1px solid #a6c8ff;
    border-radius: 12px;
    gridline-color: #ddd;
    font-size: 14px;
    selection-background-color: #cceeff;
    alternate-background-color: #f5faff;
}
QTableWidget::item {
    padding: 6px;
}
QTableCornerButton::section {
    background-color: transparent;
    border: none;
}
QHeaderView::section {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #d6eaff, stop:1 #ffffff);
    border: 1px solid #a6c8ff;
    padding: 6px;
    font-weight: bold;
    color: #2c3e50;
}
QHeaderView::section:hover {
    background: #ecf5ff;
}

/* ********************************* QPushButton ********************************* */
QPushButton#remove_button {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #ff6b6b, stop:1 #e74c3c);
    color: white;
    border: none;
    border-radius: 16px;
    padding: 6px 16px;
    font-size: 18px;
    font-weight: bold;
}
QPushButton#remove_button:hover {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #e74c3c, stop:1 #c0392b);
}
QPushButton#remove_button:pressed {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #c0392b, stop:1 #a93226);
}

QPushButton#add_button {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #76ff03, stop:1 #4caf50);
    border: 1px solid #1a98ff;
    border-radius: 16px;
    padding: 6px 16px;
    font-size: 18px;
    font-weight: bold;
    color: white;
}
QPushButton#add_button:hover {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5fd000, stop:1 #338237);
}

QPushButton#update_button {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #6ee96e, stop:1 #4caf50);
    color: white;
    border: none;
    border-radius: 20px;
    padding: 6px 12px;
    font-size: 20px;
    font-weight: bold;
}
QPushButton#update_button:hover {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5fba63, stop:1 #338237);
}

/* ********************************* QComboBox ********************************* */
QComboBox {
    border: 1px solid #a6c8ff;
    border-radius: 8px;
    padding: 4px 6px;
    font-size: 14px;
    background-color: #ffffff;
}
QComboBox::drop-down {
    border-left: 1px solid #a6c8ff;
}
QComboBox QAbstractItemView {
    border: 1px solid #a6c8ff;
    selection-background-color: #cceeff;
    selection-color: #1f3b4d;
    background-color: #ffffff;
}

/* ********************************* QCheckBox ********************************* */
QCheckBox {
    spacing: 8px;
    font-size: 16px;
    color: #2c3e50;
}
QCheckBox::indicator {
    width: 18px;
    height: 18px;
    border-radius: 4px;
    border: 2px solid #7ea1ff;
    background: #ffffff;
}
QCheckBox::indicator:hover {
    border: 2px solid #1a98ff;
}
QCheckBox::indicator:checked {
    background-color: #0077d8;
    border: 2px solid #0077d8;
}

/* ********************************* QScrollArea ********************************* */
QScrollArea {
    border: none;
    background-color: transparent;
}
"""
