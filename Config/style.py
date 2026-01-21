
settings_panel_style = """
/* ********************************* Settings Panel ********************************* */
#SettingsPanel{
    border-radius: 20px;
    border: 1px solid #4b0082;
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #d7bbff, stop:1 #f1e8ff);
}

/* ********************************* Title Label ********************************* */
QLabel#Title {
    background: qlineargradient(x1:0, y1:1, x2:0, y2:0, stop:0 #e9d0fb, stop:1 #b3ecff);
    color: black;
    border: 3px solid #4b0082;
    border-radius: 20px;
    padding: 15px 30px;
    font-size: 28px;
    font-weight: bold;
    font-family: "Arial Black", sans-serif;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.4);
}

/* ********************************* Buttons ********************************* */
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
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5499c7, stop:1 #21618c);
    border: 2px solid #1b4f72;
}

/* ********************************* QLabel ********************************* */
QLabel {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #fdfefe, stop:1 #ebedef);
    color: #2c3e50;
    border: 1px solid #d5d8dc;
    border-radius: 8px;
    padding: 8px 12px;
    font-size: 24px;
    font-weight: bold;
    min-width: 220px;
    border: 1px solid #8b00a5;
}

/* ********************************* LineEdit ********************************* */
QLineEdit{
    background-color: white; 
    border-radius: 10px; 
    min-height: 50px; 
    font-weight: bold; 
    font-size: 18px;
    border: 1px solid #8b00a5;
}

/* ********************************* QListWidget ********************************* */
QListWidget {
    font-size: 18px;
    font-weight: bold;
    color: #2c3e50;
    padding: 5px;
}

QListWidget::item {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #ffffff, stop:1 #e0d4ff);
    border: 1px solid #d5b3ff;
    border-radius: 8px;
    margin: 3px 0px;
    padding: 8px 12px;
}

QListWidget::item:selected {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #bfa0ff, stop:1 #9b5de5);
    color: white;
    border: 1px solid #8b00a5;
}

"""
move_panel_style = """
/* ********************************* Global Style ********************************* */
QWidget {
    background-color: #f5f7fa;
    font-size: 22px;
}

/* ********************************* RootWidget ********************************* */
#RootWidget {
    border-radius: 10px;
    border: 1px solid #0062b3;
    background: #94cfff;
}

/* ********************************* QTableWidget ********************************* */
#RootTable {
    background-color: white;
    border: 1px solid #d0d7de;
    gridline-color: #e1e4e8;
}

#RootTable::item {
    padding: 6px;
}

#RootTable::item:selected {
    background: #d6eaf8;
}

/* ********************************* QHeaderView ********************************* */
QHeaderView::section {
    background-color: #d2e1ff;
    color: #2c3e50;
    font-weight: bold;
    border: 1px solid #d0d7de;
    padding: 6px;
}

/* ********************************* QLineEdit ********************************* */
QLineEdit {
    border: 1px solid #d0d7de;
    border-radius: 6px;
    padding: 4px 6px;
    background-color: #ffffff;
}

QLineEdit:focus {
    border: 1px solid #3498db;
    background-color: #f0f8ff;
}

/* ********************************* QComboBox ********************************* */
QComboBox {
    border: 1px solid #d0d7de;
    border-radius: 6px;
    padding: 4px 6px;
    background-color: #ffffff;
    spacing: 0px;
}

QComboBox:hover {
    border: 1px solid #3498db;
}

QComboBox::drop-down {
    border-left: 1px solid #d0d7de;
    width: 20px;
}

/* ********************************* QCheckBox ********************************* */
QCheckBox::indicator {
    width: 40px;
    height: 40px;
    border-radius: 6px;
    border: 2px solid #b0b8c1;
    background-color: #ffffff;
}

QCheckBox::indicator:hover {
    border: 2px solid #3498db;
}

QCheckBox::indicator:checked {
    background-color: #3498db;
    border: 2px solid #3498db;
}

QCheckBox::indicator:checked:hover {
    background-color: #218cad;
    border: 2px solid #218cad;
}

/* ********************************* QPushButton -> remove_button ********************************* */
QPushButton#remove_button {
    background-color: #e74c3c;
    color: white;
    border: none;
    border-radius: 10px;
    padding: 4px 10px;
    font-weight: bold;
}

QPushButton#remove_button:hover {
    background-color: #c0392b;
}

QPushButton#remove_button:pressed {
    background-color: #a93226;
}

/* ********************************* QPushButton -> add_button ********************************* */
QPushButton#add_button {
    background-color: #27ae60;
    color: white;
    border: none;
    border-radius: 10px;
    padding: 6px 14px;
    font-size: 22px;
    font-weight: bold;
}

QPushButton#add_button:hover {
    background-color: #1e8449;
}

QPushButton#add_button:pressed {
    background-color: #196f3d;
}
"""
matrix_panel_style = """
/* ********************************* RootWidget ********************************* */
#RootWidget {
    border-radius: 10px;
    border: 1px solid #0062b3;
    background: #94cfff;
}

/* ********************************* QTableWidget ********************************* */
QTableWidget {
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 10px;
    gridline-color: #e5e7eb;
    alternate-background-color: #f9fafb;   /* color the even row */
    padding: 10px;
}

QTableWidget::item:hover {
    background: #c2eaff;
}

/* ********************************* QHeaderView ********************************* */
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

/ ******************** remove the scrollbar buttons -> horizontal ******************** /
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

QScrollBar::add-line:horizontal, QScrollBar::sub-line:horizontal {
    width: 0;
}

/ ******************** remove the scrollbar buttons -> vertical ******************** /
QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {
    height: 0;
}
"""
sk_panel_style = """
/* ********************************* RootWidget ********************************* */
#RootWidget {
    border-radius: 10px;
    border: 1px solid #0062b3;
    background: #94cfff;
}

/* ********************************* Title ********************************* */
QLabel#title {
    font-size: 36px;
    font-weight: bold;
    color: #2c3e50;
    padding: 4px 6px;
    border-radius: 6px;
    background: #eef3fb;     
    border: 1px solid #cfd8e3;   
    min-height: 60px;
}

QLabel#title:hover {
    background: #e4ecf8;        
    border: 1px solid #5b8def; 
}

/* ********************************* QCheckBox ********************************* */
QCheckBox#checkbox_comment{
    margin-left:auto;
    margin-right:auto;
}

/* ********************************* QPushButton -> Remove Button ********************************* */
QPushButton#remove_button {
    background-color: #e5533d;  
    color: white;
    border: none;
    border-radius: 12px;
    padding: 6px 14px;
    font-size: 14px;
    font-weight: bold;
}

QPushButton#remove_button:hover {
    background-color: #cf3f2d;    
}

QPushButton#remove_button:pressed {
    background-color: #b73324; 
}

/* ********************************* QPushButton -> Add Button ********************************* */
QPushButton#add_button {
    background-color: #4caf50;   
    color: white;
    border: none;
    border-radius: 12px;
    padding: 6px 14px;
    font-size: 22px;
    font-weight: bold;
}

QPushButton#add_button:hover {
    background-color: #43a047; 
}

QPushButton#add_button:pressed {
    background-color: #388e3c;
}

/* ********************************* QPushButton -> Update Button ********************************* */
QPushButton#update_button {
    background-color: #ae57cb;
    color: white;
    border: none;
    border-radius: 12px;
    padding: 6px 14px;
    font-size: 22px;
    font-weight: bold;
}

QPushButton#update_button:hover {
    background-color: #86469b;
}

QPushButton#update_button:pressed {
    background-color: #5e316d;
}

/* ********************************* Wrap ********************************* */
QWidget#wrap{
    background-color: #e4f0fb;
    border-radius: 12px;
}

/* ********************************* Scroll ********************************* */
QWidget#ScrollContent{
    background-color: #ffffff;    
    border-radius: 12px;
    border: 1px solid #d0d7de; 
}

QScrollArea{
    border-radius: 12px;
    border: 1px solid #d0d7de;
}
"""
detector_panel_style = """
/* ********************************* Global Style ********************************* */
QWidget {
    background-color: #f5f7fa;
    font-size: 22px;
}

/* ********************************* RootWidget ********************************* */
#RootWidget {
    border-radius: 10px;
    border: 1px solid #0062b3;
    background: #94cfff;
}

/* ********************************* QTableWidget ********************************* */
#RootTable {
    background-color: white;
    border: 1px solid #d0d7de;
    gridline-color: #e1e4e8;
}

#RootTable::item {
    padding: 6px;
}

#RootTable::item:selected {
    background: #d6eaf8;
}

/* ********************************* QHeaderView ********************************* */
QHeaderView::section {
    background-color: #d2e1ff;
    color: #2c3e50;
    font-weight: bold;
    border: 1px solid #d0d7de;
    padding: 6px;
}

/* ********************************* QLineEdit ********************************* */
QLineEdit {
    border: 1px solid #d0d7de;
    border-radius: 6px;
    padding: 4px 6px;
    background-color: #ffffff;
}

QLineEdit:focus {
    border: 1px solid #3498db;
    background-color: #f0f8ff;
}

/* ********************************* QComboBox ********************************* */
QComboBox {
    border: 1px solid #d0d7de;
    border-radius: 6px;
    padding: 4px 6px;
    background-color: #ffffff;
    spacing: 0px;
}

QComboBox:hover {
    border: 1px solid #3498db;
}

QComboBox::drop-down {
    border-left: 1px solid #d0d7de;
    width: 20px;
}

/* ********************************* QCheckBox ********************************* */
QCheckBox::indicator {
    width: 40px;
    height: 40px;
    border-radius: 6px;
    border: 2px solid #b0b8c1;
    background-color: #ffffff;
}

QCheckBox::indicator:hover {
    border: 2px solid #3498db;
}

QCheckBox::indicator:checked {
    background-color: #3498db;
    border: 2px solid #3498db;
}

QCheckBox::indicator:checked:hover {
    background-color: #218cad;
    border: 2px solid #218cad;
}

/* ********************************* QPushButton -> Remove Button ********************************* */
QPushButton#remove_button {
    background-color: #e74c3c;
    color: white;
    border: none;
    border-radius: 10px;
    padding: 4px 10px;
    font-weight: bold;
}

QPushButton#remove_button:hover {
    background-color: #c0392b;
}

QPushButton#remove_button:pressed {
    background-color: #a93226;
}

/* ********************************* QPushButton -> Add Button ********************************* */
QPushButton#add_button {
    background-color: #27ae60;
    color: white;
    border: none;
    border-radius: 10px;
    padding: 6px 14px;
    font-size: 22px;
    font-weight: bold;
}

QPushButton#add_button:hover {
    background-color: #1e8449;
}

QPushButton#add_button:pressed {
    background-color: #196f3d;
}

"""
schedule_panel_style = """
/* ********************************* RootWidget ********************************* */
#RootWidget {
    border-radius: 10px;
    border: 1px solid #0062b3;
    background: #94cfff;
}

/* ********************************* Column Wrap ********************************* */
#column_wrap {
    border-radius: 15px;
    border: 1px solid #0077d7;
    background: qlineargradient( x1:0, y1:0, x2:0, y2:1, stop:0 #cce7ff, stop:1 #ffffff );
    padding: 6px;
}

/* ********************************* Title ********************************* */
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

/* ********************************* Table -> section ********************************* */
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

/* ********************************* Buttons -> Remove Button ********************************* */
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

/* ********************************* Column Wrap ********************************* */
#bottom_widget {
    border-radius: 15px;
    border: 1px solid #0077d7;
    background: qlineargradient( x1:0, y1:0, x2:0, y2:1, stop:0 #cce7ff, stop:1 #ffffff );
    padding: 6px;
}


/* ********************************* Buttons -> Add Button ********************************* */
QPushButton#add_row_button {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #6ee96e, stop:1 #4caf50);
    color: white;
    border: none;
    border-radius: 15px;
    padding: 6px 6px;
    font-size: 18px;
    font-weight: bold;
}

QPushButton#add_row_button:hover {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #5fba63, stop:1 #338237);
}

#add_row_button:disabled {
    background-color: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #c03f45, stop:1 #942a25);
}

/* ********************************* Buttons -> Update Button ********************************* */
QPushButton#update_button {
    background-color: #4caf50;   
    color: white;
    border: none;
    border-radius: 12px;
    padding: 6px 14px;
    font-size: 22px;
    font-weight: bold;
}

QPushButton#update_button:hover {
    background-color: #43a047; 
}

QPushButton#update_button:pressed {
    background-color: #388e3c;
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

/* ********************************* QComboBox -> combo_num_prog ********************************* */
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
image_panel_style = """
/* ********************************* RootWidget ********************************* */
#RootWidget {
    border-radius: 12px;
    border: 1px solid #d0d7de; 
    background: #94cfff;
}

/* ********************************* Scroll Content ********************************* */
#ScrollContent {
    background-color: #ffffff;    
    padding: 10px;
    border-radius: 12px;
}

QScrollArea{
    border-radius: 12px;
    border: 1px solid #d0d7de;
}

/* ********************************* Column Wrap ********************************* */
#column_wrap {
    border-radius: 12px;
    border: 1px solid #0077d7;
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #eaf4ff,
                                stop:1 #ffffff);
    padding: 8px;
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

/* ********************************* buttons -> Add Button ********************************* */
QPushButton#add_button {
    background-color: #27ae60;
    color: white;
    border: none;
    border-radius: 10px;
    padding: 6px 14px;
    font-size: 22px;
    font-weight: bold;
}

QPushButton#add_button:hover {
    background-color: #1e8449;
}

QPushButton#add_button:pressed {
    background-color: #196f3d;
}


/* ********************************* buttons -> Update Button ********************************* */
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

/* ********************************* buttons -> Remove Button ********************************* */
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

/* ********************************* QLineEdit ********************************* */
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

/* ********************************* QCheckBox ********************************* */
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



inter_stage_panel_style = """
/* ********************************* RootWidget ********************************* */
QWidget#root {
    border-radius: 16px;
    border: 1px solid #1a98ff;
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #c6e9ff, stop:1 #e6f7ff);
    padding: 10px;
}

/* ********************************* Scroll Area ********************************* */
QScrollArea {
    border-radius: 12px;
    border: 1px solid #d0d7de;
}
#ScrollContent {
    background-color: #ffffff;    
    padding: 10px;
    border-radius: 12px;
}

/* ********************************* Title ********************************* */
QLabel#title {
    font-size: 26px;
    font-weight: bold;
    color: #2c3e50;
    padding: 6px 12px;
    border-radius: 12px;
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #d2e1ff,
                                stop:1 #f0f8ff);
    border: 1px solid #1a98ff;
    text-align: center;
}
QLabel#title:hover {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #eaf2ff,
                                stop:1 #ffffff);
    border: 1px solid #3498db;
}

/* ********************************* QTableWidget ********************************* */
QTableWidget {
    border: 1px solid #a6c8ff;
    gridline-color: #dce6f1;
    font-size: 14px;
    selection-background-color: #cceeff;
    alternate-background-color: #f5faff;
}

QHeaderView::section {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #d6eaff, stop:1 #ffffff);
    border: 1px solid #a6c8ff;
    padding: 6px;
    font-weight: bold;
    color: #2c3e50;
}
QHeaderView::section:hover {
    background-color: #ecf5ff;
}

QWidget#Wrap {
    border-radius: 14px;
    border: 1px solid #1a98ff;         /* גבול כחול עדין */
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,stop:0 #ffffff, stop:1 #e6f7ff);
    padding: 10px;
    margin-bottom: 12px;
    /* צל קל כדי שיבלוט מעל הרקע */
    box-shadow: 2px 2px 8px rgba(0,0,0,0.08);
}

/* ********************************* Wrap ********************************* */

/* ********************************* QPushButton ********************************* */
QPushButton#add_inter_stage_button,
QPushButton#add_action_button,
QPushButton#remove_button,
QPushButton#update_button {
    color: white;
    font-weight: bold;
    border-radius: 14px;
    padding: 6px 16px;
    font-size: 16px;
    border: none;
    min-height: 36px;
}

/* Add Inter Stage Button */
QPushButton#add_inter_stage_button {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #00bfa5, stop:1 #26c6da);
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}
QPushButton#add_inter_stage_button:hover {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #26c6da, stop:1 #00bfa5);
    box-shadow: 0 6px 8px rgba(0,0,0,0.15);
}
QPushButton#add_inter_stage_button:pressed {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #009688, stop:1 #00acc1);
    box-shadow: none;
}

/* Add Action Button */
QPushButton#add_action_button {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #76ff03, stop:1 #4caf50);
    border: 1px solid #1a98ff;
}
QPushButton#add_action_button:hover {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #5fd000, stop:1 #338237);
}

/* Remove Button */
QPushButton#remove_button {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #ff5252, stop:1 #b71c1c);
}
QPushButton#remove_button:hover {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #ff1744, stop:1 #d50000);
}

/* Update Button */
QPushButton#update_button {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #6ee96e, stop:1 #4caf50);
}
QPushButton#update_button:hover {
    background: qlineargradient(x1:0, y1:0, x2:0, y2:1,
                                stop:0 #5fba63, stop:1 #338237);
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

/* ********************************* QLineEdit ********************************* */
QLineEdit {
    border: 1px solid #a6c8ff;
    border-radius: 6px;
    padding: 4px 8px;
    background: #ffffff;
    font-size: 14px;
}
QLineEdit:focus {
    border: 1px solid #1a98ff;
    background: #f0f9ff;
}

/* ********************************* QCheckBox ********************************* */
QCheckBox {
    spacing: 6px;
    font-size: 14px;
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

"""
navigator_panel_style = """
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
main_window_style = """
        QMainWindow {
            background: qlineargradient(x1:0, y1:0, x2:0, y2:1, stop:0 #cce0ff, stop:1 #e6f2fe);
        }
        """
parameters_ta_window_style = """
                QWidget{
                    font: 20px;
                }
                QCheckBox::indicator {
                    width: 24px;
                    height: 24px;
                    border-radius: 12px;           /* עיגול */
                    border: 2px solid #007F2A;     /* רגיל */
                    background-color: white;
                }
                QCheckBox::indicator:checked {
                    background-color: #000000;     /* כתום כהה כשמסומן */
                    border: 2px solid #007F2A;
                }
                QWidget#container {
                    background-color: #C7FFDA;      /* כתום בהיר */
                }
"""

