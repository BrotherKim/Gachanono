import requests, bs4
import pandas as pd

def Craw(URLs, tableSpecStr, lineSpecStr, cellSpecStr):
  for URL in URLs:
    response = requests.get(URL).text.encode('utf-8')
    response = bs4.BeautifulSoup(response, 'html.parser')

    tables = response.select(tableSpecStr)
    print('%s,' % (URL))
    for table in tables:
      lines = table.select(lineSpecStr)
      for line in lines:
        cells = line.select(cellSpecStr)
        for cell in cells:
          data = cell.text.replace('\r', ' ').replace('\n', ' ').strip()
          print('%s' % (data), end = ',')
        print(',')
      print(',')