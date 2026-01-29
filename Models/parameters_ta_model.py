class _ParametersTaProgram:
    def __init__(self, program_number:int, min_list:[int], max_list:[int], type_list:[int], str:int, cycle:int, is_copied: bool):
        self.program_number = program_number
        self.min_list = min_list
        self.max_list = max_list
        self.type_list = type_list
        self.str = str
        self.cycle = cycle
        self.is_copied = is_copied

class ParametersTaModel:
    def __init__(self):
        self.parameters = []

    # ============================== CRUD ============================== #
    def add_program(self, program_number, min_list, max_list, type_list, str, cycle, is_copied):
        """
        This method add new program to the model
        """
        self.parameters.append(_ParametersTaProgram(program_number, min_list, max_list, type_list, str, cycle, is_copied))

    def get_parameters(self):
        """
        This method return all the parameters of the model
        """
        return self.parameters

    def update_parameters(self, program_number, min_list, max_list, type_list, str, cycle, to_copy):
        """
        This method update all the parameters of the model
        """
        if to_copy:
            self.parameters[program_number].min_list    = self.parameters[0].min_list.copy()
            self.parameters[program_number].max_list    = self.parameters[0].max_list.copy()
            self.parameters[program_number].type_list   = self.parameters[0].type_list.copy()
            self.parameters[program_number].str         = self.parameters[0].str
            self.parameters[program_number].cycle       = self.parameters[0].cycle
        else:
            self.parameters[program_number].min_list    = min_list
            self.parameters[program_number].max_list    = max_list
            self.parameters[program_number].type_list   = type_list
            self.parameters[program_number].str         = str
            self.parameters[program_number].cycle       = cycle

        self.parameters[program_number].is_copied       = to_copy

    def get_program(self, prog_number):
        """
        This method return the parameters of the program number
        """
        return self.parameters[prog_number]

    # ============================== Logic ============================== #
    def is_equal(self, param1, param2):
        """
        This method check if two parameters are equal with their values
        """
        # check min
        for min1, min2 in zip (param1.min_list, param2.min_list):
            if min1 != min2:
                return False

        # check max
        for max1, max2 in zip (param1.max_list, param2.max_list):
            if max1 != max2:
                return False

        # check type
        for type1, type2 in zip (param1.type_list, param2.type_list):
            if type1 != type2:
                return False

        # cycle
        if param1.cycle != param2.cycle:
            return False

        # str
        if param1.str != param2.str:
            return False

        # success
        return True

    def reset_parameters_model(self):
        """
        This method clear all the data in the model
        """
        self.parameters.clear()
