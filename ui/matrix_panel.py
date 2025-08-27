from PyQt6.QtCore import Qt
from PyQt6.QtGui import QColor, QFont
from PyQt6.QtWidgets import QWidget, QVBoxLayout, QTableWidget, QPushButton, QTableWidgetItem, QAbstractItemView

from config.colors import light_red_color
from controllers.data_controller import DataController


class MatrixPanel(QWidget):
    """
    Main application window for the Config Tool.
    Holds all panels (set move, min green, matrix, SK, navigator) in a single layout.
    """
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

        # =============== table =============== #
        self.tbl = QTableWidget(self)
        self.tbl.itemChanged.connect(self.on_cell_changed)  # fire a function in every change on the table (by the code and the user)
        self.tbl.setAlternatingRowColors(True)              # allows every even row to be colored in different color
        self.tbl.setStyleSheet(table_style)                 #

        # =============== button =============== #
        self.btn = QPushButton("עדכן", self)
        self.btn.clicked.connect(self._update_changes)

        # =============== button =============== #
        self.changes = []                                   # save the changes with the format (move_out, move_in, value)

        # =============== button =============== #
        self.moves_length = None

        # =============== controllers =============== #
        self.data_controller = DataController()

        # =============== layout =============== #
        root_layout = QVBoxLayout()
        root_layout.addWidget(self.tbl)
        root_layout.addWidget(self.btn)

        # =============== self =============== #
        self.setLayout(root_layout)
        self.hide()



    def show_panel(self):
        # get all data
        all_moves           = self.data_controller.get_all_moves()
        all_moves_names     = [m.name for m in all_moves if not m.name.startswith("B")]
        self.moves_length   = len(all_moves_names)

        # Set matrix
        self.tbl.setRowCount(self.moves_length)                       # set how many rows the table will have
        self.tbl.setColumnCount(self.moves_length)                    # set how many columns the table will have
        self.tbl.setHorizontalHeaderLabels(all_moves_names)     # set headers
        self.tbl.setVerticalHeaderLabels(all_moves_names)       # set headers
        self.tbl.verticalHeader().setDefaultSectionSize(50)     # set height of each row
        self.tbl.blockSignals(True)         # block signals
        self.set_values()                   # set values from DB
        self.disable_pedestrian()           # disable pedestrian cells
        self.shade_diagonal()               # shade diagonal
        self.tbl.blockSignals(False)        # release signals
        self.tbl.setFocusPolicy(Qt.FocusPolicy.NoFocus)                         # disable the focus
        self.tbl.setSelectionMode(QAbstractItemView.SelectionMode.NoSelection)  # disable the choosing
        self.show()

    def set_values(self):
        # initialize
        row_idx = {}  # the key is the value of the header and the value is the index of the header (header_value, header_index)
        col_idx = {}  # key - value of header, value - index of the header

        # set font
        font = QFont()
        font.setFamily("Arial")
        font.setPointSize(14)
        font.setBold(True)

        all_cells = self.data_controller.get_all_matrix_cells() # get all matrix cells
        self.tbl.clearContents()                                # clear the cells (not removing them)

        # set index to each header
        for i in range(self.moves_length):
            it = self.tbl.verticalHeaderItem(i)
            row_idx[it.text().strip()] = i
            col_idx[it.text().strip()] = i

        # fill values by MatrixCell(move_out, move_in, wait_time)
        for cell in all_cells:
            # get the indexes of the cell
            i = row_idx.get(cell.move_out) # value = dict.get(key)
            j = col_idx.get(cell.move_in)
            if i is None or j is None:
                continue

            # fill the cell with 'QTableWidgetItem' that holds the 'wait_time'
            item = QTableWidgetItem(str(cell.wait_time))
            item.setFont(font)
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
            self.tbl.setItem(i, j, item)

        # fill the changes if were
        for row_name, col_name, val in self.changes:
            i = row_idx.get(row_name)
            j = col_idx.get(col_name)
            if i is None or j is None:
                continue
            item = self.tbl.item(i, j) or QTableWidgetItem()    # get the 'QTableWidgetItem' or create new one if not exist
            item.setFont(font)
            item.setData(Qt.ItemDataRole.DisplayRole, int(val)) # set value to cell
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter) # center the text
            self.tbl.setItem(i, j, item)                        # set the cell in the table

    def shade_diagonal(self):
        for i in range(self.moves_length):
            item = self.tbl.item(i, i)
            if item is None:
                item = QTableWidgetItem()
                self.tbl.setItem(i, i, item)
            item.setText("—")                                           # set text
            item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)   # make it not editable
            item.setBackground(QColor(220, 220, 220))                   # light gray
            item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)         # center the text

    def disable_pedestrian(self):
        p_rows = [i for i in range(self.tbl.rowCount())
                  if self.tbl.verticalHeaderItem(i).text().strip().lower().startswith('p')]
        p_cols = p_rows

        for i in p_rows:
            for j in p_cols:
                item = self.tbl.item(i, j) or QTableWidgetItem("")
                item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEditable)
                self.tbl.setItem(i, j, item)
                item.setBackground(light_red_color)

    def on_cell_changed(self, item: QTableWidgetItem):

        row_idx = item.row()        # get row index of the item
        col_idx = item.column()     # get column index of the item

        row_name = self.tbl.verticalHeaderItem(row_idx).text().strip()      # get row header name
        col_name = self.tbl.horizontalHeaderItem(col_idx).text().strip()    # get column header name

        val = item.text().strip()

        if val == "" or not val.isdigit():
            item.setText("")
            self.data_controller.write_log("Only numbers are allowed", "r")
            return False

        # create font
        font = QFont()
        font.setFamily("Arial")
        font.setPointSize(14)
        font.setBold(True)

        self.tbl.blockSignals(True)                         # block signals
        item.setFont(font)                                  # set font
        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter) # set text in the center
        self.tbl.blockSignals(False)                        # release signals
        self.changes.append((row_name, col_name, val))      # save changes
        return True

    def _update_changes(self):
        if self.data_controller.update_matrix(self.changes):
            self.changes = []
