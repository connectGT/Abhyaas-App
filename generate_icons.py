from PIL import Image, ImageDraw
import os
import shutil

source_image = r"C:/Users/gurut/.gemini/antigravity/brain/7f407dd1-7e01-4861-9e38-1fbe2dbab1ed/.user_uploaded/media_1790852477643.jpg"
res_dir = r"app/src/main/res"

sizes = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192
}

# Delete anydpi-v26 to remove default adaptive xml
anydpi_path = os.path.join(res_dir, "mipmap-anydpi-v26")
if os.path.exists(anydpi_path):
    shutil.rmtree(anydpi_path)

img = Image.open(source_image).convert("RGBA")

# Create circular mask for round icons
def make_round(im, size):
    im_resized = im.resize((size, size), Image.Resampling.LANCZOS)
    mask = Image.new('L', (size, size), 0)
    draw = ImageDraw.Draw(mask)
    draw.ellipse((0, 0, size, size), fill=255)
    
    result = Image.new('RGBA', (size, size), (0,0,0,0))
    result.paste(im_resized, (0, 0), mask)
    return result

for density, size in sizes.items():
    folder = os.path.join(res_dir, f"mipmap-{density}")
    if not os.path.exists(folder):
        os.makedirs(folder)
        
    # Delete old webp
    old_webp = os.path.join(folder, "ic_launcher.webp")
    old_round_webp = os.path.join(folder, "ic_launcher_round.webp")
    if os.path.exists(old_webp): os.remove(old_webp)
    if os.path.exists(old_round_webp): os.remove(old_round_webp)
    
    # Save standard
    im_resized = img.resize((size, size), Image.Resampling.LANCZOS)
    im_resized.save(os.path.join(folder, "ic_launcher.png"))
    
    # Save round
    im_round = make_round(img, size)
    im_round.save(os.path.join(folder, "ic_launcher_round.png"))

print("Icons generated successfully!")
