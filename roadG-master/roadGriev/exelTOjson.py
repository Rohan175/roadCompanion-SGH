import xlrd
from collections import OrderedDict
# Open the workbook and select the first worksheet
wb = xlrd.open_workbook('exel.xlsx')
sh = wb.sheet_by_index(0)
# List to hold dictionaries
cars1 = []
# Iterate through each row in worksheet and fetch values into dict
for rownum in range(1, sh.nrows):
    cars = OrderedDict()
    row_values = sh.row_values(rownum)
    cars['name'] = row_values[0]
    cars['email'] = row_values[1]
    cars['password'] = row_values[2]
    cars['user-type'] = row_values[3]
    cars['area'] = row_values[4]
    cars['city'] = row_values[5]
    cars['state'] = row_values[6]
    cars1.append(cars)

print(cars1)
# Serialize the list of dicts to JSON
#j = json.dumps(cars1)
# Write to file
#with open('data.json', 'w') as f:
 #   f.write(j)
