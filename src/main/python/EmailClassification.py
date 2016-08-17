def train(testType,	trainingData):
	"""
	@param testType - 0, 1, or 2, to indicate Example, Provisional, or System.
	@param trainingData - each string contains a single record, and has 4 or more tokens, comma-separated.that character fields could contain commas.
	and, to avoid incorrect interpretation, the content of these columns starts and ends with double quotes.
	Column 1 - ID. Column 2 - Subject. Column 3 - Body. Column 4 - Category.
	"""
    return 0

def classify(msgData, timeLeft):
	"""
	Classifies emails.
	@param message data, containing id, subject and body of the email.
	@param time left, time allowed to do this processing.
	@return  6 or more elements. The first 6 should be 3 pairs of categories and the confidence level of each option.
	Then for  each extracted data field found by your solution, it should return a pair  of strings with the field name and the field value. 
	So the expected returned should looks like this:
	"CAT1","CONF1","CAT2","CONF2","CAT3","CONF3"[,"FIELD NAME 1","FIELD VALUE 1"]...[,"FIELD NAME N","FIELD VALUE N"] 
	values in the range between 0 and 1 inclusive.
	""" 
	result = []
	return result