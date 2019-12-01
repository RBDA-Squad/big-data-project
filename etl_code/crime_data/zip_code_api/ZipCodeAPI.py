import requests
import json
import csv

with open('rows.csv', 'r') as source_csv:
    csv_reader = csv.reader(source_csv, delimiter='\"')
    tmp_list = []

    for row in csv_reader:
        if len(row) == 3:
            new_row_str = ''.join(row[0] + row[2])
            new_row = new_row_str.split(',')
            zipcode = "-1"

            if len(new_row) == 18:
                GOOGLE_MAPS_API_URL = 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + \
                    new_row[16] + ',' + new_row[17] + \
                    '&key=AIzaSyAMYXrUpsrTEsMc34ccjV5Ub2aoN6cdZ-Y'

                req = requests.get(GOOGLE_MAPS_API_URL)
                res = req.json()

                list1 = res['results']
                for dic1 in list1:
                    if 'address_components' not in dic1:
                        continue
                    list2 = dic1['address_components']

                    for dic2 in list2:
                        if 'types' not in dic2:
                            continue
                        list3 = dic2["types"]

                        for item in list3:
                            if item == "postal_code":
                                if 'long_name' not in dic2:
                                    continue
                                zipcode = dic2["long_name"]

            new_row.append(zipcode)
            print(new_row)
            tmp_list.append(new_row)

with open('result.csv', 'w') as result_csv:
    writer = csv.writer(result_csv)
    writer.writerows(tmp_list)
