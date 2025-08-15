from PyQt5.QtCore import Qt
from PyQt5.QtGui import QColor
from PyQt5.QtWidgets import QWidget, QVBoxLayout, QTableWidget, QPushButton, QTableWidgetItem

from controllers.data_controller import DataController


class Matrix(QWidget):
    def __init__(self):
        super().__init__()

        self.length = None
        self.data_controller = DataController()

        self.tbl = QTableWidget(self)
        self.btn = QPushButton("עדכן", self)
        self.changes = []  # כאן נשמור (move_out, move_in, value)

        self.tbl.itemChanged.connect(self.on_cell_changed)

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
        matrix_size         = 50

        # Set matrix
        self.tbl.setRowCount(self.length)
        self.tbl.setColumnCount(self.length)
        self.tbl.setHorizontalHeaderLabels(all_moves_names)
        self.tbl.setVerticalHeaderLabels(all_moves_names)
        # self.tbl.horizontalHeader().setFixedHeight(40)
        # self.tbl.horizontalHeader().setFixedWidth(40)
        # self.tbl.verticalHeader().setFixedHeight(40)
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

            item = QTableWidgetItem(str(cell.wait_time))
            item.setTextAlignment(Qt.AlignCenter)
            self.tbl.setItem(i, j, item)

        for row_name, col_name, val in self.changes:
            i = row_idx.get(row_name)
            j = col_idx.get(col_name)
            if i is None or j is None:
                continue
            item = self.tbl.item(i, j) or QTableWidgetItem()
            self.tbl.setItem(i, j, item)
            item.setData(Qt.DisplayRole, int(val) if str(val).isdigit() else str(val))
            item.setTextAlignment(Qt.AlignCenter)

    def shade_diagonal(self):
        

        for i in range(self.length):  # תאורה כשאינדקס שורה == אינדקס עמודה
            item = self.tbl.item(i, i)
            if item is None:
                item = QTableWidgetItem("")  # אפשר גם "—"
                self.tbl.setItem(i, i, item)

            item.setBackground(QColor(220, 220, 220))  # אפור בהיר
            item.setTextAlignment(Qt.AlignCenter)

    def dark_Pedestrian(self):
        

        for i in range(self.length):
            P_rows = [i for i in range(self.tbl.rowCount())
                      if self.tbl.verticalHeaderItem(i).text().strip().lower().startswith('p')]
            P_cols = [j for j in range(self.tbl.columnCount())
                      if self.tbl.horizontalHeaderItem(j).text().strip().lower().startswith('p')]

        black = QColor(0,0,0)
        for i in P_rows:
            for j in P_cols:
                item = self.tbl.item(i, j) or QTableWidgetItem("")
                item.setFlags(item.flags() & ~Qt.ItemIsEditable)
                self.tbl.setItem(i, j, item)
                item.setBackground(black)

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

########################################################
# QTableWidget        -> ready table that based on QTableWidgetItem.
# QTableWidgetItem    ->
#
#
#