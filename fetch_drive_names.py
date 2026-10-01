import urllib.request
import re

# File ID from the first message + all 19 new ones
urls = [
    ('1otlModfBD5Gv3HmZ6WGeexC9nuNHsazq', 'https://drive.google.com/file/d/1otlModfBD5Gv3HmZ6WGeexC9nuNHsazq/view'),
    ('1b2lVTBOfFST6_NczcHFaDkEj4PznUskd', 'https://drive.google.com/file/d/1b2lVTBOfFST6_NczcHFaDkEj4PznUskd/view'),
    ('1SWdrbjfh6eYw1c3FjqXN9rwIclgoBxme', 'https://drive.google.com/file/d/1SWdrbjfh6eYw1c3FjqXN9rwIclgoBxme/view'),
    ('10QU-q7YREhWP5_AETfbSvvuwgxqXsEkw', 'https://drive.google.com/file/d/10QU-q7YREhWP5_AETfbSvvuwgxqXsEkw/view'),
    ('1TRznu0ZEPoN0HGPl2qx51VsBoivIpLod', 'https://drive.google.com/file/d/1TRznu0ZEPoN0HGPl2qx51VsBoivIpLod/view'),
    ('1Tolmqx2Pmx0LWQ3QaRbw7E6HZMbNpwh_', 'https://drive.google.com/file/d/1Tolmqx2Pmx0LWQ3QaRbw7E6HZMbNpwh_/view'),
    ('1MeBhkxJYVH078aO2IgSORT0OtRvCF3c4', 'https://drive.google.com/file/d/1MeBhkxJYVH078aO2IgSORT0OtRvCF3c4/view'),
    ('10odZo24YebrsnjYcx3JUkMNNPUW4DGuG', 'https://drive.google.com/file/d/10odZo24YebrsnjYcx3JUkMNNPUW4DGuG/view'),
    ('1gd4XmzhbXViIBw74wqKQSC9L32TcdXOw', 'https://drive.google.com/file/d/1gd4XmzhbXViIBw74wqKQSC9L32TcdXOw/view'),
    ('1vVlb6w1Nhgqcr-uir5AUiVAafHw-aRqB', 'https://drive.google.com/file/d/1vVlb6w1Nhgqcr-uir5AUiVAafHw-aRqB/view'),
    ('1dy9mkPulOuJ3Sw1oN3tEYzI37kfKVRtJ', 'https://drive.google.com/file/d/1dy9mkPulOuJ3Sw1oN3tEYzI37kfKVRtJ/view'),
    ('1NhGZL9rN2emAVBU5IPwnFbA-8VUwuhgx', 'https://drive.google.com/file/d/1NhGZL9rN2emAVBU5IPwnFbA-8VUwuhgx/view'),
    ('13tUtstaiC0fH_kyGaYc0DZS7K6mPt1g0', 'https://drive.google.com/file/d/13tUtstaiC0fH_kyGaYc0DZS7K6mPt1g0/view'),
    ('1aVOJm894AFLdnCobSbdiULpiXHa_ACH_', 'https://drive.google.com/file/d/1aVOJm894AFLdnCobSbdiULpiXHa_ACH_/view'),
    ('1xnxiJFit-k3qAxABM2AqKZe-k8wo58JO', 'https://drive.google.com/file/d/1xnxiJFit-k3qAxABM2AqKZe-k8wo58JO/view'),
    ('1mw6obVfd1c0t_o9nkjB_5QuzroAKPchA', 'https://drive.google.com/file/d/1mw6obVfd1c0t_o9nkjB_5QuzroAKPchA/view'),
    ('1AzzZHQtoqXys0H86yAiWfD3Kroz7wc1u', 'https://drive.google.com/file/d/1AzzZHQtoqXys0H86yAiWfD3Kroz7wc1u/view'),
    ('1S74w9Sl643ZWTkZc1K44D7E6SnphSnr3', 'https://drive.google.com/file/d/1S74w9Sl643ZWTkZc1K44D7E6SnphSnr3/view'),
    ('1lMIwrYJSVn0Jx646Lnz9gmDCfV8yr197', 'https://drive.google.com/file/d/1lMIwrYJSVn0Jx646Lnz9gmDCfV8yr197/view'),
    ('1_uYSb8mfjmLKT0zxljPhwB_D6RGMadRk', 'https://drive.google.com/file/d/1_uYSb8mfjmLKT0zxljPhwB_D6RGMadRk/view'),
]

headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'}

for file_id, url in urls:
    try:
        req = urllib.request.Request(url, headers=headers)
        with urllib.request.urlopen(req, timeout=10) as resp:
            html = resp.read().decode('utf-8', errors='ignore')
            title_match = re.search(r'<title>(.*?) - Google Drive</title>', html)
            if title_match:
                print(f'{file_id}|{title_match.group(1)}')
            else:
                print(f'{file_id}|UNKNOWN')
    except Exception as e:
        print(f'{file_id}|ERROR: {e}')
