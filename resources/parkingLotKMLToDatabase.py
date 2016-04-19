import csv
import io
import sys

global shapes

def readFile(fileName):
	latCoords = []
	longCoords = []
	name = ""
	perm = ""
	global shapes

	with open(fileName, 'r') as currentFile:
		outputFile = open("parkingOuput.txt", 'w')
		outputFile.write('INSERT INTO ParkingLot (name, latitude, longitude, permission) VALUES\n')

		for line in currentFile:
			if ':' in line:
				if (latCoords):
					writeLine(outputFile,name,perm,latCoords,longCoords)
				(perm, name) = line.split(':')
				latCoords = []
				longCoords = []
			if ',' in line:
				(latC, longC, dummy) = line.split(',')
				latCoords.append(latC)
				longCoords.append(longC)

		writeLine(outputFile,name,perm,latCoords,longCoords)
		outputFile.write(';')
	currentFile.close()
	outputFile.close()


    # f = io.open(fileName,'r')
    # line = f.readline()
    # if (line.):
    # 	print line

    # f.close()
def writeLine(out,name,perm,latCoords,longCoords):

	#NAME
	out.write('(\'')
	out.write(name.rstrip('\n'))
	out.write('\', ')

	#LONG/LAT
	out.write('\'')
	out.write(','.join(latCoords))
	out.write('\', ')
	out.write('\'')
	out.write(','.join(longCoords))
	out.write('\', ')

	#PERMISSION
	out.write('\'')
	out.write(perm.rstrip('\n'))
	out.write('\'),\n')




# def writeLine(name, perm, latCoords, longCoords):
	# out = open("parkingOuput.txt", 'w')
# 	out.write('(')

# 	#NAME
# 	out.write('\'')
# 	out.write(name.rstrip('\n'))
# 	out.write('\', ')

# 	#LONG/LAT
# 	out.write('\'')
# 	out.write(','.join(latCoords))
# 	out.write('\', ')
# 	out.write('\'')
# 	out.write(','.join(longCoords))
# 	out.write('\', ')

# 	#PERMISSION
# 	out.write('\'')
# 	out.write(perm.rstrip('\n'))
# 	out.write('\');')
# 
	# out.close()
    
if __name__ == "__main__":
    fileName = "building-data.txt"
	
    readFile(fileName)
