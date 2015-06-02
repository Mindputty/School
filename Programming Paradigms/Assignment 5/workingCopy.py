# question 3

import os, datetime, shutil
def workingCopy(path):
    i = datetime.datetime.now()
    os.mkdir("WorkingCopy")
    dList = os.listdir(path)
    for filename in dList:
        fn = ''
        filenameL = filename.split('.')
        if len(filenameL) > 1:
            _renamed = filenameL[0].split() 
            fn = _renamed[0]
            for index in range(1,len(_renamed)):
                _renamed[index] = _renamed[index].lower()
                if not (_renamed.index(_renamed[index]) == 0):
                    _renamed[index] = _renamed[index].capitalize()
                fn += _renamed[index]
            fn += '%02d%02d%02d' %(i.year,i.month,i.day)
            fn += '.'
            fn += filenameL[len(filenameL)-1]
            shutil.copyfile(filename, "WorkingCopy/%s" %fn)
        
    
