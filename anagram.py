#ISHAN DESHPANDE
#SY BTECH COMP B
#ANAGRAM PROBLEM STATEMENT


read = open(r"anagram.txt","r+")
strlist=read.readlines()
strlist=[line.rstrip('\n') for line in open(r"anagram.txt","r+")]
anagram=[]
strlist.sort(key = len)
lfor=len(strlist)
for i in range(lfor):
    pstr=sorted(strlist[i])
    pstr=''.join(pstr)
    lps=len(pstr)


    for m in range(i+1,lfor):
        lns=len(strlist[m])
        if lps!=lns:
            break
        else:
            nxtstr=sorted(strlist[m])
            nxtstr=''.join(nxtstr)
            if pstr==nxtstr:
                anagram.append(strlist[m])
                strlist[m]='\0'
    anagram.append(strlist[i])
    strlist[i]='\0'
    for n in anagram:
        print(n, end=" ")
    print("\n")
    del anagram[:]
