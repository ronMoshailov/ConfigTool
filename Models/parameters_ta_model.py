class ParametersTaProgram:
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

    def add_program(self, program_number, min_list, max_list, type_list, str, cycle, is_copied):
        self.parameters.append(ParametersTaProgram(program_number, min_list, max_list, type_list, str, cycle, is_copied))

    def get_parameters(self):
        return self.parameters

    def update_parameters(self, program_number, min_list, max_list, type_list, str, cycle, to_copy):
        if to_copy:
            self.parameters[program_number].min_list = self.parameters[0].min_list.copy()
            self.parameters[program_number].max_list = self.parameters[0].max_list.copy()
            self.parameters[program_number].type_list = self.parameters[0].type_list.copy()
            self.parameters[program_number].str = self.parameters[0].str
            self.parameters[program_number].cycle = self.parameters[0].cycle
            return

        self.parameters[program_number].min_list = min_list
        self.parameters[program_number].max_list = max_list
        self.parameters[program_number].type_list = type_list
        self.parameters[program_number].str = str
        self.parameters[program_number].cycle = cycle

    def is_equal(self, param1, param2):
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


    def reset(self):
        self.parameters.clear()