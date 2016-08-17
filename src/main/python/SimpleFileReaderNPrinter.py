def readNPrintFile(fileName):
	"""
	Read the passed file name.
	Print each word in a separte line.
	"""
	for line in open(fileName):
		for word in line.split():
			print word
			

readNPrintFile("./Gutso Gold.txt")