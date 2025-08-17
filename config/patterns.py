import re

move_pattern = re.compile(
            r"""^                          # התחלה
            \s*tk\.(k\d+|p[a-zA-Z]|B[a-zA-Z])     # שם המונע אחרי tk.
            \s*=\s*new\s+Move\(                   # התחלה של new Move
            \s*tk\s*,\s*                          # הטק tk,
            "[^"]+"\s*,\s*                      # השם בתוך גרשיים כפולים
            MoveType\.([A-Za-z_]+)\s*,\s*        # MoveType
            (\d+)\s*,\s*                          # מספר ראשון (min_green)
            \d+\s*,\s*                            # המספר הבא (לא רלוונטי כרגע)
            (true|false)                          # true/false
            """, re.VERBOSE
        )

matrix_pattern = re.compile(
            r"""
            tk\.zwz\.setzeZwz                      # קריאת הפונקציה
            \(\s*                                   # (
            tk\.(?P<out>[A-Za-z_]\w*)\s*,          # out
            \s*tk\.(?P<inn>[A-Za-z_]\w*)\s*,       # in
            \s*(?P<t1>-?\d+)\s*,                   # זמן 1
            \s*(?P<t2>-?\d+)\s*                    # זמן 2
            \)\s*;                                  # );
            """,
            re.VERBOSE
        )

sk_pattern = re.compile(
            r'^\s*(//)?\s*'  # 1) האם מתחיל ב-// (הערה)
            r'new\s+SchaltKanal\('
            r'\s*Var\.tk1\.([A-Za-z0-9]+)\s*,'  # 2) שם המופע אחרי Var.tk1.  → group(2)
            r'\s*Var\.[^,]+,\s*'  # הצבע הלוגי (לא מעניין אותנו)
            r'(hwGreen200|hwAmber200|hwRed200)\s*,'  # 3) color גולמי → group(3)
            r'\s*[^,]*,\s*sk(\d+)\s*,'  # 4) מספר כרטיס אחרי sk → group(4)
            r'\s*(\d+)\s*,'  # 5) המספר הבא (בין שני הפסיקים) → group(5)
        )

