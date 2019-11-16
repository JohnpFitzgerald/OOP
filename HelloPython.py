import datetime
print("Hello world!")
now = datetime.datetime.now()
print ("Current date and time is ")
print (now.strftime("%A, %d-%m-%Y : %H:%M"))	

firstSet = {1982,1989,1999,2002,2009,2013,2019}
secondSet = {1985,1992,2001,2002,2005,2009,2015}
thirdSet = {}

#firstSet.difference_update(secondSet) 
thirdSet = firstSet.union(secondSet)
print(thirdSet)
	