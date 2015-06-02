# question 1:

def convert(txtFile):
    inputFile = open(txtFile, 'r')
    outputFile = open('outFile.txt', 'w')
    for line in inputFile:
        myList = line.split()
        costIndex = myList.index('$') + 1
        for word in range(0,(costIndex - 1)):
            print(myList[word], end=' ', file=outputFile)    
        print(0.75 * int(myList[costIndex]), end=' ', file=outputFile)
        print('euros', file=outputFile)
    inputFile.close()
    outputFile.close()
