import Config

from PyQt6.QtCore import Qt
from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QAbstractItemView, QTableWidget, QTableWidgetItem, QMessageBox

class MatrixView(QWidget):
    def __init__(self):
        super().__init__()

        # Controller Methods
        self.update_method = None

        # Data
        self.moves_length = None
        self.changed_cells = []

        # Table
        self.tbl = QTableWidget(self)
        self.tbl.setAlternatingRowColors(True)                                  # allows every even row to be colored in different color
        self.tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)                         # disable the focus
        self.tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)  # disable the choosing
        self.tbl.verticalHeader().setDefaultSectionSize(50)                     # set height of each row
        self.tbl.itemChanged.connect(self._on_cell_changed)                     # fire a function in every change on the table (by the code and the user)

        # Root Layout
        root_layout = QVBoxLayout()
        root_layout.addWidget(self.tbl)

        # Self
        self.setLayout(root_layout)
        self.setStyleSheet(Config.style.matrix_panel_style)
        self.hide()

    def show_view(self, all_moves, all_cells):
        # Data
        all_names           = [m.name for m in all_moves if not m.name.startswith("B")]
        self.moves_length   = len(all_names)

        # Set Matrix
        self.tbl.blockSignals(True)         # block signals
        self.changed_cells.clear()
        self._init_table(all_names)
        self._fill_values(all_cells)        # set values from DB
        self._disable_pedestrian()          # disable pedestrian cells
        self._shade_diagonal()              # shade diagonal
        self.tbl.blockSignals(False)        # release signals
        self.show()

    def hide_view(self):
        self.hide()

    # ============================== CRUD ============================== #

    # ============================== Logic ============================== #
    def _init_table(self, all_moves_names):
        self.tbl.clear()  # clear content and headers

        # set headers
        self.tbl.setRowCount(self.moves_length)                 # set how many rows the table will have
        self.tbl.setColumnCount(self.moves_length)              # set how many columns the table will have
        self.tbl.setHorizontalHeaderLabels(all_moves_names)     # set headers
        self.tbl.setVerticalHeaderLabels(all_moves_names)       # set headers

        # set empty cells in all the table
        for i in range(self.moves_length):
            for j in range(self.moves_length):
                item = QTableWidgetItem("")
                item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                self.tbl.setItem(i, j, item)

    def _disable_pedestrian(self):
        p_rows = p_cols = [i for i in range(self.tbl.rowCount()) if self.tbl.verticalHeaderItem(i).text().strip().lower().startswith('p')]

        for i in p_rows:
            for j in p_cols:
                item = self.tbl.item(i, j)
                item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
                item.setBackground(Config.constants.light_red_color)

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

        self.update_method(row_name, col_name, val)

    def _fill_values(self, all_cells):
        # data
        row_idx = {}  # key - value of header, value - index of the header
        col_idx = {}  # key - value of header, value - index of the header

        # set index to each header
        for i in range(self.moves_length):
            it = self.tbl.verticalHeaderItem(i)
            row_idx[it.text().strip()] = i  # key - value of header, value - index of the header
            col_idx[it.text().strip()] = i  # key - value of header, value - index of the header

        # fill DB values
        for cell in all_cells:
            i = row_idx.get(cell.move_out) # value = dict.get(key)
            j = col_idx.get(cell.move_in)
            self.tbl.item(i, j).setText(str(cell.wait_time)) # fill the cell


