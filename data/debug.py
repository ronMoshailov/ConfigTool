from controllers.data_controller import DataController


def displayAllMoves():
    """
    This method print all the information about all moves.

    :param moves_list: List of all moves
    :return: None
    """
    data_controller = DataController()

    moves_list = data_controller.get_all_moves()

    print(moves_list)
    print("\ndisplayAllMoves:")
    for move in moves_list:
        print(f"self.name: {move.name:<5}, move_type: {move.type:<25}, is_main: {move.is_main:<8}, min_green: {move.min_green:<3}")

    matrix_cells = data_controller.data_manager.MatrixCells
    for cell in matrix_cells:
        print(f"out: {cell.move_out:<5}, in: {cell.move_in:<5}, out: {cell.wait_time:<5}")

    sk_list = data_controller.sk_manager
    for sk in sk_list:
        print(f"Number card: {sk.number_card}")
        for channel in sk.sk_channel_list:
            print(f"name: {channel.name:<5}, color: {channel.color:<5}, card_number: {channel.card_number:<5}, channel: {channel.is_commented}, is_commented: {channel.is_commented}")
