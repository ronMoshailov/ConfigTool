class ParametersTaProgram:
    def __init__(self, program_number:int, min_list:[int], max_list:[int], type_list:[int], str:int, cycle:int):
        self.program_number = program_number
        self.min_list = min_list
        self.max_list = max_list
        self.type_list = type_list
        self.str = str
        self.cycle = cycle


class ParametersTaModel:
    def __init__(self):
        self.parameters = []

    def add_program(self, program_number, min_list, max_list, type_list, str, cycle):
        self.parameters.append(ParametersTaProgram(program_number, min_list, max_list, type_list, str, cycle))

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



