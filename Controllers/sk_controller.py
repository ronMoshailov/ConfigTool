from PyQt6.QtCore import QSignalBlocker, Qt
from PyQt6.QtGui import QBrush
from PyQt6.QtWidgets import QMessageBox, QTableWidget, QTableWidgetItem

import Config


class SkController:
    def __init__(self, view, model):
        # Fields
        self.view = view
        self.model = model
        self.all_moves = None

        # Controller Methods
        self.view.add_sk_method = self.add_sk_card
        self.view.remove_sk_method = self.remove_sk_card
        self.view.change_color_method = self.change_color
        self.view.change_name_method = self.change_name
        self.view.update_comment_method = self.update_comment
        self.view.update_data_method = self.update_data

    def init_model(self, path):
        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()
                if "SK24 sk" in line and not line.startswith("//"):
                    self.model.add_sk()

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                if line.startswith("package"):
                    Config.constants.PROJECT_NUMBER = line.replace("package", "").replace(";", "").strip()
                line = line.strip()
                if "new SchaltKanal" not in line:
                    continue
                match = Config.patterns.sk_pattern.match(line)
                if match:
                    is_commented = bool(match.group(1))
                    name = match.group(2)
                    color = match.group(3)
                    card_number = int(match.group(4))
                    channel = int(match.group(5))
                    self.model.set_channel(card_number, name, color, channel, is_commented)
                    # if card == self.number_card:
                    #     self.sk_channel_list.append(SkChannel(name, color, channel, is_commented))

    def show_view(self, all_moves):
        self.all_moves = all_moves
        self.view.show_view(self.model.sk_list, all_moves)

    # ============================== CRUD ============================== #
    def add_sk_card(self):
        """
        This method add new sk card to the model
        """
        self.model.add_sk()
        self.view.show_view(self.model.sk_list, self.all_moves)

    def remove_sk_card(self, card_num):
        """
        This method remove sk card from the model
        """
        self.model.remove_sk(card_num)
        self.view.show_view(self.model.sk_list, self.all_moves)

    def rename_move(self, old_name, new_name):
        # Used By Main Controller
        """
        This method rename a move from all the sk cards in the model
        """
        self.model.rename_move(old_name, new_name)

    def remove_move(self, move_name):
        # Used By Main Controller
        """
        This method set a removed move as comment
        """
        self.model.remove_move(move_name)

    def change_color(self, table: QTableWidget, row: int, col: int, fix_color = False):
        """
        This method manages the change of the color of the channel
        """
        if col != 2:
            return

        combo = table.cellWidget(row, 1)
        item = table.item(row, 2)
        move_name = combo.currentText()

        with QSignalBlocker(combo), QSignalBlocker(table):
            if move_name == "-":
                return

        cur = item.text()

        if move_name.startswith("k"):
            if fix_color:
                if cur == "":
                    item.setText("🔴")
                return
            nxt_color = {"🔴": "🟡", "🟡": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")
        elif move_name.startswith("p"):
            if fix_color:
                if cur == "🟡" or cur == "":
                    item.setText("🔴")
                return
            nxt_color = {"🔴": "🟢", "🟢": "🔴", "": "🔴"}.get(cur, "🔴")
        elif move_name.startswith("B"):
            if fix_color:
                item.setText("🟡")
                return
            nxt_color = {"🟡": "🟡"}.get(cur, "🟡")
        item.setText(nxt_color)

    def change_name(self, table: QTableWidget, row: int, col: int):
        """
        This method manages the change of the name of the channel
        """
        combo = table.cellWidget(row, col)
        move_name = combo.currentText()

        white = QBrush(Config.constants.white_color)
        gray = QBrush(Config.constants.gray_color)

        with QSignalBlocker(combo), QSignalBlocker(table):
            move_name = "" if move_name == "-" else move_name

            # clear the row
            if move_name == "":
                (table.item(row, 0) or QTableWidgetItem()).setBackground(gray)
                table.cellWidget(row, 1).setStyleSheet(f"QComboBox {{ background-color: rgb({white.color().red()},{white.color().green()},{white.color().blue()}); }}")
                (table.item(row, 2) or QTableWidgetItem()).setBackground(white)
                table.item(row, 2).setText("")
                table.cellWidget(row, 3).setStyleSheet("margin-left:auto; margin-right:auto;")
                table.cellWidget(row, 3).setCheckState(Qt.CheckState.Unchecked)
            else:
                self.change_color(table, row, 2, True)
        return True

    def update_comment(self, table, row_number, state):
        """
        This method manage the logic of the comment checkbox.
        Color the row and check if the row can be in comment.
        """
        # disable the option to check if there is no move
        if table.cellWidget(row_number, 1).currentText() == "-":
            table.cellWidget(row_number, 3).setCheckState(Qt.CheckState.Unchecked)
            return False

        gray_brush = QBrush(Config.constants.gray_color)
        light_green_brush = QBrush(Config.constants.light_green_color)
        white_brush = QBrush(Config.constants.white_color)

        # color the rows
        if Qt.CheckState(state) == Qt.CheckState.Checked:
            # col 0
            (table.item(row_number, 0) or QTableWidgetItem()).setBackground(light_green_brush)
            # col 1
            table.cellWidget(row_number, 1).setStyleSheet(f"QComboBox {{ background-color: rgb({light_green_brush.color().red()},{light_green_brush.color().green()},{light_green_brush.color().blue()}); }}")
            # col 2
            (table.item(row_number, 2) or QTableWidgetItem()).setBackground(light_green_brush)
        else:
            # col 0
            (table.item(row_number, 0) or QTableWidgetItem()).setBackground(gray_brush)
            # col 1
            table.cellWidget(row_number, 1).setStyleSheet(f"QComboBox {{ background-color: rgb({white_brush.color().red()},{white_brush.color().green()},{white_brush.color().blue()}); }}")
            # col 2
            (table.item(row_number, 2) or QTableWidgetItem()).setBackground(white_brush)
        return True

    def update_data(self, tables_list):
        if self._is_names_valid(tables_list) and self._is_color_valid(tables_list):
            for idx, table in enumerate(tables_list):     # for each table
                for row_num in range(24):                       # for each row in table

                    move_name = table.cellWidget(row_num, 1).currentText()
                    status = table.cellWidget(row_num, 3).isChecked()
                    mapping_color = {"🔴": "hwRed200", "🟡": "hwAmber200", "🟢": "hwGreen200"}
                    emoji = table.item(row_num, 2).text()
                    if emoji in mapping_color:
                        color = mapping_color[emoji]
                    else:
                        color = ""

                    if move_name == "-":
                        self.model.set_channel(idx + 1, "", "", row_num+1, False)
                        continue

                    self.model.set_channel(idx + 1, move_name, color, row_num+1, status)

            QMessageBox.information(self.view, "SK כרטיס", "העדכון הצליח")

    # ============================== Logic ============================== #
    def _is_names_valid(self, tables_list):
        """
        This method check if names has exactly the count instances he needs.

        :return: True if succeeded, False otherwise
        """
        dict_count = {}

        for table in tables_list:
            for row_number in range(24):
                move_name = table.cellWidget(row_number, 1).currentText()
                if move_name != "-":
                    if move_name in dict_count:
                        dict_count[move_name] += 1
                    else:
                        dict_count[move_name] = 1

        for key, val in dict_count.items():
            if key.startswith("k"):
                if val != 3:
                    QMessageBox.critical(self.view, "שגיאה", f"המופע {key} מופיע {val} פעמים")
                    return False
            elif key.startswith("p"):
                if val != 2:
                    QMessageBox.critical(self.view, "שגיאה", f"המופע {key} מופיע {val} פעמים")
                    return False
            elif key.startswith("B"):
                if val != 1:
                    QMessageBox.critical(self.view, "שגיאה", f"המופע {key} מופיע {val} פעמים")
                    return False
        return True

    def _is_color_valid(self, tables_list):
        """
        This method check if color has exactly the count instances he needs.
        :return:
        """
        dict_count = {}

        for table in tables_list:
            for row_number in range(24):
                move_name = table.cellWidget(row_number, 1).currentText()
                move_color = table.item(row_number, 2).text()
                if move_name != "-":
                    if move_name in dict_count:
                        if move_color in dict_count[move_name]:
                            QMessageBox.critical(self.view, "שגיאה", f"המופע {move_name} בעל מספר שגוי של הצבע ה-{move_color}")
                            return False
                        dict_count[move_name].append(move_color)
                    else:
                        dict_count[move_name] = []
                        dict_count[move_name].append(move_color)
        return True

    def reset(self):
        # Used By Main Controller
        """
        This method clear all the data in the model
        """
        self.model.reset()

    # ============================== Write To File ============================== #
    def write_to_file(self, path):
        # data
        code = []

        # update tk1.java file
        with open(path, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        for line in lines:
            if "write sk cards here" in line:
                for idx, card in enumerate(self.model.sk_list):
                    code.append(f"\t\tSK24 sk{idx+1} = new SK24(cardsIndex++, (HwTeilknoten)Var.hwTk1);\n")

            if "write sk channels here" in line:
                self.add_lines(code)
                continue

            code.append(line)

        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(code)

    def add_lines(self, code):
        for sk_card in self.model.sk_list:
            card_num = sk_card.card_number
            all_channels = sk_card.all_channels
            for channel in all_channels:
                if not channel.name:
                    continue
                line = ""
                color_mapping = {"hwRed200": "lred","hwAmber200": "lamber", "hwGreen200": "lgreen"}
                line += f"\t\tnew SchaltKanal(Var.tk1.{channel.name}"
                line += " " * (33 - len(line))
                line += f", Move.{color_mapping[channel.color]}"
                line += " " * (51 - len(line))
                line += f", {channel.color}"
                line += " " * (63 - len(line))
                line += f", Hw.HF, sk{card_num},"
                line += " " * (33 - len(line))
                if channel.channel >= 10:
                    line += f"{channel.channel}, Hw.SK);\n"
                else:
                    line += f" {channel.channel}, Hw.SK);\n"

                if channel.is_comment:
                    line = "//" + line

                code.append(line)


