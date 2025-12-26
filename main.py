import sys

from PyQt6.QtWidgets import QApplication

from Controllers.main_controller import MainController


def main():
    app = QApplication([])          # create the app, if you want to get arguments so 'sys.argv'
    window = MainController()           # create main window
    # window.showMaximized()          # show in full-screen
    # window.show()                   # show the main window
    exit_code = app.exec()          # Qt start infinite loop
    sys.exit(exit_code)             # print the exit code


if __name__ == "__main__":
    main()






