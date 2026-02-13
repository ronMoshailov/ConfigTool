import re
###########################################
############### Explanation ###############
# *     - 0 or more
# +     - 1 or more
# |     - or
# ()    - group
# []    - group that possible characters
# [^]   - group with impossible characters



# /s    - space
# /d    - number







# 'r' before sentence is raw string. it means that it's relate to \ as normal char

move_pattern = re.compile(
    # target: tk.k1   	=  new Move(     tk  , "_1"  , MoveType.Traffic			,	    5 ,   0 , false );

    r'\s*'                          # start with 0 or more spaces
    r'tk\.'                         # then should be "tk."
    r'(k\d+'                        # then should be "k" with at least 1 number after the 'k'
    r'|'                            # or
    r'p[a-z]'                       # then should be "p" with at least lowercase letters
    r'|'                            # or
    r'B[a-z])'                      # then should be "B" with at least lowercase letters
    r'\s*'                          # then should be 0 or more spaces
    r'='                            # then should be '='
    r'\s*'                          # then should be 0 or more spaces
    r'new\s+Move\('                 # then should be 'new Move( tk,'
    r'\s*'                          # then should be 0 or more spaces
    r'tk'                           # then should be tk
    r'\s*'                          # then should be 0 or more spaces
    r','                            # then should be ,
    r'\s*'                          # then should be 0 or more spaces
    r'"_([1-9]+|[a-zA-Z]+)"'        # then should be "_#]" when # is any number or letter
    r'\s*'                          # then should be 0 or more spaces
    r','                            # then should be ,
    r'\s*'                          # then should be 0 or more spaces
    r'MoveType\.'                   # then should be "MoveType."
    r'([A-Za-z_]+)'                 # catch the move type 
    r'\s*'                          # then should be 0 or more spaces
    r','                            # then should be ,
    r'\s*'                          # then should be 0 or more spaces
    r'(\d+)'                        # then should be any number
    r'\s*,\s*\d+\s*,\s*'            # then skip this section
    r'(true|false)'                 # then should be true or false
    r'\s*\);'                       # then should be the end of the line
)

matrix_pattern = re.compile(
    # target: tk.zwz.setzeZwz( tk.k1    , tk.pb     ,  7  ,  10);
    r'\s*'                          # start with 0 or more spaces
    r'tk.zwz.setzeZwz\('            # then should be "tk.zwz.setzeZwz("
    r'\s*'                          # then should be 0 or more spaces
    r'tk\.'                         # then should be "tk."
    r'(k\d+'                        # then should be "k" with at least 1 number after the 'k'
    r'|'                            # or
    r'p[a-z])'                      # then should be "p" with at least lowercase letters
    r'\s*,\s*'
    r'tk\.'                         # then should be "tk."
    r'(k\d+'                        # then should be "k" with at least 1 number after the 'k'
    r'|'                            # or
    r'p[a-z])'                      # then should be "p" with at least lowercase letters
    r'\s*,\s*'
    r'(\d+)\s*,\s*(\d+)\);'
)

sk_pattern = re.compile(
            r'^\s*(//)?\s*'  # 1) האם מתחיל ב-// (הערה)
            r'new\s+SchaltKanal\('
            r'\s*Var\.tk1\.([A-Za-z0-9]+)\s*,'  # 2) שם המופע אחרי Var.tk1.  → group(2)
            r'\s*Move\.[^,]+,\s*'  # הצבע הלוגי (לא מעניין אותנו)
            r'(hwGreen200|hwAmber200|hwRed200)\s*,'  # 3) color גולמי → group(3)
            r'\s*[^,]*,\s*sk(\d+)\s*,'  # 4) מספר כרטיס אחרי sk → group(4)
            r'\s*(\d+)\s*,'  # 5) המספר הבא (בין שני הפסיקים) → group(5)
        )

# detectors_pattern = re.compile(r'^(?!\s*//)\s*public\s+(DEDetector|DDetector|EDetector|TPDetector|QDetector)\s+([^;]+);')
detectors_pattern = re.compile(
    r"""
    tk\.(\w+)              # 1) e1
    \s*=\s*new\s+
    (\w+)                  # 2) EDetector
    \s*\(\s*
    "([^"]+)"              # 3) E1
    \s*,\s*
    tk\.(\w+)              # 4) k1
    """,
    re.VERBOSE
)

schedule_pattern = re.compile(
    r'(?:TagesPlan\s+(\w+)\s*=\s*new\s+TagesPlan\("[^"]+",\s*tk\.p(\d{2}))'
    r'|'
    r'(?:([a-zA-Z_]\w*)\.initProgWunsch\(\s*(\d{1,2})\s*,\s*(\d{1,2})\s*,\s*tk\.p(\d{2})\s*\);)'
)

image_pattern = re.compile(
    r'Phase([A-Za-z0-9_]+)\s*'              # שם הפאזה (EQA)
    r'\([^,]*,\s*[^,]*,\s*'                 # דילוג על tk, "PhaseEQA"
    r'([^,]+),\s*'                          # 11
    r'([^,]+),\s*'                          # 4
    r'([^,]+),\s*'                          # 1
    r'([^,\)]+).*?'                          # true
    r'Move\[\]\s*\{([^}]*)\}'               # כל מה שבתוך {} של Move[]
)

settings_pattern = re.compile(
    r'public\s+static\s+String\s+anlagenName\s*=\s*"(?P<anlagenName>[^"]+)"|'
    r'public\s+static\s+String\s+tk1Name\s*=\s*"(?P<tk1Name>[^"]+)"|'
    r'public\s+static\s+String\s+version\s*=\s*"(?P<version>[^"]+)"|'
    r'public\s+static\s+String\[\]\s+versions\s*=\s*\{(?P<versionsInside>[^}]+)\}|'
    r'Var\.tk1\s*=\s*new\s+Tk1\([^,]+,\s*[^,]+,\s*[^,]+,\s*(?P<tk1Arg>\d+)\s*\)',
    re.MULTILINE
)



# ^     - start of a row
# $     - end of a row
# .     - every char
# ?     - optional
#