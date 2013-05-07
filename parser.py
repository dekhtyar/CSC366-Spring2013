csvFile = open("hitters.txt", 'r')
insertFile = open("BASEBALL-build-hitters.sql", 'w')
str = []
csvFile.readline() #to get rid of the column names at the top
#put "INSERT( " at the beginning of each line
#fill in the insert with the values in the .csv File
#put a ");" at the end
for row in csvFile:
   insertFile.write("INSERT INTO hitters\nVALUES(")
   insertFile.write(row)
   insertFile.write(");\n")

csvFile.close()
insertFile.close()

