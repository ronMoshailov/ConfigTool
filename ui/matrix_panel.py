from PyQt6.QtCore import Qt
from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QTableWidget, QPushButton, QTableWidgetItem, QAbstractItemView, \
    QMessageBox

from config.colors import light_red_color
from config.style import matrix_panel_style, ariel_14_bold_font
from controllers.data_controller import DataController


class MatrixPanel(QWidget):
    """
    Main application window for the Config Tool.
    Holds all panels (set move, min green, matrix, SK, navigator) in a single layout.
    """
    def __init__(self):
        super().__init__()

        # Controllers
        self.data_controller = DataController()

        # Data
        self.moves_length = None
        self.changes = []

        # Table
        self.tbl = QTableWidget(self)
        self.tbl.setAlternatingRowColors(True)              # allows every even row to be colored in different color
        self.tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)                         # disable the focus
        self.tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)  # disable the choosing
        self.tbl.verticalHeader().setDefaultSectionSize(50)     # set height of each row

        self.tbl.itemChanged.connect(self._on_cell_changed)  # fire a function in every change on the table (by the code and the user)

        # Button
        self.btn = QPushButton("עדכן", self)
        self.btn.setObjectName("update_button")
        self.btn.clicked.connect(self._update_changes)

        # Root Layout
        root_layout = QVBoxLayout()
        root_layout.addWidget(self.tbl)
        root_layout.addWidget(self.btn)

        # Self
        self.setLayout(root_layout)
        self.setStyleSheet(matrix_panel_style)
        self.hide()

    # =============== General methods =============== #
    def show_panel(self):
        # get all data
        all_moves           = self.data_controller.get_all_moves()
        all_moves_names     = [m.name for m in all_moves if not m.name.startswith("B")]
        self.moves_length   = len(all_moves_names)

        # Set matrix
        self.tbl.blockSignals(True)         # block signals
        self.changes.clear()
        self._init_table(all_moves_names)
        self._fill_values()                  # set values from DB
        self._disable_pedestrian()           # disable pedestrian cells
        self._shade_diagonal()               # shade diagonal
        self.tbl.blockSignals(False)        # release signals
        self.show()

    def _fill_values(self):
        # data
        row_idx = {}  # key - value of header, value - index of the header
        col_idx = {}  # key - value of header, value - index of the header

        # set index to each header
        for i in range(self.moves_length):
            it = self.tbl.verticalHeaderItem(i)
            row_idx[it.text().strip()] = i  # key - value of header, value - index of the header
            col_idx[it.text().strip()] = i  # key - value of header, value - index of the header

        # fill DB values
        all_cells = self.data_controller.get_all_matrix_cells()
        for cell in all_cells:
            i = row_idx.get(cell.move_out) # value = dict.get(key)
            j = col_idx.get(cell.move_in)
            self.tbl.item(i, j).setText(str(cell.wait_time)) # fill the cell

        # fill changes values
        for row_name, col_name, val in self.changes:
            i = row_idx.get(row_name)
            j = col_idx.get(col_name)
            self.tbl.item(i, j).setText(val)        # set the cell in the table

    def _disable_pedestrian(self):
        p_rows = p_cols = [i for i in range(self.tbl.rowCount()) if self.tbl.verticalHeaderItem(i).text().strip().lower().startswith('p')]

        for i in p_rows:
            for j in p_cols:
                item = self.tbl.item(i, j)
                item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
                item.setBackground(light_red_color)

    def _shade_diagonal(self):
        for i in range(self.moves_length):
            item = self.tbl.item(i, i)
            item.setText("—")
            item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)   # make it not editable
            item.setBackground(QColor(220, 220, 220))                   # light gray

    def _on_cell_changed(self, item: QTableWidgetItem):
        row_idx = item.row()        # get row index of the item
        col_idx = item.column()     # get column index of the item

        row_name = self.tbl.verticalHeaderItem(row_idx).text().strip()      # get row header name
        col_name = self.tbl.horizontalHeaderItem(col_idx).text().strip()    # get column header name

        val = item.text().strip()

        if val == "":
            item.setText("")
            return

        if not val.isdigit():
            QMessageBox.critical(self, "שגיאה", "הכנס רק מספרים")
            item.setText("")
            return

        # override or add
        for i, (r, c, v) in enumerate(self.changes):
            if r == row_name and c == col_name:
                self.changes[i] = (r, c, val)
                return
        self.changes.append((row_name, col_name, val))

    def _update_changes(self):
        success, message = self.data_controller.update_matrix(self.changes)
        if success:
            QMessageBox.information(self, "Matrix", message)
            self.changes.clear()
            return
        QMessageBox.critical(self, "Matrix", message)

    def _init_table(self, all_moves_names):
        self.tbl.clear()  # מנקה גם את התוכן וגם את הכותרות
        # self.tbl.setRowCount(0)  # מאפס שורות
        # self.tbl.setColumnCount(0)  # מאפס עמודות

        self.tbl.setRowCount(self.moves_length)                 # set how many rows the table will have
        self.tbl.setColumnCount(self.moves_length)              # set how many columns the table will have
        self.tbl.setHorizontalHeaderLabels(all_moves_names)     # set headers
        self.tbl.setVerticalHeaderLabels(all_moves_names)       # set headers

        for i in range(self.moves_length):
            for j in range(self.moves_length):
                item = QTableWidgetItem("")               # תא ריק
                item.setFont(ariel_14_bold_font)          # הגדרת פונטים אם רוצים
                item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                self.tbl.setItem(i, j, item)



