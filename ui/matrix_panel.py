from PyQt6.QtCore import Qt
from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QTableWidget, QPushButton, QTableWidgetItem, QAbstractItemView

from controllers.data_controller import DataController


class MatrixPanel(QWidget):
    def __init__(self):
        super().__init__()

        # =============== style =============== #
        table_style = """
        /* טבלה כללית */
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
        """

        # =============== fields =============== #
        self.tbl = QTableWidget(self)
        self.tbl.itemChanged.connect(self.on_cell_changed)
        self.tbl.setAlternatingRowColors(True)
        self.tbl.setStyleSheet(table_style)

        self.btn = QPushButton("עדכן", self)

        self.changes = []  # כאן נשמור (move_out, move_in, value)

        self.length = None

        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== layout =============== #
        layout = QVBoxLayout(self)
        layout.addWidget(self.tbl)
        layout.addWidget(self.btn)

        self.setLayout(layout)

        self.hide()



    def show_panel(self):
        # get all data
        all_moves           = self.data_controller.get_all_moves()
        all_moves_names     = [m.name for m in all_moves if not m.name.startswith("B")]
        self.length              = len(all_moves_names)

        # Set matrix
        self.tbl.setRowCount(self.length)
        self.tbl.setColumnCount(self.length)
        self.tbl.setHorizontalHeaderLabels(all_moves_names)
        self.tbl.setVerticalHeaderLabels(all_moves_names)
        # self.tbl.horizontalHeader().setFixedHeight(40)
        # self.tbl.horizontalHeader().setFixedWidth(40)
        self.tbl.verticalHeader().setDefaultSectionSize(50)
        # self.tbl.verticalHeader().setFixedWidth(150)
        self.tbl.blockSignals(True)
        self.set_values()
        self.dark_Pedestrian()
        self.shade_diagonal()
        self.tbl.blockSignals(False)
        self.show()

    def set_values(self):
        all_cells = self.data_controller.get_all_matrix_cells()
        self.tbl.clearContents()


        row_idx = {}  # key - value of header, value - index of the header
        for i in range(self.length):
            it = self.tbl.verticalHeaderItem(i)
            if it:
                row_idx[it.text().strip()] = i

        col_idx = {} # key - value of header, value - index of the header
        for j in range(self.tbl.columnCount()):
            it = self.tbl.horizontalHeaderItem(j)
            if it:
                col_idx[it.text().strip()] = j

        # מילוי הערכים לפי MatrixCell(move_out, move_in, wait_time)
        for cell in all_cells:
            i = row_idx.get(cell.move_out) # value = dict.get(key)
            j = col_idx.get(cell.move_in)
            if i is None or j is None:
                continue

            item = QTableWidgetItem(str(cell.wait_time))
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            self.tbl.setItem(i, j, item)

        for row_name, col_name, val in self.changes:
            i = row_idx.get(row_name)
            j = col_idx.get(col_name)
            if i is None or j is None:
                continue
            item = self.tbl.item(i, j) or QTableWidgetItem()
            self.tbl.setItem(i, j, item)
            item.setData(Qt.ItemDataRole.FDisplayRole, int(val) if str(val).isdigit() else str(val))
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)

    def shade_diagonal(self):
        

        for i in range(self.length):  # תאורה כשאינדקס שורה == אינדקס עמודה
            item = self.tbl.item(i, i)
            if item is None:
                item = QTableWidgetItem("")  # אפשר גם "—"
                self.tbl.setItem(i, i, item)
            item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
            self.tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)  # לא נותן לטבלה בכלל לקבל focus
            self.tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)  # לא מאפשר בחירה

            item.setBackground(QColor(220, 220, 220))  # אפור בהיר
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)


    def dark_Pedestrian(self):
        

        for i in range(self.length):
            P_rows = [i for i in range(self.tbl.rowCount())
                      if self.tbl.verticalHeaderItem(i).text().strip().lower().startswith('p')]
            P_cols = [j for j in range(self.tbl.columnCount())
                      if self.tbl.horizontalHeaderItem(j).text().strip().lower().startswith('p')]

        # black = QColor(255, 0, 0)
        for i in P_rows:
            for j in P_cols:
                item = self.tbl.item(i, j) or QTableWidgetItem("")
                item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
                self.tbl.setItem(i, j, item)
                # item.setBackground(black)

    def on_cell_changed(self, item: QTableWidgetItem):
        # שמות הכותרות לשורה/עמודה
        i, j = item.row(), item.column()
        row_name = self.tbl.verticalHeaderItem(i).text().strip()
        col_name = self.tbl.horizontalHeaderItem(j).text().strip()

        text = (item.text() or "").strip()
        # המרה למספר אם רלוונטי (אחרת שמור כמחרוזת)
        try:
            val = int(text)
        except ValueError:
            val = text

        # שמירת השינוי
        self.changes.append((row_name, col_name, val))
        print(f"row: {row_name:<5}, col: {col_name:<5}, value: {val}")
        # אופציונלי: להימנע מריבוי רשומות, אפשר לשמור גם במפה:
        # self._last_changes[(row_name, col_name)] = val

