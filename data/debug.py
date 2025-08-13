def displayAllMoves(moves_list):
    """
    This method print all the information about all moves.

    :param moves_list: List of all moves
    :return: None
    """
    print("\ndisplayAllMoves:")
    for move in moves_list:
        print(f"self.name: {move.name:<5}, move_type: {move.type:<25}, is_main: {move.is_main:<8}, min_green: {move.min_green:<3}")
