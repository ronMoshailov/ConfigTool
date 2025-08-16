import re

from entities.sk_channel import SkChannel


class SkManager:
    def __init__(self, number_card):
        self.number_card = number_card
        self.sk_channel_list = []

    def initialize_sk(self, path):
        pattern = re.compile(
            r'^\s*(//)?\s*'  # 1) האם מתחיל ב-// (הערה)
            r'new\s+SchaltKanal\('
            r'\s*Var\.tk1\.([A-Za-z0-9]+)\s*,'  # 2) שם המופע אחרי Var.tk1.  → group(2)
            r'\s*Var\.[^,]+,\s*'  # הצבע הלוגי (לא מעניין אותנו)
            r'(hwGreen200|hwAmber200|hwRed200)\s*,'  # 3) color גולמי → group(3)
            r'\s*[^,]*,\s*sk(\d+)\s*,'  # 4) מספר כרטיס אחרי sk → group(4)
            r'\s*(\d+)\s*,'  # 5) המספר הבא (בין שני הפסיקים) → group(5)
        )

        print(f"SkManager:\tinitialize_sk\t[start] ")

        with open(path, 'r', encoding='utf-8') as file:
            for line in file:
                line = line.strip()

                if "new SchaltKanal" not in line:
                    continue
                print("found line matched")
                match = pattern.match(line)
                if match:
                    is_commented = bool(match.group(1))
                    name = match.group(2)
                    color = match.group(3)
                    card = int(match.group(4))
                    channel = int(match.group(5))
                    if card == self.number_card:
                        print(f"match pattern: {card}")
                        self.sk_channel_list.append(SkChannel(name, color, channel, is_commented))

        print(f"SkManager:\tinitialize_sk\t[end] ")
