import io

with io.open(r"app\src\main\java\com\example\abhyaas\AbhyaasApplication.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("RemoteTestResultRepositoryImpl()", "MockTestResultRepositoryImpl()")

with io.open(r"app\src\main\java\com\example\abhyaas\AbhyaasApplication.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("AbhyaasApplication patched.")
