import re

# ===================== move pattern ==================== #
move_pattern = re.compile(
    # target: tk.k1   	=  new Move(     tk  , "_1"  , MoveType.Traffic			,	    5 ,   0 , false );
    # should be used with "match"
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

# ===================== matrix pattern ==================== #
matrix_pattern = re.compile(
    # target: tk.zwz.setzeZwz( tk.k1    , tk.pb     ,  7  ,  10);
    # should be used with "match"
    r'\s*'                          # start with 0 or more spaces
    r'tk.zwz.setzeZwz\('            # then should be "tk.zwz.setzeZwz("
    r'\s*'                          # then should be 0 or more spaces
    r'tk\.'                         # then should be "tk."
    r'(k\d+'                        # then should be "k" with at least 1 number after the 'k'
    r'|'                            # or
    r'p[a-z])'                      # then should be "p" with at least lowercase letters
    r'\s*,\s*'                      # then skip to the next argument
    r'tk\.'                         # then should be "tk."
    r'(k\d+'                        # then should be "k" with at least 1 number after the 'k'
    r'|'                            # or
    r'p[a-z])'                      # then should be "p" with at least lowercase letters
    r'\s*,\s*'                      # then skip to the next argument
    r'(\d+)'                        # catch the time when the first argument out
    r'\s*,\s*'                      # then skip to the next argument
    r'(\d+)'                        # catch the time when the second argument out
    r'\);'                          # then should be " );"
)

# ===================== sk channel pattern ==================== #
sk_pattern = re.compile(
    # target: new SchaltKanal(Var.tk1.k5     , Move.lred,   hwRed200  , Hw.HF, sk1, 7, Hw.SK);
    # should be used with "match"
    r'\s*'                              # start with 0 or more spaces
    r'(?://)?'                          # maybe will be here //
    r'\s*'                              # then should be 0 or more spaces
    r'new SchaltKanal\(Var\.tk1\.'      # then should be "new SchaltKanal(Var.tk1."
    r'(k\d+|p[a-z]|B[a-z])'             # then should be "k" with at least 1 number after the 'k'
    r'\s*,\s*'                          # then skip to the next argument
    r'Move\.(?:lred|lamber|lgreen)'     # then should be "Move.lred" or "Move.lamber" or "Move.lgreen"
    r'\s*,\s*'                          # then skip to the next argument
    r'(hwGreen200|hwAmber200|hwRed200)' # then catch "hwGreen200" or "hwAmber200" or "hwRed200"
    r'\s*,\s*'                          # then skip to the next argument
    r'Hw.HF'                            # then should be "Hw.HF"
    r'\s*,\s*'                          # then skip to the next argument
    r'sk(\d+)'                          # then catch the number sk card
    r'\s*,\s*'                          # then skip to the next argument
    r'(\d+)'                            # then catch the number channel
    r'\s*,\s*'                          # then skip to the next argument
    r'Hw.SK\);'                         # then should be the end of the line
)

# ===================== detector pattern ==================== #
detectors_pattern = re.compile(
    # target: tk.d_3	  = new DDetector ("D-3"   , tk.k3 ,    true ,        true ,                  true );
    # should be used with "match"
    r'\s*'                                      # start with 0 or more spaces
    r'tk\.'                                     # then should be "tk."
    r'(\w+)'                                    # catch any word until space (the variable name d_3)
    r'\s*=\s*new\s+'                            # than should be " = new "
    r'(\w+)'                                    # catch detector type (DDetector|EDetector|QDetector|DEDetector|TPDetector)
    r'\s*\('                                    # then skip to the next argument
    r'"([^"]+)"'                                # catch  the name of the detector "D-3"
    r'\s*,\s*'                                  # then skip to the next argument
    r'tk\.'                                     # then should be "th."
    r'(\w+)'                                    # then catch the move that related to the detector
    r'\s*,\s*true\s*,\s*true\s*,\s*true\s*\);'  # then should be the end of the line
)

# ===================== schedule pattern ==================== #
schedule_pattern = re.compile(
    # should be used with "match"
    # target: TagesPlan sun_thur = new TagesPlan("Sunday_Thurday", tk.p06);
    r'(?:TagesPlan\s*(\w+)\s*=\s*new\s*TagesPlan\("[^"]+",\s*tk\.p(\d{2}))'
    r'|'
    # target: sun_thur.initProgWunsch(  6, 00,  tk.p07 );
    r'(?:([a-zA-Z_]\w*)\.initProgWunsch\(\s*(\d{1,2})\s*,\s*(\d{1,2})\s*,\s*tk\.p(\d{2})\s*\);)'
)

# ===================== image pattern ==================== #
image_pattern = re.compile(
    # target number 1: tk.PhEQA     =          new PhaseEQA   (tk, "PhaseEQA"	,    11  ,    3            ,  1              , true       , new Move[] {tk.k5, tk.k7, tk.pa, tk.pb, tk.pe, tk.pf       });
    # target number 2: tk.MainPhase = tk.PhA = new PhaseA     (tk, "PhaseA"     ,    10  , new int[] { 3 } , new int[] { 0 } , false      , new Move[] {tk.k5, tk.k7, tk.pa, tk.pb, tk.pe, tk.pf});
    # should be used with "search"
    r'Phase'                    # seach for "Phase"
    r'([A-Za-z0-9_]+)'          # than search for any word (The name of the image)
    r'\s*'                      # then should be 0 or more spaces
    r'\('                       # "\("
    r'[^,]*'                    # then should be the first argument(tk)
    r',\s*'                     # then skip to the next argument
    r'[^,]*'                    # then should be the second argument (PhaseXxX)
    r',\s*'                     # then skip to the next argument
    r'([^,]+)'                  # then catch the third argument (image number)
    r',\s*'                     # then skip to the next argument
    r'([^,]+)'                  # then catch the fourth argument (skeleton time) 
    r',\s*'                     # then skip to the next argument
    r'([^,]+)'                  # then catch the fifth argument (stop point)
    r',\s*'                     # then skip to the next argument
    r'(true|false)'             # the catch the sixth argument (true or false)
    r'\s*,\s*'                  # then skip to the next argument
    r'new Move\[\]\s*\{'        # then should be "new Move[] {"
    r'([^}]*)'                  # catch everything until you reach to "}"
    r'\}'                       # end of the line
)

# ===================== settings pattern ==================== #
settings_pattern = re.compile(
    r'public\s+static\s+String\s+anlagenName\s*=\s*"(?P<anlagenName>[^"]+)"|'
    r'public\s+static\s+String\s+tk1Name\s*=\s*"(?P<tk1Name>[^"]+)"|'
    r'public\s+static\s+String\s+version\s*=\s*"(?P<version>[^"]+)"|'
    r'public\s+static\s+String\[\]\s+versions\s*=\s*\{(?P<versionsInside>[^}]+)\}|'
    r'Var\.tk1\s*=\s*new\s+Tk1\([^,]+,\s*[^,]+,\s*[^,]+,\s*(?P<tk1Arg>\d+)\s*\)',
    re.MULTILINE
)

# ===================== Explanation ==================== #
# *     - 0 or more
# +     - 1 or more
# |     - or
# ()    - group
# []    - group that possible characters
# [^x]  - group of any character until reached to 'x'

# ===================== xxx ==================== #
# \s    - space
# \d    - number
# \d{x} - exactly x digits
# r     - before sentence is raw string. it means that it's relate to \ as normal char
# (?:xx)    - group without catching
# (?:xx)?   - group without catching and optional
