import urllib.request
import re

# The 5 files with Hindi names - fetch with UTF-8 stdout
urls_hindi = [
    ('1aVOJm894AFLdnCobSbdiULpiXHa_ACH_', 'https://drive.google.com/file/d/1aVOJm894AFLdnCobSbdiULpiXHa_ACH_/view'),
    ('1xnxiJFit-k3qAxABM2AqKZe-k8wo58JO', 'https://drive.google.com/file/d/1xnxiJFit-k3qAxABM2AqKZe-k8wo58JO/view'),
    ('1mw6obVfd1c0t_o9nkjB_5QuzroAKPchA', 'https://drive.google.com/file/d/1mw6obVfd1c0t_o9nkjB_5QuzroAKPchA/view'),
    ('1lMIwrYJSVn0Jx646Lnz9gmDCfV8yr197', 'https://drive.google.com/file/d/1lMIwrYJSVn0Jx646Lnz9gmDCfV8yr197/view'),
    ('1_uYSb8mfjmLKT0zxljPhwB_D6RGMadRk', 'https://drive.google.com/file/d/1_uYSb8mfjmLKT0zxljPhwB_D6RGMadRk/view'),
]

import sys, io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'}

for file_id, url in urls_hindi:
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
