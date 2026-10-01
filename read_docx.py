import docx

doc = docx.Document(r'C:\Users\gurut\Downloads\ABHYAAS App\Test\full test 1.docx')
with open('docx_out.txt', 'w', encoding='utf-8') as f:
    for i, p in enumerate(doc.paragraphs[:50]):
        if p.text.strip():
            f.write(f"{i}: {p.text.strip()}\n")
