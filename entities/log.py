class Log:
    """
    This class represents log errors.
    """
    @staticmethod
    def error(message: str):
        """
        This method print to the console red message to indicate for errors

        :param message: string of the message
        :return: None
        """
        print(f"\033[91m{message}\033[0m")
        # \033 → means control sequence for the text between '[' to 'm'

    @staticmethod
    def warning(message: str):
        """
        This method print to the console yellow message to indicate for warnings

        :param message: string of the message
        :return: None
        """
        print(f"\033[93m{message}\033[0m")
        # \033 → means control sequence for the text between '[' to 'm'

    @staticmethod
    def success(message: str):
        """
        This method print to the console green message to indicate for success

        :param message: string of the message
        :return: None
        """
        print(f"\033[92m{message}\033[0m")
