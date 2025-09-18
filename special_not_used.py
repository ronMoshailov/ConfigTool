# import re
#
# from PyQt6.QtCore import Qt
# from PyQt6.QtWidgets import QWidget
#
# from config.style import blue_button_white_text_style, combo_style
# from entities.log import Log
#
#
#
#
# def create_window(app, title = None):
#     """
#     This method create new window and return him.
#
#     :param title: The title of the window
#     :param app: The app
#     :return:
#     """
#     if title is None:
#         Log.warning("WARNING: Your title is 'None' so the window has no title")
#     window = QWidget()
#     window.setWindowTitle(title)
#     window.setWindowState(Qt.WindowMaximized)
#     # screen = app.primaryScreen().availableGeometry().center()
#     # window.setGeometry(screen.x(), screen.y() // 2, 800, 600)  # (X, Y, Width, Height)
#     return window
#
#
# def set_blue_button_white_text_style(btn_list):
#     """
#     This method set button to blue color and white text style.
#
#     :param btn_list: List of buttons
#     :return: None
#     """
#     for btn in btn_list:
#         btn.setStyleSheet(blue_button_white_text_style)
#
#
#
#
#
# def build_combo(combo, item_list):
#     """
#     This method set values to combo from the item_list.
#
#     :param combo: The combo box
#     :param item_list: The list of values to add to the combo box
#     :return: None
#     """
#     item_list.sort()
#     for idx, item in enumerate (item_list):
#         combo.addItem(item)
#         combo.setItemData(idx, Qt.AlignCenter, Qt.TextAlignmentRole)
#     combo.setLayoutDirection(Qt.RightToLeft)
#     combo.setStyleSheet(combo_style)
#
#
# # Set move layout
# combo_style = """
#             QComboBox {
#                 background-color: #ecf0f1;
#                 border: 2px solid #3498db;
#                 border-radius: 8px;
#                 font-size: 22px;
#                 min-width: 173px;
#                 min-height: 50px;
#                 font-weight: bold;
#                 color: #2c3e50;
#             }
#             QComboBox:hover {
#                 border: 2px solid #2980b9;
#             }
#             QComboBox::drop-down {
#                 subcontrol-origin: padding;
#                 subcontrol-position: top left;
#                 width: 25px;
#                 border-right: 1px solid #3498db;
#             }
#         """
#
# blue_button_white_text_style = """
#                 QPushButton {
#                     background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
#                                                       stop:0 #5dade2, stop:1 #2e86c1);
#                     color: white;
#                     border: 2px solid #2471a3;
#                     border-radius: 10px;
#                     padding: 0px;
#                     font-weight: bold;
#                     font-size: 18px;
#                     min-width: 50px;
#                     min-height: 36px;
#                 }
#
#                 QPushButton:hover {
#                     background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
#                                                       stop:0 #5499c7, stop:1 #21618c);
#                     border: 2px solid #1b4f72;
#                 }
#
#                 QPushButton:checked {
#                     background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1,
#                                                       stop:0 #58d68d, stop:1 #28b463);
#                     border: 2px solid #239b56;
#                 }
#
#                 QPushButton:disabled {
#                     background-color: #d5d8dc;
#                     border: 2px solid #a6acaf;
#                     color: #7f8c8d;
#                 }
#             """
#
# min_group_row_style = """
# /* הווידג'ט העוטף כל שורה */
# #minRowCard {
#     background-color: #ecf0f1;
#     border: 1px solid #3498db;
#     border-radius: 10px;
#     padding: 10px;           /* ריווח פנימי יפה */
#     margin: 4px 0;           /* רווח בין קלפים */
# }
#
# /* פקדי קלט בתוך השורה */
# #minRowCard QLineEdit {
#     background: #ffffff;
#     border: 1px solid #bcd6f0;
#     border-radius: 6px;
#     padding: 6px 8px;
#     font-size: 14px;
#     color: #2c3e50;
# }
# #minRowCard QLineEdit:focus {
#     border: 1px solid #3498db;
# }
#
# /* כפתור עדכון – תואם לסגנון שלך */
# #minRowCard QPushButton {
#     background-color: qlineargradient(x1:0,y1:0,x2:0,y2:1,
#                                       stop:0 #5dade2, stop:1 #2e86c1);
#     color: white;
#     border: 2px solid #2471a3;
#     border-radius: 10px;
#     padding: 6px 12px;
#     font-weight: bold;
#     font-size: 14px;
# }
# #minRowCard QPushButton:hover {
#     background-color: qlineargradient(x1:0,y1:0,x2:0,y2:1,
#                                       stop:0 #5499c7, stop:1 #21618c);
#     border: 2px solid #1b4f72;
# }
# """
#
#
#
#
# phase_rows_style = """
# /* רקע של ה־ScrollArea נקי */
# QScrollArea {
#     border: none;
#     background: transparent;
# }
# QScrollArea > QWidget > QWidget {
#     background: transparent;
# }
#
# /* עטיפות שורות (כל שורה = קטגוריה) */
# #rowTraffic, #rowPedestrian, #rowBlinker {
#     background: #f8fafc;                 /* רקע עדין */
#     border: 1px solid #e2e8f0;           /* מסגרת דקה */
#     border-radius: 12px;
#     padding: 10px;
#     margin-bottom: 12px;
# }
#
# /* פס צבע מימין (ב־RTL זה נראה כמו "טאג" צבעוני) */
# #rowTraffic    { border-right: 6px solid #27ae60; }  /* ירוק - תנועה */
# #rowPedestrian { border-right: 6px solid #8e44ad; }  /* סגול - הולכי רגל */
# #rowBlinker    { border-right: 6px solid #e67e22; }  /* כתום - בלינקרים */
#
# /* כרטיס פריט (המופע) */
# QFrame#phaseCard {
#     background: #ffffff;
#     border: 1px solid #d0d7de;
#     border-radius: 10px;
#     padding: 8px;
#     margin: 4px;                          /* רווח קטן בין כרטיסים */
#     min-width: 140px;
# }
# QFrame#phaseCard:hover {
#     border: 1px solid #3498db;
#     box-shadow: 0px 0px 6px rgba(52,152,219,0.25);   /* לא כל theme תומך, יתעלם אם לא */
# }
#
# /* טקסט ואייקון בתוך הכרטיס */
# QFrame#phaseCard QLabel {
#     font-size: 14px;
#     color: #2c3e50;
# }
#
# /* כפתור מחיקה מינימליסטי */
# QFrame#phaseCard QPushButton {
#     border: none;
#     background: #ffebee;
#     min-width: 28px;
#     min-height: 28px;
#     border-radius: 6px;
# }
# QFrame#phaseCard QPushButton:hover {
#     background: #ffcdd2;
# }
# """
#
#
