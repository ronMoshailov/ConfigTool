def replace_lines_starting_with_A(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    new_lines = []
    for line in lines:
        if line.startswith('D'):
            new_lines.append("A was hereȚ\n")
        else:
            new_lines.append(line)

    with open(file_path, 'w', encoding='utf-8') as f:
        f.writelines(new_lines)


path =r"C:\Users\ron.MENORAH-RND\PycharmProjects\TelAvivModifier\B"
replace_lines_starting_with_A(path)