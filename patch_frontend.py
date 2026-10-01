import io

# 1. Update Retrofit Client BASE_URL
with io.open(r"app\src\main\java\com\example\abhyaas\data\network\RetrofitClient.kt", "r", encoding="utf-8") as f:
    content = f.read()

# Replace BASE_URL (assuming it is mocky or 10.0.2.2:8080)
import re
content = re.sub(r'const val BASE_URL = ".*?"', 'const val BASE_URL = "http://10.0.2.2:8000/api/v1/"', content)

with io.open(r"app\src\main\java\com\example\abhyaas\data\network\RetrofitClient.kt", "w", encoding="utf-8") as f:
    f.write(content)

# 2. Update AbhyaasApplication to use Remote
with io.open(r"app\src\main\java\com\example\abhyaas\AbhyaasApplication.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("MockTestResultRepositoryImpl()", "RemoteTestResultRepositoryImpl()")

with io.open(r"app\src\main\java\com\example\abhyaas\AbhyaasApplication.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Frontend patched.")
