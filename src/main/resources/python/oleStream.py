import os, sys
import olefile

#print(sys.argv[1])
#print(sys.argv[2])

switch = int(sys.argv[1])#控制判断分支
input = sys.argv[2]

if switch == 0:
	output = sys.argv[3]
	filetype = sys.argv[4]
	with olefile.OleFileIO(input) as ole:
		try:# 为了保证文件被关掉
			if filetype not in ['.wmf', '.bmp']:
				bin_data = ole.openstream("Package").read()
			else:
				bin_data = ole.openstream("CONTENTS").read()
		except:
			pass
		with open(output, "wb") as output_file:
			try:# 为了保证文件被关掉
				output_file.write(bin_data)
			except:
				pass
elif switch == 1:
	with olefile.OleFileIO(input) as ole:
		for k in ole.listdir():
			if 'WORDDOCUMENT' == k[0].upper():
				output = input + '.docx'
			elif 'WORKBOOK' == k[0].upper():
				output = input + '.xls'
	os.rename(input, output)
			
