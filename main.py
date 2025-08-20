import sys

from PyQt6.QtWidgets import QApplication

from ui.main_window import MainWindow

def main():
    app = QApplication([])          # create the app, if you want to get arguments so 'sys.argv'
    window = MainWindow()           # create main window
    window.show()                   # show the main window
    exit_code = app.exec()          # Qt start infinite loop
    sys.exit(exit_code)             # print the exit code

if __name__ == "__main__":
    main()
