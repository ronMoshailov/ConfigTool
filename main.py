import sys

from PyQt6.QtWidgets import QApplication

from ui.main_window import MainWindow


def main():
    app = QApplication([])          # create the app, if you want to get arguments so 'sys.argv'
    window = MainWindow()           # create main window
    window.showMaximized()          # show in full-screen
    window.show()                   # show the main window
    exit_code = app.exec()          # Qt start infinite loop
    sys.exit(exit_code)             # print the exit code


if __name__ == "__main__":
    main()

################

# QSizePolicy.Policy.Maximum
# אומר ל־layout: "אל תתן לי יותר מקום ממה שאני מבקש (sizeHint), אבל אם אין ברירה אפשר להרחיב אותי קצת. ברגע שיש widget אחר שצריך מקום – תעדיף אותו."

# QSizePolicy.Policy.Expanding
# אומר ל־layout: "אני אשמח לגדול ככל שאפשר. אם יש מקום פנוי – תן לי אותו." כלומר: ה־scroll area יתפוס את כל הגובה שהוא יכול.

# Fixed – הגודל קבוע (לא משנה מה קורה בלייאאוט).
#
# Minimum – מקבל רק את המינימום שלו.
#
# Maximum – מוכן לקבל עד המקסימום, אבל לא מעבר.
#
# Preferred – יעדיף את ה־sizeHint() שלו, אבל מוכן להתגמש.
#
# Expanding – ישמח לגדול עד אין סוף (כל עוד ה־layout נותן).

################
# QLayoutItem - A QLayoutItem represents an item in a layout, which can be a widget, a spacer, or a nested layout.





