import re


def clean_text(text):
    return re.sub(r'[^A-Za-z0-9א-ת]', '', text)
