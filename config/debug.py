from controllers.data_controller import DataController

def displayAllMoves():
    """
    This method print all the information about all moves.

    :param moves_list: List of all moves
    :return: None
    """
    data_controller = DataController()

    moves_list = data_controller.get_all_moves()

    # print all the moves
    print("\nAll moves:")
    for move in moves_list:
        print(f"self.name: {move.name:<5}, move_type: {move.type:<25}, is_main: {move.is_main:<8}, min_green: {move.min_green:<3}")

    # print all matrix instances
    print("\nall matrix:")
    matrix_cells = data_controller.data_manager.MatrixCells
    for cell in matrix_cells:
        print(f"out: {cell.move_out:<5}, in: {cell.move_in:<5}, out: {cell.wait_time:<5}")

    # print all sk channels
    print("\nall sk:")
    sk_list = data_controller.sk_manager
    for sk in sk_list:
        print(f"Number card: {sk.number_card}")
        for channel in sk.sk_channel_list:
            # print(f"name: {channel.name:<5}, color: {channel.color:<10}, channel: {channel.channel:<5}, is_comment: {channel.is_comment}")
            if channel.name != "":
                print(f"name: {channel.name:<5}, color: {channel.color:<10}, channel: {channel.channel:<5}, is_comment: {channel.is_comment}")

    # print all sk channels
    print("\nall detectors:")
    detectors_list = data_controller.get_all_detectors()
    for detector in detectors_list:
        print(f"name: {detector.name:<5}, type: {detector.type:<10}, extension unit: {detector.ext_unit if detector.ext_unit is not None else "None":<5}")

    # print all schedule
    print("\nschedule:")
    schedule_manager = data_controller.schedule_manager
    for manager in schedule_manager:
        print(f" ---- table: {manager.table_num} ---- ")
        for item in manager.schedule_list:
            print(f"hour: {item.hour:<5}, minute: {item.minute:<5}, program_num: {item.program_num:<5}")

    # print all images
    print("\nimages:")
    image_list = data_controller.data_manager.images
    for img in image_list:
        print(f" ---- image: {img.image_name} ---- ")
        print(f"skeleton: {img.skeleton}")
        for move in img.move_list:
            print(f"name: {move.name:<5}")
