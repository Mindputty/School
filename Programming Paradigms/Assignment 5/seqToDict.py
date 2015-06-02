# question 2

def seqToDict(mySeq):
    mySeq.sort()
    myDict = {}
    for elem in mySeq:
        mycount = mySeq.count(elem)
        if not elem in myDict:
            myDict[elem] = mycount
    return myDict
