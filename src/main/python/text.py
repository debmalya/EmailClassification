import re

def get_text(string):
	"""
	normalizing white space and stripping HTML markups. 
	"""
	text = re.sub('\s+',' ',string)
	text = re.sub(r'<.*?>',' ',text)
	return text
	
	
	
print get_text("<pre>Hi,<br><br>Unless I&#69;m mistaken, this bill has not been paida Do I know if there is a problem or if it&#69;s just an oversight ?<br><br>If it&#69;s an oversight, thank you to make the emergency ")